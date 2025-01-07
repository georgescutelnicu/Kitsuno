package com.kitsuno.dao;

import com.kitsuno.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/user_data.sql")
class UserDAOImplTest {

    @Autowired
    private UserDAOImpl userDAO;

    private final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Test
    void testFindByIdExisting() {
        Optional<User> user = userDAO.findById(1);

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("user1");
        assertThat(user.get().getEmail()).isEqualTo("user1@example.com");
    }

    @Test
    void testFindByIdNonExisting() {
        Optional<User> user = userDAO.findById(999);

        assertThat(user).isEmpty();
    }

    @Test
    void testFindByUsernameExisting() {
        Optional<User> user = userDAO.findByUsername("user1");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("user1");
        assertThat(user.get().getEmail()).isEqualTo("user1@example.com");
    }

    @Test
    void testFindByUsernameNonExisting() {
        Optional<User> user = userDAO.findByUsername("user0");

        assertThat(user).isEmpty();
    }

    @Test
    void testFindByEmailExisting() {
        Optional<User> user = userDAO.findByEmail("user2@example.com");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("user2");
        assertThat(user.get().getEmail()).isEqualTo("user2@example.com");
    }

    @Test
    void testFindByEmailNonExisting() {
        Optional<User> user = userDAO.findByEmail("user0@example.com");

        assertThat(user).isEmpty();
    }

    @Test
    void testFindByApiKeyExisting() {
        Optional<User> user = userDAO.findByApiKey("apikey1");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("user1");
    }

    @Test
    void testFindByApiKeyNonExisting() {
        Optional<User> user = userDAO.findByApiKey("apikey0");

        assertThat(user).isEmpty();
    }

    @Test
    void testSaveUser() {
        User newUser = new User();
        newUser.setUsername("user3");
        newUser.setPassword(PASSWORD_ENCODER.encode("hashedpassword3"));
        newUser.setEmail("user3@example.com");
        newUser.setApiKey("apikey3");

        User savedUser = userDAO.save(newUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("user3");
        assertThat(savedUser.getEmail()).isEqualTo("user3@example.com");
        assertThat(savedUser.getApiKey()).isEqualTo("apikey3");
    }
}
