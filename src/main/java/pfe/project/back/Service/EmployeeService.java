package pfe.project.back.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.project.back.Entity.Role;
import pfe.project.back.Entity.User;
import pfe.project.back.Repo.DemandeRepository;
import pfe.project.back.Repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DemandeRepository demandeRepository;

    public List<User> getAllEmployees() {
        return userRepository.findByRole(Role.EMPLOYEE);
    }



    public User createEmployee(User employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(Role.EMPLOYEE);  // Force toujours le role EMPLOYE
        employee.setStatus("ACTIVE");
        return userRepository.save(employee);
    }

    public User updateEmployee(Integer id, User updatedEmployee) {
        User existingEmployee = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // Vérifier le CIN avant modification
        if (userRepository.existsByCin(updatedEmployee.getCin()) &&
                !existingEmployee.getCin().equals(updatedEmployee.getCin())) {
            throw new RuntimeException("CIN déjà utilisé");
        }

        existingEmployee.setFirstname(updatedEmployee.getFirstname());
        existingEmployee.setLastname(updatedEmployee.getLastname());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setCin(updatedEmployee.getCin());

        // Encoder le mot de passe seulement s’il est fourni et différent
        if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
            if (!passwordEncoder.matches(updatedEmployee.getPassword(), existingEmployee.getPassword())) {
                existingEmployee.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
            }
        }

        return userRepository.save(existingEmployee);
    }

    public  List<User> rechercherParCinouEmail(String cin, String email) {
        return userRepository.findByCinOrEmail(cin, email);
   }
}
