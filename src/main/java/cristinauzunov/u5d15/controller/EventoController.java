package cristinauzunov.u5d15.controller;

import cristinauzunov.u5d15.entity.Evento;
import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.payloads.EventoDTO;
import cristinauzunov.u5d15.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public Evento crea(@RequestBody EventoDTO body, @AuthenticationPrincipal Utente utenteLoggato) {
        return eventoService.crea(body, utenteLoggato);
    }

    @GetMapping
    public List<Evento> trovaTutti() {
        return eventoService.trovaTutti();
    }

    @GetMapping("/{id}")
    public Evento trovaPerId(@PathVariable Long id) {
        return eventoService.trovaPerId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public Evento aggiorna(@PathVariable Long id, @RequestBody EventoDTO body, @AuthenticationPrincipal Utente utenteLoggato) {
        return eventoService.aggiorna(id, body, utenteLoggato);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public void elimina(@PathVariable Long id, @AuthenticationPrincipal Utente utenteLoggato) {
        eventoService.elimina(id, utenteLoggato);
    }
}