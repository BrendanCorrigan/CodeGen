#set ($instanceName = $stringUtils.uncapitalize(${bean.name}))
//"use strict";
$(document).ready(function () {
    
    // Temporary
    var tableData = [];      
        
	// Create scope variables
//	var rootURL = "http:/localhost:8080/services";
	var rootURL = "services";

    var selectedId;

    // Set the validator for the form
    $('#genericForm').validator();
    
	// Set update entry fields to use datepicker
	$('#genericForm .date').datepicker ({
		format : "dd/mm/yyyy",
        todayHighlight: true,
		todayBtn : "linked",
		language : "en-GB"
	});

	// ====================================================================
	// Object functions and helpers
	// ====================================================================
 	
	// ${bean.name} object
	function ${bean.name}(${bean.primaryKey.name}, ${attributeNames}) {
		this.${bean.primaryKey.name} = Number(${bean.primaryKey.name});
		
#foreach($attribute in $bean.attributes)
		this.${attribute.name} = ${attribute.name};
#end
		
		this.toJsonString = function() { return JSON.stringify(this)};
	};
	
	// Helper function to retrieve a Contact object from the form
	function get${bean.name}FromForm() {
		return new ${bean.name}(
			Number($('#${bean.primaryKey.name}').val())
#foreach($attribute in $bean.attributes)
			,$('#${attribute.name}').val()
#end			
		);
	};
	
	// Helper function to populate a form from a JSON object
	function populateForm( data ) {
		$('#${bean.primaryKey.name}').val(Number(data.${bean.primaryKey.name}));
#foreach($attribute in $bean.attributes)
		$('#${attribute.name}').val(data.${attribute.name});
#end			
	};
    
    
    
    
	// ====================================================================	
	// Create the table configuration
	// ====================================================================    
    

    var grid = $('#genericTable').bootgrid({
       formatters: {

           "searchableCheck": function(column, row){ if (row.searchable) return "<span class='fa fa-check'></span>"; },
           "mandatoryCheck": function(column, row){ if (row.mandatory) return "<span class='fa fa-check'></span>"; },

            "commands": function(column, row)
            {

                return "<button type='button' class='btn btn-small btn-default command-edit' data-row-id='" + row.${bean.primaryKey.name} + "' data-toggle='modal' data-target='#genericModal'><span class='fa fa-pencil'></span></button> " + 
                "<button type='button' class='btn btn-danger btn-small btn-default command-delete' data-row-id='" + row.${bean.primaryKey.name} + "' data-toggle='modal' data-target='#confirmDeletion'><span class='fa fa-trash-o'></span></button>";
            }
        }
    }).on("loaded.rs.jquery.bootgrid", function()
    {
        /* Executes after data is loaded and rendered */
        grid.find(".command-edit").on("click", function(e)
        {
            selectedId = $(this).data("row-id");
            var selectedItem = $.grep(tableData, function(e){ return e.${bean.primaryKey.name} == selectedId; });
                        
            $('#genericForm')[0].reset()
            $('#genericForm').validator('destroy').validator()            
            populateForm({});
            //populateForm(selectedItem[0]);
            get${bean.name}(selectedId);
            
            $('#createConfirmButton').hide();
            $('#updateConfirmButton').show();
            $('#modeText').text("Make changes to record: " + selectedId);

            // ----------------------------------------------------------------
            // Show the modal window
            // ----------------------------------------------------------------      
            $($(this).attr("data-target")).modal("show");

        }).end().find(".command-delete").on("click", function(e)
        {
            
            selectedId = Number($(this).data("row-id")); 

            var selectedItem = $.grep(tableData, function(e){ return e.${bean.primaryKey.name} == selectedId; });
            console.log("selectedItem: " + selectedItem);
            console.log("You pressed delete on row: " + selectedId + " --> " + JSON.stringify(selectedItem[0]));        

            // ----------------------------------------------------------------
            // create a user friendly message here to confirm that this is the 
            // record you want to delete.
            // ----------------------------------------------------------------
            
            var deleteConfirmMessage = selectedItem[0].firstName + " " + selectedItem[0].lastName;
            
            $(".modal-body #entryToDelete").text(deleteConfirmMessage);
            
            // ----------------------------------------------------------------
            // Show the modal window
            // ----------------------------------------------------------------  
            $($(this).attr("data-target")).modal("show");
            
        });
        
    });
    
    
	
	// ====================================================================	
	// Deal with button actions
	// ====================================================================

    //--------------------------------------------------
    // Create a new Entry
    //--------------------------------------------------

    $('#createItemButton').on('click', function () {

        $('#createConfirmButton').show();
        $('#updateConfirmButton').hide();
        $('#modeText').text("Create a new record...");
        
        $('#genericForm')[0].reset()
        $('#genericForm').validator('destroy').validator()
	populateForm({});
        

    });
        
    $('#createConfirmButton').on('click', function () {

    	if (!$('#createConfirmButton').hasClass('disabled')) {
    		console.log("The Confirm Create button has been disabled.")
		var newEntry = get${bean.name}FromForm();
		
        console.log("CONFIRM UPDATE PRESSED FOR ID: " + JSON.stringify(newEntry));
		
		if(!newEntry.${bean.primaryKey.name}) {

            // should validate all fields to make sure that they have valid values.
            // if all is well the record can be updated.
            
            create${bean.name}(newEntry);
            
            // close the modal window once data has been saved successfully.
            $('#genericModal').modal("hide");
            
        } else {
            console.log("ID IS NOT NULL: " + newEntry.${bean.primaryKey.name});
        }
    	}

    });    
    

    //--------------------------------------------------
    // Update existing Entry...
    //--------------------------------------------------
    
    $('#updateConfirmButton').on('click', function () {

    	if (!$('#updateConfirmButton').hasClass('disabled')) {
    		console.log("The Confirm Update button has been disabled.")
    	
        var updatedEntry = get${bean.name}FromForm();
        
        // sanity check the data...
        if (updatedEntry.${bean.primaryKey.name} === selectedId) {
            // should validate all fields to make sure that they have valid values.

            // if all is well the record can be updated.
            update${bean.name}(updatedEntry);            
            
            // close the modal window once data has been updated successfully.
            $('#genericModal').modal("hide");                                    
            
        } else {
            // there has been a problem - the selected entry and the updated entry
            // have different keys. This is a problem.

            console.log("Updated ID and Selected Item ID don't match: " + updatedEntry.${bean.primaryKey.name} + " vs " + selectedId);
	        }

        }

    });


    //--------------------------------------------------
    // Delete function 
    //--------------------------------------------------
    
    $('#deleteConfirmButton').on('click', function() {

    	delete${bean.name}(selectedId);

    });

    
    
    
    
    
    
	// ====================================================================
	// Ajax requests
	// ====================================================================

    // ----------------------------------------------------------------------------------------------------
	// GET
    // ----------------------------------------------------------------------------------------------------
	function get${bean.name}(id) {
		$.ajax({
			type : "GET",
			url : rootURL + "/$instanceName/" + id,
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			
			success : function ( data, status, jqXHR ) {
				console.log("get${bean.name} status: " + status + " data: " + JSON.stringify(data));
				populateForm(data);
				
			},
			
			error : function (jqXHR, status) {
				console.log("get${bean.name} status: " + status);
												
			}		
			
		});
	}
	
    // ----------------------------------------------------------------------------------------------------
	// GET
    // ----------------------------------------------------------------------------------------------------
	function get${bean.name}s() {
		$.ajax({
			type : "GET",
			url : rootURL + "/$instanceName",
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			
			success : function ( data, status, jqXHR ) {
				console.log("get${bean.name}s status: " + status + " Retrieved: " + data.length + " records");
				console.log("Example data[0]: " + JSON.stringify(data[0]));
				
				tableData = data;
                
                $("#genericTable").bootgrid('clear');
                $("#genericTable").bootgrid('append', tableData);
				
			},
			
			error : function (jqXHR, status) {
				console.log("get${bean.name}s status: " + status);
												
			}		
			
		});
	}

    // ----------------------------------------------------------------------------------------------------
	// DELETE
    // ----------------------------------------------------------------------------------------------------
	function delete${bean.name}(id) {
		$.ajax({
			type : "DELETE",
			url : rootURL + "/$instanceName/" + id,
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			
			success : function ( data, status, jqXHR ) {
                
                var selectedEntry = $.grep(tableData, function(e){ return e.${bean.primaryKey.name} == selectedId; });

                var index = tableData.indexOf(selectedEntry[0]);
                if (index > -1) {
                    tableData.splice(index, 1);                

                    // Remove the row from the table.
                    $("#genericTable").bootgrid('remove', [selectedId]);

                }				
			},
			
			error : function (jqXHR, status) {
				console.log("delete${bean.name} status: " + status);												
			}		
			
		});
	}
	
	
    // ----------------------------------------------------------------------------------------------------
	// POST
    // ----------------------------------------------------------------------------------------------------
	function create${bean.name}($instanceName) {
		$.ajax({
			type : "POST",
			url : rootURL + "/$instanceName",
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			data : ${instanceName}.toJsonString(),
			dataType : "json",
			
			success : function ( data, status, jqXHR ) {                
				console.log("create${bean.name} - status: " + status + " data: " + JSON.stringify(data));
                
                tableData.push(data);
                $("#genericTable").bootgrid('append', [data]);
				
			},
			
			error : function (jqXHR, status) {
				console.log("create${bean.name} - status: " + status);
												
			}
			
		});
	}

	
    // ----------------------------------------------------------------------------------------------------
	// PUT
    // ----------------------------------------------------------------------------------------------------
	function update${bean.name}($instanceName) {
		$.ajax({
			type : "PUT",
			url : rootURL + "/$instanceName",
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			data : ${instanceName}.toJsonString(),
			dataType : "json",
			
			success : function ( data, status, jqXHR ) {
				console.log("update${bean.name} - status: " + status + " data: " + JSON.stringify(data));
                
                var selectedEntry = $.grep(tableData, function(e){ return e.${bean.primaryKey.name} == selectedId; });

                var index = tableData.indexOf(selectedEntry[0]);
                if (index > -1) {
                    tableData.splice(index, 1, data);                

                    // Remove the row from the table.
                    $("#genericTable").bootgrid('remove', [selectedId]);
                    $("#genericTable").bootgrid('append', [data]);

                }                				
			},
			
			error : function (jqXHR, status, text) {
				console.log("update${bean.name} - status: " + status + " text: " + text);
												
			}
						
		});
	}

	
	// ====================================================================
	// Validation
	// ====================================================================
	
	function validate${bean.name}($instanceName) {
		var valid = true;
		
		return valid;
	};

    
    
	// ====================================================================
    // Helper function to retrieve content of a form...
	// ====================================================================
    
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };    
    
    
    get${bean.name}s();
	
})