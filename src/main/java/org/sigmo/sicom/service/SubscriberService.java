/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.exception.ExceptionMessage;

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

            throw new BusinessException(new ExceptionMessage("login.invalid"), "Login ou Senha inexiste.");

        } catch (Exception e) {

            throw new BusinessException(new ExceptionMessage("error.generic.exception"), 
                    "Problemas no acesso ao sistema.");
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
        Query query = super.getEm().createNamedQuery("Subscriber.listByRole");
        query.setParameter("role", role);
        return (List<Subscriber>) query.getResultList();
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
