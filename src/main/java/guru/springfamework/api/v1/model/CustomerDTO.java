package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstname;
    private String lastname;
    @JsonProperty("customer_url")
    private String customerUrl;

    public CustomerDTO(final Long id, final String firstname, final String lastname){
        this.id = id;
        this.firstname = firstname;
        this.lastname =lastname;
    }

}
