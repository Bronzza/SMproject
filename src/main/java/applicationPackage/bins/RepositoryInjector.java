package applicationPackage.bins;

import applicationPackage.Repositories.CustomerRepository;
import applicationPackage.Repositories.VisitRepository;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;


@ManagedBean(name="inject", eager = true)
@ApplicationScoped
public class RepositoryInjector {

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private VisitRepository visitRepository;

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
}
