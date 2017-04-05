package modle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Contains the Students properties
 */

public class StudentModle implements Parcelable {
    public static final Creator<StudentModle> CREATOR = new Creator<StudentModle>() {
        @Override
        public StudentModle createFromParcel(final Parcel in) {
            return new StudentModle(in);
        }

        @Override
        public StudentModle[] newArray(final int size) {
            return new StudentModle[size];
        }
    };
    private String mStudentName, mSchoolName, mEmail, mGender, mRollNo;

    /**
     * Setting all the values at the time of generating object
     *
     * @param mStudentName String containing the name of Student
     * @param mSchoolName  String containing the name of School
     * @param mEmail       String containing the email address of Student
     * @param mGender      String containing the Gender of the student
     * @param mRollNo      String containing the RollNO of the Student
     */
    public StudentModle(final String mStudentName, final String mSchoolName, final String mEmail, final String mGender, final String mRollNo) {
        this.mStudentName = mStudentName;
        this.mSchoolName = mSchoolName;
        this.mEmail = mEmail;
        this.mGender = mGender;
        this.mRollNo = mRollNo;
    }

    /**
     * saves value to String using parcel
     *
     * @param in parcel that carry information from another activity
     */
    protected StudentModle(final Parcel in) {
        mStudentName = in.readString();
        mEmail = in.readString();
        mGender = in.readString();
        mRollNo = in.readString();
        mSchoolName = in.readString();
    }


    /**
     * @return name of the student
     */
    public String getmStudentName() {
        return mStudentName;
    }

    /**
     * Sets name of the Student
     *
     * @param mStudentName String Containing name
     */
    public void setmStudentName(final String mStudentName) {
        this.mStudentName = mStudentName;
    }

    /**
     * @return School Name
     */
    public String getmSchoolName() {
        return mSchoolName;
    }

    /**
     * sets School Name
     *
     * @param mSchoolName String Name of School
     */
    public void setmSchoolName(final String mSchoolName) {
        this.mSchoolName = mSchoolName;
    }

    /**
     * @return Email address
     */
    public String getmEmail() {
        return mEmail;
    }

    /**
     * Sets email address
     *
     * @param mEmail String containing email
     */
    public void setmEmail(final String mEmail) {
        this.mEmail = mEmail;
    }

    /**
     * @return Gender of student
     */
    public String getmGender() {
        return mGender;
    }

    /**
     * sets Gender of the Student
     *
     * @param mGender String Containing gender od the student
     */
    public void setmGender(final String mGender) {
        this.mGender = mGender;
    }

    /**
     * @return RollNO
     */
    public String getmRollNo() {
        return mRollNo;
    }

    /**
     * sets rollno of Student
     *
     * @param mRollNo String containing Roll nO of student
     */
    public void setmRollNo(final String mRollNo) {
        this.mRollNo = mRollNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mStudentName);
        dest.writeString(mEmail);
        dest.writeString(mGender);
        dest.writeString(mRollNo);
        dest.writeString(mSchoolName);
    }
}
