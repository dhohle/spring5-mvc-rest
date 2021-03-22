package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private final static String FIRSTNAME = "Harry";
    private final static String LASTNAME = "Bores";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        List<CustomerDTO> customerDTOS = Arrays.asList(
                new CustomerDTO(1l, FIRSTNAME, LASTNAME),
                new CustomerDTO(2L, "another first", "another last"));

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        //when
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomersById() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO(2L, FIRSTNAME, LASTNAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        //when
        mockMvc.perform(get("/api/v1/customers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(2)));
    }

    @Test
    public void getCustomersByFirstname() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO(2L, FIRSTNAME, LASTNAME);

        when(customerService.getCustomerByFirstname(anyString())).thenReturn(customerDTO);

        //when
        mockMvc.perform(get("/api/v1/customers/firstname/" + FIRSTNAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
    }


    @Test
    public void testGetCustomersByLastname() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO(2L, FIRSTNAME, LASTNAME);

        when(customerService.getCustomerByLastname(anyString())).thenReturn(customerDTO);

        //when
        mockMvc.perform(get("/api/v1/customers/lastname/" + LASTNAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");
        customer.setLastname("Flintstone");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customer.getFirstname());
        returnDto.setLastname(customer.getLastname());
        returnDto.setCustomerUrl("/api/v1/customer/1");

        when(this.customerService.createNewCustomer(customer)).thenReturn(returnDto);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customer/1")));
    }

    @Test
    public void testUpdateCustomer() throws  Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customerDTO.getFirstname());
        returnDto.setLastname(customerDTO.getLastname());
        returnDto.setCustomerUrl("/api/v1/customers/1");

        when(this.customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);

        //when/then
        mockMvc.perform(put("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));

    }


}