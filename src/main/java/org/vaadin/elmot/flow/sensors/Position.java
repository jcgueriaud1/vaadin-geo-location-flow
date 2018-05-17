package org.vaadin.elmot.flow.sensors;

import java.io.Serializable;
import java.util.Date;

public class Position implements Serializable {
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double accuracy;
    private Double altitudeAccuracy;
    private Double heading;
    private Double speed;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(Double altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Position{" +
                "[" + latitude +
                ";  " + longitude +
                "] +-" + accuracy +
                "; alt: " + altitude +
                " +-" + altitudeAccuracy +
                "; hdg: " + heading +
                "; v: " + speed +
                "m/s; timestamp:" + new Date(timestamp).toString() +
                '}';
    }
}
