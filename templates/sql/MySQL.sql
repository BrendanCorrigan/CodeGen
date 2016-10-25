CREATE TABLE ${bean.table} IF NOT EXISTS (
	${bean.primaryKey.columnName} ${bean.primaryKey.columnType} #if(${bean.primaryKey.autoIncrement}==true)AUTO_INCREMENT#end NOT NULL,    
#foreach($attribute in $bean.attributes)
    ${attribute.columnName} ${attribute.columnType}#if($attribute.columnSize > 0)($attribute.columnSize)#end#if(${attribute.mandatory}) NOT NULL#end,        
#end            
PRIMARY KEY(${bean.primaryKey.columnName}),
INDEX ${bean.table}_${bean.primaryKey.columnName}_INDEX (${bean.primaryKey.columnName}));