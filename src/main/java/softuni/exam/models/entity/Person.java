package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Enums.StatusType;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "people")
public class Person extends BaseEntity{


    @Column(name="first_name",unique = true)
    private String firstName;


    @Column(name="last_name")
    private String lastName;

    @Column(unique = true)
    @Email
    private String email;

    @Column(unique = true)
    private String phone;


    @Enumerated(EnumType.STRING)
    @Column(name="status_type")
    private StatusType statusType;

    @ManyToOne
    private Country country;





//    •	id – accepts integer values, a primary identification field, an auto incremented field.
//•	first name - accepts char sequence (between 2 to 30 inclusive). The values are unique in the database.
//•	last name - accepts char sequence (between 2 to 30 inclusive).
//            •	email – an email (must contains ‘@’ and ‘.’ – dot). The email of a person is unique.
//•	phone – accepts char sequence (between 2 to 13 inclusive). Can be nullable. The phone of a person is unique.
//•	status type – the employment status of the person. Ordinal enumeration, one of the following – unemployed, employed, freelancer
//•	Constraint: The people table has a relation with the countries table.

}
