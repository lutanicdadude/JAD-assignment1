package model;

/**
 * User.java
 * A simple JavaBean representing a user.
 * This class stores user data and provides getters and setters.
 */

/////////////////////////////////////////////////////   WILL USE WHEN USING SESSION ////////////////////////////////////////////////////
public class User {

    private int id;
    private String name;
    private String email;

	/*
	 * private String phoneNumber; // NEW
	 */
    public User() {}

	public User(int user_id, String name, String email/* , String phoneNumber */) {
        this.id = user_id;
        this.name = name;
        this.email = email;
		/* this.phoneNumber = phoneNumber; */
    }

    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

	/*
	 * public String getPhoneNumber() { return phoneNumber; } // NEW public void
	 * setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; } // NEW
	 */}

