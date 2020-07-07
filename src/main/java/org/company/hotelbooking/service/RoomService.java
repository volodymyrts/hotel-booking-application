package org.company.hotelbooking.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.company.hotelbooking.entity.Booking;
import org.company.hotelbooking.entity.Room;
import org.company.hotelbooking.repository.BookingRepository;
import org.company.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Volodymyr
 */
@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> getAllRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    public List<Room> getRoomsFilteredByCategory(int id) {
        return roomRepository.getAllByRoomCategory(id);
    }

    /**
     * This method gets all available rooms for specified period of dates.
     * @param in date when user wants to come to the hotel
     * @param out date when user wants to leave room
     * @return list of available rooms
     */
    public List<Room> getAvailableRooms(Date in, Date out){
        List<Booking> booked = bookingRepository.findBookingsThatOverlapSpecifiedPeriod(in, out);
        List<Room> allRooms = (List<Room>) roomRepository.findAll();
        Set<Room> reservedRooms = booked.stream()
                .map(Booking::getRoom)
                .collect(Collectors.toSet());
        return allRooms.stream()
                .filter(e -> !reservedRooms.contains(e))
                .collect(Collectors.toList());
    }
}
