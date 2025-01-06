package com.kitsuno.controller;

import com.kitsuno.dto.PasswordUpdateDTO;
import com.kitsuno.dto.UsernameUpdateDTO;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @MockBean
    private FlashcardService flashcardService;

    @Test
    public void testShowProfile() throws Exception {
        User user = new User();
        user.setId(1);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));
        when(flashcardService.countFlashcardsByUserId(user.getId())).thenReturn(5);

        mockMvc.perform(get("/profile").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("countFlashcards"))
                .andExpect(model().attributeExists("usernameUpdateDTO"))
                .andExpect(model().attributeExists("passwordUpdateDTO"));

        mockedStatic.close();
    }

    @Test
    public void testUpdateUsernameSuccess() throws Exception {
        User user = new User();
        user.setUsername("user1");

        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO();
        usernameUpdateDTO.setNewUsername("user2");

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));
        when(userService.findByUsername("user2")).thenReturn(Optional.empty());

        mockMvc.perform(post("/updateUsername")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("usernameUpdateDTO", usernameUpdateDTO)
                        .flashAttr("passwordUpdateDTO", passwordUpdateDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("usernameSuccess"));

        verify(userService, times(1)).updateUsernameAndRefreshAuthentication(user,
                "user2");

        mockedStatic.close();
    }

    @Test
    public void testUpdateUsernameUsernameAlreadyExists() throws Exception {
        User user = new User();
        user.setUsername("user1");

        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO();
        usernameUpdateDTO.setNewUsername("existingUser");

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();


        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));

        when(userService.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/updateUsername")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("usernameUpdateDTO", usernameUpdateDTO)
                        .flashAttr("passwordUpdateDTO", passwordUpdateDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeHasFieldErrors("usernameUpdateDTO", "newUsername"));

        verify(userService, times(0)).updateUsernameAndRefreshAuthentication(any(), any());

        mockedStatic.close();
    }

    @Test
    public void testUpdatePasswordSuccess() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("hashedPassword.1");

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setOldPassword("hashedPassword.1");
        passwordUpdateDTO.setNewPassword("hashedPassword.2");
        passwordUpdateDTO.setConfirmPassword("hashedPassword.2");

        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO();

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("hashedPassword.1", user.getPassword())).thenReturn(true);

        mockMvc.perform(post("/updatePassword")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("passwordUpdateDTO", passwordUpdateDTO)
                        .flashAttr("usernameUpdateDTO", usernameUpdateDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("passwordSuccess"));

        verify(userService, times(1)).updatePassword(user, "hashedPassword.2");

        mockedStatic.close();
    }

    @Test
    public void testUpdatePasswordOldPasswordIncorrect() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("hashedPassword.1");

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setOldPassword("wrongOldPassword");
        passwordUpdateDTO.setNewPassword("hashedPassword.2");
        passwordUpdateDTO.setConfirmPassword("hashedPassword.2");

        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO();

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongOldPassword", user.getPassword())).thenReturn(false);

        mockMvc.perform(post("/updatePassword")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("passwordUpdateDTO", passwordUpdateDTO)
                        .flashAttr("usernameUpdateDTO", usernameUpdateDTO))
                .andExpect(status().isOk()) // Stay on the profile page
                .andExpect(model().attributeHasFieldErrors("passwordUpdateDTO", "oldPassword"))
                .andExpect(view().name("profile"));

        mockedStatic.close();
    }

    @Test
    public void testUpdatePasswordNewAndConfirmPasswordDoNotMatch() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("hashedPassword.1");

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setOldPassword("hashedPassword.1");
        passwordUpdateDTO.setNewPassword("hashedPassword.2");
        passwordUpdateDTO.setConfirmPassword("differentPassword");

        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO();

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("hashedPassword.1", user.getPassword())).thenReturn(true);

        mockMvc.perform(post("/updatePassword")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("passwordUpdateDTO", passwordUpdateDTO)
                        .flashAttr("usernameUpdateDTO", usernameUpdateDTO))
                .andExpect(status().isOk()) // Stay on the profile page
                .andExpect(model().attributeHasFieldErrors("passwordUpdateDTO", "confirmPassword"))
                .andExpect(view().name("profile"));

        mockedStatic.close();
    }

    @Test
    public void testUpdatePasswordNotValid() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("hashedPassword.1");

        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        passwordUpdateDTO.setOldPassword("hashedPassword.1");
        passwordUpdateDTO.setNewPassword("pass");
        passwordUpdateDTO.setConfirmPassword("pass");

        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO();

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("hashedPassword.1", user.getPassword())).thenReturn(true);

        mockMvc.perform(post("/updatePassword")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("passwordUpdateDTO", passwordUpdateDTO)
                        .flashAttr("usernameUpdateDTO", usernameUpdateDTO))
                .andExpect(status().isOk()) // Stay on the profile page
                .andExpect(model().attributeHasFieldErrors("passwordUpdateDTO", "newPassword"))
                .andExpect(view().name("profile"));

        mockedStatic.close();
    }

    @Test
    public void testUpdateApiKeySuccess() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setApiKey("apikey1");

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));

        doAnswer(invocation -> {
            user.setApiKey("apikey2");
            return null;
        }).when(userService).updateApiKey(user);
        when(userService.save(user)).thenReturn(user);

        mockMvc.perform(get("/updateApiKey")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).updateApiKey(user);

        assertEquals("apikey2", user.getApiKey());

        mockedStatic.close();
    }
}
