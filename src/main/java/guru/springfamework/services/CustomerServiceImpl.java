package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return this.customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                            final CustomerDTO customerDTO = this.customerMapper.customerToCustomerDTO(customer);
                            customerDTO.setCustomerUrl("/api/v1/customer/" + customerDTO.getId());
                            return customerDTO;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return this.customerRepository.findById(id)
                .map(this.customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO getCustomerByFirstname(String firstname) {
        return this.customerRepository.findByFirstname(firstname)
                .map(this.customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO getCustomerByLastname(String lastname) {
        return this.customerRepository.findByLastname(lastname)
                .map(this.customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    public CustomerDTO saveAndReturnDTO(final Customer customer){
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDto.setCustomerUrl("/api/v1/customer/"+savedCustomer.getId());

        return returnDto;
    }
}
