package com.example.kate.proyectofinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProveedorActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    RecyclerView recyclerView;
    ProveedorAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);

        new AsyncFetch().execute();

        findViewById(R.id.button_nuevo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProveedorActivity.this, ProveedorNuevoActivity.class);
                ProveedorActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(ProveedorActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tCargando Información del Api...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(getResources().getString(R.string.url) + "/api/proveedor/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("No fue posible mostrar la informacion");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();

            ArrayList<Proveedor> proveedores = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(result);

                //Toma la informacion del JSON y la almacena en un ArrayList
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Proveedor proveedor = new Proveedor();
                    proveedor.setIdProveedor(json_data.getString("IdProveedor").trim());
                    proveedor.setNombre(json_data.getString("Nombre").trim());
                    proveedor.setDireccion(json_data.getString("Direccion").trim());
                    proveedor.setTelefono(json_data.getString("Telefono").trim());
                    proveedores.add(proveedor);
                }

                //Muestra la informacion en el recyclerView
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                adapter = new ProveedorAdapter(ProveedorActivity.this, proveedores);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProveedorActivity.this));

            } catch (JSONException e) {
                Toast.makeText(ProveedorActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
