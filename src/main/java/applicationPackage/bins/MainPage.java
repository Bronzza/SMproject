package applicationPackage.bins;

import applicationPackage.MyEvent;
import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.Repositories.SpecialistRepository;
import applicationPackage.Repositories.VisitRepository;
import applicationPackage.entitys.*;
import applicationPackage.utils.DateFormater;
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
    private Customer mainPageCustomer = new Customer();

    public Customer getMainPageCustomer() {
        return mainPageCustomer;
    }

    public void setMainPageCustomer(Customer mainPageCustomer) {
        this.mainPageCustomer = mainPageCustomer;
    }

    //fields for creating Visit, update CustomerBase
    private Procedure localProcedure = new Procedure();
    private Customer newCustomer = new Customer();

    //Visit
    private String selectedSpecialistId;
    private Boolean isMan = false;

    public Boolean getMan() {
        return isMan;
    }

    public void setMan(Boolean man) {
        isMan = man;
    }

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
        example.setName(event.getSelectedProcedureName());
        Optional<Procedure> existing = procedureRepository.findOne(Example.of(example));
        if (existing.isPresent()) {
            localProcedure = existing.get();
            event.getLocalVisit().setProcedure(localProcedure);
            event.getLocalVisit().setFanalPrice(localProcedure.getCost()
                    - (localProcedure.getCost() * event.getLocalCustomer().getDiscount() / 100));
        }
//        finalPriceVisit = localProcedure.getCost() - localProcedure.getCost() / 100 * event.getLocalCustomer().getDiscount();
    }

    public String getSelectedSpecialistId() {
        return selectedSpecialistId;
    }

    public void setSelectedSpecialistId(String selectedSpecialistId) {
        this.selectedSpecialistId = selectedSpecialistId;
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
//            try {
            if ((visit.getPayed() == null || !visit.getPayed()) && visit.getStart().before(yesterday())) {
                //do nothing
            } else {
                event = new MyEvent();
                event.setLocalVisit(visit);
                //сделать выбор цветов мастера отдельным методом. в добавлении мастера выбирать
                //его цвет и сетить в мейн.хштмл (пока не знаю как сетить)
                switch (event.getLocalVisit().getLocalSpecalist().getName()){
                    case "Fren":
                        event.setStyleClass("color-red");
                        break;
                    case "Dape":
                        event.setStyleClass("color-green");
                        break;
                    case "Natali":
                        event.setStyleClass("color-blue");
                        break;
                }
                event.setStartDate(visit.getStart());
                event.setEndDate(makeEndDate(event.getLocalVisit()));
                event.setLocalCustomer(visit.getCustomer());
                event.getLocalVisit().setFanalPrice(visit.getProcedure().getCost()
                        - (visit.getProcedure().getCost() * event.getLocalCustomer().getDiscount() / 100));
                String title = makeEventTitle(event);
                event.setTitle(title);
                event.setSelectedProcedureName(event.getLocalVisit().getProcedure().getName());
                event.setSelectedSpecialistId(String.valueOf(event.getLocalVisit().getLocalSpecalist().getId()));
                eventModel.addEvent(event);
            }
//            } catch (NullPointerException e) {
//                System.out.println("Exception");
//            }
        }

    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
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
            saveVisitInBase(event);
            updateCustomerInBase(event);
            isClientNew = false;
            String title = makeEventTitle(event);
            event.setTitle(title);
            eventModel.addEvent(event);
        } else {
            String title = makeEventTitle(event);
            event.setTitle(title);
            eventModel.updateEvent(event);
            saveVisitInBase(event);
            updateCustomerInBase(event);
            isClientNew = false;
        }
    }

    public String makeEventTitle(MyEvent event) {
        Visit visit = event.getLocalVisit();
        StringBuilder sb = new StringBuilder();
        if (event.getSelectedProcedureName() == null) {
            sb.append(event.getLocalVisit().getProcedure().getName());
            sb.append('\n');
        } else {
            sb.append(event.getSelectedProcedureName());
            sb.append('\n');
        }
        sb.append(event.getLocalVisit().getCustomer().toString());
        sb.append('\n');
        sb.append(DateFormater.formatDate(visit.getStart(), true));
        sb.append('\n');
        sb.append(DateFormater.formatDate(makeEndDate(event.getLocalVisit()), true));
        sb.append('\n');
        sb.append("Specialist: ");
        sb.append(visit.getLocalSpecalist().getName());
        return sb.toString();
    }


    public void updateCustomerInBase(MyEvent event) {
        Customer example = new Customer();
        example.setSurName(event.getLocalCustomer().getSurName());
        example.setTelNumber(event.getLocalCustomer().getTelNumber());
        Optional<Customer> existing = customerRepository.findOne(Example.of(example));
        if (existing.isPresent()) {
            Customer temp = existing.get();
            temp.getListVisit().add(event.getLocalVisit());
            customerRepository.save(temp);
        }
    }

    public void saveVisitInBase(MyEvent event) {
        if (isVisitExist(event.getLocalVisit())) {
            event.getLocalVisit().setCustomer(event.getLocalCustomer());
            event.getLocalVisit().setStart(event.getStartDate());
            event.getLocalVisit().setLocalSpecalist(findSpectById(event.getSelectedSpecialistId()));
            visitRepository.save(event.getLocalVisit());
        } else {
            Visit visit = event.getLocalVisit();
            visit.setProcedure(procedureRepository.findById(localProcedure.getId()).get());
            visit.setFanalPrice(event.getLocalVisit().getFanalPrice());
            visit.setStart(event.getStartDate());
            visit.setLocalSpecalist(findSpectById(event.getSelectedSpecialistId()));
            visit.setCustomer(event.getLocalCustomer());
            visit.setPayed(event.getLocalVisit().getPayed());
            visitRepository.save(visit);
        }
    }

    public boolean isVisitExist(Visit visit) {
        Visit example = visit;
        if (visit.getId() == null) {
            return false;
        } else {
            Optional<Visit> existing = visitRepository.findById(visit.getId());
            return existing.isPresent();
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


    public void onEventSelect(SelectEvent selectEvent) {
        event = (MyEvent) selectEvent.getObject();
        selectedSpecialistId = String.valueOf(event.getLocalVisit().getLocalSpecalist().getId());
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
        newCustomer.setMan(isMan);
        Optional<Customer> existing = customerRepository.findOne(Example.of(newCustomer));
        if (existing.isPresent()) {
            sendMessage("Customer is not new, please find him/her in base");
        } else {
            customerRepository.save(newCustomer);
        }
        event.setLocalCustomer(newCustomer);
        newCustomer = new Customer();
        isMan = false;
        isClientNew = false;
    }

    public List<SelectItem> selectTitleProcedure() {

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
//        selectedSpecialistId = null;
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

    public void setCustomerInfoAuto() {
        for (Customer customer : customerRepository.findAll()) {
            if (customer.getSurName().equals(mainPageCustomer.getSurName())) {
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

    public void setClientMan() {
        newCustomer.setMan(isMan);
    }

    public void calculateEndDate() {
        event.getEndDate().setTime(event.getStartDate().getTime() + localProcedure.getDurationMin() * 60000);
    }

    public Date makeEndDate(Visit visit) {
        Date resultDate = new Date();
        resultDate.setTime(visit.getStart().getTime() +
                visit.getProcedure().getDurationMin() * 60000);
        return resultDate;
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }

}