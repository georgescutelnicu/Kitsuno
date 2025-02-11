package com.kitsuno.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 5, max = 14, message = "Username must be between 5 and 14 characters")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 60, max = 60, message = "Encrypted password must have 60 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "join_date", nullable = false, updatable = false)
    private LocalDate joinDate;

    @PrePersist
    protected void onCreate() {
        this.joinDate = LocalDate.now();
    }

    @Column(name = "api_key", unique = true)
    private String apiKey;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "verification_token", length = 64, unique = true)
    private String verificationToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flashcard> flashcards;

    public User() {
    }

    public User(String username, String email, String password, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", flashcards=" + flashcards +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public LocalDate getJoinDate() { return joinDate; }

    public String getApiKey() { return apiKey; }

    public boolean isEnabled() {
        return enabled;
    }

    public String getVerificationToken() { return verificationToken; }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setVerificationToken(String token) { this.verificationToken = token; }

    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
