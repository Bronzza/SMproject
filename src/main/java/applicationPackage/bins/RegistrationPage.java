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
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static applicationPackage.Enums.AccessRights.VIEWER;

@Named
@ViewScoped
public class RegistrationPage implements Serializable {

    private AccessRights accessRights = VIEWER;

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
        if (!checkEmail(emailRegistration)) {
            sendMessage("Incorrect e-mail adress");
            return "";
        } else if (!checkDate(birthdayRegistration)) {
            sendMessage("Incorrect birthday date ");
            return "";
        } else {
            User user = new User();
            Customer customerTemp = new Customer();
            customerTemp.setPassword(passwordRegistration);
            customerTemp.setName(name);
            customerTemp.setSurName(surName);
            customerTemp.setTelNumber(telNumberRegistration);
            customerTemp.setEmail(emailRegistration);
            customerTemp.setBirthday(birthdayRegistration);
            customerTemp.setMan(isManReg);
            cr.save(customerTemp);

            user.setPassword(passwordRegistration);
            user.setBirthday(birthdayRegistration);
            user.seteMail(emailRegistration);
            user.setMan(isManReg);
            user.setName(name);
            user.setSurName(surName);
            ur.save(user);
            sendMessage("Your data is saved");
            return "";
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

    public boolean checkDate(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        String[] delimBrithDay = new String[3];
        int i = 0;
        while (st.hasMoreElements()) {
            delimBrithDay[i] = (String) st.nextElement();
            i++;
        }
        boolean isDate = false;
        Calendar calendar = Calendar.getInstance();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        try {
            if (Integer.parseInt(delimBrithDay[1]) > 0 && Integer.parseInt(delimBrithDay[1]) <= 12 &&
                    Integer.parseInt(delimBrithDay[2]) > 1920 &&
                    (year - Integer.parseInt(delimBrithDay[2]) > 5)) {
                switch (Integer.parseInt(delimBrithDay[1])) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        if (Integer.parseInt(delimBrithDay[0]) < 0 && Integer.parseInt(delimBrithDay[0]) >= 31)
                            break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if (Integer.parseInt(delimBrithDay[0]) < 0 && Integer.parseInt(delimBrithDay[0]) >= 30)
                            break;
                    case 2:
                        if (Integer.parseInt(delimBrithDay[0]) < 0 && Integer.parseInt(delimBrithDay[0]) >= 29)
                            break;
                    default:
                        isDate = true;
                        break;
                }
            }
        } catch (ClassCastException e) {
            sendMessage("incorrect input of Date");
        }
        return isDate;
    }

    public boolean checkEmail (String email){
         boolean checkEmail=false;
        String emailPattern = "^\\w+([.\\w]+)*\\w@\\w((.\\w)*\\w+)*\\.\\D{2,3}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(emailRegistration);
        if (matcher.matches()) checkEmail=true;
            return checkEmail;
        //          та же проверка только ручками
//        int sobaka = emailRegistration.indexOf('@');
//        int dot = emailRegistration.lastIndexOf('.');
//        Pattern pattern = Pattern.compile("\\D*");
//        Matcher matcher = pattern.matcher(emailRegistration.substring(dot+1, emailRegistration.length()-1));
//        if (sobaka != emailRegistration.lastIndexOf('@') || (dot - sobaka <= 2) || emailRegistration.length()
//                - dot < 3 || emailRegistration.length() - dot > 4 || !matcher.matches() ) {
    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}
