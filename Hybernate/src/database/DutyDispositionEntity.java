package database;

/**
 * Created by Home on 06.04.2017.
 */
public class DutyDispositionEntity {
    private int eventDuty;
    private int musician;
    private double points;
    private String description;
    private Enum dutyDispositionStatus;

    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    public int getMusician() {
        return musician;
    }

    public void setMusician(int musician) {
        this.musician = musician;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enum getDutyDispositionStatus() {
        return dutyDispositionStatus;
    }

    public void setDutyDispositionStatus(Enum dutyDispositionStatus) {
        this.dutyDispositionStatus = dutyDispositionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DutyDispositionEntity that = (DutyDispositionEntity) o;

        if (eventDuty != that.eventDuty) return false;
        if (musician != that.musician) return false;
        if (Double.compare(that.points, points) != 0) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (dutyDispositionStatus != null ? !dutyDispositionStatus.equals(that.dutyDispositionStatus) : that.dutyDispositionStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = eventDuty;
        result = 31 * result + musician;
        temp = Double.doubleToLongBits(points);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dutyDispositionStatus != null ? dutyDispositionStatus.hashCode() : 0);
        return result;
    }
}
