package ma.enset.customerservice.CustomerQuery.controller;


import lombok.AllArgsConstructor;
import ma.enset.commonapi.query.GetAllCustomersQuery;
import ma.enset.commonapi.query.GetCustomerById;
import ma.enset.customerservice.CustomerQuery.entities.Customer;
import ma.enset.customerservice.CustomerQuery.repositories.CustomerRepository;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Customer/queries")
@AllArgsConstructor
public class CustomerQueryController {
    private QueryGateway queryGateway;
    private CustomerRepository customerRepository ;
    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers(){
        return queryGateway.query(new GetAllCustomersQuery(),
                ResponseTypes.multipleInstancesOf(Customer.class)).join();
    }

    @QueryHandler
    public List<Customer> on(GetAllCustomersQuery query){

        return customerRepository.findAll();
    }

    @GetMapping("/getCustomer/{id}")
    public Customer getCustomer(@PathVariable String id){
        return queryGateway.query(new GetCustomerById(id),
                ResponseTypes.instanceOf(Customer.class)).join();
    }

    @QueryHandler
    public Customer on(GetCustomerById query){
        return customerRepository.findById(query
                .getId()).get();
    }

}

