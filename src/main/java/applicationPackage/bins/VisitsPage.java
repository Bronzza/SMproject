package applicationPackage.bins;

import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.VisitRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Visit;
import org.primefaces.event.CellEditEvent;
import org.springframework.data.domain.Example;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ViewScoped
@Named
public class VisitsPage implements Serializable {

    @Inject
    VisitRepository visitRepository;

    @Inject
    CustomerRepository customerRepository;

    public VisitRepository getVisitRepository() {
        return visitRepository;
    }

    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("customer", "start", "procedure", "fanalPrice", "localSpecalist");


    List<String> columnKeys = new ArrayList<>();

    private List<ColumnModel> columns;

    private List<Visit> visitList;

    private List<Visit> filteredVisits = new ArrayList<>();


    @PostConstruct
    public void init() {
        List <Visit> allVisits = visitRepository.findAll();
        visitList = new ArrayList<>();
        for (Visit visit: allVisits) {
            if(visit.getPayed()) visitList.add(visit);
        }
        columnKeys.add("customer");
        columnKeys.add("start");
        columnKeys.add("procedure");
        columnKeys.add("localSpecalist");
        columnKeys.add("fanalPrice");
        createDynamicColumns();
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }


    public List<String> getColumnKeys() {
        return columnKeys;
    }

    public List<Visit> getFilteredVisits() {
        return filteredVisits;
    }

    public void setFilteredVisits(List<Visit> filteredVisits) {
        this.filteredVisits = filteredVisits;
    }

    public void setColumnKeys(List<String> columnKeys) {
        this.columnKeys = columnKeys;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    private void createDynamicColumns() {
        columns = new ArrayList<ColumnModel>();

        for (String columnKey : columnKeys) {
            if (VALID_COLUMN_KEYS.contains(columnKey)) {
                switch (columnKey) {
                    case ("fanalPrice"):
                        String correctHeader = "Final Price";
                        columns.add(new ColumnModel(correctHeader, columnKey));

                        break;
                    case ("start"):
                        correctHeader = "Start time";
                        columns.add(new ColumnModel(correctHeader, columnKey));
                        break;
                    case ("customer"):
                        correctHeader = "Customer's Surname";
                        columns.add(new ColumnModel(correctHeader, columnKey));

                        Customer exampleCustomer = new Customer();
                        exampleCustomer.setSurName(columnKey);

                        break;
                    case ("procedure"):
                        correctHeader = "Procedure";
                        columns.add(new ColumnModel(correctHeader, columnKey));
                        break;
                    case ("localSpecalist"):
                            correctHeader = "Specialist";
                        columns.add(new ColumnModel(correctHeader, columnKey));
                        break;
                    default:
                        columns.add(new ColumnModel(columnKey, columnKey));
                }
            }
        }
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form1:visits");
        table.setValueExpression("sortBy", null);

        //update columns
        createDynamicColumns();
    }

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);


        }
    }

}
