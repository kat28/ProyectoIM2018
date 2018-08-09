package com.example.kate.proyectofinal;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ProductoDetalleActivity extends AppCompatActivity {

    EditText nombre, precio, cantidad, idPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        this.idPro = (EditText) findViewById(R.id.input_idPro);
        this.nombre = (EditText) findViewById(R.id.input_nombre);
        this.precio = (EditText) findViewById(R.id.input_precio);
        this.cantidad = (EditText) findViewById(R.id.input_cantidad);


        idPro.setText(getIntent().getStringExtra("IdProducto"));
        idPro.setEnabled(false);
        nombre.setText(getIntent().getStringExtra("Nombre"));
        precio.setText(getIntent().getStringExtra("Precio"));
        cantidad.setText(getIntent().getStringExtra("Cantidad"));

        findViewById(R.id.button_actualizar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextInputLayout tilIdPro = (TextInputLayout) findViewById(R.id.input_layout_idPro);
                TextInputLayout tilNombre = (TextInputLayout) findViewById(R.id.input_layout_nombre);
                TextInputLayout tilPrecio = (TextInputLayout) findViewById(R.id.input_layout_precio);
                TextInputLayout tilCantidad = (TextInputLayout) findViewById(R.id.input_layout_cantidad);

                tilIdPro.setError(null);
                tilNombre.setError(null);
                tilPrecio.setError(null);
                tilCantidad.setError(null);

                if(nombre.getText().toString().equals("")){
                    tilNombre.setError("Campo requerido");
                    return;
                }
                if(precio.getText().toString().equals("")){
                    tilPrecio.setError("Campo requerido");
                    return;
                }
                if(cantidad.getText().toString().equals("")){
                    tilCantidad.setError("Campo requerido");
                    return;
                }

                RequestQueue requestQueue = Volley.newRequestQueue(ProductoDetalleActivity.this);
                String URL = getResources().getString(R.string.url)+"/api/Producto/"+ idPro.getText().toString();
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("IdProducto", idPro.getText().toString());
                    jsonBody.put("Nombre", nombre.getText().toString());
                    jsonBody.put("Precio", precio.getText().toString());
                    jsonBody.put("Cantidad",  cantidad.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Toast.makeText(ProductoDetalleActivity.this,"Producto Actualizado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProductoDetalleActivity.this, MainActivity.class);
                        ProductoDetalleActivity.this.startActivity(intent);
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
                RequestQueue requestQueue = Volley.newRequestQueue(ProductoDetalleActivity.this);
                String URL = getResources().getString(R.string.url)+"/api/Producto/"+ idPro.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Toast.makeText(ProductoDetalleActivity.this,"Producto Eliminado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProductoDetalleActivity.this, MainActivity.class);
                        ProductoDetalleActivity.this.startActivity(intent);
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
