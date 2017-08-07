package es.metichi.animabeyondfantasy;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Metichi on 07/08/2017.
 */

public class SheetAdapter extends RecyclerView.Adapter<SheetAdapter.SheetViewHolder>{
    ArrayList<CharacterDisplayBundle> bundles;
    public SheetAdapter(ArrayList<CharacterDisplayBundle> bundles){
        this.bundles = bundles;
    }
    public static class SheetViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout card;
        public TextView title;
        public FloatingActionButton button;
        public FrameLayout frame;
        public SheetViewHolder(ConstraintLayout v){
            super(v);
            card = v;
            title = v.findViewById(R.id.cardHeader);
            button = v.findViewById(R.id.cardButton);
            frame = v.findViewById(R.id.cardFrame);
        }
    }

    @Override
    public SheetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infowindow_frame,parent,false);
        return new SheetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SheetViewHolder holder, int position) {
        CharacterDisplayBundle bundle = bundles.get(position);
        holder.title.setText(bundle.getTitle());
        holder.button.setOnClickListener(bundle.getOnClickListener());
        holder.frame.addView(bundle.getLayout());
    }

    @Override
    public int getItemCount() {
        return bundles.size();
    }
}