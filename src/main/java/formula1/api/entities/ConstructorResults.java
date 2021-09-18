package formula1.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ConstructorResults {

    private @Id @GeneratedValue Long constructorResultsId;
    private Long raceId;
    private Long constructorId;
    private double points;
    private String status;

    public ConstructorResults() {
    }

    public ConstructorResults(Long constructorResultsId, Long raceId, Long constructorId, double points, String status) {
        this.constructorResultsId = constructorResultsId;
        this.raceId = raceId;
        this.constructorId = constructorId;
        this.points = points;
        this.status = status;
    }

    public Long getConstructorResultsId() {
        return constructorResultsId;
    }

    public void setConstructorResultsId(Long constructorResultsId) {
        this.constructorResultsId = constructorResultsId;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(Long constructorId) {
        this.constructorId = constructorId;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructorResults that = (ConstructorResults) o;
        return points == that.points && Objects.equals(constructorResultsId, that.constructorResultsId) && Objects.equals(raceId, that.raceId) && Objects.equals(constructorId, that.constructorId) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constructorResultsId, raceId, constructorId, points, status);
    }

    @Override
    public String toString() {
        return "ConstructorResults{" +
                "constructorResultsId=" + constructorResultsId +
                ", raceId=" + raceId +
                ", constructorId=" + constructorId +
                ", points=" + points +
                ", status='" + status + '\'' +
                '}';
    }
}
