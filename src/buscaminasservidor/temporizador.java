/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.util.TimerTask;

/**
 *
 * @author Fer
 */
public class temporizador extends TimerTask {
    
    tablero tableroJuego;
    
    temporizador(tablero tableroNuevo){
        this.tableroJuego = tableroNuevo;
    }
    
    public void run() {
       
    }
    
}
