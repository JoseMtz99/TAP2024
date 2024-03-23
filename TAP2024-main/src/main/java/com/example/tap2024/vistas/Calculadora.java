package com.example.tap2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Scene escena;
    private VBox vContenedor;
    private HBox hbPrincipal;
    private GridPane gdpTeclado;
    private TextField txtPantalla;
    private Button btnClear, btnC;
    private Button[][] arBotones = new Button[4][4];
    private char[] arEtiquetas = {'7','8','9','/','4','5','6','*','1','2','3','-','0','.','=','+'};

    double numero1, numero2, resultado;
    boolean enOperacion, error, infinito;
    char operacion;

    public Calculadora(){
        CrearUI();
        this.setTitle("Mi primer calculadora :) ");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        enOperacion=false;
        error=false;
        infinito=false;
        btnClear = new Button("CE");
        btnC = new Button("C");
        btnClear.setOnAction(actionEvent -> resetearCalculadora());
        btnC.setOnAction(actionEvent -> limpiarPantalla());
        txtPantalla = new TextField("0");
        txtPantalla.setMaxWidth(125);
        txtPantalla.setEditable(false);
        hbPrincipal = new HBox(txtPantalla,btnClear,btnC);
        gdpTeclado = new GridPane();
        CrearTeclado();
        vContenedor = new VBox(hbPrincipal, gdpTeclado);
        vContenedor.setSpacing(5);
        escena=new Scene(vContenedor,200,200);
        escena.getStylesheets().add(getClass().getResource("/estilos/calculadora.css").toString());

    }

    private void resetearCalculadora() {
        txtPantalla.clear();
        txtPantalla.setText("0");
        enOperacion=false;
        numero1=0;
        numero2=0;
        resultado=0;
        infinito = false;
    }
    private void limpiarPantalla(){
        if (!infinito){
            txtPantalla.clear();
            txtPantalla.setText("0");
        }else txtPantalla.setText("Presiona CE para continuar");
    }

    private void CrearTeclado() {
        int pos=0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <4 ; j++) {
                arBotones[i][j] = new Button(arEtiquetas[pos]+"");
                arBotones[i][j].setPrefSize(50,50);
                gdpTeclado.add(arBotones[i][j],j,i);
                int finalPos = pos;
                arBotones[i][j].setOnAction(event-> setValue(arEtiquetas[finalPos]) );

                if (arEtiquetas[pos]=='+' || arEtiquetas[pos]=='-' ||arEtiquetas[pos]=='*' || arEtiquetas[pos]=='/' || arEtiquetas[pos]=='=')
                    arBotones[i][j].setId("color-operador");
                pos++;
            }
        }


    }

    private void setValue(char simbolo) {
        switch (simbolo){
            case '0','1','2','3','4','5','6','7','8','9':
                if(!infinito) {
                    if (Double.parseDouble(txtPantalla.getText()) == 0) {
                        if (txtPantalla.getText().charAt(txtPantalla.getText().length() - 1) == '.') {
                            txtPantalla.appendText(simbolo + "");
                        } else {
                            txtPantalla.clear();
                            txtPantalla.appendText(simbolo + "");
                        }

                    } else txtPantalla.appendText(simbolo + "");
                }else txtPantalla.setText("Presiona CE para continuar");
                break;
            case '.':
                if (!infinito) {
                    error = false;
                    for (int i = 0; i < txtPantalla.getText().length(); i++) {
                        if (txtPantalla.getText().charAt(i) == '.') {
                            error = true;
                        }
                    }
                    if (!error) txtPantalla.appendText(simbolo + "");
                }else txtPantalla.setText("Presiona CE para continuar");
                break;
            case '+', '-', '*', '/':
                if (!infinito) {
                    if (Double.parseDouble(txtPantalla.getText()) == 0) {
                        if (enOperacion) {
                            operacion = simbolo;
                        } else {
                            numero1 = Double.parseDouble(txtPantalla.getText());
                            txtPantalla.clear();
                            txtPantalla.setText("0");
                            enOperacion = true;
                            operacion = simbolo;
                        }
                    } else {
                        if (enOperacion) {
                            realizarOperacion();
                            txtPantalla.clear();
                            txtPantalla.setText("0");
                            numero1 = resultado;
                            operacion = simbolo;
                            resultado = 0;
                        } else {
                            numero1 = Double.parseDouble(txtPantalla.getText());
                            txtPantalla.clear();
                            txtPantalla.setText("0");
                            enOperacion = true;
                            operacion = simbolo;
                        }
                    }
                }else txtPantalla.setText("Presiona CE para continuar");
                break;
            case '=':
                if(!infinito) {
                    if (Double.parseDouble(txtPantalla.getText()) == 0) {
                        if (enOperacion) {
                            if (operacion=='/') {
                                txtPantalla.setText("Infinito");
                                infinito = true;
                            }else{
                                realizarOperacion();
                                txtPantalla.clear();
                                txtPantalla.setText(String.valueOf(((Double) resultado)));
                                resultado = 0;
                                enOperacion = false;
                            }

                        }
                    } else {
                        if (enOperacion) {
                            realizarOperacion();
                            txtPantalla.clear();
                            txtPantalla.setText(String.valueOf(((Double) resultado)));
                            resultado = 0;
                            enOperacion = false;
                        } else System.out.println("Error, usa un operador y un segundo numero para realizar una operacion");//Error
                    }
                }else txtPantalla.setText("Presiona CE para continuar");
                break;


        }

    }

    private void realizarOperacion() {
        numero2=Double.parseDouble(txtPantalla.getText());
        if (operacion=='+') resultado=numero1+numero2;
        else if (operacion=='-') resultado=numero1-numero2;
        else if (operacion=='*') resultado=numero1*numero2;
        else if (operacion=='/') resultado=numero1/numero2;
        numero1=0;
        numero2=0;
    }

}
