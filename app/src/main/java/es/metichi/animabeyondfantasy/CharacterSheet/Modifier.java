package es.metichi.animabeyondfantasy.CharacterSheet;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Modifier {
    private int value;
    private String source;
    private String description;
    private String[] affectedFields;

    public Modifier(int value, String source, String description, String[] affectedFields){
        this.value = value;
        this.source = source;
        this.description = description;
        this.affectedFields = affectedFields;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String[] getAffectedFields() {
        return affectedFields;
    }

    public void setAffectedFields(String[] affectedFields) {
        this.affectedFields = affectedFields;
    }
}
