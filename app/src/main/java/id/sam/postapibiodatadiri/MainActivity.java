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

import id.sam.postapibiodatadiri.model.Result;
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
    GeoLocator geoLocator;

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

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUserData(txtName.getText().toString(),
                        txtAlamat.getText().toString(),
                        txtPhone.getText().toString(),
                        String.valueOf(geoLocator.getLattitude()),
                        String.valueOf(geoLocator.getLongitude()));
            }
        });

        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilCamera();
            }
        });
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
}