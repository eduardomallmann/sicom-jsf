/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe Subscriber.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe representa em forma de objeto a entidade Subscriber da tabela "subscriber" do banco de dados.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Entity
@Table(name = "subscriber")
@NamedQueries(value = {
    @NamedQuery(
            name = "Subscriber.listByRole",
            query = "SELECT s FROM Subscriber s "
                    + " WHERE s.role = :role "
                    + " ORDER BY s.fullName ASC "),
    @NamedQuery(
            name = "Subscriber.login",
            query = "SELECT s FROM Subscriber s "
                    + "WHERE s.email = :email ")
})
public class Subscriber implements BaseEntity {

    private static final long serialVersionUID = -6592407682392159729L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "event")
    private boolean event;
    @Column(name = "job")
    private String job;
    @Column(name = "course")
    private String course;
    @OneToMany(mappedBy = "subscriber")
    private List<SubscriberDetails> subscriberDetailses;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public boolean isEvent() {
        return event;
    }

    public void setEvent(final boolean event) {
        this.event = event;
    }

    public String getJob() {
        return job;
    }

    public void setJob(final String job) {
        this.job = job;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(final String course) {
        this.course = course;
    }

    public List<SubscriberDetails> getSubscriberDetailses() {
        return subscriberDetailses;
    }

    public void setSubscriberDetailses(final List<SubscriberDetails> subscriberDetailses) {
        this.subscriberDetailses = subscriberDetailses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.fullName);
        hash = 17 * hash + Objects.hashCode(this.cpf);
        hash = 17 * hash + Objects.hashCode(this.email);
        hash = 17 * hash + Objects.hashCode(this.password);
        hash = 17 * hash + Objects.hashCode(this.role);
        hash = 17 * hash + (this.event ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.job);
        hash = 17 * hash + Objects.hashCode(this.course);
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
        final Subscriber other = (Subscriber) obj;
        if (this.event != other.event) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.job, other.job)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Subscriber{" + "id=" + id + ", fullName=" + fullName + ", cpf=" + cpf + ", email=" + email
               + ", password=" + password + ", role=" + role + ", event=" + event + ", job=" + job + ", course=" + course
               + '}';
    }

}
