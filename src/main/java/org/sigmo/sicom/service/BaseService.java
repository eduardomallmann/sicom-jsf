/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.sigmo.sicom.entity.BaseEntity;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe BaseService.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe é responsável por implementar os serviços básicos de CRUD e acesso a persistência via JPA.
 * <br>
 *
 * @param <T> Entidade que sera manipulada
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 *
 */
@SuppressWarnings({"checkstyle:designforextension", "unchecked"})
public class BaseService<T extends BaseEntity> {

    /**
     * Injeçao do EntityManager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Metodo responsavel por recuperar objeto da entidade manipulada a partir de seu identificador serializado.
     *
     * @param entityType entidade a ser recuperada
     * @param identifier identificador da entidade a ser maniulada
     *
     * @return Instancia encontrada da entidade manipulada
     */
    public T find(final Class<T> entityType, final Serializable identifier) {

        T bean = em.find(entityType, identifier);
        em.refresh(bean);
        return bean;
    }

    /**
     * Metodo responsavel por recuperar a lista de objetos existentes para a entidade manipulada.
     *
     * @param entityType entidade a ser listada
     *
     * @return Lista com os objetos da entidade manipulada
     */
    public List<T> listAll(final Class<T> entityType) {

        StringBuffer strQry = new StringBuffer();
        strQry.append("SELECT object(o) FROM ").append(entityType.getSimpleName()).append(" AS o");
        final Query query = em.createQuery(strQry.toString());
        return query.getResultList();
    }

    /**
     * Metodo responsavel por persistir um objeto da entidade manipulada.
     *
     * @param bean objeto a ser persistido
     *
     * @return objeto persistido.
     */
    public T persist(final T bean) {

        em.persist(bean);
        em.flush();
        return bean;
    }

    /**
     * Metodo responsavel por atualizar o objeto da entidade manipulada.
     *
     * @param bean objeto a ser atualizado
     *
     * @return objeto atualizado.
     */
    public T merge(final T bean) {

        em.merge(bean);
        em.flush();
        return bean;
    }

    /**
     * Metodo responsavel por remover o objeto da entidade manipulada.
     *
     * @param bean objeto a ser removido
     */
    public void remove(final T bean) {

        em.remove(bean);
        em.flush();
    }

    /**
     * Metodo responsavel por remover objeto da entidade manipulada a partir de seu identificador serializado.
     *
     * @param entityType entidade a ser removida
     * @param identifier identificador do objeto a ser removido
     */
    public void remove(final Class<T> entityType, final Serializable identifier) {

        final T bean = this.find(entityType, identifier);
        if (bean != null) {
            em.remove(bean);
            em.flush();
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
