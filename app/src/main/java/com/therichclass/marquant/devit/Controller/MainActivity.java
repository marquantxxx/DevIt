package com.therichclass.marquant.devit.Controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.therichclass.marquant.devit.Api.Client;
import com.therichclass.marquant.devit.Api.Service;
import com.therichclass.marquant.devit.DevAdapter;
import com.therichclass.marquant.devit.Model.EndlessScroll;
import com.therichclass.marquant.devit.Model.Item;
import com.therichclass.marquant.devit.Model.ItemResponse;
import com.therichclass.marquant.devit.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    TextView Disconnected;
    public Map<String, String> qryOptions = new HashMap<>();
    ProgressDialog pd;
    private SwipeRefreshLayout refreshContainer;
    String queryusers = "language:java location:lagos";
    private static final String Perpage = "50";
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //locate the recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //initialize views
        initViews();

        //locate the swipe refresh layout
        refreshContainer = (SwipeRefreshLayout) findViewById(R.id.refreshContainer);

        //set color for refresh icon
        refreshContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        //set listener for refresh layout
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //display below toast when recyclerview has been refreshed
                Toast.makeText(MainActivity.this, "Developers list Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        //setting onscroll listener to fetch more developers and populate homepage
        recyclerView.addOnScrollListener(new EndlessScroll() {
            @Override
            public void onLoadMore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, "OnScroll Begins", Toast.LENGTH_SHORT).show();
//                    }
//                }, 3000);
                getDevelopers(queryusers);
            }
        });
    }

    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Github Developers...");
        pd.setCancelable(false);
        pd.show();

        //setting the layout manager
        LinearLayoutManager linManager = new LinearLayoutManager(this);

        //set layout orientation
        linManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linManager);

        //set smoothscroll
        recyclerView.smoothScrollToPosition(0);

        //log to determine when fetching operation is being carried out
        Log.d("Fetching", "Called get dev");
        getDevelopers(queryusers);
    }

    private void getDevelopers(String devs) {
        Disconnected = (TextView) findViewById(R.id.disconnected);

        try{ qryOptions.put("q", devs);
        qryOptions.put("per_page", Perpage);


        if (page > 1) {
            qryOptions.put("page", String.valueOf(page));
        }
        Service apiService = Client.getClient().create(Service.class);

        //log to trace when the api has been called
        Log.e("Fetching", "Called Api");
        Call<ItemResponse> call = apiService.getItems(qryOptions);

        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                if (response.isSuccessful()) {
                    ItemResponse result = response.body();
                    Log.d("Fetching", "onResponse: Data " + response);
                    //at the end of the list do nothing or add more developers and increase page count
                    if (result.getItems() != null || !(result.getItems().isEmpty())) {
                        //display list of developers
                        List<Item> items = response.body().getItems();
                        recyclerView.setAdapter(new DevAdapter(getApplicationContext(), items));
                        recyclerView.smoothScrollToPosition(0);
                        refreshContainer.setRefreshing(false);
                        //hides the progress dialog after refresh
                        pd.hide();


                        //populate developers list
                        page++;

                    }
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.d("Fetching", "onFailure: Data " + t);
                Toast.makeText(MainActivity.this, "Couldn't fetch data,please Retry", Toast.LENGTH_LONG).show();
                Disconnected.setVisibility(View.VISIBLE);
                pd.hide();
            }
        });
    }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();


}
    }
}
