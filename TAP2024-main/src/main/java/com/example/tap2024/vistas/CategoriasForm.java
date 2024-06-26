package com.example.tap2024.vistas;

import com.example.tap2024.modelos.CategoriasDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoriasForm extends Stage {
    private Scene escena;
    private TextField[] arrTxtCampos = new TextField[1];
    private Button btnGuardar;
    private VBox vbPrincipal;
    String [] arrPromts = new String[]{"Nombre de Categoria","Direccion de la imagen"};
    private CategoriasDao objCat;
    private TableView<CategoriasDao> tbvCategorias;


    public CategoriasForm(TableView<CategoriasDao> tbvCategorias, CategoriasDao objCat) {
        this.tbvCategorias = tbvCategorias;
        this.objCat=(objCat==null)? new CategoriasDao():objCat;
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
        btnGuardar.setOnAction(actionEvent -> GuardarEmpleado());
        vbPrincipal.getChildren().add(btnGuardar);
        escena= new Scene(vbPrincipal,300,250);

    }
    private void LlenarForm() {
        arrTxtCampos[0].setText(objCat.getNombreCategoria());
        arrTxtCampos[1].setText(objCat.getDirImagen());
    }

    private void GuardarEmpleado() {
        objCat.setNombreCategoria(arrTxtCampos[0].getText());
        objCat.setDirImagen(arrTxtCampos[1].getText());


        if (objCat.getIdCategoria()>0){
            objCat.ACTUALIZAR();
        }else objCat.INSERTAR();

        tbvCategorias.setItems(objCat.CONSULTARVISTA());
        tbvCategorias.refresh();

        arrTxtCampos[0].clear();
        arrTxtCampos[1].clear();

    }
}
