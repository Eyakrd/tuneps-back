package pfe.project.back.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pfe.project.back.Entity.User;
import pfe.project.back.Repo.UserRepo;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")


@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")

        public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
            return ResponseEntity.ok(service.authenticate(request));
    }


    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ProfileResponse profile = ProfileResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .cin(user.getCin())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();

        return ResponseEntity.ok(profile);
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Boolean> verifyPassword(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        String password = request.get("password");
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean passwordMatches = service.getPasswordEncoder().matches(password, user.getPassword());

        return ResponseEntity.ok(passwordMatches);
    }


    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody User updatedUser, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setEmail(updatedUser.getEmail());
        user.setCin(updatedUser.getCin());
        // attention au mot de passe, il faut le encoder avant
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Profil mis à jour avec succès"));
    }


}


