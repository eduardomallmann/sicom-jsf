/*
 * Copyright (c) 2009 Virtual Office.
 * Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário da Virtual Office.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */

package org.sigmo.sicom.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.service.SubscriberService;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe registerController.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe ...
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@Named("registerController")
@ViewScoped
public class registerController extends BaseController implements Serializable {
    
    private static final long serialVersionUID = -4770797471613018053L;

    @EJB
    private SubscriberService subscriberService;
    
    private Subscriber subscriber = new Subscriber();

    public void save() {
        
        //TODO: Verificações e Exceções
        
        this.subscriber = this.subscriberService.save(this.subscriber);
        
    }
    
    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
    
    
}