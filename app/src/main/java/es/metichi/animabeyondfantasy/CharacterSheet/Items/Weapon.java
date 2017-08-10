package es.metichi.animabeyondfantasy.CharacterSheet.Items;

import es.metichi.animabeyondfantasy.CharacterSheet.Combat;

/**
 * Created by Metichi on 10/08/2017.
 */

public class Weapon extends Item{
    public Weapon(String name, String description, float weight, Currency value, int quality, int speed, int sturdiness,
                  int breaking, int presence, Combat.DamageType primary, Combat.DamageType secondary,){
        super(name,description, weight, value, quality);
    }

    private static class Special
}