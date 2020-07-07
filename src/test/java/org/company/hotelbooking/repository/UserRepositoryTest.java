package org.company.hotelbooking.repository;

import org.company.hotelbooking.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Volodymyr
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    /**
     * Test verifies method {@link UserRepository#findById} can successfully find user.
     */
    @Test
    public void whenFindByIdThenReturnUser() {

        User user = new User();
        user.setName("UserName");
        user.setSurname("UserSurname");
        entityManager.persist(user);
        entityManager.flush();

        User foundUser = userRepository.findById(user.getId());

        Assert.assertEquals(foundUser, user);

    }

}
