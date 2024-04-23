package com.example.tap2024.vistas;

import com.example.tap2024.modelos.CategoriasDao;
import com.example.tap2024.modelos.MesasDao;
import com.example.tap2024.modelos.ProductosDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AppTaqueria extends Stage {
    private Scene escena;
    private HBox hbox, hbAgregar;
    private VBox vboxBotones, vboxMesas;
    private Button btnMas,btnMenos,btnAgregar, btnadmin, cuentaButton, tomarOrdenButton, regresarButton;
    private GridPane gridMesas, gdpCategorias, gdpProducto;
    private String estiloBotones,estilomesa;
    private TextField txtCantidad;
    private Label lblCantidad;
    private int cantidad;
    private IconoMesa icnMesa;
    private IconoCategorias icnCategorias;
    private Image imgFondo;
    private BackgroundImage backgroundImage;
    private Background background;
    private ArrayList<ProductosDao> listaProductos;

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
        backgroundImage = new BackgroundImage(imgFondo,null,null,null,null);


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


        tomarOrdenButton.setOnAction(event -> BotonOrden());
        cuentaButton.setOnAction(event -> botonPresionado("Cuenta"));
        btnadmin.setOnAction(actionEvent -> new VistaAdmon());
        regresarButton.setOnAction(event -> this.close());


        vboxBotones.getChildren().addAll(tomarOrdenButton, cuentaButton, btnadmin,  regresarButton);


        vboxMesas = new VBox(10);
        vboxMesas.setAlignment(Pos.TOP_CENTER); // Alineación superior

        MesasDao mesa = new MesasDao();
        ArrayList<MesasDao> listaMesas = new ArrayList<>();
        listaMesas = mesa.CONSULTAR();

        int h=10;
        int v=10;

        gridMesas = new GridPane();
        if (listaMesas.size()%4==0) {
            v=listaMesas.size()/4;
            h=4;
        } else if (listaMesas.size()%3==0) {
            v=listaMesas.size()/3;
            h=3;
        } else if (listaMesas.size()%2==0) {
            v=listaMesas.size()/2;
            h=2;
        }

        gridMesas.setHgap(h);
        gridMesas.setVgap(v);

        for (int i = 0; i < listaMesas.size(); i++) {

            IconoMesa icnMesa = new IconoMesa();
            icnMesa.mesa=listaMesas.get(i);

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
        gdpCategorias= new GridPane();
        gdpCategorias.setPrefSize(200,100);
        CategoriasDao cat = new CategoriasDao();
        ArrayList<CategoriasDao> listaCategorias = new ArrayList<>();
        listaCategorias = cat.CONSULTAR();

        h=listaCategorias.size()/2;
        v=2;

        gdpCategorias.setHgap(h);
        gdpCategorias.setVgap(v);
        int cont=0;
        for (int i = 0; i <v ; i++) {
            for (int j = 0; j < h; j++) {
                IconoCategorias icnCategorias = new IconoCategorias();
                icnCategorias.categoriasDao=listaCategorias.get(cont);
                Image imgCat = new Image(getClass().getResourceAsStream("/"+listaCategorias.get(cont).getDirImagen()));
                ImageView imvCat = new ImageView(imgCat);
                imvCat.setFitWidth(80);
                imvCat.setFitHeight(80);

                icnCategorias.boton = new Button();
                icnCategorias.boton.setGraphic(imvCat);
                icnCategorias.boton.setPrefSize(90, 90);
                gdpCategorias.add(icnCategorias.boton,j,i);

                icnCategorias.boton.setOnAction(actionEvent ->BotonCategorias(icnCategorias.categoriasDao.getIdCategoria()));

                cont++;

            }

        }
        gdpCategorias.setDisable(true);
        gdpProducto= new GridPane();

        btnMenos= new Button("-");
        btnMenos.setDisable(true);

        btnMas=new Button("+");
        btnAgregar=new Button("Agregar");
        lblCantidad= new Label("Cantidad");

        txtCantidad=new TextField();
        cantidad=0;
        txtCantidad.setText(String.valueOf(cantidad));
        txtCantidad.setEditable(false);
        hbAgregar= new HBox(btnMenos,btnMas,lblCantidad,txtCantidad,btnAgregar);

        vboxMesas.getChildren().addAll(gridMesas, gdpCategorias, gdpProducto, hbAgregar);
        background = new Background(backgroundImage);
        hbox.setBackground(background);
        hbox.getChildren().addAll(vboxBotones, vboxMesas);


        btnMas.setOnAction(actionEvent -> SumarProducto());
        btnMenos.setOnAction(actionEvent -> RestarProducto());

        if (!admin){
           btnadmin.setDisable(true);
        }

    }

    private void BotonOrden() {
        gdpCategorias.setDisable(false);
    }

    private void BotonCategorias(int idCate) {
        System.out.println(idCate);
        listaProductos=new ArrayList<>();
        gdpProducto.getChildren().clear();
        gdpProducto.setPrefSize(200,100);
        ProductosDao prod = new ProductosDao();
        if (listaProductos.size()>0){
            listaProductos.clear();
        }
        listaProductos = prod.CONSULTARPORCATEGORIA(idCate);
        int h=0;
        int v=0;
        if (listaProductos.size()%4==0) {
            h=listaProductos.size()/2;
            v=2;
        } else if (listaProductos.size()%3==0) {
            h= listaProductos.size()/3;
            v=3;
        }else if (listaProductos.size()%2==0) {
            h=listaProductos.size()/2;
            v=2;
        }else{
            h=listaProductos.size();
            v=1;
        }

        gdpProducto.setHgap(h);
        gdpProducto.setVgap(v);
        int cont=0;
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < h; j++) {
                System.out.println(listaProductos.get(cont).getNombreProducto());
                IconoProducto icnProducto = new IconoProducto();
                icnProducto.producto=listaProductos.get(cont);
                System.out.println(listaProductos.get(cont).getDirImagen());
                Image imgProd = new Image(getClass().getResourceAsStream("/"+listaProductos.get(cont).getDirImagen()));
                ImageView imvProd = new ImageView(imgProd);
                imvProd.setFitWidth(80);
                imvProd.setFitHeight(80);

                icnProducto.Boton = new Button();
                icnProducto.Boton.setGraphic(imvProd);
                icnProducto.Boton.setPrefSize(90, 90);
                gdpProducto.add(icnProducto.Boton,j,i);

                icnProducto.Boton.setOnAction(actionEvent -> BotonProducto(icnProducto));
                cont++;
            }
        }
        gdpProducto.setVisible(true);
    }

    private void BotonProducto(IconoProducto icnProd) {
    }

    private void EstiloMesas(IconoMesa icnMesa) {
        if (icnMesa.mesa.getOcupada()==1){
            estilomesa = "-fx-background-color: red;";
        }else {
            estilomesa = "-fx-background-color: green;";
        }
        icnMesa.Boton.setStyle(estilomesa);
    }

    private void BotonMesa(IconoMesa icono) {
        gdpCategorias.setDisable(true);
        gdpProducto.setVisible(false);
        if (icono.mesa.getOcupada()==0){
            tomarOrdenButton.setText("Tomar orden");
            tomarOrdenButton.setDisable(false);
        }else{
            tomarOrdenButton.setText("Modificar orden");
            tomarOrdenButton.setDisable(false);
        }


    }

    private void RestarProducto() {
        cantidad--;
        txtCantidad.setText(String.valueOf(cantidad));
        if (cantidad<1){
            btnMenos.setDisable(true);
        }

    }

    private void SumarProducto() {
        if (cantidad<1){
            btnMenos.setDisable(false);
        }
        cantidad++;
        txtCantidad.setText(String.valueOf(cantidad));
    }


    private void botonPresionado(String opcion) {
        System.out.println("Botón presionado: " + opcion);
        if (opcion.equals("Empleados")) {
            EmpleadoTaqueria empleadoTaqueria = new EmpleadoTaqueria();
            empleadoTaqueria.show();
        }
     
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


