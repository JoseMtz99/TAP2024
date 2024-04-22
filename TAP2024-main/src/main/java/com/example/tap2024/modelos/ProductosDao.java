package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductosDao {
    private int idProducto, idCategoria;
    private String nombreProducto,dirImagen;
    private float precio, costo;

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDirImagen() {
        return dirImagen;
    }

    public void setDirImagen(String dirImagen) {
        this.dirImagen = dirImagen;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void INSERTAR(){
        String query = "INSERT INTO Producto (" +
                "idCategoria, " +
                "nombreProducto, " +
                "precio, " +
                "costo, "+
                "dirImagen) " +
                "VALUES("+idCategoria+",'"+nombreProducto+"',"+precio+","+costo+",'"+dirImagen+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query = "UPDATE Producto SET " +
                "nombreProducto='"+nombreProducto+"'," +
                "precio="+precio+"," +
                "costo="+costo+"," +
                "dirImagen='"+dirImagen+"' " +
                "WHERE idProducto = "+idProducto+";";

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


    public ObservableList<ProductosDao> CONSULTAR(){
        ObservableList<ProductosDao> listaProd= FXCollections.observableArrayList();
        String query = "SELECT * from Producto";
        try{
            ProductosDao prod;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                prod = new ProductosDao();
                prod.idProducto = res.getInt("idProducto");
                prod.idCategoria = res.getInt("idCategoria");
                prod.nombreProducto = res.getString("nombreProducto");
                prod.precio = res.getFloat("precio");
                prod.costo = res.getFloat("costo");
                prod.dirImagen = res.getString("dirImagen");

                listaProd.add(prod);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaProd;
    }

    public ArrayList<ProductosDao> CONSULTARPORCATEGORIA(int idCat){
        ArrayList<ProductosDao> listaProdCat=new ArrayList();
        String query = "SELECT * from Producto where idCategoria = "+idCat;
        try{
            ProductosDao prod;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                prod = new ProductosDao();
                prod.idProducto = res.getInt("idProducto");
                prod.idCategoria = res.getInt("idCategoria");
                prod.nombreProducto = res.getString("nombreProducto");
                prod.precio = res.getFloat("precio");
                prod.costo = res.getFloat("costo");
                prod.dirImagen = res.getString("dirImagen");

                listaProdCat.add(prod);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaProdCat;
    }


}
