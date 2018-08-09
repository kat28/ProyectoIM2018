package com.example.kate.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> implements Filterable {
    ArrayList<Producto> productos = new ArrayList<Producto>();
    private ArrayList<Producto> filteredList = new ArrayList<>();
    Context context;

    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        this.productos = productos;
        this.context = context;
        this.filteredList = productos;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_producto_view_layout, parent, false);
        ProductoViewHolder productoViewHolder = new ProductoViewHolder(view, filteredList);
        return productoViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        final Producto producto = filteredList.get(position);
        holder.idProducto.setText(producto.getIdProducto());
        holder.nombre.setText(producto.getNombre());
        holder.precio.setText(producto.getPrecio());
        holder.cantidad.setText(producto.getCantidad());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductoDetalleActivity.class);
                intent.putExtra("IdProducto", producto.getIdProducto());
                intent.putExtra("Nombre", producto.getNombre());
                intent.putExtra("Precio", producto.getPrecio());
                intent.putExtra("Cantidad", producto.getCantidad());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredList = productos;
                } else {

                    ArrayList<Producto> newFilteredList = new ArrayList<>();

                    for (Producto producto : productos) {
                        if (producto.getNombre().toLowerCase().contains(charString)) {
                            newFilteredList.add(producto);
                        }
                    }
                    filteredList = newFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Producto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, precio, cantidad, idProducto;
        ArrayList<Producto> productos;

        public ProductoViewHolder(View view, ArrayList<Producto> jugadores) {
            super(view);
            this.productos = productos;

            idProducto = (TextView) view.findViewById(R.id.idProducto);
            nombre = (TextView) view.findViewById(R.id.nombre);
            precio = (TextView) view.findViewById(R.id.precio);
            cantidad = (TextView) view.findViewById(R.id.cantidad);
        }

    }
}
