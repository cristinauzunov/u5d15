package cristinauzunov.u5d15.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // chi ha prenotato: tante prenotazioni -> un utente
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    // a quale evento: tante prenotazioni -> un evento
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private LocalDate dataPrenotazione;

    // costruttore vuoto richiesto da JPA
    public Prenotazione() {
    }

    public Prenotazione(Utente utente, Evento evento, LocalDate dataPrenotazione) {
        this.utente = utente;
        this.evento = evento;
        this.dataPrenotazione = dataPrenotazione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }
}