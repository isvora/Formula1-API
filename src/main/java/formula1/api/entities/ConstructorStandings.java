package formula1.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ConstructorStandings {

    private @Id @GeneratedValue Long id;
    private Long raceId;
    private Long constructorId;
    private double points;
    private int position;
    private String positionText;
    private int wins;

    public ConstructorStandings() {
    }

    public ConstructorStandings(Long id, Long raceId, Long constructorId, double points, int position, String positionText, int wins) {
        this.id = id;
        this.raceId = raceId;
        this.constructorId = constructorId;
        this.points = points;
        this.position = position;
        this.positionText = positionText;
        this.wins = wins;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        ConstructorStandings that = (ConstructorStandings) o;
        return Double.compare(that.points, points) == 0 && position == that.position && wins == that.wins && Objects.equals(id, that.id) && Objects.equals(raceId, that.raceId) && Objects.equals(constructorId, that.constructorId) && Objects.equals(positionText, that.positionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, raceId, constructorId, points, position, positionText, wins);
    }

    @Override
    public String toString() {
        return "ConstructorStandings{" +
                "id=" + id +
                ", raceId=" + raceId +
                ", constructorId=" + constructorId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                '}';
    }
}
