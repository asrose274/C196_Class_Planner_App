package com.example.aaronrosec196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;

import java.util.List;

public class termAdapter extends RecyclerView.Adapter<termAdapter.termViewHolder> {
    class termViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        private termViewHolder(View itemView){
            super(itemView);
            termItemView=itemView.findViewById(R.id.termItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Term current=mTerms.get(position);
                    Intent intent=new Intent(context,addedit_terms.class);
                    intent.putExtra("name", current.getName());
                    intent.putExtra("start", current.getTermStart());
                    intent.putExtra("end", current.getTermEnd());
                    intent.putExtra("id", current.getTermId());
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public termAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public termAdapter.termViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_list_item,parent,false);
        return new termViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull termAdapter.termViewHolder holder, int position) {
        if(mTerms!=null){
            Term current=mTerms.get(position);
            String name=current.getName();
            holder.termItemView.setText(name);
        }
        else{
            holder.termItemView.setText("No term name");
        }
    }

    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mTerms!=null){
        return mTerms.size();
        }
        else return 0;
    }
}
