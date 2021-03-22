package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest {

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO1;
    VendorDTO vendorDTO2;

    @Before
    public void setUp() throws Exception {
        vendorDTO1 = new VendorDTO(1L, "name1");
        vendorDTO2 = new VendorDTO(2L, "name2");
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        List<VendorDTO> vendorDTOS = Arrays.asList(vendorDTO1, vendorDTO2);

        when(this.vendorService.getAllVendors()).thenReturn(vendorDTOS);
        //when
        mockMvc.perform(get("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO1);

        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }


    @Test
    public void createVendor() throws Exception {
        given(vendorService.createVendor(vendorDTO1)).willReturn(vendorDTO1);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }


    @Test
    public void updateVendor() throws Exception {
        given(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO1);

        mockMvc.perform(put(VendorController.BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }

    @Test
    public void patchVendor() throws Exception {
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO1);

        mockMvc.perform(patch(VendorController.BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }

    @Test
    public void deleteVendorById() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL+"/1"))
                .andExpect(status().isOk());
        then(vendorService).should().deleteVendorById(anyLong());
    }

}