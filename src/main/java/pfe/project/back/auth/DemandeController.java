package pfe.project.back.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pfe.project.back.Entity.Demande;
import pfe.project.back.Service.DemandeService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController


@RequestMapping("/api/demandes")
public class DemandeController {
    @Autowired
    private DemandeService demandeService;


    @PostMapping("/ajouterDemande")
    public Demande ajouterDemande(@RequestParam("typeDec") String typeDec,
                                  @RequestParam("nom") String nom,
                                  @RequestParam("email") String email,
                                  @RequestParam("telephone") String telephone,
                                  @RequestParam("adresse") String adresse,
                                  @RequestParam("statutSocial") String statutSocial,
                                  @RequestParam("texteAppel") String texteAppel,
                                  @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Demande demande = new Demande();
            demande.setTypeDec(typeDec);
            demande.setNom(nom);
            demande.setEmail(email);
            demande.setTelephone(telephone);
            demande.setAdresse(adresse);
            demande.setStatutSocial(statutSocial);
            demande.setTexteAppel(texteAppel);
            if (file != null && !file.isEmpty()) {
                demande.setFile(file.getBytes());
                demande.setFileName(file.getOriginalFilename());
            } else {
                demande.setFile(null); // Aucun fichier
                demande.setFileName(null); // Aucun nom de fichier
            }
        return demandeService.saveDemande(demande);} catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout de la demande.", e);
        }
    }
    @GetMapping("/telechargerFichier/{id}")
    public ResponseEntity<ByteArrayResource> telechargerFichier(@PathVariable Integer id) {
        Demande demande = demandeService.getDemandeById(id);

        if (demande.getFile() != null) {
            ByteArrayResource resource = new ByteArrayResource(demande.getFile());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + demande.getFileName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/liste")
    public List<Demande> obtenirToutesLesDemandes() {
        return demandeService.getAllDemandes();
    }
}