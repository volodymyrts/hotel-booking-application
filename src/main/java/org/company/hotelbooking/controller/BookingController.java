package org.company.hotelbooking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.company.hotelbooking.dto.BookingDto;
import org.company.hotelbooking.entity.Booking;
import org.company.hotelbooking.entity.Option;
import org.company.hotelbooking.entity.Room;
import org.company.hotelbooking.entity.User;
import org.company.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Volodymyr
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @RequestMapping(value = "/user{id}", method = RequestMethod.GET)
    public List<Booking> getBookingsByUserId(@PathVariable("id") int id) {
        return bookingService.getBookingsByUserId(id);
    }

    @RequestMapping(value = "/price/{id}", method = RequestMethod.GET)
    public Double getPriceByBookingNumber(@PathVariable("id") int id) {
        return bookingService.getTotalPriceByBookingId(id);
    }

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createBooking(@RequestBody BookingDto bookingDto) throws ParseException {

        User user = new User();
        user.setId(bookingDto.getUser().getId());

        Room room = new Room();
        room.setId(bookingDto.getRoom().getId());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setDateIn(booking.getDateIn());
        booking.setDateOut(booking.getDateOut());

        if ( bookingDto.getOptions().size() != 0 ) {
            List<Option> options = new ArrayList<>();
            options.addAll(bookingDto.getOptions());
            booking.setOptions(options);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date dateIn = dateFormat.parse(bookingDto.getDateIn());
        booking.setDateIn(dateIn);

        Date dateOut = dateFormat.parse(bookingDto.getDateOut());
        booking.setDateOut(dateOut);

        bookingService.createBooking(booking);
    }

}
