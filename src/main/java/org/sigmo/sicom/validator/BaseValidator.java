/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.validator;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe BaseValidator.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe implementa serviço básicos para validação.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class BaseValidator {

    /**
     * Adiciona mensagens no contexto corrente.
     *
     * @param severity   Define e severidade da mensagem.
     * @param subjectMsg Assunto da mensagem de erro.
     * @param bodyMsg    Mensagem de erro.
     *
     * @return mensagem da aplicação.
     */
    protected FacesMessage createMessage(final Severity severity,
                                         final String subjectMsg,
                                         final String bodyMsg) {

        //cria uma mensagem e adiciona ao contexto
        return new FacesMessage(severity, subjectMsg, bodyMsg);
    }
}
