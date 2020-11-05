/**
 *
 */
package it.unicam.cs.asdl1920.mp1;

/**
 * Una Presence Facility è una facility che può essere presente oppure no. Ad
 * esempio la presenza di un proiettore HDMI oppure la presenza dell'aria
 * condizionata.
 *
 * @author ENZO DI NARDO - enzo.dinardo@studenti.unicam.it
 *
 *
 */
public class PresenceFacility extends Facility {

    /**
     * Costruisce una presence facility.
     *
     * @param codice
     * @param descrizione
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla.
     */
    public PresenceFacility(String codice, String descrizione) {
        super(codice, descrizione);
    }

    /*
     * Una Presence Facility soddisfa una facility solo se la facility passata è
     * una Presence Facility ed ha lo stesso codice.
     *
     */
    @Override
    public boolean satisfies(Facility o) {
        if (o == null) {
            throw new NullPointerException("La facility passata è nulla");
        }
        return this.equals(o);
    }

}
