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

    @Inject
    UsersRepository usersRepository;

    @Column (unique = true, nullable = false)
    private String emailTemprorary;
    private String passwordTemprorary;

    public void saveData (){
        User user = new User();
        user.seteMail(emailTemprorary);
        user.setPassword(passwordTemprorary);
        if(usersRepository.findOne(Example.of(user)).isPresent()) {
            sendMessage("Email is already exists");
            return;
        }
    }

    public String login (){
        User example = new User();
        example.seteMail(emailTemprorary);
        example.setPassword(passwordTemprorary);
        List <User> list = usersRepository.findAll();
        for (User user:list) {
            if (example.equals(user)) {
                sendMessage("Hello");
                return "goToMain";
            }
        }
        sendMessage("Your login or password is incorrent sir");
        return null;
    }

    public String getEmailTemprorary() {
        return emailTemprorary;
    }

    public void setEmailTemprorary(String emailTemprorary) {
        this.emailTemprorary = emailTemprorary;
    }

    public String getPasswordTemprorary() {
        return passwordTemprorary;
    }

    public void setPasswordTemprorary(String passwordTemprorary) {
        this.passwordTemprorary = passwordTemprorary;
    }


    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
    public String goToRegistration(){
        sendMessage("Registration page");
        return "goToRegist";
    }
}
