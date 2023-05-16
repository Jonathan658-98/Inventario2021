package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.ejemplo.inventario2021.DescriptionActivity;
import com.ejemplo.inventario2021.ListaProductosAdapter;
import com.ejemplo.inventario2021.MainActivity;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.interfaces.ShowListener;
import com.ejemplo.inventario2021.producto.Producto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaProductosVentaActivity extends AppCompatActivity implements ShowListener {


    private List<Producto> listProducto;
    private RecyclerView rvLista;
    private CharSequence search = "";
    private EditText searchView;
    private RecyclerAdapter adapter;
    private Button buttonAddToWatchList;
    ArrayList<Producto> selectedElements = new ArrayList<>();

    ConexionSQLiteHelper conn;  //Para conectar con la BBDD
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_venta);


        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        buttonAddToWatchList = findViewById(R.id.buttonAddToWatchList);
        searchView = findViewById(R.id.search_bar);

        rvLista = findViewById(R.id.recyclerVenta);


        //recyclerViewProductos = (RecyclerView) findViewById(R.id.recyclerProductos);
        //recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        consultarListaProductos();  //Llama al método
        //registrarVenta();
    }
    //==============================================================================================

    //Método par consultar los productos de la BBDD
    private void consultarListaProductos() {

        //List<Producto> listProducto = new ArrayList<>();
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
            //producto.setCantVenta(cursor.getString(7));

            listProducto.add(producto);  //Agrega los datos en la lista productos
        }

        //Toast.makeText(this, "Total productos: " + listProducto.size(), Toast.LENGTH_LONG).show();

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
        /*adapter = new RecyclerAdapter(this, listProducto, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto item) { moveToDescription(item);
            }
        });*/

        adapter = new RecyclerAdapter(this, listProducto, this);

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, listProducto, this);
        rvLista.setAdapter(recyclerAdapter);

        buttonAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  List<Producto> selectedElements =  recyclerAdapter.getSelectedElements();
                //selectedElements = (ArrayList<Producto>) recyclerAdapter.getSelectedElements();
                StringBuilder showNames = new StringBuilder();
                for(int i = 0; i < selectedElements.size(); i++){
                    if(i == 0){
                        showNames.append(selectedElements.get(i).getDetalle());
                    }else{
                        showNames.append("\n").append(selectedElements.get(i).getDetalle());
                    }
                }
                //Toast.makeText(ListaProductosVentaActivity.this, showNames.toString(), Toast.LENGTH_SHORT).show();
                navegarActivity((ArrayList<Producto>) selectedElements);  //Iniciar la actividad y envia el array de elementos seleccionados

            }
        });



        /*RecyclerView recyclerView = findViewById(R.id.recyclerVenta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);*/

        rvLista = findViewById(R.id.recyclerVenta);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvLista.setLayoutManager(layoutManager);
        rvLista.setAdapter(adapter);


        /*SelectedAdapter selectedAdapter = new SelectedAdapter(this, selectedElements);

        RecyclerView elementosSeleccionados = findViewById(R.id.rvElementosSeleccionados);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        elementosSeleccionados.setLayoutManager(layoutManager2);
        elementosSeleccionados.setAdapter(selectedAdapter);*/
    }

    //==============================================================================================

    private void navegarActivity(ArrayList<Producto> elementos){

        /*Bundle enviarDatos = new Bundle();
        String [] datos = new String[]{"Daniel", "Tene", "24"};
        enviarDatos.putStringArray("keyDatos", datos);*/

        /*ItemsSeleccionadosActivity objDatos = new ItemsSeleccionadosActivity();
        objDatos.recibeElementos(elementos);*/

        /*Intent intent = new Intent(getApplicationContext(), ItemsSeleccionadosActivity.class);
        intent.putExtras(enviarDatos); //Envia parametros
        startActivity(intent);*/

        /*ArrayList<String> datos = new ArrayList<>();
        datos.add("Daniel");*/

        Intent intent = new Intent(getApplicationContext(), ItemsSeleccionadosActivity.class);
        //intent.putStringArrayListExtra("Lista", datos); //Envia parametros
        intent.putParcelableArrayListExtra("Elementos", (ArrayList<? extends Parcelable>) elementos);
        startActivity(intent);
    }

    //==============================================================================================

    //Método para cambiar de activity
    private void moveToDescription(Producto item) {
            /*Toast.makeText(this, "Código : " + item.getCodigo() + "\n"
                    +"Detalle: "+ item.getDetalle() + "\n"+"Cantidad: "+  item.getCantidad(), Toast.LENGTH_LONG).show();*/
        ventaProductos(item);

    }


    //==============================================================================================

    public void ventaProductos(Producto item) {    //Método para enviar una alerta para la salida de productos
        //Captura los datos del item producto
        String codigo = item.getCodigo();
        String detalle = item.getDetalle();
        String cantidad = item.getCantidad();
        String precio = item.getValor();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);        //Crea un objeto para generar el alert Dialog

        //Creo El EditText para capturar el dato ingresado por el usr
        final EditText weightInput = new EditText(getApplicationContext());         //Cre un editText
        weightInput.setInputType(InputType.TYPE_CLASS_NUMBER);                      //Permite ingresar datos numericos
        builder.setView(weightInput);                                               //visualiza el editText

        //cosntruye alertDialog
        builder.setMessage( codigo + "\n"+ detalle +"\n" +"En Stock: "+cantidad +"\n"+"Digite la cantidad: ")      //Mensaje
                .setTitle("Producto que se va a facturar");                   //Titulo

        //Button para confirmar el proceso
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"Thanks so much", Toast.LENGTH_SHORT).show();
                descontarCantidad(weightInput.getText(), codigo, cantidad, detalle, precio);    //llama al método y envía los parámetros necesarios
            }
        });

        //Button para cancelar el proceso
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


        AlertDialog dialog = builder.create();      //Crea la interfaz del AlertDialog
        dialog.show();                              //mostrar el dialogo

    }

    //==============================================================================================

    private void descontarCantidad(Editable text, String codigo, String cantidadBd, String detalle, String precio) {   //Método para ingresar la cantidad ingresada
        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura

        String[] parametros = {codigo};                             //captura el codigo del producto
        String cantIngresada = text.toString();                     //Convierte a un string el dato ingresado por el usr
        int valorIngresado = Integer.parseInt(cantIngresada);       //Convierte a int la cantidad ingresada
        int valorBD = Integer.parseInt(cantidadBd);                 //Convierte a int la la cantidad de la  BBDD
        int total;
        String totalCantVendida;

        //Código para vender productos
        if (cantIngresada.length() != 0 ) {                           //Cuando ingresa el texto
            ContentValues values = new ContentValues();              //Crea un objeto del tipo ContentValues para interactuar con la BBDD
            if (valorBD >= valorIngresado ){                         //Si el valor en la BBDD es mayor
                total = valorBD - valorIngresado;                    //resta la cantidad de productos ingresados

                //Actualiza la BBDD con los nuevos datos ingresados
                values.put("cantidad", total);
                db.update("productos", values,Utilidades.CAMPO_CODIGO+"=?", parametros);
                db.close();

                registrarVenta(text, codigo, detalle, precio);      //llama al método para registrar la venta en la tabla ventas

                Toast.makeText(getApplicationContext(),"¡Se agrego la cantidad de productos!", Toast.LENGTH_LONG).show();
                //Regresa al menu inicial
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "¡Stock Insuficiente!", Toast.LENGTH_LONG).show();
            }
        }else{


        }
    }
    //==============================================================================================
    private void registrarVenta(Editable text, String codigo, String detalle, String precio) {    //Método que permite registras los productos vendidos
        String cantIngresada = text.toString(); //Valor de la venta ingresada por el usuario
        String fecha = fechaVenta();            //Captura la fecha actual
        String[] parametros = {codigo};         //Captura el codigo del producto
        int totalProdVendidos;                  //Para el total de productos vendidos
        String totalProd;
        String id = null;
        boolean band, cod, fech;
        band = false;
        cod = false;
        fech = false;

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this,"bd productos", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();            //Abre la conexion con la BDD



        //Realiza una consulta en la BBDD en la tabla ventas
        Cursor cursor = db.rawQuery(" SELECT * FROM " + Utilidades.TABLA_VENTAS, null);
        /*Producto producto = null;
        List<Producto> listProducto = new ArrayList<>();
        while(cursor.moveToNext()) {
            producto = new Producto();
            producto.setIdVenta(cursor.getString(0));
            producto.setCodigoVenta(cursor.getString(1));
            producto.setDetalleVenta(cursor.getString(2));
            producto.setCantidadVenta(cursor.getString(3));
            producto.setFechaVenta(cursor.getString(4));
            listProducto.add(producto);  //Agrega los datos en la lista productos
        }

        for(int i = 0; i < listProducto.size(); i++)
            Toast.makeText(this, "Producto: " + listProducto.get(i).getIdVenta(), Toast.LENGTH_SHORT).show();*/

        while(cursor.moveToNext()) {         //recorre cada fila de la tabla
            if(codigo.equals(cursor.getString(1))) //Veirifica si el código del producto a facturar ya existe en la BBDD
                cod = true;
            else
                cod = false;

            if(fecha.equals(cursor.getString(5)) && cod == true) {   //Si ya existe un producto con las misma fecha y el mismo código
               id = cursor.getString(0);
                band = true;
                break;
            }
        }

        Toast.makeText(this, "Bandera = : " + band, Toast.LENGTH_SHORT).show();

        ContentValues values = new ContentValues();                                                 //Crea un objeto del tipo ContentValues para ingresar datos en la BBDD
        if(band == true) {
            //while(cursor.moveToNext()){
                if (id.equals(cursor.getString(0))) {                                                   //Si los codigos son iguales
                    Toast.makeText(this, "ID Producto: " + id, Toast.LENGTH_SHORT).show();
                    totalProdVendidos = Integer.parseInt(cursor.getString(3)) + Integer.parseInt(cantIngresada);    //Suma las cantidades vendidas totales
                    totalProd = String.valueOf(totalProdVendidos);
                    String[] par = {id};
                    values.put("cantidadVenta", totalProd);                                                             //El valor total se guarda en la columna cantidadVenta
                    db.update("ventas", values, Utilidades.CAMPO_ID + "=?", par);    //Actualia la fila
                    db.close();                                                                         //Cierra la base de datos

            }
        }
        if(band == false){                                                                                      //Ingresa productos no existentes
            values.put("codigoVenta", codigo);
            values.put("detalleVenta", detalle);
            values.put("cantidadVenta", cantIngresada);
            values.put("precioVenta", precio);
            values.put("fechaVenta", fecha);
            db.insert("ventas", null, values);                                  //Inserta datos en la tabla ventas
            db.close();                                                                             //Cierra la base de datos
        }

        /*String [] par16 = {"16"};
        db.delete("ventas", Utilidades.CAMPO_ID + "=?", par16);
        String [] par24 = {"24"};
        db.delete("ventas", Utilidades.CAMPO_ID + "=?", par24);
        String [] parb = {"25"};
        db.delete("ventas", Utilidades.CAMPO_ID + "=?", parb);
        String [] par0 = {"26"};
        db.delete("ventas", Utilidades.CAMPO_ID + "=?", par0);
        String [] par = {"5"};
        db.delete("ventas", Utilidades.CAMPO_ID + "=?", par);*/

    }
    //==============================================================================================

    public String fechaVenta(){                                    //Método que toma la fecha actual
        int dia = 0, mes = 0, anio = 0;                            //Atributos para la fecha
        String fechaVenta;                                         //Para la fecha
        Calendar fecha = Calendar.getInstance();                   //Crea un objeto del tipo Calendar
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        anio = fecha.get(Calendar.YEAR);
        fechaVenta = String.valueOf(dia) +"/"+ (String.valueOf(mes+1) ) +"/" +String.valueOf(anio); //General la fecha actual
        return fechaVenta;                                         //devuelve la fehca
    }

    //==============================================================================================

    public void compararFechas(){                                   //Método que permite comparar fechas
        String fechaActual = fechaVenta();                          //Toma la fecha actual
        String fechaBD = "";                                             //Toma la fecha del producto existente en la BDD

        if(fechaBD.equals(fechaActual)){

        }
    }

    //==============================================================================================


    //==============================================================================================

    @Override
    public void onShowAction(Boolean isSelected) {
        if(isSelected){
            //Toast.makeText(this, "ha seleccionado un item", Toast.LENGTH_SHORT).show();
            buttonAddToWatchList.setVisibility(View.VISIBLE);
        }else{
            buttonAddToWatchList.setVisibility(View.GONE);
        }
    }
    //==============================================================================================

}