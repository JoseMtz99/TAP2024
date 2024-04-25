package com.example.tap2024.componentes;

import com.example.tap2024.modelos.CategoriasDao;
import com.example.tap2024.vistas.CategoriasForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell2 extends TableCell<CategoriasDao, String> {
    Button btnCelda;
    int opc;
    CategoriasDao objCat;

    public ButtonCell2(int opc){
        this.opc=opc;
        String txtButton = (opc == 1)? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBton(opc));
    }

    private void AccionBton(int opc) {
        TableView<CategoriasDao> tbvCategorias = ButtonCell2.this.getTableView();
        objCat = tbvCategorias.getItems().get(ButtonCell2.this.getIndex());
        if (opc == 1) {
            new CategoriasForm(tbvCategorias,objCat);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistemas");
            alert.setHeaderText("Alerta!!!!!!!!!!!!!!!!!!!!");
            alert.setContentText("¿Deseas Eliminar la Categoria?"+objCat.getNombreCategoria()+"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                objCat.ELIMINAR();
                tbvCategorias.setItems(objCat.CONSULTARVISTA());
                tbvCategorias.refresh();
            }
        }
    }

    protected void updateItem(String item,boolean emty){
        super.updateItem(item, emty);
        if (!emty)
            this.setGraphic(btnCelda);
    }
}
