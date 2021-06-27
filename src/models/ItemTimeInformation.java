package models;

public class ItemTimeInformation implements Item {
    private long aliveTime;
    private long idleTime;

    public ItemTimeInformation(long aliveTime,long idleTime){
        this.aliveTime = aliveTime;
        this.idleTime = idleTime;
    }

    @Override
    public Long getFirstField() {
        return aliveTime;
    }

    @Override
    public Long getSecondField() {
        return idleTime;
    }
}
