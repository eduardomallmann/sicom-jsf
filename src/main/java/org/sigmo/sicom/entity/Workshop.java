/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe Workshop.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe representa em forma de objeto a entidade Workshop da tabela "workshop" do banco de dados.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Entity
@Table(name = "workshop")
public class Workshop implements BaseEntity {

    private static final long serialVersionUID = -1496475428168980525L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description", unique= true, nullable = false)
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "panelist")
    private String panelist;
    @Column(name = "workshop_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date workshopDate;
    @OneToMany(mappedBy = "workshop")
    private List<SubscriberDetails> subscriberDetailses;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String getPanelist() {
        return panelist;
    }

    public void setPanelist(final String panelist) {
        this.panelist = panelist;
    }

    public Date getWorkshopDate() {
        return workshopDate;
    }

    public void setWorkshopDate(final Date workshopDate) {
        this.workshopDate = workshopDate;
    }

    public List<SubscriberDetails> getSubscriberDetailses() {
        return subscriberDetailses;
    }

    public void setSubscriberDetailses(final List<SubscriberDetails> subscriberDetailses) {
        this.subscriberDetailses = subscriberDetailses;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.price);
        hash = 97 * hash + Objects.hashCode(this.panelist);
        hash = 97 * hash + Objects.hashCode(this.workshopDate);
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
        final Workshop other = (Workshop) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.panelist, other.panelist)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.workshopDate, other.workshopDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workshop{" + "id=" + id + ", description=" + description + ", price=" + price
               + ", panelist=" + panelist + ", workshopDate=" + workshopDate + '}';
    }

}
