package models;

public class ItemModelAverage implements Item{

    private final String name;
    private final double value;

    public ItemModelAverage(String name, double value){
        this.name=name;
        this.value = value;

    }
    @Override
    public Object getFirstField() {
        return name;
    }

    @Override
    public Object getSecondField() {
        return value;
    }
}
