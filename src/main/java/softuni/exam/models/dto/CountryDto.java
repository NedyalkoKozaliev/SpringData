package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CountryDto {

   @Size(min=2,max=30)
    private String name;


    @Size(min=2,max=19)
    private String countryCode;

    @Size(min=2,max=19)
    private String currency;


}
