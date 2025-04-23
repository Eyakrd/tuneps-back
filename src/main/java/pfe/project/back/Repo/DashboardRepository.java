package pfe.project.back.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pfe.project.back.Entity.Dashboard;

import java.awt.*;
import java.util.Optional;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @Query(value = "SELECT * FROM dashboard ORDER BY ID DESC LIMIT 1 ", nativeQuery = true)
    public Dashboard findLastDashboard();





    @Query(value = "SELECT * FROM dashboard WHERE type = :type ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Dashboard findLastDashboardByType(@Param("type") String type);










}
