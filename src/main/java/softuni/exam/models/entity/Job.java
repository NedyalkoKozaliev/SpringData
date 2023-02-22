package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity{

    @Column()
    private String title;

    @Column()
    private BigDecimal salary;

    @Column(name="hoursaweek")
    private Integer hoursAWeek;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Company company;




//    •	id – accepts integer values, a primary identification field, an auto incremented field.
//•	title – accepts char sequence (between 2 to 40 inclusive).
//            •	salary – accepts number values that are more than or equal to 300.00.
//            •	hours a week – accepts number values that are more than or equal to 10.00.
//            •	description - a long and detailed description of needed skill for this job with a character length value higher than or equal to 5.
//            •	Constraint: The jobs table has a relation with the companies table.

}
