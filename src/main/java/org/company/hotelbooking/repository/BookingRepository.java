package org.company.hotelbooking.repository;

import java.util.Date;
import java.util.List;
import org.company.hotelbooking.entity.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Volodymyr
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findAllByUserId(int id);

    Booking findById(int id);

    // select all bookings that overlap input dates
    @Query("select b from Booking as b where (b.dateIn >= :dIn and b.dateIn < :dOut) " +
            "or (b.dateOut > :dIn and b.dateOut <= :dOut)")
    List<Booking> findBookingsThatOverlapSpecifiedPeriod(@Param("dIn") Date dIn, @Param("dOut") Date dOut);

}
