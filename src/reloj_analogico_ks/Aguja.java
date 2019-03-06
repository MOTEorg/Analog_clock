
package reloj.analogico.kevin.chaca;

import java.awt.*;


public class Aguja {
    public int xCentral=0, yCentral=0, radio=1, grosor;
    public Color color;
    public double angulo=0d;
    public static double media=Math.PI, cuarto=Math.PI/2;
    public int puntosX[]= new int[4];
    public int puntosY[]= new int[4];
    public double divisionCuatro;
    
    public void inicializarParametros(int xCent, int yCent, int rad, int gros, Color col){
        this.xCentral=xCent;
        this.yCentral=yCent;
        this.radio=rad;
        this.grosor=gros;
        this.color=col;
        this.calucularForma();
    }
    
    public void inicializarAngulo(double ang){
        this.angulo=ang;
        this.calucularForma();
    }
    
    public void calucularForma(){
       int gros = grosor/2;
       divisionCuatro= 2*Math.PI/4;
       double angle = -this.angulo + divisionCuatro;

        puntosX[0] = (int)(xCentral + radio * Math.cos (angle));
        puntosY[0] = (int)(yCentral - radio * Math.sin (angle));

        for (int i=1; i<4; ++i)
        {
          angle += divisionCuatro;
          puntosX[i] = (int)(xCentral + gros * Math.cos (angle));
          puntosY[i] = (int)(yCentral - gros * Math.sin (angle));
        }
       
    }
    
    public void pintar(Graphics g){
        g.setColor(color);
        g.fillPolygon(puntosX, puntosY, 4);
        g.setColor(color.brighter());
        g.drawPolygon(puntosX, puntosY, 4);
    }


}

