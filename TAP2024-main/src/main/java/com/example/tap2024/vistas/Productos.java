package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCell;
import com.example.tap2024.componentes.ButtonCellP;
import com.example.tap2024.modelos.CategoriasDao;
import com.example.tap2024.modelos.EmpleadosDao;
import com.example.tap2024.modelos.ProductosDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class Productos extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bdpPrincipal;
    private ToolBar tlbOpciones;
    private TableView<ProductosDao> tbvProducto;
    private Scene escena;
    private Button btnAgregarProducto;


    public Productos(){
        CrearUi();
        this.setTitle("Categorias de Productos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUi() {
        ImageView imvProductos = new ImageView(getClass().getResource("/Images/newEmployee.png").toString());
        imvProductos.setFitHeight(50);
        imvProductos.setFitWidth(50);
        btnAgregarProducto= new Button();
        btnAgregarProducto.setGraphic(imvProductos);
        btnAgregarProducto.setOnAction(actionEvent -> new ProductosForm(tbvProducto, null));
        tlbOpciones=new ToolBar(btnAgregarProducto);
        CreateTable();
        bdpPrincipal=new BorderPane();//tlbOpciones,tbvEmpleados
        bdpPrincipal.setTop(tlbOpciones);
        bdpPrincipal.setCenter(tbvProducto);
        pnlPrincipal = new Panel("Menu de Productos");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bdpPrincipal);
        escena = new Scene(pnlPrincipal, 600, 500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CreateTable() {
        ProductosDao objProductos = new ProductosDao();
        tbvProducto = new TableView<ProductosDao>();

        TableColumn<ProductosDao, String> tbcNomPro = new TableColumn<>("Introduce el Nombre");
        tbcNomPro.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        TableColumn<ProductosDao, String> tbcPrecio = new TableColumn<>("Introduce el Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductosDao, String> tbcCosto = new TableColumn<>("Introduce el Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<ProductosDao, String> tbcDirImg = new TableColumn<>("Introduce la direccion");
        tbcDirImg.setCellValueFactory(new PropertyValueFactory<>("dirImagen"));

        TableColumn<ProductosDao,String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ProductosDao, String>, TableCell<ProductosDao, String>>() {
            @Override
            public TableCell<ProductosDao, String> call(TableColumn<ProductosDao, String> empleadosDaoStringTableColumn) {
                return new ButtonCellP(1);
            }
        }
        );

        TableColumn<ProductosDao,String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ProductosDao, String>, TableCell<ProductosDao, String>>() {
                    @Override
                    public TableCell<ProductosDao, String> call(TableColumn<ProductosDao, String> empleadosDaoStringTableColumn) {
                        return new ButtonCellP(2);
                    }
                }
        );

        tbvProducto.getColumns().addAll(tbcDirImg,tbcNomPro,tbcPrecio,tbcCosto,tbcEditar,tbcEliminar);
        tbvProducto.setItems(objProductos.CONSULTARVISTA());

    }
}
