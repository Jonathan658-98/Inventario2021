package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.inventario2021.MainActivity;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;


public class ActivityFactura extends AppCompatActivity {
    private List<Producto> items = new ArrayList<>();   //ArrayLista para los lementos seleccionados
    private DataTable dataTable;
    private ConexionSQLiteHelper conn;  //Para conectar con la BBDD
    private SQLiteDatabase sqLiteDatabase;
    private Button btnGuardar;
    private Button btnCancelar;
    private TextView tvFecha;
    private TextView tvNumFactura;
    private  int dia = 0, mes = 0, anio = 0;    //Atributos para la fecha
    private double valorFactura ;
    private  Boolean band = true;

    private EditText etNombreCliente, etDireccion;
    
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        dataTable = findViewById(R.id.data_table);  //Referencia la tabla de la UI
        items = getIntent().getParcelableArrayListExtra("Elementos");   //Recibe el ArrayList con los elementos seleccionados

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        tvFecha = findViewById(R.id.tvFecha);
        //tvNumFactura = findViewById(R.id.tvNumFactura);


        //Datos del cliente
        etNombreCliente = findViewById(R.id.etNomCliente);
        etDireccion = findViewById(R.id.etDireccion);


        //Toast.makeText(getApplicationContext(), "Cantidad: " + items.get(0).getSumar(), Toast.LENGTH_SHORT).show();
        mostrarFecha();
        crearTabla();
        //consultarDatosCliente();
        //consultarDatosFactura();
        //consultarTablaDetalle();

        btnGuardar.setOnClickListener(new View.OnClickListener() {  //Evento para confirmar la transacción
            @Override
            public void onClick(View view) {
               generarAlertDialog();
            }


        });//Fin del evento Click del btnGuardar

    }// Fin del metodo onCreate

    //==============================================================================================
    private void limpiarTabla() {
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Item", 10)
                .item("Codigo", 10)
                .item("Descripcion ", 10)
                .item("Cantidad", 10)
                .item("Precio", 10)
                .item("Total", 10)
                .build();

        ArrayList<DataTableRow> rows = new ArrayList<>();   //Crea un arrayList del tipo DataTableRow

        for(int i = 0; i <= items.size(); i++){
            DataTableRow row = new DataTableRow.Builder()
                    .value("")
                    .value("")
                    .value("")
                    .value("")
                    .value("")
                    .value("")
                    .build();
            rows.add(row);  //Agrega los datos a una fila nueva

        }
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(getApplicationContext());
    }

    //==============================================================================================

    private String mostrarFecha() {
        Calendar fecha = Calendar.getInstance();
        String fechaDos;
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        anio = fecha.get(Calendar.YEAR);
        tvFecha.setText(dia+"/"+(mes+1)+"/"+anio);

        fechaDos = String.valueOf(dia) + "/"+ String.valueOf(mes+1) + "/"+ String.valueOf(anio);

        return fechaDos;
    }

    //==============================================================================================
    private void crearTabla() {
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Item", 10)
                .item("Codigo", 10)
                .item("Descripcion ", 10)
                .item("Cantidad", 10)
                .item("Precio", 10)
                .item("Total", 10)
                .build();

        DecimalFormat formato1 = new DecimalFormat("#.00"); //Restringir a dos decimales

        double total = 0;
        double totalFactura = 0;
        ArrayList<DataTableRow> rows = new ArrayList<>();
        for(int i = 0; i < items.size(); i++){
            total = Double.parseDouble(String.valueOf(items.get(i).getSumar()))  * Double.parseDouble(items.get(i).getValor());
            totalFactura += total;
            valorFactura = totalFactura;
            DataTableRow row = new DataTableRow.Builder()
                    .value(String.valueOf(i+1))
                    .value(items.get(i).getCodigo())
                    .value(items.get(i).getDetalle())
                    .value(String.valueOf(items.get(i).getSumar()))
                    .value(items.get(i).getValor())
                    .value(String.valueOf(formato1.format(total)))  //Total de cada item
                    .build();
            rows.add(row); //Guarda los datos en el ArrayList
            //total = 0;
        }


        DataTableRow row = new DataTableRow.Builder()   //Añade una fila par ael total
                .value("")
                .value("")
                .value("")
                .value("")
                .value("TOTAL")
                .value(String.valueOf(formato1.format(totalFactura)))   //Total de la factura
                .build();
        rows.add(row);  //Agrega los datos a una fila nueva

        dataTable.setCornerRadius(30);
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(this);
    }  //Fin del método crearTabla

    //==============================================================================================
    private void generarAlertDialog() { //Método para generar un cuadro de dialogo y confirmar la transacción
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Desea continuar con la transacción!")
         .setTitle("¡Facturación!");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Toast.makeText(getApplicationContext(),"Thanks so much", Toast.LENGTH_SHORT).show();
                if(etNombreCliente.getText().toString().equals("") || etDireccion.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe llenar todos los campos!!!", Toast.LENGTH_SHORT).show();
                }else{
                    llenarTablaClientes();
                }

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

    }   //Fin del método generarAlertDialog
    //==============================================================================================
    private Boolean consultarStockInsuficiente(){
        int cantidadVenta = 0;
        int cantidadBb = 0;
        Boolean bandera = true;

        for(int i = 0; i < items.size(); i++){
            cantidadVenta = items.get(i).getSumar();
            cantidadBb = devolverStock(items.get(i).getCodigo());
            if(cantidadBb < cantidadVenta){ //Si la cantidad de algún producto es menor a la cantidad a vender
                bandera = false;       //El valor de la bandera cambia
                break;              //rompe el ciclo
            }else{

            }
        }

        return bandera;
    }

    //==============================================================================================
    private void descontarProductos(){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura
        int cantidadVenta = 0;
        int cantidadBb = 0;
        int total = 0;
        band = consultarStockInsuficiente();

        for(int i = 0; i < items.size(); i++){  //Ciclo para recorrer el ArrayList con los productos a facturar
            cantidadVenta = items.get(i).getSumar();
            //Toast.makeText(getApplicationContext(),"Cantidad :" + cantidadVenta, Toast.LENGTH_SHORT).show();
            cantidadBb = devolverStock(items.get(i).getCodigo());   //stock del producto
            String[] parametros = {items.get(i).getCodigo()};       //Almacena el codigo del producto
            ContentValues values = new ContentValues();              //Crea un objeto del tipo ContentValues para interactuar con la BBDD
            try{
                if(band == true){
                    if(cantidadBb >= cantidadVenta ){                       //Si el stock es mayor que la cantidad a vender
                        total = cantidadBb - cantidadVenta;                 //resta la cantidad de productos ingresados
                        //Actualiza la BBDD con los nuevos datos ingresados
                        values.put("cantidad", total);
                        db.update("productos", values,Utilidades.CAMPO_CODIGO+"=?", parametros);
                    }
                }else{
                    //Toast.makeText(getApplicationContext(),"Existen productos con cantidades superiores al stock:", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.toString() + cantidadVenta +"\n"+ "Cantidad Bd :" + cantidadBb, Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(getApplicationContext(),"Cantidad venta :" + cantidadVenta +"\n"+ "Cantidad Bd :" + cantidadBb, Toast.LENGTH_SHORT).show();
        }

        if(band == true){

            llenarTablaFactura(etNombreCliente.getText().toString());
            llenarTablaDetalle();
            limpiarTabla();
            Toast.makeText(getApplicationContext(),"Factura generada con éxito!", Toast.LENGTH_SHORT).show();
            //finish();
            //setResult(ActivityFactura.RESULT_OK);
            //cambiarActividad();
        }else{
            Toast.makeText(getApplicationContext(),"Existen productos con cantidades superiores al stock:", Toast.LENGTH_SHORT).show();
        }


        db.close(); //Cierra la base de datos
    }

    //==============================================================================================
    private int devolverStock(String codigoProducto){    //Método que accede a la BD y devuelve el stock de un producto
        int cantidad = 0;
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura

        //Realiza una consulta en la BBDD
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PRODUCTO, null);
        while (cursor.moveToNext()) {     //Devuelve los registros
            if(codigoProducto.equals(cursor.getString(1))){ //Si el codigo del producto es igual
                cantidad = Integer.parseInt(cursor.getString(3));   //Cantidad existente del prodcuto
                break;  //rompe el ciclo
                //Toast.makeText(getApplicationContext(),"Cantidad BD:" + cantidadBd, Toast.LENGTH_SHORT).show();
            }
        }
        db.close(); //Cierra la conexión con la BBDD

        return cantidad;    //Devuelve la cantidad del producto
    }
    //==============================================================================================
    private void cambiarActividad(){
        Intent intent = new Intent(this, ListaProductosVentaActivity.class);
        startActivity(intent);
    }

    //==============================================================================================
    private void llenarTablaClientes(){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura
        String nombre = etNombreCliente.getText().toString();
        String direccion = etDireccion.getText().toString();

        ContentValues values = new ContentValues();     //Permite realizar el registro

        band = consultarStockInsuficiente();        //Si retorna true puede el stock es suficiente

        if((!nombre.isEmpty() && !direccion.isEmpty())){
            if(band== true){
                values.put("nombre", nombre);
                values.put("direccion", direccion);
                db.insert("clientes", null, values);
                db.close(); //Cierra la conexión con la BBDD
                descontarProductos();   //Llama al método para desocntar la cantidad de productos vendidos
            }else{
                Toast.makeText(this, "No se puede facturar, Existen productos con cantidades superiores al stock:!!!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Debe llenar todos los campos!!!", Toast.LENGTH_SHORT).show();
        }

        /*if(band == false){
            Toast.makeText(this, "No se puede facturar, Existen productos con cantidades superiores al stock:!!!", Toast.LENGTH_SHORT).show();
        }*/

        //db.execSQL("INSERT INTO clientes (nombre, direccion) VALUES(nombre, direccion)");


    }
    //==============================================================================================

    private void consultarDatosCliente(){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CLIENTES, null);
        while (cursor.moveToNext()) {     //Devuelve los registros
            Toast.makeText(this, "Id: " + cursor.getString(0) +"\n"+"Nombre: "
                    + cursor.getString(1) +"\n"+"Dirección: " + cursor.getString(2), Toast.LENGTH_SHORT).show();
        }
        db.close(); //Cierra la conexión con la BBDD
    }
    //==============================================================================================
    private void llenarTablaFactura(String nombre){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura

        String fecha = mostrarFecha();


        ContentValues values = new ContentValues();     //Permite realizar el registro
        values.put("nombreCliente", nombre);
        values.put("fecha", fecha);
        values.put("total", String.valueOf(valorFactura));
        db.insert("factura", null, values);
        db.close();

        try{
            consultarDatosFactura();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //==============================================================================================
    private int consultarDatosFactura(){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura
        String nombreCliente = etNombreCliente.getText().toString();

        int id_factura = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_FACTURAS, null);

        while (cursor.moveToNext()) {     //Devuelve los registros
            /*Toast.makeText(this, "Id_factura: " + cursor.getString(0) +"\n"+"NombreCliente :"
                    + cursor.getString(1) +"\n"+"Fecha: " + cursor.getString(2)
                    +"\n"+"TotalFactura: " + cursor.getString(3), Toast.LENGTH_SHORT).show();*/
            if(nombreCliente.equals(cursor.getString(1))){  //Si el nombre del Cliente esta en la tabla factura
                id_factura = Integer.parseInt(cursor.getString(0)); //Captura el Id de la factura que corresponde a ese cliente
                break;
            }

        }
        db.close(); //Cierra la conexión con la BBDD
        return id_factura;
    }
    //==============================================================================================
    public void llenarTablaDetalle(){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura
        ContentValues values = new ContentValues();     //Permite realizar el registro
        //ContentValues values2 = new ContentValues();

        String[] parametros = {String.valueOf(consultarDatosFactura())};

        int idFactura = consultarDatosFactura();    //Almacena el id de la factura del cliente
        double total = 0;
        double totalFactura = 0;

        DecimalFormat formato1 = new DecimalFormat("#.00"); //Restringir a dos decimales

        for(int i = 0; i < items.size(); i++){  //Ciclo para recorrer el ArrayList
            total = Double.parseDouble(String.valueOf(items.get(i).getSumar()))  * Double.parseDouble(items.get(i).getValor());
            totalFactura += total;
            values.put("id_factura", idFactura);
            values.put("codigo", items.get(i).getCodigo());
            values.put("descripcion", items.get(i).getDetalle());
            values.put("cantidad",String.valueOf(items.get(i).getSumar()));
            values.put("precio", items.get(i).getValor());
            values.put("total", String.valueOf(formato1.format(total)));
            db.insert("detalle", null, values);

        }

        /*
         db.update("productos", values, Utilidades.CAMPO_ID+"=?", parametros);

        Toast.makeText(getApplicationContext(), " Producto actualizado correctamente",Toast.LENGTH_LONG).show();
        db.close(); //cerrar la concexión
         */
        /*values2.put("total", totalFactura);
        db.update("factura", values2, Utilidades.CAMPO_ID_FACTURA+"=?", parametros);    //Actualiza la tabla factura con el campo total*/
        db.close(); //Cierra la conexión con la BBDD

        llenarTablaTotalFactura(idFactura, String.valueOf(totalFactura));   //Llamamos al metodo para llenar el total de la factura

    }

    //==============================================================================================
    private void llenarTablaTotalFactura(int id, String total){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura
        ContentValues values = new ContentValues();     //Permite realizar el registro

        values.put("id_factura", id);
        values.put("total", total);
        db.insert("totalFactura", null, values);
        db.close();

        finish();
        cambiarActividad();
    }
    //==============================================================================================

    private void consultarTablaDetalle(){
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura


        int id_factura = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_DETALLE, null);
        while (cursor.moveToNext()) {     //Devuelve los registros
            Toast.makeText(this, "Id_detalle: " + cursor.getString(0) +"\n"+"id_factura :"
                    + cursor.getString(1) +"\n"+"Código: " + cursor.getString(2) +"\n"+
                    "Descripción :" + cursor.getString(3) +"\n" +
                    "cantidad :" + cursor.getString(4) +"\n" +
                    "Precio :" + cursor.getString(5) +"\n"+
                    "Total:" + cursor.getString(6) +"\n", Toast.LENGTH_LONG).show();

        }
        db.close(); //Cierra la conexión con la BBDD


    }
    //==============================================================================================









}   //Fin de la clase ActivityFactura