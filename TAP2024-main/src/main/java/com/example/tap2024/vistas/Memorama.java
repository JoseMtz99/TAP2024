package com.example.tap2024.vistas;

import com.example.tap2024.componentes.Hilo;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.When;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


public class Memorama extends Stage {
    private Scene escena;
    private VBox vbPrincipal, vbJugadores;
    private HBox hbPrincipal, hbSecundario, hbJugador1, hbJugador2;
    private GridPane gdpCartas;
    private Label lbNoPares, lbTimer, lbJugador1, lbJugador2;
    private TextField txtEntrada, txtTimer, txtPuntuacion1, txtPuntuacion2;
    private Button btnRevolver, btnJugar;
    private int puntuacion1, puntuacion2, paresX, paresY, entrada, segundosTemporizador, paresEncontrados;
    Alert alerta;
    private Tarjeta[][] arrBtnCarts;

    private boolean turno1;
    private Tarjeta temp1;
    Temporizador temp;

    public Memorama() {
        CrearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.show();
        this.setMaximized(true);
    }
    private void CrearUI() {
        alerta=new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("¡¡¡Alerta!!!");
        lbNoPares = new Label("Numero de pares:");
        txtEntrada = new TextField();
        txtEntrada.setMinWidth(15);
        txtEntrada.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtEntrada.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        btnRevolver = new Button("Revolver");
        btnJugar=new Button("Jugar");
        btnJugar.setVisible(false);
        btnJugar.setDisable(true);
        hbPrincipal = new HBox(lbNoPares, txtEntrada,btnRevolver,btnJugar);
        hbPrincipal.setSpacing(5);
        lbJugador1 = new Label("Jugador 1: ");
        txtPuntuacion1 = new TextField();
        txtPuntuacion1.setEditable(false);
        hbJugador1 = new HBox(lbJugador1, txtPuntuacion1);
        hbJugador1.setSpacing(5);
        lbJugador2 = new Label("Jugador 2: ");
        txtPuntuacion2 = new TextField();
        txtPuntuacion2.setEditable(false);
        hbJugador2 = new HBox(lbJugador2,txtPuntuacion2);
        hbJugador2.setSpacing(5);
        vbJugadores = new VBox(hbJugador1,hbJugador2);
        vbJugadores.setSpacing(5);
        gdpCartas = new GridPane();
        gdpCartas.setMinSize(250,250);
        lbTimer= new Label("Tiempo Restante");
        txtTimer=new TextField();
        txtTimer.setEditable(false);
        hbSecundario = new HBox(vbJugadores,lbTimer,txtTimer);
        hbSecundario.setSpacing(5);
        vbPrincipal = new VBox(hbPrincipal,gdpCartas,hbSecundario);
        vbPrincipal.setSpacing(5);
        escena = new Scene(vbPrincipal,400,200);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        btnJugar.getStyleClass().setAll("btn-success","btn");
        btnRevolver.getStyleClass().setAll("btn","btn-primary");


        btnRevolver.setOnAction(actionEvent -> RevolverCarts());
        btnJugar.setOnAction(actionEvent -> {
            try {
                Jugar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
    private void RevolverCarts() {
        if (!txtEntrada.getText().isEmpty()) {
            String[] arrImagenes = {"ElArbol.jpg", "ElBarril.jpg", "ElCatrin.jpg", "ElDiablo.jpg", "ElGallo.jpg", "ElMelon.jpg", "ElParaguas.jpg", "ElPescado.jpg", "ElValiente.jpg", "LaBotella.jpg", "LaDama.jpg", "LaEscalera.jpg", "LaPalma.jpg", "LaRana.jpg", "LaSirena.jpg"};
            entrada = Integer.parseInt(txtEntrada.getText());
            if (entrada < arrImagenes.length + 1 && entrada > 2) {
                btnJugar.setVisible(true);
                btnJugar.setDisable(false);
                puntuacion1 = 0;
                puntuacion2 = 0;
                txtPuntuacion1.setText(puntuacion1 + "");
                txtPuntuacion2.setText(puntuacion2 + "");
                if ((entrada * 2) % 4 == 0) {
                    paresX = 4;
                    paresY = entrada / 2;
                } else if ((entrada * 2) % 3 == 0) {
                    paresX = 3;
                    paresY = (entrada / 3) * 2;
                } else {
                    paresX = 2;
                    paresY = entrada;
                }
                arrBtnCarts = new Tarjeta[paresX][paresY];
                ImageView imvCarta;
                int posX = 0;
                int posY = 0;
                int cont = 0;
                gdpCartas.getChildren().clear();
                for (int i = 0; i < entrada; ) {
                    posX = (int) (Math.random() * paresX);
                    posY = (int) (Math.random() * paresY);
                    if (arrBtnCarts[posX][posY] == null) {
                        Button btnTemp = new Button();
                        ImageView imvTemp = new ImageView(getClass().getResource("/Images/ParteTrasera.jpg").toString());
                        arrBtnCarts[posX][posY] = new Tarjeta();
                        arrBtnCarts[posX][posY].setBoton(btnTemp);
                        imvCarta = new ImageView(getClass().getResource("/Images/" + arrImagenes[i]).toString());
                        imvCarta.setFitHeight(100);
                        imvCarta.setFitWidth(75);
                        imvTemp.setFitHeight(100);
                        imvTemp.setFitWidth(75);
                        arrBtnCarts[posX][posY].setCara1(imvCarta);
                        arrBtnCarts[posX][posY].setCara2(imvTemp);
                        arrBtnCarts[posX][posY].boton.setPrefSize(100, 150);
                        arrBtnCarts[posX][posY].boton.setGraphic(arrBtnCarts[posX][posY].cara1);
                        arrBtnCarts[posX][posY].idTarjeta = i;
                        gdpCartas.add(arrBtnCarts[posX][posY].boton, posY, posX);
                        cont++;

                        if (cont == 2) {
                            i++;
                            cont = 0;
                        }
                    }
                }

            } else {
                alerta.setContentText("Error, Solo acepto numeros desde 3 a 15");
                alerta.showAndWait();
            }

        } else {
            alerta.setContentText("Error, primero escribe un numero");
            alerta.showAndWait();
        }
    }
    private void Jugar() throws InterruptedException {

        paresEncontrados=0;
        turno1 = true;
        segundosTemporizador=10;
        temp= new Temporizador();
        temp.setSegundos(segundosTemporizador);
        temp.setTxtSegundos(txtTimer);
        temp.setRunning(true);
        temp.start();
        lbJugador1.getStyleClass().addAll("lbl-success");
        lbJugador2.getStyleClass().addAll("lbl-danger");
        txtPuntuacion1.getStyleClass().addAll("bg-success");
        txtPuntuacion2.getStyleClass().addAll("bg-danger");
        btnJugar.setDisable(true);
        gdpCartas.getChildren().clear();
        VoltearCartas();


    }



    private void VoltearCartas() {
        for (int i = 0; i < arrBtnCarts.length; i++) {
            for (int j = 0; j < arrBtnCarts[0].length; j++) {
                arrBtnCarts[i][j].boton.setGraphic(arrBtnCarts[i][j].cara2);
                gdpCartas.add(arrBtnCarts[i][j].boton,j,i);
                int finalI = i;
                int finalJ = j;
                arrBtnCarts[i][j].boton.setOnAction(actionEvent -> GirarCarta(arrBtnCarts[finalI][finalJ]));
            }

        }
    }
    private void GirarCarta(Tarjeta tarjeta) {
        if (temp1==null){
            tarjeta.boton.setDisable(true);
            temp1 = new Tarjeta();
            temp1=tarjeta;
            temp1.idTarjeta=tarjeta.idTarjeta;
            tarjeta.boton.setGraphic(tarjeta.cara1);
        } else {
            segundosTemporizador=10;
            temp.setSegundos(segundosTemporizador);
            tarjeta.boton.setGraphic(tarjeta.cara1);
            tarjeta.boton.setDisable(true);

            if (temp1.idTarjeta==tarjeta.idTarjeta){
                if (turno1){
                    puntuacion1++;
                    txtPuntuacion1.setText(puntuacion1+"");
                }else{
                    puntuacion2++;
                    txtPuntuacion2.setText(puntuacion2+"");
                }
                paresEncontrados++;
            }else {
                tarjeta.boton.setDisable(false);
                temp1.boton.setDisable(false);

                tarjeta.boton.setGraphic(tarjeta.cara2);
                temp1.boton.setGraphic(temp1.cara2);

                turno1=temp.CambiarTurno(turno1);
                if (turno1){
                    lbJugador1.getStyleClass().clear();
                    lbJugador2.getStyleClass().clear();
                    txtPuntuacion1.getStyleClass().clear();
                    txtPuntuacion2.getStyleClass().clear();
                    lbJugador1.getStyleClass().addAll("lbl-success","lbl-lg");
                    lbJugador2.getStyleClass().addAll("lbl-danger");
                    txtPuntuacion1.getStyleClass().addAll("bg-success");
                    txtPuntuacion2.getStyleClass().addAll("bg-danger");
                }else{
                    lbJugador1.getStyleClass().clear();
                    lbJugador2.getStyleClass().clear();
                    txtPuntuacion1.getStyleClass().clear();
                    txtPuntuacion2.getStyleClass().clear();
                    lbJugador2.getStyleClass().addAll("lbl-success","lbl-lg");
                    lbJugador1.getStyleClass().addAll("lbl-danger");
                    txtPuntuacion2.getStyleClass().addAll("bg-success");
                    txtPuntuacion1.getStyleClass().addAll("bg-danger");
                }

            }
            temp1=null;

        }



        if (paresEncontrados==entrada){
            temp.setRunning(false);
            Alert alertaGanador = new Alert(Alert.AlertType.INFORMATION);
            alertaGanador.setTitle("Mensaje del sistema");
            alertaGanador.setHeaderText("Felicidades");
            if (puntuacion1>puntuacion2){
                alertaGanador.setContentText("El ganador es: Jugador 1");
            }else if(puntuacion1<puntuacion2)alertaGanador.setContentText("El ganador es: Jugador 2");
            else alertaGanador.setContentText("Empate");
            Optional<ButtonType> result = alertaGanador.showAndWait();
        }
    }

}

class Tarjeta {
    public Button boton;
    public ImageView cara1;
    public ImageView cara2;
    public int idTarjeta;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

    public ImageView getCara1() {
        return cara1;
    }

    public void setCara1(ImageView cara1) {
        this.cara1 = cara1;
    }

    public ImageView getCara2() {
        return cara2;
    }

    public void setCara2(ImageView cara2) {
        this.cara2 = cara2;
    }


}





class Temporizador extends Thread {
    private TextField txtSegundos;
    private int segundos;
    private boolean running;

     public void setRunning(boolean running) {
         this.running = running;
     }
     public void setTxtSegundos(TextField txtSegundos) {
         this.txtSegundos = txtSegundos;
     }
     public void setSegundos(int segundos) {
         this.segundos = segundos;
     }
     public boolean CambiarTurno(boolean turno){
         if (turno){
             turno=false;
         }else{
             turno = true;
         }
         return turno;

     }

     @Override
    public void run() {
         while (segundos > -1 && running) {
             segundos--;
             txtSegundos.setText(segundos + "");
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
             if (segundos == 0) {

                 segundos = 10;
             }
         }
    }


}