package applicationPackage.bins;

import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Enums.AccessRights;
import applicationPackage.Repositories.UsersRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import java.io.Serializable;
import java.util.Date;

import static applicationPackage.Enums.AccessRights.VIEWER;

@Named
public class RegistrationPage implements Serializable{
    private String loginRegistration;
    private String passwordRegistration;
    private AccessRights accessRights = VIEWER;

    @Inject
    CustomerRepository cr;

    @Inject
    UsersRepository ur;

    private String Name;
    private String SurName;
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String saveRegistrationInfo() {
        User user = new User();
        Customer customerTemp = new Customer();
        customerTemp.setLogin(getLoginRegistration());
        customerTemp.setPassword(getPasswordRegistration());
        customerTemp.setName(getName());
        customerTemp.setSurName(getSurName());
        customerTemp.setTelNumber(getTelNumberRegistration());
        customerTemp.setEmail(getEmailRegistration());
        customerTemp.setBirthday(getBirthdayRegistration());
        customerTemp.setMan(isManReg());
        cr.save(customerTemp);
        user.setLogin(getLoginRegistration());
        user.setPassword(getPasswordRegistration());
        user.setBirthday(getBirthdayRegistration());
        user.seteMail(getEmailRegistration());
        user.setMan(isManReg());
        user.setName(getName());
        user.setSurName(getSurName());
        ur.save(user);
        sendMessage("Going to Login Page");
        return "goToLogin";

    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}
