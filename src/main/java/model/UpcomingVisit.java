package model;

import java.time.LocalDate;

public class UpcomingVisit {
    private int id;
    private String name;
    private String relationship;
    private LocalDate visitDate;
    private String visitTime;
    private String purposeOfVisit;

    public UpcomingVisit() {}

    public UpcomingVisit(int id, String name, String relationship,
                         LocalDate visitDate, String visitTime,
                         String purposeOfVisit) {
        this.id = id;
        this.name = name;
        this.relationship = relationship;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.purposeOfVisit = purposeOfVisit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRelationship() {
        return relationship;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }
}
