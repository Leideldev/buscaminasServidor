/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Fer
 */
public class BuscaminasServidor {

    /**
     * @param args the command line arguments
     */
    //private static Set<String> names = new HashSet<>();
    //private static Set<PrintWriter> writers = new HashSet<>();
   //static HashMap <PrintWriter, String> map = new HashMap <PrintWriter, String> ();
   //static HashMap <String, ArrayList <String>> bloqueados = new HashMap <String, ArrayList <String>> ();
 
    public static void main(String[] args) throws Exception  {
         System.out.println("The chat server is running... ");
        
        try (ServerSocket listener = new ServerSocket(59001)) {
        tablero tableroJuego = new tablero();
        
            
            while (true) {
                 System.out.println(tableroJuego.jugadoresTotales);
                 if(tableroJuego.jugadoresTotales==4){
                     tableroJuego = new tablero();
                     System.out.println(tableroJuego.jugadoresTotales);
                 }
                 
                tableroJuego.pool.execute(new Handler(listener.accept(),tableroJuego));
                System.out.println(tableroJuego.jugadoresTotales);
            }
        }
       
    }
    
    private static class Handler implements Runnable {

        private String name;
        private Socket socket;
        private tablero juego;
        private Scanner in;
        private PrintWriter out;
        Timer timer = new Timer();
        
        
        public Handler(Socket socket, tablero tablero) {
            this.socket = socket;
            juego = tablero;
            timer.schedule(new temporizador(juego), 10000, 10000); 
           
            System.out.println(timer.toString());
            try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            }catch(IOException e){
                
            }
            while (true) {
                    out.println("SUBMITNAME");
                    name = in.nextLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (juego.names) {
                        if (!juego.names.contains(name)) {
                            String color= juego.asignarColor();
                            out.println("NAMEACCEPTED " + name);
                            out.println("SIZE " + "," + juego.getTamanox() + "," +  juego.getTamanoy() + "," + color);
                             juego.jugadoresTotales++;
                            juego.names.add(color);
                            break;
                        }
                    }
                }
        }

        public void run() {
            try {
                System.out.println(juego);
                
                ArrayList <String> bloquea = new ArrayList <String>();

                
                System.out.println(juego.toString());
                juego.mapa.put(out, name);
                juego.writers.add(out);

                while (true) {
                  
                    String input = in.nextLine();
                   
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
                                  if(juego.marcaValida==juego.minasTablero){
                        System.out.println("perdedores");                      
                         for (PrintWriter writer : juego.writers) {                             
                                         writer.println("GANADOR" + "," + juego.validarGanador());                             
                }
                        break;
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
                               out.println("PERDEDOR");
                           }else{
                               if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero == 0 && !juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].tieneBandera){
                                  juego.descubrirAdyacentes(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2]));
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
                    }
                
                  
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    juego.writers.remove(out);
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
