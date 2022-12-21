package data;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * {@link Repository} für {@link Book Bücher}
 */
public class BookRepository extends Repository<Book> {

    @Override
    public void save(Book book) {
        BiPredicate<Book, Book> duplicatePredicate = (b1, b2)
                -> b1.getTitle().equals(b2.getTitle())
                && b1.getAuthor().equals(b2.getAuthor());

        if (findAll().stream().anyMatch(b -> duplicatePredicate.test(book, b))) {
            throw new RuntimeException("Buch existiert bereits!");
        }

        super.save(book);
    }

    /**
     * Sucht anhand {@link Book#getTitle() Titel} und
     * {@link Book#getAuthor() Autor*in} nach
     * {@link User Büchern}
     * @param searchString der Suchbegriff
     * @return eine Liste aller gefundenen Bücher
     */
    @Override
    public List<Book> search(String searchString) {
        Predicate<Book> searchPredicate = u -> u.getTitle().contains(searchString) || u.getAuthor().contains(searchString);
        return findAll().stream().filter(searchPredicate).toList();
    }
}
