/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import de.heuboe.tim.timAttrType;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author peter_2
 */
public class ViewerTableModel extends AbstractTableModel
{
    public static class ViewerColumn
    {
        private String name;
        private timAttrType type;
        
        public ViewerColumn( String name, timAttrType type ) 
        {
            this.name = name;
            this.type = type;
        }

        public String getName() 
        {
            return name;
        }

        public void setName(String name) 
        {
            this.name = name;
        }

        public timAttrType getType() 
        {
            return type;
        }

        public void setType(timAttrType type) 
        {
            this.type = type;
        }
    }
    
    private ViewerColumn[] viewerColumns = 
    {
        new ViewerColumn( "ID", timAttrType.timATLong ), 
        new ViewerColumn( "INP", timAttrType.timAtDateTime ), 
        new ViewerColumn( "STA", timAttrType.timAtDateTime ), 
        new ViewerColumn( "MSE", timAttrType.timATString ), 
        new ViewerColumn( "SNM", timAttrType.timATString ), 
        new ViewerColumn( "ERF", timAttrType.timATString ), 
        new ViewerColumn( "DOB", timAttrType.timATString ), 
        new ViewerColumn( "PHR", timAttrType.timATString ), 
        new ViewerColumn( "@PL", timAttrType.timATLong ), 
        new ViewerColumn( "@SL", timAttrType.timATLong ), 
    };
  
    private VInfoSet vinfoSet;
    
    public ViewerTableModel()
    {
        vinfoSet = new VInfoSet();
    }
    
    public ViewerTableModel( ViewerColumn[] viewerColumns, VInfoSet vinfoSet )
    {
        this.viewerColumns = viewerColumns;
        this.vinfoSet = vinfoSet;
    }
    
    public void init( TableColumnModel tcm )
    {
        int index = 0;
        for( ViewerColumn vc : viewerColumns )
        {
            if( vc.getType() == timAttrType.timAtDateTime )
                tcm.getColumn( index ).setPreferredWidth( 330 );

            if( vc.getType() == timAttrType.timATString )
                tcm.getColumn( index ).setPreferredWidth( 170 );

            if( vc.getType() == timAttrType.timATLong )
                tcm.getColumn( index ).setPreferredWidth( 170 );
             
            index++;            
        }
    }
    
    @Override
    public String getColumnName( int index )
    {
        return viewerColumns[index].getName();
    }
    
    @Override
    public int getRowCount() 
    {
        return vinfoSet.getRecordCount();
    }
    
    public boolean rowAlive( int rowIndex )
    {
         VInfoRecord record = vinfoSet.getRecord( rowIndex );
         return record.alive();
    }

    @Override
    public int getColumnCount() 
    {
        return viewerColumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        VInfoRecord record = vinfoSet.getRecord( rowIndex );
        return record.getValue( getColumnName( columnIndex ) );
    }
    
    public ViewerColumn[] getColumns()
    {
        return viewerColumns;
    }
    
    public void updateVInfoSet( VInfoSet vinfoSet )
    {
        this.vinfoSet = vinfoSet;
        fireTableDataChanged();
    }
}
