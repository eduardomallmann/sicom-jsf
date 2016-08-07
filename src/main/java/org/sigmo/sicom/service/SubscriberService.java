/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
import javax.persistence.Query;
import org.sigmo.sicom.entity.Subscriber;

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
public class SubscriberService extends BaseService<Subscriber> {

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
