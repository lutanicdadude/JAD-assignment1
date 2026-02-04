package model;

public class Payment {
  private String email;
  private String serviceName;
  private String serviceDate;
  private String serviceStart;
  private String serviceEnd;
  private String additionalNotes;
  private double duration;

  // Empty constructor is needed when creating objects manually in DAO
  public Payment() {}

  // Optional full constructor
  public Payment(String email, String serviceName, String serviceDate, String serviceStart, String serviceEnd, String additionalNotes, double duration) {
      this.email = email;
      this.serviceName = serviceName;
      this.serviceDate = serviceDate;
      this.serviceStart = serviceStart;
      this.serviceEnd = serviceEnd;
      this.duration = duration;
      this.additionalNotes = additionalNotes;
  }

  // Getters
  
  public String getEmail() {
    return email;
  }
  
  public String getServiceName() {
      return serviceName;
  }

  public String getServiceDate() {
      return serviceDate;
  }

  public String getServiceStart() {
      return serviceStart;
  }
  
  public String getServiceEnd() {
    return serviceEnd;
  }
  
  public double getDuration() {
    return duration;
  }
  
  public String getAdditionalNotes() {
    return additionalNotes;
  }

  // Setters
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
  }

  public void setServiceDate(String serviceDate) {
      this.serviceDate = serviceDate;
  }

  public void setServiceStart(String serviceStart) {
      this.serviceStart = serviceStart;
  }
  
  public void setServiceEnd(String serviceEnd) {
    this.serviceEnd = serviceEnd;
  }
  
  public void setDuration(double duration) {
    this.duration = duration;
  }
  
  public void setAdditionalNotes(String additionalNotes) {
    this.additionalNotes = additionalNotes;
  }
}
