package formula1.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class DriverStandings {
    private @Id @GeneratedValue Long driverStandingsId;
    private Long raceId;
    private Long driverId;
    private double points;
    private int position;
    private String positionText;
    private int wins;

    public DriverStandings() {
    }

    public DriverStandings(Long driverStandingsId, Long raceId, Long driverId, double points, int position, String positionText, int wins) {
        this.driverStandingsId = driverStandingsId;
        this.raceId = raceId;
        this.driverId = driverId;
        this.points = points;
        this.position = position;
        this.positionText = positionText;
        this.wins = wins;
    }

    public Long getDriverStandingsId() {
        return driverStandingsId;
    }

    public void setDriverStandingsId(Long driverStandingsId) {
        this.driverStandingsId = driverStandingsId;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPositionText() {
        return positionText;
    }

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverStandings that = (DriverStandings) o;
        return Double.compare(that.points, points) == 0 &&
                position == that.position &&
                wins == that.wins &&
                Objects.equals(driverStandingsId, that.driverStandingsId) &&
                Objects.equals(raceId, that.raceId) &&
                Objects.equals(driverId, that.driverId) &&
                Objects.equals(positionText, that.positionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverStandingsId, raceId, driverId, points, position, positionText, wins);
    }

    @Override
    public String toString() {
        return "DriverStandings{" +
                "driverStandingsId=" + driverStandingsId +
                ", raceId=" + raceId +
                ", driverId=" + driverId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                '}';
    }
}
