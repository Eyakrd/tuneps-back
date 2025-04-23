package pfe.project.back.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pfe.project.back.Entity.Dashboard;
import pfe.project.back.Repo.DashboardRepository;
import pfe.project.back.Service.DashboardService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboards")
@CrossOrigin
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private DashboardRepository dashboardRepository;

    @GetMapping("/allDashbords")
    public List<Dashboard> getAllDashboards() {
        return dashboardService.getAllDashboards();
    }

    @PostMapping
    public Dashboard addDashboard(@Valid @RequestBody Dashboard dash) {
        return dashboardService.addDashboard(dash);
    }
    @GetMapping("last")
    public Dashboard getLastDashboard() {
        return dashboardService.getLastDashboard();
    }

    @GetMapping("/type/{type}")
    public Dashboard getDashboardsByType(@PathVariable String type) {
        return dashboardService.getDashboardsByType(type);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().body("Le champ 'status' est requis");
        }

        try {
            dashboardService.updateDashboardStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}