/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.heuboe.vinfo.viewer.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter_2
 */
public class CfgColumnGroup
{
    public static class CfgColumn
    {
        private String displayName;
        private String code;
        
        public CfgColumn( String displayName, String code )
        {
            this.displayName = displayName;
            this.code = code;
        }
        
        public CfgColumn( String code )
        {
            this.displayName = code;
            this.code = code;
        }

        public void setDisplayName(String displayName)
        {
            this.displayName = displayName;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getDisplayName()
        {
            return displayName;
        }

        public String getCode()
        {
            return code;
        }
    }
    
    private String name;
    private List<CfgColumn> cfgColumns;
    
    public CfgColumnGroup( String name, List<CfgColumn> cfgColumns ) 
    {
        this.name = name;
        this.cfgColumns = cfgColumns; 
    }
    
    private static List<CfgColumnGroup> cfgColumnSupply = 
                    new ArrayList<CfgColumnGroup>()
                    {{
                        add( new CfgColumnGroup( "Identity",
                                                 new ArrayList<CfgColumn>()   
                                                 {{
                                                     add( new CfgColumn( "ID" ) );
                                                     add( new CfgColumn( "Version" ) );
                                                 }} ));
                        add( new CfgColumnGroup( "Time",
                                                 new ArrayList<CfgColumn>()   
                                                 {{
                                                     add( new CfgColumn( "Time" ) );
                                                     add( new CfgColumn( "STA" ) );
                                                 }} ));
                    }};
            
    public static List<CfgColumnGroup> getCfgColumns()
    {
        return cfgColumnSupply;
    }
}
