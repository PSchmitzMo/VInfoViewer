/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author peter_2
 */
public class VInfoSet 
{
    private List<VInfoRecord> records;
    private final Map<Integer,VInfoRecord> recordMap = new TreeMap<>();
    
    public VInfoSet()
    {
        this.records = new ArrayList<>();
    }
    
    public VInfoSet( List<VInfoRecord> records )
    {
        this.records = new ArrayList<>();
        
        for( VInfoRecord record : records )
        {
            int id = Integer.parseInt( (String)record.getValue( "ID" ) );
            this.recordMap.put( id, record );
            this.records.add( record );
        }     
    }
    
    public int getRecordCount()
    {
        return this.records.size();
    }
    
    public VInfoRecord getRecord( int index )
    {
        return records.get( index );
    }
    
    public void merge( List<VInfoRecord> records )
    {
        for( VInfoRecord record : recordMap.values() )
        {
            record.alive( false );
        }

        for( VInfoRecord record : records )
        {
            int id = Integer.parseInt( (String)record.getValue( "ID" ) );
            this.recordMap.put( id, record );
        }    
        
        this.records = new ArrayList<>( recordMap.values() );
    }
}
