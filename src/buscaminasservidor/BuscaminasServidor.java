/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.event.ActionEvent;

/**
 *
 * @author Fer
 */
public class BuscaminasServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        tablero juego = new tablero();
       
        juego.crearTablero();
        juego.crearPanelJuego(10,10);
        juego.llenarPanelJuego();
        juego.agregarPanelesTablero();        
        juego.contarMinasAdyacentes();
        
       
       
    }
    
}
