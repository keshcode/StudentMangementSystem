package com.example.keshav.studentmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modle.StudentModle;

/**
 * This activity show the student Details contain  and button moves to next activity
 * collecting inputs to show on this activty
 */
public class ShowStudentDetailsActivity extends AppCompatActivity {
    private static final int RESULT_CODE_EDITED_STUDENT_FORM = 21;
    private static final int RESULT_CODE = 2;
    private static final int REQUEST_CODE = 2;
    private static int rollno = 0;
    private ArrayList<StudentModle> studentInfoList;
    private StudentModle studentInfo;
    private Button btnCreateStudent;
    private Switch switchView;
    private Spinner spSortBy;
    private String name = "Name";
    private String roll = "RollNo";
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
                Log.d("debug", choose);
                if (choose.equals(name)) {
                    Log.d("debug", "sorting by name");
                    sortByName();
                }
                if (choose.equals(roll)) {
                    Log.d("debug", "sorting by roll no");
                    sortByRollNo();
                }
                for (StudentModle s : studentInfoList) {
                    Log.d("ara", s.getmFirstName());
                    Log.d("ara", s.getmLastName());
                    Log.d("ara", s.getmRollNo());
                }
                //recyclerViewAdaptor.notifyItemMoved(0, studentInfoList.size() - 1);
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
                intent.putExtra("RollNoGenrated", rollno);
                startActivityForResult(intent, RESULT_CODE);
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
        Log.d("debug", String.valueOf(requestCode));
        if (data != null) {
            studentInfo = data.getParcelableExtra("savedInfo");
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


}
