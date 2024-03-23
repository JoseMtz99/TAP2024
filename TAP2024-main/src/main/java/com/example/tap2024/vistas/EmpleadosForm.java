package com.example.tap2024.vistas;

import com.example.tap2024.modelos.EmpleadosDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmpleadosForm extends Stage {

    private Scene escena;
    private TextField[] arrTxtCampos = new TextField[5];
    private Button btnGuardar;
    private VBox vbPrincipal;
    String [] arrPromts = new String[]{"Nombre del empleado","RFC","Sueldo","Telefono","Direccion"};
    private EmpleadosDao objEmp;
    private TableView<EmpleadosDao> tbvEmpleados;
    public EmpleadosForm(TableView<EmpleadosDao> tbvEmp, EmpleadosDao objEmp) {
        tbvEmpleados= tbvEmp;
        this.objEmp=(objEmp==null)? new EmpleadosDao():objEmp;
        CrearUI();
        this.setTitle("Insertar Usuario");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vbPrincipal=new VBox();
        vbPrincipal.setSpacing(10);
        vbPrincipal.setAlignment(Pos.CENTER);
        vbPrincipal.setPadding(new Insets(10));
        for (int i = 0; i < arrTxtCampos.length; i++) {
            arrTxtCampos[i]= new TextField();
            arrTxtCampos[i].setPromptText(arrPromts[i]);
            vbPrincipal.getChildren().add(arrTxtCampos[i]);
        }
        LlenarForm();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarEmpleado());
        vbPrincipal.getChildren().add(btnGuardar);
        escena= new Scene(vbPrincipal,300,250);

    }

    private void LlenarForm() {
        arrTxtCampos[0].setText(objEmp.getNomEmpleado());
        arrTxtCampos[1].setText(objEmp.getRfcEmpleado());
        arrTxtCampos[2].setText((objEmp.getSalario()+""));
        arrTxtCampos[3].setText(objEmp.getTelefono());
        arrTxtCampos[4].setText(objEmp.getDireccion());

    }

    private void GuardarEmpleado() {
        objEmp.setNomEmpleado(arrTxtCampos[0].getText());
        objEmp.setRfcEmpleado(arrTxtCampos[1].getText());
        objEmp.setSalario(Float.parseFloat(arrTxtCampos[2].getText()));
        objEmp.setTelefono(arrTxtCampos[3].getText());
        objEmp.setDireccion(arrTxtCampos[4].getText());
        if (objEmp.getIdEmpleado()>0){
            objEmp.ACTUALIZAR();
        }else objEmp.INSERTAR();

        tbvEmpleados.setItems(objEmp.CONSULTAR());
        tbvEmpleados.refresh();

        arrTxtCampos[0].clear();
        arrTxtCampos[1].clear();
        arrTxtCampos[2].clear();
        arrTxtCampos[3].clear();
        arrTxtCampos[4].clear();

    }


}
