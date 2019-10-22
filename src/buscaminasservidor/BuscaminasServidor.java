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
    private static Set<String> names = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();
   static HashMap <PrintWriter, String> map = new HashMap <PrintWriter, String> ();
   static HashMap <String, ArrayList <String>> bloqueados = new HashMap <String, ArrayList <String>> ();
   static tablero juego = new tablero(); 
    public static void main(String[] args) throws Exception  {
         System.out.println("The chat server is running... ");
        ExecutorService pool = Executors.newFixedThreadPool(500);
        try (ServerSocket listener = new ServerSocket(59001)) {
                   
        juego.crearTablero();
        juego.crearPanelJuego(10,10);
        juego.llenarPanelJuego();
        juego.agregarPanelesTablero();        
        juego.contarMinasAdyacentes();
            while (true) {
                pool.execute(new Handler(listener.accept()));

            }
        }
       
    }
    
    private static class Handler implements Runnable {

        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        
        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
                ArrayList <String> bloquea = new ArrayList <String>();

                while (true) {
                    out.println("SUBMITNAME");
                    name = in.nextLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            String color= juego.asignarColor();
                            out.println("NAMEACCEPTED " + name);
                            out.println("SIZE " + "," + 10 + "," + 10 + "," + color);
                            names.add(color);
                            break;

                        }
                    }
                }
                
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + " has joined");
                }
                
                map.put(out, name);
                bloqueados.put(name, bloquea);
                writers.add(out);

                while (true) {
                    String input = in.nextLine();
                    if(juego.casillasPorAbrir == 0 || juego.casillasPorAbrir <0){
                        System.out.println("perdedores");                      
                         for (PrintWriter writer : writers) {                             
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
                           if(!juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].estaMarcada && juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece.equals("")){
                              juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].estaMarcada = true;
                               juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece = arrayan[3];
                                for (PrintWriter writer : writers) {
                                  
                                         writer.println("MARCADA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony + "," +arrayan[3]);
                                    
                }
                               System.out.println("Entra marcar");
                           }else{
                               if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece.equals(arrayan[3])){                 
                                   juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].banderaPertenece = "";
                                   juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].estaMarcada = false;
                                    for (PrintWriter writer : writers) {
                                  
                                         writer.println("DESMARCADA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony);                                   
                }
                                    System.out.println("mina no marcad");
                               }
                               
                             
                           }                         
                    }else if(input.toLowerCase().startsWith("descubrir ")){
                        String [] arrayan; 
                           arrayan = input.split(","); 
                           
                           if(juego.validarCasillaMinaClick(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2]))){
                               out.println("PERDEDOR");
                           }else{
                               if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero == 0){
                                  juego.descubrirAdyacentes(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2]));
                                for(int i=0;i < juego.tamanox; i++){ 
           for(int j=0;j < juego.tamanoy; j++){ 
             if(!juego.juego[i][j].casillaTablero.isEnabled()){
                  for (PrintWriter writer : writers) {
                    writer.println("ABIERTAS" + "," + juego.juego[i][j].posicionx + "," + juego.juego[i][j].posiciony + "," + juego.juego[i][j].numero);
                }
             }           
                               }                               
        }  
        }else{
              if(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].casillaTablero.isEnabled()){
               juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].casillaTablero.setEnabled(false);
               juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].casillaTablero.setText(String.valueOf(juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero));
               juego.casillasPorAbrir--;
               for (PrintWriter writer : writers) {
                    writer.println("ABIERTA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero);
                }     
              }                    
                               } 
                           }
                    }else if(input.toLowerCase().startsWith("descubriruna ")){
                         String [] arrayan; 
                         arrayan = input.split(",");
                         
                         if(juego.validarCasillaMinaClick(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2]))){
                               out.println("PERDEDOR");
                           }else{
                 juego.descubrirCasilla(Integer.parseInt(arrayan[1]),Integer.parseInt(arrayan[2]));
                 out.println("ABIERTA" + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posicionx + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].posiciony + "," + juego.juego[Integer.parseInt(arrayan[1])][Integer.parseInt(arrayan[2])].numero);
                       
        
                           }
                        
                    }
                
                  
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                if (name != null) {
                    System.out.println(" is leaving");
                    names.remove(name);
                    for(PrintWriter writer:writers){
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
