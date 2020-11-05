package it.unicam.cs.asdl1920.mp2;

import java.util.ArrayList;
import java.util.List;


/**
 * Un RBTree, un albero rosso-nero, è un albero binario di ricerca con le
 * seguenti proprietà:
 * 
 * 1- Ciascun nodo è rosso o nero e la radice è sempre nera
 * 
 * 2- Ciascuna foglia NIL (cioè una foglia che non contiene una etichetta di
 * tipo E) è nera
 * 
 * 3- Se un nodo è rosso allora entrambi i figli sono neri
 * 
 * 4- Ogni cammino da un nodo ad una foglia sua discendente contiene lo stesso
 * numero di nodi neri (contando anche le foglie NIL)
 * 
 * Si può dimostrare che le operazioni di inserimento, ricerca e rimozione di un
 * valore in un RBTree hanno un costo O(lg n), dove n è il numeo di nodi
 * dell'albero. Ciò è dovuto al fatto che la cosiddetta altezza nera (cioè il
 * numero dei nodi neri incontrati in un cammino) viene mantenuta uguale per
 * tutti i cammini dalla radice alle foglie.
 * 
 * Per maggiori dettagli si veda il Cap. 13 di T.H. Cormen, C.E. Leiserson, R.L.
 * Rivest, C. Stein, Introduzione agli Algoritmi e Strutture Dati (terza
 * edizione), McGraw-Hill, 2010 -
 * https://www.mheducation.it/9788838665158-italy-introduzione-agli-algoritmi-e-strutture-dati-3ed
 * 
 * In questa implementazione degli RBTree è possibile inserire elementi
 * duplicati, ma non è possibile inserire elementi null.
 * 
 * @author ENZO DI NARDO - enzo.dinardo@studenti.unicam.it
 *
 * @param <E>
 *                il tipo delle etichette dei nodi dell'albero. La classe E deve
 *                avere un ordinamento naturale implementato tramite il metodo
 *                compareTo. Tale ordinamento è quello usato nell'RBTree per
 *                confrontare le etichette dei nodi.
 * 
 * 
 */
public class RBTree<E extends Comparable<E>> {
    /*
     * Costanti e metodi static.
     */

    /*
     * E' un flag che rappresenta il colore rosso dei nodi negli RBTree.
     */
    protected static final boolean RED = true;

    /*
     * E' un flag che rappresenta il colore rosso dei nodi negli RBTree.
     */
    protected static final boolean BLACK = false;

    /**
     * Determina se un nodo di un albero RBTree è rosso.
     * 
     * @param x
     *              un nodo di un albero RBTree
     * 
     * @return true se il nodo passato è colorato di rosso, false altrimenti
     */
    protected static boolean isRed(
            @SuppressWarnings("rawtypes") RBTree.RBTreeNode x) {
        if (isNil(x))
            return false;
        return x.color == RED;
    }

    /**
     * Determina se un nodo di un albero RBTree è nero.
     * 
     * @param x
     *              un nodo di un albero RBTree
     * 
     * @return true se il nodo passato è colorato di nero oppure se è una foglia
     *         NIL (che è sempre considerata un nodo nero in un RBTree), false
     *         altrimenti
     */
    protected static boolean isBlack(
            @SuppressWarnings("rawtypes") RBTree.RBTreeNode x) {
        if (isNil(x))
            return true;
        return x.color == BLACK;
    }

    /**
     * Determina se un certo nodo è una foglia NIL.
     * 
     * E' possibile rappresentare le foglie NIL sia con il valore null sia con
     * un particolare nodo "sentinella". Questa API è indipendente dalla
     * particolare scelta implementativa poiché fa riferimento al concetto
     * astratto di foglia NIL negli alberi rosso-neri.
     * 
     * @param n
     *              un puntatore a un nodo di un RBTRee
     * 
     * @return true se il puntatore passato punta a una foglia NIL.
     */
    protected static boolean isNil(
            @SuppressWarnings("rawtypes") RBTree.RBTreeNode n) {
        // la sentinella è l'unico oggetto nodo che ha elemento uguale a null
        return (n != null && n.el == null);
    }

    /*
     * Variabili istanza e metodi non static.
     */

    /*
     * Il nodo radice di questo albero. Se null, allora l'albero è vuoto.
     */
    private RBTreeNode root;

    /*
     * Il numero di elementi in questo RBTree, diverso dal numero di nodi poiché
     * un elemento può essere inserito più di una volta e ciò non incrementa il
     * numero di nodi, ma solo un contatore di molteplicità nel nodo che
     * contiene l'elemento ripetuto.
     */
    private int size;

    /*
     * Il numero di nodi in questo RBTree.
     */
    private int numberOfNodes;

    /*
     * oggetto nodo sentinella, usa un costruttore particolare da aggiungere alla classe interna
     */
    private RBTreeNode sentinella = new RBTreeNode();

    /**
     * Costruisce un RBTree vuoto.
     */
    public RBTree() {
        this.root = sentinella;
        this.size = 0;
        this.numberOfNodes = 0;
    }

    /**
     * Costruisce un RBTree che consiste solo di un nodo radice.
     * 
     * @param rootElement
     *                        l'informazione associata al nodo radice
     * @throws NullPointerException
     *                                  se l'elemento passato è null
     */
    public RBTree(E element) {
        if (element == null) {
            throw new NullPointerException("L'elemento passato è null");
        }
        this.root = new RBTreeNode(element);
        this.size = 1;
        this.numberOfNodes = root.getCount();
        this.root.color = BLACK;
    }

    /**
     * Determina se questo RBTree contiene un certo elemento.
     * 
     * @param el
     *               l'elemento da cercare
     * @return true se l'elemento è presente in questo RBTree, false altrimenti.
     * @throws NullPointerException
     *                                  se l'elemento {E el} è null
     */
    public boolean contains(E el) {
        if (el == null) {
            throw new NullPointerException("L'elemento passato è null");
        }
        return this.root.search(el)!=null;
    }

    /**
     * Restituisce il nodo che è etichettato con un certo elemento dato.
     * 
     * @param el
     *               l'elemento che etichetta il nodo cercato
     * 
     * @return il puntatore al nodo che è etichettato con l'elemento dato,
     *         oppure null se nell'albero non c'è nessun nodo etichettato con
     *         quell'elemento
     * 
     * @throws NullPointerException
     *                                  se l'elemento passato è null
     * 
     */
    protected RBTreeNode getNodeOf(E el) {
        if (el == null) {
            throw new NullPointerException("L'elemento passato è null");
        }
        return root.search(el);
    }

    /**
     * Determina il numero di occorrenze di un certo elemento in questo RBTree.
     * 
     * @param el
     *               l'elemento di cui determinare il numero di occorrenze
     * @return il numero di occorrenze dell'elemento {E el} in questo RBTree,
     *         zero se non è presente.
     * @throws NullPointerException
     *                                  se l'elemento {E el} è null
     */
    public int getCount(E el) {
        if(el == null){
            throw new NullPointerException("L'elemento passato è null");
        }
        RBTreeNode elNode = root.search(el);
        if(elNode==null){
            return 0;
        } else return elNode.getCount();
    }

    /**
     * Restituisce l'altezza nera di questo RBTree, cioè il numero di nodi
     * colorati di nero in un qualsiasi cammino dalla radice a una foglia NIL.
     * Se questo RBTree è vuoto viene restituito il valore -1. L'altezza nera è
     * sempre maggiore o uguale di 1 in un albero non vuoto.
     * 
     * @return l'altezza di questo RBTree, -1 se questo RBTree è vuoto.
     */
    public int getBlackHeight() {
        if(this.isEmpty()){
            return -1;
        }
        return root.getBlackHeight();
    }

    /**
     * Restituisce l'elemento minimo presente in questo RBTree.
     * 
     * @return l'elemento minimo in questo RBTree
     * @return null se questo RBTree è vuoto.
     */
    public E getMinimum() {
        if(this.isEmpty()){
            return null;
        }
        return root.getMinimum().el;
    }

    /**
     * Restituisce l'elemento massimo presente in questo RBTree.
     * 
     * @return l'elemento massimo in questo RBTree
     * @return null se questo RBTree è vuoto.
     */
    public E getMaximum() {
        if(this.isEmpty()){
            return null;
        }
        return root.getMaximum().el;
    }

    /**
     * Restituisce il numero di nodi in questo RBTree.
     * 
     * @return il numero di nodi in questo RBTree.
     */
    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }

    /**
     * Restituisce l'elemento <b>strettamente</b> predecessore, in questo
     * RBTree, di un dato elemento. Si richiede che l'elemento passato sia
     * presente nell'albero.
     * 
     * @param el
     *               l'elemento di cui si chiede il predecessore
     * @return l'elemento <b>strettamente</b> predecessore rispetto
     *         all'ordinamento naturale della classe {@code E}, di {@code el} in
     *         questo RBTree, oppure {@code null} se {@code el} è l'elemento
     *         minimo.
     * @throws NullPointerException
     *                                      se l'elemento {@code el} è null
     * @throws IllegalArgumentException
     *                                      se l'elemento {@code el} non è
     *                                      presente in questo RBTree.
     */
    public E getPredecessor(E el) {
        if(el==null){
            throw new NullPointerException("L'elemento passato è null");
        }
        if(!this.contains(el)){
            throw new IllegalArgumentException("L'elemento non è presento in questo RBTree");
        }
        return root.search(el).getPredecessor().getEl();
    }

    /**
     * @return the root
     */
    protected RBTreeNode getRoot() {
        return this.root;
    }

    /**
     * Restituisce il numero di elementi contenuti in questo RBTree. In caso di
     * elementi ripetuti essi vengono contati più volte.
     * 
     * @return il numero di elementi di tipo {@code E} presenti in questo
     *         RBTree, zero se non è presente nessun elemento.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Restituisce l'elemento <b>strettamente</b> successore, in questo RBTree,
     * di un dato elemento. Si richiede che l'elemento passato sia presente
     * nell'albero.
     * 
     * @param el
     *               l'elemento di cui si chiede il successore
     * @return l'elemento <b>strettamente</b> successore, rispetto
     *         all'ordinamento naturale della classe {@code E}, di {@code el} in
     *         questo RBTree, oppure {@code null} se {@code el} è l'elemento
     *         massimo.
     * @throws NullPointerException
     *                                      se l'elemento {@code el} è null
     * @throws IllegalArgumentException
     *                                      se l'elemento {@code el} non è
     *                                      presente in questo RBTree.
     */
    public E getSuccessor(E el) {
        if(el==null){
            throw new NullPointerException("L'elemento passato è null");
        }
        if(!this.contains(el)){
            throw new IllegalArgumentException("L'elemento non è presente in questo RBTree");
        }
        return root.search(el).getSuccessor().getEl();
    }

    /**
     * Restituisce la lista degli elementi contenuti in questo RBTree secondo
     * l'ordine determinato dalla visita in-order. Per le proprietà degli alberi
     * binari di ricerca la lista ottenuta conterrà gli elementi in ordine
     * crescente rispetto all'ordinamento naturale della classe {@code E}. Nel
     * caso di elementi ripetuti, essi appaiono più volte nella lista
     * consecutivamente.
     * 
     * @return la lista ordinata degli elementi contenuti in questo RBTree,
     *         tenendo conto della loro molteplicità.
     */
    public List<E> inOrderVisit() {
        List<E> inOrderList = new ArrayList<>();
        root.inOrderVisit(inOrderList);
        return  inOrderList;
    }

    /**
     * Determina se questo RBTree è vuoto.
     * 
     * @return true se questo RBTree è vuoto, false altrimenti.
     */
    public boolean isEmpty() {
        return isNil(this.root);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String descr = "RBTree [root=" + root.el.toString() + ", size=" + size
                + ", numberOfNodes=" + numberOfNodes + "]\n";
        return descr + this.root.toString();
    }

    /**
     * Inserisce un nuovo elemento in questo RBTree. Se l'elemento è già
     * presente viene incrementato il suo numero di occorrenze.
     * 
     * @param el
     *               l'elemento da inserire.
     * @return il numero di confronti tra elementi della classe {@code E}
     *         effettuati durante l'inserimento (cioè il numero di chiamate al
     *         metodo compareTo della classe E)
     * @throws NullPointerException
     *                                  se l'elemento {@code el} è null
     */
    public int insert(E el) {
        if (el == null){
            throw new NullPointerException("L'elemento passato è null");
        }
        int cmpCount =  root.insert(el);
        root.color = BLACK;
        return cmpCount;
    }

    /**
     * Cancella da questo RBTree una occorrenza di un certo elemento.
     * 
     * @param element
     *                    l'elemento da cancellare
     * @return true, se l'elemento è stato cancellato, false se non era presente
     *         o l'albero è vuoto.
     * @throws NullPointerException
     *                                  se l'elemento passato è nullo
     */
    public boolean remove(E el) {
        if(el==null){
            throw new NullPointerException("L'elemento passato è nullo");
        }
        return root.remove(el);
    }

    /**
     * Classe interna per i nodi di un RBTree. La classe definisce i nodi
     * ricorsivamente, cioè un nodo contiene puntatori a nodi della stessa
     * classe. Le operazioni esposte come public nelle API della classe
     * principale RBTree<E> sono "duplicate" nei nodi e tipicamente il metodo
     * pubblico della classe principale fa dei controlli e poi, se è il caso,
     * chiama il metodo "gemello" sul nodo radice dell'albero.
     */
    protected class RBTreeNode {

        /*
         * Etichetta del nodo. ATTENZIONE: non può cambiare! Nel caso di
         * removeFixUp, bisogna utilizzare un metodo opportuno per sostituire il
         * nodo da rimuovere con il suo successore. Infatti non è possibole
         * cambiare l'etichetta associata al nodo da rimuovere. Attenzione al
         * caso di sostituzione della radice e al caso di sostituzione di un
         * nodo sul suo genitore!
         */
        protected final E el;

        // sottoalbero sinistro
        protected RBTreeNode left;

        // sottoalbero destro
        protected RBTreeNode right;

        // genitore del nodo, null se questo nodo è la radice del BRTree
        protected RBTreeNode parent;

        // colore del nodo
        protected boolean color;

        // conta il numero di occorrenze dell'elemento (molteplicità)
        protected int count;

        /**
         * Crea un nodo di un RBTree con l'etichetta passata. Il nodo creato non
         * ha nessun collegamento con altri nodi, ha molteplicità pari a 1 e
         * colore rosso.
         *
         * @param el l'etichetta da associare al nodo
         * @throws NullPointerException se l'etichetta passata è null
         */
        protected RBTreeNode(E el) {
            if (el == null) {
                throw new NullPointerException();
            }
            this.el = el;
            this.left = sentinella;
            this.right = sentinella;
            this.parent = sentinella;
            this.color = RED;
            this.count = 1;
        }

        /* Costruttore che costruisce la sentinella */
        protected RBTreeNode() {
            this.el = null;
        }

        /**
         * Metodo ricorsivo che implementa la visita in-order a partire dal nodo
         * this.
         *
         * @param r la lista a cui aggiungere gli elementi visitati in-order
         */
        protected void inOrderVisit(List<E> r) {
            if (this.left != null) {
                this.left.inOrderVisit(r);
            }
            // Aggiungo il totale degli elementi
            for(int i=0; i<count; i++){
                r.add(this.el);
            }
            if (this.right != null) {
                this.right.inOrderVisit(r);
            }
        }

        /**
         * Trova il nodo dell'albero che contiene l'elemento successore di
         * quello contenuto in questo nodo. Il metodo assume che il nodo con un
         * elemento successore esista nell'albero. Ciò deve essere controllato
         * dal metodo getSuccessor() della classe principale.
         *
         * @return un puntatore al nodo che contiene l'elemento successore a
         * quello contenuto in questo nodo, secondo l'ordinamento
         * naturale della classe E.
         */
        protected RBTreeNode getSuccessor() {
            RBTreeNode currentNode = this;
            // Se currentNode ha un figlio destro il successore è nodo più a sinistra del figlio destro
            if (!isNil(currentNode.right)) {
                return currentNode.right.getMinimum();
            }
            // Si risale l'albero fino a trovare il nodo che è figlio sinistro del suo genitore
            // Il genitore è il successore cercato
            RBTreeNode currentNodeParent = currentNode.parent;
            while (!isNil(currentNodeParent) && currentNode.equals(currentNodeParent.right)) {
                currentNode = currentNodeParent;
                currentNodeParent = currentNodeParent.parent;
            }
            return currentNodeParent;
        }

        /**
         * Trova il nodo dell'albero che contiene l'elemento predecessore di
         * quello contenuto in questo nodo. Il metodo assume che il nodo con un
         * elemento predecessore esista nell'albero. Ciò deve essere controllato
         * dal metodo getPredecessor() della classe principale.
         *
         * @return un puntatore al nodo che contiene l'elemento predecessore a
         * quello contenuto in questo nodo, secondo l'ordinamento
         * naturale della classe E.
         */
        protected RBTreeNode getPredecessor() {
            RBTreeNode currentNode = this;
            // Se currentNode ha un figlio sinistro il successore è nodo più a destra del figlio sinistro
            if (!isNil(currentNode.left)) {
                return currentNode.left.getMaximum();
            }
            // Si risale l'albero fino a trovare il nodo che è figlio destro del suo genitore
            // Il genitore è il successore cercato
            RBTreeNode currentNodeParent = this.parent;
            while (!isNil(currentNodeParent) && currentNode.equals(currentNodeParent.left)) {
                currentNode = currentNodeParent;
                currentNodeParent = currentNodeParent.parent;
            }
            return currentNodeParent;
        }

        /**
         * Cerca un elemento nel (sotto)albero di cui questo nodo è radice.
         *
         * @param el l'elemento da cercare
         * @return il puntatore al nodo che contiene l'elemento cercato oppure
         * null se l'elemento non esiste nel (sotto)albero di cui questo
         * nodo è la radice.
         */
        protected RBTreeNode search(E el) {
            RBTreeNode currentNode = this;
            // Continuo il ciclo fino alla sentinella, vado a sinistra o a destra a seconda del nodo
            // Se arrivo alla sentinella senza averlo trovato significa che non è presente
            while (!isNil(currentNode)) {
                int cmp = el.compareTo(currentNode.el);
                if (cmp < 0) {
                    currentNode = currentNode.left;
                } else if (cmp > 0) {
                    currentNode = currentNode.right;
                } else return currentNode;
            }
            return null;
        }

        /**
         * Restituisce il nodo con elemento minimo nel (sotto)albero di cui
         * questo nodo è radice.
         *
         * @return il nodo con elemento minimo nel (sotto)albero di cui questo
         * nodo è radice
         */
        protected RBTreeNode getMinimum() {
            RBTreeNode currentNode = this;
            // Continuo ad andare a sinistra fino a quando trovo la foglia
            while (!isNil(currentNode.left)) {
                currentNode = currentNode.left;
            }
            return currentNode;
        }

        /**
         * Restituisce il nodo con elemento massimo nel (sotto)albero di cui
         * questo nodo è radice.
         *
         * @return il nodo con elemento massimo nel (sotto)albero di cui questo
         * nodo è radice
         */
        protected RBTreeNode getMaximum() {
            RBTreeNode currentNode = this;
            // Continuo ad andare a destra fino a quando trovo la foglia
            while (!isNil(currentNode.right)) {
                currentNode = currentNode.right;
            }
            return currentNode;
        }

        /**
         * @return the el
         */
        protected E getEl() {
            return el;
        }

        /**
         * @return the left
         */
        protected RBTreeNode getLeft() {
            return left;
        }

        /**
         * @return the right
         */
        protected RBTreeNode getRight() {
            return right;
        }

        /**
         * @return the parent
         */
        protected RBTreeNode getParent() {
            return parent;
        }

        /**
         * @return the color
         */
        protected boolean getColor() {
            return color;
        }

        /**
         * @return the count
         */
        protected int getCount() {
            return count;
        }

        /**
         * Determina se questo nodo è attualmente colorato di rosso.
         *
         * @return true se questo nodo è attualmente colorato di rosso, false
         * altrimenti
         */
        protected boolean isRed() {

            return this.color == RED;
        }

        /**
         * Determina se questo nodo è attualmente colorato di nero.
         *
         * @return true se questo nodo è attualmente colorato di nero, false
         * altrimenti
         */
        protected boolean isBlack() {

            return this.color == BLACK;
        }

        /**
         * Determina l'altezza nera di questo nodo, cioè il numero di nodi neri
         * che si incontrano in un qualsiasi cammino da qui ad una foglia NIL.
         * Non viene contata la colorazione di questo nodo.
         *
         * @return l'altezza nera di questo nodo
         */
        protected int getBlackHeight() {
            int bh = 0;
            RBTreeNode currentNode = this;
            while (!isNil(currentNode)) {
                if (currentNode.isBlack()) {
                    bh++;
                }
                currentNode = currentNode.left;
            }
            return bh;
        }

        /**
         * Inserisce un elemento nel (sotto)albero di cui questo nodo è radice.
         * Se l'elemento è già presente viene semplicemente incrementata la sua
         * molteplicità.
         *
         * @param el l'elemento da inserire
         * @return il numero di operazioni di comparazione (chiamate al metodo
         * compareTo della classe E) effettuate per l'inserimento
         * dell'elemento.
         */
        protected int insert(E el) {
            RBTreeNode y = sentinella;
            RBTreeNode x = this;
            RBTreeNode z = new RBTreeNode(el);
            int countCmp = 0;
            // Caso in cui la radice è una sentinella, quindi l'albero è vuoto
            if (isNil(x)) {
                root = z;
                z.parent = y;
                numberOfNodes++;
                size++;
            }
            // L'albero ha almeno un elemento
            else {
                while (!isNil(x)) {
                    y = x;
                    int cmp = el.compareTo(x.el);
                    countCmp++;
                    // Se l'elemento da inserire è minore dell'elemento del nodo in cui mi trovo
                    // continuo verso sinistra e aumento il contatore delle comparazioni
                    if (cmp < 0) {
                        x = x.left;
                    }
                    // Se l'elemento da inserire è maggiore dell'elemento del nodo in cui mi trovo
                    // continuo verso destra e aumento il contatore delle comparazioni
                    else if (cmp > 0) {
                        x = x.right;
                    }
                    //Se l'elemento da inserire è già presente aumento il count ed esco;
                    else {
                        x.count++;
                        size++;
                        return countCmp;
                    }
                }
                z.parent = y;
                if (z.el.compareTo(y.el) < 0) {
                    countCmp++;
                    y.left = z;
                } else {
                    countCmp++;
                    y.right = z;
                }
                numberOfNodes++;
                size++;
            }
            insertFixup(z);
            return countCmp;
        }

        /**
         * Effettua una rotazione a sinistra.
         *
         * @param x il nodo su cui effettuare la rotazione, deve avere un
         *          figlio destro
         * @throws IllegalArgumentException se il nodo passato ha figlio
         *                                  destro NIL
         */
        protected void leftRotate(RBTreeNode x) {
            if (isNil(x.right)) {
                throw new IllegalArgumentException("il nodo passato ha figlio destro NIL");
            }
            root = this;
            RBTreeNode y = x.right;
            x.right = y.left;
            if (!isNil(y.left)) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (isNil(x.parent)) {
                root = y;
            } else if (x.parent.left == x) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        /**
         * Effettua una rotazione a destra.
         *
         * @param x il nodo su cui effettuare la rotazione, deve avere un
         *          figlio sinistro
         * @throws IllegalArgumentException se il nodo passato ha figlio
         *                                  sinistro NIL
         */
        protected void rightRotate(RBTreeNode x) {
            if (isNil(x.left)) {
                throw new IllegalArgumentException("il nodo passato ha figlio sinistro NIL");
            }
            root = this;
            RBTreeNode y = x.left;
            x.left = y.right;
            if (!isNil(y.right)) {
                y.right.parent = x;
            }
            y.parent = x.parent;
            if (isNil(x.parent)) {
                root = y;
            } else if (x.parent.right == x) {
                x.parent.right = y;
            } else {
                x.parent.left = y;
            }
            y.right = x;
            x.parent = y;
        }

        /**
         * Rimuove un elemento dal (sotto)albero di cui questo nodo è radice. Se
         * l'elemento ha molteplicità maggiore di 1 allora viene semplicemente
         * decrementata la molteplicità.
         *
         * @param el l'elemento da rimuovere
         * @return true se l'elemento è stato rimosso (anche solo semplicemente
         * decrementando la molteplicità), false se non era presente.
         */
        protected boolean remove(E el) {
            RBTreeNode z = this.search(el);
            RBTreeNode x;
            RBTreeNode y = z;
            boolean yOriginalColor = y.color;

            if(z==null){
                return false;
            }
            // Decremento count se la molteplicità dell'elemento da rimuovere è maggiore di 1
            if(z.count>1){
                z.count--;
                size--;
                return true;
            }
            // Figlio sinistro è nullo
            if(isNil(z.left)){
                x = z.right;
                changeNode(z, z.right);
            }
            // Figlio destro è nullo
            else if(isNil(z.right)){
                x = z.left;
                changeNode(z, z.left);
            }
            // Ha entrambi i figli
            else{
                y = z.right.getMinimum();
                yOriginalColor = y.color;
                x = y.right;
                if(y.parent.equals(z)){
                    x.parent = y;
                }
                else{
                    changeNode(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                changeNode(z, y);
                y.left = z.left;
                y.left.parent = y;
                y.color = z.color;
            }
            if(yOriginalColor==BLACK){
                removeFixup(x);
            }
            numberOfNodes--;
            size--;
            return true;
        }

        /*
         * Scambia il nodo target con il nodo with
         *
         * @param target elemento da rimuovere
         * @param with elemento da inserire al posto di target
         */
        private void changeNode(RBTreeNode target, RBTreeNode with){
            //Target parent è null, quindi target è la radice
            if(isNil(target.parent)){
                root = with;
            }
            // Target è figlio sinistro
            else if(target.equals(target.parent.left)){
                target.parent.left = with;
            }
            // Target è figlio destro
            else{
                target.parent.right = with;
            }
            with.parent = target.parent;
        }

        /*
         * Metodo privato chiamato da insert per ripristinare le proprietà dell'albero
         *
         * @param z
         */
        private void insertFixup(RBTreeNode z) {
            while (z.parent.isRed()) {
                if (z.parent.equals(z.parent.parent.left)) {
                    RBTreeNode y = z.parent.parent.right;
                    // Caso 1, y è rosso
                    if (y.isRed()) {
                        z.parent.color = BLACK;
                        y.color = BLACK;
                        z.parent.parent.color = RED;
                        z = z.parent.parent;
                    }
                    // Caso 2, y è nero e z è figlio destro
                    else if (z.equals(z.parent.right)) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    // Caso 3, y è nero e z è figlio sinistro
                    else {
                        z.parent.color = BLACK;
                        z.parent.parent.color = RED;
                        rightRotate(z.parent.parent);
                    }
                }
                else {
                    RBTreeNode y = z.parent.parent.left;
                    // Caso 1, y è rosso
                    if(y.isRed()){
                        z.parent.color = BLACK;
                        y.color = BLACK;
                        z.parent.parent.color = RED;
                        z = z.parent.parent;
                    }
                    // Caso 2, y è nero e z è figlio destro
                    else if (z.equals(z.parent.left)){
                        z = z.parent;
                        rightRotate(z);
                    }
                    // Caso 3, y è nero e z è figlio sinistro
                    else {
                        z.parent.color = BLACK;
                        z.parent.parent.color = RED;
                        leftRotate(z.parent.parent);
                    }
                }
            }
            root.color = BLACK;
        }

        /*
         * Metodo privato chiamato da remove per ripristinare le proprietà dell'albero
         *
         * @param x
         */
        private void removeFixup(RBTreeNode x){
            while (!x.equals(root) && x.color == BLACK){
                if (x.equals(x.parent.left)){
                    RBTreeNode w = x.parent.right;
                    // Caso 1, w è rosso
                    if (w.isRed()){
                        w.color = BLACK;
                        x.parent.color = RED;
                        leftRotate(x.parent);
                        w = x.parent.right;
                    }
                    // Caso 2, entrambi i figli sono neri
                    if (w.left.isBlack() && w.right.isBlack()){
                        w.color = RED;
                        x = x.parent;
                    }
                    else{
                        // Caso 3, il figlio destro di w è nero
                        if (w.right.isBlack()){
                            w.left.color = BLACK;
                            w.color = RED;
                            rightRotate(w);
                            w = x.parent.right;
                        }
                        // Caso 4, w è nero e il suo figlio destro è rosso
                        else {
                            w.color = x.parent.color;
                            x.parent.color = BLACK;
                            w.right.color = BLACK;
                            leftRotate(x.parent);
                            x = root;
                        }
                    }
                }
                // Se x è figlio destro
                else{
                    RBTreeNode w = x.parent.left;
                    // Caso 1, w è rosso
                    if (w.isRed()){
                        w.color = BLACK;
                        x.parent.color = RED;
                        rightRotate(x.parent);
                        w = x.parent.left;
                    }

                    // Caso 2, entrambi i figli sono neri
                    if (w.right.isBlack() && w.left.isBlack()){
                        w.color = RED;
                        x = x.parent;
                    }
                    else{
                        // Caso 3, il figlio sinistro di w è nero
                        if (w.left.isBlack()){
                            w.right.color = BLACK;
                            w.color = RED;
                            leftRotate(w);
                            w = x.parent.left;
                        }
                        // Caso 4, w è nero e il suo figlio sinistro è rosso
                        w.color = x.parent.color;
                        x.parent.color = BLACK;
                        w.left.color = BLACK;
                        rightRotate(x.parent);
                        x = root;
                    }
                }
            }
            x.color = BLACK;
        }

    }
}
