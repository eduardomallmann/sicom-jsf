/*
 * Copyright (c) 2009 Virtual Office.
 * Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário da Virtual Office.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.controller;

import java.io.Serializable;
import java.security.Principal;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.exception.BusinessException;
import org.sigmo.sicom.service.SubscriberService;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe LoginController.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe implementa as funcionalidades de acesso ao sistema para os Usuários cadastrados na entidade "Subscriber".
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController extends BaseController implements Serializable {

    private static final long serialVersionUID = 8992901952152952826L;

    @EJB
    private SubscriberService subscriberService;

    private Subscriber subscriber = new Subscriber();

    /**
     * Acessa o sistema, autenticando os usuários cadastrados na entidade "Subscriber".
     *
     * @return Acessa ao sistema
     */
    @Produces
    @Named("subscriberLogged")
    public Subscriber getSubscriber() {
                try {
            if (FacesContext.getCurrentInstance() != null && (this.subscriber == null
                                                              || this.subscriber.getId() == null)) {

                Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

                if (principal != null) {
                    subscriber = this.subscriberService.login(FacesContext.getCurrentInstance().getExternalContext()
                            .getUserPrincipal().getName());
                }
            }
            return subscriber;

        } catch (BusinessException e) {

            //adiciona mensagem de erro
            super.addMessage(FacesMessage.SEVERITY_ERROR, e.getExceptionMessage().toString());
            return null;
        }
    }

    public SubscriberService getSubscriberService() {
        return subscriberService;
    }

    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

}
