package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstrakte Klasse für konkrete Repositories.
 */
public abstract class Repository<T extends Readable> {

    private final List<T> list = new ArrayList<>();

    // /////////////////////////////////////////////////////////////////////////
    // Methoden
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Speichert eine Entität
     * @param entity die Entität
     */
    public void save(T entity) {
        list.add(entity);
    }

    /**
     * Löscht eine Entität
     * @param entity die Entität
     */
    public void delete(T entity) {
        list.remove(entity);
    }

    /**
     * Findet alle Entitäten.
     * 
     * Diese Liste soll immutable sein,
     * damit die Liste nicht direkt bearbeitet werden kann,
     * sondern anhand {@link #save(Object)} und {@link #delete(Object)}
     * verändert wird.
     * 
     * @return eine immutable Liste der Entitäten.
     */
    public List<T> findAll() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Findet Entitäten anhand eines Suchbegriffes.
     * @param searchString der Suchbegriff
     * @return eine Liste alle gefundenen Entitäten
     */
    public abstract List<T> search(String searchString);
}
