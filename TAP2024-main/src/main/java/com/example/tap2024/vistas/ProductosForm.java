package com.example.tap2024.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductosForm extends Stage {
    private Scene escena;
    private TextField[] arrTxtCampos = new TextField[1];
    private Button btnGuardar;
    private VBox vbPrincipal;
    String [] arrPromts = new String[]{"Nombre del producto","Direccion de la imagen"};
    private com.example.tap2024.vistas.ProductosDao objPro;
    private TableView<com.example.tap2024.vistas.ProductosDao> tbvProductos;


    public ProductosForm(TableView<com.example.tap2024.vistas.ProductosDao> tbvProductos, com.example.tap2024.vistas.ProductosDao objPro) {
        this.tbvProductos = tbvProductos;
        this.objPro=(objPro==null)? new com.example.tap2024.vistas.ProductosDao():objPro;
        CrearUI();
        this.setTitle("Inserta categoria");
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
        btnGuardar.setOnAction(actionEvent -> GuardarProducto());
        vbPrincipal.getChildren().add(btnGuardar);
        escena= new Scene(vbPrincipal,300,250);
    }

    private void LlenarForm() {
        arrTxtCampos[0].setText(objPro.getNombreProducto());
        arrTxtCampos[1].setText(objPro.getDirImagen());
    }

    private void GuardarProducto() {
        objPro.setNombreProducto(arrTxtCampos[0].getText());
        objPro.setDirImagen(arrTxtCampos[1].getText());

        if (objPro.getIdProducto()>0){
            objPro.ACTUALIZAR();
        }else objPro.INSERTAR();

        tbvProductos.setItems(objPro.CONSULTAR());
        tbvProductos.refresh();

        arrTxtCampos[0].clear();
        arrTxtCampos[1].clear();

    }

}
