package com.kitsuno.service;

import com.kitsuno.dao.UserDAO;
import com.kitsuno.dto.UserDTO;
import com.kitsuno.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("user1", "user1@example.com", "hashedpassword1", true);
        user1.setId(1);
        user1.setApiKey("apikey1");
        user2 = new User("user2", "user2@example.com", "hashedpassword2", true);
        user2.setId(2);
        user2.setApiKey("apikey2");
    }

    @Test
    void testFindByIdExisting() {
        when(userDAO.findById(1)).thenReturn(Optional.of(user1));

        Optional<User> user = userService.findById(1);

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("user1");
    }

    @Test
    void testFindByIdNonExisting() {
        when(userDAO.findById(999)).thenReturn(Optional.empty());

        Optional<User> user = userService.findById(999);

        assertThat(user).isEmpty();
    }

    @Test
    void testFindByUsernameExisting() {
        when(userDAO.findByUsername("user1")).thenReturn(Optional.of(user1));

        Optional<User> user = userService.findByUsername("user1");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("user1");
    }

    @Test
    void testFindByUsernameNonExisting() {
        when(userDAO.findByUsername("user3")).thenReturn(Optional.empty());

        Optional<User> user = userService.findByUsername("user3");

        assertThat(user).isEmpty();
    }

    @Test
    void testFindByEmailExisting() {
        when(userDAO.findByEmail("user1@example.com")).thenReturn(Optional.of(user1));

        Optional<User> user = userService.findByEmail("user1@example.com");

        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("user1@example.com");
    }

    @Test
    void testFindByEmailNonExisting() {
        when(userDAO.findByEmail("user3@example.com")).thenReturn(Optional.empty());

        Optional<User> user = userService.findByEmail("user3@example.com");

        assertThat(user).isEmpty();
    }

    @Test
    void testFindByApiKeyExisting() {
        when(userDAO.findByApiKey("apikey1")).thenReturn(Optional.of(user1));

        Optional<User> user = userService.findByApiKey("apikey1");

        assertThat(user).isPresent();
        assertThat(user.get().getApiKey()).isEqualTo("apikey1");
    }

    @Test
    void testFindByApiKeyNonExisting() {
        when(userDAO.findByApiKey("apikey3")).thenReturn(Optional.empty());

        Optional<User> user = userService.findByApiKey("apikey3");

        assertThat(user).isEmpty();
    }

    @Test
    void testSave() {
        User user3 = new User("user3", "user3@example.com", "hashedpassword3", true);
        user3.setId(3);

        when(userDAO.save(user3)).thenReturn(user3);

        User savedUser = userService.save(user3);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("user3");
        assertThat(savedUser.getId()).isEqualTo(3);
    }

    @Test
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user3");
        userDTO.setEmail("user3@example.com");
        userDTO.setPassword("password3");

        User expectedUser = new User();
        expectedUser.setUsername("user3");
        expectedUser.setEmail("user3@example.com");
        expectedUser.setPassword("encodedPassword3");
        expectedUser.setEnabled(true);
        expectedUser.setApiKey("apikey3");

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword3");
        when(userDAO.save(any(User.class))).thenReturn(expectedUser);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        boolean result = userService.registerUser(userDTO, bindingResult);

        assertThat(result).isTrue();
        verify(userDAO).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("user3");
        assertThat(savedUser.getEmail()).isEqualTo("user3@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword3");
        assertThat(savedUser.getApiKey()).isNotNull();
    }

    @Test
    void testUpdateUsername() {
        String newUsername = "newUser1";

        when(userDetailsService.loadUserByUsername(newUsername)).thenReturn(userDetails);
        when(userDAO.save(user1)).thenReturn(user1);

        userService.updateUsernameAndRefreshAuthentication(user1, newUsername);

        assertThat(user1.getUsername()).isEqualTo(newUsername);
        verify(userDAO).save(user1);
        verify(userDetailsService).loadUserByUsername(newUsername);
    }

    @Test
    void testUpdatePassword() {
        String newPassword = "newPassword";

        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        when(userDAO.save(user1)).thenReturn(user1);

        userService.updatePassword(user1, newPassword);

        assertThat(user1.getPassword()).isEqualTo("encodedNewPassword");
        verify(userDAO).save(user1);
    }

    @Test
    void testGenerateApiKey() {
        String generatedApiKey = userService.generateApiKey();

        assertThat(generatedApiKey).isNotNull();
        assertThat(generatedApiKey).hasSize(32);
    }

    @Test
    void testUpdateApiKey() {
        userService.updateApiKey(user1);

        assertThat(user1.getApiKey()).isNotNull();
        assertThat(user1.getApiKey()).isNotEqualTo("apikey1");
        assertThat(user1.getApiKey().length()).isEqualTo(32);
    }
}
