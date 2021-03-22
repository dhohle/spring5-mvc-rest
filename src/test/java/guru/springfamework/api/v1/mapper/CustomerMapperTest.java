package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO () throws Exception{
        // given
        final Customer customer = new Customer(1L, "firstname", "lastname");

        //when
        final CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // then
        assertEquals(Long.valueOf(1L), customerDTO.getId());
        assertEquals("firstname", customerDTO.getFirstname());
        assertEquals("lastname", customerDTO.getLastname());
    }
}
