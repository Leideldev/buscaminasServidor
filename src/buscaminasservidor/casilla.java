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
public class casilla implements ActionListener  {
    
    JButton casillaTablero;  
    int posicionx;
    int posiciony;
    boolean tieneMina = false;
    boolean tieneNumero;    
    HashMap<JButton, casilla> map = new HashMap<JButton, casilla>();
    
    casilla(int pocisionx,int pocisiony){
        this.casillaTablero = new JButton();
        this.casillaTablero.addActionListener(this);       
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

    public boolean isTieneNumero() {
        return tieneNumero;
    }

    public void setTieneNumero(boolean tieneNumero) {
        this.tieneNumero = tieneNumero;
    }
    
    
    public void descubrirCasillasAdyacentes(int pocisionx,int pocisiony){
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(map.get(e.getSource()).tieneMina){     
            System.out.println("valiste keso tiene mina");
        }else{
           map.get(e.getSource()).casillaTablero.setEnabled(false);
           
        }
        
    }
   
    
}
