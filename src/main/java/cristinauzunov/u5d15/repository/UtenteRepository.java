package cristinauzunov.u5d15.repository;

import cristinauzunov.u5d15.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}