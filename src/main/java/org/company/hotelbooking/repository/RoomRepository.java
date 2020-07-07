package org.company.hotelbooking.repository;

import java.util.List;
import org.company.hotelbooking.entity.Room;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Volodymyr
 */
public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> getAllByRoomCategory(int categoryId);

}
