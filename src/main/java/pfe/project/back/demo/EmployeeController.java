package pfe.project.back.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pfe.project.back.Entity.Demande;
import pfe.project.back.Entity.User;
import pfe.project.back.Repo.UserRepo;
import pfe.project.back.Service.DemandeService;
import pfe.project.back.Service.EmployeeService;
import pfe.project.back.auth.RegisterRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public ResponseEntity<List<User>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping("/addemployee")
    public ResponseEntity<User> createEmployee(@RequestBody User employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateEmployee(@PathVariable Integer id, @RequestBody User employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }
    @GetMapping("/rechercher")
    public ResponseEntity<List<User>> rechercherEmployeeparCinOuEmail(@RequestParam String cin,@RequestParam String email) {
        List<User> result= employeeService.rechercherParCinouEmail(cin, email);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/user/{id}/status")
    public ResponseEntity<Map<String, String>> updateUserStatus(@PathVariable Integer id, @RequestParam String status) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        String upperStatus = status.toUpperCase();
        if (!upperStatus.equals("ACTIVE") && !upperStatus.equals("INACTIVE")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status invalide (doit être ACTIVE ou INACTIVE)");
        }

        user.setStatus(upperStatus);
        userRepo.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Statut mis à jour avec succès");

        return ResponseEntity.ok(response);
    }


}


