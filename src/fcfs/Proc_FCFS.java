package fcfs;

public class Proc_FCFS extends Proceso{

    public Proc_FCFS(String name,int id,int time){
        super(name,id,time);
    }
    
    @Override
    public boolean isEnd() {
        return this.getState() == Thread.State.TERMINATED;
    }

    @Override
    public boolean isThreadWait() {
        return false;
    }

    @Override
    public long getRunTime() {
        if(this._start == 0) return 0;
        
        return System.currentTimeMillis() - this._start;
    }

    @Override
    public long getRemainingTime() {
        final long max = this._time * 1000;
        
        long rem_time = max - this.getRunTime();
        
        return (rem_time > max? max : rem_time);
    }

    @Override
    public float getProgressTime() {
        return (float)this.getRunTime()/(float)(this._time * 1000);
    }

    @Override
    public void setOnWait() {
        
    }

    @Override
    public void setOnNotify() {
        
    }
    
    @Override
    public void run(){
        this._start = System.currentTimeMillis();
        
        try{
            this.sleep(this._time * 1000);
        }catch(InterruptedException ex){
            
        }
        
        this._end = System.currentTimeMillis();
    }

    @Override
    public void unique() {
        
    }
    
}
