package applicationPackage.bins;

import applicationPackage.MyEvent;
import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.Repositories.SpecialistRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Procedure;
import applicationPackage.entitys.Specialist;
import applicationPackage.entitys.User;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.springframework.data.domain.Example;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class MainPage implements Serializable {
    private ScheduleModel eventModel;
    private MyEvent event;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Inject
    CustomerRepository customerRepository;

    @Inject
    SpecialistRepository specialistRepository;

    @Inject
    ProcedureRepository procedureRepository;

    private boolean isClientNew;
//    private Customer localCustomer;

    //fields for creating Visit, update CustomerBase
    private String procedureName;
    private int procedureDurationMin;
    private int procedureCost;
    //Visit
    private Date dateVisit;
    private int finalPriceVisit;
    private List<Specialist> specialistsVisit;
    private Specialist first = new Specialist("Vasya");
    //Customer
    private String nameCustomer;
    private String surNameCustomer;
    private String telNumberCustomer;
    private int discountCustomer;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public int getProcedureDurationMin() {
        return procedureDurationMin;
    }

    public void setProcedureDurationMin(int procedureDurationMin) {
        this.procedureDurationMin = procedureDurationMin;
    }

    public int getProcedureCost() {
        return procedureCost;
    }

    public void setProcedureCost(int procedureCost) {
        this.procedureCost = procedureCost;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public int getFinalPriceVisit() {
        return finalPriceVisit;
    }

    public void setFinalPriceVisit(int finalPriceVisit) {
        this.finalPriceVisit = finalPriceVisit;
    }

    public List<Specialist> getSpecialistsVisit() {
        return specialistsVisit;
    }

    public void setSpecialistsVisit(List<Specialist> specialistsVisit) {
        this.specialistsVisit = specialistsVisit;
    }

    public Specialist getFirst() {
        return first;
    }

    public void setFirst(Specialist first) {
        this.first = first;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getSurNameCustomer() {
        return surNameCustomer;
    }

    public void setSurNameCustomer(String surNameCustomer) {
        this.surNameCustomer = surNameCustomer;
    }

    public String getTelNumberCustomer() {
        return telNumberCustomer;
    }

    public void setTelNumberCustomer(String telNumberCustomer) {
        this.telNumberCustomer = telNumberCustomer;
    }

    public int getDiscountCustomer() {
        return discountCustomer;
    }

    public void setDiscountCustomer(int discountCustomer) {
        this.discountCustomer = discountCustomer;
    }

    public ProcedureRepository getProcedureRepository() {
        return procedureRepository;
    }

    public void setProcedureRepository(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }


    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        event = new MyEvent();
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        eventModel.addEvent(new DefaultScheduleEvent("Plant the new garden stuff",
                theDayAfter3Pm(), fourDaysLater3pm()));


    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1);    //set random day of month

        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }


    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public boolean getClientNew() {
        return isClientNew;
    }

    public void setClientNew(boolean clientNew) {
        isClientNew = clientNew;
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    public MyEvent getEvent() {
        return event;
    }

    public void setEvent(MyEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (MyEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new MyEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addClient(String surname) {
        if (!isClientNew) {
            Customer example = new Customer();
            Optional<Customer> existing = customerRepository.findOne(Example.of(example));
            if (existing.isPresent()) {
                event.setLocalCustomer(existing.get());
            } else return;
        } else {

//            event.getLocalCustomer().setName(ge);
            return;
        }
    }

//    public Customer getLocalCustomer() {
//        return localCustomer;
//    }
//
//    public void setLocalCustomer(Customer localCustomer) {
//        this.localCustomer = localCustomer;
//    }

    public List<SelectItem> findProcedres() {
        List<SelectItem> list = new ArrayList<>();
        for (Procedure procedure : procedureRepository.findAll()) {
            list.add(new SelectItem(procedure, procedure.getName()));
        }
        return list;
    }

    public boolean isClientNew() {
        return isClientNew;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public SpecialistRepository getSpecialistRepository() {
        return specialistRepository;
    }

    public void setSpecialistRepository(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }
}