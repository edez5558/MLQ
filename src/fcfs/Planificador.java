package fcfs;

import fcfs.Proceso;
import fcfs.Visualizador;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Planificador extends Thread{
    protected ArrayList<Proceso> _cola;
    
    protected static Visualizador _visu; 
    private static ArrayList<Proceso> _main_list;
    
    protected boolean isRunning;
    
    Planificador(){
        this._cola = new ArrayList<Proceso>();
        isRunning = false;
    }
    
    @Override
    public void run(){
        this.init();
        this.thread_func();
    }
    
    public boolean isRun(){
        return isRunning;
    }
    
    public boolean isEmpty(){
        return this._cola.isEmpty();
    }
    
    public static void setVisu(Visualizador ref_visu){
        Planificador._visu = ref_visu;
    }
    
    public static void setMainList(ArrayList<Proceso> main_list){
        _main_list = main_list;
    }
    
    public static void removeOnList(Proceso a){
        _main_list.remove(a);
    }
    
    public ArrayList<Proceso> getCola(){
        return _cola;
    }
    
    public void thread_func(){
        Proceso current = null;
        
        while(isRunning){
            if(this._cola.isEmpty() && isRunning){
                synchronized(this){
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            
            while(!this._cola.isEmpty()){
                current = _cola.get(0);

                if(current.isThreadWait()){
                    current.setOnNotify();
                }else{
                    current.start();
                }

                _visu.updateProcess(current);

                if(current.isThreadWait()){
                    _visu.setRunHorse(current.getID());
                }else{
                    _visu.setInactiveHorse(current.getID());
                }

                this.update(current);
            }  
        }
    }
    
    public abstract void init();
    public abstract void update(Proceso b);
    public abstract void encolar(Proceso a);
    public abstract void terminate();
}
