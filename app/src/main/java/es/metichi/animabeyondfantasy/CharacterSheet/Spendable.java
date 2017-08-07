package es.metichi.animabeyondfantasy.CharacterSheet;

/**
 * Created by Metichi on 07/08/2017.
 */

public interface Spendable {
    int getMaxValue();
    int getCurrentValue();
    void setCurrentValue(int value);
    String getName();
}
