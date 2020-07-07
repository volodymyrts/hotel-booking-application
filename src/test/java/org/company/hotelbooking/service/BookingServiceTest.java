package org.company.hotelbooking.service;

import org.company.hotelbooking.entity.Booking;
import org.company.hotelbooking.entity.Option;
import org.company.hotelbooking.entity.Room;
import org.company.hotelbooking.entity.User;
import org.company.hotelbooking.repository.BookingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Volodymyr
 */
@RunWith(SpringRunner.class)
public class BookingServiceTest {

    @MockBean
    private BookingRepository bookingRepository;

    private Booking bookingWithoutOptions;
    private Double bookingWithoutOptionsTotalPrice;
    private Integer bookingWithoutOptionsId;

    private Booking bookingWithOptions;
    private Double bookingWithOptionsTotalPrice;
    private Integer bookingWithOptionsId;

    private List<Booking> listOfBookings;

    public void prepareBookingWithoutOptions() {

        bookingWithoutOptions = new Booking();
        bookingWithoutOptionsId = 1;
        bookingWithoutOptions.setId(bookingWithoutOptionsId);

        Room room1 = new Room();
        room1.setId(1);
        room1.setRoomPrice(20.0);

        bookingWithoutOptions.setRoom(room1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            bookingWithoutOptions.setDateIn(sdf.parse("2018-03-10"));
            bookingWithoutOptions.setDateOut(sdf.parse("2018-03-12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Option> emptyOptionList = new LinkedList<>();
        bookingWithoutOptions.setOptions(emptyOptionList);

        bookingWithoutOptionsTotalPrice = 40.0;

    }

    public void prepareBookingWithOptions() {

        bookingWithOptions = new Booking();
        bookingWithOptionsId = 1;
        bookingWithOptions.setId(bookingWithOptionsId);

        Room room1 = new Room();
        room1.setId(1);
        room1.setRoomPrice(20.0);

        bookingWithOptions.setRoom(room1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            bookingWithOptions.setDateIn(sdf.parse("2018-03-10"));
            bookingWithOptions.setDateOut(sdf.parse("2018-03-12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Option option1 = new Option();
        option1.setId(1);
        option1.setOptionPrice(10.0);
        Option option2 = new Option();
        option2.setId(2);
        option2.setOptionPrice(15.0);
        List<Option> optionList = new LinkedList<>();
        optionList.add(option1);
        optionList.add(option2);

        bookingWithOptions.setOptions(optionList);

        bookingWithOptionsTotalPrice = 90.0;

    }

    public void prepareListOfBookings() {

        User user1 = new User();
        user1.setId(1);
        Booking booking1 = new Booking();
        booking1.setUser(user1);
        Booking booking2 = new Booking();
        booking2.setUser(user1);

        listOfBookings = new LinkedList<>();
        listOfBookings.add(booking1);
        listOfBookings.add(booking2);

    }

    /**
     * Test verifies that all existing bookings in repository can be found.
     */
    @Test
    public void whenGetAllBookingsThenReturnListOfAllBookings() {

        Booking booking1 = new Booking();
        booking1.setId(1);
        Booking booking2 = new Booking();
        booking2.setId(2);
        Booking booking3 = new Booking();
        booking3.setId(3);


        List<Booking> bookings = new LinkedList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);

        Mockito.when(bookingRepository.findAll()).thenReturn(bookings);

        LinkedList<Booking> foundBookings = (LinkedList<Booking>) bookingRepository.findAll();

        Assert.assertEquals(Arrays.asList(foundBookings), Arrays.asList(bookings));

    }

    /**
     * Test verifies that if there are no bookings in repository then empty list is returned.
     */
    @Test
    public void whenGetAllBookingsOnEmptyRepositoryThenReturnEmptyList() {

        List<Booking> bookings = new LinkedList<>();

        Mockito.when(bookingRepository.findAll()).thenReturn(bookings);

        LinkedList<Booking> foundBookings = (LinkedList<Booking>) bookingRepository.findAll();

        Assert.assertTrue("List is not empty! ", foundBookings.isEmpty());

    }

    /**
     * Test verifies that bookings can be found by existing user id.
     */
    @Test
    public void whenGetBookingsByUserIdThenReturnListOfBookings() {

        prepareListOfBookings();

        int userId = 1;

        Mockito.when(bookingRepository.findAllByUserId(userId)).thenReturn(listOfBookings);

        LinkedList<Booking> foundBookings = (LinkedList<Booking>) bookingRepository.findAllByUserId(userId);

        Assert.assertEquals(Arrays.asList(foundBookings), Arrays.asList(listOfBookings));
    }

    /**
     * Test verifies that total price for booking with options calculates correctly.
     */
    @Test
    public void whenGetTotalPriceByBookingWithOptionsIdThenReturnTotalPriceWithOptions() {

        prepareBookingWithOptions();

        Mockito.when(bookingRepository.findById(bookingWithOptionsId)).thenReturn(bookingWithOptions);

        Double optionsPrice = bookingWithOptions.getOptions().stream().mapToDouble(Option::getOptionPrice).sum();
        Long countDays = (bookingWithOptions.getDateOut().getTime() - bookingWithOptions.getDateIn().getTime()) / (1000*60*60*24);
        Double totalPrice = (bookingWithOptions.getRoom().getRoomPrice() + optionsPrice) * countDays;

        Assert.assertEquals(bookingWithOptionsTotalPrice, totalPrice);

    }

    /**
     * Test verifies that total price for booking without options calculates correctly.
     */
    @Test
    public void whenGetTotalPriceByBookingIdWithoutOptionsThenReturnTotalPriceWithoutOptions() {

        prepareBookingWithoutOptions();

        Mockito.when(bookingRepository.findById(bookingWithoutOptionsId)).thenReturn(bookingWithoutOptions);

        Double optionsPrice = bookingWithoutOptions.getOptions().stream().mapToDouble(Option::getOptionPrice).sum();
        Long countDays = (bookingWithoutOptions.getDateOut().getTime() - bookingWithoutOptions.getDateIn().getTime()) / (1000*60*60*24);
        Double totalPrice = (bookingWithoutOptions.getRoom().getRoomPrice() + optionsPrice) * countDays;

        Assert.assertEquals(bookingWithoutOptionsTotalPrice, totalPrice);

    }

    /**
     * Test verifies that method createBooking calls 1 time.
     */
    @Test
    public void whenCallMethodCreateBookingThenItInvokesOneTime() {

        BookingService bookingService = mock(BookingService.class);
        Booking booking = new Booking();
        booking.setId(1);
        bookingService.createBooking(booking);

        verify(bookingService, times(1)).createBooking(booking);
    }

}
