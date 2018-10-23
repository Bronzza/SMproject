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
import java.util.List;

@FacesConverter("customerConverter")
public class CustomerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {
            CustomerRepository customerRepository = (CustomerRepository)
                    facesContext.getExternalContext().getApplicationMap().get("inject");
            try {
                for (Customer customer : customerRepository.findAll()) {
                    if (customer.getSurName().equals(s)) {
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
            return String.valueOf(((Customer) object).getId());
        } else {
            return null;
        }
    }
}
