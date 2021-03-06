/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.sigmo.sicom.entity.Workshop;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe WorkshopService.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe implementa os serviços de negocio da entidade "Workshop".
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@Stateless
public class WorkshopService extends BaseService<Workshop> {

    /**
     * Recupera todos os registros da Entidade salvos no banco de dados.
     *
     * @return lista com os registros da entidade.
     */
    public List<Workshop> listAll() {
        return super.listAll(Workshop.class);
    }

    /**
     * Recupera o registro conforme seu identificador.
     *
     * @param id atributo identificador da entidade.
     *
     * @return registro com o identificador informado.
     */
    public Workshop findOne(final Long id) {
        return super.find(Workshop.class, id);
    }

    /**
     * Persiste o objeto repassado no banco de dados ou atualiza o mesmo.
     *
     * @param workshop objeto a ser persistido.
     *
     * @return objeto atualizado.
     */
    public Workshop save(final Workshop workshop) {

        Workshop workshopPersisted;

        if (workshop.getId() == null) {
            workshopPersisted = super.persist(workshop);
        } else {
            workshopPersisted = super.merge(workshop);
        }
        return workshopPersisted;
    }

    /**
     * Recupera o registro conforme seu atributo "description".
     * <p>
     * @param description atributo da entidade.
     * <p>
     * @return instância do registro recuperado.
     */
    public Workshop findByDescription(final String description) {

        StringBuffer str = new StringBuffer();
        str.append("SELECT w FROM Workshop w ");
        str.append("WHERE w.description = :description ");

        Query query = super.getEm().createQuery(str.toString());
        query.setParameter("description", description);

        return (Workshop) query.getSingleResult();

    }

}
