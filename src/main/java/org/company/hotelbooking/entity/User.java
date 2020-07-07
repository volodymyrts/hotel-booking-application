package org.company.hotelbooking.entity;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * An entity class which contains the information of a single user.
 * @author Volodymyr
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 3, max = 12)
    private String name;

    @Column(name = "surname")
    @NotBlank
    @Size(min = 3, max = 12)
    private String surname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private transient List<Booking> booking;

}
