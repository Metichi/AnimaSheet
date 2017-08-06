package es.metichi.animabeyondfantasy;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import es.metichi.animabeyondfantasy.CharacterSheet.Character;

public class SheetActivity extends AppCompatActivity {
    private String[] mSheetTabs;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ConstraintLayout mHeader;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);
        mSheetTabs = getResources().getStringArray(R.array.sheetTabs);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mHeader = (ConstraintLayout) getLayoutInflater().inflate(R.layout.menu_header,null);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.menu_items,mSheetTabs));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.addHeaderView(mHeader);

        character = (Character) getIntent().getSerializableExtra("CHARACTER");

        updateHeader();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectItem(i);
        }
    }
    private void selectItem(int position){
    }

    public void updateHeader(){
        TextView characterName = (TextView) mHeader.findViewById(R.id.characterName);
        TextView characterLevel = (TextView) mHeader.findViewById(R.id.characterLevel);
        TextView characterCategory = (TextView) mHeader.findViewById(R.id.characterCategory);

        characterName.setText(character.getName());
        characterLevel.setText(String.valueOf(character.getTotalLevel()));
        characterCategory.setText(character.getCurrentCategory() == null ? getResources().getText(R.string.noCategory):character.getCurrentCategory().getName());
    }
}