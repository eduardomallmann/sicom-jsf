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
 * Esta classe implementa serviço basicos para validaçao.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class BaseValidator {

    private static final String BUNDLE
            = "org.sigmo.sicom.resources.ApplicationResources";

    /**
     * Adiciona mensagens no contexto corrente.
     *
     * @param severity   Define e severidade da mensagem.
     * @param keyMessage Informa a chave da mensagem presente no bundle.
     *
     * @return mensagem da aplicaçao
     */
    protected FacesMessage createMessage(final Severity severity,
                                         final String keyMessage) {

        //Recupera o contexto corrente
        FacesContext ctx = FacesContext.getCurrentInstance();

        //Recupera o arquivo de mensagens do locale atual
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE,
                                                         ctx.getViewRoot().getLocale());

        //pega a mensagem associada a chave informada
        String msg = bundle.getString(keyMessage);

        //cria uma mensagem e adiciona ao contexto
        return new FacesMessage(severity, msg, msg);
    }
}
