/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.entity.SubscriberDetails;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe SubscriberDetailsService.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe implementa os serviços de negocio da entidade "SubscriberDetails".
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Stateless
public class SubscriberDetailsService extends BaseService<SubscriberDetails> {

    /**
     * Recupera todos os registros da Entidade salvos no banco de dados.
     *
     * @return lista com os registros da entidade.
     */
    public List<SubscriberDetails> listAll() {
        return super.listAll(SubscriberDetails.class);
    }

    /**
     * Recupera o registro conforme seu identificador.
     *
     * @param id atributo identificador da entidade.
     *
     * @return registro com o identificador informado.
     */
    public SubscriberDetails findOne(final Long id) {
        return super.find(SubscriberDetails.class, id);
    }

    /**
     * Persiste o objeto repassado no banco de dados ou atualiza o mesmo.
     *
     * @param subscriberDetails objeto a ser persistido.
     *
     * @return objeto atualizado.
     */
    public SubscriberDetails save(final SubscriberDetails subscriberDetails) {

        SubscriberDetails subscriberDetailsPersisted;

        if (subscriberDetails.getId() == null) {
            subscriberDetailsPersisted = super.persist(subscriberDetails);
        } else {
            subscriberDetailsPersisted = super.merge(subscriberDetails);
        }
        return subscriberDetailsPersisted;
    }

    /**
     * Recupera lista com "SubscriberDetails" por seu objeto "Subscriber".
     * <p>
     * @param id atributo "id" do objeto de "Subscriber".
     * <p>
     * @return lista com os objetos encontrados.
     */
    public List<SubscriberDetails> listBySubscriber(final Long id) {

        StringBuffer str = new StringBuffer();
        str.append("SELECT s FROM SubscriberDetails s ");
        str.append("WHERE s.subscriber.id = :id");

        Query query = super.getEm().createQuery(str.toString());
        query.setParameter("id", id);

        return (List<SubscriberDetails>) query.getResultList();
    }

    /**
     * Conta a quantidade de objetos existentes conforme seu objeto "Subscriber".
     * <p>
     * @param id id atributo "id" do objeto de "Subscriber".
     * <p>
     * @return quantidade de objetos encontrados.
     */
    public Long countBySubscriber(final Long id) {

        StringBuffer str = new StringBuffer();
        str.append("SELECT COUNT(s) FROM SubscriberDetails s ");
        str.append("WHERE s.subscriber.id = :id");

        Query query = super.getEm().createQuery(str.toString());
        query.setParameter("id", id);

        return (Long) query.getSingleResult();
    }

}
