/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

//import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import java.util.List;
import javax.ejb.Stateless;
import org.sigmo.sicom.entity.SubscriberOrder;
import org.sigmo.sicom.helper.PagSeguroIntegrator;

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
     * @return objeto salvo.
     */
    public SubscriberOrder save(final SubscriberOrder subscriberOrder) {
        //instancia novo objeto a receber as modificações a serem realizadas
        SubscriberOrder subscriberOrderPersisted;
        //verifica se o objeto é novo ou será atualizado
        if (subscriberOrder.getId() == null) {
            //persiste o novo objeto no banco de dados
            subscriberOrderPersisted = super.persist(subscriberOrder);
        } else {
            //atualiza o objeto existente
            subscriberOrderPersisted = super.merge(subscriberOrder);
        }
        //verifica se será necessário enviar ao pagseguro
        if (subscriberOrder.getAmount().longValue() > 0) {
            //recupera a url de redirecionamento para realizar o pagamento
//            subscriberOrderPersisted = this.saveRedirectURL(subscriberOrderPersisted);
        }
        //retorna o subscriber salvo
        return subscriberOrderPersisted;
    }

    /**
     * Recupera a url de redirecionamento para realizar o pagamento no pagseguro.
     * <p>
     * @param subscriberOrder objeto a ser atualizado.
     * <p>
     * @return objeto salvo
     * <p>
     * @throws PagSeguroServiceException caso ocorra erro.
     */
//    public SubscriberOrder saveRedirectURL(final SubscriberOrder subscriberOrder) throws PagSeguroServiceException {
//        //recebe a url de redirecionamenot do site do pagseguro
//        String redirectURL = PagSeguroIntegrator.register(subscriberOrder);
//        //salva a url recebida no objeto
//        subscriberOrder.setRedirectURL(redirectURL);
//        //retorna o objeto atualizado no banco de dados
//        return super.merge(subscriberOrder);
//    }

}
