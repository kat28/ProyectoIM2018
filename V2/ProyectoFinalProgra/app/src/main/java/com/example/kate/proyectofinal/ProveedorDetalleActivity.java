package com.example.kate.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProveedorDetalleActivity extends AppCompatActivity {

    EditText nombre, direccion, telefono, idProv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_detalle);


        this.idProv = (EditText) findViewById(R.id.input_idProv);
        this.nombre = (EditText) findViewById(R.id.input_nombre);
        this.direccion = (EditText) findViewById(R.id.input_direccion);
        this.telefono = (EditText) findViewById(R.id.input_tel);


        idProv.setText(getIntent().getStringExtra("IdProveedor"));
        idProv.setEnabled(false);
        nombre.setText(getIntent().getStringExtra("Nombre"));
        direccion.setText(getIntent().getStringExtra("Direccion"));
        telefono.setText(getIntent().getStringExtra("Telefono"));

        findViewById(R.id.button_actualizar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextInputLayout tilidProv = (TextInputLayout) findViewById(R.id.input_layout_idProv);
                TextInputLayout tilNombre = (TextInputLayout) findViewById(R.id.input_layout_nombre);
                TextInputLayout tildireccion = (TextInputLayout) findViewById(R.id.input_layout_direccion);
                TextInputLayout tiltelefono = (TextInputLayout) findViewById(R.id.input_layout_tel);

                tilidProv.setError(null);
                tilNombre.setError(null);
                tildireccion.setError(null);
                tiltelefono.setError(null);

                if(nombre.getText().toString().equals("")){
                    tilNombre.setError("Campo requerido");
                    return;
                }
                if(direccion.getText().toString().equals("")){
                    tildireccion.setError("Campo requerido");
                    return;
                }
                if(telefono.getText().toString().equals("")){
                    tiltelefono.setError("Campo requerido");
                    return;
                }

                RequestQueue requestQueue = Volley.newRequestQueue(ProveedorDetalleActivity.this);
                String URL = getResources().getString(R.string.url)+"/api/proveedor/"+ idProv.getText().toString();
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("IdProveedor", idProv.getText().toString());
                    jsonBody.put("Nombre", nombre.getText().toString());
                    jsonBody.put("Direccion", direccion.getText().toString());
                    jsonBody.put("Telefono",  telefono.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Toast.makeText(ProveedorDetalleActivity.this,"Proveedor Actualizado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProveedorDetalleActivity.this, MainActivity.class);
                        ProveedorDetalleActivity.this.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                requestQueue.add(stringRequest);
            }

        });

        findViewById(R.id.button_eliminar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(ProveedorDetalleActivity.this);
                String URL = getResources().getString(R.string.url)+"/api/proveedor/"+ idProv.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Toast.makeText(ProveedorDetalleActivity.this,"Proveedor Eliminado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProveedorDetalleActivity.this, MainActivity.class);
                        ProveedorDetalleActivity.this.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                });
                requestQueue.add(stringRequest);
            }
        });

    }
}
