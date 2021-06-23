package models;

import java.util.Arrays;

public class ItemTimePeriod implements Item {
    private final int  min;
    private final int  max;
    private final int[] timeArray = new int[2];

    public ItemTimePeriod(int min, int max) {
        this.min = min;
        this.max = max;
        timeArray[0] = min;
        timeArray[1] = max;
    }

    public Integer getFirstField() {
        return min;
    }

    public Integer getSecondField() {
        return max;
    }

    public int[] getTimeArray() {
        return timeArray;
    }

    @Override
    public String toString() {
        return "ItemTimePeriod{" +
                "min=" + min +
                ", max=" + max +
                ", item=" + Arrays.toString(timeArray) +
                '}';
    }
}
