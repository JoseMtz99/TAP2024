package com.example.tap2024.vistas;

import com.example.tap2024.componentes.HiloImpresora;
import com.example.tap2024.modelos.ArchivoImpresora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;


public class Impresora extends Stage {
    Scene escena;
    int numArchivo;
    TableView<ArchivoImpresora> tabla;
    VBox vbPrincipal;
    HBox hbPrincipal;
    Button btnAgregarTarea, btnPausa;
    Label lblProgreso;
    private ProgressBar pgbImpresion;
    private ObservableList<ArchivoImpresora> datos;
    private HiloImpresora hilo;
    TableColumn<ArchivoImpresora,String> numeroArchivo;
    TableColumn<ArchivoImpresora,String> nombreArchivo;
    TableColumn<ArchivoImpresora,Integer> numeroHojas;
    TableColumn<ArchivoImpresora,String> hora;


    public Impresora() {
        crearUI();
        this.setTitle("Impresora");
        this.setScene(escena);
        this.show();
    }

    private void crearUI(){
        datos = FXCollections.observableArrayList();
        numArchivo = 1;
        tabla= new TableView<>();
        crearTabla();
        lblProgreso = new Label("Progreso");
        btnAgregarTarea = new javafx.scene.control.Button("Agregar tarea");
        btnAgregarTarea.setOnAction(actionEvent -> {agregarTarea();});
        pgbImpresion = new ProgressBar();
        pgbImpresion.setProgress(0);
        btnPausa = new javafx.scene.control.Button("Pausar");
        btnPausa.setOnAction(actionEvent -> {
            try {
                botonPausa();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        hbPrincipal= new HBox(btnAgregarTarea, lblProgreso, pgbImpresion, btnPausa);
        hbPrincipal.setSpacing(15);
        vbPrincipal = new VBox(tabla, hbPrincipal);
        vbPrincipal.setSpacing(15);
        escena= new Scene(vbPrincipal,530,500);
        iniciarHilo();

    }

    private void botonPausa() throws InterruptedException {
        if (hilo.isEsperar()){
            hilo.setEsperar(false);
            btnPausa.setText("Pausar");
        }else{
            hilo.setEsperar(true);
            btnPausa.setText("Reanudar");

        }
    }


    private void crearTabla(){
        tabla.setEditable(true);
        numeroArchivo = new TableColumn<>("Numero archivo");
        numeroArchivo.setPrefWidth(100);
        numeroArchivo.setCellValueFactory(new PropertyValueFactory<>("numeroArchivo"));
        nombreArchivo = new TableColumn<>("Nombre Archivo");
        nombreArchivo.setPrefWidth(250);
        nombreArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));
        numeroHojas = new TableColumn<>("Numero hojas");
        numeroHojas.setPrefWidth(100);
        numeroHojas.setCellValueFactory(new PropertyValueFactory<>("numeroHojas"));
        hora = new TableColumn<>("Hora");
        hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tabla.getColumns().addAll(numeroArchivo, nombreArchivo, numeroHojas, hora);

    }
    private void agregarTarea() {
        ArchivoImpresora archivo = new ArchivoImpresora();
        archivo.setNumeroArchivo(numArchivo);
        numArchivo++;
        archivo.setNombreArchivo("Archivo"+archivo.getNumeroArchivo()+"_"+LocalDateTime.now());
        archivo.setNumeroHojas((int) (Math.random() * 15) + 1);
        archivo.setHora(archivo.getHours()+":"+archivo.getMinutes()+":"+archivo.getSeconds());
        datos.add(archivo);
        tabla.setItems(datos);
        tabla.refresh();
    }

    private void iniciarHilo(){
        hilo= new HiloImpresora();
        hilo.setPgbProgreso(pgbImpresion);
        hilo.setCorrer(true);
        hilo.setEsperar(false);
        hilo.setTabla(tabla);
        hilo.start();
    }


}

