package org.company.hotelbooking.entity;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * An entity class which contains the information of a single booking.
 * @author Volodymyr
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;

    @Column(name = "date_in")
    private Date dateIn;

    @Column(name = "date_out")
    private Date dateOut;

    @JoinColumn(name = "room_id")
    @ManyToOne(optional = false)
    private Room room;

    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "booking_option",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
    private List<Option> options;

}
