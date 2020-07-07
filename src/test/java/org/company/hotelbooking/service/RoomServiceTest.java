package org.company.hotelbooking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.company.hotelbooking.entity.Booking;
import org.company.hotelbooking.entity.Room;
import org.company.hotelbooking.repository.BookingRepository;
import org.company.hotelbooking.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Volodymyr
 */
@RunWith(SpringRunner.class)
public class RoomServiceTest {

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private BookingRepository bookingRepository;

    private LinkedList<Room> listWithFourRooms;

    public void setUp() {

        Room room1 = new Room();
        room1.setId(1);
        room1.setRoomCategory(1);
        Room room2 = new Room();
        room2.setId(2);
        room2.setRoomCategory(1);
        Room room3 = new Room();
        room3.setId(3);
        room3.setRoomCategory(2);
        Room room4 = new Room();
        room4.setId(4);
        room4.setRoomCategory(2);

        listWithFourRooms = new LinkedList<>();
        listWithFourRooms.add(room1);
        listWithFourRooms.add(room2);
        listWithFourRooms.add(room3);
        listWithFourRooms.add(room4);

    }

    /**
     * Test verifies that all existing rooms in repository can be found.
     */
    @Test
    public void whenGetAllRoomsThenReturnListOfAllRooms() {

        setUp();

        Mockito.when(roomRepository.findAll()).thenReturn(listWithFourRooms);

        LinkedList<Room> foundRoomList = (LinkedList<Room>) roomRepository.findAll();

        Assert.assertEquals(Arrays.asList(foundRoomList), Arrays.asList(listWithFourRooms));

    }

    /**
     * Test verifies that when there are no rooms in repository, method returns empty list.
     */
    @Test
    public void whenGetAllRoomsOnEmptyDatasetThenReturnEmptyList() {

        LinkedList<Room> emptyRoomList = new LinkedList<>();

        Mockito.when(roomRepository.findAll()).thenReturn(emptyRoomList);

        LinkedList<Room> foundRoomList = (LinkedList<Room>) roomRepository.findAll();

        Assert.assertTrue(foundRoomList.isEmpty());

    }

    /**
     * Test verifies that user can find all rooms by category id.
     */
    @Test
    public void whenGetRoomsFilteredByCategoryThenReturnListOfRooms() {

        setUp();

        // list that contains only rooms with categoryId = 1
        Room room1 = new Room();
        room1.setId(1);
        room1.setRoomCategory(1);
        Room room2 = new Room();
        room2.setId(2);
        room2.setRoomCategory(1);
        List<Room> categoryOneRoomList = new LinkedList<>();
        categoryOneRoomList.add(room1);
        categoryOneRoomList.add(room2);

        int categoryId = 1;

        Mockito.when(roomRepository.getAllByRoomCategory(categoryId))
                .thenReturn(listWithFourRooms.stream().filter(e -> e.getRoomCategory() == categoryId).collect(Collectors.toList()));

        ArrayList<Room> foundRoomsList = (ArrayList<Room>) roomRepository.getAllByRoomCategory(categoryId);

        Assert.assertEquals(Arrays.asList(foundRoomsList), Arrays.asList(categoryOneRoomList));

    }

    /**
     * Test verifies that user gets empty list when there is no rooms with selected category id.
     */
    @Test
    public void whenGetRoomsFilteredByCategoryThenReturnEmptyList() {

        setUp();

        int categoryId = 3;

        Mockito.when(roomRepository.getAllByRoomCategory(categoryId))
                .thenReturn(listWithFourRooms.stream().filter(e -> e.getRoomCategory() == categoryId).collect(Collectors.toList()));

        ArrayList<Room> foundRoomsList = (ArrayList<Room>) roomRepository.getAllByRoomCategory(categoryId);

        Assert.assertTrue(foundRoomsList.isEmpty());

    }

    /**
     * Test verifies that empty list is returned when call method getAvailableRooms with reserved dates.
     */
    @Test
    public void whenGetAvailableRoomsOnReservedDatesThenReturnEmptyList() throws ParseException {

        Room room1 = new Room();
        room1.setId(1);
        Room room2 = new Room();
        room2.setId(2);

        List<Room> roomList = new LinkedList<>();
        roomList.add(room1);
        roomList.add(room2);

        Booking booking1 = new Booking();
        Booking booking2 = new Booking();

        booking1.setRoom(room1);
        booking2.setRoom(room2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        booking1.setDateIn(sdf.parse("2018-03-10"));
        booking1.setDateOut(sdf.parse("2018-03-12"));
        booking2.setDateIn(sdf.parse("2018-03-10"));
        booking2.setDateOut(sdf.parse("2018-03-12"));


        List<Booking> bookingList = new LinkedList<>();
        bookingList.add(booking1);
        bookingList.add(booking2);

        Mockito.when(roomRepository.findAll()).thenReturn(roomList);

        Mockito.when(
                bookingRepository.findBookingsThatOverlapSpecifiedPeriod(booking1.getDateIn(), booking1.getDateOut()))
                .thenReturn(bookingList);

        LinkedList<Booking> booked = (LinkedList<Booking>) bookingRepository.findBookingsThatOverlapSpecifiedPeriod(booking1.getDateIn(), booking1.getDateOut());

        Set<Room> reservedRooms = booked.stream()
                .map(Booking::getRoom)
                .collect(Collectors.toSet());
        List<Room> availableRooms = roomList.stream()
                .filter(e -> !reservedRooms.contains(e))
                .collect(Collectors.toList());

        Assert.assertTrue(availableRooms.isEmpty());

    }

    /**
     * Test verifies that list with 1 room is returned.
     */
    @Test
    public void whenGetAvailableRoomsThenReturnListWithOneRoom() throws ParseException {

        Room room1 = new Room();
        room1.setId(1);
        Room room2 = new Room();
        room2.setId(2);
        Room room3 = new Room();
        room3.setId(3);
        Room room4 = new Room();
        room4.setId(4);

        listWithFourRooms = new LinkedList<>();
        listWithFourRooms.add(room1);
        listWithFourRooms.add(room2);
        listWithFourRooms.add(room3);
        listWithFourRooms.add(room4);

        // list with only one room: room4
        List<Room> roomFourList = new LinkedList<>();
        roomFourList.add(room4);

        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        Booking booking3 = new Booking();

        booking1.setRoom(room1);
        booking2.setRoom(room2);
        booking3.setRoom(room3);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        booking1.setDateIn(sdf.parse("2018-03-10"));
        booking1.setDateOut(sdf.parse("2018-03-12"));
        booking2.setDateIn(sdf.parse("2018-03-10"));
        booking2.setDateOut(sdf.parse("2018-03-12"));
        booking3.setDateIn(sdf.parse("2018-03-10"));
        booking3.setDateOut(sdf.parse("2018-03-12"));

        List<Booking> bookingList = new LinkedList<>();
        bookingList.add(booking1);
        bookingList.add(booking2);
        bookingList.add(booking3);

        Mockito.when(roomRepository.findAll()).thenReturn(listWithFourRooms);

        Mockito.when(
                bookingRepository.findBookingsThatOverlapSpecifiedPeriod(booking1.getDateIn(), booking1.getDateOut()))
                .thenReturn(bookingList);

        LinkedList<Booking> booked = (LinkedList<Booking>) bookingRepository.findBookingsThatOverlapSpecifiedPeriod(booking1.getDateIn(), booking1.getDateOut());

        Set<Room> reservedRooms = booked.stream()
                .map(Booking::getRoom)
                .collect(Collectors.toSet());
        List<Room> availableRooms = listWithFourRooms.stream()
                .filter(e -> !reservedRooms.contains(e))
                .collect(Collectors.toList());

        Assert.assertEquals(Arrays.asList(roomFourList), Arrays.asList(availableRooms));

    }

    /**
     * Test verifies that list of available rooms is returned.
     */
    @Test
    public void whenGetAvailableRoomsThenReturnListOfAvailableRooms() throws ParseException {

        Room room1 = new Room();
        room1.setId(1);
        Room room2 = new Room();
        room2.setId(2);
        Room room3 = new Room();
        room3.setId(3);
        Room room4 = new Room();
        room4.setId(4);

        List<Room> roomList = new LinkedList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);

        // list with two rooms: room3 and room4
        List<Room> roomThreeFourList = new LinkedList<>();
        roomThreeFourList.add(room3);
        roomThreeFourList.add(room4);

        Booking booking1 = new Booking();
        Booking booking2 = new Booking();

        booking1.setRoom(room1);
        booking2.setRoom(room2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        booking1.setDateIn(sdf.parse("2018-03-10"));
        booking1.setDateOut(sdf.parse("2018-03-12"));
        booking2.setDateIn(sdf.parse("2018-03-10"));
        booking2.setDateOut(sdf.parse("2018-03-12"));


        List<Booking> bookingList = new LinkedList<>();
        bookingList.add(booking1);
        bookingList.add(booking2);

        Mockito.when(roomRepository.findAll()).thenReturn(roomList);

        Mockito.when(
                bookingRepository.findBookingsThatOverlapSpecifiedPeriod(booking1.getDateIn(), booking1.getDateOut()))
                .thenReturn(bookingList);

        LinkedList<Booking> booked = (LinkedList<Booking>) bookingRepository.findBookingsThatOverlapSpecifiedPeriod(booking1.getDateIn(), booking1.getDateOut());

        Set<Room> reservedRooms = booked.stream()
                .map(Booking::getRoom)
                .collect(Collectors.toSet());
        List<Room> availableRooms = roomList.stream()
                .filter(e -> !reservedRooms.contains(e))
                .collect(Collectors.toList());

        Assert.assertEquals(Arrays.asList(roomThreeFourList), Arrays.asList(availableRooms));

    }
}
