package alize.commun.modele;

import java.util.List;

import org.jooq.tools.StringUtils;
import org.jooq.util.ColumnDefinition;
import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;
import org.jooq.util.ForeignKeyDefinition;
import org.jooq.util.TableDefinition;

/**
 * It is recommended that you extend the DefaultGeneratorStrategy. Most of the
 * GeneratorStrategy API is already declared final. You only need to override any
 * of the following methods, for whatever generation behaviour you'd like to achieve
 *
 * Beware that most methods also receive a "Mode" object, to tell you whether a
 * TableDefinition is being rendered as a Table, Record, POJO, etc. Depending on
 * that information, you can add a suffix only for TableRecords, not for Tables
 */
public class AsInDatabaseStrategy extends DefaultGeneratorStrategy {

    /**
     * Override this method to define the base class for those artefacts that
     * allow for custom base classes
     */
    @Override
    public String getJavaClassExtends(Definition definition, Mode mode) {
        String extendName = null;
        
    	if(mode == Mode.POJO) {
    		TableDefinition table = (TableDefinition) definition;
            List<ForeignKeyDefinition> foreignKeys = table.getForeignKeys();
            
            int k = 0;
            boolean trouve = false;            
            while(!trouve && k < foreignKeys.size()) {
            	List<ColumnDefinition> columnsForeignKey = foreignKeys.get(k).getKeyColumns();
            	if(columnsForeignKey.size() == 1) {
            		ColumnDefinition column = columnsForeignKey.get(0);
            		trouve = column.getName().compareTo("id") == 0;
            	}
            	k++;
            }
            
            if(trouve) {
            	TableDefinition foreignTable = foreignKeys.get(k-1).getReferencedTable();
        		extendName = StringUtils.toCamelCase(foreignTable.getOutputName());
            }
            
    	}
    	
		return extendName;
    }

}