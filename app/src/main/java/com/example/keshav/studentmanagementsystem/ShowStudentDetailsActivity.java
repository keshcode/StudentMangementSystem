package com.example.keshav.studentmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.keshav.studentmanagementsystem.Adaptor.RecyclerViewAdaptor;

import java.util.ArrayList;

import modle.StudentModle;

/**
 * This activity show the student Details contain  and button moves to next activity
 * collecting inputs to show on this activty
 */
public class ShowStudentDetailsActivity extends AppCompatActivity {
    private static final int RESULT_CODE_EDITED_STUDENT_FORM = 21;
    private static final int RESULT_CODE = 2;
    private static final int REQUEST_CODE = 1;
    private static int rollno = 0;
    private ArrayList<StudentModle> studentInfoList;
    private StudentModle studentInfo;
    private Button btnCreateStudent;
    private Switch switchView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_details);
        init();
        btnCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(ShowStudentDetailsActivity.this, StudentDetailFormActivity.class);
                rollno++;
                intent.putExtra("RollNoGenrated", rollno);
                startActivityForResult(intent, RESULT_CODE);
            }
        });
    }

    /**
     * initialise the variables
     */
    protected void init() {
        btnCreateStudent = (Button) findViewById(R.id.btnCreateStudent);
        studentInfoList = new ArrayList<StudentModle>();
        switchView = (Switch) findViewById(R.id.switchView);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        studentInfo = data.getParcelableExtra("savedInfo");
        Log.d("debug", String.valueOf(resultCode));
        if (resultCode == 2) {
            Log.d("debug", "YOU SHALl pass by 2");
            studentInfoList.add(studentInfo);
        }
        if (resultCode == RESULT_CODE_EDITED_STUDENT_FORM) {
            int pos = data.getIntExtra("pos", 0);
            Log.d("debug", String.valueOf(pos));
            studentInfoList.set(pos, studentInfo);
        }

        Log.d("debug", "YOU pass at all time");
        RecyclerViewAdaptor recyclerViewAdaptor = new RecyclerViewAdaptor(this, studentInfoList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvStudentDetails);
        recyclerView.setAdapter(recyclerViewAdaptor);
        setView(recyclerView);
        recyclerView.setHasFixedSize(true);
    }

    /**
     * @param recyclerView reference to the recyclerView
     */
    protected void setView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowStudentDetailsActivity.this));
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    recyclerView.setLayoutManager(new GridLayoutManager(ShowStudentDetailsActivity.this, 2));
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(ShowStudentDetailsActivity.this));
                }
            }
        });
    }


}
