package cristinauzunov.u5d15.exceptions;

import java.time.LocalDateTime;

public class MessaggioErrore {

    private String messaggio;
    private LocalDateTime dataOra;
    private int codice;

    public MessaggioErrore(String messaggio, LocalDateTime dataOra, int codice) {
        this.messaggio = messaggio;
        this.dataOra = dataOra;
        this.codice = codice;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }
}