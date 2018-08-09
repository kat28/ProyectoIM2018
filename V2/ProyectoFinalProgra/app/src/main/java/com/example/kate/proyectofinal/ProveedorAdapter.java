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

public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ProveedorViewHolder> implements Filterable {

    ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
    private ArrayList<Proveedor> filteredList = new ArrayList<>();
    Context context;

    public ProveedorAdapter(Context context, ArrayList<Proveedor> proveedores) {
        this.proveedores = proveedores;
        this.context = context;
        this.filteredList = proveedores;
    }

    @Override
    public ProveedorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_proveedor_view_layout, parent, false);
        ProveedorAdapter.ProveedorViewHolder proveedorViewHolder = new ProveedorAdapter.ProveedorViewHolder(view,filteredList);
        return proveedorViewHolder;
    }

    @Override
    public void onBindViewHolder(ProveedorViewHolder holder, int position) {
        final Proveedor proveedor = filteredList.get(position);
        holder.idProveedor.setText(proveedor.getIdProveedor());
        holder.nombre.setText(proveedor.getNombre());
        holder.direccion.setText(proveedor.getDireccion());
        holder.telefono.setText(proveedor.getTelefono());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProveedorDetalleActivity.class);
                intent.putExtra("IdProveedor", proveedor.getIdProveedor());
                intent.putExtra("Nombre", proveedor.getNombre());
                intent.putExtra("Direccion", proveedor.getDireccion());
                intent.putExtra("Telefono", proveedor.getTelefono());
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
                    filteredList = proveedores;
                } else {

                    ArrayList<Proveedor> newFilteredList = new ArrayList<>();

                    for (Proveedor proveedor : proveedores) {
                        if (proveedor.getNombre().toLowerCase().contains(charString)) {
                            newFilteredList.add(proveedor);
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
                filteredList = (ArrayList<Proveedor>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ProveedorViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, direccion, telefono, idProveedor;
        ArrayList<Proveedor> proveedores;

        public ProveedorViewHolder(View view, ArrayList<Proveedor> proveedores) {
            super(view);
            this.proveedores = proveedores;

            idProveedor = (TextView) view.findViewById(R.id.idProveedor);
            nombre = (TextView) view.findViewById(R.id.nombre);
            direccion = (TextView) view.findViewById(R.id.direccion);
            telefono = (TextView) view.findViewById(R.id.telefono);
        }

    }
}
