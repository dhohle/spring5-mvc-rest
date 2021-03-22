package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        final CustomerListDTO customerListDTO = new CustomerListDTO(this.customerService.getAllCustomers());
        return new ResponseEntity<>(customerListDTO, HttpStatus.OK);
    }

    @GetMapping({"/firstname/{firstname}"})
    public ResponseEntity<CustomerDTO> getCustomersByFirstname(@PathVariable final String firstname) {
        return new ResponseEntity<>(this.customerService.getCustomerByFirstname(firstname), HttpStatus.OK);
    }

    @GetMapping({"/lastname/{lastname}"})
    public ResponseEntity<CustomerDTO> getCustomersByLastname(@PathVariable final String lastname) {
        return new ResponseEntity<>(this.customerService.getCustomerByLastname(lastname), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> getCustomersById(@PathVariable final String id) {
        return new ResponseEntity<>(this.customerService.getCustomerById(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(this.customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable final Long id, @RequestBody final CustomerDTO customerDTO) {
        return new ResponseEntity<>(this.customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
    }
}
