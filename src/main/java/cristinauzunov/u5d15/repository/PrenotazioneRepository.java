package cristinauzunov.u5d15.repository;

import cristinauzunov.u5d15.entity.Prenotazione;
import cristinauzunov.u5d15.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    List<Prenotazione> findByUtente(Utente utente);
}