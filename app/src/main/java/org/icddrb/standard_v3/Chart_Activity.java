package org.icddrb.standard_v3;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import forms_datamodel.IndicatorList_DataModel;

public class Chart_Activity extends AppCompatActivity {

    TextView lblIndicator;
    Bundle IDbundle;
    static String INDICATOR_ID = "";
    static String INDICATOR_NAME = "";
    Connection C;
    HorizontalBarChart chart;
    private List<IndicatorList_DataModel> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_activity);
        C = new Connection(this);

        IDbundle = getIntent().getExtras();
        INDICATOR_ID = IDbundle.getString("indicator_id");
        INDICATOR_NAME = IDbundle.getString("indicator_name");

        ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }});

        lblIndicator = findViewById(R.id.lblIndicator);
        lblIndicator.setText(INDICATOR_NAME);

        //chart = findViewById(R.id.barchart);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mAdapter = new DataAdapter(dataList);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        DataSearch();

        /*ArrayList<BarEntry> entries = new ArrayList<>();
        BarEntry barEntry;
        List<String> xlabels = new ArrayList<>();
        Cursor cur = C.ReadData("Select indicatorid, filter1, filter2, serial, xlabel, xvalue, ylabel, yvalue from data_chart order by serial");
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            barEntry = new BarEntry(Integer.parseInt(cur.getString(cur.getColumnIndex("xvalue"))), Float.parseFloat(cur.getString(cur.getColumnIndex("yvalue"))));
            entries.add(barEntry);
            xlabels.add(cur.getString(cur.getColumnIndex("xlabel")));
            cur.moveToNext();
        }
        cur.close();

        initBarChart(chart,xlabels);
        showBarChart(chart,entries);*/
    }

    private void showBarChart(BarChart chart, ArrayList<BarEntry> entries){
        BarDataSet bardataset = new BarDataSet(entries, "No Of Employee");
        initBarDataSet(bardataset);

        //BarDataSet bardataset1 = new BarDataSet(entries, "Salary");
        //initBarDataSet(bardataset1);

        //ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bardataset);

        BarData data = new BarData(dataSets);
        chart.setFitBars(true);
        //chart.setDrawValueAboveBar(true);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.setData(data);
        chart.invalidate();
    }


    private void initBarDataSet(BarDataSet barDataSet){
        //Changing the color of the bar
        barDataSet.setColor(Color.parseColor("#01A9DB"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(true);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
    }

    private void initBarChart(BarChart barChart, List<String> xlabels){
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false);
        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setText("Test Graph");
        description.setEnabled(false);
        //description.setPosition(XAxis.XAxisPosition.TOP,XAxis.XAxisPosition.TOP);
        barChart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart.animateX(1000);

        //------------------------------------------------------------------------------------------
        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(true);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xlabels));

        //------------------------------------------------------------------------------------------
        YAxis leftAxis = barChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);
        YAxis rightAxis = barChart.getAxisRight();
        leftAxis.setDrawGridLines(false);


        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);

        //------------------------------------------------------------------------------------------
        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.SQUARE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);
        legend.setEnabled(false);
    }


    private void DataSearch()
    {
        try
        {
            IndicatorList_DataModel d = new IndicatorList_DataModel();
            String SQL = "Select distinct filter1 from data_chart order by filter1";

            List<IndicatorList_DataModel> data = d.SelectIndicators_Filter(this, SQL);
            dataList.clear();

            dataList.addAll(data);
            try {
                mAdapter.notifyDataSetChanged();
            }catch ( Exception ex){
                Connection.MessageBox(Chart_Activity.this,ex.getMessage());
            }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Chart_Activity.this, e.getMessage());
            return;
        }
    }

    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
        private List<IndicatorList_DataModel> dataList;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout secListRow;
            TextView lblFilter;
            HorizontalBarChart barchart;
            RelativeLayout secChart;



            public MyViewHolder(View convertView) {
                super(convertView);
                secListRow = (LinearLayout)convertView.findViewById(R.id.secListRow);
                lblFilter = (TextView)convertView.findViewById(R.id.lblFilter);
                barchart = (HorizontalBarChart)convertView.findViewById(R.id.barchart);
                secChart = (RelativeLayout) convertView.findViewById(R.id.secChart);
            }
        }
        public DataAdapter(List<IndicatorList_DataModel> datalist) {
            this.dataList = datalist;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chart_activity_row, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final IndicatorList_DataModel data = dataList.get(position);
            holder.lblFilter.setText(data.getfilter1());
            holder.secChart.setVisibility(View.GONE);

            /*ArrayList<BarEntry> entries = new ArrayList<>();
            BarEntry barEntry;
            List<String> xlabels = new ArrayList<>();
            Cursor cur = C.ReadData("Select indicatorid, filter1, filter2, serial, xlabel, xvalue, ylabel, yvalue from data_chart where filter1='"+ data.getfilter1() +"' order by serial");
            cur.moveToFirst();
            entries.clear();
            xlabels.clear();
            while (!cur.isAfterLast()) {
                barEntry = new BarEntry(Integer.parseInt(cur.getString(cur.getColumnIndex("xvalue"))), Float.parseFloat(cur.getString(cur.getColumnIndex("yvalue"))));
                entries.add(barEntry);
                xlabels.add(cur.getString(cur.getColumnIndex("xlabel")));
                cur.moveToNext();
            }
            cur.close();

            initBarChart(holder.barchart,xlabels);
            showBarChart(holder.barchart,entries);*/

            holder.secListRow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(holder.secChart.getVisibility()==View.VISIBLE){
                        holder.secChart.setVisibility(View.GONE);
                    }else {
                        holder.secChart.setVisibility(View.VISIBLE);
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        BarEntry barEntry;
                        List<String> xlabels = new ArrayList<>();
                        Cursor cur = C.ReadData("Select indicatorid, filter1, filter2, serial, xlabel, xvalue, ylabel, yvalue from data_chart where filter1='"+ data.getfilter1() +"' order by serial");
                        cur.moveToFirst();
                        entries.clear();
                        xlabels.clear();
                        while (!cur.isAfterLast()) {
                            barEntry = new BarEntry(Integer.parseInt(cur.getString(cur.getColumnIndex("xvalue"))), Float.parseFloat(cur.getString(cur.getColumnIndex("yvalue"))));
                            entries.add(barEntry);
                            xlabels.add(cur.getString(cur.getColumnIndex("xlabel")));
                            cur.moveToNext();
                        }
                        cur.close();

                        initBarChart(holder.barchart,xlabels);
                        showBarChart(holder.barchart,entries);
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
}