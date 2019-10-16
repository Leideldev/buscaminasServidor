/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Fer
 */
public class tablero implements ActionListener{
    
    HashMap<JButton, casilla> map = new HashMap<JButton, casilla>();
    int tamanox;
    int tamanoy;   
    JFrame tablero;
    JPanel panelJuego;
    casilla[][] juego;
    JButton [][] botones;
    
    public int getTamanox() {
        return tamanox;
    }

    public void setTamanox(int tamanox) {
        this.tamanox = tamanox;
    }

    public int getTamanoy() {
        return tamanoy;
    }

    public void setTamanoy(int tamanoy) {
        this.tamanoy = tamanoy;
    }

    public JFrame getTablero() {
        return tablero;
    }

    public void setTablero(JFrame tablero) {
        this.tablero = tablero;
    }

    public JPanel getPanelJuego() {
        return panelJuego;
    }

    public void setPanelJuego(JPanel panelJuego) {
        this.panelJuego = panelJuego;
    }
    
    
    public void crearTablero(){
        tablero = new JFrame();
        tablero.setTitle("Tablero buscaminas");
         tablero.setSize(1000,600);
        tablero.setLocationRelativeTo(null);               
        tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void crearPanelJuego(int tamanox,int tamanoy){
        panelJuego = new JPanel();
        setTamanox(tamanox);
        setTamanoy(tamanoy);
        panelJuego.setLayout(new GridLayout(tamanox,tamanoy));
        
    }
    
    public void agregarPanelesTablero(){
        tablero.add(panelJuego);    
        tablero.setVisible(true);
        panelJuego.setVisible(true);
        panelJuego.revalidate();
        panelJuego.repaint();
    }
    
    public void llenarPanelJuego(){
     juego = new casilla[tamanox][tamanoy];
    
      for(int i=0;i < tamanox; i++){        
        for(int j=0;j < tamanoy; j++){        
          casilla casillaObjeto = new casilla(i,j);      
          if (Math.random() > 0.90){              
              casillaObjeto.setTieneMina(true);
              panelJuego.add(casillaObjeto.getCasillaTablero());
              casillaObjeto.getCasillaTablero().addActionListener(this);
              map.put(casillaObjeto.casillaTablero, casillaObjeto);
          }else{
           panelJuego.add(casillaObjeto.getCasillaTablero());
            casillaObjeto.getCasillaTablero().addActionListener(this);
            map.put(casillaObjeto.casillaTablero, casillaObjeto);
          }
            
          juego[i][j] = casillaObjeto;
         
      }   
      } 
      
    }
    
    public void contarMinasAdyacentes(){
        int minas = 0;
              for(int i=0;i < tamanox; i++){        
        for(int j=0;j < tamanoy; j++){
            if(!juego[i][j].tieneMina){
                        
            if(esCasillaValida(i-1,j)){
                if(juego[i-1][j].tieneMina){
                    minas++;
                }            
            }
            if(esCasillaValida(i+1,j)){
                if(juego[i+1][j].tieneMina){
                    minas++;
                }            
            } 
            if(esCasillaValida(i,j+1)){
                if(juego[i][j+1].tieneMina){
                    minas++;
                }            
            } 
            if(esCasillaValida(i,j-1)){
                if(juego[i][j-1].tieneMina){
                    minas++;
                }            
            } 
            if(esCasillaValida(i-1,j+1)){
                if(juego[i-1][j+1].tieneMina){
                    minas++;
                }            
            }
            if(esCasillaValida(i-1,j-1)){
                if(juego[i-1][j-1].tieneMina){
                    minas++;
                }            
            } 
            if(esCasillaValida(i+1,j+1)){
                if(juego[i+1][j+1].tieneMina){
                    minas++;
                }            
            } 
            if(esCasillaValida(i+1,j-1)){
                if(juego[i+1][j-1].tieneMina){
                    minas++;
                }            
            }
            juego[i][j].numero = minas;
            minas=0;
            }
      }   
      }         
    }
    
    public void descubrirAdyacentes(int posicionx, int posiciony){
   
        if(esCasillaValida(posicionx,posiciony) && juego[posicionx][posiciony].numero == 0 ){
            juego[posicionx][posiciony].revisada=true;
            juego[posicionx][posiciony].casillaTablero.setEnabled(false);
            juego[posicionx][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony].numero));
            
             if(esCasillaValida(posicionx-1,posiciony)){  
                if(!juego[posicionx-1][posiciony].tieneMina && !juego[posicionx-1][posiciony].revisada){         
                    juego[posicionx-1][posiciony].casillaTablero.setEnabled(false);
                    juego[posicionx-1][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx-1][posiciony].numero));
                     juego[posicionx-1][posiciony].revisada=true;
                    descubrirAdyacentes(posicionx-1,  posiciony);
                }            
            }
            if(esCasillaValida(posicionx+1,posiciony)){
                
                if(!juego[posicionx+1][posiciony].tieneMina && !juego[posicionx+1][posiciony].revisada){      
                     juego[posicionx+1][posiciony].casillaTablero.setEnabled(false);
                     juego[posicionx+1][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx+1][posiciony].numero));
                      juego[posicionx+1][posiciony].revisada=true;
                     descubrirAdyacentes(posicionx+1,  posiciony);
                }            
            } 
            if(esCasillaValida(posicionx,posiciony+1)){               
                if(!juego[posicionx][posiciony+1].tieneMina && !juego[posicionx][posiciony+1].revisada){     
                     juego[posicionx][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx][posiciony+1].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony+1].numero));
                      juego[posicionx][posiciony+1].revisada=true;
                     descubrirAdyacentes(posicionx,  posiciony+1);
                }            
            } 
            if(esCasillaValida(posicionx,posiciony-1)){
                if(!juego[posicionx][posiciony-1].tieneMina && !juego[posicionx][posiciony-1].revisada){              
                     juego[posicionx][posiciony-1].casillaTablero.setEnabled(false);
                     juego[posicionx][posiciony-1].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony-1].numero));
                      juego[posicionx][posiciony-1].revisada=true;
                     descubrirAdyacentes( posicionx,  posiciony-1);
                }            
            } 
            if(esCasillaValida(posicionx-1,posiciony+1)){
                if(!juego[posicionx-1][posiciony+1].tieneMina &&  !juego[posicionx-1][posiciony+1].revisada){                  
                     juego[posicionx-1][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx-1][posiciony+1].casillaTablero.setText(String.valueOf(juego[posicionx-1][posiciony+1].numero));
                      juego[posicionx-1][posiciony+1].revisada=true;
                     descubrirAdyacentes(posicionx-1,  posiciony+1);
                }            
            }
            if(esCasillaValida(posicionx-1,posiciony-1)){
                if(!juego[posicionx-1][posiciony-1].tieneMina &&  !juego[posicionx-1][posiciony-1].revisada){       
                   juego[posicionx-1][posiciony-1].casillaTablero.setEnabled(false);
                   juego[posicionx-1][posiciony-1].casillaTablero.setText(String.valueOf(juego[posicionx-1][posiciony-1].numero));
                    juego[posicionx-1][posiciony-1].revisada=true;
                   descubrirAdyacentes(posicionx-1,  posiciony-1);
                }            
            } 
            if(esCasillaValida(posicionx+1,posiciony+1)){
                if(!juego[posicionx+1][posiciony+1].tieneMina &&  !juego[posicionx+1][posiciony+1].revisada){              
                    juego[posicionx+1][posiciony+1].casillaTablero.setEnabled(false);
                    juego[posicionx+1][posiciony+1].casillaTablero.setText(String.valueOf(juego[posicionx+1][posiciony+1].numero));
                     juego[posicionx+1][posiciony+1].revisada=true;
                    descubrirAdyacentes(posicionx+1,  posiciony+1);
                }            
            } 
            if(esCasillaValida(posicionx+1,posiciony-1)){
                if(!juego[posicionx+1][posiciony-1].tieneMina &&  !juego[posicionx + 1][posiciony - 1].revisada){                  
                    juego[posicionx+1][posiciony-1].casillaTablero.setEnabled(false);
                    juego[posicionx+1][posiciony-1].casillaTablero.setText(String.valueOf(juego[posicionx+1][posiciony-1].numero));
                     juego[posicionx+1][posiciony-1].revisada=true;
                      descubrirAdyacentes(posicionx+1,  posiciony-1);
                }            
            }
        }
 
    }
    
    
    
    
    public boolean esCasillaValida(int pocisionx,int pocisiony){
        if(pocisionx >= 0 && pocisiony >= 0 && pocisionx < tamanox && pocisiony < tamanoy){
            
            return true;
        }   
       
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(map.get(e.getSource()).isTieneMina()){
            System.out.println("valiste tiene mina");
        }else{
            map.get(e.getSource()).casillaTablero.setEnabled(false);
            map.get(e.getSource()).casillaTablero.setText(String.valueOf(map.get(e.getSource()).numero));
            descubrirAdyacentes(map.get(e.getSource()).posicionx,map.get(e.getSource()).posiciony);
            
        }
       
    }
}
