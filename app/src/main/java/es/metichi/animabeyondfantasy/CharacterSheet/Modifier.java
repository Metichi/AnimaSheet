package es.metichi.animabeyondfantasy.CharacterSheet;

/**
 * Created by Metichi on 31/07/2017.
 */

public abstract class Modifier {
    private int value;
    private String source;
    private String description;

    public Modifier(int value, String source, String description){
        this.value = value;
        this.source = source;
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public abstract void giveTo(Character character);
    public abstract void removeFrom(Character character);
}
