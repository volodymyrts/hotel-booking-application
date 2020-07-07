package org.company.hotelbooking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.company.hotelbooking.entity.Room;
import org.company.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Volodymyr
 */
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET
    )
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @RequestMapping(
            value = "/category/{id:[\\d]+}",
            method = RequestMethod.GET
    )
    public List<Room> getRoomsFilteredByCategory(@PathVariable("id") int id) {
        return roomService.getRoomsFilteredByCategory(id);
    }

    @RequestMapping(
            value = "/available/{date1}/{date2}",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<Room>> getFreeRoomsForSpecifiedDates(
            @PathVariable("date1") String dateIn,
            @PathVariable("date2") String dateOut) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date in = dateFormat.parse(dateIn);
        Date out = dateFormat.parse(dateOut);
        if (in.compareTo(out) >= 0) {
            return ResponseEntity.notFound().build();
        }

        List<Room> result = roomService.getAvailableRooms(in, out);
        return ResponseEntity.ok().body(result);
    }

}
