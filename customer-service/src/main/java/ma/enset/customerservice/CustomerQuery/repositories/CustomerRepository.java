package ma.enset.customerservice.CustomerQuery.repositories;

import ma.enset.customerservice.CustomerQuery.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
}
