/**
 *
 */
package it.unicam.cs.asdl1920.mp1;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Un time slot è un intervallo di tempo continuo che può essere associato ad
 * una prenotazione o a una facility. Gli oggetti della classe sono immutabili.
 * Non sono ammessi time slot che iniziano e finiscono nello stesso istante.
 *
 * @author ENZO DI NARDO - enzo.dinardo@studenti.unicam.it
 *
 *
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /**
     * Rappresenta la soglia di tolleranza da considerare nella sovrapposizione
     * di due Time Slot. Se si sovrappongono per un numero di minuti minore o
     * uguale a questa soglia allora NON vengono considerati sovrapposti.
     */
    public static final int MINUTES_OF_TOLERANCE_FOR_OVERLAPPING = 5;

    private final GregorianCalendar start;

    private final GregorianCalendar stop;

    /**
     * Crea un time slot tra due istanti di inizio e fine
     *
     * @param start
     *                  inizio del time slot
     * @param stop
     *                  fine del time slot
     * @throws NullPointerException
     *                                      se uno dei due istanti, start o
     *                                      stop, è null
     * @throws IllegalArgumentException
     *                                      se start è uguale o successivo a
     *                                      stop
     */
    public TimeSlot(GregorianCalendar start, GregorianCalendar stop) {
        if (start == null || stop == null) {
            throw new NullPointerException("Tentativo di creare un TimeSlot senza start o senza stop");
        }
        if (start.compareTo(stop) >= 0) {
            throw new IllegalArgumentException("Tentativo di creare un TimeSlot con start uguale o successivo a stop");
        }
        this.start = start;
        this.stop = stop;
    }

    /**
     * @return the start
     */
    public GregorianCalendar getStart() {
        return start;
    }

    /**
     * @return the stop
     */
    public GregorianCalendar getStop() {
        return stop;
    }


    /*
     * Un time slot è uguale a un altro se rappresenta esattamente lo stesso
     * intervallo di tempo, cioè se inizia nello stesso istante e termina nello
     * stesso istante.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlot timeSlot = (TimeSlot) o;

        if (start != null ? !start.equals(timeSlot.start) : timeSlot.start != null) return false;
        return stop != null ? stop.equals(timeSlot.stop) : timeSlot.stop == null;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (stop != null ? stop.hashCode() : 0);
        return result;
    }

    /*
     * Un time slot precede un altro se inizia prima. Se due time slot iniziano
     * nello stesso momento quello che finisce prima precede l'altro. Se hanno
     * stesso inizio e stessa fine sono uguali, in compatibilità con equals.
     */
    @Override
    public int compareTo(TimeSlot o) {
        int valueStart = this.start.compareTo(o.start);
        int valueStop = this.stop.compareTo(o.start);

        if (valueStart == 0 && valueStop < 0) return -1;
        if (this.equals(o)) return 0;
        return valueStart;
    }

    /**
     * Determina se questo time slot si sovrappone a un altro time slot dato,
     * considerando la soglia di tolleranza.
     *
     * @param o
     *              il time slot che viene passato per il controllo di
     *              sovrapposizione
     * @return true se questo time slot si sovrappone per più di
     *         MINUTES_OF_TOLERANCE_FOR_OVERLAPPING minuti a quello passato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean overlapsWith(TimeSlot o) {
        if (o == null) {
            throw new NullPointerException("Tentativo di verificare una sovrapposizione con un timeslot nullo");
        }
        long thisStart = this.start.getTimeInMillis();
        long thisStop = this.stop.getTimeInMillis();
        long oStart = o.start.getTimeInMillis();
        long oStop = o.stop.getTimeInMillis();
        long tolerance = MINUTES_OF_TOLERANCE_FOR_OVERLAPPING * 1000 * 60;

        // False se uno dei due timeslot non è compreso interamente nell'altro
        // oppure è compreso per un tempo minore del tempo di tolleranza
        if (thisStart < oStart && oStop - oStart <= tolerance) return false;
        else if (thisStop - thisStart <= tolerance) return false;

        return (oStop > thisStart && (oStop - thisStart) > tolerance)
                && (thisStop > oStart && (thisStop - oStart) > tolerance);
    }

    /*
     * Esempio di stringa: [4/11/2019 11.0 - 4/11/2019 13.0] Esempio di stringa:
     * [10/11/2019 11.15 - 10/11/2019 23.45]
     */
    @Override
    public String toString() {
        return "[" +
                this.start.get(Calendar.DAY_OF_MONTH) + "/" +
                (this.start.get(Calendar.MONTH) + 1) + "/" +
                this.start.get(Calendar.YEAR) + " " +
                this.start.get(Calendar.HOUR_OF_DAY) + "." +
                this.start.get(Calendar.MINUTE) +
                " - " +
                this.stop.get(Calendar.DAY_OF_MONTH) + "/" +
                (this.stop.get(Calendar.MONTH) + 1) + "/" +
                this.stop.get(Calendar.YEAR) + " " +
                this.stop.get(Calendar.HOUR_OF_DAY) + "." +
                this.stop.get(Calendar.MINUTE) +
                "]";
    }

}
