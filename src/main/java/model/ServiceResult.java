package model;

public class ServiceResult {
    private String id;
    private String name;
    private String category;
    private String description;
    private String price;
    private String duration;
    private String status;
  
    public ServiceResult(String id, String name, String category, String description, String price, String duration, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.status = status;
    }
  
    public String getid() {
        return id;
    }
  
    public String getname() {
        return name;
    }
    
    public String getcategory() {
        return category;
    }
    
    public String getdescription() {
      return description;
    }
    
    public String getprice() {
        return price;
    }
    
    public String getduration() {
      return duration;
    }
    
    public String getstatus() {
        return status;
    }
}