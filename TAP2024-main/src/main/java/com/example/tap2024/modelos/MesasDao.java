package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MesasDao {
    private int  numeroMesa, ocupada;

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getOcupada() {
        return ocupada;
    }

    public void setOcupada(int ocupada) {
        this.ocupada = ocupada;
    }

    public ArrayList<MesasDao> CONSULTAR(){
        ArrayList<MesasDao> listaMesa = new ArrayList();
        String query = "SELECT * from Mesa";
        try{
            MesasDao mesa;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                mesa = new MesasDao();
                mesa.numeroMesa = res.getInt("numeroMesa");
                mesa.ocupada = res.getInt("ocupada");

                listaMesa.add(mesa);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaMesa;
    }
    public void ELIMINAR(){
        String query = "DELETE FROM Mesa where numeroMesa = "+numeroMesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void INSERTAR(){
        String query = "INSERT INTO Mesa (" +
                "numeroMesa, " +
                "ocupada) " +
                "VALUES('"+numeroMesa+"','"+ocupada+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE Mesa SET " +
                "ocupada='"+ocupada+"'" +
                "WHERE numeroMesa = "+numeroMesa;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
