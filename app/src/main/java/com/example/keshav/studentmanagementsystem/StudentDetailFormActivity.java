package com.example.keshav.studentmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import modle.StudentModle;

/**
 * This Activity contains Student Detail form and Collect all data and push to ArrayList
 */
public class StudentDetailFormActivity extends AppCompatActivity {
    private static final int RESULT_CODE_EDITED_STUDENT_FORM = 21;
    private static final int RESULT_CODE_STUDENT_FORM = 2;
    private String view = "View";
    private EditText etFirstName, etLastName, etEmail, etSchoolName;
    private RadioButton rdbtnMale, rdbtnFemale, rdbtnOther;
    private TextView tvRollNo;
    private String firstName, lastName, email, schoolName, gender = "", rollNo;
    private Button btnSave;
    private StudentModle studentData;
    private Intent paser;
    private int num;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_form);
        init();


        num = getIntent().getIntExtra("RollNoGenrated", 0);

        if (num != 0) {
            Integer rollno = new Integer(num);
            tvRollNo.setText(rollno.toString());

        } else {
            paser = getIntent();
            studentData = paser.getParcelableExtra("obj");
            setEdtitableStudentDetail();
            if (paser.getStringExtra("mode").equals(view)) {
                setViewMode();
            }

        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                setStudentInfo();
                if (valideInput()) {
                    save();
                }
            }
        });
    }

    /**
     * sets to view mode disable edit mode and hides save button
     */
    protected void setViewMode() {
        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);
        etEmail.setEnabled(false);
        etSchoolName.setEnabled(false);
        btnSave.setVisibility(View.GONE);
    }

    /**
     * Sets values stored student detail to edit
     */
    protected void setEdtitableStudentDetail() {
        etFirstName.setText(studentData.getmStudentName());
        etSchoolName.setText(studentData.getmSchoolName());
        etEmail.setText(studentData.getmEmail());
        tvRollNo.setText(studentData.getmRollNo());

    }

    /**
     * saves the student Info object to intent and finish this activity
     */
    protected void save() {
        Intent intent = new Intent();
        intent.putExtra("savedInfo", studentData);
        if (num != 0) {
            Log.d("debug", "when num not equal to zero");
            setResult(RESULT_CODE_STUDENT_FORM, intent);
        } else {
            Log.d("debug", "when num equals to zero");
            intent.putExtra("pos", paser.getIntExtra("pos", 0));
            setResult(RESULT_CODE_EDITED_STUDENT_FORM, intent);
        }
        finish();
    }


    /**
     * @return if all the inputs are valid or not
     */
    protected boolean valideInput() {
        if ((!firstName.isEmpty())
                && (!lastName.isEmpty())
                && (!email.isEmpty())
                && (!schoolName.isEmpty())
                && (!gender.isEmpty())) {
            if (isValidEmail(email)) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Type Valid Email", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * validate email address
     *
     * @param emailaddr String contain email address
     * @return boolean is valid email or not valid email
     */
    private boolean isValidEmail(final String emailaddr) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailaddr).matches();
    }

    /**
     * initialize all variable
     */
    protected void init() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etSchoolName = (EditText) findViewById(R.id.etSchoolName);
        tvRollNo = (TextView) findViewById(R.id.tvRollNo);
        rdbtnFemale = (RadioButton) findViewById(R.id.rdBtnFemale);
        rdbtnMale = (RadioButton) findViewById(R.id.rdBtnMale);
        rdbtnOther = (RadioButton) findViewById(R.id.rdBtnOther);
        btnSave = (Button) findViewById(R.id.btnSave);


    }

    /**
     * sets value to member function variable
     */
    protected void setStudentInfo() {
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email = etEmail.getText().toString();
        schoolName = etSchoolName.getText().toString();
        rollNo = tvRollNo.getText().toString();
        if (rdbtnMale.isChecked()) {
            gender = rdbtnMale.getText().toString();
        } else if (rdbtnFemale.isChecked()) {
            gender = rdbtnFemale.getText().toString();
        } else {
            gender = rdbtnOther.getText().toString();
        }

        studentData = new StudentModle(firstName, schoolName, email, gender, rollNo);
    }


}
