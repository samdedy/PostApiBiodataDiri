package id.sam.postapibiodatadiri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.location.aravind.getlocation.GeoLocator;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import id.sam.postapibiodatadiri.model.Result;
import id.sam.postapibiodatadiri.model.UpdateData;
import id.sam.postapibiodatadiri.service.APIClient;
import id.sam.postapibiodatadiri.service.APIInterfacesRest;
import in.balakrishnan.easycam.CameraBundleBuilder;
import in.balakrishnan.easycam.CameraControllerActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtPhone, txtAlamat, txtGPS;
    Button btnSend, btnCamera;
    ImageView imgFoto;
    private String[] list ;
    int editData = 0;
    String id_data = "";
    GeoLocator geoLocator;
    Double lat = 0.0, lon = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geoLocator = new GeoLocator(getApplicationContext(),MainActivity.this);
        txtName = findViewById(R.id.txtNama);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtPhone = findViewById(R.id.txtPhone);
        txtGPS = findViewById(R.id.txtGPS);
        imgFoto = findViewById(R.id.imgFoto);

        lat = geoLocator.getLattitude();
        lon = geoLocator.getLongitude();

        txtGPS.setText(String.valueOf(lat)+";"+String.valueOf(lon));
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editData == 110){
                    if (file != null) {
                        editData(id_data,
                                txtName.getText().toString(),
                                txtAlamat.getText().toString(),
                                txtPhone.getText().toString(),
                                String.valueOf(lat),
                                String.valueOf(lon));
                    } else {
                        editDataEnoughCamera(id_data,
                                txtName.getText().toString(),
                                txtAlamat.getText().toString(),
                                txtPhone.getText().toString(),
                                String.valueOf(lat),
                                String.valueOf(lon));
                    }
                } else {
                    postUserData(txtName.getText().toString(),
                            txtAlamat.getText().toString(),
                            txtPhone.getText().toString(),
                            String.valueOf(lat),
                            String.valueOf(lon));
                }
            }
        });

        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilCamera();
            }
        });

        editData = getIntent().getIntExtra("flag",0);
        mappingEditData();
    }

    File file;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 214) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                list = data.getStringArrayExtra("resultData");
                file = new File(list[0]);
                Picasso.get().load(file).into(imgFoto);

                txtGPS.setText(String.valueOf(geoLocator.getLattitude()) +";"+ String.valueOf(geoLocator.getLongitude()));
            }
        }
    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void postUserData(String nama, String alamat, String telepon, String lat, String lon){
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part bodyImg1 = MultipartBody.Part.createFormData("photo", file.getName(), requestFile1);

        Call<Result> absentAdd = apiInterface.addData(
                toRequestBody(nama),
                toRequestBody(alamat),
                toRequestBody(telepon),
                toRequestBody(lat),
                toRequestBody(lon),
                bodyImg1);

        absentAdd.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                Result status = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (status != null) {

                    if (status.getStatus()) {
                        Toast.makeText(MainActivity.this, "Data Berhasil ditambah", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.body().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //     Toast.makeText(ShoppingProductGrid.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        String error = jObjError.get("status_detail").toString();
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        try {
                            Toast.makeText(MainActivity.this, "Send Failed, " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        //    Toast.makeText(ShoppingProductGrid.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void editData(String id, String nama, String alamat, String telepon, String lat, String lon){
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part bodyImg1 = MultipartBody.Part.createFormData("photo", file.getName(), requestFile1);

        Call<UpdateData> absentAdd = apiInterface.updateData(
                toRequestBody(id),
                toRequestBody(nama),
                toRequestBody(alamat),
                toRequestBody(telepon),
                toRequestBody(lat),
                toRequestBody(lon),
                bodyImg1);

        absentAdd.enqueue(new Callback<UpdateData>() {
            @Override
            public void onResponse(Call<UpdateData> call, Response<UpdateData> response) {
                progressDialog.dismiss();
                UpdateData status = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (status != null) {

                    if (status.getStatus()) {
                        Toast.makeText(MainActivity.this, "Data Berhasil diupdate", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //     Toast.makeText(ShoppingProductGrid.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        String error = jObjError.get("message").toString();
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        try {
                            Toast.makeText(MainActivity.this, "Send Failed, " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        //    Toast.makeText(ShoppingProductGrid.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void editDataEnoughCamera(String id, String nama, String alamat, String telepon, String lat, String lon){
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();

        Call<UpdateData> absentAdd = apiInterface.updateDatEnoughPhoto(
                toRequestBody(id),
                toRequestBody(nama),
                toRequestBody(alamat),
                toRequestBody(telepon),
                toRequestBody(lat),
                toRequestBody(lon),
                toRequestBody(""));

        absentAdd.enqueue(new Callback<UpdateData>() {
            @Override
            public void onResponse(Call<UpdateData> call, Response<UpdateData> response) {
                progressDialog.dismiss();
                UpdateData status = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (status != null) {

                    if (status.getStatus()) {
                        Toast.makeText(MainActivity.this, "Data Berhasil diupdate", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //     Toast.makeText(ShoppingProductGrid.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        String error = jObjError.get("message").toString();
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        try {
                            Toast.makeText(MainActivity.this, "Send Failed, " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        //    Toast.makeText(ShoppingProductGrid.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public void ambilCamera(){
        Intent intent = new Intent(MainActivity.this, CameraControllerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("inputData", new CameraBundleBuilder()
                .setFullscreenMode(false)
                .setDoneButtonString("Add")
                .setSinglePhotoMode(false)
                .setMax_photo(3)
                .setManualFocus(true)
                .setBucketName(getClass().getName())
                .setPreviewEnableCount(true)
                .setPreviewIconVisiblity(true)
                .setPreviewPageRedirection(true)
                .setEnableDone(false)
                .setClearBucket(true)
                .createCameraBundle());
        startActivityForResult(intent, 214);
    }

    public void mappingEditData(){
        if (editData == 110){
            id_data = getIntent().getStringExtra("id");
            txtName.setText(getIntent().getStringExtra("nama"));
            txtAlamat.setText(getIntent().getStringExtra("alamat"));
            txtPhone.setText(getIntent().getStringExtra("telepon"));
            txtGPS.setText(getIntent().getStringExtra("lat")+";"+getIntent().getStringExtra("lon"));
            String image = getIntent().getStringExtra("photo");
            Picasso.get().load(image).into(imgFoto);
        }
    }
}