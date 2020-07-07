package org.company.hotelbooking.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * An entity class which contains the information of a single room.
 * @author Volodymyr
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;

    @Column(name = "room_number")
    @NotBlank
    private int roomNumber;

    @Column(name = "room_category")
    @NotBlank
    private int roomCategory;

    @Column(name = "room_price")
    @NotBlank
    private Double roomPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private transient List<Booking> booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                roomNumber == room.roomNumber &&
                roomCategory == room.roomCategory &&
                Objects.equals(roomPrice, room.roomPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNumber, roomCategory, roomPrice);
    }
}
