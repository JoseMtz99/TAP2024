package com.example.tap2024.vistas;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;

public class CuadroMagico extends Stage {
    private Scene escena;
    private Label lbEntrada;
    private TextField txtEntrada;
    private Button btnCrear;
    private GridPane gdpCuadro;
    private VBox vbPrincipal;
    private HBox hbPrincipal;
    int entrada, longitud, posicion, posicionSig;
    double width, height;
    RandomAccessFile archivo;


    public CuadroMagico() {
        this.setTitle("Cuadro Magico");
        this.setMaximized(true);
        width=1200;
        height=600;
        CrearUI();
        this.setScene(escena);

        this.show();
    }

    private void CrearUI() {
        txtEntrada = new TextField();
        txtEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtEntrada.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        btnCrear = new Button("Crear");
        hbPrincipal = new HBox(txtEntrada, btnCrear);
        lbEntrada = new Label("Ingresa el numero de columnas del cuadro magico: ");
        gdpCuadro = new GridPane();
        vbPrincipal = new VBox(lbEntrada, hbPrincipal,gdpCuadro);
        escena = new Scene(vbPrincipal);
        escena.getStylesheets().add(getClass().getResource("/estilos/CuadroMagico.css").toString());

        btnCrear.setOnAction(actionEvent -> {
            try {
                CrearCuadro();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void CrearCuadro() throws IOException {
        RandomAccessFile archivo = new RandomAccessFile("C:\\Users\\josec\\OneDrive\\Tecno\\IntelliJ Projects\\TAP2024-main\\TAP2024-main\\src\\main\\resources\\Files\\archivo.dat", "rw");
        entrada = Integer.parseInt(txtEntrada.getText());

        if (entrada % 4 == 0) {

        } else if (entrada % 2 == 0) {

        } else if(entrada>=3){
            CrearImpar();
            gdpCuadro.getChildren().clear();
            gdpCuadro.setGridLinesVisible(false);
            gdpCuadro.setPrefSize(width,height);
            gdpCuadro.setGridLinesVisible(true);
            int cont = 0;
            for (int i = 0; i < entrada; i++) {
                for (int j = 0; j < entrada; j++) {
                    archivo.seek(cont* 4L);
                    cont++;
                    int numero = archivo.readInt();
                    Label lbtablero = new Label(numero+"");
                    lbtablero.setPrefSize(width/entrada,height/entrada);
                    gdpCuadro.add(lbtablero,j,i);

                }
            }
        }
    }

    private void CrearImpar() {
        try (RandomAccessFile archivo = new RandomAccessFile("C:\\Users\\josec\\OneDrive\\Tecno\\IntelliJ Projects\\TAP2024-main\\TAP2024-main\\src\\main\\resources\\Files\\archivo.dat", "rw")) {
            longitud = entrada*entrada;
            posicion = Math.round(entrada / 2);
            archivo.setLength(0);
            archivo.setLength(longitud * 4L);
            for (int i = 0; i < longitud; i++) {
                archivo.seek(posicion * 4L);
                archivo.writeInt(i + 1);
                posicionSig = PosicionarDiagonal(posicion);
                archivo.seek(posicionSig * 4L);
                if (archivo.readInt() == 0) {
                    posicion = posicionSig;
                } else {
                    posicion = PosicionarAbajo(posicion);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private int PosicionarAbajo(int posicionActual) {
        if (posicionActual < (longitud - entrada)) {
            posicionActual = posicionActual + entrada;
        } else {
            posicionActual = (posicionActual - longitud) + entrada;
        }
        return posicionActual;
    }

    private int PosicionarDiagonal(int posicionActual) {
        if (posicionActual < (entrada - 1)) {
            posicionActual = posicionActual + (longitud - entrada + 1);
        } else if (posicionActual == (entrada - 1)) {
            posicionActual = posicionActual + (entrada * (entrada - 2)) + 1;
        } else if ((posicionActual + 1) % entrada == 0) {
            posicionActual = posicionActual - (2 * entrada) + 1;
        } else {
            posicionActual = (posicionActual - entrada) + 1;
        }
        return posicionActual;
    }

}

