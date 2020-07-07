package org.company.hotelbooking.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.company.hotelbooking.entity.User;
import org.company.hotelbooking.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Volodymyr
 */
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    /**
     * Test verifies that all existing users in repository can be found.
     */
    @Test
    public void whenGetAllUsersThenReturnListOfAllUser() {

        User user11 = new User();
        user11.setId(11);
        User user12 = new User();
        user11.setId(12);
        User user13 = new User();
        user11.setId(13);

        List<User> userList = new LinkedList<>();
        userList.add(user11);
        userList.add(user12);
        userList.add(user13);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        LinkedList<User> foundUserList = (LinkedList<User>) userRepository.findAll();

        Assert.assertEquals(Arrays.asList(foundUserList), Arrays.asList(userList));

    }

    /**
     * Test verifies that when there are no users in repository, method returns empty list.
     */
    @Test
    public void whenGetAllUsersOnNullArrayThenReturnEmptyList() {

        List<User> userList = new LinkedList<>();

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        LinkedList<User> foundUserList = (LinkedList<User>) userRepository.findAll();

        Assert.assertTrue(foundUserList.isEmpty());

    }

    /**
     * Test verifies that existing user can be found.
     */
    @Test
    public void whenUserFindByIdThenReturnUser() {

        User user = new User();
        user.setId(88);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(user);

        User foundUser = userRepository.findById(user.getId());

        Assert.assertEquals(foundUser, user);
    }

    /**
     * Test verifies that non-existing user can not be found.
     */
    @Test
    public void whenUserFindByInvalidIdThenReturnNull() {

        User user = new User();
        user.setId(88);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(user);

        User foundUser = userRepository.findById(99);

        Assert.assertNull(foundUser);
    }

    /**
     * Test verifies that method createUser calls 1 time.
     */
    @Test
    public void whenCallMethodCreateUserThenItInvokesOneTime() {

        UserService userService = mock(UserService.class);
        User user = new User();
        user.setId(88);
        userService.createUser(user);

        verify(userService, times(1)).createUser(user);
    }

}
