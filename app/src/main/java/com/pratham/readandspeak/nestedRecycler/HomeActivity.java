package com.pratham.readandspeak.nestedRecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.pratham.readandspeak.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ArrayList<SectionDataModel> allSampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<SectionDataModel>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Talkbot");
        }

        createDummyData();


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);


    }

    public void createDummyData() {
        for (int i = 1; i <= 3; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 3; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
}