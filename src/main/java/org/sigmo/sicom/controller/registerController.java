/*
 * Copyright (c) 2009 Virtual Office.
 * Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário da Virtual Office.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.service.SubscriberService;
import org.sigmo.sicom.type.SubscriberType;
import org.sigmo.sicom.util.MD5Hash;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe registerController.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe ...
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@Named("registerController")
@SessionScoped
public class registerController extends BaseController implements Serializable {

    private static final long serialVersionUID = -4770797471613018053L;

    @EJB
    private SubscriberService subscriberService;

    private Subscriber subscriber;
    private Subscriber newSubscriber;
    private boolean existingCpf = false;
    private boolean existingEmail = false;
    private String password;
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
        //guarda password não encriptado para ser utilizado no login
        password = this.newSubscriber.getPassword();
        //encripta a senha informada pelo subscriber
        this.newSubscriber.setPassword(MD5Hash.encripty(this.newSubscriber.getPassword()));
        //seta o papel para o subscriber
        this.newSubscriber.setRole(SubscriberType.SUBSCRIBER.toString());
        //salva o subscriber e retorna o objeto com o id
        this.setSubscriber(this.subscriberService.save(this.newSubscriber));
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
                //retorna para a página de inscrição
                return "inscricao";
            } else {
                //envia mensagem de erro caso um dos campos não tenha sido preenchido
                super.addMessage(FacesMessage.SEVERITY_ERROR, "Campos de e-mail ou senha devem ser preenchidos");
            }
        } catch (BusinessException ex) {
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, ex.getExceptionMessage().toString());
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
            this.subscriber = this.subscriberService.authentication(subscriber.getEmail(), this.password);
        } catch (BusinessException ex) {
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, ex.getExceptionMessage().toString());
        }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
