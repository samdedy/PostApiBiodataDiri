package id.sam.postapibiodatadiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONObject;

import id.sam.postapibiodatadiri.adapter.AdapterListSimple;
import id.sam.postapibiodatadiri.model.getall.ListBiodata;
import id.sam.postapibiodatadiri.service.APIClient;
import id.sam.postapibiodatadiri.service.APIInterfacesRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBiodataActivity extends AppCompatActivity {

    RecyclerView rvBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        rvBiodata = findViewById(R.id.rvBiodata);
        callForecastbyCity();
    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callForecastbyCity(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(ListBiodataActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<ListBiodata> call3 = apiInterface.getListBiodata();
        call3.enqueue(new Callback<ListBiodata>() {
            @Override
            public void onResponse(Call<ListBiodata> call, Response<ListBiodata> response) {
                progressDialog.dismiss();
                ListBiodata listBiodata = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (listBiodata !=null) {

                    //     txtKota.setText(dataWeather.getName());
                    //     txtTemperature.setText(new DecimalFormat("##.##").format(dataWeather.getMain().getTemp()-273.15));

                    AdapterListSimple adapter = new AdapterListSimple(ListBiodataActivity.this,listBiodata.getData().getBiodata());


                    rvBiodata.setLayoutManager(new LinearLayoutManager(ListBiodataActivity.this));
                    rvBiodata.setItemAnimator(new DefaultItemAnimator());
                    rvBiodata.setAdapter(adapter);
                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ListBiodataActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ListBiodataActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListBiodata> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}