package applicationPackage.bins;

import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.VisitRepository;
import applicationPackage.entitys.Customer;
import applicationPackage.entitys.Visit;
import org.primefaces.event.CellEditEvent;
import org.springframework.data.domain.Example;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
public class ClientsPage implements Serializable {

    @Inject
    CustomerRepository customerRepository;
    @Inject
    VisitRepository visitRepository;

    private final static List<String> VALID_COLUMN_KEYS =
            Arrays.asList("sur_name", "name", "tel_number", "email", "deposit", "discount", "login", "password");

    private List<ColumnModel> columns;

    private List<String> columKeys = new ArrayList<>();

    private List<Customer> customers;

    private List<Customer> filteredCustomers;

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public List<String> getColumKeys() {
        return columKeys;
    }

    public void setColumKeys(List<String> columKeys) {
        this.columKeys = columKeys;
    }

    public List<Customer> getFileterdCustomers() {
        return customers;
    }

    public void setFileterdCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getFilteredCars() {
        return filteredCustomers;
    }

    public void setFilteredCars(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            try {
                String value = (String) oldValue;
                Customer example = new Customer();
                example.setSurName(value);
                Optional<Customer> optionalCustomer = customerRepository.findOne(Example.of(example));
                if (optionalCustomer.isPresent()) {
                    Customer updatedCustomer = optionalCustomer.get();
                    updatedCustomer.setSurName((String) newValue);
                    customerRepository.save(updatedCustomer);
                } else {
                    example = new Customer();
                    example.setName(value);
                    optionalCustomer = customerRepository.findOne(Example.of(example));
                    if (optionalCustomer.isPresent()) {
                        Customer updatedCustomer = optionalCustomer.get();
                        updatedCustomer.setName((String) newValue);
                        customerRepository.save(updatedCustomer);
                    } else {
                        example = new Customer();
                        example.setEmail(value);
                        optionalCustomer = customerRepository.findOne(Example.of(example));
                        if (optionalCustomer.isPresent()) {
                            Customer updatedCustomer = optionalCustomer.get();
                            updatedCustomer.setEmail((String) newValue);
                            customerRepository.save(updatedCustomer);
                        } else {
                            example = new Customer();
                            example.setTelNumber(value);
                            optionalCustomer = customerRepository.findOne(Example.of(example));
                            if (optionalCustomer.isPresent()) {
                                Customer updatedCustomer = optionalCustomer.get();
                                updatedCustomer.setTelNumber((String) newValue);
                                customerRepository.save(updatedCustomer);
                            }
                        }
                    }
                }
            } catch (ClassCastException exc) {

            }
            try {
                Date value = (Date) oldValue;
                Customer example = new Customer();
                example.setBirthday(value);
                Optional<Customer> optionalCustomer = customerRepository.findOne(Example.of(example));
                if (optionalCustomer.isPresent()) {
                    Customer updatedCustomer = optionalCustomer.get();
                    updatedCustomer.setBirthday((Date) newValue);
                    customerRepository.save(updatedCustomer);
                }
            } catch (ClassCastException exc) {

            }


            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public VisitRepository getVisitRepository() {
        return visitRepository;
    }

    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public List<Visit> findAllVisit() {
        return visitRepository.findAll();
    }

    private void createDynamicColumns() {
//        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<ColumnModel>();

        for (String columnKey : columKeys) {
            String key = columnKey.trim();

            if (VALID_COLUMN_KEYS.contains(key)) {
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
            }
        }
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
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
