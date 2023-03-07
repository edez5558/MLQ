package fcfs;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MLQ implements Runnable{
    private final Visualizador _visu;
    private final ArrayList<Proceso> _process;
    private boolean isRunning;
    
    private Plan_RR round_robin;
    private Plan_FCFS fcfs;
    
    int id;
    
    MLQ(Visualizador ref_visu){
        this._visu = ref_visu;
        this._process = new ArrayList<Proceso>();
        Planificador.setMainList(this._process);
        this.isRunning = true;
        
        this.round_robin = new Plan_RR();
        this.fcfs = new Plan_FCFS();
        
        this.id = 0;
    }
    
    @Override
    public void run() {
        if(_visu == null || _process == null){
            System.out.println("Error null pointer");
            return;
        }
        
        this.round_robin.start();
        this.fcfs.start();
        
        while(isRunning){
            if(!this.round_robin.isEmpty() && this.round_robin.getState() == Thread.State.WAITING){
                synchronized(this.round_robin){
                    this.round_robin.notify();
                }
            }
            
            if(!this.fcfs.isEmpty() && this.fcfs.getState() == Thread.State.WAITING){
                synchronized(this.fcfs){
                    this.fcfs.notify();
                }
            }
        }
        
        try {
            this.round_robin.join();
            this.fcfs.join();
        } catch (InterruptedException ex) {
        }
    }
    
    public void encolar(String name,int time,boolean random){
        Proceso aux = null;
        if(random){
            aux = new Proc_RR(name,id,time,1);
            
            this.round_robin.encolar(aux);
            
            this._visu.setHorseIcon(id);
            
            this._process.add(aux);
        }else{
            aux = new Proc_FCFS(name,id,time);
            
            this.fcfs.encolar(aux);

            this._visu.setPandaIcon(id);
            
            this._process.add(aux);
        }
        
        this._visu.setRunHorse(id);
        id++;
    }
    
    public void end(){
        isRunning = false;
        this.fcfs.terminate();
        this.round_robin.terminate();
    }
    
}
