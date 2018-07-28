package com.example.kate.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    RecyclerView recyclerView;
    ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make call to AsyncTask
       // new AsyncFetch().execute();
        findViewById(R.id.button_producto).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductoActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.button_proveedor).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProveedorActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

/*     @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }*/

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        adapter.getFilter().filter(newText);
//        return true;
//    }
//
//    private class AsyncFetch extends AsyncTask<String, String, String> {
//        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
//        HttpURLConnection conn;
//        URL url = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //this method will be running on UI thread
//            pdLoading.setMessage("\tLoading...");
//            pdLoading.setCancelable(false);
//            pdLoading.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                url = new URL(getResources().getString(R.string.url)+"/api/producto/");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return e.toString();
//            }
//            try {
//                // Setup HttpURLConnection class to send and receive data from php and mysql
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(READ_TIMEOUT);
//                conn.setConnectTimeout(CONNECTION_TIMEOUT);
//                conn.setRequestMethod("GET");
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                return e1.toString();
//            }
//            try {
//                int response_code = conn.getResponseCode();
//                if (response_code == HttpURLConnection.HTTP_OK) {
//                    InputStream input = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//                    return (result.toString());
//                } else {
//                    return ("unsuccessful");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                conn.disconnect();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            pdLoading.dismiss();
//
//            ArrayList<Producto> jugadores = new ArrayList<>();
//
//            try {
//                JSONArray jArray = new JSONArray(result);
//                // Extract data from json and store into ArrayList as class objects
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    Producto jugador = new Producto();
//                    jugador.setIdProducto(json_data.getString("IdProducto").trim());
//                    jugador.setNombre(json_data.getString("Nombre").trim());
//                    jugador.setPrecio(json_data.getString("Precio").trim());
//                    jugador.setCantidad(json_data.getString("Cantidad").trim());
//                    jugadores.add(jugador);
//                }
//
//                // Setup and Handover data to recyclerview
//                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//                adapter = new ProductoAdapter(MainActivity.this, jugadores);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//            } catch (JSONException e) {
//                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }
//    }
}
