/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe BusinessException.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe é responsavel por capturar as exceções do sistema.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 3753486938872731227L;

    private List<ExceptionMessage> exceptionMessages;

    /**
     * Construtor da classe.
     * <p>
     * @param message Recebe a mensagem de erro.
     * @param cause   Causa raiz do problema.
     */
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor da classe.
     * <p>
     * @param message       Recebe a mensagem de erro.
     * @param exceptionMessages Lista com objetos contendo mensagem i18n.
     * @param cause         Causa raiz do problema.
     */
    public BusinessException(final String message, final List<ExceptionMessage> exceptionMessages, final Throwable cause) {
        super(message, cause);
        this.exceptionMessages = exceptionMessages;
    }

    /**
     * Construtor da classe.
     * <p>
     * @param message      Recebe a mensagem de erro.
     * @param exceptionMessage Objeto contendo mensagem i18n.
     * @param cause        Causa raiz do problema.
     */
    public BusinessException(final String message, final ExceptionMessage exceptionMessage, final Throwable cause) {
        super(message, cause);
        addErrorMessage(exceptionMessage);
    }

    /**
     * Construtor da classe.
     * <p>
     * @param message       Recebe a mensagem de erro.
     * @param exceptionMessages Lista com objetos contendo mensagem i18n.
     */
    public BusinessException(final String message, final List<ExceptionMessage> exceptionMessages) {
        super(message);
        this.exceptionMessages = exceptionMessages;
    }

    /**
     * Construtor da classe.
     * <p>
     * @param message      Recebe a mensagem de erro.
     * @param exceptionMessage Objeto contendo mensagem i18n.
     */
    public BusinessException(final String message, final ExceptionMessage exceptionMessage) {
        super(message);
        addErrorMessage(exceptionMessage);
    }

    /**
     * Adiciona uma nova mensagem de erro i18n.
     * <p>
     * @param exceptionMessage Contém a mensagem internacionalizada.
     */
    public final void addErrorMessage(final ExceptionMessage exceptionMessage) {
        if (exceptionMessages == null) {
            exceptionMessages = new ArrayList<>();
        }
        exceptionMessages.add(exceptionMessage);
    }

    /**
     * Recupera as mensagens i18n.
     * <p>
     * @return Lista com a mensagens de negócio.
     */
    public final List<ExceptionMessage> getErrorMessages() {
        return exceptionMessages;
    }

}
