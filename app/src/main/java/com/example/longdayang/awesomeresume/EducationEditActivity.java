package com.example.longdayang.awesomeresume;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.longdayang.awesomeresume.model.Education;
import com.example.longdayang.awesomeresume.util.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class EducationEditActivity extends AppCompatActivity {

    public static final String KEY_EDUCATION = "education";
    private Education education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        education = getIntent().getParcelableExtra(KEY_EDUCATION);
        if(education != null){
            setupUI();
        }
        setTitle(education == null? "New education" : "Edit education");

    }

    private void setupUI() {
        ((EditText)findViewById(R.id.education_edit_school)).setText(education.school);
        ((EditText)findViewById(R.id.education_edit_major)).setText(education.major);
        ((EditText)findViewById(R.id.education_edit_startdate)).
                setText(DateUtils.dateToString(education.startDate));
        ((EditText)findViewById(R.id.education_edit_enddate)).
                setText(DateUtils.dateToString(education.endDate));
        ((EditText)findViewById(R.id.education_edit_courses)).
                setText(TextUtils.join("\n",education.courses));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    private void saveAndExit() {
        if(education == null){
            education = new Education();
        }

        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        education.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        education.startDate =
                DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_startdate)).
                        getText().toString());
        education.endDate =
                DateUtils.stringToDate(((EditText) findViewById(R.id.education_edit_enddate)).
                        getText().toString());
        education.courses = Arrays.asList(TextUtils.split(((EditText)
                findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, education);
        setResult(Activity.RESULT_OK,resultIntent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.ic_save){
            saveAndExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
