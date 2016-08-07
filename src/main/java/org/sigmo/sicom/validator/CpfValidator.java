/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe CpfValidator.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe valida o CPF informado.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@FacesValidator("cpfValidator")
public class CpfValidator extends BaseValidator implements Validator {

    private static final int[] CPFWHEIGHT = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * Método construtor padrão vazio.
     */
    public CpfValidator() {
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String cpf = (String) value;
        boolean isValidCPF;

        //testa valores invalidos
        if ((cpf.compareTo("00000000000") == 0)
            || (cpf.compareTo("11111111111") == 0)
            || (cpf.compareTo("22222222222") == 0)
            || (cpf.compareTo("33333333333") == 0)
            || (cpf.compareTo("44444444444") == 0)
            || (cpf.compareTo("55555555555") == 0)
            || (cpf.compareTo("66666666666") == 0)
            || (cpf.compareTo("77777777777") == 0)
            || (cpf.compareTo("88888888888") == 0)
            || (cpf.compareTo("99999999999") == 0)) {

            isValidCPF = false;

        } else {

            Integer firstDigit = digitCalc(cpf.substring(0, 9));
            Integer secondDigit = digitCalc(cpf.substring(0, 9) + firstDigit);

            isValidCPF = cpf.equals(cpf.substring(0, 9) + firstDigit.toString() + secondDigit.toString());
        }

        if (isValidCPF) {

            throw new ValidatorException(super.createMessage(FacesMessage.SEVERITY_ERROR,
                                                             "validator.cpf.invalid"));
        }

    }

    /**
     * Metodo que calcula os digitos verificadores do CPF.
     *
     * @param cpf CPF a ser veriricado
     *
     * @return digito calculado
     */
    private int digitCalc(String cpf) {

        int sum = 0;
        for (int index = cpf.length() - 1; index >= 0; index--) {
            int digit = Integer.parseInt(cpf.substring(index, index + 1));
            sum += digit * CPFWHEIGHT[CPFWHEIGHT.length - cpf.length() + index];
        }
        sum = 11 - sum % 11;

        return sum > 9 ? 0 : sum;

    }

}
