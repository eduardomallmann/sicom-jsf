/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.exception;

import java.util.Arrays;
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

    private String key;
    private String[] args;

    /**
     * Metodo construtor padrão vazio.
     */
    public ExceptionMessage() {
    }

    /**
     * Metodo construtor opcional.
     * 
     * @param key chave do atributo no Resources I18n
     * @param args argumentos do erro
     */
    public ExceptionMessage(final String key, final String[] args) {
        this.key = key;
        this.args = args;
    }

    /**
     * Metodo construtor opcional.
     * 
     * @param key chave do atributo no Resources I18n
     */
    public ExceptionMessage(final String key) {
        this.key = key;
    }

    public final String getKey() {
        return key;
    }

    public final void setKey(final String key) {
        this.key = key;
    }

    public final String[] getArgs() {
        return args;
    }

    public final void setArgs(final String[] args) {
        this.args = args;
    }

    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.key);
        hash = 89 * hash + Arrays.deepHashCode(this.args);
        return hash;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExceptionMessage other = (ExceptionMessage) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Arrays.deepEquals(this.args, other.args)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "ErrorMessage{" + "key=" + key + ", args=" + args + '}';
    }


}
