package com.example.fitconnect;

import java.io.Serializable;

// We implement the Serializable interface. This "marks" the class as being able to be
// converted into a stream of bytes, which is how Android sends it in an Intent.
public class Buddy implements Serializable {

    private String name;
    private String interest;

    // Constructor to create a new Buddy object.
    public Buddy(String name, String interest) {
        this.name = name;
        this.interest = interest;
    }

    // "Getter" methods to access the data.
    public String getName() {
        return name;
    }

    public String getInterest() {
        return interest;
    }

    // This method is important for how the object will be displayed in our ListView.
    @Override
    public String toString() {
        return name + " - " + interest;
    }
}