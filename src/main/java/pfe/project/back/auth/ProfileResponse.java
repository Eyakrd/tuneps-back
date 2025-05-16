package pfe.project.back.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String cin;
    private String role;
    private String password;
}

