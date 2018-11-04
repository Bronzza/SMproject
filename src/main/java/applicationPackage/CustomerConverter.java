package applicationPackage;


import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.entitys.Customer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class CustomerConverter implements Converter {

    @Inject
    CustomerRepository customerRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {

            try {
                for (Customer customer : customerRepository.findAll()) {
                    if (s.toLowerCase().contains(customer.getSurName().toLowerCase())) {
                        return customer;
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error",
                        "Not a valid customer"));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null) {
            if (((Customer)object).getSurName() == null) return "";
            return object.toString();
        } else {
            return "";
        }
    }
}
