/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe MD5Hash.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe é responsável por implementar a criptografia no sistema.
 * <br>
 *
 * @author Eduardo Mallmann
 */
public class MD5Hash {

    private static MessageDigest md = null;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metodo construtor vazio padrao.
     */
    protected MD5Hash() {
    }

    /**
     * Prepara o valor para ser encriptado.
     *
     * @param text valor a ser encriptado
     * @return valor preparado
     */
    private static char[] hexCodes(final byte[] text) {

        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {

            hexString = "00" + Integer.toHexString(text[i]);

            hexString.toUpperCase().getChars(hexString.length() - 2,
                    hexString.length(), hexOutput, i * 2);
        }

        return hexOutput;
    }

    /**
     * Executa a criptografia do parâmetro informado utilizando o padrão MD5.
     *
     * @param pwd Dado que será criptografado.
     * @return String contendo o dado criptografado.
     */
    public static String encripty(final String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }

}
