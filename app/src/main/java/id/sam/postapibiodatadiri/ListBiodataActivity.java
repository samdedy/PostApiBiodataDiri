package id.sam.postapibiodatadiri;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import id.sam.postapibiodatadiri.adapter.AdapterListSimple;
import id.sam.postapibiodatadiri.model.DeleteData;
import id.sam.postapibiodatadiri.model.getall.Biodatum;
import id.sam.postapibiodatadiri.model.getall.ListBiodata;
import id.sam.postapibiodatadiri.service.APIClient;
import id.sam.postapibiodatadiri.service.APIInterfacesRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListBiodataActivity extends AppCompatActivity implements AdapterListSimple.OnItemClickListener{

    RecyclerView rvBiodata;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        rvBiodata = findViewById(R.id.rvBiodata);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListBiodataActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
                if (listBiodata !=null) {
                    AdapterListSimple adapter = new AdapterListSimple(ListBiodataActivity.this,listBiodata.getData().getBiodata());
                    adapter.setOnItemClickListener(ListBiodataActivity.this);
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

    public void deleteDataBiodata(Integer id){
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<DeleteData> del = apiInterface.deleteData((Integer) id);
        del.enqueue(new Callback<DeleteData>() {
            @Override
            public void onResponse(Call<DeleteData> call, Response<DeleteData> response) {
                DeleteData deleteBiodata = response.body();
                if (deleteBiodata !=null) {
                    Toast.makeText(ListBiodataActivity.this, "data berhasil di hapus", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<DeleteData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callForecastbyCity();
    }

    @Override
    public void onItemClick(@NonNull View view, Biodatum obj, int position) {
        deleteDataBiodata(Integer.parseInt(obj.getId()));
    }
}