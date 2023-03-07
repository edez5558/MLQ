package fcfs;

public class Plan_RR extends Planificador{
   
    Plan_RR(){
        
    }
    
    @Override
    public void update(Proceso current) {
        if(current.isThreadWait()){
            current.unique();
            this._cola.add(current);
            this._cola.set(0,null);
        }else{
            removeOnList(current);
        }
        this._cola.remove(0);
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
