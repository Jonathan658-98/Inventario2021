package com.ejemplo.inventario2021.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.bbdd.ConexionSQLiteHelper;
import com.ejemplo.inventario2021.bbdd.Utilidades;
import com.ejemplo.inventario2021.producto.Producto;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class ImprimirActivity extends AppCompatActivity {

    private  int dia = 0, mes = 0, anio = 0;
    private TextView tvFecha;
    private DataTable dataTable, dataTableDos;
    private ConexionSQLiteHelper conn;  //Para conectar con la BBDD
    private SQLiteDatabase sqLiteDatabase;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprimir);

        tvFecha = findViewById(R.id.fecha);
        dataTable = findViewById(R.id.data_table);
        //dataTableDos = findViewById(R.id.data_table_dos);
        mostrarFecha();
        crearTabla();
        //crearTablaDos();

        /*add = findViewById(R.id.btnAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Agregar Productos!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ImprimirActivity.this, ActivityIngresarProductos.class);
                startActivity(intent);

            }
        });*/

        /*try {
            createPdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    //==============================================================================================
    private void crearTablaDos() {
        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();             //Abre la conexión con la BBDD
        sqLiteDatabase = conn.getWritableDatabase();

        DataTableHeader header = new DataTableHeader.Builder()  //Construye la tabla con los respectivos campos
                .item("Codigo", 10)
                .item("Descripcion ", 10)
                .item("Cantidad Vendida", 10)
                .item("Fecha Venta", 10)
                .build();

        String[] columns = {"codigoVenta", "detalleVenta", "cantidadVenta", "fechaVenta"};  //Array para la consulta con la BD
        Cursor cursor = sqLiteDatabase.query("ventas", columns, null,null,null,null,null);
        ArrayList<DataTableRow> rows = new ArrayList<>();

        for(int i = 0; i < cursor.getCount(); i++){     //Ciclo para llenar las filas
            cursor.moveToNext();
            DataTableRow row = new DataTableRow.Builder()
                    .value(cursor.getString(0))
                    .value(cursor.getString(1))
                    .value(cursor.getString(2))
                    .value(cursor.getString(3))
                    .build();
            rows.add(row); //Guarda los datos en el ArrayList
        }

        dataTableDos.setHeader(header);
        dataTableDos.setRows(rows);
        dataTableDos.inflate(this);
    }

    //==============================================================================================
    private void crearTabla() {
        conn = new ConexionSQLiteHelper(this, "bd productos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();             //Abre la conexión con la BBDD
        sqLiteDatabase = conn.getWritableDatabase();

        DataTableHeader header = new DataTableHeader.Builder()
                .item("N°", 5)
                .item("Codigo", 10)
                .item("Descripcion ", 10)
                .item("Cantidad", 10)
                .build();

        String[] columns = {"codigo", "detalle", "cantidad"};
        Cursor cursor = sqLiteDatabase.query("productos", columns, null,null,null,null,null);
        ArrayList<DataTableRow> rows = new ArrayList<>();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();
            DataTableRow row = new DataTableRow.Builder()
                    .value(String.valueOf(i+1))
                    .value(cursor.getString(0))
                    .value(cursor.getString(1))
                    .value(cursor.getString(2))
                    .build();
            rows.add(row); //Guarda los datos en el ArrayList
        }

        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(this);
    }  //Fin del método

    //==============================================================================================
    private void mostrarFecha() {
        Calendar fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        anio = fecha.get(Calendar.YEAR);
        tvFecha.setText(dia+"/"+(mes+1)+"/"+anio);
    }
    //==============================================================================================

    /*private void createPdf() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF.pdf" );
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(File);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.setMargins(0,0,0,0);
        document.close();
        Toast.makeText(this, "Pdf Created", Toast.LENGTH_LONG).show();

    }*/
}