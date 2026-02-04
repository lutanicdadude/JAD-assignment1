package model;

public class Customer {
  private int id;           
  private String name;     
  private String email;    
  private String paymentMethod; 
  private String lastLoggedIn; 
  private String createdAt; 

  public Customer() {
  }

  public Customer(int id, String name, String email, String paymentMethod, String lastLoggedIn, String createdAt) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.paymentMethod = paymentMethod;
      this.lastLoggedIn = lastLoggedIn;
      this.createdAt = createdAt;
  }

  // --- Getters & Setters ---

  public int getId() {
      return id;
  }

  public void setId(int id) {
      this.id = id;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
  
  public String getLastLoggedIn() {
    return lastLoggedIn;
  }

  public void setLastLoggedIn(String lastLoggedIn) {
    this.lastLoggedIn = lastLoggedIn;
  }
  
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
