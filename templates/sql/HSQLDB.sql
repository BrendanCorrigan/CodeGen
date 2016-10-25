CREATE TABLE IF NOT EXISTS ${bean.table} (
	${bean.primaryKey.columnName} ${bean.primaryKey.columnType} #if(${bean.primaryKey.autoIncrement}==true)GENERATED ALWAYS AS IDENTITY (START WITH 1)#end NOT NULL,
#foreach($attribute in $bean.attributes)
    ${attribute.columnName} ${attribute.columnType}#if($attribute.columnType.equalsIgnoreCase("varchar"))($attribute.columnSize)#end#if(${attribute.mandatory}) NOT NULL#end,        
#end            
PRIMARY KEY(${bean.primaryKey.columnName}));
CREATE INDEX IF NOT EXISTS ${bean.table}_${bean.primaryKey.columnName}_INDEX ON ${bean.table}(${bean.primaryKey.columnName});