package com.ejemplo.inventario2021.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "productos.db";
    public static final String TABLE_PRODUCTOS = "productos";
    public static final String TABLE_VENTAS = "ventas";
    public static final String TABLE_FACTURA = "factura";
    public static final String TABLE_DETALLE = "detalle";
    public static final String TABLE_CLIENTE = "clientes";
    public static final String TABLE_TOTAL_FACTURA = "totalFactura";



    final String CREAR_TABLA_PRODUCTOS = "CREATE TABLE productos ( id INTEGER primary key autoincrement , codigo TEXT, " +
            "detalle TEXT, cantidad TEXT, valor TEXT, proveedor TEXT)";

    //final String CREAR_TABLA_VENTAS = "CREATE TABLE ventas ( id INTEGER PRIMARY KEY AUTOINCREMENT, codigoVenta TEXT, detalleVenta TEXT, cantidadVenta TEXT, precioVenta TEXT, fechaVenta TEXT)";

    final String CREAR_TABLA_FACTURA = "CREATE TABLE factura (id_factura INTEGER PRIMARY KEY AUTOINCREMENT, nombreCliente TEXT, fecha TEXT, total TEXT)";

    final String CREAR_TABLA_DETALLE = "CREATE TABLE detalle (id_detalle INTEGER PRIMARY KEY AUTOINCREMENT, id_factura INTEGER , codigo TEXT, descripcion TEXT, cantidad TEXT, precio TEXT, total TEXT)";

    final String CREAR_TABLA_CLIENTE = "CREATE TABLE clientes (id_cliente INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, direccion TEXT)";

    final String CREAR_TABLA_TOTAL_FACTURA = "CREATE TABLE totalFactura (id_factura INTEGER, total TEXT)";
    //id INTEGER primary key autoincrement
    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        //super(context,DATABASE_NOMBRE, null, DATABASE_VERSION);
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_PRODUCTOS);        //Crea la tabla productos
        //db.execSQL(CREAR_TABLA_VENTAS);

        db.execSQL(CREAR_TABLA_FACTURA);
        db.execSQL(CREAR_TABLA_DETALLE);
        db.execSQL(CREAR_TABLA_CLIENTE);
        db.execSQL(CREAR_TABLA_TOTAL_FACTURA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_PRODUCTOS);
        //db.execSQL("DROP TABLE " + TABLE_VENTAS);

        db.execSQL("DROP TABLE " + TABLE_FACTURA);
        db.execSQL("DROP TABLE " + TABLE_DETALLE);
        db.execSQL("DROP TABLE " + TABLE_CLIENTE);
        db.execSQL("DROP TABLE " + TABLE_TOTAL_FACTURA);

        //db.execSQL("DROP TABLE IF EXISTS productos");   //si encuentra una version antigua la elimina
        //db.execSQL("DROP TABLE IF EXISTS ventas");
        onCreate(db);       //Vuelve a generarla y envia el parametro de la base de datos
    }
}
