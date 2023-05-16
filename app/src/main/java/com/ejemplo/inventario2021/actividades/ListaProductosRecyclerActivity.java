package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ejemplo.inventario2021.DescriptionActivity;
import com.ejemplo.inventario2021.ListaProductosAdapter;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaProductosRecyclerActivity extends AppCompatActivity {

    //private List<Producto> listProducto;
    private RecyclerView rvLista;
    private CharSequence search = "";
    private EditText searchView;
    private ListaProductosAdapter adapter;

    ConexionSQLiteHelper conn;  //Para conectar con la BBDD
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_recycler);

        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);

        searchView = findViewById(R.id.search_bar);

        //recyclerViewProductos = (RecyclerView) findViewById(R.id.recyclerProductos);
        //recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        
        consultarListaProductos();  //Llama al método

    }

    //==============================================================================================

    //Método par consultar los productos de la BBDD
    private void consultarListaProductos() {

        List<Producto> listProducto = new ArrayList<>();
        SQLiteDatabase db = conn.getWritableDatabase();  //Abrir la conexion con la BBDD

        Producto producto = null;     //Para llenar la informacion
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PRODUCTO, null);
            while (cursor.moveToNext()){
                producto = new Producto();
                producto.setId(cursor.getString(0));
                producto.setCodigo(cursor.getString(1));
                producto.setDetalle(cursor.getString(2));
                producto.setCantidad(cursor.getString(3));
                producto.setValor(cursor.getString(4));
                producto.setProveedor(cursor.getString(5));

                listProducto.add(producto);  //Agrega los datos en la lista productos
            }

            //Toast.makeText(this, "Total productos: " + listProducto.size(), Toast.LENGTH_SHORT).show();

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

         adapter = new ListaProductosAdapter(this, listProducto, new ListaProductosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) {

                moveToDescription(item);
            }
        });

         rvLista = findViewById(R.id.recyclerProductos);
         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
         rvLista.setLayoutManager(layoutManager);
         rvLista.setAdapter(adapter);

    }

    //==============================================================================================
    //Método para cambiar de activity
    private void moveToDescription(Producto item) {
        //Toast.makeText(this, "El: " + item, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DescriptionActivity.class);    //Cambia de actividad con el metodo onClick
        intent.putExtra("Producto", (Serializable) item); //Envia parametros
        startActivity(intent);
    }

    //==============================================================================================

}