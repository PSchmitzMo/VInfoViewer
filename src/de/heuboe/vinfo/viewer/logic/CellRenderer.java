/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import de.heuboe.tim.timAttrType;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author peter_2
 */

public class CellRenderer implements TableCellRenderer 
{ 
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer(); 
    
    static 
    {
        DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.RIGHT);
    }
    
    private final ViewerTableModel tableModel;
    
    public CellRenderer( ViewerTableModel tableModel )
    {
        this.tableModel = tableModel;
    }
    
    @Override 
    public Component getTableCellRendererComponent( JTable table, 
                                                    Object value, 
                                                    boolean isSelected, 
                                                    boolean hasFocus, 
                                                    int row, 
                                                    int column ) 
    { 
        Object val = value;
        ViewerTableModel.ViewerColumn viewerColumn = tableModel.getColumns()[column];
        if( viewerColumn.getType() == timAttrType.timAtDateTime )
        {
            if( value != null )
            {
                long secs = Long.parseLong((String)value);
                SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy hh:mm:ss" );
                val = sdf.format( new Date( secs * 1000L ) );
            }
        }

        Component c = DEFAULT_RENDERER.getTableCellRendererComponent( table, 
                                                                      val, 
                                                                      isSelected, 
                                                                      hasFocus, 
                                                                      row, 
                                                                      column); 
        
        if( !tableModel.rowAlive( row ) )
        {
            if( !isSelected )
                c.setBackground( Color.red );
            else
                c.setBackground( Color.LIGHT_GRAY );
        }
        
        return c; 
    } 
}