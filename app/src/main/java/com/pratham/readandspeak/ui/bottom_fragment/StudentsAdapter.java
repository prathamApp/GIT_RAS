package com.pratham.readandspeak.ui.bottom_fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pratham.readandspeak.RASApplication;
import com.pratham.readandspeak.R;
import com.pratham.readandspeak.domain.Student;
import com.pratham.readandspeak.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anki on 10/30/2018.
 */

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder> {
    List<Student> studentAvatarList;
    ArrayList avatarList;
    StudentClickListener studentClickListener;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        ImageView avatar;
        RelativeLayout rl_card;

        public MyViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.content_title);
            avatar = itemView.findViewById(R.id.content_thumbnail);
            rl_card = itemView.findViewById(R.id.rl_card);
        }
    }

    public StudentsAdapter(Context c, Fragment context, List<Student> studentAvatars, ArrayList avatarList) {
        this.studentAvatarList = studentAvatars;
        this.avatarList = avatarList;
        this.studentClickListener = (StudentClickListener) context;
        this.mContext = c;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Student studentAvatar = studentAvatarList.get(position);
        holder.studentName.setText(studentAvatar.getFullName());

        Glide.with(mContext).load(RASApplication.pradigiPath + "/.RAS/English/RAS_Thumbs/" + studentAvatar.getAvatarName()).into(holder.avatar);

        holder.rl_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentAvatarList.get(position).getStudentID();
                studentClickListener.onStudentClick(studentAvatarList.get(position).getFullName(),studentAvatarList.get(position).getStudentID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentAvatarList.size();
    }


}
