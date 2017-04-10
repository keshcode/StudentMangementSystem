package com.example.keshav.studentmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.keshav.studentmanagementsystem.Adaptor.RecyclerViewAdaptor;
import com.example.keshav.studentmanagementsystem.Constants.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modle.StudentModle;

/**
 * This activity show the student Details contain  and button moves to next activity
 * collecting inputs to show on this activty
 */
public class ShowStudentDetailsActivity extends AppCompatActivity implements Constants {
    private static int rollno = 0;
    private ArrayList<StudentModle> studentInfoList;
    private StudentModle studentInfo;
    private Button btnCreateStudent;
    private Switch switchView;
    private Spinner spSortBy;
    private RecyclerViewAdaptor recyclerViewAdaptor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_details);
        init();
        setSpinneroption();
        spSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                String choose;
                choose = parent.getItemAtPosition(position).toString();
                if (choose.equals(R.string.name)) {
                    sortByName();
                }
                if (choose.equals(R.string.roll)) {
                    sortByRollNo();
                }
                recyclerViewAdaptor.notifyDataSetChanged();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvStudentDetails);
                recyclerView.setAdapter(recyclerViewAdaptor);
                setView(recyclerView);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }

        });
        recyclerViewAdaptor = new RecyclerViewAdaptor(this, studentInfoList);
        btnCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(ShowStudentDetailsActivity.this, StudentDetailFormActivity.class);
                rollno++;
                intent.putExtra(KEY_ROLL, rollno);
                startActivityForResult(intent, REQUEST_CODE_ADD_STUDENT_FORM);
            }
        });
    }

    /**
     * sorts arrayList by name of student
     */
    protected void sortByName() {
        Collections.sort(studentInfoList, new Comparator<StudentModle>() {
            public int compare(final StudentModle one, final StudentModle other) {
                return (one.getmFirstName() + one.getmLastName()).compareTo(other.getmFirstName() + other.getmLastName());
            }
        });

    }

    /**
     * sorts arrayList BY roll no of Student
     */
    protected void sortByRollNo() {
        Collections.sort(studentInfoList, new Comparator<StudentModle>() {
            public int compare(final StudentModle one, final StudentModle other) {
                return (one.getmRollNo()).compareTo(other.getmRollNo());
            }
        });

    }

    /**
     * sets items in spinner
     */
    protected void setSpinneroption() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortby_array, R.layout.support_simple_spinner_dropdown_item);
        spSortBy.setAdapter(adapter);
    }

    /**
     * initialise the variables
     */
    protected void init() {
        btnCreateStudent = (Button) findViewById(R.id.btnCreateStudent);
        studentInfoList = new ArrayList<StudentModle>();
        switchView = (Switch) findViewById(R.id.switchView);
        spSortBy = (Spinner) findViewById(R.id.spSortBy);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            studentInfo = data.getParcelableExtra(KEY_STUDENT_OBJ);
            if (requestCode == REQUEST_CODE_ADD_STUDENT_FORM) {
                studentInfoList.add(studentInfo);
            }
            if (requestCode == REQUEST_CODE_EDITED_STUDENT_FORM) {
                int pos = data.getIntExtra(KEY_POSITION, 0);
                studentInfoList.set(pos, studentInfo);
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvStudentDetails);
            recyclerView.setAdapter(recyclerViewAdaptor);
            setView(recyclerView);
            recyclerView.setHasFixedSize(true);
        }
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


    @Override
    public void checkstyle() {

    }
}
