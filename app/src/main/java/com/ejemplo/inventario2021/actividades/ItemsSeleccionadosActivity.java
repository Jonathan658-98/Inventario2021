package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemsSeleccionadosActivity extends AppCompatActivity {


    private Button facturar;
    private RecyclerView rvElementos;
    private SelectedAdapter selectedAdapter;
    private List<Producto> listProducto;
    private RecyclerView rvLista;
    private ImageView sumar, restar;
    private EditText cantidadVenta;
    private List<Producto> elementos = new ArrayList<>();  //Aray para los elementos seleccionados


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_seleccionados);

        facturar = findViewById(R.id.btnFacturar);
        sumar = findViewById(R.id.btnSumar);
        cantidadVenta = findViewById(R.id.etCantitadVenta);


        //Producto elementos = (Producto) getIntent().getParcelableArrayListExtra("Elementos", ); //Recibe el parametro enviado desde ListaProductosVentaActivity

        elementos = getIntent().getParcelableArrayListExtra("Elementos");
        //Toast.makeText(getApplicationContext(), "Cantidad: " + cantidadVenta.getText(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "Cantidad: " + elementos.get(0).get).show();
        //lista = new ArrayList<>();
        //lista = getIntent().getParcelableArrayExtra("Elementos");

        /*elementos = getIntent().getParcelableArrayListExtra("Elementos");
        getIntent().getParcelableArrayListExtra(elementos);*/

        /*Bundle recibeDatos = getIntent().getExtras();
        String [] info = recibeDatos.getStringArray("keyDatos");
        // info. = getIntent().getExtras();*/

        //Toast.makeText(getApplicationContext(), "Nombre: "+info[0]+ "\nApellido: "+info[1]+ "\nEdad: "+info[2], Toast.LENGTH_SHORT).show();

        facturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityFactura.class);
                intent.putParcelableArrayListExtra("Elementos", (ArrayList<? extends Parcelable>) elementos);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Productos listos para factgurar.!!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Detalle: " + elementos.get(0).getDetalle(), Toast.LENGTH_SHORT).show();
            }
        });

        cargarRecyclerView();
    }
    //==============================================================================================
    private void cargarRecyclerView() {
        /*ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);

        listProducto = new ArrayList<>();
        SQLiteDatabase db = conn.getWritableDatabase();  //Abrir la conexion con la BBDD

        Producto producto = null;     //Para llenar la informacion
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PRODUCTO, null);    //Realiza una consulta en la BBDD
        while (cursor.moveToNext()){    //Accede a todos los datos de la BBDD
            producto = new Producto();
            producto.setId(cursor.getString(0));
            producto.setCodigo(cursor.getString(1));
            producto.setDetalle(cursor.getString(2));
            producto.setCantidad(cursor.getString(3));
            producto.setValor(cursor.getString(4));
            producto.setCantVenta(cursor.getString(7));

            listProducto.add(producto);  //Agrega los datos en la lista productos
        }

        Toast.makeText(getApplicationContext(), "Producto: "+listProducto.get(0).getCodigo(), Toast.LENGTH_SHORT).show();*/
        selectedAdapter = new SelectedAdapter(this, elementos, new SelectedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) {
                selectedAdapter.item(item);
            }
        });

        rvLista = findViewById(R.id.rvElementosSeleccionados);  //Referencia el rv de la activity
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvLista.setLayoutManager(layoutManager);
        rvLista.setAdapter(selectedAdapter);

        /*rvLista = findViewById(R.id.recyclerProductos);
        rvLista.setHasFixedSize(true);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(selectedAdapter);*/


    }
    //==============================================================================================


    //==============================================================================================

}