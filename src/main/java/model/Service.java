package model;

public class Service {
    private int serviceId;
    private String serviceName;
    private String categoryName;
    private String description;
    private double price;

    // Empty constructor is needed when creating objects manually in DAO
    public Service() {}

    // Optional full constructor
    public Service(int serviceId, String serviceName, String categoryName, String description, double price) {
        this.serviceId = serviceId;  
        this.serviceName = serviceName;
        this.categoryName = categoryName;
        this.description = description;
        this.price = price;
    }

    // Getters
    public int getServiceId() {
      return serviceId;
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }
    
    public double getPrice() {
      return price;
    }

    // Setters
    public void setServiceId(int serviceId) {
      this.serviceId = serviceId;
  }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrice(double price) {
      this.price = price;
  }
}
