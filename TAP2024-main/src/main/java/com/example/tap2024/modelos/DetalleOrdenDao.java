package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DetalleOrdenDao {
        private int cantidad, idProducto,idOrden;
        private float precio;


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public void INSERTAR(){
        String query = "INSERT INTO DetalleOrden (" +
                "idProducto, " +
                "cantidad, " +
                "idOrden, " +
                "precio, "+
                "VALUES("+idProducto+",'"+cantidad+"',"+idOrden+","+",'"+precio+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query = "UPDATE Producto SET " +
                "precio="+precio+"," +
                "cantidad="+cantidad+"," +
                "WHERE idProducto = "+idOrden+";";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query = "DELETE FROM Producto where idProducto = "+idProducto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ObservableList<DetalleOrdenDao>CONSULTARVISTA(){
        ObservableList<DetalleOrdenDao> listaDtOrden = FXCollections.observableArrayList();
        String query = "SELECT * from Producto";
        try{
            DetalleOrdenDao DtOrden;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                DtOrden = new DetalleOrdenDao();
                DtOrden.idOrden = res.getInt("idOrden");
                DtOrden.idProducto = res.getInt("idProducto");
                DtOrden.cantidad = res.getInt("cantidad");
                DtOrden.precio = res.getInt("precio");

                listaDtOrden.add(DtOrden);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDtOrden;
    }
    public ArrayList<DetalleOrdenDao> CONSULTAORDEN(int idOrden){
        ArrayList<DetalleOrdenDao> listaDtOrden = new ArrayList();
        String query = "SELECT * from Producto where idCategoria = "+idOrden;
        try {
            DetalleOrdenDao DtOrden;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                DtOrden = new DetalleOrdenDao();
                DtOrden = new DetalleOrdenDao();
                DtOrden.idOrden = res.getInt("idOrden");
                DtOrden.idProducto = res.getInt("idProducto");
                DtOrden.cantidad = res.getInt("cantidad");
                DtOrden.precio = res.getInt("precio");

                listaDtOrden.add(DtOrden);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDtOrden;
    }
}
