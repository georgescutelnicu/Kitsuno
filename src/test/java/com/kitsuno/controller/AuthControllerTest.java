package com.kitsuno.controller;

import com.kitsuno.dto.UserDTO;
import com.kitsuno.entity.User;
import com.kitsuno.service.UserService;
import com.kitsuno.testutils.MockUserDetailsArgumentResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testShowRegistrationForm() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testRegisterUserValid() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("hashedpassword.1");
        userDTO.setConfirmPassword("hashedpassword.1");

        when(userService.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userService.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        when(userService.registerUser(userDTO, null)).thenReturn(true);


        mockMvc.perform(post("/register")
                        .flashAttr("user", userDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testRegisterUserInvalidPassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("hashedpassword1");
        userDTO.setConfirmPassword("hashedpassword1");

        when(userService.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userService.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/register")
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user", "password"));
    }

    @Test
    void testRegisterUserPasswordMismatch() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("hashedpassword.1");
        userDTO.setConfirmPassword("hashedpassword.2");

        when(userService.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userService.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/register")
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user", "confirmPassword"));
    }

    @Test
    void testRegisterUserUsernameExists() throws Exception {
        User existingUser = new User();
        existingUser.setUsername("user1");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("hashedpassword.1");
        userDTO.setConfirmPassword("hashedpassword.1");

        when(userService.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(existingUser));
        when(userService.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/register")
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user", "username"));
    }

    @Test
    void testRegisterUserEmailExists() throws Exception {
        User existingUser = new User();
        existingUser.setEmail("user1@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");
        userDTO.setEmail("user1@example.com");
        userDTO.setPassword("hashedpassword.1");
        userDTO.setConfirmPassword("hashedpassword.1");

        when(userService.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userService.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        mockMvc.perform(post("/register")
                        .flashAttr("user", userDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user", "email"));
    }

    @Test
    void testShowLoginFormAuthenticated() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController())
                .setCustomArgumentResolvers(new MockUserDetailsArgumentResolver(true))
                .build();

        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));
    }

    @Test
    public void testShowLoginFormUnauthenticated() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new AuthController())
                .setCustomArgumentResolvers(new MockUserDetailsArgumentResolver(false))
                .setViewResolvers(viewResolver)
                .build();

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testShowLogoutForm() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("logout"));
    }
}
