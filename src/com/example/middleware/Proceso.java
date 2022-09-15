package com.example.middleware;

import com.example.paquete.Paquete;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Proceso extends Thread{
    public int PUERTO_ACTUAL=0;
    String resultado;
    public String mns;
    public int puertosArray[];

    @Override // para usar polimorfismo
    public void run(){

        try {
            ServerSocket calculadora = new ServerSocket(PUERTO_ACTUAL);
            while (true){
                //RECIBIR
                int guardo_emisor_cliente = 0;
                int guardo_emisor_servidor = 0;
                Socket misocketC = calculadora.accept();
                ObjectInputStream flujoEntradaC = new ObjectInputStream(misocketC.getInputStream());
                Paquete paqueteRecibido = (Paquete) flujoEntradaC.readObject();
                if (paqueteRecibido.getIDdireccion() == 'C'){
                    guardo_emisor_cliente = paqueteRecibido.getPuertoEmisor();
                } else if (paqueteRecibido.getIDdireccion() == 'S') {
                    guardo_emisor_servidor = paqueteRecibido.getPuertoEmisor();
                }

                System.out.println(paqueteRecibido.getMensaje()+ " "+paqueteRecibido.getPuertoEmisor() + " "+paqueteRecibido.getIDdireccion()+" Estoy recibiendo en el puerto "+PUERTO_ACTUAL);
                if(paqueteRecibido.getIDdireccion() == 'C' | paqueteRecibido.getIDdireccion() == 'S'){
                    int puertoServidor = 13000;
                    int enviar = 12000;
                    System.out.println(paqueteRecibido.getPuertoEmisor()+"    REVISO     "+puertoServidor);
                    for (int j=0; j <= 4; j++ ) {
                        if (true){ //manda a al servidor paqueteRecibido.getPuertoEmisor() != guardo_emisor_servidor
                            try {
                                //mandar SERVIDOR
                                Socket flujoSalidaS = new Socket("127.0.0.1", puertoServidor);
                                ObjectOutputStream paqueteSalida = new ObjectOutputStream(flujoSalidaS.getOutputStream());
                                paqueteRecibido.getMensaje();
                                paqueteSalida.writeObject(paqueteRecibido);
                                flujoSalidaS.close();
                                System.out.println("se mando resultado a " + puertoServidor);
                                puertoServidor++;
                            } catch (IOException e) {
                                //System.out.println(puertoServidor + " Servidor no está a la escucha");
                                puertoServidor++;
                            }
                        }
                    }
                    System.out.println(paqueteRecibido.getPuertoEmisor()+"    REVISO     "+puertoServidor);
                    for (int i=0; i <= 4; i++ ){
                        //System.out.println(paqueteRecibido.getPuertoEmisor() + " "+ puertoServidor);
                        if (true){ // manda a la calculadora
                                try {
                                Socket flujoSalidaC = new Socket("127.0.0.1", enviar);
                                ObjectOutputStream paqueteSalida =  new ObjectOutputStream(flujoSalidaC.getOutputStream());
                                paqueteSalida.writeObject(paqueteRecibido);
                                flujoSalidaC.close();
                                System.out.println("se mando resultado a " + enviar);
                                enviar++;
                            }catch (IOException e){
                                //System.out.println(enviar+" Cliente no está a la escucha");
                                enviar++;
                            }
                        }
                    }

                /*} else if (paqueteRecibido.getIDdireccion() == 'S'){
                    //MANDO CALCULADORA
                Socket flujoSalidaC = new Socket("127.0.0.1", obtenerPuerto(paqueteRecibido));
                ObjectOutputStream paqueteSalida =  new ObjectOutputStream(flujoSalidaC.getOutputStream());
                paqueteSalida.writeObject(paqueteRecibido);
                flujoSalidaC.close();*/

                }

                misocketC.close();
                //misocketS.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }



    }

    public int obtenerPuerto(Paquete paquete){
        int puerto = paquete.getPuertoEmisor();
        return puerto;
    }
    public void asignarPuerto(int puertoActual){
        PUERTO_ACTUAL = puertoActual;
    }
}
