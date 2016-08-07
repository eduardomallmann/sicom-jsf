/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
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

    
}