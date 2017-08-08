package es.metichi.animabeyondfantasy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Skill;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterEditor} interface
 * to handle interaction events.
 */
public class Fragment_Secondary_Skill extends Fragment {
    private CharacterEditor mListener;
    private HashMap<String,TextView[]> secondarySkills;
    private Adapter_Sheet mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView skillGroupList;

    public Fragment_Secondary_Skill() {
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
        View layout = inflater.inflate(R.layout.fragment_secondary_skill, container, false);
        skillGroupList = layout.findViewById(R.id.secondarySkillRecycler);
        skillGroupList.setAdapter(mAdapter);
        skillGroupList.setLayoutManager(mManager);
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        skillGroupList.removeAllViews();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CharacterEditor) {
            mListener = (CharacterEditor) context;
            secondarySkills = new HashMap<>();
            mAdapter = generateAdapter(context);
            mListener.setTitle(R.string.secundaryFragmentTitle);
            mManager = new LinearLayoutManager(context);
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

    private Adapter_Sheet generateAdapter(Context context){
        ArrayList<Skill.SecondarySkill> athletics = new ArrayList<>();
        ArrayList<Skill.SecondarySkill> social = new ArrayList<>();
        ArrayList<Skill.SecondarySkill> perceptive = new ArrayList<>();
        ArrayList<Skill.SecondarySkill> intellectual = new ArrayList<>();
        ArrayList<Skill.SecondarySkill> vigor = new ArrayList<>();
        ArrayList<Skill.SecondarySkill> subterfuge= new ArrayList<>();
        ArrayList<Skill.SecondarySkill> creative = new ArrayList<>();

        HashMap<String,Skill> allSkills = mListener.getCharacter().getAllSkills();
        for (String key : allSkills.keySet()){
            if(allSkills.get(key) instanceof Skill.SecondarySkill){
                Skill.SecondarySkill s = (Skill.SecondarySkill) allSkills.get(key);
                switch (s.getType()){
                    case ATHLETIC:
                        athletics.add(s);
                        break;
                    case SOCIAL:
                        social.add(s);
                        break;
                    case PERCEPTIVE:
                        perceptive.add(s);
                        break;
                    case INTELLECTUAL:
                        intellectual.add(s);
                        break;
                    case VIGOR:
                        vigor.add(s);
                        break;
                    case SUBTERFUGE:
                        subterfuge.add(s);
                        break;
                    case CREATIVE:
                        creative.add(s);
                }
            }

        }
        ArrayList<CharacterDisplayBundle> bundles = new ArrayList<>();
        bundles.add(createBundle(context,"Atléticas",athletics));
        bundles.add(createBundle(context,"Sociales",social));
        bundles.add(createBundle(context,"Perceptivas",perceptive));
        bundles.add(createBundle(context,"Intelectuales",intellectual));
        bundles.add(createBundle(context,"Vigor",vigor));
        bundles.add(createBundle(context,"Subterfugio",subterfuge));
        bundles.add(createBundle(context,"Creativas",creative));

        return new Adapter_Sheet(bundles);
    }
    CharacterDisplayBundle createBundle(Context context, String title, ArrayList<Skill.SecondarySkill> skills){
        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        RecyclerView skillGroup = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.cdb_empty_recyclerview,null);
        skillGroup.setLayoutManager(new LinearLayoutManager(context));
        skillGroup.setAdapter(new SecondarySkillAdapter(skills));

        return new CharacterDisplayBundle(title,skillGroup,listener,true);
    }

    private class SecondarySkillAdapter extends RecyclerView.Adapter<SecondarySkillAdapter.SecondarySkillViewHolder>{
        private ArrayList<Skill.SecondarySkill> skills;

        public class SecondarySkillViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout layout;
            public TextView skillName;
            public TextView skillValue;
            public SecondarySkillViewHolder(LinearLayout v){
                super(v);
                layout = v;
                skillName = v.findViewById(R.id.secondary_skill_name);
                skillValue = v.findViewById(R.id.secondary_skill_value);
            }
        }
        public SecondarySkillAdapter(ArrayList<Skill.SecondarySkill> skills){
            this.skills = skills;
        }

        @Override
        public SecondarySkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.cdb_secondary_skill_blank,parent,false);
            return new SecondarySkillViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(SecondarySkillViewHolder holder, int position) {
            Skill.SecondarySkill s = skills.get(position);
            holder.skillName.setText(s.getName());
            int finalValue = s.getFinalValue();
            holder.skillValue.setText(String.valueOf(finalValue));
            secondarySkills.put(s.getName(),new TextView[]{holder.skillName,holder.skillValue});
        }

        @Override
        public int getItemCount() {
            return skills.size();
        }
    }
}
