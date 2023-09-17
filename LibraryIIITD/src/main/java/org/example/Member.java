package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private String name;
    private int age;
    private String phone;
    private int memno;
    private int fine;
    public ArrayList<String> books = new ArrayList<>();

    public Member(String name, int age, String phone, int memno) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.memno = memno;
    }

    public String getName() {
        return name;
    }
    public int getFine(){
        return this.fine;
    }
    public int getAge() {
        return age;
    }
    public String getBooks() {
        StringBuilder booksNames = new StringBuilder();

        for (String book : books) {
            booksNames.append(book).append(", ");
        }


        if (booksNames.length() > 2) {
            booksNames.setLength(booksNames.length() - 2);
        }

        return booksNames.toString();
    }
    public String getPhone() {
        return phone;
    }

    public int getMemberId() {
        return memno;
    }

    public void menu() {
        System.out.printf("\nWelcome %s. Member ID: Lib_id%d\n", this.name, this.memno);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("1. List Available Books\n" +
                        "2. List My Books\n" +
                        "3. Issue Book\n" +
                        "4. Return Book\n" +
                        "5. Pay Fine\n" +
                        "6. Back\n");

                int input = Integer.parseInt(reader.readLine());
                switch (input) {
                    case 1:
                        listAvailableBooks();
                        break;
                    case 2:
                        listMemberBooks();
                        break;
                    case 3:
                        issueBook();
                        break;
                    case 4:
                        returnBook();
                        break;
                    case 5:
                        payFine();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a number.");
        }
    }

    private void listAvailableBooks() {
        System.out.println("These are the available books.");
        int counter = 1;
        for (String bookName : Library.Books.keySet()) {
            if(Library.Books.get(bookName).getCopies()>0) {
                System.out.println(counter + ". " + bookName);
                counter++;
            }

        }
        System.out.println("\n");
    }

    private void listMemberBooks() {
        System.out.println("Your Books:");
        System.out.println("---------------------------------------------");
        for (String book : books) {
            System.out.printf("Book ID-%d\n",Library.Books.get(book).getId());
            System.out.printf("Book Name-%s\n",Library.Books.get(book).getName());
            System.out.printf("Book Author-%s\n",Library.Books.get(book).getAuthor());
            System.out.println("---------------------------------------------");
        }
        System.out.println("---------------------------------------------");
    }
    boolean hasOverdueBooks(){
        for(String book: this.books){
            if(Duration.between(Library.Books.get(book).getDueDateForMember(this.name),Instant.now()).toSeconds()>0){
                return true;
            }
        }
        return false;
    }

    private void issueBook() {

        if (this.hasOverdueBooks()) {
            System.out.println("You have overdue books. Please return them before issuing a new one.");
            return;
        }
        if (books.size() >= 2) {
            System.out.println("You have already issued the maximum number of books, return them to issue more.");
            return;
        }
        System.out.print("Enter the name of the book you want to issue: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String bookName = reader.readLine();

            // Check if the book exists in the library
            if (Library.Books.containsKey(bookName)) {
                Books book = Library.Books.get(bookName);

                // Check if there are available copies of the book
                if (book.getCopies() > 0) {
                    // Issue the book to the member
                    books.add(bookName);
                    book.setCopies(book.getCopies() - 1);

                    // Set the due date for this member 10 days from today
                    Duration sec = Duration.ofSeconds(10);
                    Instant dueDate = Instant.now().plus(sec);

                    // Store the due date for this specific book and member
                    book.setDuedateForMember(this.name, dueDate);

                    System.out.println("Book issued successfully.");
                } else {
                    System.out.println("Sorry, the selected book is not available at the moment.");
                }
            } else {
                System.out.println("Book not found in the library.");
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    private void returnBook() {
        System.out.print("Enter the name of the book you want to return: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String bookName = reader.readLine();

            // Check if the member has issued the book
            if (books.contains(bookName)) {
                Books book = Library.Books.get(bookName);
                Instant dueDate = book.getDueDateForMember(this.name);

                if (dueDate != null) {
                    long daysLate = Duration.between(dueDate, Instant.now()).toDays();

                    if (daysLate > 0) {
                        // Fine calculation: 3 rupees per day
                        int fineAmount = (int) (daysLate * 3);
                        this.fine += fineAmount;
                        System.out.printf("You returned the book %d days late. Your fine is %d rupees.%n", daysLate, fineAmount);
                    } else {
                        System.out.println("Book returned on time. No fine.");
                    }

                    // Remove the book from the member's list and update library copies
                    books.remove(bookName);
                    book.setCopies(book.getCopies() + 1);
                    book.removeDueDateForMember(this.name);

                    System.out.printf("Book Id-%d returned successfully.\n", book.getId());
                } else {
                    System.out.println("You have not issued this book.");
                }
            } else {
                System.out.println("You have not issued this book.");
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    private void payFine() {
        System.out.printf("Your FIne of Rs %d payed Sucessfully\n",this.fine);
        this.fine=0;
    }
}
