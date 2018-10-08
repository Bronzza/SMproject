package applicationPackage.bins;

import applicationPackage.MyEvent;
import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.Repositories.SpecialistRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Procedure;
import applicationPackage.entitys.Specialist;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import org.springframework.data.domain.Example;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
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

    @Inject
    CustomerRepository customerRepository;

    @Inject
    SpecialistRepository specialistRepository;

    @Inject
    ProcedureRepository procedureRepository;

    private Boolean isClientNew;

    //fields for creating Visit, update CustomerBase
    private Procedure localProcedure = new Procedure();

    //Visit
    private Date dateVisit;
    private int finalPriceVisit;
    private List<Specialist> specialistsForVisit;

    private String selectedSpecialistId;

    //Customer
    private Customer mainPageCustomer = new Customer();

    public void setMainPageCustomer(Customer mainPageCustomer) {
        this.mainPageCustomer = mainPageCustomer;
    }

    private String nameCustomer;
    private String surNameCustomer;
    private String telNumberCustomer;
    private int discountCustomer;

    public void setLocalProcedure(Procedure localProcedure) {
        this.localProcedure = localProcedure;
    }

    public Customer getMainPageCustomer() {
        return mainPageCustomer;
    }


    public Procedure getLocalProcedure() {
        return localProcedure;
    }

    public void calculatePrise() {
        Procedure example = new Procedure();
        example.setName(event.getTitle());
        Optional<Procedure> existing = procedureRepository.findOne(Example.of(example));
        if (existing.isPresent()) {
            localProcedure = existing.get();
//            localProcedure.setCost((existing.get().getCost()));
        } else return;
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

    public String getSelectedSpecialistId() {
        return selectedSpecialistId;
    }

    public void setSelectedSpecialistId(String selectedSpecialistId) {
        this.selectedSpecialistId = selectedSpecialistId;
    }

    public List<Specialist> getSpecialistsForVisit() {
        return specialistsForVisit;
    }

    public void setSpecialistsForVisit(List<Specialist> specialistsForVisit) {
        this.specialistsForVisit = specialistsForVisit;
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

    public Boolean getClientNew() {
        return isClientNew;
    }

    public void setClientNew(Boolean clientNew) {
        isClientNew = clientNew;
    }

    public MyEvent getEvent() {
        return event;
    }

    public void setEvent(MyEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
            Procedure procedure = new Procedure();
        } else
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

    public List<SelectItem> selectTitleProcedure() {
//        SelectItemGroup g1 = new SelectItemGroup("Hair cat");
//        SelectItemGroup g2 = new SelectItemGroup("Massage");
        List<SelectItem> list = new ArrayList<>();
        for (Procedure procedure : procedureRepository.findAll()) {
            list.add(new SelectItem(procedure.getName(), procedure.getName()));

        }
//        g2.setSelectItems(list.toArray(new SelectItem[] {}));
//        g1.setSelectItems(new SelectItem[]{new SelectItem("Box", "Box"),
//                new SelectItem("WomenHair", "WomenHair")});
//        list = new ArrayList<>();
//        list.add(g1);
//        list.add(g2);

        return list;
    }

    public List<SelectItem> selectSpecialist() {
        List<SelectItem> list = new ArrayList<>();
        for (Specialist specialist : specialistRepository.findAll()) {
            list.add(new SelectItem(specialist.getId(), specialist.getName()));
        }
        return list;
    }

    public List<SelectItem> selectCustomer() {
        List<SelectItem> list = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            list.add(new SelectItem(customer.getSurName(), customer.getSurName()));
        }
        return list;
    }

    public void setCustomerInfo() {
        for (Customer customer : customerRepository.findAll()) {
            if (customer.getSurName().equals(event.getLocalCustomer().getSurName())) {
                event.setLocalCustomer(customer);
                return;
            } 
        }
    }

    public Boolean isClientNew() {
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

    public List<Customer> completeCustomer(String query) {
        List<Customer> allCustomer = customerRepository.findAll();
        List<Customer> filteredCustomer = new ArrayList<Customer>();
        for (int i = 0; i < allCustomer.size(); i++) {
            Customer temp = allCustomer.get(i);
            if (temp.getSurName().toLowerCase().startsWith(query)) {
                filteredCustomer.add(temp);
            }
        }
        return filteredCustomer;
    }

}