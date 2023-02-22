package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company extends BaseEntity{


    @Column(unique = true)
    private String name;

    @Column()
    private String website;


    @Column(name="date_established")
    private LocalDate dateEstablished;

    @ManyToOne
    private Country country;

    @ManyToMany
    private Set<Job> jobs;





//    •	id – accepts integer values, a primary identification field, an auto incremented field.
//•	name – accepts char sequence (between 2 to 40 inclusive). The values are unique in the database.
//•	website – accepts char sequence (between 2 to 30 inclusive).
//            •	date established – a date in the "yyyy-MM-dd" format.
//•	jobs – list with all jobs that the company is offering.
//•	Constraint: The companies table has a relation with jobs table
//•	Constraint: The companies table has a relation with countries table

}
