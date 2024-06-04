package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDao {
    private int idOrden, idEmpleado, numeroMesa, activa;
    String fecha, observaciones;

    public int getIdOrden() {
        return idOrden;
    }

    public int getActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        this.activa = activa;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void INSERTAR(){
        String query = "INSERT INTO orden (" +
                "idEmpleado, " +
                "numeroMesa," +
                "fecha, " +
                "Activa) " +
                "VALUES("+idEmpleado+","+numeroMesa+","+fecha+","+activa+");";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query = "UPDATE orden SET " +
                "idEmpleado="+idEmpleado+"," +
                "numeroMesa="+numeroMesa+"," +
                "fecha='"+fecha+"'," +
                "observaciones='"+observaciones+"', " +
                "activa = "+activa+" "+
                "WHERE idOrden = "+idOrden;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void CERRARCUENTA(){
        String query = "UPDATE orden SET " +
                "activa = "+activa+" "+
                "WHERE idOrden = "+idOrden;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM orden where idOrden = "+idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ObservableList<OrdenDao> CONSULTAR(){
        ObservableList<OrdenDao> listaOrden = FXCollections.observableArrayList();
        String query = "SELECT * from orden";
        try{
            OrdenDao orden;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                orden = new OrdenDao();
                orden.idOrden=res.getInt("idOrden");
                orden.idEmpleado=res.getInt("idEmpleado");
                orden.numeroMesa=res.getInt("numeroMesa");
                orden.fecha=res.getString("fecha");
                orden.observaciones=res.getString("observaciones");
                orden.activa=res.getInt("activa");

                listaOrden.add(orden);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaOrden;
    }

    public OrdenDao CONSULTARPORORDEN(){

        String query = "SELECT * from orden where idOrden ="+idOrden;
        OrdenDao orden = new OrdenDao();
        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){

                orden.idOrden=res.getInt("idOrden");
                orden.idEmpleado=res.getInt("idEmpleado");
                orden.numeroMesa=res.getInt("numeroMesa");
                orden.fecha=res.getString("fecha");
                orden.observaciones=res.getString("observaciones");
                orden.activa=res.getInt("activa");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return orden;
    }


}
