/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.exception.ExceptionMessage;
import org.sigmo.sicom.util.MD5Hash;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe SubscriberService.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe implementa os serviços de negocio da entidade "Subscriber".
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Stateless
public class SubscriberService extends BaseService<Subscriber> {

    /**
     * Retorna o objeto da entidade "Subscriber" autenticado por seu "login" e "password".
     *
     * @param email atributo "email" da entidade "Subscriber"
     * <p>
     * @return Retorna o objeto atualizado, com o seu identificador atualizado
     * <p>
     * @throws BusinessException Caso ocorra alguma exceção
     * @see Subscriber
     */
    public Subscriber login(final String email) throws BusinessException {

        try {
            //monta a query para autenticação dos usuários
            Query query = super.getEm().createNamedQuery("Subscriber.login");

            //define os parametros login e password
            query.setParameter("email", email);

            //executa a consulta
            return (Subscriber) query.getSingleResult();

        } catch (NoResultException nre) {

            throw new BusinessException("Login ou Senha inexiste.", new ExceptionMessage("error.login.notmatched"));

        } catch (Exception e) {

            throw new BusinessException("Problemas no acesso ao sistema.", new ExceptionMessage("error.login.backend"));
        }
    }

    /**
     * Método que autentica o "Subscriber".
     * <p>
     * @param email    atributo "email" da entidade "Subscriber"
     * @param password atributo "password" da entidade "Subscriber"
     * <p>
     * @return Subscriber autenticado
     * <p>
     * @throws BusinessException Caso o Subscriber não tenha sido persistido previamente.
     */
    public Subscriber authentication(final String email, final String password) throws BusinessException {
        try {
            //busca o contexto do conteiner da aplicação
            FacesContext context = FacesContext.getCurrentInstance();
            //define o request
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            //define a sessão
            HttpSession session = request.getSession(false);
            //define o tempo máximo de inatividade desta sessão
            session.setMaxInactiveInterval(1800);
            //autentica o Subscriber na sessão
            request.login(email, password);
            //busca o Subscriber logado
            Subscriber subscriber = this.login(email);
            //define a sessão como autenticada
            session.setAttribute("authenticated", true);
            //retorna o Subscriber autenticado
            return subscriber;
        } catch (ServletException ex) {
            throw new BusinessException("Falha na autenticação do usuário.",
                                        new ExceptionMessage("error.authentication.subscriber"));
        }
    }

    /**
     * Recupera todos os registros da Entidade salvos no banco de dados.
     *
     * @return lista com os registros da entidade.
     */
    public List<Subscriber> listAll() {
        return super.listAll(Subscriber.class);
    }

    /**
     * Recupera todos os registros da Entidade salvos no banco de dados que possuam o parâmetro informado.
     *
     * @param role atributo da entidade.
     *
     * @return lista com os registros encontrados.
     */
    public List<Subscriber> listByRole(final String role) {
        //cria a pesquisa
        Query query = super.getEm().createNamedQuery("Subscriber.listByRole");
        //define o parâmetro da pesquisa
        query.setParameter("role", role);
        //retorna os resultados da pesquisa
        return (List<Subscriber>) query.getResultList();
    }

    /**
     * Altera a senha do registro.
     * <p>
     * @param newPwd       nova senha a ser inserida.
     * @param subscriberId identificador do objeto.
     * <p>
     * @return objeto com senha alterada.
     */
    public Subscriber changePwd(final String newPwd, final Long subscriberId) {
        
        String password = MD5Hash.encripty(newPwd);        

        //monta a pesquisa
        StringBuffer str = new StringBuffer();
        str.append("UPDATE Subscriber s ");
        str.append(" SET s.password = '").append(password).append("', ");
        str.append(" s.unencryptedPassword = '").append(newPwd).append("' ");
        str.append(" WHERE s.id = ").append(subscriberId);
        //cria a pesquisapesquisa
        Query query = super.getEm().createQuery(str.toString());
        //executa a pesquisa
        query.executeUpdate();
        //manda o db atualizar tudo q está só em memória
        super.getEm().flush();
        //recupera o objeto atualizado
        Subscriber subsChanged = this.findOne(subscriberId);
        //retorna o objeto atualizado
        return subsChanged;
    }

    /**
     * Recupera o registro conforme seu identificador.
     *
     * @param id atributo identificador da entidade.
     *
     * @return registro com o identificador informado.
     */
    public Subscriber findOne(final Long id) {
        return super.find(Subscriber.class, id);
    }

    /**
     * Recupera o registro conforme seu atributo email.
     * <p>
     * @param email atributo email do usuário a ser pesquisado.
     * <p>
     * @return usuário com o e-mail informado.
     */
    public Subscriber findByEmail(final String email) {
        //cria os comandos SQL
        StringBuffer strQuery = new StringBuffer();
        strQuery.append(" SELECT s FROM Subscriber s ");
        strQuery.append(" WHERE s.email = :email ");
        //cria a pesquisa
        Query query = super.getEm().createQuery(strQuery.toString());
        //insere o parâmetro email
        query.setParameter("email", email);
        //retorna o usuário pelo e-mail cadastrado
        return (Subscriber) query.getSingleResult();
    }

    /**
     * Conta a quantidade de registros com o email informado.
     * <p>
     * @param email atributo e-mail da entidade
     * <p>
     * @return quantidade de registros com o e-mail informado.
     */
    public Long countByEmail(String email) {
        //cria os comandos SQL
        StringBuffer strQuery = new StringBuffer();
        strQuery.append(" SELECT COUNT(s) FROM Subscriber s ");
        strQuery.append(" WHERE s.email = :email ");
        //cria a pesquisa
        Query query = super.getEm().createQuery(strQuery.toString());
        //insere o parâmetro email
        query.setParameter("email", email);
        //retorna a contagem
        return (Long) query.getSingleResult();
    }

    /**
     * Conta a quantidade de registros com o cpf informado.
     * <p>
     * @param cpf atributo cpf da entidade
     * <p>
     * @return quantidade de registros com o cpf informado.
     */
    public Long countByCPF(final String cpf) {
        //cria os comandos SQL
        StringBuffer strQuery = new StringBuffer();
        strQuery.append(" SELECT COUNT(s) FROM Subscriber s ");
        strQuery.append(" WHERE s.cpf = :cpf ");
        //cria a pesquisa
        Query query = super.getEm().createQuery(strQuery.toString());
        //insere o parâmetro email
        query.setParameter("cpf", cpf);
        //retorna a contagem
        return (Long) query.getSingleResult();
    }

    /**
     * Persiste o objeto repassado no banco de dados ou atualiza o mesmo.
     *
     * @param subscriber objeto a ser persistido.
     *
     * @return objeto atualizado.
     */
    public Subscriber save(final Subscriber subscriber) {

        Subscriber subscriberPersisted;

        if (subscriber.getId() == null) {
            subscriberPersisted = super.persist(subscriber);
        } else {
            subscriberPersisted = super.merge(subscriber);
        }
        return subscriberPersisted;
    }

}
