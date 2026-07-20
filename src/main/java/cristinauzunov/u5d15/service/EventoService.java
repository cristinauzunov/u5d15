package cristinauzunov.u5d15.service;

import cristinauzunov.u5d15.entity.Evento;
import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.exceptions.NotFoundException;
import cristinauzunov.u5d15.exceptions.UnauthorizedException;
import cristinauzunov.u5d15.payloads.EventoDTO;
import cristinauzunov.u5d15.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento crea(EventoDTO body, Utente organizzatore) {
        Evento nuovoEvento = new Evento(
                body.getTitolo(),
                body.getDescrizione(),
                body.getData(),
                body.getLuogo(),
                body.getPostiDisponibili(),
                organizzatore
        );
        return eventoRepository.save(nuovoEvento);
    }

    public List<Evento> trovaTutti() {
        return eventoRepository.findAll();
    }

    public Evento trovaPerId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento non trovato con id " + id));
    }

    public Evento aggiorna(Long id, EventoDTO body, Utente utenteLoggato) {
        Evento evento = this.trovaPerId(id);

        if (!evento.getOrganizzatore().getId().equals(utenteLoggato.getId())) {
            throw new UnauthorizedException("Non puoi modificare un evento che non hai creato");
        }

        evento.setTitolo(body.getTitolo());
        evento.setDescrizione(body.getDescrizione());
        evento.setData(body.getData());
        evento.setLuogo(body.getLuogo());
        evento.setPostiDisponibili(body.getPostiDisponibili());

        return eventoRepository.save(evento);
    }

    public void elimina(Long id, Utente utenteLoggato) {
        Evento evento = this.trovaPerId(id);

        if (!evento.getOrganizzatore().getId().equals(utenteLoggato.getId())) {
            throw new UnauthorizedException("Non puoi eliminare un evento che non hai creato");
        }

        eventoRepository.delete(evento);
    }

    public Evento salva(Evento evento) {
        return eventoRepository.save(evento);
    }
}