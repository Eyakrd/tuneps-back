package pfe.project.back.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.project.back.Entity.Demande;
import pfe.project.back.Entity.User;
import pfe.project.back.Repo.UserRepo;
import pfe.project.back.Service.DemandeService;
import pfe.project.back.Service.EmployeeService;
import pfe.project.back.auth.RegisterRequest;

import java.util.List;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DemandeService demandeService;

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
}


