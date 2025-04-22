package pfe.project.back.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ajout de la stratégie pour l'ID
    private Integer id;

    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false) // Empêche le stockage de mots de passe nulls
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @PrePersist
    private void prePersist() {
        if (role == null) {
            role = Role.USER; // Set default role to USER if not specified
        }
    }
//    @OneToMany(mappedBy = "user")
//    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority( role.name().trim()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }


}
