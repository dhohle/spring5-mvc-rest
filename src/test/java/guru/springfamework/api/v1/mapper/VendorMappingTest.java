package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMappingTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    private final static Long ID = 2L;
    private final static String NAME = "some name";

    @Test
    public void vendorToVendorDTOTest () {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // then
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendorTest () {
        //given
        VendorDTO vendorDto = new VendorDTO();
        vendorDto.setId(ID);
        vendorDto.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDto);

        // then
        assertEquals(ID, vendor.getId());
        assertEquals(NAME, vendor.getName());
    }


}
