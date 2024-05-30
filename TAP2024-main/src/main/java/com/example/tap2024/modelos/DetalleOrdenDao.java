package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetalleOrdenDao {
    private int cantidad, idProducto, idOrden;
    private float precio;

    // Métodos getters y setters omitidos por brevedad

    public ObservableList<PieChart.Data> fetchPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        String query = "SELECT nombreCategoria, COUNT(*) AS cantidad FROM Producto GROUP BY nombreCategoria";
        try (Statement stmt = Conexion.connection.createStatement()) {
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                pieChartData.add(new PieChart.Data(res.getString("nombreCategoria"), res.getInt("cantidad")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pieChartData;
    }

    public ObservableList<XYChart.Data<String, Number>> getVentasPorDia() {
        ObservableList<XYChart.Data<String, Number>> ventasPorDia = FXCollections.observableArrayList();
        String query = "SELECT DATE(fechaOrden) AS dia, SUM(cantidad * precio) AS totalVentas FROM DetalleOrden GROUP BY dia";
        try (Statement stmt = Conexion.connection.createStatement()) {
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                ventasPorDia.add(new XYChart.Data<>(res.getString("dia"), res.getDouble("totalVentas")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventasPorDia;
    }

    public ObservableList<XYChart.Data<String, Number>> getEmpleadoConMasVentas() {
        ObservableList<XYChart.Data<String, Number>> empleadoMasVentas = FXCollections.observableArrayList();
        String query = "SELECT e.nombreEmpleado, SUM(d.cantidad * d.precio) AS totalVentas FROM DetalleOrden d JOIN Empleado e ON d.idEmpleado = e.idEmpleado GROUP BY e.idEmpleado";
        try (Statement stmt = Conexion.connection.createStatement()) {
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                empleadoMasVentas.add(new XYChart.Data<>(res.getString("nombreEmpleado"), res.getDouble("totalVentas")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleadoMasVentas;
    }
}
