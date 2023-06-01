package com.example.aaronrosec196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.R;

import java.util.List;

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.courseViewHolder> {
    class courseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private courseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.courseItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current=mCourses.get(position);
                    Intent intent=new Intent(context,addedit_courses.class);
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("start", current.getCourseStart());
                    intent.putExtra("end", current.getCourseEnd());
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("instructor", current.getInstructor());
                    intent.putExtra("instructor_phone", current.getInstructorPhone());
                    intent.putExtra("instructor_email", current.getInstructorEmail());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("termId", current.getTermId());
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public courseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public courseAdapter.courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new courseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull courseAdapter.courseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            String name=current.getCourseName();
            holder.courseItemView.setText(name);
        }
        else{
            holder.courseItemView.setText("No course name");
        }
    }

    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mCourses!=null){
        return mCourses.size();
        }
        else return 0;
    }
}
