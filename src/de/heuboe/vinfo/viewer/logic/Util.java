/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import de.heuboe.log.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 *
 * @author peters
 *
 * Util class
 *
 */
public final class Util 
{
    private static final Logger LOGGER = Logger.getLogger( Util.class );
    
    public static final Long CONN_TIMEOUT = 300000L;		// ms

    private static String rdsDbVersion = "14.0";
    
    public static final String CFG_FILE_NAME = "de.heuboe.vinfo.viewer.cfg.file";
    public static final String CFG_HOST_KEY = "de.heuboe.vinfo.viewer.cfg.host";
    public static final String CFG_PORT_KEY = "de.heuboe.vinfo.viewer.cfg.port";
    public static final String CFG_UPD_INTERVAL_KEY = "de.heuboe.vinfo.viewer.cfg.update.interval";
    
    public static Properties configProperties = new Properties();
    
    public static String CFG_HOST = "HOST";
    public static String CFG_PORT = "9999";
    public static String CFG_UPD_INTERVAL = "60";
    

    private Util() 
    {
    }
    
    public static Properties getConfigProperties()
    {
        return configProperties;
    }
    
    private static Properties readConfigFile()
    {
        Properties  props = new Properties();
	InputStream input = null;
        
        String propertyFile = java.lang.System.getProperty( CFG_FILE_NAME );

	try 
        {
            input = new FileInputStream( propertyFile );
            props.load(input);
            
            return props;
	} 
        catch (IOException ex) 
        {
             LOGGER.error( "Cannot read configuration property file <" + propertyFile + ">" );
             LOGGER.error( ex.toString() );
	} 
        finally 
        {
            if (input != null) 
            {
                try 
                {
                    input.close();
                } 
                catch( IOException ex ) 
                {
                    LOGGER.error( "Cannot close property file <" + propertyFile + ">" );
                    LOGGER.error( ex.toString() );
                }
            }
	}
    
        return null;
    }
    

    public static String getRdsDbVersion() 
    {
        return rdsDbVersion;
    }
    
    public static void notifyPropertiesUpdate( Properties properties )
    {
        String host = properties.getProperty( CFG_HOST_KEY );
        if( host != null )
        {
            CFG_HOST = host;
             configProperties.setProperty( CFG_HOST_KEY, host);
       }
        
        String port = java.lang.System.getProperty( CFG_PORT_KEY );
        if( port != null )
        {
            CFG_PORT = port;
            configProperties.setProperty( CFG_PORT_KEY, port);
        }
    }
    
    public static void init()
    {
        Properties props = readConfigFile();
        notifyPropertiesUpdate( props );

        notifyPropertiesUpdate( java.lang.System.getProperties() );
    }

    public static void setRdsdbVersion(String rdv) 
    {
        rdsDbVersion = rdv;
    }

    /**
     *
     * Executes exit()
     *
     * @param code exit code
     */
    @SuppressWarnings("deprecation")
    public static final void exit(int code) 
    {
        de.heuboe.system.System.exit(code);
    }

    /**
     *
     * executes Sleep
     *
     * @param ms milliseconds
     */
    public static final void sleep(int ms) 
    {
        try 
        {
            Thread.sleep(ms);
        } 
        catch ( Throwable ex ) 
        {					  
        }
    }

    /**
     *
     * executes Sleep for 5000 ms
     *
     */
    public static final void pause() 
    {
        try 
        {
            Thread.sleep(5000);
        } 
        catch (Throwable ex) 
        {				
        }
    }

    /**
     *
     * Throwable to String
     *
     * @param ex throwable
     * @return
     */
    public static String toString(Throwable ex) 
    {
        String msg = ex.getMessage();
        if (msg == null) 
        {
            msg = Util.toString(ex);
        }

        return msg;
    }
}
