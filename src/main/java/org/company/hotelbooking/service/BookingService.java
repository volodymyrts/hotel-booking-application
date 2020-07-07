package org.company.hotelbooking.service;

import java.util.List;
import org.company.hotelbooking.entity.Booking;
import org.company.hotelbooking.entity.Option;
import org.company.hotelbooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Volodymyr
 */
@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUserId(int id) {
        return bookingRepository.findAllByUserId(id);
    }

    /**
     * Method sums price for room and additional options and multiplies for number of days.
     * @param bookingId booking id
     * @return total price for booking
     */
    public Double getTotalPriceByBookingId(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId);
        Double optionsPrice = booking.getOptions().stream().mapToDouble(Option::getOptionPrice).sum();
        Long countDays = (booking.getDateOut().getTime() - booking.getDateIn().getTime()) / (1000*60*60*24);
        return (booking.getRoom().getRoomPrice() + optionsPrice) * countDays;
    }

    public void createBooking(Booking booking) {
        // checking if room available for booking for specified days
        if (roomService.getAvailableRooms(booking.getDateIn(), booking.getDateOut()).stream()
                .filter(e -> e.getId() == booking.getRoom().getId())
                .findAny().isPresent())
        {
            bookingRepository.save(booking);
        }

    }

}
