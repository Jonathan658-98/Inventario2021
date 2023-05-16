package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.inventario2021.DescriptionActivity;
import com.ejemplo.inventario2021.ListaProductosAdapter;
import com.ejemplo.inventario2021.MainActivity;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaVentasActivity extends AppCompatActivity {

    private TextView codigo;
    private TextView cantidad;
    private TextView detalle;
    private TextView fecha;



    private List<Producto> listProducto;
    private RecyclerView rvLista;
    private CharSequence search = "";
    private EditText searchView;
    private ListaVentasAdapter adapter;
    //private ListaProductosAdapter adapter;


    private  int dia, mes = 0, año;
    TextView tvFecha;


    ConexionSQLiteHelper conn;  //Para conectar con la BBDD
    //==============================================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ventas);

        /*codigo = findViewById(R.id.txtCodigo);
        cantidad = findViewById(R.id.txtCantidad);
        detalle = findViewById(R.id.txtDetalle);
        fecha = findViewById(R.id.ventaFecha);*/

        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);

        searchView = findViewById(R.id.search_bar);

        consultarListaProductos();  //Llama al método
        //registrarVenta();
       //mostrarFecha();
    }

    //==============================================================================================

    private void registrarVenta() {

        /*ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();*/

    }


    private void mostrarFecha() {
        Calendar fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        año = fecha.get(Calendar.YEAR);
        Toast.makeText(getApplicationContext(), "Fecha: " +dia+"/"+(mes+1)+"/"+año,Toast.LENGTH_SHORT).show();
        //tvFecha.setText(dia+"/"+mes+"/"+año);
    }
    //==============================================================================================

    //Método par consultar los productos de la BBDD
    private void consultarListaProductos() {

        //List<Producto> listProducto = new ArrayList<>();
        listProducto = new ArrayList<>();
        SQLiteDatabase db = conn.getWritableDatabase();  //Abrir la conexion con la BBDD

        Producto producto = null;     //Para llenar la informacion
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_VENTAS, null);    //Realiza una consulta en la BBDD
        while (cursor.moveToNext()){    //Accede a todos los datos de la BBDD
            producto = new Producto();

            producto.setIdVenta(cursor.getString(0));
            producto.setCodigoVenta(cursor.getString(1));
            producto.setDetalleVenta(cursor.getString(2));
            producto.setCantidadVenta(cursor.getString(3));
            producto.setPrecioVenta(cursor.getString(4));
            producto.setFechaVenta(cursor.getString(5));

            listProducto.add(producto);  //Agrega los datos en la lista productos - el ArrayList contiene todos los productos con sus elementos
        }

        /*for(int i = 0; i < listProducto.size(); i++){
            Toast.makeText(this, "Total productos: " + listProducto.get(i).getDetalle(), Toast.LENGTH_LONG).show();
        }*/

        //Toast.makeText(this, "Total productos: " + listProducto.size() , Toast.LENGTH_LONG).show();

        setUserRecycler(listProducto);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                adapter.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //==============================================================================================

    private void setUserRecycler(List<Producto> listProducto) {
        adapter = new ListaVentasAdapter(this, listProducto, new ListaVentasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) {    //Implementa el método de la interfaz
                moveToDescription(item, listProducto);
            }
        });

        rvLista = findViewById(R.id.recyclerProductosVendidos);                 //referencia a la interfaz xml con el cardView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvLista.setLayoutManager(layoutManager);
        rvLista.setAdapter(adapter);

    }

    //==============================================================================================
    //Método para cambiar de activity
    private void moveToDescription(Producto item, List<Producto> listProducto) {
        generarAlertDialog(item);
    //Toast.makeText(this, "El: " + item, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "El: " + listProducto.get(0).getCodigoVenta(), Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(this, DescriptionActivity.class);    //Cambia de actividad con el metodo onClick
        //ntent.putExtra("Producto", item); //Envia parametros
        //startActivity(intent);
    }

    //==============================================================================================
    private void generarAlertDialog(Producto item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Desea eliminar este producto")
                .setTitle("¡Atención!");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Thanks so much", Toast.LENGTH_SHORT).show();
                eliminarProductoVendido(item);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"¡No se elimino el producto!", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();  //mostrat el dialogo

    }

    //==============================================================================================

    public void eliminarProductoVendido(Producto item){
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {item.getCodigoVenta()};

        db.delete("ventas", Utilidades.CAMPO_CODIGO_VENTA + "=?", parametros);
        Toast.makeText(getApplicationContext(), " Se eliminó el producto", Toast.LENGTH_LONG).show();
        db.close(); //cerrar la concexión

        //Regresa al menu inicial
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //==============================================================================================
}