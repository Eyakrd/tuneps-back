package pfe.project.back.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.project.back.Entity.Demande;
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer> {
}
