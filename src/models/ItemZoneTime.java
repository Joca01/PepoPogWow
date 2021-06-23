package models;

public class ItemZoneTime implements Item {
    private float time;
    private Zone zone;

    public ItemZoneTime(Zone zone, float time) {
        this.time = time;
        this.zone = zone;
    }

    public Zone getFirstField() {
        return zone;
    }

    public Float getSecondField() {
        return time;
    }

    @Override
    public String toString() {
        return "ItemZoneTime{" +
                "time=" + time +
                ", zone=" + zone +
                '}';
    }
}
