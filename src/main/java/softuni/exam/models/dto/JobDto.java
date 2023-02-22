package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Company;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDto {

    @Size(min=2,max=40)
    @XmlElement(name="jobTitle")
    private String title;

    @DecimalMin(value = "300")
    @XmlElement()
    private BigDecimal salary;

    @Min(value=10)
    @XmlElement(name="hoursAWeek")
    private Integer hoursAWeek;

    @Size(min=5)
    @XmlElement()
    private String description;

    @XmlElement(name="companyId")
    private Long company;



}
