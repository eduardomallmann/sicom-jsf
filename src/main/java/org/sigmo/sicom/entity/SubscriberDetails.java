/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe SubscriberDetails.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe representa em forma de objeto a entidade SubscriberDetails da tabela "subscriber_details" do banco de
 * dados.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Entity
@Table(name = "subscriber_details")
public class SubscriberDetails implements BaseEntity {

    private static final long serialVersionUID = -3033344519185325956L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;
    @ManyToOne
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;
    @Column(name = "presence")
    private boolean presence;
    @Column(name = "payment")
    private boolean payment;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private SubscriberOrder order;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(final Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(final Workshop workshop) {
        this.workshop = workshop;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(final boolean presence) {
        this.presence = presence;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(final boolean payment) {
        this.payment = payment;
    }

    public SubscriberOrder getOrder() {
        return order;
    }

    public void setOrder(SubscriberOrder order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.subscriber);
        hash = 71 * hash + Objects.hashCode(this.workshop);
        hash = 71 * hash + (this.presence ? 1 : 0);
        hash = 71 * hash + (this.payment ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubscriberDetails other = (SubscriberDetails) obj;
        if (this.presence != other.presence) {
            return false;
        }
        if (this.payment != other.payment) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.subscriber, other.subscriber)) {
            return false;
        }
        if (!Objects.equals(this.workshop, other.workshop)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SubscriberDetails{" + "id=" + id + ", presence=" + presence + ", payment=" + payment + '}';

    }
}
