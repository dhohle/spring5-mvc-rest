package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    VendorDTO getVendorById(Long id);

    List<VendorDTO> getAllVendors();

    VendorDTO createVendor(VendorDTO vendorDTO);

    void deleteVendorById(Long id);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
}
