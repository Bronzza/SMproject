package applicationPackage.bins;

import applicationPackage.Repositories.UsersRepository;
import applicationPackage.entitys.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class LoginPage implements Serializable{


    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void showError() {
        if (error != null && !error.trim().isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Неправильный логин или пароль", null));
        }
    }
}
