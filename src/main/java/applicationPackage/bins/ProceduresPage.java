package applicationPackage.bins;

import applicationPackage.Enums.Proffession;
import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.Repositories.SpecialistRepository;
import applicationPackage.entitys.Procedure;
import applicationPackage.entitys.Specialist;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Example;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
public class ProceduresPage implements Serializable{

    @Inject
    ProcedureRepository procedureRepository;
    @Inject
    SpecialistRepository specialistRepository;

    private String name;
    private Integer durationMin;
    private Integer cost;

    public Integer getSpecialistRequired() {
        return specialistRequired;
    }

    public void setSpecialistRequired(Integer specialistRequired) {
        this.specialistRequired = specialistRequired;
    }

    private Integer specialistRequired;


    private String nameSpecialist;
    private Proffession proffession;


    public ProcedureRepository getProcedureRepository() {
        return procedureRepository;
    }

    public void setProcedureRepository(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpecialist() {
        return nameSpecialist;
    }

    public void setNameSpecialist(String nameSpecialist) {
        this.nameSpecialist = nameSpecialist;
    }

    public Integer getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(Integer durationMin) {
        this.durationMin = durationMin;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public SpecialistRepository getSpecialistRepository() {
        return specialistRepository;
    }

    public void setSpecialistRepository(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    public Proffession getProffession() {
        return proffession;
    }

    public void setProffession(Proffession proffession) {
        this.proffession = proffession;
    }

    public void saveProcedure (){
        Procedure procedure = new Procedure();
        procedure.setName(name);
        procedure.setDurationMin(durationMin);
        procedure.setCost(cost);
        name=null;
        durationMin = null;
        cost=null;

        try{
            procedureRepository.save(procedure);
            sendMessage("Procedure saved");
        }
        catch (Exception e){
            sendMessage("Incorrect data");
            return;
        }
    }

    public void saveSpecialist (){
        Specialist specialist = new Specialist();
        specialist.setName(nameSpecialist);
        specialist.setProffession(proffession);
        nameSpecialist=null;
        proffession=null;
        try{
            specialistRepository.save(specialist);
            sendMessage("Procedure saved");
        }
        catch (Exception e){
            sendMessage("Incorrect data");
            return;
        }
    }

    public List<SelectItem> selectProffesion() {

        List<SelectItem> list = new ArrayList<>();
        for (Proffession proffession : Proffession.values()) {
            list.add(new SelectItem(proffession, proffession.name()));
        }
        return list;
    }

    public void sendMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message, null));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }
    public String goToMainPage(){
        sendMessage("Main page");
        return "goToMain";
    }
}
