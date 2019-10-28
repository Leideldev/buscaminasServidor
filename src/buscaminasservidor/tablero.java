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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    Set<String> names = new HashSet<>();
    Set<PrintWriter> writers = new HashSet<>();
    HashMap<PrintWriter, String> mapa = new HashMap <PrintWriter, String> ();
    ArrayList <casilla> minas = new <casilla> ArrayList();
    casilla[][] juego;
    String [] colores = {"GREEN","YELLOW","BLUE","ORANGE"};
    int colorAsignado = 0;
    jugador Jugador1 = new jugador();
    boolean juegoComenzado = false;
    int jugadoresTotales = 0;
    ExecutorService pool = Executors.newFixedThreadPool(4);
    
    
    tablero(){
        crearTablero();
        crearPanelJuego(5,5);
        llenarPanelJuego();
        agregarPanelesTablero();        
        contarMinasAdyacentes();
    }
    
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
          if (Math.random() > 0.85){              
              casillaObjeto.setTieneMina(true);
              minasTablero++;
              panelJuego.add(casillaObjeto.getCasillaTablero());
              casillaObjeto.getCasillaTablero().addMouseListener(this);
              map.put(casillaObjeto.casillaTablero, casillaObjeto);
              minas.add(casillaObjeto);
          }else{
           panelJuego.add(casillaObjeto.getCasillaTablero());
            casillaObjeto.getCasillaTablero().addMouseListener(this);
            map.put(casillaObjeto.casillaTablero, casillaObjeto);
          }
            
          juego[i][j] = casillaObjeto;
          
      }   
      } 
      
    }
    
    public String asignarColor(){
        if(colorAsignado==colores.length || juegoComenzado){
        colorAsignado=0;
    }
        if(colorAsignado<colores.length){
            String color = colores[colorAsignado];
            colorAsignado++;       
            System.out.println("Asignado color: " + colorAsignado);
           return color;          
        }else{
            return "";
        }
        
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
  
   
        if(esCasillaValida(posicionx,posiciony) && juego[posicionx][posiciony].numero == 0 && !juego[posicionx][posiciony].tieneBandera){
            juego[posicionx][posiciony].revisada=true;
            juego[posicionx][posiciony].casillaTablero.setEnabled(false);
            juego[posicionx][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony].numero));
           
             if(esCasillaValida(posicionx-1,posiciony)){  
                if(!juego[posicionx-1][posiciony].tieneMina && !juego[posicionx-1][posiciony].revisada && !juego[posicionx-1][posiciony].tieneBandera){         
                    juego[posicionx-1][posiciony].casillaTablero.setEnabled(false);
                    juego[posicionx-1][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx-1][posiciony].numero));
                     juego[posicionx-1][posiciony].revisada=true;
                    descubrirAdyacentes(posicionx-1,  posiciony);
                    
                }            
            }
            if(esCasillaValida(posicionx+1,posiciony)){
                
                if(!juego[posicionx+1][posiciony].tieneMina && !juego[posicionx+1][posiciony].revisada && !juego[posicionx+1][posiciony].tieneBandera){      
                     juego[posicionx+1][posiciony].casillaTablero.setEnabled(false);
                     juego[posicionx+1][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx+1][posiciony].numero));
                      juego[posicionx+1][posiciony].revisada=true;
                     descubrirAdyacentes(posicionx+1,  posiciony);
                    
                }            
            } 
            if(esCasillaValida(posicionx,posiciony+1)){               
                if(!juego[posicionx][posiciony+1].tieneMina && !juego[posicionx][posiciony+1].revisada && !juego[posicionx][posiciony+1].tieneBandera){     
                     juego[posicionx][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx][posiciony+1].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony+1].numero));
                      juego[posicionx][posiciony+1].revisada=true;
                     descubrirAdyacentes(posicionx,  posiciony+1);
                     
                }            
            } 
            if(esCasillaValida(posicionx,posiciony-1)){
                if(!juego[posicionx][posiciony-1].tieneMina && !juego[posicionx][posiciony-1].revisada && !juego[posicionx][posiciony-1].tieneBandera){              
                     juego[posicionx][posiciony-1].casillaTablero.setEnabled(false);
                     juego[posicionx][posiciony-1].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony-1].numero));
                      juego[posicionx][posiciony-1].revisada=true;
                     descubrirAdyacentes( posicionx,  posiciony-1);
                  
                }            
            } 
            if(esCasillaValida(posicionx-1,posiciony+1)){
                if(!juego[posicionx-1][posiciony+1].tieneMina &&  !juego[posicionx-1][posiciony+1].revisada &&  !juego[posicionx-1][posiciony+1].tieneBandera){                  
                     juego[posicionx-1][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx-1][posiciony+1].casillaTablero.setText(String.valueOf(juego[posicionx-1][posiciony+1].numero));
                      juego[posicionx-1][posiciony+1].revisada=true;
                     descubrirAdyacentes(posicionx-1,  posiciony+1);
                   
                }            
            }
            if(esCasillaValida(posicionx-1,posiciony-1)){
                if(!juego[posicionx-1][posiciony-1].tieneMina &&  !juego[posicionx-1][posiciony-1].revisada &&  !juego[posicionx-1][posiciony-1].tieneBandera){       
                   juego[posicionx-1][posiciony-1].casillaTablero.setEnabled(false);
                   juego[posicionx-1][posiciony-1].casillaTablero.setText(String.valueOf(juego[posicionx-1][posiciony-1].numero));
                    juego[posicionx-1][posiciony-1].revisada=true;
                   descubrirAdyacentes(posicionx-1,  posiciony-1);
                 
                }            
            } 
            if(esCasillaValida(posicionx+1,posiciony+1)){
                if(!juego[posicionx+1][posiciony+1].tieneMina &&  !juego[posicionx+1][posiciony+1].revisada &&  !juego[posicionx+1][posiciony+1].tieneBandera){              
                    juego[posicionx+1][posiciony+1].casillaTablero.setEnabled(false);
                    juego[posicionx+1][posiciony+1].casillaTablero.setText(String.valueOf(juego[posicionx+1][posiciony+1].numero));
                     juego[posicionx+1][posiciony+1].revisada=true;
                    descubrirAdyacentes(posicionx+1,  posiciony+1);
                   
                }            
            } 
            if(esCasillaValida(posicionx+1,posiciony-1)){
                if(!juego[posicionx+1][posiciony-1].tieneMina &&  !juego[posicionx + 1][posiciony - 1].revisada &&  !juego[posicionx + 1][posiciony - 1].tieneBandera ){                  
                    juego[posicionx+1][posiciony-1].casillaTablero.setEnabled(false);
                    juego[posicionx+1][posiciony-1].casillaTablero.setText(String.valueOf(juego[posicionx+1][posiciony-1].numero));
                     juego[posicionx+1][posiciony-1].revisada=true;
                      descubrirAdyacentes(posicionx+1,  posiciony-1);
                     
                }            
            }
        }
 
    }
    
    public void descubrirMinaAdyacente(int posicionx, int posiciony){
  
   
        if(esCasillaValida(posicionx,posiciony) && juego[posicionx][posiciony].tieneMina && !juego[posicionx][posiciony].tieneBandera){
            juego[posicionx][posiciony].revisada=true;
            juego[posicionx][posiciony].casillaTablero.setEnabled(false);
           juego[posicionx][posiciony].casillaTablero.setBackground(Color.red);
            for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx][posiciony].posicionx + "," + juego[posicionx][posiciony].posiciony);
                }
             if(esCasillaValida(posicionx-1,posiciony)){  
                if(juego[posicionx-1][posiciony].tieneMina && !juego[posicionx-1][posiciony].revisada && !juego[posicionx-1][posiciony].tieneBandera){         
                    juego[posicionx-1][posiciony].casillaTablero.setEnabled(false);                 
                     juego[posicionx-1][posiciony].revisada=true;
                     juego[posicionx-1][posiciony].casillaTablero.setBackground(Color.red);
                      for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx-1][posiciony].posicionx + "," + juego[posicionx-1][posiciony].posiciony);
                }
                    descubrirMinaAdyacente(posicionx-1,  posiciony);
                    
                }            
            }
            if(esCasillaValida(posicionx+1,posiciony)){
                
                if(juego[posicionx+1][posiciony].tieneMina && !juego[posicionx+1][posiciony].revisada && !juego[posicionx+1][posiciony].tieneBandera){      
                     juego[posicionx+1][posiciony].casillaTablero.setEnabled(false);
                     
                      juego[posicionx+1][posiciony].revisada=true;
                      juego[posicionx+1][posiciony].casillaTablero.setBackground(Color.red);
                       for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx+1][posiciony].posicionx + "," + juego[posicionx+1][posiciony].posiciony);
                }
                     descubrirMinaAdyacente(posicionx+1,  posiciony);
                    
                }            
            } 
            if(esCasillaValida(posicionx,posiciony+1)){               
                if(juego[posicionx][posiciony+1].tieneMina && !juego[posicionx][posiciony+1].revisada && !juego[posicionx][posiciony+1].tieneBandera){     
                     juego[posicionx][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx][posiciony+1].casillaTablero.setBackground(Color.red);
                      juego[posicionx][posiciony+1].revisada=true;
                       for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx][posiciony+1].posicionx + "," + juego[posicionx][posiciony+1].posiciony);
                }
                     descubrirMinaAdyacente(posicionx,  posiciony+1);
                     
                }            
            } 
            if(esCasillaValida(posicionx,posiciony-1)){
                if(juego[posicionx][posiciony-1].tieneMina && !juego[posicionx][posiciony-1].revisada && !juego[posicionx][posiciony-1].tieneBandera){              
                     juego[posicionx][posiciony-1].casillaTablero.setEnabled(false);
                     juego[posicionx][posiciony-1].casillaTablero.setBackground(Color.red);
                      juego[posicionx][posiciony-1].revisada=true;
                       for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx][posiciony-1].posicionx + "," + juego[posicionx][posiciony-1].posiciony);
                }
                     descubrirMinaAdyacente( posicionx,  posiciony-1);
                  
                }            
            } 
            if(esCasillaValida(posicionx-1,posiciony+1)){
                if(juego[posicionx-1][posiciony+1].tieneMina &&  !juego[posicionx-1][posiciony+1].revisada &&  !juego[posicionx-1][posiciony+1].tieneBandera){                  
                     juego[posicionx-1][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx-1][posiciony+1].casillaTablero.setBackground(Color.red);
                      juego[posicionx-1][posiciony+1].revisada=true;
                       for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx-1][posiciony+1].posicionx + "," + juego[posicionx-1][posiciony+1].posiciony);
                }
                     descubrirMinaAdyacente(posicionx-1,  posiciony+1);
                   
                }            
            }
            if(esCasillaValida(posicionx-1,posiciony-1)){
                if(juego[posicionx-1][posiciony-1].tieneMina &&  !juego[posicionx-1][posiciony-1].revisada &&  !juego[posicionx-1][posiciony-1].tieneBandera){       
                   juego[posicionx-1][posiciony-1].casillaTablero.setEnabled(false);
                  juego[posicionx-1][posiciony-1].casillaTablero.setBackground(Color.red);
                    juego[posicionx-1][posiciony-1].revisada=true;
                     for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx-1][posiciony-1].posicionx + "," + juego[posicionx-1][posiciony-1].posiciony);
                }
                   descubrirMinaAdyacente(posicionx-1,  posiciony-1);
                 
                }            
            } 
            if(esCasillaValida(posicionx+1,posiciony+1)){
                if(juego[posicionx+1][posiciony+1].tieneMina &&  !juego[posicionx+1][posiciony+1].revisada &&  !juego[posicionx+1][posiciony+1].tieneBandera){              
                    juego[posicionx+1][posiciony+1].casillaTablero.setEnabled(false);
                     juego[posicionx+1][posiciony+1].casillaTablero.setBackground(Color.red);
                     juego[posicionx+1][posiciony+1].revisada=true;
                      for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx+1][posiciony+1].posicionx + "," + juego[posicionx+1][posiciony+1].posiciony);
                }
                    descubrirMinaAdyacente(posicionx+1,  posiciony+1);
                   
                }            
            } 
            if(esCasillaValida(posicionx+1,posiciony-1)){
                if(juego[posicionx+1][posiciony-1].tieneMina &&  !juego[posicionx + 1][posiciony - 1].revisada &&  !juego[posicionx + 1][posiciony - 1].tieneBandera ){                  
                    juego[posicionx+1][posiciony-1].casillaTablero.setEnabled(false);
                    juego[posicionx+1][posiciony-1].casillaTablero.setBackground(Color.red);
                     juego[posicionx+1][posiciony-1].revisada=true;
                      for (PrintWriter writer : writers) {
                    writer.println("EXPLOTADAS" + "," + juego[posicionx+1][posiciony-1].posicionx + "," + juego[posicionx+1][posiciony-1].posiciony);
                }
                      descubrirMinaAdyacente(posicionx+1,  posiciony-1);
                     
                }            
            }
        }
 
    }
    
    void descubrirCasilla(int posicionx, int posiciony){
         if(esCasillaValida(posicionx,posiciony)){
            juego[posicionx][posiciony].revisada=true;
            juego[posicionx][posiciony].casillaTablero.setEnabled(false);
            juego[posicionx][posiciony].casillaTablero.setText(String.valueOf(juego[posicionx][posiciony].numero));
    }
    }
    
    public void validarMinaMarcada(int posicionx, int posiciony){
       if(esCasillaValida(posicionx,posiciony)){
          if( juego[posicionx][posiciony].isTieneMina() && !juego[posicionx][posiciony].estaMarcada ){
              marcaValida++;
              juego[posicionx][posiciony].estaMarcada = true;
             
              System.out.println("Minas marcadas validas: " + marcaValida);
          }
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
    
    public String validarGanador(){
      int  contadorVerde = 0;
      int  contadorAmarillo = 0;
        for(int i=0;i < tamanox; i++){ 
           for(int j=0;j < tamanoy; j++){ 
            if(juego[i][j].banderaPertenece.equals("GREEN")){
                contadorVerde++;
            }else if(juego[i][j].banderaPertenece.equals("YELLOW")){
                contadorAmarillo++;
            }
        }      
        }   
        if(contadorVerde == Math.max(contadorVerde,contadorAmarillo)){
              return "GREEN";
           }else{
               return "YELLOW";
           }
    }
    
    public boolean validarCasillaMina(int posx, int posy){
        if(juego[posx][posy].tieneMina && !juego[posx][posy].estaMarcada){
            juego[posx][posy].estaMarcada = true;
            return true;
        }else{
            juego[posx][posy].estaMarcada = false;
            return false;
        }
    
    }
    
     public boolean validarCasillaMinaClick(int posx, int posy){
        if(juego[posx][posy].tieneMina){     
            return true;
        }
    return false;
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

            JOptionPane.showMessageDialog(null, "Perdiste");

        }else{
            
            if(map.get(e.getSource()).casillaTablero.isEnabled()){
              descubrirAdyacentes(map.get(e.getSource()).posicionx,map.get(e.getSource()).posiciony);  
            }
            map.get(e.getSource()).casillaTablero.setEnabled(false);  
            map.get(e.getSource()).casillaTablero.setText(String.valueOf(map.get(e.getSource()).numero));
            
            if(marcaValida == minasTablero){
                      JOptionPane.showMessageDialog(null, "Ganaste");
                      Jugador1.ganador = true;
                  }
           System.out.println("minas validas marcadas: "+marcaValida);
                  System.out.println("casillas abiertas: ");  
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
                     map.get(e.getSource()).estaMarcada=false;
                    if(marcaValida > 0 && map.get(e.getSource()).tieneMina){
                        System.out.println("llega a condicion");
                        map.get(e.getSource()).casillaTablero.setIcon(null);
                 map.get(e.getSource()).tieneBandera = false; 
                 marcaValida--;
                    }else if(marcaValida==0){
                        map.get(e.getSource()).casillaTablero.setIcon(null);
                 map.get(e.getSource()).tieneBandera = false;   
                    }
     map.get(e.getSource()).casillaTablero.setIcon(null);
                 map.get(e.getSource()).tieneBandera = false;
                }                
            }else{
                if((map.get(e.getSource()).casillaTablero.isEnabled())){
                     Image img = ImageIO.read(new FileInputStream("C:\\Users\\Fer\\Documents\\NetBeansProjects\\buscaminasServidor\\src\\images\\bandera.bmp"));
                 map.get(e.getSource()).casillaTablero.setIcon(new ImageIcon(img));
                  map.get(e.getSource()).tieneBandera = true;
                 
                }          
                  if(marcaValida == minasTablero){
                      JOptionPane.showMessageDialog(null, "Ganaste");
                      Jugador1.ganador = true;
                  }else{
                      System.out.println("Quedan: " + minasTablero + " minas");
                  }
                  System.out.println("minas validas marcadas: "+marcaValida);
                  System.out.println("casillas abiertas: ");
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
