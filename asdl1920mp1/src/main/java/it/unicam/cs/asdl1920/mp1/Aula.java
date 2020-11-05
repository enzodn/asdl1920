package it.unicam.cs.asdl1920.mp1;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Un oggetto della classe aula rappresenta una certa aula con le sue facilities
 * e le sue prenotazioni.
 *
 * @author ENZO DI NARDO - enzo.dinardo@studenti.unicam.it
 *
 */
public class Aula implements Comparable<Aula> {
    // Identificativo unico di un'aula
    private final String nome;

    // Location dell'aula
    private final String location;

    // Insieme delle facilities di quest'aula
    private final Set<Facility> facilities;

    // Insieme delle prenotazioni per quest'aula, segue l'ordinamento naturale
    // delle prenotazioni
    private final SortedSet<Prenotazione> prenotazioni;

    /**
     * Costruisce una certa aula con nome e location. Il set delle facilities è
     * vuoto. L'aula non ha inizialmente nessuna prenotazione.
     *
     * @param nome     il nome dell'aula
     * @param location la location dell'aula
     * @throws NullPointerException se una qualsiasi delle informazioni
     *                              richieste è nulla
     */
    public Aula(String nome, String location) {
        if (nome == null || location == null) {
            throw new NullPointerException("Tentativo di creare un'aula senza nome o location");
        }
        this.location = location;
        this.nome = nome;
        this.prenotazioni = new TreeSet<>();
        this.facilities = new HashSet<>();
    }

    /**
     * Costruisce una certa aula con nome, location e insieme delle facilities.
     * L'aula non ha inizialmente  nessuna prenotazione.
     *
     * @param nome       il nome dell'aula
     * @param location   la location dell'aula
     * @param facilities l'insieme delle facilities dell'aula
     * @throws NullPointerException se una qualsiasi delle informazioni
     *                              richieste è nulla
     */
    public Aula(String nome, String location, Set<Facility> facilities) {
        if (nome == null || location == null || facilities == null) {
            throw new NullPointerException("Tentativo di creare un'aula senza nome, location o facilities");
        }
        this.location = location;
        this.nome = nome;
        this.prenotazioni = new TreeSet<>();
        this.facilities = facilities;
    }

    /* Due aule sono uguali se e solo se hanno lo stesso nome */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aula aula = (Aula) o;

        return nome != null ? nome.equals(aula.nome) : aula.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }

    /* L'ordinamento naturale si basa sul nome dell'aula */
    @Override
    public int compareTo(Aula o) {
        return this.nome.compareTo(o.nome);
    }

    /**
     * @return the facilities
     */
    public Set<Facility> getFacilities() {
        return facilities;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the prenotazioni
     */
    public SortedSet<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Aggiunge una faciltity a questa aula.
     *
     * @param f la facility da aggiungere
     * @return true se la facility non era già presente e quindi è stata
     * aggiunta, false altrimenti
     * @throws NullPointerException se la facility passata è nulla
     */
    public boolean addFacility(Facility f) {
        if (f == null) {
            throw new NullPointerException("Tentativo di aggiungere una facility nulla");
        }
        if (this.facilities.contains(f)) return false;
        this.facilities.add(f);
        return true;
    }

    /**
     * Determina se l'aula è libera in un certo time slot.
     *
     * @param ts il time slot da controllare
     * @return true se l'aula risulta libera per tutto il periodo del time slot
     * specificato
     * @throws NullPointerException se il time slot passato è nullo
     */
    public boolean isFree(TimeSlot ts) {
        if (ts == null) {
            throw new NullPointerException("Tentativo di controllare un time slot nullo");
        }
        Iterator<Prenotazione> iter = this.getPrenotazioni().iterator();
        while (iter.hasNext()) {
            Prenotazione p = iter.next();
            if (p.getTimeSlot().overlapsWith(ts)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determina se questa aula soddisfa tutte le facilities richieste
     * rappresentate da un certo insieme dato.
     *
     * @param requestedFacilities l'insieme di facilities richieste da
     *                            soddisfare
     * @return true se e solo se tutte le facilities di
     * {@code requestedFacilities} sono soddisfatte da questa aula.
     * @throws NullPointerException se il set di facility richieste è nullo
     */
    public boolean satisfiesFacilities(Set<Facility> requestedFacilities) {
        if (requestedFacilities == null) {
            throw new NullPointerException("Tentativo di controllare un set di facilities nullo");
        }
        return this.getFacilities().containsAll(requestedFacilities);
    }

    /**
     * Prenota l'aula controllando eventuali sovrapposizioni.
     *
     * @param ts
     * @param docente
     * @param motivo
     * @throws IllegalArgumentException se la prenotazione comporta una
     *                                  sovrapposizione con un'altra
     *                                  prenotazione nella stessa aula.
     * @throws NullPointerException     se una qualsiasi delle informazioni
     *                                  richieste è nulla.
     */
    public void addPrenotazione(TimeSlot ts, String docente, String motivo) {
        if (ts == null || docente == null || motivo == null) {
            throw new NullPointerException("Tentativo di aggiungere una prenotazione senza time slot, docente o motivo");
        }

        // Crea un'iterator e controlla se nell'insieme delle prenotazioni ci sono sovrapposizioni nella stessa aula
        Iterator<Prenotazione> iter = this.getPrenotazioni().iterator();
        while (iter.hasNext()) {
            if (ts.overlapsWith(iter.next().getTimeSlot())) {
                throw new IllegalArgumentException("Tentativo di aggiungere una prenotazione che si sovrappone con un'altra");
            }
        }

        Prenotazione p = new Prenotazione(this, ts, docente, motivo);
        this.getPrenotazioni().add(p);

    }

    /**
     * Cancella una prenotazione di questa aula.
     *
     * @param p la prenotazione da cancellare
     * @return true se la prenotazione è stata cancellata, false se non era
     * presente.
     * @throws NullPointerException se la prenotazione passata è null
     */
    public boolean removePrenotazione(Prenotazione p) {
        if (p == null) {
            throw new NullPointerException("Tentativo di rimuovere una prenotazione nulla");
        }
        return this.getPrenotazioni().remove(p);
    }

    /**
     * Rimuove tutte le prenotazioni di questa aula che iniziano prima (o
     * esattamente in) di un punto nel tempo specificato.
     *
     * @param timePoint un certo punto nel tempo
     * @return true se almeno una prenotazione è stata cancellata, false
     * altrimenti.
     * @throws NullPointerException se il punto nel tempo passato è nullo.
     */
    public boolean removePrenotazioniBefore(GregorianCalendar timePoint) {
        if (timePoint == null) {
            throw new NullPointerException("Tentativo di rimuovere prenotazioni prima di un time slot nullo");
        }
        Iterator<Prenotazione> iter = this.prenotazioni.iterator();
        boolean esitoRemove = false;
        while (iter.hasNext() &&
                !iter.next().getTimeSlot().getStart().after(timePoint)
        ) {     iter.remove();
                esitoRemove = true;
            }
        return esitoRemove;
    }

}
