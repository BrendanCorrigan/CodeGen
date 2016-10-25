//"use strict";
$(document).ready(function () {
    
    // Temporary
    var tableData = [];      
        
	// Create scope variables
//	var rootURL = "http:/localhost:8080/services";
	var rootURL = "services";

    var selectedId;
    
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
 	
	// Customer object
	function Customer(id, forename,surname,phone,addressLine1,addressLine2,city,postCode,country,salesRepEmployeeId,creditLimit) {
		this.id = Number(id);
		
		this.forename = forename;
		this.surname = surname;
		this.phone = phone;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
		this.salesRepEmployeeId = salesRepEmployeeId;
		this.creditLimit = creditLimit;
		
		this.toJsonString = function() { return JSON.stringify(this)};
	};
	
	// Helper function to retrieve a Contact object from the form
	function getCustomerFromForm() {
		return new Customer(
			Number($('#id').val())
			,$('#forename').val()
			,$('#surname').val()
			,$('#phone').val()
			,$('#addressLine1').val()
			,$('#addressLine2').val()
			,$('#city').val()
			,$('#postCode').val()
			,$('#country').val()
			,$('#salesRepEmployeeId').val()
			,$('#creditLimit').val()
		);
	};
	
	// Helper function to populate a form from a JSON object
	function populateForm( data ) {
		$('#id').val(Number(data.id));
		$('#forename').val(data.forename);
		$('#surname').val(data.surname);
		$('#phone').val(data.phone);
		$('#addressLine1').val(data.addressLine1);
		$('#addressLine2').val(data.addressLine2);
		$('#city').val(data.city);
		$('#postCode').val(data.postCode);
		$('#country').val(data.country);
		$('#salesRepEmployeeId').val(data.salesRepEmployeeId);
		$('#creditLimit').val(data.creditLimit);
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

                return "<button type='button' class='btn btn-small btn-default command-edit' data-row-id='" + row.id + "' data-toggle='modal' data-target='#genericModal'><span class='fa fa-pencil'></span></button> " + 
                "<button type='button' class='btn btn-danger btn-small btn-default command-delete' data-row-id='" + row.id + "' data-toggle='modal' data-target='#confirmDeletion'><span class='fa fa-trash-o'></span></button>";
            }
        }
    }).on("loaded.rs.jquery.bootgrid", function()
    {
        /* Executes after data is loaded and rendered */
        grid.find(".command-edit").on("click", function(e)
        {
            selectedId = $(this).data("row-id");
            var selectedItem = $.grep(tableData, function(e){ return e.id == selectedId; });
                        
            populateForm({});
            //populateForm(selectedItem[0]);
            getCustomer(selectedId);
            
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

            var selectedItem = $.grep(tableData, function(e){ return e.id == selectedId; });
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
        
		populateForm({});
        

    });
        
    $('#createConfirmButton').on('click', function () {

		var newEntry = getCustomerFromForm();
		
        console.log("CONFIRM UPDATE PRESSED FOR ID: " + JSON.stringify(newEntry));
		
		if(!newEntry.id) {

            // should validate all fields to make sure that they have valid values.
            // if all is well the record can be updated.
            
            createCustomer(newEntry);
            
            // close the modal window once data has been saved successfully.
            $('#genericModal').modal("hide");
            
        } else {
            console.log("ID IS NOT NULL: " + newEntry.id);
        }

    });    
    

    //--------------------------------------------------
    // Update existing Entry...
    //--------------------------------------------------
    
    $('#updateConfirmButton').on('click', function () {

        var updatedEntry = getCustomerFromForm();
        
        // sanity check the data...
        if (updatedEntry.id === selectedId) {
            // should validate all fields to make sure that they have valid values.

            // if all is well the record can be updated.
            updateCustomer(updatedEntry);            
            
            // close the modal window once data has been updated successfully.
            $('#genericModal').modal("hide");                                    
            
        } else {
            // there has been a problem - the selected entry and the updated entry
            // have different keys. This is a problem.

            console.log("Updated ID and Selected Item ID don't match: " + updatedEntry.id + " vs " + selectedId);

        }

    });


    //--------------------------------------------------
    // Delete function 
    //--------------------------------------------------
    
    $('#deleteConfirmButton').on('click', function() {

    	deleteCustomer(selectedId);

    });

    
    
    
    
    
    
	// ====================================================================
	// Ajax requests
	// ====================================================================

    // ----------------------------------------------------------------------------------------------------
	// GET
    // ----------------------------------------------------------------------------------------------------
	function getCustomer(id) {
		$.ajax({
			type : "GET",
			url : rootURL + "/customer/" + id,
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			
			success : function ( data, status, jqXHR ) {
				console.log("getCustomer status: " + status + " data: " + JSON.stringify(data));
				populateForm(data);
				
			},
			
			error : function (jqXHR, status) {
				console.log("getCustomer status: " + status);
												
			}		
			
		});
	}
	
    // ----------------------------------------------------------------------------------------------------
	// GET
    // ----------------------------------------------------------------------------------------------------
	function getCustomers() {
		$.ajax({
			type : "GET",
			url : rootURL + "/customer",
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			
			success : function ( data, status, jqXHR ) {
				console.log("getCustomers status: " + status + " Retrieved: " + data.length + " records");
				console.log("Example data[0]: " + JSON.stringify(data[0]));
				
				tableData = data;
                
                $("#genericTable").bootgrid('clear');
                $("#genericTable").bootgrid('append', tableData);
				
			},
			
			error : function (jqXHR, status) {
				console.log("getCustomers status: " + status);
												
			}		
			
		});
	}

    // ----------------------------------------------------------------------------------------------------
	// DELETE
    // ----------------------------------------------------------------------------------------------------
	function deleteCustomer(id) {
		$.ajax({
			type : "DELETE",
			url : rootURL + "/customer/" + id,
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			
			success : function ( data, status, jqXHR ) {
                
                var selectedEntry = $.grep(tableData, function(e){ return e.id == selectedId; });

                var index = tableData.indexOf(selectedEntry[0]);
                if (index > -1) {
                    tableData.splice(index, 1);                

                    // Remove the row from the table.
                    $("#genericTable").bootgrid('remove', [selectedId]);

                }
                            
				
			},
			
			error : function (jqXHR, status) {
				console.log("deleteCustomer status: " + status);
												
			}		
			
		});
	}
	
	
    // ----------------------------------------------------------------------------------------------------
	// POST
    // ----------------------------------------------------------------------------------------------------
	function createCustomer(customer) {
		$.ajax({
			type : "POST",
			url : rootURL + "/customer",
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			data : customer.toJsonString(),
			dataType : "json",
			
			success : function ( data, status, jqXHR ) {                
				console.log("createCustomer - status: " + status + " data: " + JSON.stringify(data));
                
                tableData.push(data);
                $("#genericTable").bootgrid('append', [data]);
				
			},
			
			error : function (jqXHR, status) {
				console.log("createCustomer - status: " + status);
												
			}
			
		});
	}

	
    // ----------------------------------------------------------------------------------------------------
	// PUT
    // ----------------------------------------------------------------------------------------------------
	function updateCustomer(customer) {
		$.ajax({
			type : "PUT",
			url : rootURL + "/customer",
			timeout : 30000,
			contentType : "application/json; charset=utf-8",
			data : customer.toJsonString(),
			dataType : "json",
			
			success : function ( data, status, jqXHR ) {
				console.log("updateCustomer - status: " + status + " data: " + JSON.stringify(data));
                
                var selectedEntry = $.grep(tableData, function(e){ return e.id == selectedId; });

                var index = tableData.indexOf(selectedEntry[0]);
                if (index > -1) {
                    tableData.splice(index, 1, data);                

                    // Remove the row from the table.
                    $("#genericTable").bootgrid('remove', [selectedId]);
                    $("#genericTable").bootgrid('append', [data]);

                }                				
			},
			
			error : function (jqXHR, status) {
				console.log("updateCustomer - status: " + status);
												
			}
						
		});
	}

	
	// ====================================================================
	// Validation
	// ====================================================================
	
	function validateCustomer(customer) {
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
    
    
    getCustomers();
	
})