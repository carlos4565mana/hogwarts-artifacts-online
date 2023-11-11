package com.carlos.springbootcurse.user;

import com.carlos.springbootcurse.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    List<User> users;
    @BeforeEach
    void setUp() {
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("john");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("admin user");

        User u2 = new User();
        u2.setId(2);
        u2.setUsername("eric");
        u2.setPassword("654321");
        u2.setEnabled(true);
        u2.setRoles("user");

        User u3 = new User();
        u3.setId(3);
        u3.setUsername("tom");
        u3.setPassword("qwerty");
        u3.setEnabled(false);
        u3.setRoles("user");

        this.users = new ArrayList<>();
        this.users.add(u1);
        this.users.add(u2);
        this.users.add(u3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllUsersSuccess(){
        // Given. Arrange inputs and targets. Define the behavior of Mock object userRepository.
        given(this.userRepository.findAll()).willReturn(this.users);

        // When. Act on the target behavior. Act steps should cover the method to be tested.
        List<User> actualUsers = this.userService.findAll();

        // Then. Assert expected outcomes.
        assertThat(actualUsers.size()).isEqualTo(this.users.size());

        // Verify userRepository.findAll() is called exactly once.
        verify(this.userRepository, times(1)).findAll();


    }
    @Test
    void testFindUserByIdSuccess(){
        User u = new User();
        u.setId(1);
        u.setUsername("john");
        u.setPassword("123456");
        u.setEnabled(true);
        u.setRoles("admin user");

        given(this.userRepository.findById(1)).willReturn(Optional.of(u)); // Define the behavior of the mock object.


        // When. Act on the target behavior. Act steps should cover the method to be tested.
        User returnedUser = this.userService.findById(1);

        // Then. Assert expected outcomes.
        assertThat(returnedUser.getId()).isEqualTo(u.getId());
        assertThat(returnedUser.getUsername()).isEqualTo(u.getUsername());
        assertThat(returnedUser.getPassword()).isEqualTo(u.getPassword());
        assertThat(returnedUser.isEnabled()).isEqualTo(u.isEnabled());
        assertThat(returnedUser.getRoles()).isEqualTo(u.getRoles());
        verify(this.userRepository, times(1)).findById(1);


    }
    @Test
    void testFindUserByIdNotFound(){
        given(this.userRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(()->{
            User userReturned = this.userService.findById(1);
        });

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with Id 1 :(");
        verify(this.userRepository, times(1)).findById(Mockito.any(Integer.class));

    }

    @Test
    void testSaveUserSuccess(){
        User newUser = new User();

        newUser.setUsername("lily");
        newUser.setPassword("123456");
        newUser.setEnabled(true);
        newUser.setRoles("user");

        given(this.userRepository.save(newUser)).willReturn(newUser);

        User returnedUser = this.userService.save(newUser);

        // Then
        assertThat(returnedUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(returnedUser.getPassword()).isEqualTo(newUser.getPassword());
        assertThat(returnedUser.isEnabled()).isEqualTo(newUser.isEnabled());
        assertThat(returnedUser.getRoles()).isEqualTo(newUser.getRoles());
        verify(this.userRepository, times(1)).save(newUser);


    }

    @Test
    void testUpdateUserSuccess(){
        //Given
        User oldUser = new User();

        oldUser.setId(1);
        oldUser.setUsername("john");
        oldUser.setPassword("123456");
        oldUser.setEnabled(true);
        oldUser.setRoles("admin user");

        User update = new User();

        update.setUsername("john - update");
        update.setPassword("123456");
        update.setEnabled(true);
        update.setRoles("admin user");

        given(this.userRepository.findById(1)).willReturn(Optional.of(oldUser));
        given(this.userRepository.save(oldUser)).willReturn(oldUser);

        User updatedUser = this.userService.update(1, update);

        assertThat(updatedUser.getId()).isEqualTo(1);
        assertThat(updatedUser.getUsername()).isEqualTo(update.getUsername());
        verify(this.userRepository, times(1)).findById(1);
        verify(this.userRepository, times(1)).save(oldUser);


    }
    @Test
    void testUpdateNotFound(){
        User update = new User();

        update.setUsername("john - update");
        update.setPassword("123456");
        update.setEnabled(true);
        update.setRoles("admin user");

        given(this.userRepository.findById(1)).willReturn(Optional.empty());

        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.userService.update(1, update);
        });

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with Id 1 :(");
        verify(this.userRepository, times(1)).findById(1);

    }
    @Test
    void testDeleteUserSuccess(){
        User user = new User();

        user.setId(1);
        user.setUsername("john");
        user.setPassword("123456");
        user.setEnabled(true);
        user.setRoles("admin user");

        given(this.userRepository.findById(1)).willReturn(Optional.of(user));
        doNothing().when(this.userRepository).deleteById(1);

        this.userService.delete(1);

        verify(this.userRepository, times(1)).deleteById(1);



    }
    @Test
    void testDeleteNotFound(){
        given(this.userRepository.findById(1)).willReturn(Optional.empty());
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.userService.delete(1);
        });
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with Id 1 :(");
        verify(this.userRepository, times(1)).findById(1);

    }

}