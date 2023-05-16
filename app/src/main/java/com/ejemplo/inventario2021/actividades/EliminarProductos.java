package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

public class EliminarProductos extends AppCompatActivity {

    TextView etCodigo;
    TextView etDetalle;
    TextView etCandtidad;

    EditText campoCodigo, campoDetalle, campoCantidad;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_productos);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd productos", null,1);

        campoCodigo = (EditText) findViewById(R.id.etCodigo);
        campoDetalle = (EditText) findViewById(R.id.etDetalle);
        campoCantidad = (EditText) findViewById(R.id.etCantidad);


        /*
        //Refefencias atributos
        etCodigo = findViewById(R.id.etCodigo);
        etDetalle = findViewById(R.id.etDetalle);
        etCandtidad = findViewById(R.id.etCantidad);


        Producto elementos = (Producto) getIntent().getSerializableExtra("Producto");

        //Accede a los datos y retorna
        etCodigo.setText(elementos.getCodigo());
        etDetalle.setText(elementos.getDetalle());
        etCandtidad.setText(elementos.getCantidad());*/



    }

    //==============================================================================================

    public void onClick(View view ){    //Método que dispara eventos al presionar en los botones
        switch (view.getId()) {
            case R.id.btnConsultar:
                consultar();
                //consultarSql();
                break;
            /*case R.id.btnActualizar:
                //actualizarProducto();
                break;
            //case R.id.btnEliminar:
                generarAlertDialog();
                //eliminarUsuario();
                break;*/
        }
    }

    private void actualizarProducto() { //Método para editar y actualizar los datos
        SQLiteDatabase db = conn.getWritableDatabase(); //Abre la BBDD Para escribir en la base de datos
        String[] parametros = {campoCodigo.getText().toString()};   //Consulta solo con un parametro

        ContentValues values = new ContentValues(); //HashTable
        values.put("codigo", campoCodigo.getText().toString());
        values.put("detalle", campoDetalle.getText().toString());
        values.put("cantidad", campoCantidad.getText().toString());

        db.update("productos", values, Utilidades.CAMPO_CODIGO+"=?", parametros);
        Toast.makeText(getApplicationContext(), " Ya se actualizó el usuario",Toast.LENGTH_LONG).show();
        db.close(); //cerrar la concexión

    }

    //==============================================================================================

    private void generarAlertDialog() { //Métoodo que genera la Alerta antes de eliminar el producto
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

    private void eliminarProducto() {       //Método para eliminar el producto mediant el alert Dialog


        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {campoCodigo.getText().toString()};   //Consulta solo con un parametro

        db.delete(Utilidades.TABLA_PRODUCTO, Utilidades.CAMPO_CODIGO + "=?", parametros);
        Toast.makeText(getApplicationContext(), " Se eliminó el producto", Toast.LENGTH_LONG).show();
        campoCodigo.setText("");
        limpiar();
        db.close(); //cerrar la concexión

    }

    //==============================================================================================

    private void consultar() {  //Método que permite realizar una consulta para verificar los datos
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {campoCodigo.getText().toString()};
        String[] campos = {Utilidades.CAMPO_DETALLE, Utilidades.CAMPO_CANTIDAD};

        try{
            //(nombre de la tabla, enviar campos, enviar el campo de consulta, se remplace por el parametro)
            // null corresponden a datos String asociados a groupBy, Having y OrderBy  //Estructura para enviar información a la BBDD
            Cursor cursor = db.query(Utilidades.TABLA_PRODUCTO, campos,Utilidades.CAMPO_CODIGO + "=?", parametros, null, null, null);
            cursor.moveToFirst();       //Indicar que separa el primer registro que devuelve
            campoDetalle.setText(cursor.getString(0));   //Posicion 0 para el nombre
            campoCantidad.setText(cursor.getString(1)); //Posicion 1 para el telefono
            cursor.close(); //cierra la consulta

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "El documento no existe", Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    //==============================================================================================

    private void limpiar() {    //limpir el formulario
        campoDetalle.setText("");
        campoCantidad.setText("");
    }

    //==============================================================================================
}