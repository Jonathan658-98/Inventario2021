package com.ejemplo.inventario2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.inventario2021.actividades.EliminarProductos;
import com.ejemplo.inventario2021.actividades.ListaProductosVentaActivity;
import com.ejemplo.inventario2021.actividades.ListaVentasActivity;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity  implements View.OnClickListener {

    //atributos para el activity
    private TextView txtId;
    private EditText etCodigo;
    private EditText etCantidad;
    private EditText etDetalle;
    private EditText etValor;
    private EditText etProveedor;
    private TextView txtTotalProdVendidos;
    private TextView txtTotalDinero;


    //Atributos para las imagenes
    private ImageView agregar;
    private ImageView restar;


    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


        //Producto prod = new Producto();
        //Toast.makeText(getApplicationContext(), " Id: " + prod.getIdProducto(),Toast.LENGTH_LONG).show();


        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd productos", null,1);

        agregar = findViewById(R.id.imageAgregar);
        restar = findViewById(R.id.imageRestar);

        restar.setOnClickListener(this);    //forma 2

        /*agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "¡Agregar productos!", Toast.LENGTH_SHORT).show();
            }
        });*/

        restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "¡Restar productos con el OnCreate!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListaProductosVentaActivity.class);
                startActivity(intent);
            }
        });



        Producto elementos = (Producto) getIntent().getSerializableExtra("Producto");   //Recibe el parametro enviado desde ListaProductosRecyclerAdapter

        //Relacionar atributos con la UI
        txtId = findViewById(R.id.txtId);
        etCodigo = findViewById(R.id.et_Codigo);
        etDetalle = findViewById(R.id.et_Detalle);
        etCantidad = findViewById(R.id.et_Cantidad);
        etValor = findViewById(R.id.et_Valor);
        etProveedor = findViewById(R.id.et_Proveedor);
        txtTotalProdVendidos = findViewById(R.id.txtTotalProdVendidos);
        txtTotalDinero = findViewById(R.id.txtTotalDinero);



        //Con el objeto elementos envia los textos a los TextView
        txtId.setText(elementos.getId());
        etCodigo.setText(elementos.getCodigo());
        etDetalle.setText(elementos.getDetalle());
        etCantidad.setText(elementos.getCantidad());
        etValor.setText(elementos.getValor());
        etProveedor.setText(elementos.getProveedor());


        //Toast.makeText(this, "Código: " + elementos.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    //==============================================================================================

    @Override
    public void onClick(View v) {   //forma 2
        Toast.makeText(getApplicationContext(), "¡Restar productos con el implements!", Toast.LENGTH_SHORT).show();
    }

    //==============================================================================================




    //==============================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guardar, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }



    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Cuando un item sea oprimido
        when (item.itemId){
            R.id.favorito -> {
                favorito = !favorito //cambia de valor
                setFavoriteIcon(item)
            }*/


    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editar:
                Toast.makeText(this, "A presionado el botón para editar", Toast.LENGTH_SHORT).show();
                break;

            case R.id.eliminar:
                //Toast.makeText(this, "A presionado el botón para eliminar", Toast.LENGTH_SHORT).show();
                generarAlertDialog();
                break;

            case R.id.guardar:
                //Toast.makeText(this, "A presionado el botón para guardar", Toast.LENGTH_SHORT).show();
                actualizarProducto();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //==============================================================================================

    private void actualizarProducto() { //Método para editar y actualizar los datos

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();             //Abre la conexión con la BBDD
        String[] parametros = {txtId.getText().toString()};   //Consulta solo con un parametro

        String codigo = etCodigo.getText().toString();

        ContentValues values = new ContentValues(); //HashTable

        values.put("codigo", etCodigo.getText().toString());
        values.put("detalle", etDetalle.getText().toString());
        values.put("cantidad", etCantidad.getText().toString());
        values.put("valor", etValor.getText().toString());
        values.put("proveedor", etProveedor.getText().toString());

        db.update("productos", values, Utilidades.CAMPO_ID+"=?", parametros);
        //db.update("productos", values, null, null);     //Actualiza toda la fila
        Toast.makeText(getApplicationContext(), " Producto actualizado correctamente",Toast.LENGTH_LONG).show();
        db.close(); //cerrar la concexión

        //finish();   //finaliza el acivity
        //Regresa al menu inicial

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    //==============================================================================================




    //==============================================================================================
    private void generarAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Desea eliminar este producto")
                .setTitle("¡Atención!");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Thanks so much", Toast.LENGTH_SHORT).show();
                eliminarProducto();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"¡No dese ingresar productos!", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();  //mostrat el dialogo

    }

    //==============================================================================================

    private void eliminarProducto() {

        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {etCodigo.getText().toString()};

        db.delete(Utilidades.TABLA_PRODUCTO, Utilidades.CAMPO_CODIGO + "=?", parametros);
        Toast.makeText(getApplicationContext(), " Se eliminó el producto", Toast.LENGTH_LONG).show();

        etCodigo.setText("");
        etDetalle.setText("");
        etCantidad.setText("");
        etValor.setText("");
        etProveedor.setText("");

        //limpiar();
        db.close(); //cerrar la concexión

        //Regresa al menu inicial
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    //==============================================================================================

    public void Agregar(View view) {    //Método para enviar una alerta para la salida de productos

        //String id = txtId.getText().toString();
        String codigo = etCodigo.getText().toString();
        String detalle = etDetalle.getText().toString();
        String cantidad = etCantidad.getText().toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);        //Crea un objeto para generar el alert Dialog

        final EditText weightInput = new EditText(getApplicationContext()); //Cre un editText
        weightInput.setInputType(InputType.TYPE_CLASS_NUMBER);      //Permite ingresar datos numericos
        builder.setView(weightInput);                               //visualiza el editText

        //Editable cantIngreso = weightInput.getText();

        builder.setMessage( detalle + "\n"+ codigo +"\n" +"En Stock: "+cantidad+"\n"+"Digite la cantidad: ")      //Mensaje
                .setTitle("Productos que se van a ingresar");                   //Titulo

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"Thanks so much", Toast.LENGTH_SHORT).show();
                agregarCantidad(weightInput.getText(), codigo, cantidad);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"¡No se agregaron productos!", Toast.LENGTH_SHORT).show();
            }
        });

        /*builder.setNeutralButton("No comment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //dialog.dismiss(); no es necesario ponerlo porque ya esta implicito para cerrar el dialogo
            }
        });*/


        AlertDialog dialog = builder.create();      //Crea el
        dialog.show();  //mostrat el dialogo

    }


    //==============================================================================================

    private void agregarCantidad(Editable text, String codigo, String cantidadBd) {   //Método para ingresar la cantidad ingresada
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String[] parametros = {txtId.getText().toString()};

        String cantIngresada = text.toString();

        int valorIngresado = Integer.parseInt(cantIngresada);       //Convierte a int la cantidad ingresada
        int valorBD = Integer.parseInt(cantidadBd);                 //Convierte a int la la cantidad de la  BBDD
        int total;

        //Toast.makeText(this, "Cantidad BBDD: "+ cantidadBd, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Código: "+ codigo, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Cantidad Ingresada: "+cantIngresada, Toast.LENGTH_LONG).show();


        ContentValues values = new ContentValues(); //Crea un objeto del tipo ContentValues para interactuar con la BBDD

        if (cantIngresada.length() != 0) {
            total = valorIngresado + valorBD;

            values.put("cantidad", total);

            db.update("productos", values,"id"+"=?", parametros);
            db.close();

            Toast.makeText(getApplicationContext(),"¡Se agrego la cantidad de productos!", Toast.LENGTH_LONG).show();
            //Regresa al menu inicial
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        //onCreate(null);


        //Código para vender productos
        /*if (cantIngresada.length() != 0) {       //Cuando ingresa el texto
            ContentValues values = new ContentValues(); //Crea un objeto del tipo ContentValues para interactuar con la BBDD
            if (valorBD >= valorIngresado ){ //Si el valor en la BBDD es mayor
                total = valorBD - valorIngresado;   //resta la cantidad de productos ingresados

                values.put("cantidad", total);
                db.update("productos",values, null,null );
                db.close();
            }else{
                Toast.makeText(this, "¡Stock Insuficiente!", Toast.LENGTH_LONG).show();
            }

        }else{

        }*/


    }

    //==============================================================================================

}