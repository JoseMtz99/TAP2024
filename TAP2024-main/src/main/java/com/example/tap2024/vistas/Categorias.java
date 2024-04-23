package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCell;
import com.example.tap2024.modelos.CategoriasDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class Categorias extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bdpPrincipal;
    private ToolBar tlbOpciones;
    private TableView<CategoriasDao> tbvCategorias;
    private Scene escena;
    private Button btnAgregarCategoria;


    public Categorias(){
        CrearUi();
        this.setTitle("Categorias de Comida");
        this.setScene(escena);
        this.show();
    }
    private void CrearUi() {
        ImageView imvCategoria= new ImageView(getClass().getResource("/Images/newEmployee.png").toString());
        imvCategoria.setFitHeight(50);
        imvCategoria.setFitWidth(50);
        btnAgregarCategoria= new Button();
        btnAgregarCategoria.setGraphic(imvCategoria);
        btnAgregarCategoria.setOnAction(actionEvent -> new CategoriasForm(tbvCategorias, null));
        tlbOpciones=new ToolBar(btnAgregarCategoria);
        CreateTable();
        bdpPrincipal=new BorderPane();//tlbOpciones,tbvEmpleados
        bdpPrincipal.setTop(tlbOpciones);
        bdpPrincipal.setCenter(tbvCategorias);
        pnlPrincipal = new Panel("Menu del Empleado");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bdpPrincipal);
        escena = new Scene(pnlPrincipal, 600, 500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CreateTable() {
        CategoriasDao objCat = new CategoriasDao();
        tbvCategorias = new TableView<CategoriasDao>();

        TableColumn<CategoriasDao,String> tbcNomCat = new TableColumn<>("Categorias");
        tbcNomCat.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));

        TableColumn<CategoriasDao,String> tbcDirImg = new TableColumn<>("imagen de la categoria");
        tbcDirImg.setCellValueFactory(new PropertyValueFactory<>("dirImagen"));

        TableColumn<CategoriasDao,String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDao, String>, TableCell<CategoriasDao, String>>() {
                    @Override
                    public TableCell<CategoriasDao, String> call(TableColumn<CategoriasDao, String> categoriasDaoStringTableColumn) {
                        return new ButtonCell(1);
                    }
                }
        );

        TableColumn<CategoriasDao,String> tbcBorrar = new TableColumn<>("Eliminar");
        tbcBorrar.setCellFactory(
                new Callback<TableColumn<CategoriasDao, String>, TableCell<CategoriasDao, String>>() {
                    @Override
                    public TableCell<CategoriasDao, String> call(TableColumn<CategoriasDao, String> categoriasDaoStringTableColumn) {
                        return new ButtonCell(2);
                    }
                }
        );

        tbvCategorias.getColumns().addAll(tbcEditar,tbcDirImg,tbcBorrar,tbcNomCat);
        tbvCategorias.setItems(objCat.CONSULTAR());
    }
}
