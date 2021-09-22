package formula1.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class LapTime {
    private @Id @GeneratedValue Long id;
    private Long raceId;
    private Long driverId;
    private int lap;
    private int position;
    private String time;
    private int miliseconds;

    public LapTime() {
    }

    public LapTime(Long raceId, Long driverId, int lap, int position, String time, int miliseconds) {
        this.raceId = raceId;
        this.driverId = driverId;
        this.lap = lap;
        this.position = position;
        this.time = time;
        this.miliseconds = miliseconds;
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

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMiliseconds() {
        return miliseconds;
    }

    public void setMiliseconds(int miliseconds) {
        this.miliseconds = miliseconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LapTime lapTime = (LapTime) o;
        return miliseconds == lapTime.miliseconds &&
                Objects.equals(id, lapTime.id) &&
                Objects.equals(raceId, lapTime.raceId) &&
                Objects.equals(driverId, lapTime.driverId) &&
                Objects.equals(lap, lapTime.lap) &&
                Objects.equals(position, lapTime.position) &&
                Objects.equals(time, lapTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, raceId, driverId, lap, position, time, miliseconds);
    }

    @Override
    public String toString() {
        return "LapTime{" +
                "id=" + id +
                ", raceId=" + raceId +
                ", driverId=" + driverId +
                ", lap=" + lap +
                ", position=" + position +
                ", time='" + time + '\'' +
                ", miliseconds=" + miliseconds +
                '}';
    }
}
