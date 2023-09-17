package org.example;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class Library {
    private static int calculateFineForMember(Member member) {
        int totalFine = 0;

        for (int i=0;i<member.books.size();i++) {
            String bookName=member.books.get(i);
            Books book = Books.get(bookName);
            Instant dueDate = book.getDueDateForMember(member.getName());

            if (dueDate != null && dueDate.isBefore(Instant.now())) {
                // Calculate the fine for the overdue book
                long daysLate = Duration.between(dueDate, Instant.now()).toSeconds();
                int fineAmount = (int) (daysLate * 3);
                totalFine += fineAmount;
            }
        }

        return totalFine;
    }
    private static HashMap<String, Member> Members = new HashMap<>();
    private static int Memno = 0;
    private static int bookn =0;
    public static HashMap<String, Books> Books = new HashMap<>();
    public static boolean ifMem(String name){
        return Members.containsKey(name);
    }
    public static boolean correctph(String name,String phone){

        if(Members.get(name).getPhone().equals(phone)){
            return true;
        }
        else{
            return false;
        }
    }
    public static Member getMember(String Name){
        return Members.get(Name);
    }
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("\n");
                System.out.println("1. Register a member");
                System.out.println("2. Remove a member");
                System.out.println("3. Add a book\n" +
                        "4. Remove a book\n" +
                        "5. View all members along with their books and fines to be paid\n" +
                        "6. View all books\n" +
                        "7. Back\n");
                int entry = Integer.parseInt(reader.readLine());

                // Use a switch statement to handle user choices
                switch (entry) {
                    case 1:
                        // Code for registering a member
                        System.out.print("Enter Your UserName: ");
                        String name = reader.readLine();
                        if(Members.containsKey(name)){
                            System.out.println("User with this UserName already Exists. Try other User Name");
                            break;
                        }
                        System.out.print("Enter Your Age: ");
                        int age = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Your Phone No.: ");
                        String phone = reader.readLine();

                        while (phone.length() != 10) {
                            System.out.println("Phone Number Should be of 10 digits\nEnter it again");
                            phone = reader.readLine();
                        }
                        Member member = new Member(name, age, phone, Memno);
                        Members.put(name, member); // Store the member object
                        System.out.printf("Member Successfully Registered with Member id Lib_id %d%n", member.getMemberId());
                        Memno++;
                        break;
                    case 2:
                        // Code for removing a member
                        System.out.println("Enter Name");
                        String name2 = reader.readLine();
                        if (Members.get(name2) == null) {
                            System.out.printf("No user with UserName %s registered\n", name2);
                        } else {
                            Members.remove(name2);
                            System.out.printf("User %s is removed\n", name2);
                        }
                        break;
                    case 3:
                        // Code for adding a book
                        System.out.print("Enter Book Name: ");
                        String bookname = reader.readLine();
                        System.out.print("Enter Author's Name: ");
                        String author = reader.readLine();
                        System.out.print("Enter No. of copies: ");
                        int copies = Integer.parseInt(reader.readLine());
                        Books book = new Books(bookname,author,copies,bookn);
                        bookn++;
                        Books.put(bookname,book);
                        System.out.println("Book added Sucessfully!");
                        break;
                    case 4:
                        // Code for removing a book
                        System.out.print("Enter Book to be removed: ");
                        String bookr = reader.readLine();
                        if (!Books.containsKey(bookr)) {
                            System.out.printf("No book with name %s found\n", bookr);
                        } else {
                            if(Books.get(bookr).getCopies()!=Books.get(bookr).getInstock()){
                                System.out.println("Can't Remove. The Book is currently Issued to some Members");
                                break;
                            }
                            Books.remove(bookr);
                            System.out.printf("Book %s is removed\n", bookr);
                        }
                        break;
                    case 5:
                        // Code for viewing members, their books, and fines
                        int counter2 =1;
                        for(Member aa : Members.values()){
                            System.out.printf("%d.Name- %s\n Books-%s\n Fine - Rs %d\n",counter2,aa.getName(),aa.getBooks(),Library.calculateFineForMember(aa));
                            counter2++;
                        }
                        break;
                    case 6:
                        // Code for viewing all books
                        System.out.println("These are the available books.");
                        int counter = 1;
                        for (String bookName : Books.keySet()) {
                            System.out.println(counter + ". " + bookName);
                            counter++;
                        }
                        System.out.println("\n");
                        break;
                    case 7:
                        // Exit the menu
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

}

