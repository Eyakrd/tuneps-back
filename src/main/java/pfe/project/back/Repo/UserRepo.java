package pfe.project.back.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import pfe.project.back.Entity.Demande;
import pfe.project.back.Entity.Role;
import pfe.project.back.Entity.User;

import java.util.Optional;
import java.util.List;
@EnableJpaRepositories
@Repository
public interface  UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findOneByEmailAndPassword(String email, String password);
    //User findOneByEmail(String email);

    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByCinOrEmail(String cin, String email);

    boolean existsByCin(String cin);


}
