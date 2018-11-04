package applicationPackage.bins;

import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Enums.AccessRights;
import applicationPackage.Repositories.UsersRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.User;
import applicationPackage.utils.BirthDayChecker;
import applicationPackage.utils.EmailChecker;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static applicationPackage.Enums.AccessRights.ROLE_VIEWER;

@Named
@ViewScoped
public class AddClientPage implements Serializable {

    String passwordRegistration;

    @Inject
    CustomerRepository cr;

    @Inject
    UsersRepository ur;

    private String name;
    private String surName;
    private String emailRegistration;
    private String telNumberRegistration;
    private String birthdayRegistration;
    private boolean isManReg;
    private String notesField;

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

    public String getBirthdayRegistration() {
        return birthdayRegistration;
    }

    public void setBirthdayRegistration(String birthdayRegistration) {
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
        if (!EmailChecker.checkEmail(emailRegistration)) {
            sendMessage("Incorrect e-mail adress");
            return "";
        } else if (!BirthDayChecker.checkDate(birthdayRegistration)) {
            sendMessage("Incorrect birthday date ");
            return "";
        } else {
            Customer customerTemp = new Customer();

            customerTemp.setPassword(passwordRegistration);
            customerTemp.setName(name);
            customerTemp.setSurName(surName);
            customerTemp.setTelNumber(telNumberRegistration);
            customerTemp.setEmail(emailRegistration);
            customerTemp.setBirthday(birthdayRegistration);
            customerTemp.setMan(isManReg);
            customerTemp.setNotes(notesField);
            cr.save(customerTemp);

            sendMessage("Your data is saved");
            return "goToRegist";
        }
    }

    public String getPasswordRegistration() {
        return passwordRegistration;
    }

    public void setPasswordRegistration(String passwordRegistration) {
        this.passwordRegistration = passwordRegistration;
    }

    public String getNotesField() {
        return notesField;
    }

    public void setNotesField(String notesField) {
        this.notesField = notesField;
    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}
