package library;

public class DataStorage {

    private Book[] books;
    private Author[] authors;

    private int bookSize;
    private int authorSize;

    public DataStorage(int capacity) {
        books = new Book[capacity];
        authors = new Author[capacity];
    }

    public DataStorage() {
        books = new Book[16];
        authors = new Author[16];
    }

    public void add(Book book) {
        if (bookSize == books.length) {
            extendBook();
        }
        books[bookSize++] = book;
    }

    public void add(Author author) {
        if (authorSize == authors.length) {
            extendAuthor();
        }
        authors[authorSize++] = author;
    }

    private void extendBook() {
        Book[] tmp = new Book[books.length + 10];
        System.arraycopy(books, 0, tmp, 0, books.length);
        books = tmp;
    }

    private void extendAuthor() {
        Author[] tmp = new Author[authors.length + 10];
        System.arraycopy(authors, 0, tmp, 0, authors.length);
        authors = tmp;
    }

    public void printBooks() {
        for (int i = 0; i < bookSize; i++) {
            System.out.println(books[i]);
        }
    }

    public void printAuthors() {
        for (int i = 0; i < authorSize; i++) {
            System.out.println(authors[i]);
        }
    }

    public Author getAuthorByEmail(String email) throws AuthorNotFoundException {
        for (int i = 0; i < authorSize; i++) {
            if (authors[i].getEmail().equals(email)) {
                return authors[i];
            }
        }
        throw new AuthorNotFoundException(email + "email-ով հողինակ գոյություն չունի");
    }

    public void searchByKeyword(String keyword) {
        boolean isFound = false;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getTitle().contains(keyword) ||
                    books[i].getDescription().contains(keyword)) {
                System.out.println(books[i]);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Ոչ մի բան չգտնվեց");
        }
    }

    public void searchBookByAuthor(Author author) {
        boolean isFound = false;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getAuthor().equals(author)) {
                System.out.println(books[i]);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Ոչ մի բան չգտնվեց");
        }
    }

    public void searchBookByPriceRange(double min, double max) {
        boolean isFound = false;
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getPrice() >= min &&
                    books[i].getPrice() <= max) {
                System.out.println(books[i]);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Ոչ մի բան չգտնվեց");
        }
    }


    private void deleteBookByIndex(int index) {
        for (int i = index + 1; i < bookSize; i++) {
            books[i - 1] = books[i];
        }
        bookSize--;
    }

    private void deleteAuthorByIndex(int index) {
        for (int i = index + 1; i < authorSize; i++) {
            authors[i - 1] = authors[i];
        }
        authorSize--;
    }

    public void deleteByBookId(String bookId) {
        for (int i = 0; i < bookSize; i++) {
            if (books[i].getBookId().equals(bookId)) {
                deleteBookByIndex(i);
            }
        }
    }

    public void deleteAuthorByEmail(String email) {
        for (int i = 0; i < authorSize; i++) {
            if (authors[i].getEmail().equals(email)) {
                deleteAuthorByIndex(i);
            }
        }
    }

    public int getBookSize() {
        return bookSize;
    }

    public int getAuthorSize() {
        return authorSize;
    }
}
