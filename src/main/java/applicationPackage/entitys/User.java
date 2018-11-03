package applicationPackage.entitys;

import applicationPackage.Enums.AccessRights;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    @Column(unique = true, nullable = false, length = 20)

    private String password;
    @Enumerated (EnumType.STRING)
    private AccessRights accessRights;
    // info
    private String name;
    private String surName;
    private String telNub;
    private boolean isMan;
    private String birthday;
    private String eMail;




    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }

    public AccessRights getAr() {
        return ar;
    }

    public void setAr(AccessRights ar) {
        this.ar = ar;
    }

    private AccessRights ar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(eMail, user.eMail) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(eMail, password);
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

    public String getTelNub() {
        return telNub;
    }

    public void setTelNub(String telNub) {
        this.telNub = telNub;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
