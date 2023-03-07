
package fcfs;

public abstract class Proceso extends Thread{
    protected final int _time;
    protected final String _name;
    protected final int _id;
    
    protected final long _arrival;
    protected long _start;
    protected long _end;
    
    
    Proceso(String name,int id,int time_seconds){
        this._id = id;
        this._name = name;
        this._time = time_seconds;
        this._arrival = System.currentTimeMillis();
        
        this._start = 0;
        this._end = 0;
        
    }
    
    public int getTime(){
        return this._time;
    }
    
    public int getID(){
        return _id;
    }
    
    public abstract boolean isEnd();
    public abstract boolean isThreadWait();
    
    public abstract long getRunTime();
    public abstract long getRemainingTime();
    public abstract float getProgressTime();
    
    public abstract void setOnWait();
    public abstract void setOnNotify();
    
    public abstract void unique();
}
