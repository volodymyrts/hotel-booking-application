package org.company.hotelbooking.dto;

import java.util.List;
import lombok.*;
import org.company.hotelbooking.entity.Option;
import org.company.hotelbooking.entity.Room;
import org.company.hotelbooking.entity.User;

/**
 * Data transfer object class for class Booking.
 * @author Volodymyr
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private User user;
    private Room room;
    private String dateIn;
    private String dateOut;
    private List<Option> options;

}
