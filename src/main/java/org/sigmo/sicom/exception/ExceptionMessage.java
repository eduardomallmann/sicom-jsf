/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.exception;

import java.util.Objects;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe ExceptionMessage.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe é responsavel pelas mensagens de erro do sistema.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public class ExceptionMessage {

    private String messageKey;

    /**
     * Método construtor padrão.
     *
     * @param messageKey chave da mensagem internacionalizada
     */
    public ExceptionMessage(final String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(final String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.messageKey);
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
        final ExceptionMessage other = (ExceptionMessage) obj;
        if (!Objects.equals(this.messageKey, other.messageKey)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExceptionMessage{" + "messageKey=" + messageKey + '}';
    }

}
