package com.example.tap2024.vistas;

import com.example.tap2024.modelos.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AppTaqueria extends Stage {
    private Scene escena;
    private HBox hbox, hbAgregar, hbTotal;
    private VBox vboxBotones, vboxMesas, vbOrden;
    private Button btnMas, btnMenos, btnAgregar, btnadmin, cuentaButton, tomarOrdenButton, regresarButton;
    private GridPane gridMesas, gdpCategorias, gdpProducto;
    private String estiloBotones, estilomesa;
    private TextField txtCantidad, txtTotal;
    private Label lblCantidad, lblOrden, lblTotal;
    private int cantidad, h, v;
    private IconoMesa icnMesa;
    private IconoCategorias icnCategorias;
    private Image imgFondo;
    private BackgroundImage backgroundImage;
    private Background background;
    private ArrayList<ProductosDao> listaProductos;
    private TableView<DetalleOrdenDao> tbvDetOrden;
    private int numeroEmpleado, ordenActual, mesaActual;
    private Float totalCuenta;

    public AppTaqueria(boolean administrador) {
        super();
        CrearUI(administrador);
        escena = new Scene(hbox);
        this.setTitle("AppTaqueria");
        this.setScene(escena);
        this.show();
        this.setMaximized(true);

    }

    private void CrearUI(boolean admin) {
        hbox = new HBox(10);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER_LEFT);

        imgFondo = new Image(getClass().getResourceAsStream("/images/Fondo.jpeg"));
        backgroundImage = new BackgroundImage(imgFondo, null, null, null, null);

        vboxBotones = new VBox(10);
        vboxBotones.setAlignment(Pos.TOP_CENTER);

        Image imagenOrden = new Image(getClass().getResourceAsStream("/Images/orden.png"));
        ImageView imageViewOrden = new ImageView(imagenOrden);
        imageViewOrden.setFitWidth(50);
        imageViewOrden.setFitHeight(50);
        tomarOrdenButton = new Button();

        tomarOrdenButton.setDisable(true);
        tomarOrdenButton.setGraphic(imageViewOrden);

        Image imagenCuenta = new Image(getClass().getResourceAsStream("/Images/cuenta.png"));
        ImageView imageViewCuenta = new ImageView(imagenCuenta);
        imageViewCuenta.setFitWidth(50);
        imageViewCuenta.setFitHeight(50);
        cuentaButton = new Button("Generar cuenta");
        cuentaButton.setDisable(true);
        cuentaButton.setOnAction(actionEvent -> {
            try {
                generarCuenta();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        cuentaButton.setGraphic(imageViewCuenta);

        Image imagenEmpleados = new Image(getClass().getResourceAsStream("/Images/admon.png"));
        ImageView imageViewEmpleados = new ImageView(imagenEmpleados);
        imageViewEmpleados.setFitWidth(50);
        imageViewEmpleados.setFitHeight(50);
        btnadmin = new Button("Administración");
        btnadmin.setGraphic(imageViewEmpleados);

        Image imagenCocina = new Image(getClass().getResourceAsStream("/Images/cocina.png"));
        ImageView imageViewCocina = new ImageView(imagenCocina);
        imageViewCocina.setFitWidth(50);
        imageViewCocina.setFitHeight(50);
        Image imagenSalir = new Image(getClass().getResourceAsStream("/Images/salir.png"));
        ImageView imageViewSalir = new ImageView(imagenSalir);
        imageViewSalir.setFitWidth(50);
        imageViewSalir.setFitHeight(50);
        regresarButton = new Button("Cerrar");
        regresarButton.setGraphic(imageViewSalir);

        estiloBotones = "-fx-font-size: 15px; -fx-min-width: 120px; -fx-min-height: 120px; -fx-background-color: gray;";

        tomarOrdenButton.setStyle(estiloBotones);
        cuentaButton.setStyle(estiloBotones);
        btnadmin.setStyle(estiloBotones);
        regresarButton.setStyle(estiloBotones);

        btnadmin.setOnAction(actionEvent -> new VistaAdmon());
        regresarButton.setOnAction(event -> this.close());


        vboxBotones.getChildren().addAll(tomarOrdenButton, cuentaButton, btnadmin, regresarButton);


        vboxMesas = new VBox(10);
        vboxMesas.setAlignment(Pos.TOP_CENTER); // Alineación superior

        gridMesas = new GridPane();
        tbvDetOrden = new TableView<DetalleOrdenDao>();
        agregarMesas();

        gdpCategorias = new GridPane();
        gdpCategorias.setPrefSize(200, 100);
        CategoriasDao cat = new CategoriasDao();
        ArrayList<CategoriasDao> listaCategorias = new ArrayList<>();
        listaCategorias = cat.CONSULTAR();

        h = listaCategorias.size() / 2;
        v = 2;

        gdpCategorias.setHgap(h);
        gdpCategorias.setVgap(v);
        int cont = 0;
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < h; j++) {
                IconoCategorias icnCategorias = new IconoCategorias();
                icnCategorias.categoriasDao = listaCategorias.get(cont);
                Image imgCat = new Image(getClass().getResourceAsStream("/" + listaCategorias.get(cont).getDirImagen()));
                ImageView imvCat = new ImageView(imgCat);
                imvCat.setFitWidth(80);
                imvCat.setFitHeight(80);

                icnCategorias.boton = new Button();
                icnCategorias.boton.setGraphic(imvCat);
                icnCategorias.boton.setPrefSize(90, 90);
                gdpCategorias.add(icnCategorias.boton, j, i);

                icnCategorias.boton.setOnAction(actionEvent -> BotonCategorias(icnCategorias.categoriasDao.getIdCategoria()));

                cont++;

            }

        }
        gdpCategorias.setDisable(true);
        gdpProducto = new GridPane();

        btnMenos = new Button("-");
        btnMenos.setDisable(true);

        btnMas = new Button("+");
        btnAgregar = new Button("Agregar");
        btnAgregar.setDisable(true);
        lblCantidad = new Label("Cantidad");

        txtCantidad = new TextField();
        cantidad = 1;
        txtCantidad.setText(String.valueOf(cantidad));
        txtCantidad.setEditable(false);
        hbAgregar = new HBox(btnMenos, btnMas, lblCantidad, txtCantidad, btnAgregar);

        lblTotal = new Label("Total");
        lblTotal.setBackground(Background.fill(Color.WHITE));
        txtTotal = new TextField();
        txtTotal.setEditable(false);
        vbOrden = new VBox();
        lblOrden = new Label();
        hbTotal = new HBox(lblTotal, txtTotal);

        vboxMesas.getChildren().addAll(gridMesas, gdpCategorias, gdpProducto, hbAgregar);
        background = new Background(backgroundImage);
        hbox.setBackground(background);
        hbox.getChildren().addAll(vboxBotones, vboxMesas, vbOrden);


        btnMas.setOnAction(actionEvent -> SumarProducto());
        btnMenos.setOnAction(actionEvent -> RestarProducto());

        if (!admin) {
            numeroEmpleado = 7;
            btnadmin.setDisable(true);
        } else numeroEmpleado = 2;

    }

    private void generarCuenta() throws FileNotFoundException {
        MesasDao mesa = new MesasDao();
        mesa.setNumeroMesa(mesaActual);
        mesa.setOcupada(0);
        mesa.ACTUALIZAR();
        OrdenDao orden = new OrdenDao();
        orden.setIdOrden(ordenActual);
        orden.setActiva(0);
        orden.CERRARCUENTA();
        agregarMesas();

        crearPdf();
    }

    private void crearPdf () throws FileNotFoundException {

        String dest = "C:\\\\Users\\\\josec\\\\OneDrive\\\\Tecno\\\\IntelliJ Projects\\\\TAP2024-main\\\\TAP2024-main\\\\src\\\\main\\\\resources\\\\Files\\\\archivopdf.pdf";
        File file = new File(dest);
        file.getParentFile().mkdirs();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc);
        doc.add(new Paragraph("Orden: "+ordenActual));
        Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
        table.addCell("Cantidad");
        table.addCell("Producto");
        table.addCell("Precio Unitario");
        ArrayList<DetalleOrdenDao> list = new ArrayList<>();
        DetalleOrdenDao detDao = new DetalleOrdenDao();
        list = detDao.CONSULTAARRAY(ordenActual);
        OrdenDao ord = new OrdenDao();
        ord.setIdOrden(ordenActual);
        ord = ord.CONSULTARPORORDEN();
        doc.add(new Paragraph("Fecha: "+ ord.getFecha()));
        EmpleadosDao emp = new EmpleadosDao();
        emp.setIdEmpleado(ord.getIdEmpleado());
        emp = emp.CONSULTARPOREMPLEADO();
        doc.add(new Paragraph("Empleado: "+ emp.getNomEmpleado()));
        for (int i = 0; i < list.size(); i++) {
            table.addCell(list.get(i).getCantidad()+"");
            table.addCell(list.get(i).getNombreProducto());
            table.addCell(list.get(i).getPrecio()+"");
        }

        doc.add(table);
        doc.add(new Paragraph("IVA: "+totalCuenta*.138));
        doc.add(new Paragraph("Total: "+totalCuenta));
        doc.close();
    }

    private void agregarMesas() {
        MesasDao mesa = new MesasDao();
        gridMesas.getChildren().removeAll();
        ArrayList<MesasDao> listaMesas = new ArrayList<>();
        listaMesas = mesa.CONSULTAR();

        h = 10;
        v = 10;


        if (listaMesas.size() % 4 == 0) {
            v = listaMesas.size() / 4;
            h = 4;
        } else if (listaMesas.size() % 3 == 0) {
            v = listaMesas.size() / 3;
            h = 3;
        } else if (listaMesas.size() % 2 == 0) {
            v = listaMesas.size() / 2;
            h = 2;
        }

        gridMesas.setHgap(h);
        gridMesas.setVgap(v);

        for (int i = 0; i < listaMesas.size(); i++) {

            IconoMesa icnMesa = new IconoMesa();
            icnMesa.mesa = listaMesas.get(i);

            Image imagenMesa = new Image(getClass().getResourceAsStream("/Images/mesas.png"));
            ImageView imageViewMesa = new ImageView(imagenMesa);
            imageViewMesa.setFitWidth(50);
            imageViewMesa.setFitHeight(40);

            icnMesa.Boton = new Button();
            icnMesa.Boton.setGraphic(imageViewMesa);
            icnMesa.Boton.setPrefSize(60, 50);
            gridMesas.add(icnMesa.Boton, i % v, i / v);

            icnMesa.Boton.setOnAction(actionEvent -> BotonMesa(icnMesa));

            EstiloMesas(icnMesa);
        }
    }


    private void BotonMesa(IconoMesa icono) {
        mesaActual=icono.mesa.getNumeroMesa();
        cuentaButton.setDisable(true);
        gdpCategorias.setDisable(true);
        gdpProducto.setVisible(false);
        if (icono.mesa.getOcupada() == 0) {
            tomarOrdenButton.setText("Tomar orden");
            tomarOrdenButton.setDisable(false);
        } else {
            tomarOrdenButton.setText("Modificar orden");
            tomarOrdenButton.setDisable(false);

            DetalleOrdenDao objDet = new DetalleOrdenDao();
            objDet.setIdOrden(ordenActual);
            totalCuenta = objDet.TotalizarOrden();
            txtTotal.setText(totalCuenta + "");
        }

        tomarOrdenButton.setOnAction(event -> BotonOrden(icono));
    }

    private void BotonOrden(IconoMesa icono) {
        ordenActual = icono.mesa.CONSULTARNUMEROORDEN();
        OrdenDao objOrden = new OrdenDao();
        DetalleOrdenDao objDet = new DetalleOrdenDao();
        if (icono.mesa.getOcupada() != 1) {
            totalCuenta= (float) 0;
            txtTotal.setText(String.valueOf(totalCuenta));
            icono.mesa.setOcupada(1);
            icono.mesa.ACTUALIZAR();
            objOrden.setIdEmpleado(numeroEmpleado);
            objOrden.setNumeroMesa(icono.mesa.getNumeroMesa());
            objOrden.setFecha("CURRENT_DATE()");
            objOrden.setActiva(1);
            objOrden.INSERTAR();
            agregarMesas();
        } else {
            gdpCategorias.setDisable(false);

        }

        crearTabla(icono.mesa.CONSULTARNUMEROORDEN());

        if (!tbvDetOrden.getItems().isEmpty()) {
            cuentaButton.setDisable(false);
        }

    }

    private void crearTabla(int orden) {

        DetalleOrdenDao objDet = new DetalleOrdenDao();

        TableColumn<DetalleOrdenDao, Integer> tbcCantidad = new TableColumn<>("Cantidad");
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));

        TableColumn<DetalleOrdenDao, String> tbcNomProd = new TableColumn<>("Producto");
        tbcNomProd.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        TableColumn<DetalleOrdenDao, Float> tbcPrecio = new TableColumn<>("Precio unitario");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));

        if (tbvDetOrden.getColumns().isEmpty()) {
            tbvDetOrden.getColumns().addAll(tbcCantidad, tbcNomProd, tbcPrecio);
            lblOrden.setAlignment(Pos.CENTER);
            lblOrden.setBackground(Background.fill(Color.WHITE));
            vbOrden.setPrefWidth(300);
            vbOrden.getChildren().addAll(lblOrden, tbvDetOrden, hbTotal);
        }
        if (tbvDetOrden.getItems().isEmpty()) {
            tbvDetOrden.setItems(objDet.CONSULTARORDEN(orden));
        } else {

            tbvDetOrden.getItems().removeAll();
            tbvDetOrden.setItems(objDet.CONSULTARORDEN(orden));


        }
        lblOrden.setText("Orden: " + orden);
        tbvDetOrden.refresh();
    }

    private void BotonCategorias(int idCate) {
        listaProductos = new ArrayList<>();
        gdpProducto.getChildren().clear();
        gdpProducto.setPrefSize(200, 100);
        ProductosDao prod = new ProductosDao();
        if (listaProductos.size() > 0) {
            listaProductos.clear();
        }
        listaProductos = prod.CONSULTARPORCATEGORIA(idCate);
        int h = 0;
        int v = 0;
        if (listaProductos.size() % 4 == 0) {
            h = listaProductos.size() / 2;
            v = 2;
        } else if (listaProductos.size() % 3 == 0) {
            h = listaProductos.size() / 3;
            v = 3;
        } else if (listaProductos.size() % 2 == 0) {
            h = listaProductos.size() / 2;
            v = 2;
        } else {
            h = listaProductos.size();
            v = 1;
        }

        gdpProducto.setHgap(h);
        gdpProducto.setVgap(v);
        int cont = 0;
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < h; j++) {

                IconoProducto icnProducto = new IconoProducto();
                icnProducto.producto = listaProductos.get(cont);
                Image imgProd = new Image(getClass().getResourceAsStream("/" + listaProductos.get(cont).getDirImagen()));
                ImageView imvProd = new ImageView(imgProd);
                imvProd.setFitWidth(80);
                imvProd.setFitHeight(80);

                icnProducto.Boton = new Button();
                icnProducto.Boton.setGraphic(imvProd);
                icnProducto.Boton.setPrefSize(90, 90);
                gdpProducto.add(icnProducto.Boton, j, i);

                icnProducto.Boton.setOnAction(actionEvent -> BotonProducto(icnProducto));
                cont++;
            }
        }
        gdpProducto.setVisible(true);
    }

    private void BotonProducto(IconoProducto icnProd) {
        cantidad = 1;
        txtCantidad.setText(cantidad+"");
        btnMenos.setDisable(true);
        btnAgregar.setDisable(false);
        btnAgregar.setOnAction(actionEvent -> agregarProducto(icnProd));

    }

    private void agregarProducto(IconoProducto icnProd) {
        DetalleOrdenDao objDet = new DetalleOrdenDao();
        if (cuentaButton.isDisabled()) {
            cuentaButton.setDisable(false);
        }
        objDet.setIdProducto(icnProd.producto.getIdProducto());
        objDet.setCantidad(Integer.parseInt(txtCantidad.getText()));
        objDet.setIdOrden(ordenActual);
        objDet.setPrecio(icnProd.producto.getPrecio());
        objDet.setNombreProducto(icnProd.producto.getNombreProducto());
        objDet.INSERTAR();
        tbvDetOrden.getItems().removeAll();
        tbvDetOrden.setItems(objDet.CONSULTARORDEN(ordenActual));
        tbvDetOrden.refresh();
        objDet.setIdOrden(ordenActual);
        totalCuenta = objDet.TotalizarOrden();
        txtTotal.setText(totalCuenta + "");
        btnAgregar.setDisable(true);
    }

    private void EstiloMesas(IconoMesa icnMesa) {
        if (icnMesa.mesa.getOcupada() == 1) {
            estilomesa = "-fx-background-color: red;";
        } else {
            estilomesa = "-fx-background-color: green;";
        }
        icnMesa.Boton.setStyle(estilomesa);
    }


    private void RestarProducto() {
        cantidad--;
        txtCantidad.setText(String.valueOf(cantidad));
        if (cantidad < 2) {
            btnMenos.setDisable(true);
        }

    }

    private void SumarProducto() {
        if (cantidad > 0) {
            btnMenos.setDisable(false);
        }
        cantidad++;
        txtCantidad.setText(String.valueOf(cantidad));
    }


}

class IconoMesa{
    public Button Boton;
    public MesasDao mesa;

    public Button getBoton() {
        return Boton;
    }

    public void setBoton(Button boton) {
        Boton = boton;
    }

    public MesasDao getMesa() {
        return mesa;
    }

    public void setMesa(MesasDao mesa) {
        this.mesa = mesa;
    }
}

class IconoCategorias{
    public CategoriasDao categoriasDao;
    public Button boton;

    public CategoriasDao getCategoriasDao() {
        return categoriasDao;
    }

    public void setCategoriasDao(CategoriasDao categoriasDao) {
        this.categoriasDao = categoriasDao;
    }

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }
}

class IconoProducto{
    public Button Boton;
    public ProductosDao producto;

    public Button getBoton() {
        return Boton;
    }

    public void setBoton(Button boton) {
        Boton = boton;
    }

    public ProductosDao getProducto() {
        return producto;
    }

    public void setProducto(ProductosDao producto) {
        this.producto = producto;
    }
}


