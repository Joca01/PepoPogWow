package models;

public class ItemModelAverage implements Item{

    private final String name;
    private final float value;

    public ItemModelAverage(String name, float value){
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
