package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private static final Long ID = 1L;
    private static final String firstname = "Harry";
    private static final String lastname = "De Korte";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<CustomerDTO> customerDTOS = this.customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());

    }

    @Test
    public void getCustomerById() {
        //given
        Customer customer = new Customer(ID, firstname, lastname);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        //when
        CustomerDTO customerDTO = this.customerService.getCustomerById(ID);

        //then
        assertEquals(ID, customerDTO.getId());
    }

    @Test
    public void getCustomerByFirstname() {
        // given
        Customer customer = new Customer(ID, firstname, lastname);

        when(customerRepository.findByFirstname(anyString())).thenReturn(Optional.of(customer));
        //when
        CustomerDTO customerDTO = this.customerService.getCustomerByFirstname(firstname);

        //then
        assertEquals(firstname, customerDTO.getFirstname());
    }

    @Test
    public void getCustomerByLastname() {
        //given
        Customer customer = new Customer(ID, firstname, lastname);

        when(customerRepository.findByLastname(anyString())).thenReturn(Optional.of(customer));
        //when
        CustomerDTO customerDTO = this.customerService.getCustomerByLastname(lastname);

        //then
        assertEquals(lastname, customerDTO.getLastname());

    }
    @Test
    public void createNewCustomer() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");
        customerDTO.setFirstname("Hendrix");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO saveDto = this.customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), saveDto.getFirstname());
        assertEquals("/api/v1/customer/1",  saveDto.getCustomerUrl());

    }
}