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
    private Visit localVisit = new Visit();
    private Customer localCustomer = new Customer();
    private String selectedSpecialistId;
    private String selectedProcedureName;

    public MyEvent(String name) {
        this.name = name;
        localCustomer = new Customer();
        localVisit = new Visit();
    }

    public void setLocalVisit(Visit localVisit) {
        this.localVisit = localVisit;
    }

    public void setLocalCustomer(Customer localCustomer) {
        this.localCustomer = localCustomer;
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

    public String getSelectedSpecialistId() {
        return selectedSpecialistId;
    }

    public void setSelectedSpecialistId(String selectedSpecialistId) {
        this.selectedSpecialistId = selectedSpecialistId;
    }

    public String getSelectedProcedureName() {
        return selectedProcedureName;
    }

    public void setSelectedProcedureName(String selectedProcedureName) {
        this.selectedProcedureName = selectedProcedureName;
    }
}
