package es.metichi.animabeyondfantasy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import es.metichi.animabeyondfantasy.CharacterSheet.Character;
import es.metichi.animabeyondfantasy.CharacterSheet.CharacteristicRoll;
import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.CharacteristicRollDefinitions;

public class Activity_LoadCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_create);
        initUI();
    }
    private void initUI(){
        ImageButton create = (ImageButton) findViewById(R.id.newCharacterButton);
        ImageButton load = (ImageButton) findViewById(R.id.loadButton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCharacteristickRoll();
            }
        });
    }


    //region Create Character steps
    private void pickCharacteristickRoll(){
        ListView menu = (ListView) getLayoutInflater().inflate(R.layout.characteristicroll_select,null);
        menu.setAdapter(new ArrayAdapter<String>(this,R.layout.menu_items,getResources().getStringArray(R.array.characteristicRolls)));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.characteristicRollPick))
                .setView(menu)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        CharacteristicRollPicker picker = new CharacteristicRollPicker(dialog);
        menu.setOnItemClickListener(picker);
        dialog.show();
    }
    private class CharacteristicRollPicker implements AdapterView.OnItemClickListener{
        AlertDialog dialog;
        CharacteristicRollPicker(AlertDialog dialog){
            this.dialog = dialog;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CharacteristicRoll characteristicRoll;
            switch (i) {
                case 0:
                    characteristicRoll = new CharacteristicRollDefinitions.CustomRoll();
                    break;
                default:
                    characteristicRoll = null;
            }
            dialog.dismiss();
            createCharacter(characteristicRoll);
        }
    }
    private void createCharacter(CharacteristicRoll characteristicRoll){
        Character character = new Character(characteristicRoll);
        Intent intent = new Intent(this,Activity_Sheet.class);
        intent.putExtra("CHARACTER",character);
        startActivity(intent);
    }

}
