package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        final EditText username = findViewById(R.id.txt_username);
        final EditText password = findViewById(R.id.txt_password);
        final Button Login = findViewById(R.id.btnlogin);
        final Button Daftar = findViewById(R.id.btndaftar);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post("http://192.168.137.1/syifa/login.php")
                        .addBodyParameter("username", "admin")
                        .addBodyParameter("password", "admin")
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("sukses", "onResponse: " + response);
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    if (hasil.getBoolean("sukses")) {
                                        Toast.makeText(MainActivity.this, "sukses", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), MainMenu.class);
                                        startActivity(i);
                                    }else {
                                        Toast.makeText(MainActivity.this, "password atau username salah", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "password atau username salah", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                Log.d("error", "onResponse: " + error);
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DaftarActivity.class);
                startActivity(i);
            }
        });
            }

        }