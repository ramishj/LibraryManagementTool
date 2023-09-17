package org.example;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Books {
    private String name;
    private String author;
    private int copies;
    private int issuedcopies;
    private int id;
    private Instant duedate;
    public void setDuedateForMember(String memberName, Instant dueDate) {
        dueDates.put(memberName, dueDate);
    }
    private Map<String, Instant> dueDates = new HashMap<>();
    public Instant getDueDateForMember(String memberName) {
        return dueDates.get(memberName);
    }
    public Books(String name, String author, int copies, int id) {
        this.name = name;
        this.author = author;
        this.copies = copies;
        this.id = id;
        this.issuedcopies=copies;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getCopies() {
        return issuedcopies;
    }

    public void setCopies(int copies) {
        this.issuedcopies = copies;
    }

    public int getId() {
        return id;
    }
    public void setDuedate(Instant date){
        this.duedate=date;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Instant getDuedate(){
        return duedate;
    }
    public int getInstock(){return copies;}
    public void removeDueDateForMember(String memberName) {
        dueDates.remove(memberName);
    }
}
