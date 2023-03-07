package fcfs;

public class Plan_FCFS extends Planificador{
   
    Plan_FCFS(){
        
    }
    
    @Override
    public void update(Proceso current) {
        this._cola.remove(0);
        removeOnList(current);
    }

    @Override
    public void init() {
        this.isRunning = true;
    }

    @Override
    public void terminate() {
        this.isRunning = false;
    }

    @Override
    public void encolar(Proceso a) {
        this._cola.add(a);
    }

    
}
