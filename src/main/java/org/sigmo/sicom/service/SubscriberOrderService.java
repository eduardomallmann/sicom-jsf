/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
import javax.ejb.Stateless;
import org.sigmo.sicom.entity.SubscriberOrder;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe SubscriberOrderService.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe implementa os serviços de negocio da entidade "SubscriberOrder".
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Stateless
public class SubscriberOrderService extends BaseService<SubscriberOrder> {

    /**
     * Recupera todos os registros da Entidade salvos no banco de dados.
     *
     * @return lista com os registros da entidade.
     */
    public List<SubscriberOrder> listAll() {
        return super.listAll(SubscriberOrder.class);
    }

    /**
     * Recupera o registro conforme seu identificador.
     *
     * @param id atributo identificador da entidade.
     *
     * @return registro com o identificador informado.
     */
    public SubscriberOrder findOne(final Long id) {
        return super.find(SubscriberOrder.class, id);
    }

    /**
     * Persiste o objeto repassado no banco de dados ou atualiza o mesmo.
     *
     * @param subscriberOrder objeto a ser persistido.
     *
     * @return objeto atualizado.
     */
    public SubscriberOrder save(final SubscriberOrder subscriberOrder) {

        SubscriberOrder subscriberOrderPersisted;

        if (subscriberOrder.getId() == null) {
            subscriberOrderPersisted = super.persist(subscriberOrder);
        } else {
            subscriberOrderPersisted = super.merge(subscriberOrder);
        }
        return subscriberOrderPersisted;
    }

}
