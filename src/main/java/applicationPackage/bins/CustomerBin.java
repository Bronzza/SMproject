package applicationPackage.bins;

import applicationPackage.entitys.Customer;

import javax.inject.Named;

@Named
public class CustomerBin {
    private Customer customer = new Customer();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
