package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    public List<CustomerDTO> getAllCustomers();


    public CustomerDTO getCustomerById(final Long id );

    public CustomerDTO getCustomerByFirstname(final String firstname);

    public CustomerDTO getCustomerByLastname(final String lastname);

    public CustomerDTO createNewCustomer(final CustomerDTO customerDTO);

}
