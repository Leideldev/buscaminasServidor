/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.Color;
import java.util.TimerTask;

/**
 *
 * @author Fer
 */
public class temporizador extends TimerTask {
    
    tablero tableroJuego;
    int tamano;
    
    temporizador(tablero tableroNuevo){
        this.tableroJuego = tableroNuevo;
        
    }
    
    public void run() {
       
    if(!tableroJuego.minas.isEmpty()){
       int random = (int )(Math.random() * tableroJuego.minas.size());
       tableroJuego.descubrirMinaAdyacente(tableroJuego.minas.get(random).posicionx,tableroJuego.minas.get(random).posiciony);     
       tableroJuego.minas.remove(random);
       
           System.out.println("Minas restantes:" +  tableroJuego.minas.size());
    }
    }
    
}
