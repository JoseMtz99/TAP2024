package com.example.tap2024.vistas;

import com.example.tap2024.modelos.MesasDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AppTaqueria extends Stage {
    private Scene escena;
    private HBox hbox, hbAgregar;
    private VBox vboxBotones, vboxMesas;
    private Button btnMas,btnMenos,btnAgregar,cocinaButton, btnadmin, cuentaButton, tomarOrdenButton, regresarButton;
    private GridPane gridMesas, gdpCategorias, gdpProducto;
    private String estiloBotones,estilomesa;
    private TextField txtCantidad;
    private Label lblCantidad;
    private int cantidad;

    public AppTaqueria(boolean administrador) {
        super();
        CrearUI(administrador);
        escena = new Scene(hbox);
        this.setTitle("AppTaqueria");
        this.setScene(escena);
        this.sizeToScene();
        this.show();
    }

    private void CrearUI(boolean admin) {
        hbox = new HBox(10);
        hbox.setPadding(new Insets(20));
        hbox.setAlignment(Pos.CENTER);


        vboxBotones = new VBox(10);
        vboxBotones.setAlignment(Pos.TOP_CENTER);


        Image imagenOrden = new Image(getClass().getResourceAsStream("/Images/orden.png"));
        ImageView imageViewOrden = new ImageView(imagenOrden);
        imageViewOrden.setFitWidth(50);
        imageViewOrden.setFitHeight(50);
        tomarOrdenButton = new Button();
        tomarOrdenButton.setGraphic(imageViewOrden);

        Image imagenCuenta = new Image(getClass().getResourceAsStream("/Images/cuenta.png"));
        ImageView imageViewCuenta = new ImageView(imagenCuenta);
        imageViewCuenta.setFitWidth(50);
        imageViewCuenta.setFitHeight(50);
        cuentaButton = new Button();
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
        cocinaButton = new Button();
        cocinaButton.setGraphic(imageViewCocina);

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
        cocinaButton.setStyle(estiloBotones);
        regresarButton.setStyle(estiloBotones);


        tomarOrdenButton.setOnAction(event -> botonPresionado("Tomar una orden"));
        cuentaButton.setOnAction(event -> botonPresionado("Cuenta"));
        btnadmin.setOnAction(actionEvent -> new VistaAdmon());
        cocinaButton.setOnAction(event -> botonPresionado("Cocina"));
        regresarButton.setOnAction(event -> this.close());


        vboxBotones.getChildren().addAll(tomarOrdenButton, cuentaButton, btnadmin, cocinaButton, regresarButton);


        vboxMesas = new VBox(10);
        vboxMesas.setAlignment(Pos.TOP_CENTER); // Alineación superior



        gridMesas = new GridPane();
        gridMesas.setHgap(10);
        gridMesas.setVgap(10);

        gdpCategorias= new GridPane();

        gdpProducto= new GridPane();

        MesasDao mesa = new MesasDao();
        ArrayList<MesasDao> listaMesas = new ArrayList<>();
        listaMesas = mesa.CONSULTAR();


        for (int i = 0; i < listaMesas.size(); i++) {

            IconoMesa icnMesa = new IconoMesa();
            icnMesa.mesa=listaMesas.get(i);

            Image imagenMesa = new Image(getClass().getResourceAsStream("/Images/mesas.png"));
            ImageView imageViewMesa = new ImageView(imagenMesa);
            imageViewMesa.setFitWidth(50);
            imageViewMesa.setFitHeight(50);

            icnMesa.Boton = new Button();
            icnMesa.Boton.setGraphic(imageViewMesa);
            icnMesa.Boton.setPrefSize(80, 80);
            gridMesas.add(icnMesa.Boton, i % 5, i / 5);

            estilomesa = "-fx-background-color: gray;";
            icnMesa.Boton.setStyle(estilomesa);

            if (icnMesa.mesa.getOcupada()==1){
                icnMesa.Boton.setDisable(true);
            }else {
                icnMesa.Boton.setDisable(false);
            }


        }

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


        hbox.getChildren().addAll(vboxBotones, vboxMesas);


        btnMas.setOnAction(actionEvent -> SumarProducto());
        btnMenos.setOnAction(actionEvent -> RestarProducto());

        if (!admin){
           btnadmin.setDisable(true);
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


