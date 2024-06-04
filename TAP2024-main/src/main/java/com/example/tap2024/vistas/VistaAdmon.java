package com.example.tap2024.vistas;

import com.example.tap2024.modelos.DetalleOrdenDao;
import com.example.tap2024.vistas.EmpleadoTaqueria;
import javafx.collections.ObservableList;
import com.example.tap2024.modelos.Conexion;
import com.example.tap2024.modelos.DetalleOrdenDao;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VistaAdmon extends Stage {
    private Scene escena;

    public VistaAdmon() {
        Conexion.crearConexion();
        CrearUI();
        this.setTitle("Administración");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
              Image imgFondo = new Image(getClass().getResourceAsStream("/images/Fondo.jpeg"));
        BackgroundImage backgroundImage = new BackgroundImage(
                imgFondo,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
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


        PieChart pieChart = new PieChart();
        pieChart.setTitle("ESTADISTICAS");
        pieChart.setMinSize(10, 10);

        DetalleOrdenDao detalleOrdenDao = new DetalleOrdenDao();
        ObservableList<PieChart.Data> pieChartData = detalleOrdenDao.fetchPieChartData();
        pieChart.setData(pieChartData);


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChartVentasPorDia = new BarChart<>(xAxis, yAxis);
        barChartVentasPorDia.setTitle("Ventas por Día");
        xAxis.setLabel("Día");
        yAxis.setLabel("Ventas");
        barChartVentasPorDia.setMinSize(300, 200);

        XYChart.Series<String, Number> seriesVentasPorDia = new XYChart.Series<>();
        seriesVentasPorDia.setName("Ventas");
        seriesVentasPorDia.getData().addAll(detalleOrdenDao.getVentasPorDia());
        barChartVentasPorDia.getData().add(seriesVentasPorDia);


        CategoryAxis xAxisEmpleado = new CategoryAxis();
        NumberAxis yAxisEmpleado = new NumberAxis();
        BarChart<String, Number> barChartEmpleadoMasVentas = new BarChart<>(xAxisEmpleado, yAxisEmpleado);
        barChartEmpleadoMasVentas.setTitle("Empleado con Más Ventas");
        xAxisEmpleado.setLabel("Empleado");
        yAxisEmpleado.setLabel("Ventas");
        barChartEmpleadoMasVentas.setMinSize(300, 200);

        XYChart.Series<String, Number> seriesEmpleadoMasVentas = new XYChart.Series<>();
        seriesEmpleadoMasVentas.setName("Ventas");
        seriesEmpleadoMasVentas.getData().addAll(detalleOrdenDao.getEmpleadoConMasVentas());
        barChartEmpleadoMasVentas.getData().add(seriesEmpleadoMasVentas);


        CategoryAxis xAxisProducto = new CategoryAxis();
        NumberAxis yAxisProducto = new NumberAxis();
        BarChart<String, Number> barChartProductoMasVendido = new BarChart<>(xAxisProducto, yAxisProducto);
        barChartProductoMasVendido.setTitle("Producto Más Vendido");
        xAxisProducto.setLabel("Producto");
        yAxisProducto.setLabel("Cantidad Vendida");
        barChartProductoMasVendido.setMinSize(300, 200);

        XYChart.Series<String, Number> seriesProductoMasVendido = new XYChart.Series<>();
        seriesProductoMasVendido.setName("Ventas");
        XYChart.Data<String, Number> productoMasVendido = detalleOrdenDao.getProductoMasVendido();
        if (productoMasVendido != null) {
            seriesProductoMasVendido.getData().add(productoMasVendido);
        }
        barChartProductoMasVendido.getData().add(seriesProductoMasVendido);


        VBox vbBotones = new VBox(20, btnCategorias, btnProductos, btnEmpleados);
        vbBotones.setMinWidth(150);
        vbBotones.setTranslateY(20);

        VBox vbGraficas = new VBox(20, pieChart, barChartVentasPorDia, barChartEmpleadoMasVentas, barChartProductoMasVendido);
        vbGraficas.setAlignment(Pos.CENTER);
        vbGraficas.setPadding(new Insets(20));
        vbGraficas.setMinWidth(600);

        HBox hbPrincipal = new HBox(20, vbBotones, vbGraficas);
        hbPrincipal.setPrefSize(1200, 800);
        hbPrincipal.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(hbPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPadding(new Insets(20));

        StackPane pnContenedor = new StackPane();
        pnContenedor.setBackground(background);
        pnContenedor.getChildren().add(scrollPane);

        escena = new Scene(pnContenedor, 1200, 800);
    }
}
