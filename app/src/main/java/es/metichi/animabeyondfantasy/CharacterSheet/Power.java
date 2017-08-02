package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Power {
    protected String name;
    protected String description;
    protected String source;
    protected ArrayList<Modifier> powerModifiers;

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

    public void givePowerTo(Character character){
        character.getCharacterPowers().add(this);
        for (Modifier m : powerModifiers){
            m.giveTo(character);
        }
    }
    public void removePowerFrom(Character character){
        for(Modifier m : powerModifiers){
            m.removeFrom(character);
        }
        character.getCharacterPowers().remove(this);
    }
}
