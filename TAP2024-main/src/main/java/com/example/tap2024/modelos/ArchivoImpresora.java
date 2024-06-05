package com.example.tap2024.modelos;

import java.time.LocalDateTime;

public class ArchivoImpresora {
    int numeroArchivo;
    String nombreArchivo, hora;
    int numeroHojas;


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getNumeroArchivo() {
        return numeroArchivo;
    }

    public void setNumeroArchivo(int numeroArchivo) {
        this.numeroArchivo = numeroArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getNumeroHojas() {
        return numeroHojas;
    }

    public void setNumeroHojas(int numeroHojas) {
        this.numeroHojas = numeroHojas;
    }



    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }


    LocalDateTime locaDate = LocalDateTime.now();
    int hours  = locaDate.getHour();
    int minutes = locaDate.getMinute();
    int seconds = locaDate.getSecond();
}
