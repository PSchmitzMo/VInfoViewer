/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;



import de.heuboe.corba.CorbaClient;
import de.heuboe.log.Logger;
import de.heuboe.tim.NO_USER_ID;
import de.heuboe.tim.timAttribute;
import de.heuboe.tim.timElemInfo;
import de.heuboe.tim.timElemInfoExt;
import de.heuboe.tim.timElement;
import de.heuboe.tim.timErrorHBException;
import de.heuboe.tim.timErrorInvalidData;
import de.heuboe.tim.timLink;
import de.heuboe.tim.timChain;
import de.heuboe.tim.timError;
import de.heuboe.tim.timErrorUnknownId;
import de.heuboe.tim.timFilter;
import de.heuboe.tim.timLifeline;
import de.heuboe.tim.timLifelineHelper;
import de.heuboe.tim.timManager;
import de.heuboe.tim.timManagerHelper;
import de.heuboe.tim.userInfo;
import de.heuboe.util.CallStack;
import java.util.Date;


/**
 * 
 * @author peter_2
 */
public class TimServer
{
    public class TimLifelineThread extends Thread
    {
        private int m_pingInterval = 0;
        private timLifeline m_timLifeline = null;

        public TimLifelineThread( int piv, timLifeline tll )
        {
            m_pingInterval = piv;
            m_timLifeline = tll;

            m_timLifeline.setPingInterval( m_pingInterval );
            m_timLifeline.ping();
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                        m_timLifeline.ping();
                }
                catch( Throwable ex )
                {
                        LOGGER.fatal( ex.getMessage() );
                        LOGGER.fatal( "Prozess nach Fehler beendet" );
                        LOGGER.fatal( CallStack.getStackTraceAsString( ex ) );
                        Util.exit(-1);
                }

                try
                {
                        sleep( m_pingInterval * 1000L / 3L );
                } 
                catch (InterruptedException e)
                {
                        break;
                }
            }

            m_timLifeline = null;
    }

}

    private static final Logger LOGGER = Logger.getLogger( TimServer.class );
    private timManager timServer = null;
    private int clientId;
    private TimLifelineThread lifelineThread = null;


    public TimServer( String timSrvURL, String instance )
                    throws ViewerException
    {
        try
        {
            timServer = timManagerHelper.narrow( CorbaClient.getNamedObject( timSrvURL ) );

            timLifeline tll = timLifelineHelper.narrow( 
                            timServer.registerClient( "VInfoViewer_" + (new Date()).getTime() ) );


            if (tll != null)
            {
                clientId = tll.getId();

                int pi = 900;

                String globalPi = java.lang.System.getProperty( "de.heuboe.corba.ping.interval" );
                if( globalPi != null )
                        pi = Integer.parseInt( globalPi );

                lifelineThread = new TimLifelineThread( pi, tll );
                lifelineThread.start();
            }
        }
        catch( Exception ex )
        {
            throw new ViewerException( "Fehler bei der Verbindungsaufnahme zum timSrv", 
                                       ex );
        }
    }

    public void ping()
    {
        timServer.ping();
    }


    public timElemInfo read( int id )
                    throws ViewerException
    {
        try
        {
            int[] ids = new int[1];
            ids[0] = id;
            timElemInfo[] elements = timServer.getElementInfos( clientId, ids );

            if( ( elements == null ) || ( elements.length == 0 ) )
                    return null;

            return elements[0];
        }
        catch( timError ex )
        {
            throw new ViewerException( "TIM-Fehler", ex );
        }
    }
	
	
    public timElemInfoExt readDetailed( int id )
                    throws ViewerException
    {
        try
        {
            int[] ids = new int[1];
            ids[0] = id;
            timElemInfoExt[] elements = timServer.getElements( clientId, ids );

            if( ( elements == null ) || ( elements.length == 0 ) )
                    return null;

            return elements[0];
        }
        catch( timError ex )
        {
            throw new ViewerException( "TIM-Fehler", ex );
        }
    }
    
    public timElemInfoExt[] readFiltered( String filterExpr )
                    throws ViewerException
    {
        try
        {
            timFilter filter = new timFilter( filterExpr, false );
            timElemInfoExt[] elements = timServer.filterElementInfos( clientId, filter );
            
            return elements;

        }
        catch( timErrorUnknownId ex) 
        {
            throw new ViewerException( ViewerException.ERR_FATAL_TIM, "TIM-Fehler", ex);
        }
        catch( timError ex )
        {
            throw new ViewerException( ViewerException.ERR_FATAL_TIM, "TIM-Fehler", ex );
        }
    }
    
	
    public timElement newElement()
                    throws ViewerException
    {
        try 
        {
            return timServer.newElement(clientId);
        } 
        catch (timError ex) 
        {
            throw new ViewerException("TIM-Fehler", ex);
        } 
        catch (timErrorUnknownId ex) 
        {
            throw new ViewerException("TIM-Fehler", ex);
        }
    }
	
	
    public timAttribute newAttribute()
    {
        timAttribute attr = new timAttribute();
        attr.unit = "";
        attr.codeText = "";
        attr.valueText = "";
        attr.unitText = "";
        attr.valueFText = "";

        return attr;
    }

	
    public void eraseAll( String filterExpr )
                    throws ViewerException
    {

        try
        {
            timFilter filter = new timFilter( filterExpr, false );
            timElemInfo[] elements = timServer.filterElements( clientId, filter );

            userInfo ui = new userInfo( NO_USER_ID.value, "", new int[0] );
            timServer.commit( clientId, 
                              new timElemInfo[0],
                              elements, 
                              new timLink[0], 
                              new timLink[0], 
                              new timChain[0], 
                              new timChain[0], 
                              ui );
        }
        catch( timError ex )
        {
            throw new ViewerException( "TIM-Fehler: " + ex.message, ex );
        }
        catch( timErrorHBException ex )
        {
            throw new ViewerException( "TIM-Fehler: " + ex.hbMessage, ex );
        }
        catch( timErrorInvalidData ex )
        {
            throw new ViewerException( "TIM-Fehler <" + ex.objType + ":" + ex.objStr + ">", ex );
        }
        catch( timErrorUnknownId ex )
        {
            throw new ViewerException( "TIM-Fehler: <" + ex.objType + ":"  + ex.id + ">", ex );
        }
    }
}
