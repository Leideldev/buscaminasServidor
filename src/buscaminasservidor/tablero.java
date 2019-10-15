/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Fer
 */
public class tablero {
    
    int tamanox;
    int tamanoy;   
    JFrame tablero;
    JPanel panelJuego;
    casilla[][] juego;

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
        panelJuego.setBackground(Color.red);
        
    }
    
    public void agregarPanelesTablero(){
        tablero.add(panelJuego);    
        tablero.setVisible(true);
    }
    
    public void llenarPanelJuego(){
     juego = new casilla[tamanox][tamanoy];
      for(int i=0;i < tamanox; i++){        
        for(int j=0;j < tamanoy; j++){        
          casilla casillaObjeto = new casilla(i,j);      
          if (Math.random() > 0.9){              
              casillaObjeto.setTieneMina(true);
              panelJuego.add(casillaObjeto.getCasillaTablero());   
          }else{
           panelJuego.add(casillaObjeto.getCasillaTablero());     
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
            juego[i][j].casillaTablero.setText(String.valueOf(minas));
            minas=0;
            }else{
               juego[i][j].casillaTablero.setText("*"); 
               
            }
      }   
      }         
    }
 
    
    public boolean esCasillaValida(int pocisionx,int pocisiony){
        if(pocisionx >= 0 && pocisiony >= 0 && pocisionx <= tamanox-1 && pocisiony <= tamanox-1){
            return true;
        }
        return false;
    }
}
