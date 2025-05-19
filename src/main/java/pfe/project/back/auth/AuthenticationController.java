package pfe.project.back.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pfe.project.back.Dto.Mail;
import pfe.project.back.Dto.NewPassword;
import pfe.project.back.Dto.UserCode;
import pfe.project.back.Entity.User;
import pfe.project.back.Repo.EmailService;
import pfe.project.back.Repo.UserRepo;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")


@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")

    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
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


    @PostMapping("checkEmail")
    public AuthenticationResponse checkEmail(@RequestBody ResetPassword resetpassword) {
        Optional<User> optionalUser = this.service.findUserByEmail(resetpassword.getEmail());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            String code = UserCode.getCode();
            Mail mail = new Mail(resetpassword.getEmail(), code);
            emailService.sendCodeByMail(mail);

            // Ici, on utilise uniquement le champ resetCode, plus besoin de Code
            user.setResetCode(code);
            user.setResetCodeExpiration(LocalDateTime.now().plusMinutes(3));

            this.service.editUser(user);

            authenticationResponse.setResult(1);
        } else {
            authenticationResponse.setResult(0);
        }

        return authenticationResponse;
    }


    @PostMapping("/resetPassword")
    public AuthenticationResponse resetPassword(@RequestBody NewPassword newPassword) {
        Optional<User> optionalUser = this.service.findUserByEmail(newPassword.getEmail());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();


        if (optionalUser.isEmpty()) {
            authenticationResponse.setResult(0); // Email introuvable
            return authenticationResponse;
        }

        User user = optionalUser.get();
        LocalDateTime now = LocalDateTime.now();
        if (user.getResetCode()==null || user.getResetCodeExpiration()== null || !user.getResetCode().equals(newPassword.getCode()) || user.getResetCodeExpiration().isBefore(now)) {
            authenticationResponse.setResult(2); // code invalide ou expiré
            return authenticationResponse;
        }

        if(!isStrongPassword(newPassword.getPassword())){
        authenticationResponse.setResult(3); //mdp faible
        return authenticationResponse;
        }
        try {
            user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
            user.setResetCode(null);
            user.setResetCodeExpiration(null);
            service.editUser(user);

            authenticationResponse.setResult(1); // Succès
        } catch (Exception e) {
            authenticationResponse.setResult(4); // Erreur système
        }

        return authenticationResponse;
    }
    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

}

//@PostMapping("/resetPassword")
//public AuthenticationResponse resetPassword(@RequestBody NewPassword newPassword) {
//    Optional<User> optionalUser = this.service.findUserByEmail(newPassword.getEmail());
//    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//
//    if (optionalUser.isPresent()) {
//        User user = optionalUser.get();
//
//        LocalDateTime now = LocalDateTime.now();
//        if (user.getResetCode() != null
//            && user.getResetCode().equals(newPassword.getCode())
//            && user.getResetCodeExpiration() != null
//            && now.isBefore(user.getResetCodeExpiration())) {
//
//            user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
//            user.setResetCode(null);
//            user.setResetCodeExpiration(null);
//            service.editUser(user);
//            authenticationResponse.setResult(1);
//        } else {
//            // code invalide ou expiré
//            authenticationResponse.setResult(0);
//        }
//    } else {
//        authenticationResponse.setResult(0);
//    }
//
//    return authenticationResponse;
//}