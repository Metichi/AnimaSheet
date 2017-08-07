package es.metichi.animabeyondfantasy.CharacterSheet;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A skill is a value affected by a characteristic and the categories of a character.
 *
 * The skill is
 * Created by Metichi on 01/08/2017.
 */

public class Skill implements Modifyable, Serializable{
    protected Characteristic characteristic;
    protected ArrayList<Modifier> modifiers;
    protected ArrayList<Category> categories;
    protected NaturalModifier naturalModifier;
    protected String name;
    private Skill(){}

    public Skill(String name, @Nullable Characteristic characteristic, ArrayList<Category> categories){
        this.characteristic = characteristic;
        this.categories = categories;
        this.modifiers = new ArrayList<>(0);
        this.name = name;
        naturalModifier = new NaturalModifier();
    }

    /**
     * Characteristic Bonus to the skill
     *
     * Returns the value of the characteristic related to this skill to be used by the natural modifier.
     * This method should be Overriden by specific cases where the skill has its own way of calculating the
     * natural bonus, like CV, HP, Innitiative...
     * @return
     */
    public int getCharacteristicBonus(){
        if (characteristic == null){
            return 0;
        } else {
            return characteristic.getSkillBonus();
        }
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public String getName() {
        return name;
    }

    public int getBaseValue(){
        int baseValue = 0;
        for (Category category : categories){
            baseValue += category.getDPInvestedOn(this)/category.getCostOf(this);
        }
        return baseValue;
    }
    public int getNaturalBonus(){
        return naturalModifier.getValue();
    }

    public ArrayList<Category.CategoryModifier> getCategoryModifiers(){
        ArrayList<Category.CategoryModifier> categoryModifiers = new ArrayList<>(categories.size());
        for (Category category : categories){
            Category.CategoryModifier categoryModifier = category.getCategoryModifierOf(this);
            if (categoryModifier != null) {
                categoryModifiers.add(category.getCategoryModifierOf(this));
            }
        }
        return categoryModifiers;
    }
    public int getCategoryBonus(){
        int bonus = 0;
        for (Category.CategoryModifier modifier : getCategoryModifiers()){
            bonus += modifier.getValue();
        }
        return bonus;
    }
    public int getSpecialBonus(){
        int bonus = 0;
        for(Modifier modifier : modifiers){
            bonus += modifier.getValue();
        }
        return bonus;
    }

    public int getFinalValue(){
        return getBaseValue() + getNaturalBonus() + getCategoryBonus() + getSpecialBonus();
    }


    //region Interface Implementation
    @Override
    public ArrayList<Modifier> getModifiers() {
        ArrayList<Modifier> modifiers = new ArrayList<>(0);
        for(Modifier m : this.modifiers){
            modifiers.add(m);
        }
        for(Modifier m : getCategoryModifiers()){
            modifiers.add(m);
        }
        modifiers.add(naturalModifier);
        return modifiers;
    }

    @Override
    public void add(Modifier modifier) {
        modifiers.add(modifier);
    }

    @Override
    public void remove(Modifier modifier) {
            modifiers.remove(modifier);
    }
    //endregion

    public static class SkillModifier extends Modifier{
        SkillModifier(int value, String source, String description, String[] affectedFields){
            super(value,source,description,affectedFields);
        }
    }
    public class NaturalModifier extends SkillModifier{
        public NaturalModifier(){
            super(0,"Bonificador natural",
                    "Bono dependiente de la característica",
                    new String[0]);
        }
        @Override
        public int getValue(){
            this.setValue(getCharacteristicBonus());
            return getCharacteristicBonus();
        }
    }

    public static class CombatSkill extends Skill {
        public CombatSkill(String name, Characteristic characteristic, ArrayList<Category> categories){
            super(name, characteristic,categories);
        }
        public static class AttackDefense extends CombatSkill{
            public AttackDefense(String name, Characteristic characteristic, ArrayList<Category> categories){
                super(name, characteristic,categories);
            }
            @Override
            public int getCategoryBonus(){
                return Math.min(50,super.getCategoryBonus());
            }
        }
        public static class KiPoint extends SpendableCombatSkill{
            public KiPoint(Characteristic characteristic, ArrayList<Category> categories){
                super(characteristic.getName()+"Ki", characteristic, categories);
            }
            @Override
            public int getCategoryBonus(){
                return 0;
            }

            @Override
            public int getCharacteristicBonus(){
                if (characteristic.getFinalValue() <= 10){
                    return characteristic.getFinalValue();
                } else {
                    return 10 + (characteristic.getFinalValue()-10)*2;
                }
            }
        }
        public static class KiAcumulation extends CombatSkill{
            public KiAcumulation(Characteristic characteristic, ArrayList<Category> categories){
                super(characteristic.getName()+"Acu", characteristic, categories);
            }
            @Override
            public int getCategoryBonus(){
                return 0;
            }

            @Override
            public int getCharacteristicBonus(){
                int characteristicValue = characteristic.getFinalValue();
                if (characteristicValue <= 9){
                    return 1;
                } else if (characteristicValue <= 12){
                    return 2;
                } else if (characteristicValue <= 15){
                    return 3;
                } else {
                    return 4;
                }
            }
        }
        public static class SpendableCombatSkill extends Skill.CombatSkill implements Spendable{
            private int currentValue;
            public SpendableCombatSkill(String s, Characteristic c, ArrayList<Category>categories){
                super(s,c,categories);
                currentValue = getMaxValue();
            }

            @Override
            public int getMaxValue() {
                return getFinalValue();
            }

            @Override
            public void setCurrentValue(int currentValue) {
                this.currentValue = currentValue;
            }

            @Override
            public int getCurrentValue() {
                return currentValue;
            }
        }
    }
    public static class MysticSkill extends Skill {
        public MysticSkill(String name, Characteristic characteristic, ArrayList<Category> categories){
            super(name, characteristic,categories);
        }
        public static class SpendableMysticSkill extends MysticSkill implements Spendable{
            private int currentValue;
            public SpendableMysticSkill(String s, Characteristic c, ArrayList<Category> cat){
                super(s,c,cat);
                currentValue = getMaxValue();
            }

            @Override
            public int getMaxValue() {
                return getFinalValue();
            }

            @Override
            public int getCurrentValue() {
                return currentValue;
            }

            @Override
            public void setCurrentValue(int currentValue) {
                this.currentValue = currentValue;
            }
        }
    }
    public static class PsychicSkill extends Skill{
        public PsychicSkill(String name, Characteristic characteristic, ArrayList<Category> categories){
            super(name, characteristic,categories);
        }
        public static class SpendablePsychicSkill extends PsychicSkill implements Spendable{
            private int currentValue;
            public SpendablePsychicSkill(String s, Characteristic c, ArrayList<Category> cat){
                super(s,c,cat);
                currentValue = getMaxValue();
            }

            @Override
            public int getMaxValue() {
                return getFinalValue();
            }

            @Override
            public int getCurrentValue() {
                return currentValue;
            }

            @Override
            public void setCurrentValue(int currentValue) {
                this.currentValue = currentValue;
            }
        }
    }
    public static class SecondarySkill extends Skill{
        private NaturalModifier naturalModifier;
        private InnateModifier innateModifier;
        int naturalBonusPoints;
        int innatePoints;
        public enum SecondarySkillType{
            ATHLETIC,
            SOCIAL,
            PERCEPTIVE,
            INTELLECTUAL,
            VIGOR,
            SUBTERFUGE,
            CREATIVE
        }
        private SecondarySkillType type;
        public SecondarySkill(String name, Characteristic characteristic, ArrayList<Category> categories, SecondarySkillType type){
            super(name, characteristic,categories);
            this.type = type;
            naturalModifier = new NaturalModifier();
            naturalModifier.setDescription("Un modifcador natural suma el bono de característica");
            innateModifier = new InnateModifier();
            modifiers.add(naturalModifier);
            modifiers.add(innateModifier);
            naturalBonusPoints = 0;
        }

        public int getNaturalBonusPoints() {
            return naturalBonusPoints;
        }

        public void setNaturalBonusPoints(int naturalBonus) {this.naturalBonusPoints = naturalBonus;}

        public int getInnatePoints() {
            return innatePoints;
        }

        public void setInnatePoints(int innatePoints) {
            this.innatePoints = innatePoints;
        }

        public SecondarySkillType getType() {
            return type;
        }

        @Override
        public int getCharacteristicBonus(){
            return super.getCharacteristicBonus()*(getNaturalBonusPoints()+1);
        }

        @Override
        public int getNaturalBonus(){
            int bonus = super.getNaturalBonus();
            bonus += innateModifier.getValue();

            return Math.min(100,bonus);
        }

        public class InnateModifier extends Modifier{
            public InnateModifier(){
                super(0,"Bonificador Innato","Cada punto de innato da un +10 a la habilidad", new String[0]);
                innatePoints = 0;
            }

            @Override
            public int getValue(){
                this.setValue(10*innatePoints);
                return 10*innatePoints;
            }
        }
    }
    public static class SpendableSkill extends Skill implements Spendable{
        private int currentValue;
        public SpendableSkill(String s, Characteristic c, ArrayList<Category> cat){
            super(s,c,cat);
            currentValue = getMaxValue();
        }

        @Override
        public int getMaxValue() {
            return getFinalValue();
        }

        @Override
        public int getCurrentValue() {
            return currentValue;
        }

        @Override
        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }
    }
}
