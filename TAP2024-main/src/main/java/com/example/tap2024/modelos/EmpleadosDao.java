package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.jar.JarEntry;

public class EmpleadosDao {

    private int idEmpleado;
    private String nomEmpleado,rfcEmpleado, direccion, telefono;
    private float salario;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }



    public void INSERTAR(){
        String query = "INSERT INTO empleado (" +
                "nomEmpleado, " +
                "rfcEmpleado, " +
                "salario, " +
                "telefono, " +
                "direccion) " +
                "VALUES('"+nomEmpleado+"','"+rfcEmpleado+"',"+salario+",'"+telefono+"','"+direccion+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ACTUALIZAR(){
        String query = "UPDATE empleado SET " +
                "nomEmpleado='"+nomEmpleado+"'," +
                "rfcEmpleado='"+rfcEmpleado+"'," +
                "salario="+salario+"," +
                "telefono='"+telefono+"'," +
                "direccion='"+direccion+"' " +
                "WHERE idEmpleado = "+idEmpleado;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){
        String query = "DELETE FROM Empleado where idEmpleado = "+idEmpleado;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  ObservableList<EmpleadosDao> CONSULTAR(){
        ObservableList<EmpleadosDao> listaEmp = FXCollections.observableArrayList();
        String query = "SELECT * from Empleado";
        try{
            EmpleadosDao emp;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                emp = new EmpleadosDao();
                emp.idEmpleado = res.getInt("idEmpleado");
                emp.nomEmpleado=res.getString("nomEmpleado");
                emp.rfcEmpleado=res.getString("rfcEmpleado");
                emp.salario=res.getFloat("salario");
                emp.telefono=res.getString("telefono");
                emp.direccion=res.getString("direccion");
                listaEmp.add(emp);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaEmp;
    }



}