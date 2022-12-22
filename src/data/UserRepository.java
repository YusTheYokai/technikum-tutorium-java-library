package data;

import java.util.List;
import java.util.function.Predicate;

/**
 * {@link Repository} für {@link User Benutzer}
 */
public class UserRepository extends Repository<User> {

    public UserRepository() {
        save(new User("Admin", "admin", "Start123$", Role.ADMIN));
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methoden
    // /////////////////////////////////////////////////////////////////////////

    @Override
    public void save(User user) {
        if (findAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            throw new RuntimeException("Nutzername bereits in verwendung!");
        }
        if (user.getUsername().trim().length() < 3) {
            throw new RuntimeException("Nutzername muss mindestens 3 Zeichen lang sein!");
        }
        if (user.getPassword().trim().length() < 8) {
            throw new RuntimeException("Passwort muss mindestens 8 Zeichen lang sein!");
        }

        super.save(user);
    }

    /**
     * Sucht anhand {@link User#getName() Namen} und
     * {@link User#getUsername() Nutzernamen} nach
     * {@link User Nutzer*innen}
     * @param searchString der Suchbegriff
     * @return eine Liste aller gefundenen Nutzer*innen
     */
    @Override
    public List<User> search(String searchString) {
        Predicate<User> searchPredicate = u -> u.getName().toLowerCase().contains(searchString.toLowerCase())
                || u.getUsername().toLowerCase().contains(searchString.toLowerCase());
        return findAll().stream().filter(searchPredicate).toList();
    }

    /**
     * Findet eine*n {@link User Nutzer*in}
     * anhand des Nutzernamens.
     * @param username der Nutzername
     * @return den*die {@link User User*in}
     */
    public User findByUsername(String username) {
        return findAll().stream()
                        .filter(u -> u.getUsername().equals(username))
                        .findFirst()
                        .orElse(null);
    }
}
