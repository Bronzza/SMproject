package applicationPackage.bins;

import applicationPackage.Repositories.UsersRepository;
import applicationPackage.entitys.User;
import org.springframework.dao.DataIntegrityViolationException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

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
//        User example = new User();
//        example.setLogin(loginTemprorary);
//        example.setPassword(passwordTemprorary);
//        List <User> list = usersRepository.findAll();
//        for (User user:list) {
//            if (example.equals(user)) {
//                sendMessage("Hello");
//                return "goToMain";
//            }
//        }
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
        sendMessage("Your login or password is incorrent sir");
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
