package com.ejemplo.inventario2021.actividades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.inventario2021.ListaProductosAdapter;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class FacturasAdapter extends RecyclerView.Adapter<FacturasAdapter.FacturasViewHolder> {

    private Context context;
    final FacturasAdapter.OnItemClickListener listener;
    private List<Producto> listaFacturas;   //Array para los productos
    private List<Producto> filteredProductos;   //Array para los productos filtrados

    public interface OnItemClickListener{   //Crea la interfaz para el onClick
        void onItemClick(Producto item);
    }
    //==============================================================================================


    public FacturasAdapter(Context context, List<Producto> listaFacturas, FacturasAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.listaFacturas = listaFacturas;
        this.filteredProductos = listaFacturas;
    }
    //==============================================================================================


    @NonNull
    @Override
    public FacturasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_informe_facturas, parent, false);
        return new FacturasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturasViewHolder holder, int position) {

        holder.cvFacturas.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.llenarDatos(filteredProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredProductos.size();    //retorna el Array con los productos
    }

    //==============================================================================================

    public class FacturasViewHolder extends RecyclerView.ViewHolder {
        TextView id_factura, cliente, fecha, total;
        CardView cvFacturas;

        public FacturasViewHolder(@NonNull View itemView) {
            super(itemView);
            //Referencia los textView del cardView
            id_factura = (TextView) itemView.findViewById(R.id.tvIdFactura);
            cliente =  (TextView)itemView.findViewById(R.id.tvCliente);
            fecha = (TextView) itemView.findViewById(R.id.tvFecha);
            total = (TextView) itemView.findViewById(R.id.tvTotal);
            cvFacturas = itemView.findViewById(R.id.cv_informe_factura);

        }

        public void llenarDatos(final Producto item){
            id_factura.setText(item.getId());
            cliente.setText(item.getProveedor());
            fecha.setText(item.getFecha());
            total.setText((String.valueOf(item.getTotal())));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                    filteredProductos = listaFacturas;  //El Array filtered tomas los datos de los productos
                }
                else{
                    List<Producto> prodFiltered = new ArrayList<>();        //Crea un array el tipo Producto
                    for (Producto row: listaFacturas){                      //Recorre toda la lista de elementos
                        if ( row.getProveedor().toLowerCase().contains(Key.toLowerCase()) || row.getFecha().toLowerCase().contains(Key.toLowerCase()) ){ //Filtrar productos mediante código y detalle
                            prodFiltered.add(row);
                        }
                    }
                    filteredProductos = prodFiltered;
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

    //==============================================================================================

}
