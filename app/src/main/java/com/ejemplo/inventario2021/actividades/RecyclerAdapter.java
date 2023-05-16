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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.inventario2021.ListaProductosAdapter;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.interfaces.ShowListener;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    //final RecyclerAdapter.OnItemClickListener listener;
    private List<Producto> listaProducto;   //Array para los productos
    private List<Producto> filteredProductos;   //Array para los productos filtrados
    private ShowListener showListener;

    /*public interface OnItemClickListener{  < //Crea la interfaz para el onClick
        void onItemClick(Producto item);
    }*/

    //==============================================================================================




    public RecyclerAdapter(Context context, List<Producto> listaProducto, ShowListener showListener){
        //this.listener = listener;
        this.context = context;
        this.listaProducto = listaProducto;
        this.filteredProductos = listaProducto;
        this.showListener = showListener;
    }

    //Método constructor
    /*public RecyclerAdapter(Context context, List<Producto> listaProducto, RecyclerAdapter.OnItemClickListener listener){
        this.context = context;
        this.listener = listener;
        this.listaProducto = listaProducto;
        this.filteredProductos = listaProducto;
    }*/

    //==============================================================================================

   /* @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_productos,null,false);
        return new ProductosViewHolder(view);
    }*/


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_items_venta,parent,false); //carga el cardview
        return new RecyclerAdapter.ViewHolder(view);
    }


    //==============================================================================================

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Llena los campos con los datos ya resgitrados
        /*holder.codigo.setText(listaProducto.get(position).getCodigo());
        holder.detalle.setText(listaProducto.get(position).getDetalle());
        holder.cantidad.setText(listaProducto.get(position).getCantidad());*/

        //holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));  //Implementa la animación
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide));
        holder.binData(filteredProductos.get(position));
    }
    //==============================================================================================

    @Override
    public int getItemCount() {
        return filteredProductos.size();
    }

    public void setItems(List<Producto> items){
        listaProducto = items;
    }

    public List<Producto> getSelectedElements(){
        List<Producto> selectedElements = new ArrayList<>();
        for(Producto listElement: listaProducto){
            if(listElement.isSelected){
                    selectedElements.add(listElement);
            }
        }
        return selectedElements;
    }


    //==============================================================================================
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, codigo, detalle, cantidad, valor;
        CardView cv;
        View viewBackground;
        ConstraintLayout layoutTvShow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txtId) ;
            codigo = (TextView) itemView.findViewById(R.id.txtCodigo);
            detalle = (TextView) itemView.findViewById(R.id.txtDetalle);
            cantidad = (TextView) itemView.findViewById(R.id.txtCantidad);
            //valor = (TextView) itemView.findViewById(R.id.txtValor) ;
            cv = itemView.findViewById(R.id.cv_Venta);
            viewBackground = itemView.findViewById(R.id.viewBackground);

        }

        public void binData(final Producto item) {
            id.setText(item.getId());
            codigo.setText(item.getCodigo());
            detalle.setText(item.getDetalle());
            cantidad.setText(item.getCantidad());
            //valor.setText(item.getValor());

            //Toast.makeText(context.getApplicationContext(), item.getDetalle(), Toast.LENGTH_SHORT).show();


            if(item.isSelected)
                viewBackground.setBackgroundResource(R.drawable.show_selected_background);
            else
                viewBackground.setBackgroundResource(R.drawable.show_background);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    viewBackground.setBackgroundResource(R.drawable.show_background);   //Mostar el xml con la forma para selccionar
                    //listener.onItemClick(item);
                }
            });

            //Evento para marcar los cardview
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBackground.setBackgroundResource(R.drawable.show_selected_background);
                    if(item.isSelected){    //Si selecciona
                        viewBackground.setBackgroundResource(R.drawable.show_background);
                        item.isSelected = false;
                        if(getSelectedElements().size() == 0)   //si el arrayList no tiene elementos
                            showListener.onShowAction(false);   //llama al método de la interfaz
                    }else {
                        viewBackground.setBackgroundResource(R.drawable.show_selected_background);
                        item.isSelected = true;
                        showListener.onShowAction(true);
                    }
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
}
