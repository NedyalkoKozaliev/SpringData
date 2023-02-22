package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country extends BaseEntity{

    @Column(unique = true)
    private String name;


    @Column(unique = true)
    private String code;

    @Column()
    private String currency;



//    •	id – accepts integer values, a primary identification field, an auto incremented field.
//•	name – accepts char sequence (between 2 to 30 inclusive). The values are unique in the database.
//•	code - accepts char sequence (between 2 to 19 inclusive). The values are unique in the database.
//•	currency – accepts char sequence (between 2 to 19 inclusive).

}
