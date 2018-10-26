package applicationPackage.bins;

import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Enums.AccessRights;
import applicationPackage.Repositories.UsersRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static applicationPackage.Enums.AccessRights.VIEWER;

@Named
@ViewScoped
public class RegistrationPage implements Serializable {
    private String loginRegistration;
    private String passwordRegistration;
    private AccessRights accessRights = VIEWER;

    @Inject
    CustomerRepository cr;

    @Inject
    UsersRepository ur;

    private String name;
    private String surName;
    private String emailRegistration;
    private String telNumberRegistration;
    private Date birthdayRegistration;
    private boolean isManReg;

    public String getLoginRegistration() {
        return loginRegistration;
    }

    public void setLoginRegistration(String loginRegistration) {
        this.loginRegistration = loginRegistration;
    }

    public String getPasswordRegistration() {
        return passwordRegistration;
    }

    public void setPasswordRegistration(String passwordRegistration) {
        this.passwordRegistration = passwordRegistration;
    }

    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }

    public String getEmailRegistration() {
        return emailRegistration;
    }

    public void setEmailRegistration(String emailRegistration) {
        this.emailRegistration = emailRegistration;
    }

    public String getTelNumberRegistration() {
        return telNumberRegistration;
    }

    public void setTelNumberRegistration(String telNumberRegistration) {
        this.telNumberRegistration = telNumberRegistration;
    }


    public CustomerRepository getCr() {
        return cr;
    }

    public void setCr(CustomerRepository cr) {
        this.cr = cr;
    }

    public Date getBirthdayRegistration() {
        return birthdayRegistration;
    }

    public void setBirthdayRegistration(Date birthdayRegistration) {
        this.birthdayRegistration = birthdayRegistration;
    }

    public boolean isManReg() {
        return isManReg;
    }

    public void setManReg(boolean manReg) {
        isManReg = manReg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String saveRegistrationInfo() {
        String emailPattern = "^\\w+([.\\w]+)*\\w@\\w((.\\w)*\\w+)*\\.\\D{2,3}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(emailRegistration);
        if (!matcher.matches()){

//          та же проверка только ручками
//        int sobaka = emailRegistration.indexOf('@');
//        int dot = emailRegistration.lastIndexOf('.');
//        Pattern pattern = Pattern.compile("\\D*");
//        Matcher matcher = pattern.matcher(emailRegistration.substring(dot+1, emailRegistration.length()-1));
//        if (sobaka != emailRegistration.lastIndexOf('@') || (dot - sobaka <= 2) || emailRegistration.length()
//                - dot < 3 || emailRegistration.length() - dot > 4 || !matcher.matches() ) {
                sendMessage("Incorrect e-mail adress");
                return "";
            }             else {
            User user = new User();
            Customer customerTemp = new Customer();
            customerTemp.setLogin(loginRegistration);
            customerTemp.setPassword(passwordRegistration);
            customerTemp.setName(name);
            customerTemp.setSurName(surName);
            customerTemp.setTelNumber(telNumberRegistration);
            customerTemp.setEmail(emailRegistration);
            customerTemp.setBirthday(birthdayRegistration);
            customerTemp.setMan(isManReg);
            cr.save(customerTemp);
//        user.setLogin(getLoginRegistration());
//        user.setPassword(getPasswordRegistration());
//        user.setBirthday(getBirthdayRegistration());
//        user.seteMail(getEmailRegistration());
//        user.setMan(isManReg());
//        user.setName(getName());
//        user.setSurName(getSurName());
//        ur.save(user);
            sendMessage("Going to Login Page");
            return "goToLogin";
        }
    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}
