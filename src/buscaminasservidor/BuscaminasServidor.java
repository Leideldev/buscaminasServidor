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
       
        salaJugadores sala = new salaJugadores();
            
            while (true) {
            Socket cliente = listener.accept();
                 if(!sala.agregarCliente(cliente)){
                     System.out.println("Juego comenzado");       
                     sala = new salaJugadores();         
                     sala.agregarCliente(cliente);
                 }
                      
                 
        }
        }
       
    }
    
    
    
}
