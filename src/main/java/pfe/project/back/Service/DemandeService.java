package pfe.project.back.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.project.back.Entity.Demande;
import pfe.project.back.Repo.DemandeRepository;

import java.util.List;

@Service
public class DemandeService {
    @Autowired
    private DemandeRepository demandeRepository;

    public Demande saveDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Demande getDemandeById(Integer id) {
        return demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'ID : " + id));
    }
    }