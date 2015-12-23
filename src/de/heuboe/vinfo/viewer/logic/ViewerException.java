package de.heuboe.vinfo.viewer.logic;

import de.heuboe.tim.timError;

@SuppressWarnings("serial")
public class ViewerException extends Exception
{
	private static final int ERR_FATAL_MIN = 1000;
	private static final int ERR_FATAL_MAX = 2000;
	
	public static final int ERR_FATAL_LOC = 101;
	public static final int ERR_FATAL_TIM = 1001;
	public static final int ERR_FATAL_DDP = 1002;
	
    public ViewerException(String message) 
    {
            super(message);
    }

    public ViewerException(String message, timError cause) 
    {
        super( cause );
                
        String msg = cause.message;
        if( msg == null )
            msg = cause.toString();
       
        this.message = message + ": " + msg;
    }
        
    public ViewerException(String message, Throwable cause) 
    {
        super( cause );
                
        String msg = cause.toString();
        if( msg == null )
            msg = cause.toString();
       
        this.message = message + ": " + msg;
    }
    
    public ViewerException( int errCode, String message) 
    {
        this.message = message;
	this.errCode = errCode;
    }

    public ViewerException( int errCode, String message, Throwable cause) 
    {
        String msg = cause.getMessage();
        if( cause instanceof timError )
            msg = ((timError)cause).message;
        
        if( msg == null )
            msg = cause.toString();
       
        this.message = message + ": " + msg;
	this.errCode = errCode;
    }
    
    public int getErrCode()
    {
    	return errCode;
    }
    
    @Override
    public String toString()
    {
    	return this.message;
    }
    
    public boolean isFatal()
    {
    	return ( errCode > ERR_FATAL_MIN ) && ( errCode < ERR_FATAL_MAX );
    }
    
    protected int errCode = 0;
    protected String message = "";	
}
