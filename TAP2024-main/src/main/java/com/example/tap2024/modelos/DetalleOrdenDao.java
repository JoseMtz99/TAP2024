package com.example.tap2024.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetalleOrdenDao {
    private int cantidad, idProducto, idOrden;
    private float precio;
    private String nombreProducto;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void INSERTAR(){
        String query = "INSERT INTO detalleorden (" +
                "idOrden, " +
                "idProducto, " +
                "cantidad," +
                "precio) " +
                "VALUES("+idOrden+","+idProducto+","+cantidad+","+precio+")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM detalleorden where idOrden = "+idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ObservableList<DetalleOrdenDao> CONSULTAR(){
        ObservableList<DetalleOrdenDao> listaDet = FXCollections.observableArrayList();
        String query = "SELECT * from detalleorden";
        try{
            DetalleOrdenDao detalle;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                detalle = new DetalleOrdenDao();
                detalle.idOrden=res.getInt("idOrden");
                detalle.idProducto=res.getInt("idProducto");
                detalle.precio=res.getFloat("precio");
                detalle.cantidad=res.getInt("cantidad");

                listaDet.add(detalle);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaDet;
    }
// select SUM(cantidad), nombreProducto, AVG(do.precio) from detalleOrden do join producto p on do.idProducto = p.idProducto where idOrden = 1 group by nombreProducto;
    public ObservableList<DetalleOrdenDao> CONSULTARORDEN(int orden){
        ObservableList<DetalleOrdenDao> listaDet = FXCollections.observableArrayList();
        String query = "select SUM(cantidad) as Cantidad, nombreProducto, AVG(do.precio) as Precio from detalleOrden do join producto p on do.idProducto = p.idProducto where idOrden = "+orden+" group by nombreProducto;";
        try{
            DetalleOrdenDao detalle;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                detalle = new DetalleOrdenDao();
                detalle.cantidad=res.getInt("Cantidad");
                detalle.nombreProducto=res.getString("nombreProducto");
                detalle.precio=res.getFloat("Precio");

                listaDet.add(detalle);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return listaDet;
    }

    public Float TotalizarOrden(){
        Float total;
        total= (float) 0;
        String query= "Select SUM(cantidad*precio)as Total from detalleOrden where idOrden = "+idOrden+" group by idOrden;";
        try{
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                total = res.getFloat("Total");
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public ObservableList<PieChart.Data> fetchPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        String query = "SELECT c.nombreCategoria, COUNT(*) AS cantidad " +
                "FROM Producto p " +
                "JOIN detalleorden d ON p.idProducto = d.idProducto " +
                "JOIN orden o ON d.idOrden = o.idOrden " +
                "JOIN categorias c ON p.idCategoria = c.idCategoria " +
                "GROUP BY c.nombreCategoria";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                pieChartData.add(new PieChart.Data(res.getString("nombreCategoria"), res.getInt("cantidad")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching pie chart data: " + e.getMessage());
            e.printStackTrace();
        }
        return pieChartData;
    }

    public ObservableList<XYChart.Data<String, Number>> getVentasPorDia() {
        ObservableList<XYChart.Data<String, Number>> ventasPorDia = FXCollections.observableArrayList();
        String query = "SELECT DATE(o.fecha) AS dia, SUM(d.cantidad * d.precio) AS totalVentas " +
                "FROM detalleorden d " +
                "JOIN orden o ON d.idOrden = o.idOrden " +
                "GROUP BY dia " +
                "ORDER BY dia";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                ventasPorDia.add(new XYChart.Data<>(res.getString("dia"), res.getDouble("totalVentas")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sales per day: " + e.getMessage());
            e.printStackTrace();
        }
        return ventasPorDia;
    }

    public ObservableList<XYChart.Data<String, Number>> getEmpleadoConMasVentas() {
        ObservableList<XYChart.Data<String, Number>> empleadoMasVentas = FXCollections.observableArrayList();
        String query = "SELECT e.nomEmpleado, SUM(d.cantidad * d.precio) AS totalVentas " +
                "FROM DetalleOrden d " +
                "JOIN Orden o ON d.idOrden = o.idOrden " +
                "JOIN Empleado e ON o.idEmpleado = e.idEmpleado " +
                "GROUP BY e.nomEmpleado " +
                "ORDER BY totalVentas DESC";

        try (
                Statement stmt = Conexion.connection.createStatement();
                ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                empleadoMasVentas.add(new XYChart.Data<>(res.getString("nomEmpleado"), res.getDouble("totalVentas")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top employee sales: " + e.getMessage());
            e.printStackTrace();
        }
        return empleadoMasVentas;
    }

    public XYChart.Data<String, Number> getProductoMasVendido() {
        XYChart.Data<String, Number> productoMasVendido = null;
        String query = "SELECT p.nombreProducto, SUM(d.cantidad) AS totalCantidad " +
                "FROM DetalleOrden d " +
                "JOIN Producto p ON d.idProducto = p.idProducto " +
                "JOIN Orden o ON d.idOrden = o.idOrden " +
                "GROUP BY p.nombreProducto " +
                "ORDER BY totalCantidad DESC " +
                "LIMIT 1";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            if (res.next()) {
                productoMasVendido = new XYChart.Data<>(res.getString("nombreProducto"), res.getInt("totalCantidad"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching top selling product: " + e.getMessage());
            e.printStackTrace();
        }
        return productoMasVendido;
    }
}