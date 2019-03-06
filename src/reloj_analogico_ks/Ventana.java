
package reloj.analogico.kevin.chaca;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;




public class Ventana extends JFrame {
    public Aguja agujaHora, agujaMinuto, agujaSegundo;
    public double divisionSesenta;
    public double divisionDoce;
    static String[] numeros = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "1", "2"}; 
    public static int xCent, yCent, radio;  
    public boolean pmAm=false;
    public JPanel contentPane, contentPaneRepintado;
    
    public boolean primeraVez=false;
    
    
    
    
    
    public Ventana(int radio, int xCent, int yCent, Aguja agujaHor, Aguja agujaMin, Aguja agujaSeg){
        this.xCent = xCent;
        this.yCent= yCent;
        this.radio=radio;

        this.agujaHora=agujaHor;
        this.agujaMinuto=agujaMin;
        this.agujaSegundo=agujaSeg;
        
        this.setTitle("Reloj Analogico Kevin Chaca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 150, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.gray);
        
        
    }
   
    
    public void paint(Graphics g){
        super.paint(g);
       
        divisionSesenta= 2*Math.PI/60;
        divisionDoce= 2*Math.PI/12;
        
        //Dibujar DIVISIONES DE MINUTOS/SEGUNDOS Y HORAS
        int m=50;
        int r1 = radio-m;
        int r2 =r1-m/2;
        int contadorHora=0,contadorSegundo=0;
        
        for (double angulo=0d; angulo < 2*Math.PI-0.00001; angulo += divisionSesenta){
            int x1 = (int)(xCent + r1 * Math.cos(angulo));
            int y1 = (int)(yCent + r1 * Math.sin(angulo));
            int x2 = (int)(xCent + r2 * Math.cos(angulo));
            int y2 = (int)(yCent + r2 * Math.sin(angulo));
            
            if (contadorHora==contadorSegundo){
                g.setColor (Color.white);
                g.fillOval (x2-5, y2-5, 10, 10);
                contadorHora+=5;
            }
            else{
                g.setColor (Color.black);
                g.drawLine (x1, y1, x2, y2);
            }
            contadorSegundo++;
        }
        
        //DIBUJAR NUMEROS
        FontMetrics variable = g.getFontMetrics(g.getFont());
        int desc = variable.getMaxDescent()*2;
        int altura = variable.getHeight();
        int radioNumero = this.radio - altura*3;
        g.setColor (Color.white);
        
        for (int i=0; i<12; ++i){
            int x1 = (int)(xCent + radioNumero * Math.cos (i * divisionDoce));
            int y1 = (int)(yCent + radioNumero * Math.sin (i * divisionDoce));
            String num = numeros[i];
            int auxiliar = variable.stringWidth (num);
            g.drawString (num, x1 - auxiliar/2, y1 + desc);
            g.drawOval (x1-altura, y1-altura, altura*2, altura*2);
        } 
        
        //PONER NOMBRE
        g.setFont( new Font("Arial",Font.PLAIN,24 ));
        g.setColor(Color.lightGray);
        g.drawString("Kevin Chaca",xCent-70,yCent-90);
        
        //PONER AM O PM
        if(this.pmAm==false){
            g.setFont( new Font("Arial",Font.PLAIN,34 ));
            g.drawString("AM",xCent-25,yCent+90);
        }
        else{
            g.setFont( new Font("Arial",Font.PLAIN,34 ));
            g.drawString("PM",xCent-25,yCent+90);
        }
        
        //DIBUJAR LAS MANESILLAS
        agujaHora.pintar(g);
        agujaMinuto.pintar(g);
        agujaSegundo.pintar(g);
        
    }

}
  
