
package fcfs;

public class Proc_RR extends Proceso{
    private final int _quatum;
    private boolean _readyRunTime;
    
    private long _lastStart;
    private long _lastSave;
    private long _totalRun;
    
    private long _totalWait;
    
    
    Proc_RR(String name,int id, int time_seconds,int quatum){
        super(name,id,time_seconds);
        
        this._totalRun = 0;
        this._lastSave = 0;
        this._lastStart = 0;
        this._quatum = quatum;
             
        this._totalWait = 0;
        
        this._readyRunTime = true;
    }
    
    public String getProcesoState(){
        return "El proceso " + this._name + " se encuentra en el estado: " + this.getState();
    }
    
    @Override
    public boolean isEnd(){
        return this.getState() == Thread.State.TERMINATED;
    }
    
    public long getTurnaround(long init_milli){
        if(!this.isEnd()) return -1;
        return this._end - init_milli;
    }
    
    public long getWaitTime(){
        if(!this.isEnd()) return -1;
        
        return this._totalWait;
    }
    
    public long getResponseTime(){
        if(!this.isEnd()) return -1;
        
        return this._start - this._arrival;
    }
    
    @Override
    public boolean isThreadWait(){
        return this.getState() == Thread.State.WAITING;
    }  
    
    @Override
    public synchronized long getRunTime(){
        if(this._start == 0) return 0;  
        
        if(!this._readyRunTime){
            return this._totalRun;
        }

        return this._totalRun + System.currentTimeMillis() - this._lastStart;
    }
    
    @Override
    public float getProgressTime(){
        return (float)this.getRunTime()/(float)(this._time * 1000);
    }
    
    @Override
    public long getRemainingTime() {
        final long max = this._time * 1000;
        
        long rem_time = max - this.getRunTime();
        
        return (rem_time > max? max : rem_time);
    }
    
    public void updateTotalRun(){
        this._totalRun += this._lastSave - this._lastStart;
    }
    
    @Override
    public void run(){
        synchronized(this){
            this._start = System.currentTimeMillis();
            this._lastStart = this._start;
            //Tiempo de espera
            _totalWait += this._start - this._arrival;
        }
        
        int residual= this._time % _quatum;
        int div_task =  this._time/_quatum;
        
        try {      
            for(int i=0; i < div_task; i++ ){
                this.sleep(1000*_quatum);
                
                synchronized(this){
                    this._lastSave = System.currentTimeMillis();
                    this._readyRunTime = false;
                    
                    if(i != div_task - 1 || residual != 0){
                        System.out.println("El proceso " + this._name + " se ha puesto en espera");
                        this.wait();
                        System.out.println("El proceso " + this._name + " se ha reanurado");
                    }
                    
                    this._lastStart = System.currentTimeMillis();
                    this._readyRunTime = true;
                    
                    _totalWait += this._lastStart - this._lastSave;
                }
            }
            
            if(residual != 0)
                this.sleep(residual * 1000);
            
        } catch (InterruptedException ex) {
            
        }
        
        synchronized(this){
            this._end = System.currentTimeMillis();
        }
    }
    

    @Override
    public void setOnWait() {
        synchronized(this){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                
            }
        }
    }

    @Override
    public void setOnNotify() {
        synchronized(this){
            this.notify();
        }
    }

    @Override
    public void unique() {
        this.updateTotalRun();
    }
    
}
