package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of Customers.", notes = "These are some notes on the API")
    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(this.customerService.getAllCustomers());
    }

    @GetMapping({"/firstname/{firstname}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomersByFirstname(@PathVariable final String firstname) {
        return this.customerService.getCustomerByFirstname(firstname);
    }

    @GetMapping({"/lastname/{lastname}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomersByLastname(@PathVariable final String lastname) {
        return this.customerService.getCustomerByLastname(lastname);
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomersById(@PathVariable final String id) {
        return this.customerService.getCustomerById(Long.valueOf(id));
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return this.customerService.createNewCustomer(customerDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable final Long id, @RequestBody final CustomerDTO customerDTO) {
        return this.customerService.saveCustomerByDTO(id, customerDTO);
    }
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable final Long id, @RequestBody final CustomerDTO customerDTO) {
        return this.customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable final Long id){
        this.customerService.deleteCustomerById(id);
//        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
