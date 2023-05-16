package com.ejemplo.inventario2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ejemplo.inventario2021.actividades.ImprimirActivity;
import com.ejemplo.inventario2021.actividades.ListaProductosRecyclerActivity;
import com.ejemplo.inventario2021.actividades.ListaVentasActivity;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.fragments.InicioFragment;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ConexionSQLiteHelper conn;  //Para conectar con la BBDD
    //<color name="purple_700">#006db3</color>
    // <color name="purple_500">#039be5</color>

    //IComunicaFragments
    Button ventaProductos;
    Fragment fragmentInicio;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Referenciar el fragmenti inicial
        fragmentInicio = new InicioFragment();

        ventaProductos = findViewById(R.id.ventaProductos);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit(); //remplazar por el fragment inicial
        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
    }
    //==============================================================================================

    //Método para mostrar y ocultar el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //Método para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        int id = item.getItemId();  //Recuperar el item que se esta presionando
        if(id == R.id.imprimirInventario){
            //Toast.makeText(this, "Opcion 1:", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, ImprimirActivity.class);
            startActivity(intent);

        }else if(id == R.id.prodRegistrados){
            consultarListaProductos();
        }else if(id == R.id.salir){
            //Toast.makeText(this, "Opcion 3:", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //==============================================================================================
   void onClick(View view){
        Intent miintent = null;
        switch (view.getId()){
            case R.id.cardListar:
                miintent = new Intent(MainActivity.this, ListaProductosRecyclerActivity.class);
                Toast.makeText(getApplicationContext(),"Ingresar Productos", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //==============================================================================================
    private void consultarListaProductos() {    //Método que imprime el total de productos regustrados
        List<Producto> listProducto = new ArrayList<>();
        SQLiteDatabase db = conn.getWritableDatabase();  //Abrir la conexion con la BBDD

        Producto producto = null;     //Para llenar la informacion
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PRODUCTO, null);
        while (cursor.moveToNext()) {
            producto = new Producto();
            producto.setId(cursor.getString(0));
            producto.setCodigo(cursor.getString(1));
            listProducto.add(producto);  //Agrega los datos en la lista productos
        }
        Toast.makeText(this, "Total productos registrados: " + listProducto.size(), Toast.LENGTH_SHORT).show();
    }
    //==============================================================================================
}