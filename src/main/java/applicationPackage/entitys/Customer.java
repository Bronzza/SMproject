package applicationPackage.entitys;

import applicationPackage.Procedure;


import javax.persistence.Entity;
import java.sql.Date;
import java.util.List;

//@Entity
public class Customer {
    private String login;
    private String password;

    private String name;
    private String surName;
    private String telNumber;
    private String email;
    private int deposit;
    private int discount;
    private List<Procedure> depositProcedure;

    // info
    private Date birthday;
    private boolean isMan; //мужчина тру

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public List<Procedure> getDepositProcedure() {
        return depositProcedure;
    }

    public void setDepositProcedure(List<Procedure> depositProcedure) {
        this.depositProcedure = depositProcedure;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
