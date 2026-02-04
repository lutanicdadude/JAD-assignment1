package model;

public class UpcomingAppointments {

    private String service_name;
    private String scheduled_date;

    public UpcomingAppointments() {
    }

    public UpcomingAppointments(String service_name, String scheduled_date) {
        this.service_name = service_name;
        this.scheduled_date = scheduled_date;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getScheduled_date() {
        return scheduled_date;
    }

    public void setScheduled_date(String scheduled_date) {
        this.scheduled_date = scheduled_date;
    }

    @Override
    public String toString() {
        return "UpcomingAppointments{" +
                "service_name='" + service_name + '\'' +
                ", scheduled_date='" + scheduled_date + '\'' +
                '}';
    }
}
