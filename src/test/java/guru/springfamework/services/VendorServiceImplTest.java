package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    public static final Long ID = 2L;
    public static final String NAME = "Vendor";

    public static final String NAME_1 = "My Vendor";
    public static final long ID_1 = 1L;
    public static final String NAME_2 = "My Vendor";
    public static final long ID_2 = 1L;


    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.vendorService =new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getVendorById() {
        //given
        final Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        given(this.vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        final VendorDTO returnVendor = this.vendorService.getVendorById(ID);

        //then
        assertEquals(ID, returnVendor.getId());
        assertEquals(NAME, returnVendor.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception{
        //given
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
    }
    @Test
    public void getAllVendors() {
        //given
        final List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        given(this.vendorRepository.findAll()).willReturn(vendors);

        //when
        final List<VendorDTO> returnVendorDTO = this.vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(returnVendorDTO.size(), CoreMatchers.is(equalTo(3)));
    }

    @Test
    public void createVendor() {
        //given
        final VendorDTO vendorDto = new VendorDTO();
        vendorDto.setName(NAME);

        final Vendor vendor = new Vendor();
        vendor.setId(1L);

        given(this.vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        final VendorDTO returnVendor = this.vendorService.createVendor(vendorDto);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(returnVendor.getVendorUrl(), containsString("1"));
    }

    @Test
    public void deleteById() {
        this.vendorService.deleteVendorById(1L);

        verify(this.vendorRepository, times(1)).deleteById(anyLong());
    }
    @Test
    public void saveVendorByDTO() throws Exception {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.updateVendor(ID_1, vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    public void patchVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when

        VendorDTO savedVendorDTO = vendorService.patchVendor(ID_1, vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return vendor;
    }
}