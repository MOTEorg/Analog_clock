
package reloj.analogico.kevin.chaca;

import static java.lang.Thread.interrupted;
import java.util.Iterator;

public class Temporizador extends Thread {
    public int espera, cantidad, maximo;
    
    public Temporizador (int espera, int cantInic, int max) {		
        this.espera = espera;
        this.cantidad = cantInic;
        this.maximo=max;
    }	
    
    public int obtenerCantidad(){
    return this.cantidad;
    }
    
    public void reiniciar(){
        if(this.cantidad>this.maximo){
            this.cantidad=0;
        }
    }
    
    public void run(){ 
        while (!interrupted()) {			
           try {
                Thread.sleep(espera);
            } 
            catch (InterruptedException e) {
              break;
            } 
           this.cantidad++;
           this.reiniciar();   
       }
        
        
    }
}
