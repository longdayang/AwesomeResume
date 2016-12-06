package com.example.longdayang.awesomeresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.longdayang.awesomeresume.model.BasicInfo;
import com.example.longdayang.awesomeresume.model.Education;
import com.example.longdayang.awesomeresume.util.DateUtils;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private BasicInfo basicInfo;
    //private Education education;
    private List<Education> educations;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fakeData();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQ_CODE_EDUCATION_EDIT && resultCode == Activity.RESULT_OK){
            Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
            educations.add(education);
            setupEducations();
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        setupBasicInfoUI();
        //setupEducationUI();
        setupEducations();

        ImageButton addEducationBtn = (ImageButton) findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivity(intent);

                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }

        });
    }

    private void setupEducations() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations);
        educationsLayout.removeAllViews();
        for(Education education : educations){
            educationsLayout.addView(getEducationView(education));

        }

    }

    private View getEducationView(final Education education) {
        View view = getLayoutInflater().inflate(R.layout.education_item,null);
        String dataString = DateUtils.dateToString(education.startDate) + "~" +
                DateUtils.dateToString(education.endDate);
        ((TextView) view.findViewById(R.id.education_school)).setText(education.school + "("
        + dataString + ")");
        ((TextView) view.findViewById(R.id.education_courses)).setText(formatItems(education.courses));

        view.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION,education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
        return view;
    }


    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void setupBasicInfoUI() {
        ((TextView) findViewById(R.id.user_name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(basicInfo.email);
    }




    private void fakeData(){
        basicInfo = new BasicInfo();
        basicInfo.name = "Longda Yang";
        basicInfo.email = "longda.yang@yahoo.com";

        Education education1 = new Education();
        Education education2 = new Education();

        education1.school = "Vanderbilt";
        education1.major = "Electrical Engineering";
        education1.startDate = DateUtils.stringToDate("09/2015");
        education1.endDate = DateUtils.stringToDate("05/2017");
        education1.courses = new ArrayList<>();
        education1.courses.add("Data Structure");
        education1.courses.add("Algorithm");
        education1.courses.add("Intermediate Software Design");

        education2.school = "Shanghai University";
        education2.major = "Electrical Engineering";
        education2.startDate = DateUtils.stringToDate("09/2011");
        education2.endDate = DateUtils.stringToDate("05/2015");
        education2.courses = new ArrayList<>();
        education2.courses.add("Object Oriented Programming C++");
        education2.courses.add("Programming Language C");

        educations = new ArrayList<>();
        educations.add(education1);
        educations.add(education2);

    }
}
