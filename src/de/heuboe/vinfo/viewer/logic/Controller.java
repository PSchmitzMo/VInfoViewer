/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import de.heuboe.tim.timElemInfoExt;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author peter_2
 */
public class Controller 
{
    private class Updater extends TimerTask
    {
        public Updater()
        {
            timer = new Timer();
            timer.schedule( this, 1000 );
        }
        
        Timer timer;
        public void run() 
        {
            
        }
    }
    
    private String filter = "MSE == 'DEHRF'";
    private final ViewerTableModel tableData;
    private TimServer timServer = null;
    private Updater updater;
    
    public Controller( ViewerTableModel tableData )
    {
        this.timServer = System.instance().getTimServer();
        this.tableData = tableData;
        
        updater = new Updater();
    }
    
    public void updateFilter( String filter )
            throws ViewerException
    {
        this.filter = filter;
        VInfoSet vInfoSet = readElements( filter );
        tableData.updateVInfoSet( vInfoSet );
    }    
    
    private VInfoSet readElements( String filter )
            throws ViewerException
    {
        timElemInfoExt[] elements = timServer.readFiltered( filter );

        List<VInfoRecord> records = new ArrayList<>();
        for( timElemInfoExt element : elements )
        {
            records.add( new VInfoRecord( element ) );
        }
        return new VInfoSet( records );
    }
}
