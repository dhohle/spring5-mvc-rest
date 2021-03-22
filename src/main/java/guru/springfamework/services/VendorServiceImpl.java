package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendorMapper::vendorToVendorDTO)
                .map(u -> {
                    u.setVendorUrl(getVendorUrl(id));
                    return u;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return this.vendorRepository.findAll().stream().map(vendor -> {
            final VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
            return vendorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        return saveAndReturn(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public void deleteVendorById(Long id) {
        this.vendorRepository.deleteById(id);
    }


    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null)
                        vendor.setName(vendorDTO.getName());
                    return saveAndReturn(vendor);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendorToSave = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendorToSave.setId(id);
        return saveAndReturn(vendorToSave);
    }

    private VendorDTO saveAndReturn(final Vendor vendor){
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);

        returnDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return returnDto;
    }

    private static String getVendorUrl(final Long id) {
        return "/api/v1/vendors/" + id;
    }
}
