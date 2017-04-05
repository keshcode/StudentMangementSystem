package com.example.keshav.studentmanagementsystem.Adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keshav.studentmanagementsystem.R;
import com.example.keshav.studentmanagementsystem.StudentDetailFormActivity;

import java.util.ArrayList;

import modle.StudentModle;

/**
 * Created by keshav on 4/4/17.
 */

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {
    private Context context;
    private ArrayList<StudentModle> studentInfoList;

    /**
     * @param context         reference to the activty called
     * @param studentInfoList ArrayList Containing object of student Info
     */
    public RecyclerViewAdaptor(final Context context, final ArrayList<StudentModle> studentInfoList) {
        this.context = context;
        this.studentInfoList = studentInfoList;
    }

    @Override
    public RecyclerViewAdaptor.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewContact = inflater.inflate(R.layout.activty_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewContact);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdaptor.ViewHolder holder, final int position) {
        StudentModle studentinfo = studentInfoList.get(position);
        holder.tvFullName.setText(studentinfo.getmStudentName());
        holder.tvRollNo.setText(studentinfo.getmRollNo());
        holder.tvSchoolName.setText(studentinfo.getmSchoolName());
        holder.tvGender.setText(studentinfo.getmGender());
        holder.tvEmail.setText(studentinfo.getmEmail());
    }

    @Override
    public int getItemCount() {
        return studentInfoList.size();
    }

    /**
     * contains the view to be Inflated
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullName, tvRollNo, tvEmail, tvSchoolName, tvGender;

        /**
         * @param itemView view to be inflated
         */
        public ViewHolder(final View itemView) {
            super(itemView);
            final int pos = getAdapterPosition();
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvFullName = (TextView) itemView.findViewById(R.id.tvName);
            tvGender = (TextView) itemView.findViewById(R.id.tvGender);
            tvSchoolName = (TextView) itemView.findViewById(R.id.tvSchoolName);
            tvRollNo = (TextView) itemView.findViewById(R.id.tvRollNo);
            final Intent intent = new Intent(context, StudentDetailFormActivity.class);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.choose_option);
                    builder.setPositiveButton(R.string.view_details, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            intent.putExtra("mode", "view");
                            intent.putExtra("obj", studentInfoList.get(pos + 1));
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            intent.putExtra("mode", "edit");
                            intent.putExtra("obj", studentInfoList.get(pos + 1));
                            intent.putExtra("pos", pos);
                            context.startActivity(intent);
                        }
                    });
                    builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            studentInfoList.remove(pos + 1);
                            notifyItemRangeRemoved(pos, 1);
                        }
                    });
                    builder.create();
                    builder.show();
                }
            });
        }
    }
}
