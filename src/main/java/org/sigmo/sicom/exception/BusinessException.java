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
 * Esta classe é responsavel por capturar as exceçoes do sistema.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 3753486938872731227L;

    private ExceptionMessage exceptionMessage;

    /**
     * Metodo construtor padrao.
     *
     * @param exceptionMessage mensagem de erro do sistema
     * @param message          informaçao do erro
     */
    public BusinessException(final ExceptionMessage exceptionMessage, final String message) {
        super(message);
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(final ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}
