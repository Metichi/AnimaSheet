package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Metichi on 01/08/2017.
 */

public class Skill {
    protected Characteristic characteristic;
    protected ArrayList<Modifier> modifiers;
    protected ArrayList<Category> categories;
    private Skill(){}

    public Skill(Characteristic characteristic, ArrayList<Category> categories){
        this.characteristic = characteristic;
        this.categories = categories;
        this.modifiers = new ArrayList<>(0);
    }

    public int getSkillModifier(){
        return characteristic.getSkillModifier();
    }
    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public static class CombatSkill extends Skill {
        public CombatSkill(Characteristic characteristic, ArrayList<Category> categories){
            super(characteristic,categories);
        }
    }
    public static class MysticSkill extends Skill {
        public MysticSkill(Characteristic characteristic, ArrayList<Category> categories){
            super(characteristic,categories);
        }
    }
    public static class PsychicSkill extends Skill{
        public PsychicSkill(Characteristic characteristic, ArrayList<Category> categories){
            super(characteristic,categories);
        }
    }
    public static class SecondarySkill extends Skill{
        public SecondarySkill(Characteristic characteristic, ArrayList<Category> categories){
            super(characteristic,categories);
        }
    }
}
