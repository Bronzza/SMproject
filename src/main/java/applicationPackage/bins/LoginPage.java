package applicationPackage.bins;

import applicationPackage.UsersRepository;
import applicationPackage.entitys.User;
import org.hibernate.Hibernate;
import org.springframework.core.Constants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
public class LoginPage implements Serializable{

    @Inject
    UsersRepository usersRepository;
    @Column (/*unique = true,*/ nullable = false)
    private String loginTemprorary;

    private String passwordTemprorary;

    public void saveData (){
        User user = new User();
        user.setLogin(loginTemprorary);
        user.setPassword(passwordTemprorary);
        try{
        usersRepository.save(user);}
        catch (DataIntegrityViolationException e){
            sendMessage("Login is already exists");
            return;
        }
    }

    public String login (){
        User example = new User();
        example.setLogin(loginTemprorary);
        example.setPassword(passwordTemprorary);
        List <User> list = usersRepository.findAll();
        for (User user:list) {
            if (example.equals(user)) {
                sendMessage("Hello");
                return "goToMain";
            }
        }
//       узнать почему не работает. не находит, existing is empty
//        Optional<User> existing = usersRepository.findOne(Example.of(example));
//        if (existing.isPresent()) {
//            sendMessage("Hello");
//            return "goToMain";
//        }
        sendMessage("Your login doesn't exist");
        return null;
    }

    public String getLoginTemprorary() {
        return loginTemprorary;
    }

    public void setLoginTemprorary(String loginTemprorary) {
        this.loginTemprorary = loginTemprorary;
    }

    public String getPasswordTemprorary() {
        return passwordTemprorary;
    }

    public void setPasswordTemprorary(String passwordTemprorary) {
        this.passwordTemprorary = passwordTemprorary;
    }
//        public String checkPassword() {
//        if (passwordTemprorary.equals("password")) {
//            sendMessage("Hello");
//            return "goToMain";
//        } else {
//            login = "";
//            password = "";
//            sendMessage("Incorrect password");
//        }
//        return null;
//    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}
