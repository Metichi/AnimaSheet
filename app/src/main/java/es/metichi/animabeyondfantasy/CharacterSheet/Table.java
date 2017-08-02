package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;

import es.metichi.animabeyondfantasy.CharacterSheet.Items.Item;

/**
 * Created by Metichi on 01/08/2017.
 */

public class Table {
    protected int dpCost;
    protected int name;
    protected ArrayList<Power> powers;

    public int getDpCost() {
        return dpCost;
    }

    public abstract static class WeaponTable extends Table{
        ArrayList<Item.Weapon> usableWeapon;
        public boolean canUse(Item.Weapon weapon){
            return usableWeapon.contains(weapon);
        }
    }
    public abstract static class CombatStyleTable extends Table{
    }
    public abstract static class MartialArtsTable extends Table{

    }
    public abstract static class ArsMagnus extends Table{

    }
    public abstract static class MysticTable extends Table{

    }
    public abstract static class PsychicTable extends Table{

    }
    public abstract static class PsychicPattern extends Table{

    }
}
