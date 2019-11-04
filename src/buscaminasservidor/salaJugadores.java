/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Fer
 */
public class salaJugadores {
    
    
    tablero tableroDeSala;
  
    ExecutorService pool;
    
    
    salaJugadores(){
      pool = Executors.newFixedThreadPool(4);
      tableroDeSala = new tablero();
      Timer timer = new Timer();
      timer.schedule(new temporizador(tableroDeSala), 10000, 10000);
    }
    
    
    boolean agregarCliente(Socket cliente) throws IOException {
        if (tableroDeSala.jugadoresTotales == 4 || tableroDeSala.juegoComenzado == true) {    
            return false;
        }
        pool.execute(new Handler(cliente,tableroDeSala));
        return true;
    }
    
    
    private class Handler implements Runnable {

        private String name;
        private Socket socket;
        private tablero juego;
        private Scanner lectorEntrada;
        private PrintWriter escritorSalida;
        
        
        
        public Handler(Socket socket, tablero tablero) {
            this.socket = socket;
            juego = tablero;       

        }

        public void run() {
                        try{
            lectorEntrada = new Scanner(socket.getInputStream());
            escritorSalida = new PrintWriter(socket.getOutputStream(), true);
            }catch(IOException e){
                
            }
            while (true) {
                    escritorSalida.println("SUBMITNAME");
               
                   String color= juego.asignarColor();
                   
                    synchronized (juego.names) {
                        if (!juego.names.contains("xd")) {
                            
                            escritorSalida.println("NAMEACCEPTED " + color);
                            escritorSalida.println("SIZE " + "," + juego.getTamanox() + "," +  juego.getTamanoy() + "," + color);
                             juego.jugadoresTotales++;
                             juego.writers.add(escritorSalida);
                            if(juego.jugadoresTotales >= 2 && !juego.juegoComenzado){
                                    for (PrintWriter writer : juego.writers) {
                                   System.out.println("Jugadores mas de dos");
                                writer.println("COMENZAR " + color);                                
                }           
                 }
                                                  
                            juego.names.add(color);
                           
                            break;
                        }
                    }
                }
            
            try {
                System.out.println(juego);

                juego.mapa.put(escritorSalida, name);
               
               
                  
                while (true) {
                    
                    String input = lectorEntrada.nextLine();
                    
                      if(juego.minasTablero==0){
                        System.out.println("perdedores");                      
                         for (PrintWriter writer : juego.writers) {                             
                                         writer.println("GANADOR" + "," + juego.validarGanador());                             
                }
                        break;
                    }
                    
                    if (input.toLowerCase().startsWith("/quit")) {
                        return;
                    }           
                    if(input.toLowerCase().startsWith("marcar ")){
                           String [] arrayan; 
                           arrayan = input.split(","); 
                           System.out.println("Color" + arrayan[3]);
                           if( juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece.equals("")){
                           juego.validarMinaMarcada(Integer.parseInt(arrayan[1]), Integer.parseInt(arrayan[2]));
                           
                              juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].tieneBandera = true;
                               juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece = arrayan[3];
                                for (PrintWriter writer : juego.writers) {
                                  
                                         writer.println("MARCADA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony + "," +arrayan[3]);
                                    
                }

                               System.out.println("Entra marcar");
                           }else{
                               if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece.equals(arrayan[3])){
                                   if( juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].estaMarcada){
                                        juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].estaMarcada = false;
                                         juego.marcaValida--;
                                   }
                                   juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece = "";
                                   juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].tieneBandera = false;
                                   
                                    juego.validarMinaMarcada(Integer.parseInt(arrayan[1]), Integer.parseInt(arrayan[2]));
                                    for (PrintWriter writer : juego.writers) {
                                  
                                         writer.println("DESMARCADA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony);                                   
                }
                                    System.out.println("mina no marcad");
                               }
                               
                             
                           }                         
                    }else if(input.toLowerCase().startsWith("descubrir ")){
                        String [] arrayan; 
                           arrayan = input.split(","); 
                           
                           if(juego.validarCasillaMinaClick(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2])) && !juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].tieneBandera){
                               escritorSalida.println("PERDEDOR");
                           }else{
                               if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero == 0 && !juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].tieneBandera){
                                  juego.descubrirCasillasAdyacentes(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2]));
                                for(int i=0;i < juego.tamanox; i++){ 
           for(int j=0;j < juego.tamanoy; j++){ 
             if(!juego.juego[i][j].casillaTablero.isEnabled()){
                  for (PrintWriter writer : juego.writers) {
                    writer.println("ABIERTAS" + "," + juego.juego[i][j].posicionx + "," + juego.juego[i][j].posiciony + "," + juego.juego[i][j].numero);
                }
             }           
                               }                               
        }  
        }else{
              if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].casillaTablero.isEnabled() && !juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].tieneBandera){
               juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].casillaTablero.setEnabled(false);
               juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].casillaTablero.setText(String.valueOf(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero));
               
               for (PrintWriter writer : juego.writers) {
                    writer.println("ABIERTA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero);
                }     
              }                    
                               } 
                           }
                    }else if(input.toLowerCase().startsWith("comenzar")){
                        juego.juegoComenzado = true;
                         for (PrintWriter writer : juego.writers) {
                             System.out.println("hola estoy comenzando");
                             writer.println("comenzada");
                                                           }
                    }
                
                  
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (escritorSalida != null) {
                    juego.writers.remove(escritorSalida);
                }
                if (name != null) {
                    System.out.println(name + " is leaving");
                    juego.names.remove(name);
                    for(PrintWriter writer:juego.writers){
                        writer.println("MESSAGE "+name+" has left");
                    }
                }
                try{
                    socket.close();
                } catch(IOException e){
                }
                }
            }

        }
}
