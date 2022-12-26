package ma.enset.inventoryservice.InventoryCommands.controllers;


import com.example.commonapi.commands.CreateProductCommand;
import com.example.commonapi.commands.UpdateProductCommand;
import com.example.commonapi.dtos.CreateProductRequestDTO;
import com.example.commonapi.dtos.UpdateProductRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/proprietaire/commands")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createProduct(@RequestBody CreateProductRequestDTO request) {
        return commandGateway.send(new CreateProductCommand(
                UUID.randomUUID().toString(),
                request.getNom(),
                request.getPrix(),
                request.getQteStock(),
                request.getEtat()
        ));
    }

    @PutMapping("/update")
    public CompletableFuture<String> updateProduct(@RequestBody UpdateProductRequestDTO request) {
        return commandGateway.send(new UpdateProductCommand(
                request.getId(),
                request.getNom(),
                request.getPrix(),
                request.getQteStock(),
                request.getEtat()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        ResponseEntity<String> entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    @GetMapping("/eventStore/{id}")
    public Stream getEventStore(@PathVariable String id) {
        return eventStore.readEvents(id).asStream();
    }

}
