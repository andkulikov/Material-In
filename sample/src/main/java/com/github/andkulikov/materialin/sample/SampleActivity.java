package com.github.andkulikov.materialin.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.andkulikov.materialin.MaterialIn;


public class SampleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] mDelayGravities = {
            "Delay TOP",
            "Delay BOTTOM",
            "Delay LEFT",
            "Delay RIGHT"
    };

    private String[] mSlideGravities = {
            "Slide TOP",
            "Slide BOTTOM",
            "Slide LEFT",
            "Slide RIGHT"
    };

    private RecyclerView mRecyclerView;
    private AppCompatSpinner mSpinnerDelay;
    private AppCompatSpinner mSpinnerSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpinnerDelay = (AppCompatSpinner) findViewById(R.id.spinner_delay);
        mSpinnerSlide = (AppCompatSpinner) findViewById(R.id.spinner_slide);
        mSpinnerDelay.setAdapter(new ArrayAdapter<>(this, R.layout.spinner, mDelayGravities));
        mSpinnerSlide.setAdapter(new ArrayAdapter<>(this, R.layout.spinner, mSlideGravities));
        mSpinnerDelay.setSelection(1);
        mSpinnerSlide.setSelection(1);
        mSpinnerDelay.setOnItemSelectedListener(this);
        mSpinnerSlide.setOnItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new Adapter());
        startMaterialIn();
    }

    private void startMaterialIn() {
        MaterialIn.animate(mRecyclerView, getSelectedGravity(mSpinnerDelay),
                getSelectedGravity(mSpinnerSlide));
    }

    private int getSelectedGravity(AppCompatSpinner spinner) {
        int index = spinner.getSelectedItemPosition();
        int gravity = Gravity.TOP;
        switch (index) {
            case 0:
                gravity = Gravity.TOP;
                break;
            case 1:
                gravity = Gravity.BOTTOM;
                break;
            case 2:
                gravity = Gravity.LEFT;
                break;
            case 3:
                gravity = Gravity.RIGHT;
                break;
        }
        return gravity;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        startMaterialIn();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class Holder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDetails;

        public Holder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDetails = (TextView) itemView.findViewById(R.id.details);
        }

    }

    private class Adapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup viewGroup, int pos) {
            return new Holder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int pos) {
            holder.mTitle.setText("Item number " + pos + " title");
            holder.mDetails.setText("Details for list item number " + pos);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_restart) {
            startMaterialIn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
