package applicationPackage.bins;

import applicationPackage.Enums.AccessRights;
import applicationPackage.Repositories.UsersRepository;
import applicationPackage.entitys.User;
import applicationPackage.utils.EmailChecker;
import org.springframework.data.domain.Example;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static applicationPackage.Enums.AccessRights.VIEWER;

@Named
public class RegistrationPage {

    private AccessRights accessRights = VIEWER;

    @Inject
    UsersRepository usersRepository;

    private String email;
    private String password;
    private String verifyPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String saveUserInformation() {
        if (!EmailChecker.checkEmail(email)) {
            sendMessage("Enter your e-mail as a login");
            return null;
        }
        if (password != null && password.equals("")) {
            sendMessage("Enter your password please");
            return null;
        }
        if (verifyPassword != null && !verifyPassword.equals(password)) {
            sendMessage("Passwords doesn't mach");
            return null;
        }



        User example = new User();
        example.seteMail(email);
        Optional<User> exists = usersRepository.findOne(Example.of(example));
        if (exists.isPresent()) {
            sendMessage("E-mail already exists in base");
            return null;
        }

        User userNew = new User();
        userNew.seteMail(email);
        userNew.setPassword(password);
        userNew.setAccessRights(accessRights);
        usersRepository.save(userNew);
        return "goToLogin";
    }



    public void sendMessage(String message) {

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

    }
}
