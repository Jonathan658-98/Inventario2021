package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;

import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;

public class ActivityIngresarProductos extends AppCompatActivity {

    ArrayList<Producto> listaProducto;  //Tipo producto para los datos

    private EditText campoCodigo, campoDetalle, campoCantidad, campoValor, campoProveedor;
    private Button btnRegistrar;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_productos);

        //Referencias atributos y capturar los datos ingresados
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        campoCodigo = (EditText) findViewById(R.id.etCodigo);
        campoDetalle = (EditText) findViewById(R.id.etDetalle);
        campoCantidad = (EditText) findViewById(R.id.etCantidad);
        campoValor = (EditText) findViewById(R.id.etValor);
        campoProveedor = (EditText) findViewById(R.id.etProveedor);

    }
    //==============================================================================================

    public void onClick(View view){
        registrarProductos();    //llama al metodo que permite regitrar los productos
    }

    //==============================================================================================

    private void registrarProductos() { //Método que permite registrar los productos
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();             //Abre la conexión con la BBDD

        //Capturar datos ingresados
        String codigo = campoCodigo.getText().toString();
        String detalle = campoDetalle.getText().toString();
        String cantidad = campoCantidad.getText().toString();
        String valor = campoValor.getText().toString();
        String proveedor = campoProveedor.getText().toString();

        boolean band = false;
        //==============================================================================================

        //Realiza una consulta en la BBDD
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PRODUCTO, null);
        while (cursor.moveToNext()) {     //Devuelve los registros
            //Verificación de códigos ya existentes
            if (codigo.equals(cursor.getString(1))) {    //Compara los codigo ingresados
                band = true;    //si el código ingresado ya existe cambia la bandera
                break;
            }
        }

        //==============================================================================================

        //Valida campos del producto
        if (band == false) {
            if (!codigo.isEmpty() && !detalle.isEmpty() && !cantidad.isEmpty() && !valor.isEmpty() && !proveedor.isEmpty()) {   //Validación de campos
                ContentValues values = new ContentValues();     //Permite realizar el registro
                values.put("codigo", campoCodigo.getText().toString());
                values.put("detalle", campoDetalle.getText().toString());
                values.put("cantidad", campoCantidad.getText().toString());
                values.put("valor", campoValor.getText().toString());
                values.put("proveedor", campoProveedor.getText().toString());

                //Insertar los datos en la BBDD
                Long codigoResultante = db.insert("productos", null, values);
                //Toast.makeText(getApplicationContext(), "Id Registro: " + codigoResultante, Toast.LENGTH_SHORT).show();
                db.close(); //Cierra la conexión con la BBDD

                Toast.makeText(this, "Registro Exitoso!!!", Toast.LENGTH_SHORT).show();
                campoCodigo.setText("");
                campoDetalle.setText("");
                campoCantidad.setText("");
                campoValor.setText("");
                campoProveedor.setText("");
            } else {
                Toast.makeText(this, "Debe llenar todos los campos!!!", Toast.LENGTH_SHORT).show();
                //insertarDatos();
            }
        } else {
            Toast.makeText(this, "Ya existe un producto con este código!!! ", Toast.LENGTH_SHORT).show();
        }
    }
    //==============================================================================================
    private void insertarDatos(){
        try {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();             //Abre la conexión con la BBDD
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('PER','Perno','120','2.50','Mtx')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('ALC','Alicate','250','5.75','Cabero')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('PAR12','Parche 12cm','32','7.5','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('TRC11','Tuerca 11mm','523','5.7','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('PLY','Playo Importado','100','8.75','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('LLNT12','Llanta R12','24','50.25','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('LLNT13','Llanta R13','30','52.75','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('LLNT14','Llanta R14','28','58.9','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('LLNT15','Llanta R15','32','65.5','Mtx')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('MNG','Maguera Importada','50','7.50','Mtx')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('PAR14','Parche 14cm','24','8.5','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('PAR15','Parche 15cm','28','9.5','Vipal')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('ESP','Espejo Importado','50','8.95','Mtx')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('DTPL12','Destornillador Plano','110','1.95','Cabero')");
            db.execSQL("INSERT INTO productos (codigo, detalle, cantidad, valor,proveedor) VALUES('DTEST3','Destornillador Estrella','250','2.25','Cabero')");
            db.close(); //Cierra la conexión con la BBDD
        }catch (Exception e){

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}