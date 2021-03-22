package guru.springfamework.controller.v1;


import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public final static String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "List all Vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors(){
        return new VendorListDTO(this.vendorService.getAllVendors());
    }

    @ApiOperation(value = "Create new Vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody final VendorDTO vendorDTO) {
        return this.vendorService.createVendor(vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendorById(@PathVariable final Long id) {
        vendorService.deleteVendorById(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable final Long id) {
        return this.vendorService.getVendorById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendorById(@PathVariable final Long id, @RequestBody final VendorDTO vendorDTO) {
        return vendorService.patchVendor(id, vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO putVendorById(@PathVariable final Long id, @RequestBody final VendorDTO vendorDTO) {
        return vendorService.updateVendor(id, vendorDTO);
    }


}
