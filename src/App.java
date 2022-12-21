import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.Book;
import data.BookRepository;
import data.User;
import data.UserRepository;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Attribute
    // /////////////////////////////////////////////////////////////////////////

    private final Scanner scanner = new Scanner(System.in);

    private final UserRepository userRepository = new UserRepository();
    private final BookRepository bookRepository = new BookRepository();

    private User currentUser = null;

    // /////////////////////////////////////////////////////////////////////////
    // Methoden
    // /////////////////////////////////////////////////////////////////////////

    private void run() {
        System.out.println("Willkommen bei der Bücherei!");
        do {
            login();
        } while(currentUser == null);

        System.out.println(String.format("Willkommen %s! Was möchtest du tun?", currentUser.getName()));
        System.out.println("1. Nutzer*in verwalten");
        System.out.println("2. Buch hinzufügen");
        System.out.println("3. Suche");
        System.out.println("9. Ausloggen");
        System.out.println("0. Beenden");
        System.out.println("\n>> ");

        String selectionString = scanner.nextLine();
        int selection = -1;

        try {
            selection = Integer.parseInt(selectionString);
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Auswahl!");
        }

        if (selection == 1) {
            administrateUsers();
        } else if (selection == 2) {
            addBook();
        } else if (selection == 3) {
            search();
        } else if (selection == 9) {
            logout();
        } else if (selection == 0) {
            System.exit(0);
        } else {
            System.out.println("Ungültige Eingabe!");
        }
    }

    private void login() {
        System.out.print("Nutzername: ");
        String username = scanner.nextLine();
        System.out.println("Passwort: ");
        String password = scanner.nextLine();

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
        } else {
            System.out.println("Ungültiger Nutzername oder Passwort!");
        }
    }

    private void administrateUsers() {
        System.out.println("Möchtest du eine*n Nutzer*in hinzufügen oder löschen?");
        System.out.println("1. Hinzufügen");
        System.out.println("2. Löschen");
        System.out.println("\n>> ");

        String selectionString = scanner.nextLine();
        int selection = -1;

        try {
            selection = Integer.parseInt(selectionString);
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe!");
        }

        if (selection == 1) {
            addUser();
        } else if (selection == 2) {
            deleteUser();
        } else {
            System.out.println("Ungültige Eingabe!");
        }
    }

    private void addUser() {
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Nutzername: ");
        String username = scanner.nextLine();
        System.out.println("Passwort: ");
        String password = scanner.nextLine();

        userRepository.save(new User(name, username, password));
    }

    private void deleteUser() {
        System.out.println("Nutzername: ");
        String username = scanner.nextLine();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User existiert nicht!");
        } else {
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

        List<Readable> readables = new ArrayList<>();
        readables.addAll(userRepository.search(searchString));
        readables.addAll(bookRepository.search(searchString));
    }

    private void logout() {

    }
}
