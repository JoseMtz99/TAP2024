package com.example.tap2024.vistas;

import com.example.tap2024.componentes.ButtonCell;
import com.example.tap2024.modelos.EmpleadosDao;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class EmpleadoTaqueria extends Stage {

    private Panel pnlPrincipal;
    private BorderPane bdpPrincipal;
    private ToolBar tlbOpciones;
    private TableView<EmpleadosDao> tbvEmpleados;
    private Scene escena;
    private Button btnAgregarEmpleado;

    public EmpleadoTaqueria() {
        CrearUi();
        this.setTitle("Taquería Los inges");
        this.setScene(escena);
        this.show();
    }

    private void CrearUi() {
        ImageView imvEmpleado= new ImageView(getClass().getResource("/Images/newEmployee.png").toString());
        imvEmpleado.setFitHeight(50);
        imvEmpleado.setFitWidth(50);
        btnAgregarEmpleado= new Button();
        btnAgregarEmpleado.setGraphic(imvEmpleado);
        btnAgregarEmpleado.setOnAction(actionEvent -> new EmpleadosForm(tbvEmpleados, null));
        tlbOpciones=new ToolBar(btnAgregarEmpleado);
        CreateTable();
        bdpPrincipal=new BorderPane();//tlbOpciones,tbvEmpleados
        bdpPrincipal.setTop(tlbOpciones);
        bdpPrincipal.setCenter(tbvEmpleados);
        pnlPrincipal = new Panel("Taqueria los inges");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bdpPrincipal);
        escena = new Scene(pnlPrincipal, 600, 500);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());


    }

    private void CreateTable() {
        EmpleadosDao objEmp = new EmpleadosDao();
        tbvEmpleados=new TableView<EmpleadosDao>();
        TableColumn<EmpleadosDao,String> tbcNomEmp= new TableColumn<>("Empleado");
        tbcNomEmp.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadosDao,String> tbcRfcEmp= new TableColumn<>("RFC");
        tbcRfcEmp.setCellValueFactory(new PropertyValueFactory<>("rfcEmpleado"));

        TableColumn<EmpleadosDao,Float> tbcSalEmp= new TableColumn<>("Salario");
        tbcSalEmp.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadosDao,String> tbcTelEmp= new TableColumn<>("Telefono");
        tbcTelEmp.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadosDao,String> tbcDirEmp= new TableColumn<>("Direccion");
        tbcDirEmp.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<EmpleadosDao,String> tbcEditar = new TableColumn<EmpleadosDao,String>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<EmpleadosDao, String>, TableCell<EmpleadosDao, String>>() {
                    @Override
                    public TableCell<EmpleadosDao, String> call(TableColumn<EmpleadosDao, String> empleadosDaoStringTableColumn) {
                        return new ButtonCell(1);
                    }
                }
        );


        TableColumn<EmpleadosDao,String>  tbcBorrar = new TableColumn<EmpleadosDao,String>("Eliminar");
        tbcBorrar.setCellFactory(
                new Callback<TableColumn<EmpleadosDao, String>, TableCell<EmpleadosDao, String>>() {
                    @Override
                    public TableCell<EmpleadosDao, String> call(TableColumn<EmpleadosDao, String> empleadosDaoStringTableColumn) {
                        return new ButtonCell(2);
                    }
                }
        );



        tbvEmpleados.getColumns().addAll(tbcNomEmp,tbcRfcEmp,tbcSalEmp,tbcTelEmp,tbcDirEmp,tbcEditar,tbcBorrar);
        tbvEmpleados.setItems(objEmp.CONSULTAR());
    }
}
