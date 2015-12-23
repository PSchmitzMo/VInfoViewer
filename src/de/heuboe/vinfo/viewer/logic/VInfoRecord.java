/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import de.heuboe.tim.timElemInfoExt;
import de.heuboe.tim.timShortAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author peter_2
 */
public class VInfoRecord 
{
    private final timElemInfoExt element;
    private boolean alive = true;
    private final Map< String, List<timShortAttribute> > attrValues = new TreeMap<>(); 
    
    public VInfoRecord( timElemInfoExt element )
    {
         this.element = element;   
         
         for( timShortAttribute attr : element.attributes )
         {
             String attrName = attr.code;
             List<timShortAttribute> values = attrValues.get( attrName );
             if( values == null )
             {
                 values = new ArrayList<>();
                 attrValues.put( attrName, values );
             }
             
             values.add( attr );
         }
    }

    public Object getValue( String attrName )
    {
        if( attrName.equals( "ID" ) )
            return "" + element.elemRef;
        
        List<timShortAttribute> values = attrValues.get( attrName );
        if( ( values == null ) || values.isEmpty() )
            return null; 

        return values.get( 0 ).value;
    }

    public void alive( boolean alive )
    {
        this.alive = alive;
    }
    
    public boolean alive()
    {
        return this.alive;
    }
}
