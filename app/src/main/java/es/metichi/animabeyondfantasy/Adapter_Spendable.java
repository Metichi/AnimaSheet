package es.metichi.animabeyondfantasy;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import es.metichi.animabeyondfantasy.CharacterSheet.Spendable;

/**
 * Created by Metichi on 07/08/2017.
 */

public class Adapter_Spendable extends RecyclerView.Adapter<Adapter_Spendable.SpendableViewHolder>{
    private ArrayList<Spendable> spendables;
    public static class SpendableViewHolder extends RecyclerView.ViewHolder{
        public GridLayout spendableElement;
        public TextView spendableName;
        public TextView spendableMax;
        public EditText spendableCurrent;
        public ProgressBar spendableProgress;
        public FloatingActionButton fillProgress;

        public SpendableViewHolder(GridLayout v){
            super(v);
            spendableElement = v;
            spendableName = v.findViewById(R.id.spendableName);
            spendableMax = v.findViewById(R.id.maxSpendableValue);
            spendableCurrent = v.findViewById(R.id.currentSpendableValue);
            spendableProgress = v.findViewById(R.id.progressBar);
            fillProgress = v.findViewById(R.id.fillProgress);
        }
    }


    public Adapter_Spendable(ArrayList<Spendable> spendables){
        this.spendables = spendables;
    }
    @Override
    public SpendableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GridLayout spendableLayout = (GridLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cdb_spendables_element,parent,false);
        return new SpendableViewHolder(spendableLayout);
    }

    @Override
    public void onBindViewHolder(final SpendableViewHolder holder, int position) {
        final Spendable s = spendables.get(position);
        holder.spendableName.setText(s.getName());
        holder.spendableMax.setText(String.valueOf(s.getMaxValue()));
        holder.spendableCurrent.setText(String.valueOf(s.getCurrentValue()));
        holder.spendableCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()== 0){
                    s.setCurrentValue(0);
                } else {
                    s.setCurrentValue(Integer.parseInt(charSequence.toString()));
                }

                holder.spendableProgress.setProgress(s.getCurrentValue());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.spendableProgress.setMax(s.getMaxValue());
        holder.spendableProgress.setProgress(s.getCurrentValue());

        holder.fillProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.setCurrentValue(s.getMaxValue());
                holder.spendableProgress.setProgress(s.getCurrentValue());
                holder.spendableCurrent.setText(String.valueOf(s.getCurrentValue()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return spendables.size();
    }
}
