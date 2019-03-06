
package reloj.analogico.kevin.chaca;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RelojAnalogicoKevinChaca {

public static Aguja agujaHora, agujaMinuto, agujaSegundo;
public static int radio, xCent, yCent;
public static int segundo, minuto, hora;
public static double  divisionSesenta, divisionDoce, auxPosHor,  auxPosMin;
public static long  auxHor, auxMin, auxSeg, auxPosSeg;
public static Temporizador hiloSeg, hiloMin, hiloHor;
public static boolean inicioMin=false, inicioHor=false;
public static Ventana ventana;

protected static Graphics gOff;
protected static Dimension dimOff = null;


    public static void main(String[] args) {
        xCent=250;
        yCent=260;
        radio=260; 
        divisionSesenta= 2*Math.PI/60;
        divisionDoce= 2*Math.PI/12;
        agujaHora= new Aguja();
        agujaHora.inicializarParametros(xCent, yCent, radio-120, 50, Color.blue);
        agujaMinuto= new Aguja();
        agujaMinuto.inicializarParametros(xCent, yCent, radio-90, 30, Color.CYAN);
        agujaSegundo= new Aguja();
        agujaSegundo.inicializarParametros(xCent, yCent, radio-65, 10, Color.DARK_GRAY);
           
        ventana = new Ventana(radio,xCent, yCent, agujaHora, agujaMinuto, agujaSegundo);
        ventana.setVisible(true);
        
        //SACAR HORA EXACTA DEL SISTEMA
        Calendar calendario = new GregorianCalendar();
        Date fechaSistema = new Date();
        calendario.setTime(fechaSistema);
        
        int hora1=calendario.get(Calendar.HOUR_OF_DAY);
        int minuto1=calendario.get(Calendar.MINUTE);
        int segundo1=calendario.get(Calendar.SECOND);
        
        //INSTANCIAR LOS HILOS SEGUNDO, MINUTO Y HORA
        if (hora1>=12){
                hiloHor = new Temporizador(3600000,hora1-12, 12);    
            }
            else{
                hiloHor = new Temporizador(3600000,hora1, 12);
            }
        
        hiloMin = new Temporizador(60000, minuto1, 59);
        hiloSeg = new Temporizador(1000, segundo1, 59);
        hiloSeg.start();
        
        //WHILE INFINITO(HASTA QUE CIERRE LA VENTANA DEL RELOJ)
        while(true){
            try {
                Thread.sleep(999);
            } 
            catch (InterruptedException e) {}
            
            //COMPARAR SI ES AM O PM
            fechaSistema = new Date();
            calendario.setTime(fechaSistema);
            hora1=calendario.get(Calendar.HOUR_OF_DAY);
            if (hora1>=12){
                ventana.pmAm=true;    
            }
            else{
                ventana.pmAm=false;
            }
            
            //CARGAR EL ANGULO EN CADA MANESILLA
            auxPosSeg = hiloSeg.cantidad;
            if(hiloSeg.cantidad!=0){
                auxPosMin = hiloMin.cantidad + auxPosSeg/60d;
            }
            else{
                auxPosMin = hiloMin.cantidad + 60/60d;
            }
            
            if(hiloMin.cantidad==59 && hiloSeg.cantidad==0){
                auxPosHor = hiloHor.cantidad + 1;
            }
            else if(hiloMin.cantidad==0 && hiloSeg.cantidad==1){
                auxPosHor = hiloHor.cantidad;
            }
            else if(hiloMin.cantidad==0){
                auxPosHor = hiloHor.cantidad;
            }
            else{
                auxPosHor = hiloHor.cantidad + auxPosMin/60d;
            }
            agujaHora.inicializarAngulo(auxPosHor*divisionDoce);
            agujaMinuto.inicializarAngulo(auxPosMin*divisionSesenta);
            agujaSegundo.inicializarAngulo(auxPosSeg*divisionSesenta);
            
            //REDIBUJAR EL RELOJ CON LAS NUEVAS POSICIONES
            ventana.repaint();
            
            
            //INICIAR UNA SOLA VEZ HILO MINUTO PARA SINCRONIZARLE EN SEGUNDO 0
            if(hiloSeg.cantidad==0 && inicioMin==false){
               hiloMin.cantidad++;
               hiloMin.reiniciar();
               hiloMin.start();
               inicioMin=true;
            }
            
            //INICIAR UNA SOLA VEZ HILO HORA PARA SINCRONIZARLE EN MINUTO 0
            if(hiloMin.cantidad==0 && inicioHor==false ){
                hiloHor.cantidad++;
                hiloHor.reiniciar();
                hiloHor.start();
                inicioHor=true;
            }
         
            
            //MOSTRAR DATOS DE HILOS SEGUNDO, MINUTO Y HORA EN CONSOLA
            auxSeg = hiloSeg.obtenerCantidad();
            auxHor = hiloHor.obtenerCantidad();
            auxMin = hiloMin.obtenerCantidad();
            System.out.print("\n Hora: " + auxHor +"  Minutos: " + auxMin + "  Segundos: " + auxSeg);
     
        }
      
     }

}
