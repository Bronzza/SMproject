package applicationPackage.bins;

import applicationPackage.Repositories.ProcedureRepository;
import applicationPackage.entitys.Procedure;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Example;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
public class ProceduresPage implements Serializable{

    @Inject
    ProcedureRepository procedureRepository;

    private String name;
    private int durationMin;
    private int cost;


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

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void saveData (){
        Procedure procedure = new Procedure();
        procedure.setName(name);
        procedure.setDurationMin(durationMin);
        procedure.setCost(cost);

        try{
            procedureRepository.save(procedure);
            sendMessage("Procedure saved");
        }
        catch (Exception e){
            sendMessage("Incorrect data");
            return;
        }
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
