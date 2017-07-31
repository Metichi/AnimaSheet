package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Race {
    private String  name;
    private String description;
    private ArrayList<Power> powers;

    public Race(){
        name = "No name";
        description = "Nodescription";
        powers = new ArrayList<>(0);
    }

    public Race(String name, String description, ArrayList<Power> powers){
        this.name = name;
        this.description = description;
        this.powers = powers;
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

    public ArrayList<Power> getPowers() {
        return powers;
    }
}
