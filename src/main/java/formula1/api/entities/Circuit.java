package formula1.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Circuit {

    private @Id @GeneratedValue Long id;
    private String circuitRef;
    private String name;
    private String location;
    private String country;
    private double lat;
    private double lng;
    private int alt;
    private String url;

    public Circuit() { }

    public Circuit(Long id, String circuitRef, String name, String location, String country, double lat, double lng, int alt, String url) {
        this.id = id;
        this.circuitRef = circuitRef;
        this.name = name;
        this.location = location;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCircuitRef() {
        return circuitRef;
    }

    public void setCircuitRef(String circuitRef) {
        this.circuitRef = circuitRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
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
        Circuit circuit = (Circuit) o;
        return id == circuit.id && Double.compare(circuit.lat, lat) == 0 && Double.compare(circuit.lng, lng) == 0 && alt == circuit.alt && Objects.equals(circuitRef, circuit.circuitRef) && Objects.equals(name, circuit.name) && Objects.equals(location, circuit.location) && Objects.equals(country, circuit.country) && Objects.equals(url, circuit.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, circuitRef, name, location, country, lat, lng, alt, url);
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "id=" + id +
                ", circuitRef='" + circuitRef + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", alt=" + alt +
                ", url='" + url + '\'' +
                '}';
    }
}
