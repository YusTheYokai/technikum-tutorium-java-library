package data;

import java.time.LocalDate;

public class Book implements Readable {

    private final String title;
    private final String author;

    private User borrower;
    private LocalDate borrowedUntil;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methoden
    // /////////////////////////////////////////////////////////////////////////

    @Override
    public String read() {
        return String.format("(Buch) Titel: %s, Autor*in: %s", title, author);
    }

    public void borrow(User user, LocalDate until) {
        if (borrower != null) {
            throw new RuntimeException("Buch ist bereits ausgeliehen!");
        }

        borrower = user;
        borrowedUntil = until;
    }

    public void returnBook() {
        borrower = null;
        borrowedUntil = null;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getter
    // /////////////////////////////////////////////////////////////////////////

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public User getBorrower() {
        return borrower;
    }

    public LocalDate getBorrowedUntil() {
        return borrowedUntil;
    }
}
