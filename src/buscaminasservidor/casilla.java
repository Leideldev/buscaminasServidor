/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;

/**
 *
 * @author Fer
 */
public class casilla   {
    
    JButton casillaTablero;
    tablero objetoTablero = new tablero();
    boolean revisada=false;
    int posicionx;
    int posiciony;
    boolean tieneMina = false;
    boolean tieneBandera= false;
    boolean estaMarcada = false;
    int numero=0;    
    HashMap<JButton, casilla> map = new HashMap<JButton, casilla>();
    
    casilla(int pocisionx,int pocisiony){
        this.casillaTablero = new JButton();    
        this.map.put(this.casillaTablero, this);
        this.posicionx = pocisionx;
        this.posiciony = pocisiony;  
        
    }
    
       
    public JButton getCasillaTablero() {
        return casillaTablero;
    }

    public void setCasillaTablero(JButton casillaTablero) {
        this.casillaTablero = casillaTablero;
    }

    public int getPosicionx() {
        return posicionx;
    }

    public void setPosicionx(int posicionx) {
        this.posicionx = posicionx;
    }

    public int getPosiciony() {
        return posiciony;
    }

    public void setPosiciony(int posiciony) {
        this.posiciony = posiciony;
    }

    public boolean isTieneMina() {
        return tieneMina;
    }

    public void setTieneMina(boolean tieneMina) {
        this.tieneMina = tieneMina;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public HashMap<JButton, casilla> getMap() {
        return map;
    }

    public void setMap(HashMap<JButton, casilla> map) {
        this.map = map;
    }
     
   
}
   
    

