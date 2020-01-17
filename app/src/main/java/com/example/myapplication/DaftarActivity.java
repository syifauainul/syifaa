package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class DaftarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        final EditText username = findViewById(R.id.txt_username);
        final EditText password = findViewById(R.id.txt_password);
        Button Login = findViewById(R.id.btnlogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post("http://192.168.137.1/syifa/insert.php")
                        .addBodyParameter("username", username.getText().toString())
                        .addBodyParameter("password", password.getText().toString())
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean respon  = hasil.getBoolean("respon");
                                    if (respon) {
                                        Toast.makeText(getApplicationContext(), "sukses daftar", Toast.LENGTH_LONG).show();
                                        //finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
