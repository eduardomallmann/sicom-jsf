/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.exception.ExceptionMessage;
import org.sigmo.sicom.service.MailService;
import org.sigmo.sicom.service.SubscriberService;
import org.sigmo.sicom.type.SubscriberType;
import org.sigmo.sicom.util.MD5Hash;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe RegisterController.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe as funcionalidades de acesso e registro ao sistema para os usuários.
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@Named("registerController")
@SessionScoped
public class RegisterController extends BaseController implements Serializable {

    private static final long serialVersionUID = -4770797471613018053L;

    @EJB
    private SubscriberService subscriberService;
    @EJB
    private MailService mailService;

    private Subscriber subscriber;
    private Subscriber newSubscriber;
    private boolean existingCpf = false;
    private boolean existingEmail = false;
    private boolean authenticated;
    private boolean showSecretary;
    private boolean showAdmin;
    private String emailLogin;
    private String pwdLogin;

    /**
     * Metodo de inicialização do bean.
     */
    @PostConstruct
    public void init() {
        if (this.newSubscriber == null) {
            this.newSubscriber = new Subscriber();
        }
    }

    /**
     * Salva o objeto.
     */
    public void save() {
        try {
            //encripta a senha informada pelo subscriber
            this.newSubscriber.setPassword(MD5Hash.encripty(this.newSubscriber.getUnencryptedPassword()));
            //seta o papel para o subscriber
            this.newSubscriber.setRole(SubscriberType.SUBSCRIBER.toString());
            //salva o subscriber e retorna o objeto com o id
            this.setSubscriber(this.subscriberService.save(this.newSubscriber));
            //envia e-mail informando cadastro
            this.mailService.registerConfirmationMail(this.subscriber);
        } catch (BusinessException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, ex.getErrorMessages().get(0).getKey());
        }
        //adiciona mensagem de sucesso
        super.addMessage(FacesMessage.SEVERITY_INFO, "successful.create.subscriber");
    }

    /**
     * Método que salva e retorna o usuário logado para a página de inscrição.
     * <p>
     * @return página de inscrição
     */
    public String autoLogin() {
        this.save();
        return "inscricao";
    }

    /**
     * Método de autenticação via painel de login.
     * <p>
     * @return usuário logado
     */
    public String login() {
        try {
            //verifica se o email e a senha foram preenchidos
            if (emailLogin != null || pwdLogin != null || !"".equals(emailLogin) || !"".equals(pwdLogin)) {
                //Autentica o usuário e retorna o objet instanciado
                this.subscriber = subscriberService.authentication(emailLogin, pwdLogin);
                //verifica se o usuário é do secretáriado
                this.setShowSecretary("SECRETARY".equals(this.subscriber.getRole()));
                //verifica se o usuário é da administração
                this.setShowAdmin("ADMINISTRATOR".equals(this.subscriber.getRole()));
                //retorna para a página de inscrição
                return "inscricao";
            } else {
                //envia mensagem de erro caso um dos campos não tenha sido preenchido
                super.addMessage(FacesMessage.SEVERITY_ERROR, "error.login");
            }
        } catch (BusinessException ex) {
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, ex.getErrorMessages().get(0).getKey());
        }
        //retorna para a página home, caso login esteja errado.
        return "home";
    }

    /**
     * Verifica se o usuário já existe pelo cpf informado.
     */
    public void checkCPF() {
        //contador
        long countCPF;
        //verifica se o CPF foi informado
        if (this.newSubscriber.getCpf() == null || "".equals(this.newSubscriber.getCpf())) {
            return;
        } else {
            //verifica se o cpf informado já existe
            countCPF = this.subscriberService.countByCPF(this.newSubscriber.getCpf());
        }
        //caso exista informa ao usuário e não salva
        if (countCPF > 0) {
            //altera a condição do booleano
            this.existingCpf = true;
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, "error.cpf.duplicated");
        } else {
            this.existingCpf = false;
        }
    }

    /**
     * Verifica se o usuário já existe pelo e-mail informado.
     */
    public void checkEmail() {
        //contador
        long emailCount;
        //verifica se o email foi informado
        if (this.newSubscriber.getEmail() == null || "".equals(this.newSubscriber.getEmail())) {
            return;
        } else {
            //verifica se o e-mail informado já existe
            emailCount = this.subscriberService.countByEmail(this.newSubscriber.getEmail());
        }
        //caso exista informa ao usuário e não salva
        if (emailCount > 0) {
            //altera a condição do booleano
            this.existingEmail = true;
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, "error.email.duplicated");
        } else {
            this.existingEmail = false;
        }
    }

    /**
     * Envia a senha por e-mail.
     * <p>
     * @throws BusinessException caso ocorra exceção.
     */
    public void forgottenPWD() throws BusinessException {
        try {
            //retorna quantos usuários estão cadastrados com este email no db
            Long emailCount = this.subscriberService.countByEmail(emailLogin);
            //verifica se existe apenas um registro
            if (emailCount > 0 && emailCount < 2) {
                //chama o método do service que envia o e-mail
                this.mailService.sentPasswordByEmail(emailLogin);
                //adiciona mensagem de sucesso
                super.addMessage(FacesMessage.SEVERITY_INFO, "successful.mail.pwdsent");
            } else if (emailCount > 1) {
                //envia erro caso existam mais de um e-mail igual cadastrado
                throw new BusinessException("Mais de um e-mail", new ExceptionMessage("error.email.alot"));
            } else if (emailCount < 1) {
                //envia erro caso o e-mail não exista na base
                throw new BusinessException("Nao ha e-mail", new ExceptionMessage("error.email.notfound"));
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage(), e.getErrorMessages().get(0));
        }
    }

    /**
     * Método padrão alterado.
     * Autentica o Subscriber ao definir o mesmo.
     * <p>
     * @param subscriber objeto Subscriber a ser autenticado
     */
    public void setSubscriber(Subscriber subscriber) {
        try {
            //cria o usuário principal no conteiner e o define na aplicação
            this.subscriber = this.subscriberService.authentication(subscriber.getEmail(),
                                                                    subscriber.getUnencryptedPassword());
        } catch (BusinessException ex) {
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, "error.authentication.subscriber");
        }
    }

    /**
     * Verifica se o papel do usuário é de administração ou de secretariado.
     * <p>
     * @return valor booleano.
     */
    public Boolean showByRole() {
        return showAdmin || showSecretary;
    }

    /**
     * Verifica se o usuário está autenticado e retorna a sua propriedade.
     * <p>
     * @return booleano informando se o usuário está autenticado ou não.
     */
    public boolean isAuthenticated() {

        try {

            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
            HttpSession session = req.getSession();
            boolean auth = (Boolean) session.getAttribute("authenticated");

            if (auth) {
                this.authenticated = true;
            }

        } catch (Exception e) {
            this.authenticated = false;
        }

        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public boolean isExistingCpf() {
        return existingCpf;
    }

    public void setExistingCpf(boolean existingCpf) {
        this.existingCpf = existingCpf;
    }

    public boolean isExistingEmail() {
        return existingEmail;
    }

    public void setExistingEmail(boolean existingEmail) {
        this.existingEmail = existingEmail;
    }

    public Subscriber getNewSubscriber() {
        return newSubscriber;
    }

    public void setNewSubscriber(Subscriber newSubscriber) {
        this.newSubscriber = newSubscriber;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getPwdLogin() {
        return pwdLogin;
    }

    public void setPwdLogin(String pwdLogin) {
        this.pwdLogin = pwdLogin;
    }

    public boolean isShowSecretary() {
        return showSecretary;
    }

    public void setShowSecretary(boolean showSecretary) {
        this.showSecretary = showSecretary;
    }

    public boolean isShowAdmin() {
        return showAdmin;
    }

    public void setShowAdmin(boolean showAdmin) {
        this.showAdmin = showAdmin;
    }

}
