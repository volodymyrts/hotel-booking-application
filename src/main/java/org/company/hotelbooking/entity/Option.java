package org.company.hotelbooking.entity;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * An entity class which contains the information of a single option.
 * @author Volodymyr
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;

    @Column(name = "option_title")
    @NotBlank
    @Size(min = 3, max = 12)
    private String optionTitle;

    @Column(name = "option_price")
    @NotBlank
    private Double optionPrice;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "options")
    private transient List<Booking> booking;

}
