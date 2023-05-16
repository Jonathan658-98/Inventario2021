package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ejemplo.inventario2021.DescriptionActivity;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InformeFacturasActivity extends AppCompatActivity {

    //private List<Producto> listaFacturas;
    private RecyclerView rvLista;
    private CharSequence search = "";
    private EditText searchView;
    private FacturasAdapter adapter;

    ConexionSQLiteHelper conn;  //Para conectar con la BBDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_facturas);

        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        searchView = findViewById(R.id.search_bar);

        //rvLista = findViewById(R.id.recyclerFactura);   //Referencia el recycler

        consultarListaFacturas();


    }

    //==============================================================================================

    private void consultarListaFacturas() {
    int i = 0;
        List<Producto> listaFacturas = new ArrayList<>();

        //ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();             //Abre la conexión con la BBDD

        //ContentValues values = new ContentValues();

        Producto producto = null;     //Para llenar la informacion
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_FACTURAS, null);    //Realiza una consulta en la BBDD
        while (cursor.moveToNext()){
            producto = new Producto();
            i++;
            producto.setId(cursor.getString(0));
            producto.setProveedor(cursor.getString(1));
            producto.setFecha(cursor.getString(2));
            producto.setTotal(Float.parseFloat(cursor.getString(3)));
            producto.setTotalItem(String.valueOf(i));
            listaFacturas.add(producto);    //Agrega los datos en la lista
        }

        /*Cursor detalle = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_DETALLE, null);
        while (detalle.moveToNext()){
            producto = new Producto();
            producto.setIdFactura(detalle.getString(1));
            producto.setCodigoVenta(detalle.getString(2));
            producto.setDetalleVenta(detalle.getString(3));
            producto.setCantidadVenta(detalle.getString(4));
            producto.setPrecioVenta(detalle.getString(5));
            producto.setTotalItem(detalle.getString(6));

            listaFacturas.add(producto);
        }*/



        /*for(int i = 0; i < listaFacturas.size(); i++){
            Toast.makeText(this, "Id_fac: "+ listaFacturas.get(i).getId() +"\n"+
                    "Nombre: "+ listaFacturas.get(i).getProveedor() +"\n"+
                    "Fecha: "+ listaFacturas.get(i).getFecha() +"\n"+
                    "Total: "+ listaFacturas.get(i).getTotal() +"\n", Toast.LENGTH_SHORT).show();
        }*/

        try{
            setUserRecycler(listaFacturas);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }




        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    //==============================================================================================
    private void setUserRecycler(List<Producto> listaFacturas) {
            adapter = new FacturasAdapter(this, listaFacturas, new FacturasAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Producto item) {
                    moveToDescription(item, (ArrayList<Producto>)listaFacturas);
                }
            });

            rvLista = findViewById(R.id.recyclerFactura);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rvLista.setLayoutManager(layoutManager);
            rvLista.setAdapter(adapter);



    }
    //==============================================================================================
    //Método para cambiar de activity
    private void moveToDescription(Producto item, ArrayList<Producto> facturas) {
        //Toast.makeText(this, "El: " + item, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), DescriptionFacturasActivity.class);    //Cambia de actividad con el metodo onClick
        intent.putExtra("Producto", (Serializable) item); //Envia parametros
        //intent.putExtra("Elementos", (ArrayList<? extends Parcelable>) elementos);
        //intent.putParcelableArrayListExtra("Facturas", (ArrayList<? extends Parcelable>) facturas);
        startActivity(intent);
    }
    //==============================================================================================

}