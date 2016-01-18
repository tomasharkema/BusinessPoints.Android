package io.harkema.businesspoints;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.harkema.businesspoints.model.BusinessPoint;

public class ListActivity extends AppCompatActivity {

    public static final String BUSINESS_POINT_VALUE = "BUSINESS_POINT_VALUE";

    private BusinessPointsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.this.startActivity(new Intent(ListActivity.this, EditBusinessPointActivity.class));
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = BusinessPointsAdapter.getForAll();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new BusinessPointsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BusinessPoint businessPoint) {
                Intent intent = new Intent(ListActivity.this, EditBusinessPointActivity.class);
                intent.putExtra(BUSINESS_POINT_VALUE, businessPoint.id);
                ListActivity.this.startActivity(intent);
            }
        });

        updateInfos();
    }

    private void updateInfos() {

        int finishedECTS = App.instance.businessPointsDbHelper.getFinishedECTS();
        int allECTS = App.instance.businessPointsDbHelper.getAllECTS();
        ((TextView)findViewById(R.id.extraInfo)).setText("TOTAL ECTS: " + allECTS + "\nECTS TO GO: " + (allECTS - finishedECTS));
    }
}
