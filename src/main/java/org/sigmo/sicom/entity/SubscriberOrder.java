/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe SubscriberOrder.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe representa em forma de objeto a entidade SubscriberOrder da tabela "subscriber_order" do banco de dados.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Entity
@Table(name = "subscriber_order")
public class SubscriberOrder implements BaseEntity {

    private static final long serialVersionUID = -6037746238262339639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<SubscriberDetails> subscriberDetailses;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "transaction_id")
    private String transactionCode;

    /**
     * Método construtor padrão.
     */
    public SubscriberOrder() {
        super();
        this.subscriberDetailses = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public List<SubscriberDetails> getSubscriberDetailses() {
        return subscriberDetailses;
    }

    public void setSubscriberDetailses(List<SubscriberDetails> subscriberDetailses) {
        this.subscriberDetailses = subscriberDetailses;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.subscriber);
        hash = 67 * hash + Objects.hashCode(this.amount);
        hash = 67 * hash + Objects.hashCode(this.transactionCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubscriberOrder other = (SubscriberOrder) obj;
        if (!Objects.equals(this.transactionCode, other.transactionCode)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.subscriber, other.subscriber)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SubscriberOrder{" + "id=" + id + ", amount=" + amount + ", transactionCode=" + transactionCode + '}';
    }

}
