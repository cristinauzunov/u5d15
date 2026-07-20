package cristinauzunov.u5d15.controller;

import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.payloads.LoginDTO;
import cristinauzunov.u5d15.payloads.RegistrazioneDTO;
import cristinauzunov.u5d15.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtenteService utenteService;

    public AuthController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente registra(@RequestBody RegistrazioneDTO body) {
        return utenteService.registra(body);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO body) {
        return utenteService.login(body);
    }
}