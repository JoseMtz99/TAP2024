package com.example.tap2024.componentes;

import com.example.tap2024.modelos.ArchivoImpresora;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

public class HiloImpresora extends Thread{
    private ProgressBar pgbProgreso;
    private double tiempo;
    private boolean correr, esperar;
    private ArchivoImpresora archivoImpresora;
    TableView<ArchivoImpresora> tabla;

    public TableView<ArchivoImpresora> getTabla() {
        return tabla;
    }

    public void setTabla(TableView<ArchivoImpresora> tabla) {
        this.tabla = tabla;
    }
    public boolean isEsperar() {
        return esperar;
    }

    public void setEsperar(boolean esperar) {
        this.esperar = esperar;
    }

    public ArchivoImpresora getArchivoImpresora() {
        return archivoImpresora;
    }

    public void setArchivoImpresora(ArchivoImpresora archivoImpresora) {
        this.archivoImpresora = archivoImpresora;
    }



    public void setCorrer(boolean correr) {
        this.correr = correr;
    }

    public ProgressBar getPgbProgreso() {
        return pgbProgreso;
    }

    public void setPgbProgreso(ProgressBar pgbProgreso) {
        this.pgbProgreso = pgbProgreso;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public HiloImpresora()  {
    }



    @Override
    public void run() {
        super.run();
        while(correr) {
            if (!tabla.getItems().isEmpty()){
                double avance= 0;
                tiempo = tabla.getItems().getFirst().getNumeroHojas();
                while (avance<1){
                    if (!esperar){
                        avance = avance+(2/tiempo);
                        pgbProgreso.setProgress(avance);
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                tabla.getItems().removeFirst();
                tabla.refresh();
            }

        }
    }
}
