package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Enums.StatusType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PersonDto {


    @Size(min=2,max=30)
    private String firstName;


    @Size(min=2,max=30)
    private String lastName;


    @Email
    private String email;

    @Size(min=2,max=13)
    private String phone;



    private String statusType;


    private Long country;


}
