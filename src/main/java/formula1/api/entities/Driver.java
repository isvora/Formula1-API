package formula1.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Driver {

    private @Id @GeneratedValue Long driverId;
    private String driverRef;
    private int number;
    private String code;
    private String forename;
    private String surname;
    private Date dob;
    private String nationality;
    private String url;

    public Driver() {
    }

    public Driver(Long driverId, String driverRef, int number, String code, String forename, String surname, Date dob, String nationality, String url) {
        this.driverId = driverId;
        this.driverRef = driverRef;
        this.number = number;
        this.code = code;
        this.forename = forename;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
        this.url = url;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverRef() {
        return driverRef;
    }

    public void setDriverRef(String driverRef) {
        this.driverRef = driverRef;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return number == driver.number &&
                Objects.equals(driverId, driver.driverId) &&
                Objects.equals(driverRef, driver.driverRef) &&
                Objects.equals(code, driver.code) &&
                Objects.equals(forename, driver.forename) &&
                Objects.equals(surname, driver.surname) &&
                Objects.equals(dob, driver.dob) &&
                Objects.equals(nationality, driver.nationality) &&
                Objects.equals(url, driver.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, driverRef, number, code, forename, surname, dob, nationality, url);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", driverRef='" + driverRef + '\'' +
                ", number=" + number +
                ", code='" + code + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", nationality='" + nationality + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
