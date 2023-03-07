
package fcfs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Visualizador extends JFrame implements ActionListener{
    private ImageIcon _horseRun;
    private ImageIcon _pandaRun;
    private ImageIcon _horseInactive;
    private ImageIcon[] _Running;
    private JTextArea _loginfo;
    private JLabel[] _horses;
    
    private JPanel _panel;
    
    private int _nProcess;
    
    private boolean _start;
    private JButton btnStart;
    
    
    
    Visualizador(int numerate_of_process){
        this._nProcess = numerate_of_process;
        this._horses = new JLabel[this._nProcess];
        this._Running = new ImageIcon[this._nProcess];
        this._start = false;
        
        this.setSize(640,480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
        
        this.initPanel();
        this.initButton();
        
        this.setVisible(true);
    }
    
    
    
    public void updateProcess(Proceso process){
        float progress = 0;
        float last_progress = 0;
        float remaining = 0;
        
        while(true){
            synchronized(process){
                if(process.isEnd() || process.isThreadWait()) break;
                progress = process.getProgressTime();
                remaining = process.getRemainingTime()/1000.0f;
                
            }
            
            if(last_progress > progress) continue;
            
            last_progress = progress;
            movHorse(process.getID(),progress,remaining);
        }
    }
    
    public boolean isStart(){
        return this._start;
    }
    
    public void setRemaining(int id,float remaining){
        this._horses[id].setText(String.format("%1$.1f",remaining));
    }
    
    public void movHorse(int id, double distance, float remaining){
        if(id >= 0 && id < this._horses.length){
            this._horses[id].setText(String.format("%1$.1f",remaining));
            int valueX = (int)((this._panel.getBounds().getWidth() - this._horseRun.getIconWidth()) * distance);
            
            this._horses[id].setLocation(valueX,id*(this._horseRun.getIconHeight() + 15));
        }
    }
    
    public void setRunHorse(int id){
        if(id >= 0 && id < this._horses.length){
            this._horses[id].setIcon(this._Running[id]);
        }
    }
    
    public void setInactiveHorse(int id){
        if(id >= 0 && id < this._horses.length){
            this._horses[id].setIcon(this._horseInactive);
        }
    }
    
    public void setInfo(String info){
        this._loginfo.setText(info);
    }
    
    public void setPandaIcon(int id){
        _Running[id] = this._pandaRun;
    }

    public void setHorseIcon(int id){
        _Running[id] = this._horseRun;
    }
    
    private void initPanel(){
        this._panel = new JPanel();
        this._loginfo = new JTextArea();
        this._loginfo.setEditable(false);
        
        ImageIcon horse;
        Image res_img;
      
        int horse_width = 80;
        int horse_height = 50;
        
        this._panel.setLayout(null);
   
        this.getContentPane().add(this._panel);
        
        horse = new ImageIcon("horse.gif");
        res_img = horse.getImage().getScaledInstance(horse_width, horse_height - 15, Image.SCALE_DEFAULT);
        
        this._horseRun = new ImageIcon(res_img);
        
        horse = new ImageIcon("panda.gif");
        res_img = horse.getImage().getScaledInstance(horse_width, horse_height - 15, Image.SCALE_DEFAULT);
        this._pandaRun = new ImageIcon(res_img);
                
        
        horse = new ImageIcon("no_define.jpg");
        res_img = horse.getImage().getScaledInstance(horse_width, horse_height - 15, Image.SCALE_DEFAULT);
        this._horseInactive = new ImageIcon(res_img);
        
        
        
        for(int i = 0; i < _nProcess; i++){
            _horses[i] = new JLabel(this._horseInactive);
            
            Font _f = _horses[i].getFont();
            _horses[i].setBounds(0,i*horse_height,horse_width,horse_height);
            _horses[i].setText(" ");
            _horses[i].setFont(new Font(_f.getFontName(),Font.PLAIN,10));
            _horses[i].setForeground(new Color(31,67,105));
            _horses[i].setHorizontalTextPosition(JLabel.CENTER);
            _horses[i].setVerticalTextPosition(JLabel.BOTTOM);
            
            this._panel.add(_horses[i]);
        }
        
        this._loginfo.setBounds(0,_nProcess * horse_height + 20,500,100);
        
        this._panel.add(this._loginfo);
        
    }
    
    
    private void initButton(){
        btnStart = new JButton("Empezar");
        
        btnStart.setBounds(260,400,100,20);
        btnStart.setMnemonic('s');
        btnStart.setName("start");   
        
        btnStart.addActionListener(this);
        
        this._panel.add(btnStart);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnStart){
            this._start = true;
        }
    }

    
    
}
