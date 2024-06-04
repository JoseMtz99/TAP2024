package com.example.tap2024.componentes;

import com.example.tap2024.modelos.ProductosDao;
import com.example.tap2024.vistas.ProductosForm;

import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCellP extends TableCell<ProductosDao,String> {
    Button btnCelda;
    int opc;
    ProductosDao objPro;

    public ButtonCellP(int opc){
        this.opc=opc;
        String txtButton = (opc==1)? "Editar": "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc) {
        TableView<ProductosDao> tbvProductos = ButtonCellP.this.getTableView();
        objPro = tbvProductos.getItems().get(ButtonCellP.this.getIndex());
        if (opc == 1) {
            new ProductosForm(tbvProductos,objPro);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setHeaderText("Alerta!!!!!!!!!!!!!!!!!!");
            alert.setContentText("¿Desea Eliminar el Producto" + objPro.getNombreProducto()+ "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                objPro.ELIMINAR();
                tbvProductos.setItems(objPro.CONSULTARVISTA());
                tbvProductos.refresh();
            }
        }
    }

    protected void updateItem(String item, boolean empty){
        super.updateItem(item , empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}
