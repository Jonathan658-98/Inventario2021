package com.ejemplo.inventario2021;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class ListaProductosAdapter extends RecyclerView.Adapter<ListaProductosAdapter.ProductosViewHolder>{

    private Context context;
    final ListaProductosAdapter.OnItemClickListener listener;
    private List<Producto> listaProducto;   //Array para los productos
    private List<Producto> filteredProductos;   //Array para los productos filtrados

    public interface OnItemClickListener{   //Crea la interfaz para el onClick
        void onItemClick(Producto item);
    }

    //==============================================================================================

    //Método constructor
    public ListaProductosAdapter(Context context, List<Producto> listaProducto, ListaProductosAdapter.OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.listaProducto = listaProducto;
        this.filteredProductos = listaProducto;
    }

    //==============================================================================================

   /* @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_productos,null,false);
        return new ProductosViewHolder(view);
    }*/


    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_productos, parent,false);
        return new ProductosViewHolder(view);
    }

    //==============================================================================================

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, final int position) {
        //Llena los campos con los datos ya resgitrados
        /*holder.codigo.setText(listaProducto.get(position).getCodigo());
        holder.detalle.setText(listaProducto.get(position).getDetalle());
        holder.cantidad.setText(listaProducto.get(position).getCantidad());*/

        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));  //Implementa la animación
        //holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.binData(filteredProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredProductos.size();
    }

    public void setItems(List<Producto> items){
        listaProducto = items;
    }

    //==============================================================================================


    public class ProductosViewHolder extends RecyclerView.ViewHolder{
        TextView id, codigo, detalle, cantidad, valor;
        CardView cv;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txtId) ;
            codigo = (TextView) itemView.findViewById(R.id.txtCodigo);
            detalle = (TextView) itemView.findViewById(R.id.txtDetalle);
            cantidad = (TextView) itemView.findViewById(R.id.txtCantidad);
            //valor = (TextView) itemView.findViewById(R.id.txtValor) ;
            cv = itemView.findViewById(R.id.cv_productos);
        }

        public void binData(final Producto item) {
            id.setText(item.getId());
            codigo.setText(item.getCodigo());
            detalle.setText(item.getDetalle());
            cantidad.setText(item.getCantidad());

            //valor.setText(item.getValor());

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
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
                    List<Producto> prodFiltered = new ArrayList<>();        //Crea un array el tipo Producto
                    for (Producto row: listaProducto){                      //Recorre toda la lista de elementos
                        if ( row.getCodigo().toLowerCase().contains(Key.toLowerCase()) || row.getDetalle().toLowerCase().contains(Key.toLowerCase()) ){ //Filtrar productos mediante código y detalle
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

    /*
    //Implementar el onClick FORMA2
    public interface RecyclerItemClick{
        void itemClick(Producto item);
    }*/

}
