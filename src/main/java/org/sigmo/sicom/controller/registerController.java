/*
 * Copyright (c) 2009 Virtual Office.
 * Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário da Virtual Office.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.sigmo.sicom.entity.Subscriber;
import org.sigmo.sicom.service.SubscriberService;
import org.sigmo.sicom.type.SubscriberType;

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

    private Subscriber subscriber;
    private boolean existingCpf = false;
    private boolean existingEmail = false;

    /**
     * Metodo de inicialização do bean.
     */
    @PostConstruct
    public void init() {
        if (this.subscriber == null) {
            this.subscriber = new Subscriber();
        }
    }

    /**
     * Salva o objeto.
     */
    public void save() {
        //seta o papel para o subscriber
        this.subscriber.setRole(SubscriberType.SUBSCRIBER.toString());
        //salva o subscriber e retorna o objeto com o id
        this.subscriber = this.subscriberService.save(this.subscriber);
    }

    /**
     * Verifica se o usuário já existe pelo cpf informado.
     */
    public void checkCPF() {

        long existingCPF = 0;

        //verifica se o CPF foi informado
        if (this.subscriber.getCpf() != null || !"".equals(this.subscriber.getCpf())) {
            //verifica se o cpf informado já existe
            existingCPF = this.subscriberService.countByCPF(this.subscriber.getCpf());
        }
        //caso exista informa ao usuário e não salva
        if (existingCPF > 0) {
            //altera a condição do booleano
            this.existingCpf = true;
        }
    }

    /**
     * Verifica se o usuário já existe pelo e-mail informado.
     */
    public void checkEmail() {

        long existingEmail = 0;

        //verifica se o email foi informado
        if (this.subscriber.getEmail() != null || !"".equals(this.subscriber.getEmail())) {
            //verifica se o e-mail informado já existe
            existingEmail = this.subscriberService.countByEmail(this.subscriber.getEmail());
        }
        //caso exista informa ao usuário e não salva
        if (existingEmail > 0) {
            //altera a condição do booleano
            this.existingEmail = true;
        }
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public boolean isExistingCpf() {
        return existingCpf;
    }

    public void setExistingCpf(boolean existingCpf) {
        this.existingCpf = existingCpf;
    }

    public boolean isExistingEmail() {
        return existingEmail;
    }

    public void setExistingEmail(boolean existingEmail) {
        this.existingEmail = existingEmail;
    }

}
