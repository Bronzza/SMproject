package applicationPackage.entitys;

import applicationPackage.Enums.AccessRights;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    @Column(unique = true, nullable = false, length = 20)
    private String login;
    private String password;
    private AccessRights accessRights;
    // info
    private String name;
    private String surName;
    private String telNub;
    private boolean isMan;
    private Date birthday; // возможно переделать Calendar
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
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, password);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
