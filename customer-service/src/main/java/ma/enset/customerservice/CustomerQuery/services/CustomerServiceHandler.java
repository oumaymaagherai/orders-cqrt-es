package ma.enset.customerservice.CustomerQuery.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.events.CustomerCreatedEvent;
import ma.enset.commonapi.query.GetAllCustomersQuery;
import ma.enset.customerservice.CustomerQuery.entities.Customer;
import ma.enset.customerservice.CustomerQuery.repositories.CustomerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceHandler {
    private CustomerRepository customerRepository ;

    @EventHandler
    public void on(CustomerCreatedEvent event){
        Customer customer = new Customer() ;
        customer.setId(event.getId());
        customer.setAdresse(event.getAdresse());
        customer.setEmail(event.getEmail());
        customer.setTelephone(event.getTelephone());
        customer.setNom(event.getNom());
        customerRepository.save(customer) ;
    }
    @QueryHandler
    public List<Customer> on(GetAllCustomersQuery query){
        return customerRepository.findAll();
    }

}
