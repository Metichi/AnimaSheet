package es.metichi.animabeyondfantasy.CharacterSheet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Power implements Serializable {
    protected String name;
    private String description;
    private String source;
    private ArrayList<Modifier> powerModifiers;

    public Power(){
        this.name = "No name";
        this.description = "No description";
        this.source = "No source";
        powerModifiers = new ArrayList<>(0);
    }

    public Power(String name, String description, String source, ArrayList<Modifier> powerModifiers){
        this.name = name;
        this.description = description;
        this.source = source;
        this.powerModifiers = powerModifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<Modifier> getPowerModifiers() {
        return powerModifiers;
    }
}
