package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Characteristic implements Modifyable {
    String name;
    protected int base;
    protected ArrayList<Modifier> modifiers;

    public Characteristic(int base){
        this.base = base;
    }

    public int getBase() {
        return base;
    }
    public int getModifierValue(){
        int v = 0;
        for (Modifier m : modifiers){
            v+=m.getValue();
        }
        return v;
    }
    public int getFinalValue(){
        int v = base;
        for (Modifier m : modifiers){
            v += m.getValue();
        }
        return v;
    }
    public int getUsable(Character.Inhumanity i){return this.getFinalValue();}

    public int getSkillBonus(){
        NavigableMap<Integer,Integer> skillModifiers = new TreeMap<Integer,Integer>();
        skillModifiers.put(1,-30);
        skillModifiers.put(2,-20);
        skillModifiers.put(3,-10);
        skillModifiers.put(4,-5);
        skillModifiers.put(5,0);
        skillModifiers.put(6,5);
        skillModifiers.put(8,10);
        skillModifiers.put(10,15);
        skillModifiers.put(11,20);
        skillModifiers.put(13,25);
        skillModifiers.put(15,30);
        skillModifiers.put(16,35);
        skillModifiers.put(18,40);
        skillModifiers.put(20,45);

        return skillModifiers.get(skillModifiers.floorKey(getFinalValue()));
    }
    public boolean isPhysical(){
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class PhysicalCharacteristic extends Characteristic{
        public static int humanLimit = 10;
        public static int inhumanLimit = 13;
        public PhysicalCharacteristic(int v){
            super(v);
        }
        @Override
        public boolean isPhysical() {
            return true;
        }

        @Override
        public int getUsable(Character.Inhumanity i){
            switch (i){
                case HUMAN:
                    return Math.min(this.getFinalValue(),humanLimit);
                case INHUMAN:
                    return Math.min(this.getFinalValue(),inhumanLimit);
                case ZEN:
                    return this.getFinalValue();
                default: return this.getFinalValue();
            }
        }
    }
    public static class IntellectualCharacteristic extends Characteristic{
        public IntellectualCharacteristic(int v){
            super(v);
        }
    }

    public static class CharacteristicModifier extends Modifier{
        public CharacteristicModifier(int value, String source, String description, String[] affectedFields){
            super(value, source, description, affectedFields);
        }
    }

    //region Modifyable interface
    @Override
    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public void remove(Modifier modifier) {
        modifiers.remove(modifier);
    }

    @Override
    public void add(Modifier modifier) {
        modifiers.add(modifier);
    }
    //endregion
}