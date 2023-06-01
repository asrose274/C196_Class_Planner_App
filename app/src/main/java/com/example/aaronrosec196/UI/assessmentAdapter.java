package com.example.aaronrosec196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aaronrosec196.Entities.Assessment;
import com.example.aaronrosec196.R;

import java.util.List;

public class assessmentAdapter extends RecyclerView.Adapter<assessmentAdapter.assessmentViewHolder> {
    class assessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private assessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.assessmentItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Assessment current=mAssessments.get(position);
                    Intent intent=new Intent(context,addedit_assessments.class);
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("end", current.getAssessmentEnd());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("courseId", current.getCourseId());
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public assessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public assessmentAdapter.assessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new assessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull assessmentAdapter.assessmentViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment current=mAssessments.get(position);
            String name=current.getAssessmentName();
            holder.assessmentItemView.setText(name);
        }
        else{
            holder.assessmentItemView.setText("No assessment name");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mAssessments!=null){
        return mAssessments.size();
        }
        else return 0;
    }
}
