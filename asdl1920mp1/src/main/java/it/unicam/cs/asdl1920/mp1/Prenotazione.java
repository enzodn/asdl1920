package it.unicam.cs.asdl1920.mp1;

/**
 * Una prenotazione riguarda una certa aula per un certo time slot.
 *
 * @author ENZO DI NARDO - enzo.dinardo@studenti.unicam.it
 *
 */
public class Prenotazione implements Comparable<Prenotazione> {

    private final Aula aula;

    private final TimeSlot timeSlot;

    private final String docente;

    private final String motivo;

    /**
     * Costruisce una prenotazione.
     *
     * @param aula     l'aula a cui la prenotazione si riferisce
     * @param timeSlot il time slot della prenotazione
     * @param docente  il nome del docente che ha prenotato l'aula
     * @param motivo   il motivo della prenotazione
     * @throws NullPointerException se uno qualsiasi degli oggetti passati è
     *                              null
     */
    public Prenotazione(Aula aula, TimeSlot timeSlot, String docente,
                        String motivo) {
        if (aula == null || timeSlot == null || docente == null || motivo == null) {
            throw new NullPointerException("Tentativo di prenotazione con aula, timeslot, docente oppure motivo null");
        }
        this.aula = aula;
        this.timeSlot = timeSlot;
        this.docente = docente;
        this.motivo = motivo;
    }

    /**
     * @return the aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * @return the timeSlot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * @return the docente
     */
    public String getDocente() {
        return docente;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }


    /*
     * L'uguaglianza è data solo da stessa aula e stesso time slot. Non sono
     * ammesse prenotazioni diverse con stessa aula e stesso time slot.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prenotazione that = (Prenotazione) o;

        if (aula != null ? !aula.equals(that.aula) : that.aula != null) return false;
        return timeSlot != null ? timeSlot.equals(that.timeSlot) : that.timeSlot == null;
    }

    @Override
    public int hashCode() {
        int result = aula != null ? aula.hashCode() : 0;
        result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
        return result;
    }

    /*
     * Una prenotazione precede un altra in base all'ordine dei time slot. Se
     * due prenotazioni hanno lo stesso time slot allora una precede l'altra in
     * base all'ordine tra le aule.
     */
    @Override
    public int compareTo(Prenotazione o) {
        if (this.timeSlot.compareTo(o.timeSlot) == 0) {
            return this.aula.compareTo(o.aula);
        }
        return this.timeSlot.compareTo(o.timeSlot);
    }

    @Override
    public String toString() {
        return "Prenotazione [aula = " + aula + ", time slot =" + timeSlot
                + ", docente=" + docente + ", motivo=" + motivo + "]";
    }

}
