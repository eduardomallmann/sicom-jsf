/*
 * Copyright (c) 2009 Virtual Office.
 * Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário da Virtual Office.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.entity.SubscriberDetails;
import org.sigmo.sicom.entity.SubscriberOrder;
import org.sigmo.sicom.entity.Workshop;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.service.SubscriberDetailsService;
import org.sigmo.sicom.service.SubscriberOrderService;
import org.sigmo.sicom.service.SubscriberService;
import org.sigmo.sicom.service.WorkshopService;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe SubscriberController.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe ...
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@Named(value = "subscriberController")
@SessionScoped
public class SubscriberController extends BaseController implements Serializable {

    private static final long serialVersionUID = -7296982090340839648L;

    @EJB
    private SubscriberService subscriberService;
    @EJB
    private WorkshopService workshopService;
    @EJB
    private SubscriberDetailsService subscriberDetailsService;
    @EJB
    private SubscriberOrderService subscriberOrderService;

    private Subscriber subscriber;
    private SubscriberOrder subscriberOrder = new SubscriberOrder();
    private List<SubscriberDetails> subscriberActualDetails = new ArrayList<>();
    private boolean bilro;
    private boolean identidade;
    private boolean fotografia;
    private boolean projetos;
    private boolean arquetipos;
    private boolean empregabilidade;
    private boolean driin;

    /**
     * Acessa ao usuário logado e instância no objeto.
     *
     * @return usuário logado
     */
    public Subscriber getSubscriber() {
        try {
            //verifica se o usuário está logado
            if (FacesContext.getCurrentInstance() != null
                && (this.subscriber == null || this.subscriber.getId() == null)) {
                //captura o usuário logado da interface
                Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
                //verifica se o usuário foi capturado
                if (principal != null) {
                    //carrega o usuário correspondente do banco e instância o seu objeto
                    subscriber = this.subscriberService.login(FacesContext.getCurrentInstance().getExternalContext()
                            .getUserPrincipal().getName());
                }
            }
            //recupera os eventos já inscritos por este usuário
            this.getSubscriberActualDetails();
            //define o usuário para o pedido
            this.subscriberOrder.setSubscriber(subscriber);
            //retorna o usuário logado
            return subscriber;
        } catch (BusinessException e) {
            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, e.getExceptionMessage().toString());
            return null;
        }
    }

    /**
     * Desconecta o usuário logado e retorna para a home.
     * <p>
     * @return página home.
     */
    public String logout() {

        this.subscriber = null;
        this.subscriberOrder = null;
        this.subscriberActualDetails = null;
        this.setEventsToFalse();
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extc = ctx.getExternalContext();
        extc.invalidateSession();

        return "home";
    }

    /**
     * Salva o pedido e inicia a conversação com o pagseguro.
     */
    public void saveOrder() {
        //verifica se existem itens dentro do pedido
        if (!this.subscriberOrder.getSubscriberDetailses().isEmpty()) {
            //salva o pedido
            this.subscriberOrderService.save(this.subscriberOrder);
            //recupera os eventos já inscritos por este usuário
            this.getSubscriberActualDetails();
        }
    }

    /**
     * Remove o pedido salvo no banco.
     */
    public void cancelOrder() {
        //remove o pedido do salvo do banco
        this.subscriberOrderService.remove(SubscriberOrder.class, subscriberOrder.getId());
        //cria novo pedido e destrói anterior
        this.newOrder();
        //recupera os eventos já inscritos por este usuário
        this.getSubscriberActualDetails();
        //atualiza a página para os workshops já inscritos
        this.checkShops();
    }
    
    /**
     * Cria novo pedido e destrói anterior.
     */
    public void newOrder() {
        //inicializa o objeto do pedido
        this.subscriberOrder = new SubscriberOrder();
        //define o usuário logado no pedido
        this.subscriberOrder.setSubscriber(subscriber);
    }

    /**
     * Insere o Workshop ao pedido.
     * <p>
     * @param id atributo "id" do workshop.
     */
    public void insertSubscriptions(final Long id) {
        //verifica se o titulo foi inserido
        if (id != null) {
            //recupera o objeto workshop pelo seu titulo
            Workshop workshop = this.workshopService.findOne(id);
            //verifica se o workshop já foi inserido
            this.checkWorkshop(workshop);
            //cria e instancia novo objeto "SubscrbierDetails"
            SubscriberDetails subDetails = new SubscriberDetails();
            //define o subscriber atual ao detalhe
            subDetails.setSubscriber(this.subscriber);
            //define o workshop recuperado ao detalhe
            subDetails.setWorkshop(workshop);
            //define o objeto order atual ao detalhe
            subDetails.setOrder(this.subscriberOrder);
            //insere o detalhe a lista
            this.subscriberOrder.getSubscriberDetailses().add(subDetails);
            //atualiza a página para os workshops já inscritos
            this.checkShops();
            //atualiza o valor total da ordem
            updateOrderAmount();
        } else {
            super.addMessage(FacesMessage.SEVERITY_ERROR,
                             "Oficina/Palestra não encontrada, favor contatar nosso suporte: suporte@sigmo.org");
            //TODO:enviar e-mail informando o erro para o suporte
        }
    }

    /**
     * Verifica se o usuário já se inscreveu neste Workshop.
     * <p>
     * @param workshop objeto a ser verificado.
     */
    private void checkWorkshop(Workshop workshop) {
        for (SubscriberDetails details : subscriberActualDetails) {
            if (workshop.equals(details.getWorkshop())) {
                super.addMessage(FacesMessage.SEVERITY_INFO, "Você já está inscrito para este workshop");
                return;
            }
        }
    }

    /**
     * Atualiza a página para as oficinas e palestras compradas.
     */
    private void checkShops() {
        this.setEventsToFalse();
        if (!subscriberOrder.getSubscriberDetailses().isEmpty()) {
            for (SubscriberDetails details : subscriberOrder.getSubscriberDetailses()) {
                if (details.getWorkshop().getDescription().toLowerCase().contains("bilro")) {
                    this.setBilro(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("identidade")) {
                    this.setIdentidade(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("grafite")) {
                    this.setFotografia(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("projetos")) {
                    this.setProjetos(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("arquétipos")) {
                    this.setArquetipos(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("empregabilidade")) {
                    this.setEmpregabilidade(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("driin")) {
                    this.setDriin(true);
                }
            }
        }
        if (!subscriberActualDetails.isEmpty()) {
            for (SubscriberDetails details : subscriberActualDetails) {
                if (details.getWorkshop().getDescription().toLowerCase().contains("bilro")) {
                    this.setBilro(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("identidade")) {
                    this.setIdentidade(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("grafite")) {
                    this.setFotografia(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("projetos")) {
                    this.setProjetos(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("arquétipos")) {
                    this.setArquetipos(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("empregabilidade")) {
                    this.setEmpregabilidade(true);
                } else if (details.getWorkshop().getDescription().toLowerCase().contains("driin")) {
                    this.setDriin(true);
                }
            }
        }
    }

    /**
     * Atualiza o valor total da ordem.
     */
    private void updateOrderAmount() {
        //iniciando o valor total
        Double totalAmount = 0.00;
        //itera sobre os items da lista
        for (SubscriberDetails details : subscriberOrder.getSubscriberDetailses()) {
            //soma o valor dos workshops inseridos na lista
            totalAmount += details.getWorkshop().getPrice();
        }
        //define o valor total ao pedido
        this.subscriberOrder.setAmount(BigDecimal.valueOf(totalAmount));
    }

    /**
     * Remove o evento selecionado.
     * <p>
     * @param description descrição do evento selecionado.
     */
    public void removeItem(final String description) {
        //itera sobre os detalhes do usuário
        for (SubscriberDetails details : this.subscriberOrder.getSubscriberDetailses()) {
            //verifica se a descrição do evento corresponde a descrição informada
            if (description.equals(details.getWorkshop().getDescription())) {
                //remove o evento
                this.subscriberOrder.getSubscriberDetailses().remove(details);
                //atualiza o valor total
                this.updateOrderAmount();
                //altera a visualização do evento na view
                this.setEventViewToFalse(details.getWorkshop());
                //finaliza o método
                return;
            }
        }
    }

    /**
     * Define para falsa a visualização da inscrição na view.
     * <p>
     * @param workshop evento a ser analisado.
     */
    private void setEventViewToFalse(Workshop workshop) {
        if (workshop.getDescription().toLowerCase().contains("bilro")) {
            this.setBilro(false);
        } else if (workshop.getDescription().toLowerCase().contains("identidade")) {
            this.setIdentidade(false);
        } else if (workshop.getDescription().toLowerCase().contains("grafite")) {
            this.setFotografia(false);
        } else if (workshop.getDescription().toLowerCase().contains("projetos")) {
            this.setProjetos(false);
        } else if (workshop.getDescription().toLowerCase().contains("arquétipos")) {
            this.setArquetipos(false);
        } else if (workshop.getDescription().toLowerCase().contains("empregabilidade")) {
            this.setEmpregabilidade(false);
        } else if (workshop.getDescription().toLowerCase().contains("driin")) {
            this.setDriin(false);
        }
    }

    /**
     * Verifica se há ítens no carrinho de compras e define a renderização da tabela na view.
     * <p>
     * @return booleano informando se o carrinho possui ítens.
     */
    public boolean isTotalShoppingCartRendered() {
        return !this.subscriberOrder.getSubscriberDetailses().isEmpty();
    }

    /**
     * Define todos os eventos para não serem mostrados como inscritos na página.
     */
    private void setEventsToFalse() {
        this.setBilro(false);
        this.setIdentidade(false);
        this.setFotografia(false);
        this.setProjetos(false);
        this.setArquetipos(false);
        this.setEmpregabilidade(false);
        this.setDriin(false);
    }

    /**
     * Recupera a lista de detalhes dos usuários de pedidos já realizados.
     * <p>
     * @return lista com detalhes de pedidos já realizados.
     */
    public List<SubscriberDetails> getSubscriberActualDetails() {

        if (this.subscriber != null || this.subscriber.getId() != null) {
            //Recupera no banco a quantidade de itens persistidos
            Long countSubscriberDetails = this.subscriberDetailsService.countBySubscriber(this.subscriber.getId());
            //verifica se existe algum item persistido
            if (countSubscriberDetails > 0) {
                //recupera a lista de detalhes
                this.subscriberActualDetails = this.subscriberDetailsService.listBySubscriber(subscriber.getId());
                //atualiza a página para os workshops já inscritos
                this.checkShops();
            } else {
                //instância a lista de detalhes
                this.subscriberActualDetails = new ArrayList<>();
            }
        }

        return subscriberActualDetails;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public boolean isBilro() {
        return bilro;
    }

    public void setBilro(boolean bilro) {
        this.bilro = bilro;
    }

    public boolean isIdentidade() {
        return identidade;
    }

    public void setIdentidade(boolean identidade) {
        this.identidade = identidade;
    }

    public boolean isFotografia() {
        return fotografia;
    }

    public void setFotografia(boolean fotografia) {
        this.fotografia = fotografia;
    }

    public boolean isProjetos() {
        return projetos;
    }

    public void setProjetos(boolean projetos) {
        this.projetos = projetos;
    }

    public boolean isArquetipos() {
        return arquetipos;
    }

    public void setArquetipos(boolean arquetipos) {
        this.arquetipos = arquetipos;
    }

    public boolean isEmpregabilidade() {
        return empregabilidade;
    }

    public void setEmpregabilidade(boolean empregabilidade) {
        this.empregabilidade = empregabilidade;
    }

    public boolean isDriin() {
        return driin;
    }

    public void setDriin(boolean driin) {
        this.driin = driin;
    }

    public SubscriberOrder getSubscriberOrder() {
        return subscriberOrder;
    }

    public void setSubscriberOrder(SubscriberOrder subscriberOrder) {
        this.subscriberOrder = subscriberOrder;
    }

    public void setSubscriberActualDetails(List<SubscriberDetails> subscriberActualDetails) {
        this.subscriberActualDetails = subscriberActualDetails;
    }
}
