package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.owu.crm_programming_school.views.Views;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = {Views.Level1.class,Views.Level2.class,Views.Level3.class})
    private int id;

    @JsonView(value = {Views.Level1.class,Views.Level2.class,Views.Level3.class})
    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 20)
    private String name;

    @JsonView(value = {Views.Level1.class,Views.Level2.class,Views.Level3.class})
    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 20)
    private String surname;

    @Column(unique = true)
    @JsonView(value = {Views.Level1.class,Views.Level2.class,Views.Level3.class})
    @NotBlank(message = "email is a required field")
    @Email
    @Size(min = 1, max = 254)
    private String email;

    @NotBlank(message = "password cannot be empty")
    @JsonView(value = {Views.Level1.class})

    @Size(min = 8, max = 128, message = "The password must be between 8 and 20 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$", message = "The password must contain an upper and lower case letter, a number and a special character.")
    private String password;

    @JsonView(value = {Views.Level1.class})
    private String refreshToken;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JsonView(value = Views.Level1.class)
    @Hidden
    private List<Role> roles;


    @JsonView(value = {Views.Level1.class,Views.Level2.class})
    private boolean isActive;

    @JsonView(value = {Views.Level1.class,Views.Level2.class})
    private Boolean is_superuser;

    @JsonView(value = {Views.Level1.class,Views.Level2.class})
    private Boolean is_staff;

    @JsonView(value = {Views.Level1.class,Views.Level2.class})
    private LocalDateTime createdAt;

    @JsonView(value = {Views.Level1.class})
    private LocalDateTime updatedAt;

    @JsonView(value = {Views.Level1.class})
    private LocalDateTime lastLogin;

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(UserDetails loadUserByEmail) {
    }

    @Override
    @Hidden
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return authorities;
    }

    @Override
    @Hidden
    public String getUsername() {
        return this.email;
    }

    @Override
    @Hidden
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Hidden
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    @Hidden
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Hidden
    public boolean isEnabled() {
        return true;
    }
}