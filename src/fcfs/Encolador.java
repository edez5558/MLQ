package fcfs;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Encolador extends Thread{
    private String[] procesos;
    private Visualizador visu;
    private MLQ mlq;
    private Thread _update;
    
    Encolador(){
        this.procesos = new String[5];
        this.procesos[0] = "Internet";
        this.procesos[1] = "Java";
        this.procesos[2] = "Antivirus";
        this.procesos[3] = "Explorer";
        this.procesos[4] = "Spotify";
        
        this.visu = new Visualizador(5);
        this.mlq = new MLQ(this.visu);
        
        Planificador.setVisu(this.visu);
        
        this._update = new Thread(mlq);     
    }
    
    @Override
    public void run() {
        //Este hilo se encarga de encolar los procesos
        //Esperar a que el boton sea pulsado
        while(!visu.isStart()){
            try {
                this.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Encolador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Random rand = new Random();
        
        //El tiempo de ejecucion es un numero random entre 3-13
        int random_inferior = 3;
        int random_offset = 5;  
        int time = 0;
        
        
        
        if(true){//Random encolador
            this._update.start();
            for(int i = 0; i < 5; i++){
                time = rand.nextInt(random_offset - 1) + random_inferior;
                
                System.out.println("Encolando... ID: " + i + " Nombre: " + this.procesos[i] + " Time :" + time);
                this.mlq.encolar(this.procesos[i], time,rand.nextBoolean());

                //Un tiempo entre 1 a time-1
                int time_next_push = rand.nextInt(2) + 1;

                try {
                    this.sleep(1000 * time_next_push);
                } catch (InterruptedException ex) {
                    
                }

                time = rand.nextInt(9) + 2;
            }
        }else{//Encolar todos al inicio
            for(int i = 0; i < 5; i++){
                time = rand.nextInt(random_offset - 1) + random_inferior;
                
                System.out.println("Encolando... ID: " + i + " Nombre: " + this.procesos[i] + " Time :" + time);
                
                this.mlq.encolar(this.procesos[i], time,rand.nextBoolean());
                
                
                
            }
            this._update.start();
        }
        
        
        this.mlq.end();
        
        try {
            this._update.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Encolador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Encolador fcfs = new Encolador();
     
        fcfs.start();
        
        try {
            fcfs.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Encolador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}
