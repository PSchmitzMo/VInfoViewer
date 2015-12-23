/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import de.heuboe.tim.timElemInfoExt;

/**
 *
 * @author peter_2
 */
public final class System 
{
    private TimServer timServer  = null; 
    private static System instance = null;
      
    public static System instance()
    {
        if( instance == null )
            instance = new System();

        return instance;
    }
    
    private System()
    {
        
    }
    
    public TimServer getTimServer ()
    {
        return timServer;
    }
    
    public void init()
    {
        try
        {
            timServer = new TimServer( "timSrv", "VInfoViwer_" + java.lang.System.currentTimeMillis() );
            timServer.ping();
            timElemInfoExt[] elements = timServer.readFiltered( "MSE == 'DEHRF'" );
        }
        catch( ViewerException ex )
        {
            java.lang.System.out.println( ex.toString() );
        }
    }
}
