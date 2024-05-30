package com.example.tap2024.vistas;

import com.example.tap2024.modelos.Conexion;
import com.example.tap2024.modelos.DetalleOrdenDao;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class VistaAdmon extends Stage {
    private Scene escena;

    public VistaAdmon() {
        Conexion.crearConexion(); // Establecer la conexión a la base de datos
        CrearUI();
        this.setTitle("Administración");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        Image imgFondo = new Image(getClass().getResourceAsStream("/images/Fondo.jpeg"));
        BackgroundImage backgroundImage = new BackgroundImage(imgFondo, null, null, null, null);
        Background background = new Background(backgroundImage);

        Button btnCategorias = new Button("Categorías");
        Image imgCategorias = new Image(getClass().getResourceAsStream("/images/categorias.png"));
        ImageView imvCategorias = new ImageView(imgCategorias);
        imvCategorias.setFitWidth(50);
        imvCategorias.setFitHeight(50);
        btnCategorias.setGraphic(imvCategorias);
        btnCategorias.setOnAction(actionEvent -> new Categorias());

        Button btnProductos = new Button("Productos");
        Image imgProductos = new Image(getClass().getResourceAsStream("/images/productos.png"));
        ImageView imvProductos = new ImageView(imgProductos);
        imvProductos.setFitWidth(50);
        imvProductos.setFitHeight(50);
        btnProductos.setGraphic(imvProductos);
        btnProductos.setOnAction(actionEvent -> new Productos());

        Button btnEmpleados = new Button("Empleados");
        Image imgEmpleados = new Image(getClass().getResourceAsStream("/images/empleados.png"));
        ImageView imvEmpleados = new ImageView(imgEmpleados);
        imvEmpleados.setFitWidth(50);
        imvEmpleados.setFitHeight(50);
        btnEmpleados.setGraphic(imvEmpleados);
        btnEmpleados.setOnAction(actionEvent -> new EmpleadoTaqueria());

        // Crear el gráfico de pastel
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Distribución de Categorías");
        pieChart.setMinSize(20, 20); // Establecer tamaño mínimo

        DetalleOrdenDao detalleOrdenDao = new DetalleOrdenDao();
        ObservableList<PieChart.Data> pieChartData = detalleOrdenDao.fetchPieChartData();
        pieChart.setData(pieChartData);

        // Crear el gráfico de barras para Ventas por Día
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChartVentasPorDia = new BarChart<>(xAxis, yAxis);
        barChartVentasPorDia.setTitle("Ventas por Día");
        xAxis.setLabel("Día");
        yAxis.setLabel("Ventas");
        barChartVentasPorDia.setMinSize(600, 400); // Establecer tamaño mínimo

        XYChart.Series<String, Number> seriesVentasPorDia = new XYChart.Series<>();
        seriesVentasPorDia.setName("Ventas");
        seriesVentasPorDia.getData().addAll(detalleOrdenDao.getVentasPorDia());
        barChartVentasPorDia.getData().add(seriesVentasPorDia);

        // Crear el gráfico de barras para Empleado con Más Ventas
        CategoryAxis xAxisEmpleado = new CategoryAxis();
        NumberAxis yAxisEmpleado = new NumberAxis();
        BarChart<String, Number> barChartEmpleadoMasVentas = new BarChart<>(xAxisEmpleado, yAxisEmpleado);
        barChartEmpleadoMasVentas.setTitle("Empleado con Más Ventas");
        xAxisEmpleado.setLabel("Empleado");
        yAxisEmpleado.setLabel("Ventas");
        barChartEmpleadoMasVentas.setMinSize(600, 400); // Establecer tamaño mínimo

        XYChart.Series<String, Number> seriesEmpleadoMasVentas = new XYChart.Series<>();
        seriesEmpleadoMasVentas.setName("Ventas");
        seriesEmpleadoMasVentas.getData().addAll(detalleOrdenDao.getEmpleadoConMasVentas());
        barChartEmpleadoMasVentas.getData().add(seriesEmpleadoMasVentas);

        // Layouts
        VBox vbBotones = new VBox(20, btnCategorias, btnProductos, btnEmpleados);
        VBox vbGraficas = new VBox(20, pieChart, barChartVentasPorDia, barChartEmpleadoMasVentas);
        vbGraficas.setMinWidth(800); // Ancho mínimo del VBox para las gráficas
        vbGraficas.setPrefHeight(600); // Altura preferida del VBox para las gráficas

        HBox hbPrincipal = new HBox(20, vbBotones, vbGraficas);
        hbPrincipal.setPrefSize(1300, 800); // Ajustar el tamaño según sea necesario

        // Panel principal
        Panel pnContenedor = new Panel();
        pnContenedor.setBackground(background);
        pnContenedor.getChildren().add(hbPrincipal);

        escena = new Scene(pnContenedor, 1300, 800);
    }
}
