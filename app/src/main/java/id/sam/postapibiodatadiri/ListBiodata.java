package id.sam.postapibiodatadiri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ListBiodata extends AppCompatActivity {

    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(getIntent().getStringExtra("username"));
    }
}