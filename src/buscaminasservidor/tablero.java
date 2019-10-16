/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminasservidor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Fer
 */
public class tablero implements MouseListener{
    
    HashMap<JButton, casilla> map = new HashMap<JButton, casilla>();
    int tamanox;
    int tamanoy;   
    JFrame tablero;
    JPanel panelJuego;
    int minasTablero;
    int marcaValida;
    int casillasPorAbrir;
    casilla[][] juego;
    jugador Jugador1 = new jugador();
    
    
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
          if (Math.random() > 0.95){              
              casillaObjeto.setTieneMina(true);
              minasTablero++;
              panelJuego.add(casillaObjeto.getCasillaTablero());
              casillaObjeto.getCasillaTablero().addMouseListener(this);
              map.put(casillaObjeto.casillaTablero, casillaObjeto);
          }else{
           panelJuego.add(casillaObjeto.getCasillaTablero());
            casillaObjeto.getCasillaTablero().addMouseListener(this);
            map.put(casillaObjeto.casillaTablero, casillaObjeto);
          }
            
          juego[i][j] = casillaObjeto;
          
      }   
      } 
      casillasPorAbrir = map.size() - minasTablero;
    }
    
    public void contarMinasAdyacentes(){
        int numero = 0;
        
              for(int i=0;i < tamanox; i++){        
        for(int j=0;j < tamanoy; j++){
            if(!juego[i][j].tieneMina){
                        
            if(esCasillaValida(i-1,j)){
                if(juego[i-1][j].tieneMina){
                    numero++;
                    
                }            
            }
            if(esCasillaValida(i+1,j)){
                if(juego[i+1][j].tieneMina){
                    numero++;
                    
                }            
            } 
            if(esCasillaValida(i,j+1)){
                if(juego[i][j+1].tieneMina){
                    numero++;
                
                }            
            } 
            if(esCasillaValida(i,j-1)){
                if(juego[i][j-1].tieneMina){
                    numero++;
                 
                }            
            } 
            if(esCasillaValida(i-1,j+1)){
                if(juego[i-1][j+1].tieneMina){
                    numero++;
                    
                }            
            }
            if(esCasillaValida(i-1,j-1)){
                if(juego[i-1][j-1].tieneMina){
                    numero++;
                  
                }            
            } 
            if(esCasillaValida(i+1,j+1)){
                if(juego[i+1][j+1].tieneMina){
                    numero++;
                    
                }            
            } 
            if(esCasillaValida(i+1,j-1)){
                if(juego[i+1][j-1].tieneMina){
                    numero++;
                    
                }            
            }
            juego[i][j].numero = numero;
            numero=0;
            
            }
      }   
      }         
    }
    
    public void descubrirAdyacentes(int posicionx, int posiciony){
   casillasPorAbrir--;
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
    
    public void validarMinaMarcada(MouseEvent e){
        if(map.get(e.getSource()).isTieneMina() && !map.get(e.getSource()).estaMarcada){
            marcaValida++;
            map.get(e.getSource()).estaMarcada=true;
        }  else{
           
        }
        
    }
    
    public void partidaPerdida(){
        for(int i=0;i < tamanox; i++){ 
           for(int j=0;j < tamanoy; j++){ 
             juego[i][j].casillaTablero.setEnabled(false);
             juego[i][j].casillaTablero.setBackground(Color.red);
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
    public void mouseClicked(MouseEvent e) {
       if(e.getButton() == 1){
           
       if(Jugador1.sigueJugando){
       if(!Jugador1.ganador){    
       if(map.get(e.getSource()).isTieneMina()){ 
            partidaPerdida();
            Jugador1.sigueJugando = false;
            JOptionPane.showMessageDialog(null, "Perdiste demente");
        }else{
            map.get(e.getSource()).casillaTablero.setEnabled(false);  
            map.get(e.getSource()).casillaTablero.setText(String.valueOf(map.get(e.getSource()).numero));
            if(casillasPorAbrir != 0){
              descubrirAdyacentes(map.get(e.getSource()).posicionx,map.get(e.getSource()).posiciony);  
            }
            
            if(marcaValida == minasTablero && casillasPorAbrir == 0){
                      JOptionPane.showMessageDialog(null, "Ganaste burro");
                      Jugador1.ganador = true;
                  }
           System.out.println("minas validas marcadas: "+marcaValida);
                  System.out.println("casillas abiertas: "+casillasPorAbrir);  
        } 
       }
    }
       }
       
    if(e.getButton() == 3){
        try {
            if(Jugador1.sigueJugando){
                if(!Jugador1.ganador){  
            if(map.get(e.getSource()).tieneBandera){
                if(map.get(e.getSource()).casillaTablero.isEnabled()){
                  map.get(e.getSource()).casillaTablero.setIcon(null);
                 map.get(e.getSource()).tieneBandera = false;  
                }                
            }else{
                if((map.get(e.getSource()).casillaTablero.isEnabled())){
                     Image img = ImageIO.read(new FileInputStream("C:\\Users\\Fer\\Documents\\NetBeansProjects\\buscaminasServidor\\src\\images\\bandera.bmp"));
                 map.get(e.getSource()).casillaTablero.setIcon(new ImageIcon(img));
                  map.get(e.getSource()).tieneBandera = true;
                }          
                   validarMinaMarcada(e);
                  if(marcaValida == minasTablero && casillasPorAbrir == 0){
                      JOptionPane.showMessageDialog(null, "Ganaste burro");
                      Jugador1.ganador = true;
                  }else{
                      System.out.println("Quedan: " + minasTablero + " minas");
                  }
                  System.out.println("minas validas marcadas: "+marcaValida);
                  System.out.println("casillas abiertas: "+casillasPorAbrir);
            }
            }
            }
  } catch (Exception ex) {
    System.out.println(ex);
  }
    }
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
