package pfe.project.back.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.project.back.Entity.Dashboard;
import pfe.project.back.Repo.DashboardRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    public List<Dashboard> getAllDashboards() {
        return dashboardRepository.findAll();
    }

    public Dashboard addDashboard(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);

    }
    public
    Dashboard getLastDashboard() {
        return dashboardRepository.findLastDashboard();
    }


    public Dashboard getDashboardsByType(String type) {
        return dashboardRepository.findLastDashboardByType(type);
    }


    public void updateDashboardStatus(Long id, String status) {
        if (!"active".equals(status) && !"inactive".equals(status)) {
            throw new IllegalArgumentException("Status must be 'active' or 'inactive'");
        }

        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dashboard not found"));

        dashboard.setStatus(status); // Cette ligne manquait !
        dashboardRepository.save(dashboard);
    }



}