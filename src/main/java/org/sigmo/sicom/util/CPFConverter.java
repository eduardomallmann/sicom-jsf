package org.sigmo.sicom.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * <p>
 * <b>Descrição da Classe:</b>
 * <br>Classe CPFConverter.
 * <p>
 * <b>Forma de Uso:</b>
 * <br>
 * Esta classe é responsável por converter o número de cpf para a visualização padrão.
 *
 * @author Eduardo Mallmann <eduardo.mallmann@sippulse.com>
 */
@FacesConverter("cpfConverter")
public class CPFConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String firstPart = value.substring(0, 3);
        String secondPart = value.substring(3, 6);
        String thirdPart = value.substring(6, 9);
        String lastPart = value.substring(9);

        String newValue = firstPart + "." + secondPart + "." + thirdPart + "-" + lastPart;

        return newValue;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value == null || "".equals(value)) {
            return "";
        } else {
            
            String newValue = value.toString();
            
            String firstPart = newValue.substring(0, 3);
            String secondPart = newValue.substring(3, 6);
            String thirdPart = newValue.substring(6, 9);
            String lastPart = newValue.substring(9);

            newValue = firstPart + "." + secondPart + "." + thirdPart + "-" + lastPart;

            return newValue;
        }
    }

}
