package com.ejemplo.inventario2021.actividades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.inventario2021.ListaProductosAdapter;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaVentasAdapter extends RecyclerView.Adapter<ListaVentasAdapter.ViewHolder> {

    private Context context;
    final ListaVentasAdapter.OnItemClickListener listener;
    private List<Producto> listaProducto;   //Array para los productos
    private List<Producto> filteredProductos;   //Array para los productos filtrados

    public interface OnItemClickListener {   //Crea la interfaz para el onClick
        void onItemClick(Producto item);
    }

    //==============================================================================================

    //Método constructor
    public ListaVentasAdapter(Context context, List<Producto> listaProducto, ListaVentasAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.listaProducto = listaProducto;
        this.filteredProductos = listaProducto;
    }

    //==============================================================================================
    @NonNull
    @Override
    public ListaVentasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //Método para visualizar el cardView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productos_vendidos, parent, false); //carga el cardview
        return new ListaVentasAdapter.ViewHolder(view);
    }

    //==============================================================================================
    @Override
    public void onBindViewHolder(@NonNull ListaVentasAdapter.ViewHolder holder, int position) {
        //Llena los campos con los datos ya resgitrados
        /*holder.codigo.setText(listaProducto.get(position).getCodigo());
        holder.detalle.setText(listaProducto.get(position).getDetalle());
        holder.cantidad.setText(listaProducto.get(position).getCantidad());*/
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.binData(filteredProductos.get(position));
    }

    //==============================================================================================
    @Override
    public int getItemCount() {
        return filteredProductos.size();
    }

    //==============================================================================================
    private void mostrarFecha() {


        //Toast.makeText(getApplicationContext(), "Fecha: " +dia+"/"+(mes+1)+"/"+año,Toast.LENGTH_SHORT).show();
        //tvFecha.setText(dia+"/"+mes+"/"+año);
    }

    //==============================================================================================

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigoVenta, cantidadVenta, detalleVenta, fechaVenta, idVenta;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idVenta = (TextView) itemView.findViewById(R.id.txtId);
            codigoVenta = (TextView) itemView.findViewById(R.id.ventaCodigo);
            detalleVenta = (TextView) itemView.findViewById(R.id.ventaDetalle);
            cantidadVenta = (TextView) itemView.findViewById(R.id.ventaCantidad);
            fechaVenta = (TextView) itemView.findViewById(R.id.ventaFecha);

            cv = itemView.findViewById(R.id.cv_prodVendidos);
        }

        public void binData(final Producto item) {
            idVenta.setText(item.getIdVenta());
            codigoVenta.setText(item.getCodigoVenta());
            detalleVenta.setText(item.getDetalleVenta());
            cantidadVenta.setText(item.getCantidadVenta());
            fechaVenta.setText(item.getFechaVenta());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    //==============================================================================================

    //Filtro de búsqueda
    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key = charSequence.toString();
                if(Key.isEmpty()){  //Si es vacío
                    filteredProductos = listaProducto;  //El Array filtered tomas los datos de los productos
                }
                else{
                    List<Producto> prodFiltered = new ArrayList<>(); //Crea un array el tipo Producto
                    for (Producto row: listaProducto){  //Recorre toda la lista de elementos
                        //toma los datos: codigos y detalle del array listaProducto
                        if ( row.getCodigoVenta().toLowerCase().contains(Key.toLowerCase()) || row.getDetalleVenta().toLowerCase().contains(Key.toLowerCase()) ){ //Filtrar productos mediante código y detalle
                            prodFiltered.add(row); //añande al nuevo array los elementos encontrados
                        }
                    }
                    filteredProductos = prodFiltered;   //los productos filtrados se añaden al array de objetos
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProductos;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                filteredProductos = (List<Producto>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    //==============================================================================================0
}



