package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataFetcher {

    public ObservableList<PieChart.Data> fetchPieChartData() {
        ObservableList<PieChart.Data> dataPoints = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Crear la conexión
            Conexion.crearConexion();
            connection = Conexion.connection;

            // Verificar si la conexión fue exitosa
            if (connection != null) {
                String query = "SELECT categoria, valor FROM orden";  // Asegúrate de que estos nombres de columnas son correctos

                // Crear un Statement y ejecutar la consulta
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);

                // Procesar los resultados
                while (resultSet.next()) {
                    String categoria = resultSet.getString("categoria");
                    double valor = resultSet.getDouble("valor");
                    System.out.println("Categoria: " + categoria + ", Valor: " + valor);
                    dataPoints.add(new PieChart.Data(categoria, valor));
                }
            } else {
                System.err.println("La conexión a la base de datos no se pudo establecer.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos en el orden inverso de su apertura
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataPoints;
    }
}
