package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class DescriptionFacturasActivity extends AppCompatActivity {

    private TextView Id_factura, cliente, fecha, total, totalProductos;
    private DataTable dataTable;
    private List<Producto> items = new ArrayList<>();   //ArrayLista para los lementos seleccionados
    private String id_factura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_facturas);

        dataTable = findViewById(R.id.data_table_factura);  //Referencia la tabla de la UI


        Producto elementos = (Producto) getIntent().getSerializableExtra("Producto");
        //items = getIntent().getParcelableArrayListExtra("Facturas");



        //Relacionar atributos con la UI
        Id_factura = findViewById(R.id.tvIdFactura);
        cliente = findViewById(R.id.tvCliente);
        fecha = findViewById(R.id.tvFecha);
        total = findViewById(R.id.tvTotal);
        totalProductos = findViewById(R.id.tvTotalProductos);

        //Con el objeto elementos envia los textos a los TextView
        Id_factura.setText(elementos.getId());
        cliente.setText(elementos.getProveedor());
        fecha.setText(elementos.getFecha());
        total.setText("$" +String.valueOf(elementos.getTotal()));
        id_factura = elementos.getId();


        //llenarTabla(elementos);
        llenarTablaDetalle(id_factura);
        totalProductos.setText(String.valueOf(items.size()));


    }

    private void llenarTablaDetalle(String id_factura) {

        ConexionSQLiteHelper admin = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();             //Abre la BBDD en modo lectura y escritura



        DataTableHeader header = new DataTableHeader.Builder()
                .item("Codigo", 10)
                .item("Descripcion ", 10)
                .item("Cantidad", 10)
                .item("Precio", 10)
                .item("Total", 10)
                .build();

        Producto producto = null;     //Para llenar la informacion
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_DETALLE, null);
        while (cursor.moveToNext()){
            if(id_factura.equals(cursor.getString(1))){
                producto = new Producto();
                producto.setCodigoVenta(cursor.getString(2));
                producto.setDetalleVenta(cursor.getString(3));
                producto.setCantidadVenta(cursor.getString(4));
                producto.setPrecioVenta(cursor.getString(5));
                producto.setTotalItem(cursor.getString(6));
                items.add(producto);
            }
        }

        /*Toast.makeText(this, "Número de factura: " +id_factura +"\n" +
                "Total Productos Facturados: " + items.size(), Toast.LENGTH_SHORT).show();*/

        for(int i = 0; i < items.size(); i++){
            //Toast.makeText(getApplicationContext(), items.get(i).getTotalItem(), Toast.LENGTH_SHORT).show();
        }

        ArrayList<DataTableRow> rows = new ArrayList<>();
        for(int i = 0; i < items.size(); i++){
            DataTableRow row = new DataTableRow.Builder()
                    .value(items.get(i).getCodigoVenta())
                    .value(items.get(i).getDetalleVenta())
                    .value(items.get(i).getCantidadVenta())
                    .value(items.get(i).getPrecioVenta())
                    .value(items.get(i).getTotalItem())
                    .build();
            rows.add(row); //Guarda los datos en el ArrayList
        }


        dataTable.setCornerRadius(30);
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(this);

    }

    //==============================================================================================


    private void llenarTabla(Producto elementos) {

        /*for(int i = 0; i < items.size(); i++){
            Toast.makeText(getApplicationContext(), items.get(i).getProveedor(), Toast.LENGTH_SHORT).show();
        }*/

        /*DataTableHeader header = new DataTableHeader.Builder()
                .item("Item", 10)
                .item("Codigo", 10)
                .item("Descripcion ", 10)
                .item("Cantidad", 10)
                .item("Precio", 10)
                .item("Total", 10)
                .build();


        double total = 0;
        double totalFactura = 0;
        ArrayList<DataTableRow> rows = new ArrayList<>();
        for(int i = 0; i < items.size(); i++){
            total = Double.parseDouble(String.valueOf(items.get(i).getSumar()))  * Double.parseDouble(items.get(i).getValor());
            totalFactura += total;
            DataTableRow row = new DataTableRow.Builder()
                    .value(String.valueOf(i+1))
                    .value(elementos.getId())
                    .value(items.get(i).getDetalle())
                    .value(String.valueOf(items.get(i).getSumar()))
                    .value(items.get(i).getValor())
                    .value(items.get(i).getTotalItem())  //Total de cada item
                    .build();
            rows.add(row); //Guarda los datos en el ArrayList
            //total = 0;
        }



        dataTable.setCornerRadius(30);
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(this);*/
    }
}