package io.harkema.businesspoints;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import io.harkema.businesspoints.model.BusinessPoint;

public class EditBusinessPointActivity extends AppCompatActivity {

    public BusinessPoint businessPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
        findViewById(R.id.finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGradeState();
            }
        });

        final int bId = getIntent().getIntExtra(ListActivity.BUSINESS_POINT_VALUE, -1);
        if (bId != -1) {
            this.businessPoint = App.instance.businessPointsDbHelper.getById(bId);
            fillBusinessPoint();
        }

        setGradeState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_business_point, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            App.instance.businessPointsDbHelper.deleteBusinessPoint(businessPoint);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillBusinessPoint() {
        ((EditText)findViewById(R.id.courseName)).setText(businessPoint.title);
        ((EditText)findViewById(R.id.teacher)).setText(businessPoint.teacher);
        ((EditText) findViewById(R.id.ects)).setText("" + businessPoint.ects);
        ((EditText)findViewById(R.id.grade)).setText("" + (((float) businessPoint.grade) / 10.0f));
        ((CheckBox)findViewById(R.id.finished)).setChecked(businessPoint.finished);
    }

    private void validateAndSubmit() {
        String courseName = ((EditText)findViewById(R.id.courseName)).getText().toString();
        String teacher = ((EditText)findViewById(R.id.teacher)).getText().toString();
        int ects = Integer.parseInt(((EditText) findViewById(R.id.ects)).getText().toString());
        int grade = (int)Math.floor(Float.parseFloat(((EditText)findViewById(R.id.grade)).getText().toString())*10);
        boolean finished = ((CheckBox)findViewById(R.id.finished)).isChecked();

        BusinessPoint businessPoint = new BusinessPoint(courseName, teacher, ects, finished, grade);
        if (this.businessPoint != null) {
            businessPoint.id = this.businessPoint.id;

            App.instance.businessPointsDbHelper.update(businessPoint);
        } else {
            App.instance.businessPointsDbHelper.create(businessPoint);
        }

        this.businessPoint = null;
        finish();
    }

    private void setGradeState() {
        boolean finishedSelected = ((CheckBox)findViewById(R.id.finished)).isChecked();
        findViewById(R.id.gradeLayout).setVisibility(finishedSelected ? View.VISIBLE : View.GONE);
    }
}
