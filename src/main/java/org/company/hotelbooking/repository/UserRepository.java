package org.company.hotelbooking.repository;

import org.company.hotelbooking.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Volodymyr
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(int id);

}
