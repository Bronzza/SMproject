package applicationPackage.bins;

import applicationPackage.Repositories.VisitRepository;
import applicationPackage.entitys.Visit;

import javax.annotation.PostConstruct;
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
        visitList = visitRepository.findAll();
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
                        break;
                    case ("procedure"):
                        correctHeader = "Procedure";
                        columns.add(new ColumnModel(correctHeader, columnKey));
                        break;
                    default:
                        columns.add(new ColumnModel(columnKey, columnKey));
                }
//                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
//                if (columnKey.equals("fanalPrice")) {
//                    String correctHeader = "Final Price";
//                    columns.add(new ColumnModel(correctHeader, columnKey));
//                } else if (columnKey.equals("localSpecalist")) {
//
//                } else
//                columns.add(new ColumnModel(columnKey, columnKey));
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
}