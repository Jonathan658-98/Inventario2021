package com.ejemplo.inventario2021.actividades;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.inventario2021.MainActivity;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.List;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.ViewHolder> {

    private List<Producto> elementosFactura;        //Lista para almacenar los elementos a factura
    private Context context;
    //private CharSequence search = "";
    final SelectedAdapter.OnItemClickListener listener;


    public interface OnItemClickListener {   //Crea la interfaz para el onClick
        void onItemClick(Producto item);
    }

    //==============================================================================================
    public SelectedAdapter(Context context, List<Producto> listaProducto, SelectedAdapter.OnItemClickListener listener) {                      //Método constructor
        this.context = context;
        this.elementosFactura = listaProducto;
        this.listener = listener;
    }

    public void item(Producto item) {
        Toast.makeText(context.getApplicationContext(), "Producto selecionado" + item.getDetalle(), Toast.LENGTH_SHORT).show();
    }

    //==============================================================================================
    @NonNull
    @Override
    public SelectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {                 //Método para Inflar el RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_items_factura, parent, false); //Carga el cardView xml
        return new SelectedAdapter.ViewHolder(view);    //Retorna el cardView
    }

    //==============================================================================================
    @Override
    public void onBindViewHolder(@NonNull SelectedAdapter.ViewHolder holder, int position) {
        holder.cargarDatos(elementosFactura.get(position)); //Llama al método y envia la posición del elemento seleccionado
        //holder.actualizarDatos(elementosFactura.get(position));
    }

    //==============================================================================================
    @Override
    public int getItemCount() {
        return elementosFactura.size();
    }   //Devuelve el nùmero de elementos de la lista

    //==============================================================================================
    public class ViewHolder extends RecyclerView.ViewHolder {   //Clase ViewHolder
        TextView codigo, detalle, cantidad;   //Atributos
        EditText precio, venta;
        private ImageView sumar, restar;
        private EditText cantidadVenta;

        public ViewHolder(@NonNull View itemView) {             //Método Constructor
            super(itemView);
            codigo = itemView.findViewById(R.id.txtCodigo);  //Referencia los elementos del archivo xml
            detalle = itemView.findViewById(R.id.txtDetalle);
            cantidad = itemView.findViewById(R.id.txtCantidad);

            sumar = itemView.findViewById(R.id.btnSumar);
            restar = itemView.findViewById(R.id.btnRestar);
            cantidadVenta = itemView.findViewById(R.id.etCantitadVenta);
            //venta = itemView.findViewById(R.id.etCantitadVenta);
            precio = itemView.findViewById(R.id.etPrecio);
        }

        public void cargarDatos(final Producto item) {   //Método para llenar los datos
            //oast.makeText(context.getApplicationContext(), "Item: " + item.getCodigo(), Toast.LENGTH_SHORT).show();

            //int precio = 0;
            codigo.setText(item.getCodigo());
            detalle.setText(item.getDetalle());
            cantidad.setText(item.getCantidad());

            precio.setText(item.getValor());
            cantidadVenta.setText(String.valueOf(item.getSumar()));
            //Toast.makeText(context.getApplicationContext(), item.getDetalle(), Toast.LENGTH_SHORT).show();
            //cantidadVenta.setText(String.valueOf(item.getSumar()));

            //actualizarDatos(item);
            item.setSumar(Integer.parseInt(String.valueOf(cantidadVenta.getText())));


            //cantidadVenta.setText(item.getCantidadVenta());
            //item.setCantidadVenta(String.valueOf(cantidadVenta.getText()));


            Producto producto = new Producto();
            sumar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if(String.valueOf(cantidadVenta.getText()).equals("")){
                        item.setSumar(0);
                    }*/

                    int cantidad = Integer.parseInt(String.valueOf(cantidadVenta.getText()));   //Cantidad del editText
                    int valor = cantidad + 1;
                    item.setSumar(valor);   //Actualiza el valor de la cantidad
                    //item.setCantidadVenta(String.valueOf(cantidadVenta.getText())); //Actualiza el valor de la cantidad
                    cantidadVenta.setText(String.valueOf(item.getSumar()));         //Muestra el valor de la cantidad
                    //Toast.makeText(context.getApplicationContext(), "Detalle: " + item.getSumar(), Toast.LENGTH_SHORT).show();

                    //Toast.makeText(context.getApplicationContext(), "Ha presionado el boton sumar", Toast.LENGTH_SHORT).show();
                }

            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //listener.onItemClick(item); //Método de la interfaz
                    //ventaProductos(item);
                    //Toast.makeText(context.getApplicationContext(), "Producto selecionado" + item.getDetalle(), Toast.LENGTH_SHORT).show();
                }
            });


            restar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int cantidad = Integer.parseInt(String.valueOf(cantidadVenta.getText()));   //Cantidad del editText
                    if (cantidad >= 1) {
                        int valor = cantidad - 1;
                        item.setSumar(valor);
                        cantidadVenta.setText(String.valueOf(item.getSumar()));
                    }
                    //Actualiza el valor de la cantidad

                    /*for(int i = 0; i < elementosFactura.size(); i++){

                    }*/
                    //Toast.makeText(context.getApplicationContext(), "Cantidad: " + item.getCantidadVenta(), Toast.LENGTH_SHORT).show();
                    //item.setSumar(Integer.parseInt(String.valueOf(cantidadVenta.getText())));
                    //cantidadVenta.setText("1");
                }
            });


            /*cantidadVenta.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    //Toast.makeText(context.getApplicationContext(), "Detalle: " + item.getSumar(), Toast.LENGTH_SHORT).show();
                    //return false;
                    if(MotionEvent.ACTION_UP == event.getAction()) {
                        String valor = String.valueOf(cantidadVenta.getText());
                        cantidadVenta.setText(valor);


                    }

                    return false; // return is important...

                }
            });*/

            cantidadVenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {

                }
            });


        }


        //==============================================================================================

        public void actualizarDatos(Producto item) {
            Toast.makeText(context.getApplicationContext(), item.getDetalle(), Toast.LENGTH_SHORT).show();
            item.setSumar(Integer.parseInt(String.valueOf(cantidadVenta.getText())));
        }
    }

    //==============================================================================================

    //==============================================================================================

    public void ventaProductos(Producto item) {    //Método para enviar una alerta para la salida de productos
        //Captura los datos del item producto
        String codigo = item.getCodigo();
        String detalle = item.getDetalle();
        String cantidad = item.getCantidad();
        String precio = item.getValor();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);        //Crea un objeto para generar el alert Dialog

        //Creo El EditText para capturar el dato ingresado por el usr
        final EditText weightInput = new EditText(context);                         //Cre un editText
        weightInput.setInputType(InputType.TYPE_CLASS_TEXT);                      //Permite ingresar datos numericos
        builder.setView(weightInput);                                               //visualiza el editText

        //cosntruye alertDialog
        builder.setMessage(codigo + "\n" + detalle + "\n" + "En Stock: " + cantidad + "\n" + "Digite la cantidad: ")      //Mensaje
                .setTitle("Producto que se va a facturar");                   //Titulo

        //Button para confirmar el proceso
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"Thanks so much", Toast.LENGTH_SHORT).show();
                modificarPrecio(weightInput.getText(), codigo, cantidad, detalle, precio);    //llama al método y envía los parámetros necesarios
            }
        });

        //Button para cancelar el proceso
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "¡No se modifico el precio!", Toast.LENGTH_SHORT).show();
            }
        });

        /*builder.setNeutralButton("No comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //dialog.dismiss(); no es necesario ponerlo porque ya esta implicito para cerrar el dialogo
            }
        });*/


        AlertDialog dialog = builder.create();      //Crea la interfaz del AlertDialog
        dialog.show();                              //mostrar el dialogo

    }

    //==============================================================================================

    private void modificarPrecio(Editable text, String codigo, String cantidadBd, String detalle, String precio) {   //Método para ingresar la cantidad ingresada
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(context, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura

        String[] parametros = {codigo};                             //captura el codigo del producto
        String precioIngresado = text.toString();                     //Convierte a un string el dato ingresado por el usr
        int valorIngresado = Integer.parseInt(precioIngresado);       //Convierte a int la cantidad ingresada
        int valorBD = Integer.parseInt(cantidadBd);                 //Convierte a int la la cantidad de la  BBDD
        int total;
        String totalCantVendida;

        Toast.makeText(context,"Precio ingresado: " + precioIngresado ,  Toast.LENGTH_LONG).show();



        //Código para vender productos
        if (precioIngresado.length() != 0 ) {                           //Cuando ingresa el texto
            ContentValues values = new ContentValues();              //Crea un objeto del tipo ContentValues para interactuar con la BBDD
            //if (valorBD >= valorIngresado ){                         //Si el valor en la BBDD es mayor
                //total = valorBD - valorIngresado;                    //resta la cantidad de productos ingresados

            try{
                //Actualiza la BBDD con los nuevos datos ingresados
                values.put("valor", precioIngresado);
                db.update("productos", values, Utilidades.CAMPO_CODIGO+"=?", parametros);
                db.close();
            }catch (Exception e){
                Toast.makeText(context,e + precioIngresado ,  Toast.LENGTH_LONG).show();

            }


        }else{


        }
    }
    //==============================================================================================
}