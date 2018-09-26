package applicationPackage.Repositories;

import applicationPackage.entitys.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
