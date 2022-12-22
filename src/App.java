import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import data.Book;
import data.BookRepository;
import data.Role;
import data.User;
import data.UserRepository;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Attribute
    // /////////////////////////////////////////////////////////////////////////

    private static final String INVALID_INPUT = "Ungültige Eingabe!";
    private static final String USERNAME = "Nutzername: ";

    private final Scanner scanner = new Scanner(System.in);

    private final UserRepository userRepository = new UserRepository();
    private final BookRepository bookRepository = new BookRepository();

    private User currentUser = null;

    // /////////////////////////////////////////////////////////////////////////
    // Methoden
    // /////////////////////////////////////////////////////////////////////////

    private void run() {
        System.out.println("Willkommen bei der Bücherei!");

        for (;;) { // NOSONAR: Das Programm wird mit System.exit beendet
            while (currentUser == null) {
                login();
            }

            printMenu();
            execute(inputInt());
        }
    }

    private void printMenu() {
        System.out.println(String.format("%nWillkommen %s! Was möchtest du tun?", currentUser.getName()));
        System.out.println("1. " + (currentUser.getRole() == Role.ADMIN ? "Nutzer*in verwalten" : "Buch ausleihen"));
        System.out.println("2. " + (currentUser.getRole() == Role.ADMIN ? "Buch hinzufügen" : "Buch zurückgeben"));
        System.out.println("3. Suche");
        System.out.println("9. Ausloggen");
        System.out.println("0. Beenden");
    }

    private void execute(int selection) {
        if (selection == 1) {
            if (currentUser.isAdmin()) {
                administrateUsers();
            } else {
                borrowBook();
            }
        } else if (selection == 2) {
            if (currentUser.isAdmin()) {
                addBook();
            } else {
                returnBook();
            }
        } else if (selection == 3) {
            search();
        } else if (selection == 9) {
            logout();
        } else if (selection == 0) {
            System.exit(0);
        }
    }

    private void login() {
        System.out.print(USERNAME);
        String username = scanner.nextLine();
        System.out.print("Passwort: ");
        String password = scanner.nextLine();

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
        } else {
            System.out.println("Ungültiger Nutzername oder Passwort!");
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Admin
    // /////////////////////////////////////////////////////////////////////////

    private void administrateUsers() {
        System.out.println("Möchtest du eine*n Nutzer*in hinzufügen oder löschen?");
        System.out.println("1. Hinzufügen");
        System.out.println("2. Löschen");

        int selection = inputInt();

        if (selection == 1) {
            addUser();
        } else if (selection == 2) {
            deleteUser();
        } else {
            System.out.println(INVALID_INPUT);
        }
    }

    private void addUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print(USERNAME);
        String username = scanner.nextLine();
        System.out.print("Passwort: ");
        String password = scanner.nextLine();

        try {
            userRepository.save(new User(name, username, password, Role.USER));
            System.out.println("Nutzer*in wurde erfolgreich hinzugefügt!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteUser() {
        System.out.print(USERNAME);
        String username = scanner.nextLine();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User existiert nicht!");
        } else {
            bookRepository.findByBorrower(user).forEach(Book::returnBook);
            userRepository.delete(user);
        }
    }

    private void addBook() {
        System.out.print("Titel: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();

        bookRepository.save(new Book(title, author));
    }

    private void search() {
        System.out.print("Suchbegriff: ");
        String searchString = scanner.nextLine();

        List<data.Readable> readables = new ArrayList<>();

        if (currentUser.isAdmin()) {
            readables.addAll(userRepository.search(searchString));
        }

        readables.addAll(bookRepository.search(searchString));

        if (readables.isEmpty()) {
            System.out.println("Keine Ergebnisse gefunden!");
        } else {
            readables.forEach(r -> System.out.println(r.read()));
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("Du wurdest erfolgreich ausgeloggt!");
    }

    // /////////////////////////////////////////////////////////////////////////
    // User
    // /////////////////////////////////////////////////////////////////////////

    private void borrowBook() {
        System.out.print("Titel: ");
        String title = scanner.nextLine();
        System.out.print("Bis: ");
        String untilString = scanner.nextLine();

        LocalDate until = null;

        try {
            until = LocalDate.parse(untilString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Ungültiges Datum!");
            return;
        }

        try {
            Book bookToBorrow = getBookToBorrow(bookRepository.findByTitle(title));

            if (bookToBorrow.getBorrower() == null) {
                bookToBorrow.borrow(currentUser, until);
            } else {
                System.out.println("Dieses Buch ist leider bereits ausgeliehen!");
            }
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private Book getBookToBorrow(List<Book> books) {
        if (books.isEmpty()) {
            throw new NoSuchElementException("Keine Bücher mit diesem Titel gefunden!");
        } else if (books.size() == 1) {
            return books.get(0);
        } else {
            System.out.println("Mehrere Bücher mit diesem Titel gefunden!");
            System.out.println("Bitte wähle aus folgenden aus:\n");
            printBookList(books);

            int selection = inputInt();
            try {
                return books.get(selection - 1);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
        }
    }

    private void returnBook() {
        System.out.println("Welches Buch möchtest du zurückgeben?");

        List<Book> books = bookRepository.findByBorrower(currentUser);
        printBookList(books);

        try {
            Book bookToReturn = books.get(inputInt() - 1);
            
            if (bookToReturn.getBorrowedUntil().isAfter(LocalDate.now())) {
                System.out.println("Du hast dieses Buch leider zu spät zurückgegeben!");
            }

            bookToReturn.returnBook();

            System.out.println("Buch wurde erfolgreich zurückgegeben!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(INVALID_INPUT);
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // Util
    // /////////////////////////////////////////////////////////////////////////

    private int inputInt() {
        System.out.print("\n>> ");
        String selectionString = scanner.nextLine();
        int selection = -1;

        try {
            selection = Integer.parseInt(selectionString);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT);
        }

        System.out.println();
        return selection;
    }

    private void printBookList(List<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, books.get(i).read()));
        }
    }
}
