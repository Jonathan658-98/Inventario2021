package com.ejemplo.inventario2021.producto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Producto   implements Parcelable, Serializable {

    //Atributos del producto
    private String id;

    private String codigo;
    private String detalle;
    private String cantidad;
    private String valor;
    private String proveedor;
    private String cantVenta;
    private int factura;
    private float total;
    private String fecha;
    private String idVenta;

    public Boolean isSelected = false;


    //Atributos para la tabla ventas
    private String codigoVenta;
    private String detalleVenta;
    private String cantidadVenta ="1";
    private String precioVenta;
    private String fechaVenta;

    private int sumar = 1;

    private int sumarCantidad = 1;

    protected Producto(Parcel in) {
        id = in.readString();
        codigo = in.readString();
        detalle = in.readString();
        cantidad = in.readString();
        valor = in.readString();
        proveedor = in.readString();
        cantVenta = in.readString();
        factura = in.readInt();
        total = in.readFloat();
        fecha = in.readString();
        idVenta = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
        codigoVenta = in.readString();
        detalleVenta = in.readString();
        cantidadVenta = in.readString();
        precioVenta = in.readString();
        fechaVenta = in.readString();
        sumar = in.readInt();
        sumarCantidad = in.readInt();
        idFactura = in.readString();
        idDetalle = in.readString();
        totalItem = in.readString();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public int getSumarCantidad() {
        return sumarCantidad;
    }

    public void setSumarCantidad(int sumarCantidad) {
        this.sumarCantidad = sumarCantidad;
    }

    //Atributos para la factura
    private String idFactura;
    private String idDetalle;
    private String totalItem;

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    /*protected Producto(Parcel in) {
        id = in.readString();
        codigo = in.readString();
        detalle = in.readString();
        cantidad = in.readString();
        valor = in.readString();
        proveedor = in.readString();
        cantVenta = in.readString();
        factura = in.readInt();
        total = in.readFloat();
        fecha = in.readString();
        idVenta = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
        codigoVenta = in.readString();
        detalleVenta = in.readString();
        cantidadVenta = in.readString();
        precioVenta = in.readString();
        fechaVenta = in.readString();

        idFactura = in.readString();
        idDetalle = in.readString();
        totalItem = in.readString();
        //sumar = in.readInt();
    }*/

    /*public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };*/

    public void setSumar(int x){sumar = x;}
    public int getSumar(){return sumar;}

    //Método constructor
    public Producto(String id, String codigo, String detalle, String cantidad, String valor, String proveedor, int factura, float total, String cantVenta, String fecha) {
        this.id = id;

        this.codigo = codigo;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.valor = valor;
        this.proveedor = proveedor;
        this.factura = factura;
        this.total = total;
        this.cantVenta = cantVenta;
        this.fecha = fecha;
    }

    /*public Producto(String codigo, String detalle, String cantidad) {
        this.codigo = codigo;
        this.detalle = detalle;
        this.cantidad = cantidad;
    }*/

    public Producto(String idVenta) {
        this.idVenta = idVenta;
    }
    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public Producto() { }

    //Métodos Getters y Setters para la tabla productos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getFactura() {
        return factura;
    }

    public void setFactura(int factura) {
        this.factura = factura;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    //==============================================================================================
    //Métodos Getters y Setters para la tabla ventas

    public String getCantVenta() {
        return cantVenta;
    }

    public void setCantVenta(String cantVenta) {
        this.cantVenta = cantVenta;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public String getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(String detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public String getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(String cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(codigo);
        parcel.writeString(detalle);
        parcel.writeString(cantidad);
        parcel.writeString(valor);
        parcel.writeString(proveedor);
        parcel.writeString(cantVenta);
        parcel.writeInt(factura);
        parcel.writeFloat(total);
        parcel.writeString(fecha);
        parcel.writeString(idVenta);
        parcel.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
        parcel.writeString(codigoVenta);
        parcel.writeString(detalleVenta);
        parcel.writeString(cantidadVenta);
        parcel.writeString(precioVenta);
        parcel.writeString(fechaVenta);
        parcel.writeInt(sumar);
        parcel.writeInt(sumarCantidad);
        parcel.writeString(idFactura);
        parcel.writeString(idDetalle);
        parcel.writeString(totalItem);
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(codigo);
        parcel.writeString(detalle);
        parcel.writeString(cantidad);
        parcel.writeString(valor);
        parcel.writeString(proveedor);
        parcel.writeString(cantVenta);
        parcel.writeInt(factura);
        parcel.writeFloat(total);
        parcel.writeString(fecha);
        parcel.writeString(idVenta);
        parcel.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
        parcel.writeString(codigoVenta);
        parcel.writeString(detalleVenta);
        parcel.writeString(cantidadVenta);
        parcel.writeString(precioVenta);
        parcel.writeString(fechaVenta);
        parcel.writeInt(sumar);
        parcel.writeString(idFactura);
        parcel.writeString(idDetalle);
        parcel.writeString(totalItem);
    }*/


}
