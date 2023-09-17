package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    MainLOOP:
    while(true) {
        try {
            System.out.println("\nLibrary Portal Initialized….");
            System.out.println("1. Enter as a librarian");
            System.out.println("2. Enter as a member");
            System.out.println("3. Exit");

            int entry = Integer.parseInt(reader.readLine());


            switch (entry) {
                case 1:
                    // Code for librarian entry
                    Library.main(args);
                    break;
                case 2:
                    // Code for member entry
                    System.out.println("Enter Your name ");
                    String name = reader.readLine();
                    System.out.println("Enter Your Phone No. ");
                    String number=reader.readLine();

                    if(Library.ifMem(name) && Library.correctph(name,number)){
                        Library.getMember(name).menu();
                    }
                    else{
                        System.out.printf("Member with Name: %s and Phone No: %s doesn't exist. \n",name,number);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the Library Portal");

                    break MainLOOP;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a number.");
        }
    }
    }
}
