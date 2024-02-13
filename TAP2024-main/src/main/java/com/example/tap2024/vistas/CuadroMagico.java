package com.example.tap2024.vistas;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.nio.Buffer;

public class CuadroMagico extends Stage {
    private Scene escena;

    public CuadroMagico(){
        this.setTitle("Cuadro Magico");
        this.setScene(new Scene(new Button("Da click")));
        this.show();
    }

    private void CrearUI(){
        escena = new  Scene(new Button("Da click"));

    }
}
