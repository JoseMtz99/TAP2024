package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriasDao {
    private int idCategoria;
    private String nombreCategoria,dirImagen;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDirImagen() {
        return dirImagen;
    }

    public void setDirImagen(String dirImagen) {
        this.dirImagen = dirImagen;
    }

    public void INSERTAR(){
        String query = "INSERT INTO Categorias (" +
                "nombreCategoria, " +
                "dirImagen) " +
                "VALUES('"+nombreCategoria+"','"+dirImagen+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query = "UPDATE Categorias SET " +
                "nombreCategoria='"+nombreCategoria+"'," +
                "dirImagen='"+dirImagen+"' " +
                "WHERE idCategoria = "+idCategoria;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query = "DELETE FROM Categorias where idCategoria = "+idCategoria;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<CategoriasDao> CONSULTAR(){
        ArrayList<CategoriasDao> listaCat = new ArrayList<>();
        String query = "SELECT * from Categorias";
        try{
            CategoriasDao cat;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                cat = new CategoriasDao();
                cat.idCategoria=res.getInt("idCategoria");
                cat.nombreCategoria=res.getString("nombreCategoria");
                cat.dirImagen=res.getString("dirImagen");

                listaCat.add(cat);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaCat;
    }


}
