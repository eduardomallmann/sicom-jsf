/*
 * Copyright (c) 2016 SIGMO. Todos os direitos reservados.
 * Este software é confidencial e um produto proprietário do grupo de pesquisa da UFSC - SIGMO.
 * Qualquer uso não autorizado, reprodução ou transferência deste software é terminantemente proibida.
 */
package org.sigmo.sicom.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe EmailValidator.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe valida o endereco de email informado.
 * <br>
 *
 * @author Eduardo Mallmann <contato@eduardomallmann.com>
 */
@FacesValidator("emailValidator")
public class EmailValidator extends BaseValidator implements Validator {

    /**
     * Metodo construtor padrao vazio.
     */
    public EmailValidator() {
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String enteredEmail = (String) value;
        //Set the email pattern string
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))"
                         + "([a-zA-Z]{2,4}|[0-9]{1,6})(\\]?)$");

        //Match the given string with the pattern
        Matcher m = p.matcher(enteredEmail);

        //Check whether match is found
        boolean matchFound = m.matches();

        if (!matchFound) {

            throw new ValidatorException(super.createMessage(FacesMessage.SEVERITY_ERROR,
                                                             "Campo E-mail: informação inválida, favor rever",
                                                             "E-mail inválido."));
        }
    }

}
