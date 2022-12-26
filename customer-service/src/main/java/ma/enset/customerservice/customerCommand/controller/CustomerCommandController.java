package ma.enset.customerservice.customerCommand.controller;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.commands.CreateCustomerCommand;
import ma.enset.commonapi.commands.UpdateCustomerCommand;
import ma.enset.commonapi.dtos.CreateCustomerRequestDTO;
import ma.enset.commonapi.dtos.UpdateCustomerRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/Customer/commands")
@AllArgsConstructor
public class CustomerCommandController {
    private final CommandGateway commandGateway ;
    private final EventStore eventStore ;

    @PostMapping("/createCustomer")
    public CompletableFuture<String > createCustomer(@RequestBody CreateCustomerRequestDTO createCustomerRequestDTO){
        return commandGateway.send(
                new CreateCustomerCommand(
                        UUID.randomUUID().toString(),
                        createCustomerRequestDTO.getNom(),
                        createCustomerRequestDTO.getAdresse(),
                        createCustomerRequestDTO.getEmail(),
                        createCustomerRequestDTO.getTelephone()
                )
        );
    }


    @PutMapping("/updateCustomer")
    public CompletableFuture<String > updateCustomer(@RequestBody UpdateCustomerRequestDTO requestDTO){
        return commandGateway.send(
                new UpdateCustomerCommand(
                        requestDTO.getId(),
                        requestDTO.getNom(),
                        requestDTO.getAdresse(),
                        requestDTO.getEmail(),
                        requestDTO.getTelephone()
                )
        );
    }

    @GetMapping("/eventStore/{customerId}")
    public Stream getEventsForAccount(@PathVariable(value = "customerId") String customerId) {
        return eventStore.readEvents(customerId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        ResponseEntity<String> entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

}

