package es.metichi.animabeyondfantasy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Character;
import es.metichi.animabeyondfantasy.CharacterSheet.Characteristic;
import es.metichi.animabeyondfantasy.CharacterSheet.Spendable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterEditor} interface
 * to handle interaction events.
 */
public class Fragment_General extends Fragment {
    private Adapter_Sheet sheetAdapter;
    private LinearLayoutManager layoutManager;
    private CharacterEditor mListener;
    private RecyclerView fragmentList;

    TextView characterName, gender, age, origin, height, weight, hairColor, eyeColor;
    HashMap<String,TextView[]> characteristics;

    public Fragment_General() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_general, container, false);
        fragmentList = fragmentView.findViewById(R.id.general_fragment_list);
        fragmentList.setHasFixedSize(true);
        fragmentList.setLayoutManager(layoutManager);
        fragmentList.setAdapter(sheetAdapter);
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CharacterEditor) {
            mListener = (CharacterEditor) context;
            layoutManager = new LinearLayoutManager(context);
            characteristics = new HashMap<>();
            sheetAdapter = createSheetAdapter(context);
            mListener.setTitle(R.string.generalFragementTitle);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CharacterEditor");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Adapter_Sheet createSheetAdapter(Context context){
        ArrayList<CharacterDisplayBundle> bundles = new ArrayList<>(1);
        bundles.add(generateCharacterDescriptionBundle(context));
        bundles.add(generateSpendableBundle(context));
        bundles.add(generatePrimaryCharacteristicBundle(context));
        bundles.add(generateSecondaryCharacteristicBundle(context));

        Adapter_Sheet adapter = new Adapter_Sheet(bundles);
        return adapter;
    }



    private CharacterDisplayBundle generateCharacterDescriptionBundle(Context context){
        View characterDescription = LayoutInflater.from(context).inflate(R.layout.cdb_character_description,null);
        characterName = characterDescription.findViewById(R.id.characterName);
        age = characterDescription.findViewById(R.id.characterAge);
        origin = characterDescription.findViewById(R.id.characterOrigin);
        height = characterDescription.findViewById(R.id.characterHeight);
        weight = characterDescription.findViewById(R.id.characterWeight);
        hairColor = characterDescription.findViewById(R.id.characterHairColor);
        eyeColor = characterDescription.findViewById(R.id.characterEyeColor);
        gender = characterDescription.findViewById(R.id.characterGender);

        String title = "Descripcion de personaje";
        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Que edite los valores mediante un menú
            }
        };
        CharacterDisplayBundle bundle = new CharacterDisplayBundle(title,characterDescription,listener);
        updateCharacterDescription();
        return bundle;
    }
    private CharacterDisplayBundle generatePrimaryCharacteristicBundle(Context context){
        String title = "Características";
        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Este botón te permite redistribuir la tirada de característica
            }
        };

        TableLayout characteristicTable = (TableLayout) LayoutInflater.from(context).inflate(R.layout.cdb_characteristics,null);
        for (Characteristic c : mListener.getCharacter().getPrimaryCharacteristics()){
            TableRow newRow = (TableRow) LayoutInflater.from(context).inflate(R.layout.cdb_characteristics_emptyrow,null);
            TextView characteristicName = newRow.findViewById(R.id.characteristicName);
            TextView characteristicBase = newRow.findViewById(R.id.characteristicBase);
            TextView characteristicBonus = newRow.findViewById(R.id.characteristicBonus);
            TextView characteristicFinal = newRow.findViewById(R.id.characteristicFinal);
            characteristicTable.addView(newRow);
            TextView[] characteristic = new TextView[] {characteristicName,characteristicBase,characteristicBonus,characteristicFinal};
            characteristics.put(c.getName(),characteristic);
        }
        updatePrimaryCharacteristics();

        CharacterDisplayBundle bundle = new CharacterDisplayBundle(title,characteristicTable,listener);
        return bundle;
    }
    private CharacterDisplayBundle generateSecondaryCharacteristicBundle(Context context){
        String title = "Características secundarias";
        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Este botón te permite redistribuir la tirada de característica
            }
        };

        TableLayout characteristicTable = (TableLayout) LayoutInflater.from(context).inflate(R.layout.cdb_secondary_characteristics,null);
        for (String key : mListener.getCharacter().getSecondaryCharacteristics().keySet()){
            Characteristic c = mListener.getCharacter().getSecondaryCharacteristics().get(key);
            TableRow newRow = (TableRow) LayoutInflater.from(context).inflate(R.layout.cdb_secondary_characteristics_emptyrow,null);
            TextView characteristicName = newRow.findViewById(R.id.characteristicName);
            TextView characteristicFinal = newRow.findViewById(R.id.characteristicFinal);
            characteristicTable.addView(newRow);
            TextView[] characteristic = new TextView[] {characteristicName,characteristicFinal};
            characteristics.put(c.getName(),characteristic);
        }
        updateSecondaryCharacteristics();

        CharacterDisplayBundle bundle = new CharacterDisplayBundle(title,characteristicTable,listener,true);
        return bundle;
    }
    private CharacterDisplayBundle generateSpendableBundle(Context context){
        ArrayList<Spendable> spendables = new ArrayList<>();
        spendables.add((Spendable) mListener.getCharacter().getHp());
        spendables.add((Spendable) mListener.getCharacter().getSecondaryCharacteristics().get("Stamina"));
        Adapter_Spendable adapter = new Adapter_Spendable(spendables);
        RecyclerView layout = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.cdb_spendables,null);
        layout.setAdapter(adapter);
        layout.setLayoutManager(new LinearLayoutManager(context));

        String title = "Consumibles";
        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        };

        return new CharacterDisplayBundle(title,layout,listener,true);

    }
    private void updateCharacterDescription(){
        Character character = mListener.getCharacter();
        characterName.setText(character.getName());
        age.setText(String.valueOf(character.getAge()));
        origin.setText(character.getHomeTown());
        height.setText(String.valueOf(character.getHeight()));
        weight.setText(String.valueOf(character.getWeight()));
        hairColor.setText(character.getHairColor());
        eyeColor.setText(character.getEyeColor());
        gender.setText(character.getGender()== Character.Gender.MALE ? "Masculino" : "Femenino");
    }
    private void updatePrimaryCharacteristics(){
        for (Characteristic c : mListener.getCharacter().getPrimaryCharacteristics()){
            TextView[] characteristic = characteristics.get(c.getName());
            characteristic[0].setText(c.getName());
            characteristic[1].setText(String.valueOf(c.getBase()));
            characteristic[2].setText(String.valueOf(c.getSkillBonus()));
            characteristic[3].setText(String.valueOf(c.getFinalValue()));
        }
    }
    private void updateSecondaryCharacteristics(){
        for (String key : mListener.getCharacter().getSecondaryCharacteristics().keySet()){
            Characteristic c = mListener.getCharacter().getSecondaryCharacteristics().get(key);
            TextView[] characteristic = characteristics.get(c.getName());
            characteristic[0].setText(c.getName());
            characteristic[1].setText(String.valueOf(c.getFinalValue()));
        }
    }
    private void updateCharacteristics(){
        updatePrimaryCharacteristics();
        updateSecondaryCharacteristics();
    }
}
