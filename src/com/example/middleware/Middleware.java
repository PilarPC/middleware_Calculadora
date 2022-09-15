package com.example.middleware;

public class Middleware extends Thread {

    public static void main (String[] args){
        for(int puerto=11000; puerto<= 11026;puerto++){
            Proceso hilo1 = new Proceso(); //solo para el extends Thread
            //para arrancar los hilos, simpre se inician despues de instanciar todos los hilos, para que funcionen de forma simultánea
            hilo1.asignarPuerto(puerto);
            hilo1.start();
        }

    }
}
