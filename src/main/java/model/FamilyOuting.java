package model;

public class FamilyOuting {

  private int id;
  private String serviceName;
  private String serviceDate;
  private String additionalNotes;
  private int serviceDuration;
  private String serviceStart;
  private String serviceEnd;
  private String status;
  private int numOfParticipants;
  private String location;
  
  public int getNumOfParticipants() {
    return numOfParticipants;
  }
  
  public void setNumOfParticipants(int numOfParticipants) {
    this.numOfParticipants = numOfParticipants; 
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location; 
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id; 
  }
  
  public String getServiceName() {
    return serviceName;
  }
  
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName; 
  }
  
  public String getServiceDate() {
    return serviceDate;
  }
  
  public void setServiceDate(String serviceDate) {
    this.serviceDate = serviceDate; 
  }
  
  public String getAdditionalNotes() {
    return additionalNotes;
  }
  
  public void setAdditionalNotes(String additionalNotes) {
    this.additionalNotes = additionalNotes; 
  }
  
  public int getServiceDuration() {
    return serviceDuration;
  }
  
  public void setServiceDuration(int serviceDuration) {
    this.serviceDuration = serviceDuration; 
  }
  
  public String getServiceStart() {
    return serviceStart;
  }
  
  public void setServiceStart(String serviceStart) {
    this.serviceStart = serviceStart; 
  }
  
  public String getServiceEnd() {
    return serviceEnd;
  }
  
  public void setServiceEnd(String serviceEnd) {
    this.serviceEnd = serviceEnd; 
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status; 
  }
}
