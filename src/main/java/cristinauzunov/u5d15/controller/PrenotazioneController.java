package cristinauzunov.u5d15.controller;

import cristinauzunov.u5d15.entity.Prenotazione;
import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.service.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione prenota(@PathVariable Long eventoId, @AuthenticationPrincipal Utente utenteLoggato) {
        return prenotazioneService.prenota(eventoId, utenteLoggato);
    }

    @GetMapping("/mie")
    public List<Prenotazione> trovaMie(@AuthenticationPrincipal Utente utenteLoggato) {
        return prenotazioneService.trovaMie(utenteLoggato);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void annulla(@PathVariable Long prenotazioneId, @AuthenticationPrincipal Utente utenteLoggato) {
        prenotazioneService.annulla(prenotazioneId, utenteLoggato);
    }
}