package applicationPackage.bins;

import applicationPackage.MyEvent;
import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.Repositories.SpecialistRepository;
import applicationPackage.Repositories.VisitRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Procedure;
import applicationPackage.entitys.Specialist;
import applicationPackage.entitys.Visit;
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

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named

public class MainPage implements Serializable {
    private ScheduleModel eventModel;
    private MyEvent event;

    @Inject
    VisitRepository visitRepository;

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
    private Customer newCustomer = new Customer();

    public void setLocalProcedure(Procedure localProcedure) {
        this.localProcedure = localProcedure;
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
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
            event.getLocalVisit().setProcedure(localProcedure);
            event.getLocalVisit().setFanalPrice(localProcedure.getCost()
                    - (localProcedure.getCost() * event.getLocalCustomer().getDiscount() / 100));
        }
//        finalPriceVisit = localProcedure.getCost() - localProcedure.getCost() / 100 * event.getLocalCustomer().getDiscount();
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

    public VisitRepository getVisitRepository() {
        return visitRepository;
    }

    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
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
        for (Visit visit : visitRepository.findAll()) {
            event = new MyEvent();
            event.setTitle(visit.getProcedure().getName());
            event.setStartDate(visit.getStart());
            event.setEndDate(visit.getStart());

            event.getEndDate().setTime(visit.getStart().getTime() +
                    visit.getProcedure().getDurationMin() * 60000);
            event.setLocalVisit(visit);
            event.setLocalCustomer(visit.getCustomer());
            event.getLocalVisit().setFanalPrice(visit.getProcedure().getCost()
                    - (visit.getProcedure().getCost() * event.getLocalCustomer().getDiscount() / 100));
            eventModel.addEvent(event);
        }

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
        event.getLocalVisit().setProcedure(localProcedure);
        event.getLocalVisit().setFanalPrice(finalPriceVisit);
        event.getLocalVisit().setStart(event.getStartDate());
        event.getLocalVisit().getLocalSpecalist().add(findSpectById(selectedSpecialistId));
        event.getLocalVisit().setCustomer(event.getLocalCustomer());
        visitRepository.save(event.getLocalVisit());
        updateCustomerInBase();
//        event.getLocalCustomer().getListVisit().add;
    }
//
//    public void confirmEvent(ActionEvent actionEvent){
//        visitRepository.save(event.getLocalVisit());
//    }

    public void updateCustomerInBase() {
        Customer example = new Customer();
        example.setSurName(event.getLocalCustomer().getSurName());
        example.setSurName(event.getLocalCustomer().getTelNumber());
        Optional<Customer> existing = customerRepository.findOne(Example.of(example));
        if (existing.isPresent()) {
            Customer temp = existing.get();
            temp.getListVisit().add(event.getLocalVisit());
            customerRepository.save(temp);
        }
    }

    public Specialist findSpectById(String s) {
        Specialist example = new Specialist();
        example.setId(Long.parseLong(s));
        Optional<Specialist> existing = specialistRepository.findOne(Example.of(example));
        if (existing.isPresent()) {
            return existing.get();
        } else return null;
    }


    public Long fingVisitId() {
        Visit example = new Visit();
        example.setStart(event.getLocalVisit().getStart());
        Optional<Visit> existing = visitRepository.findOne(Example.of(example));
        if (existing.isPresent()) {
            return existing.get().getId();
        } else return null;
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

    public void addClient() {
        Optional<Customer> existing = customerRepository.findOne(Example.of(newCustomer));
        if (existing.isPresent()) {
            sendMessage("Customer is not new, please find it in base");
        } else customerRepository.save(newCustomer);

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


    public void calculateEndDate() {
        Calendar calendar = Calendar.getInstance();
        event.getEndDate().setTime(event.getStartDate().getTime() + localProcedure.getDurationMin() * 60000);
    }

    public String buttonClients() {
        sendMessage("Going to Clients page");
        return "goToLogin";
    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
}