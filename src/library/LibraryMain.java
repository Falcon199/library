package library;

import java.util.Scanner;

public class LibraryMain implements Commands {


    static DataStorage dataStorage = new DataStorage();
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        boolean isRun = true;
        while (isRun) {
            printCommands();
            int command;
            try {
                String commandStr = scanner.nextLine();
                command = Integer.parseInt(commandStr);
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    System.out.println("Հաջողություն");
                    break;
                case ADD_AUTHOR:
                    addAuthor();
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case SEARCH_BY_KEYWORD:
                    searchByKeyword();
                    break;
                case SEARCH_BY_AUTHOR:
                    searchByAuthor();
                    break;
                case SEARCH_BY_PRICE_RANGE:
                    searchByPrice();
                    break;
                case DELETE:
                    delete();
                    break;
                case PRINT_ALL_AUTHORS:
                    dataStorage.printAuthors();
                    break;
                case PRINT_ALL_BOOKS:
                    dataStorage.printBooks();
                    break;
                default:
                    System.out.println("Սխալ հրաման");
            }
        }

    }

    private static void searchByPrice() {
        System.out.println("Ներմուծեք մին․ ու մաքսիմում թվերը․ օր՝ 10-60");
        String priceRange = scanner.nextLine();
        String[] priceRangeData = priceRange.split("-");
        double min = Double.parseDouble(priceRangeData[0]);
        double max = Double.parseDouble(priceRangeData[1]);
        dataStorage.searchBookByPriceRange(min, max);
    }

    private static void searchByAuthor() {
        System.out.println("Ընտրեք հեղինակի email-ը");
        dataStorage.printAuthors();
        try {
            String authorEmail = scanner.nextLine();
            Author author = dataStorage.getAuthorByEmail(authorEmail);
            dataStorage.searchBookByAuthor(author);
        } catch (AuthorNotFoundException e) {
            System.out.println(e);
        }

    }


    private static void addAuthor() {
        System.out.println("Ներմուծեք հեղինակի տվյալները " +
                "(name,surname,email,gender)");
        String authorDataStr = scanner.nextLine();
        String[] authorData = authorDataStr.split(",");
        try {
            dataStorage.getAuthorByEmail(authorData[2]);
            System.out.println("Տրված mail-ով հեղինակ գոյություն ունի");
        } catch (AuthorNotFoundException e) {
            Author author = new Author();
            author.setName(authorData[0]);
            author.setSurname(authorData[1]);
            author.setEmail(authorData[2]);
            author.setGender(authorData[3]);
            dataStorage.add(author);
            System.out.println("Հեղինակը ավելացվեց");
        }


    }

    private static void delete() {
        dataStorage.printBooks();
        System.out.println("Ներմուծել գրքի իդ-ն ջնջելու համար");
        String bookId = scanner.nextLine();
        dataStorage.deleteByBookId(bookId);
    }

    private static void searchByKeyword() {
        System.out.println("Ներմուծեք փնտրվող բառը");
        String keyword = scanner.nextLine();
        dataStorage.searchByKeyword(keyword);
    }

    private static void addBook() {
        if (dataStorage.getAuthorSize() > 0) {
            System.out.println("Ընտրեք հեղինակի email-ը");
            dataStorage.printAuthors();
            String authorEmail = scanner.nextLine();
            try {
                Author author = dataStorage.getAuthorByEmail(authorEmail);
                System.out.println("Ներմուծեք գրքի տվյալները " +
                        "(bookId, title, description,price,count)");
                String bookDataStr = scanner.nextLine();
                String[] bookData = bookDataStr.split(",");

                Book book = new Book();
                book.setBookId(bookData[0]);
                book.setTitle(bookData[1]);
                book.setDescription(bookData[2]);
                book.setAuthor(author);
                book.setPrice(Double.parseDouble(bookData[3]));
                book.setCount(Integer.parseInt(bookData[4]));

                dataStorage.add(book);
                System.out.println("Գիրքը ավելացվեց");
            } catch (AuthorNotFoundException e) {
                System.out.println("Ներմուծված մեյլով հեղինակ չգտնվեց");
                addBook();
            }
        } else {
            System.out.println("Հեղինակ չկա! Գիրք ավելացնելու համար ավելացրեք հեղինակ");
        }

    }

    private static void printCommands() {
        System.out.println("Ներմուծել " + EXIT + " Դուրս գալու համար");
        System.out.println("Ներմուծել " + ADD_AUTHOR + " Հեղինակ ավելացնելու համար");
        System.out.println("Ներմուծել " + ADD_BOOK + " Գիրք ավելացնելու համար");
        System.out.println("Ներմուծել " + SEARCH_BY_KEYWORD + " Բառով գիրք փնտրելու համար");
        System.out.println("Ներմուծել " + SEARCH_BY_AUTHOR + " Հեղինակով գիրք փնտրելու համար");
        System.out.println("Ներմուծել " + SEARCH_BY_PRICE_RANGE + " Գնով գիրք փնտրելու համար");
        System.out.println("Ներմուծել " + DELETE + " Գիրք ջնջելու համար");
        System.out.println("Ներմուծել " + PRINT_ALL_AUTHORS + " Բոլոր հեղինակներին տեսնելու համար");
        System.out.println("Ներմուծել " + PRINT_ALL_BOOKS + " Բոլոր գրքերը տեսնելու համար");
    }

}
