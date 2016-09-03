/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.exception;

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

    private ExceptionMessage exceptionMessage;

    /**
     * Metodo construtor padrão.
     *
     * @param message informação do erro
     */
    public BusinessException(final String message) {
        super(message);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(final ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}
