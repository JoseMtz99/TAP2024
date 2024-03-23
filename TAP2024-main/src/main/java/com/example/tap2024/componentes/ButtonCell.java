package com.example.tap2024.componentes;

import com.example.tap2024.modelos.EmpleadosDao;
import com.example.tap2024.vistas.EmpleadosForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell extends TableCell<EmpleadosDao,String> {
    Button btnCelda;

    int opc;
    EmpleadosDao objEmp;

    public ButtonCell(int opc) {
        this.opc=opc;
        String txtButton = (opc==1)? "Editar" : "Eliminar";
        btnCelda=new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc) {
        TableView<EmpleadosDao> tbvEmpleados = ButtonCell.this.getTableView();
        objEmp = tbvEmpleados.getItems().get(ButtonCell.this.getIndex());
        if (opc==1){
            //Editar
            new EmpleadosForm(tbvEmpleados, objEmp);
        }else {
            //eliminar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Alerta!!!");
            alert.setContentText("¿Desea eliminar el registro del empleado "+objEmp.getNomEmpleado()+"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                objEmp.ELIMINAR();
                tbvEmpleados.setItems(objEmp.CONSULTAR());
                tbvEmpleados.refresh();
            }
        }

    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty )
            this.setGraphic(btnCelda);
    }
}
