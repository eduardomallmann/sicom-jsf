package org.sigmo.sicom.helper;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.NotificationService;
import java.math.BigDecimal;
import org.sigmo.sicom.entity.SubscriberDetails;
import org.sigmo.sicom.entity.SubscriberOrder;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe PagSeguroIntegrator.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe auxilia na integração dos pagamentos via pagseguro.
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class PagSeguroIntegrator {

    /**
     * Registra a operação no pagseguro e recebe a url de redirecionamento para o site do pagseguro.
     * <p>
     * @param order pedido a ser enviado ao pagseguro.
     * <p>
     * @return url de redirecionamento.
     * <p>
     * @throws PagSeguroServiceException caso ocorra exceção.
     */
    public static String register(SubscriberOrder order) throws PagSeguroServiceException {
        //instancia novo objeto checkout
        Checkout checkout = new Checkout();
        //define a moeda para o padrão brasileiro
        checkout.setCurrency(Currency.BRL);
        //referenciando a transação do PagSeguro em seu sistema  
        checkout.setReference(order.getId().toString());
        //URL para onde o comprador será redirecionado (GET) após o fluxo de pagamento  
        checkout.setRedirectURL("http://www.sigmo.org/sicom/inscricao.jsf");
        //URL para onde serão enviadas notificações (POST) indicando alterações no status da transação  
        checkout.setNotificationURL("http://www.sigmo.org/sicom/notifications");
        //itera sobre os itens e os adiciona no checkout
        for (SubscriberDetails events : order.getSubscriberDetailses()) {
            //instancia um novo item a ser adicionado no checkout
            Item item = new Item();
            //define o identificador
            item.setId(events.getWorkshop().getId().toString());
            //define a descrição
            item.setDescription(events.getWorkshop().getDescription());
            //define a quantidade
            item.setQuantity(1);
            //converte o valor em double para BigDecimal
            BigDecimal eventAmount = new BigDecimal(events.getWorkshop().getPrice());
            //define o valor unitário do item
            item.setAmount(eventAmount);
            //adiciona o item no checkout
            checkout.addItem(item);
        }
        //instancia o objeto com informações do usuário que está realizando a compra
        Sender sender = new Sender();
        //define o nome do usuário
        sender.setName(order.getSubscriber().getFullName());
        //define o e-mail do usuário
        sender.setEmail(order.getSubscriber().getEmail());
        //define o CPF do usuário
        sender.addDocument(DocumentType.CPF, order.getSubscriber().getCpf());
        //adiciona o usuário ao checkout
        checkout.setSender(sender);

        try {
            // TODO: inserir o método de autenticação.
            
            //recupera a url retornada pelo registro do checkout no pagseguro
            String checkoutURL = checkout.register(PagSeguroConfig.getAccountCredentials());
            //retorna a url recebida do pagseguro para regirecionamento
            return checkoutURL;

        } catch (PagSeguroServiceException e) {
            // TODO: verificar uma forma de tratar o erro.
            e.printStackTrace();
        }
        return null;
    }

    
    
    /**
     * Recupera o status da transação no pagseguro de acordo com o codigo
     * recebido via notificação.
     * <p>
     * @param notificationCode Codigo da notificação enviado pelo pagseguro.
     * <p>
     * @return Transaction contem os dados atuais da transação.
     * <p>
     * @throws PagSeguroServiceException caso ocorra alguma exceção.
     */
    public static Transaction retrieveTransactionStatus(String notificationCode)
            throws PagSeguroServiceException {

        return NotificationService.checkTransaction(PagSeguroConfig.getAccountCredentials(), notificationCode);
    }

}
