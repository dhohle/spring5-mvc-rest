package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VendorDTO {
    private Long id;
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;

    public VendorDTO(final Long id, final String name){
        this.id = id;
        this.name = name;
    }
}
