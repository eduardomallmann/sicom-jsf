/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.type;

import java.io.Serializable;

/**
 * <p>
 * <b>Descrição do Enum:</b>
 * <br>Enum SubscriberType.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Este enum e responsavel por definir os tipos de Assinantes do sistema.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
public enum SubscriberType implements Serializable {

    /*
     * Cria uma instancia da enumeracao para os administradores.
     */
    ADMINISTRATOR("ADMINISTRATOR"),
    /*
     * Cria uma instancia da enumeracao para palestrantes.
     */
    PANELIST("PANELIST"),
    /*
     * Cria uma instancia da enumeracao para secretariado.
     */
    SECRETARY("SECRETARY"),
    /*
     * Cria uma instancia da enumeracao para assinantes em geral.
     */
    SUBSCRIBER("SUBSCRIBER");

    private final String subscriberType;

    /**
     * Construtor privado da classe
     *
     * @param subscriberType define o tipo de assinante.
     */
    private SubscriberType(final String subscriberType) {
        this.subscriberType = subscriberType;
    }

    @Override
    public String toString() {
        return this.subscriberType;
    }

}
