package cristinauzunov.u5d15.service;

import cristinauzunov.u5d15.entity.Utente;
import cristinauzunov.u5d15.enums.Ruolo;
import cristinauzunov.u5d15.payloads.LoginDTO;
import cristinauzunov.u5d15.payloads.RegistrazioneDTO;
import cristinauzunov.u5d15.repository.UtenteRepository;
import cristinauzunov.u5d15.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTTools jwtTools;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder, JWTTools jwtTools) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTools = jwtTools;
    }

    public Utente registra(RegistrazioneDTO body) {
        // controllo che username ed email non siano già usati
        if (utenteRepository.existsByUsername(body.getUsername())) {
            throw new RuntimeException("Username già in uso");
        }
        if (utenteRepository.existsByEmail(body.getEmail())) {
            throw new RuntimeException("Email già in uso");
        }

        // cripto la password con BCrypt
        String passwordCriptata = passwordEncoder.encode(body.getPassword());

        // creo l'utente, di default con ruolo UTENTE_NORMALE
        Utente nuovoUtente = new Utente(
                body.getNome(),
                body.getCognome(),
                body.getUsername(),
                body.getEmail(),
                passwordCriptata,
                Ruolo.UTENTE_NORMALE
        );

        return utenteRepository.save(nuovoUtente);
    }

    public String login(LoginDTO body) {
        // cerco l'utente per username
        Utente utente = utenteRepository.findByUsername(body.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenziali errate"));

        // confronto la password ricevuta con quella criptata nel database
        if (!passwordEncoder.matches(body.getPassword(), utente.getPassword())) {
            throw new RuntimeException("Credenziali errate");
        }

        // se le credenziali sono giuste, genero e restituisco il token
        return jwtTools.generaToken(utente);
    }

    public Utente trovaPerId(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }
}