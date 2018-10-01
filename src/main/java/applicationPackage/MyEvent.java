package applicationPackage;

import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.Repositories.SpecialistRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Procedure;
import applicationPackage.entitys.Specialist;
import applicationPackage.entitys.Visit;
import org.primefaces.model.DefaultScheduleEvent;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyEvent extends DefaultScheduleEvent {

    private String name;
    private Visit localVisit;
    private Customer localCustomer;

    public MyEvent(String name) {
        this.name = name;
        localCustomer = new Customer();
        localVisit = new Visit();
        localSpecialistList = new ArrayList<>();
        localProcedureList = new ArrayList<>();
    }

    private List<Specialist> localSpecialistList;
    private List<Procedure> localProcedureList;

    public void setLocalVisit(Visit localVisit) {
        this.localVisit = localVisit;
    }

    public void setLocalCustomer(Customer localCustomer) {
        this.localCustomer = localCustomer;
    }

    public void setLocalSpecialistList(List<Specialist> localSpecialistList) {
        this.localSpecialistList = localSpecialistList;
    }

    public void setLocalProcedureList(List<Procedure> localProcedureList) {
        this.localProcedureList = localProcedureList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyEvent(){}

    public MyEvent(String s, Date object, Date object1) {
        super(s, object, object1);
    }

    public Visit getLocalVisit() {
        return localVisit;
    }

    public Customer getLocalCustomer() {
        return localCustomer;
    }

    public List<Specialist> getLocalSpecialistList() {
        return localSpecialistList;
    }

    public List<Procedure> getLocalProcedureList() {
        return localProcedureList;
    }

}
