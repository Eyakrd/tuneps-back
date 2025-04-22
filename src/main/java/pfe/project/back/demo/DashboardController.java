package pfe.project.back.demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pfe.project.back.Entity.Dashboard;
import pfe.project.back.Repo.DashboardRepository;
import pfe.project.back.Service.DashboardService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
@CrossOrigin
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

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
}