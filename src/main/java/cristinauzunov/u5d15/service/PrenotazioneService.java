package cristinauzunov.u5d15.service;

import cristinauzunov.u5d15.entity.Evento;
import cristinauzunov.u5d15.entity.Prenotazione;
import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.repository.PrenotazioneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoService eventoService;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, EventoService eventoService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.eventoService = eventoService;
    }

    public Prenotazione prenota(Long eventoId, Utente utenteLoggato) {
        // recupero l'evento (se non esiste, trovaPerId lancia già l'eccezione)
        Evento evento = eventoService.trovaPerId(eventoId);

        // controllo che ci siano ancora posti disponibili
        if (evento.getPostiDisponibili() <= 0) {
            throw new RuntimeException("Non ci sono più posti disponibili per questo evento");
        }

        // decremento i posti disponibili e salvo l'evento aggiornato
        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoService.salva(evento);

        // creo la prenotazione con la data di oggi
        Prenotazione nuovaPrenotazione = new Prenotazione(utenteLoggato, evento, LocalDate.now());
        return prenotazioneRepository.save(nuovaPrenotazione);
    }

    public List<Prenotazione> trovaMie(Utente utenteLoggato) {
        return prenotazioneRepository.findByUtente(utenteLoggato);
    }

    public void annulla(Long prenotazioneId, Utente utenteLoggato) {
        // recupero la prenotazione
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata con id " + prenotazioneId));

        // controllo che la prenotazione sia dell'utente loggato
        if (!prenotazione.getUtente().getId().equals(utenteLoggato.getId())) {
            throw new RuntimeException("Non puoi annullare una prenotazione che non è tua");
        }

        // ripristino il posto sull'evento
        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);
        eventoService.salva(evento);

        // elimino la prenotazione
        prenotazioneRepository.delete(prenotazione);
    }
}