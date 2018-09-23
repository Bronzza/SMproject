package applicationPackage.entitys;

import applicationPackage.Procedure;


import javax.persistence.Entity;
import java.sql.Date;
import java.util.List;

@Entity
public class Customer {
    private String name;
    private String surName;
    private String telNumber;
    private int deposit;
    private int discount;
    private List<Procedure> depositProcedure;

    // info
    private Date birthday;
    private boolean isMan; //мужчина тру

}
