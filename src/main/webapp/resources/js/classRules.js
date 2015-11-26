//replace the obnoxious error message for required.
jQuery.extend(jQuery.validator.messages, {
    required: "Required field",
    remote: "Invalid input",
    email: "Please enter a valid email address.",
    url: "Please enter a valid URL.",
    date: "Please enter a valid date.",
    dateISO: "Please enter a valid date (ISO).",
    number: "Please enter a valid number.",
    digits: "Please enter only digits.",
    creditcard: "Please enter a valid credit card number.",
    equalTo: "Please enter the same value again.",
    accept: "Please enter a value with a valid extension.",
    maxlength: jQuery.validator.format("Please enter no more than {0} characters."),
    minlength: jQuery.validator.format("Please enter at least {0} characters."),
    rangelength: jQuery.validator.format("Please enter a value between {0} and {1} characters long."),
    range: jQuery.validator.format("Please enter a value between {0} and {1}."),
    max: jQuery.validator.format("Please enter a value less than or equal to {0}."),
    min: jQuery.validator.format("Please enter a value greater than or equal to {0}."),
    regex: "Kindly recheck character input",
    skip_or_fill_minimum: function(_prop, _elem){
    	var _message = "";
    	$(_prop[1]).each(function(){
    		var _propName = $(this).prop("placeholder") 
    			|| $(this).prop("id")
	    	    	.replace(/([a-z])([A-Z])/g, '$1 $2')
	    	    	.replace(/^./, function(str){ return str.toUpperCase(); });
    		_message += _message ? (", " + _propName) : _propName;	
    	});
    	
    	return "All of these fields must not be empty: " + _message;
    }
});

$.validator.addMethod("regex", function(value, element, param) {
	  if(value==""){
		 return  true;      
	  }else if($.trim(value)==""){
			 return false;
	  }else{
		  return value.match(new RegExp(param));
	  }
});

$.validator.addMethod("checkDoB",function(value,element,param){
	var day = value.substring(8,10);
	var month = value.substring(5,7);
	var year = parseInt(value.substring(0,4));
	var age = parseInt(18);
	
	var cutOffDate = new Date(year + age, month, day);
	if (cutOffDate > Date.now()) {
	    return false;
	} else {
	    return true;
	}
	//message in here
}, "Must be 18 years old to apply");

$.validator.addMethod("checkHowMuch", function(value,element,param){
	var _elem = $(element);
	var _relElem = $("#" + _elem.data("check-how-much"));
	var _isValid;
	if(_elem.prop("nodeName").toUpperCase() === "SELECT"){
		return _relElem.valid();
	}
	var _elemVal = _relElem.val().toUpperCase();
	if(_elemVal === "RENTED" || _elemVal === "MORTGAGED"){
		var _howVal = _elem.val();
		var _parsedVal = null;
		return _howVal && (_parsedVal = parseFloat(_howVal)) && _parsedVal > 0.0;
	} else {
		return true;
	}
}, function(_prop, _elem){
	var _elemObj = $(_elem);
	var _resVal = (_elemObj.prop("nodeName").toUpperCase() === "SELECT") ?
			_elemObj.val().toUpperCase()
			: $("#" + _elemObj.data("check-how-much")).val().toUpperCase();
	return "Required for " + _resVal + " Residence Type.";
});

$.validator.addMethod("greaterThan", function (value, element, param) {
    var $element = $(element)
        , $min;

    if (typeof(param) === "string") {
        $min = $(param);
    } else {
        $min = $("#" + $element.data("min"));
    }

    if (this.settings.onfocusout) {
        $min.off(".validate-greaterThan").on("blur.validate-greaterThan", function () {
            $element.valid();
        });
    }
    return value && $min.val() && parseInt(value) > parseInt($min.val());
}, function(_prop, _elem){
	var _elemObj = $(_elem);
	var _toName = _elemObj.prop("id")
	    	.replace(/([a-z])([A-Z])/g, '$1 $2')
	    	.replace(/^./, function(str){ return str.toUpperCase(); });
	var _fromName = _elemObj.data("min")
		.replace(/([a-z])([A-Z])/g, '$1 $2')
		.replace(/^./, function(str){ return str.toUpperCase(); });
	
	return jQuery.validator.format("{0} must be greater than {1}")(_toName, _fromName);
});

//alias the remote validator method and add the custom message.
$.validator.addMethod("checkUserName", $.validator.methods.remote,
"Username already exists!");

$.validator.addMethod("checkStoreCd", $.validator.methods.remote,
"Store Code does not exists!");

$.validator.addClassRules({
    
	uploadField: {
		accept: "image/jpeg, image/pjpeg"
	},
	//customer method which Checks remotely if the username already exists.
    usernameCheckRemotely: {
	
	 required: true,
	 maxlength: 20,
	 //Do a remote checking if username is already taken.
	 checkUserName:{
		 url: "../users", //make sure to return true or false with a 200 status code
		 type: "GET"
	 }
    },
    
    storeCdCheckRemotely:{
    	required: true,
    	checkStoreCd:{
    		url: "../stores",
    		type: "GET"
    	}
    },
    passwordField: {
        required: true,
        minlength: 6,
        maxlength: 20
    },
    
    email_nonMandatory:{
    	email: true,
    	maxlength: 50
    },
    
   email_req: {
   	 	email: true,
        maxlength: 50,
        required: true
   },
   //id card type and card no. group
   idGroup:{
	   required: true,
	   skip_or_fill_minimum: [2,".idGroup"]
   },
   otherGroup:{
	   skip_or_fill_minimum: [2,".otherGroup"]
   },
   
   //only product 1 is required
   productGroup_1: {
	   required: true,
	   skip_or_fill_minimum: [5,".productGroup_1"]
   },
   
   productGroup_2: {       
	   skip_or_fill_minimum: [5,".productGroup_2"]
   },
   
   productGroup_3: {       
	   skip_or_fill_minimum: [5,".productGroup_3"]
   },
   
   //name group
   spouseName:{
	   skip_or_fill_minimum: [2,".spouseName"]
   },
   
   motherMaidenName:{
	   skip_or_fill_minimum: [2,".motherMaidenName"]
   },
   
   birthDayChecker:{
	   required: true,
	   maxlength: 30,
	   regex: "^[0-9 -]*$",
	   checkDoB :true
   },
   
   
   dateDashedNumber_req:{
	   required: true,
	   maxlength: 30,
	   regex: "^[0-9 -]*$"
   },
   
   contactTimeGroup:{
	   range: [0,24],
	   skip_or_fill_minimum: [2,".contactTimeGroup"]
   },
   
   greaterThan:{
	   greaterThan: true  
   },
   
   /**
    * <validation name>_<size>_<req or not req>
    */
   name_30_req: {
       maxlength: 30,
       regex: "^([ \u00c0-\u01ffa-zA-Z'\-])+$",
       required: true
   },
   
   alphaNum_30_req:{
	   required: true,
	   maxlength: 30,
	   regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   alphaNum_50_req:{
	   required: true,
	   maxlength: 30,
	   regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   alphaNum_70_req:{
	   required: true,
	   maxlength: 70,
	   regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   alphaNum_30:{
	   maxlength: 30,
	   regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   alphaNum_50:{
	   maxlength: 30,
	   regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   alphaNum_70:{
	   maxlength: 70,
	   regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   
   name_30: {
       maxlength: 30,
       regex: "^([ \u00c0-\u01ffa-zA-Z'\-])+$"
   },
   
   any_2_req: {
       maxlength: 2,
       required: true,
       regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   any_3_req: {
       maxlength: 3,
       required: true,
       regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   any_4_req: {
       maxlength: 4,
       required: true,
       regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   phone_reg_4: {
       maxlength: 4,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?"
   },
   
   phoneno_11: {
       maxlength: 11,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?"
   },
   
   phone_reg_4_req: {
       maxlength: 4,
       required: true,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?"
   },
   
   phoneno_11_req: {
       maxlength: 11,
       required: true,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?"
   },
   //made a separate grouping for other phone elements
   homePhone :{
	   skip_or_fill_minimum: [2,".homePhone"]
   },

   homeMobile :{
	   skip_or_fill_minimum: [2,".homeMobile"]
   },
   otherPhone :{
	   skip_or_fill_minimum: [2,".otherPhone"]
   },
   corpPhone :{
	   skip_or_fill_minimum: [2,".corpPhone"]
   },
   
   //reference
   refHomePhone :{
	   skip_or_fill_minimum: [2,".refHomePhone"]
   },
   refHomeMobile :{
	   skip_or_fill_minimum: [2,".refHomeMobile"]
   },
   refCorpPhone :{
	   skip_or_fill_minimum: [2,".refCorpPhone"]
   },
   
   
   any_5_req: {
       maxlength: 5,
       required: true
   },
   any_6_req: {
       maxlength: 6,
       required: true,
       regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   any_10_req: {
       maxlength: 10,
       regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$",
       required: true
   },
   
   any_30: {
       maxlength: 30
   },
   
   any_30_req: {
       maxlength: 30,
       required: true
   },
   
   currency_10: {
       maxlength: 10,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?"
   },
   
   currency_10_req: {
       maxlength: 10,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?",
       required: true
   },
   
   //product price: ranged, numeric, size 10, required
   firstPriceRules: {
	   range: [1000,999999],
       maxlength: 10,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?",
       required: true
   },
   
   otherPriceRules: {
	   range: [1000,999999],
       maxlength: 10,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?"
   },
   
   
   any_20: {
       maxlength: 20,
       regex: "^[A-Za-z0-9 - &]*[A-Za-z0-9][A-Za-z0-9 - &]*$"
   },
   
   any_20_req: {
       maxlength: 20,
       required: true
   },
   
   any_50_req: {
       maxlength: 50,
       required: true
   },
   
   any_50: {
       maxlength: 50
   },
   
   any_70: {
       maxlength: 70
   },
   
   any_70_req: {
       maxlength: 70,
       required: true
   },
   
   selectField: {
	   required: true
   },
   
   checkHowMuch: {
	   checkHowMuch: true
   }
});

(function($) {
	/**
	 * 
	 * usage example
	 * $("#form_id").validateForm(
	  	{
	  		submitButton : "#btn_submit_data_entry", 	// jQuery selector for the button that will trigger submit
	 		parentSelector : "*[class^='col-']", 		// jQuery selector for looking up the parent of a form field 
	  											 		// will be used for attaching error/success css class
	  		tabSelector : ".tab-pane", 					// jQuery selector for tabs containing form field. refer to dataentry.html for expected layout
	  		modal : {
	  				selector:{
							id: "#modal_generic",					// jQuery selector for the modal fragment
							title: ".modal-title", 					// jQuery selector for the element that will contain the title of the modal fragment
							message: "#generic-message", 			// jQuery selector for the element that will contain the body of the modal fragment
							errortable: "#generic-table-errors"},	// jQuery selector for the element that will contain the error table of the modal fragment
					message:{
							title: "Input Errors",											// Title message for the server side validation error result
							body: "Kindly review highlighted fields for errors.",			// Body message for the server side validation error result. Set to null if N/A
							generic_title: "Input Errors",									// Title message for the client side validation error result
							generic_body: "Kindly review highlighted fields for errors."}	// Body message for the client side validation error result
					},
			beforeSubmit : function(){},				// callback function that will be called after the client validation succeeded 
														// and before the form will be submitted to the server
			submitSuccess : function(data){}			// callback function that will be called when server side processing is successful.
		});
	 */
	$.fn.validateForm = function(config){
		
		config = config || {};
		
		var _thisForm = this;
		
		_thisForm.data("osa.validateForm.submitButton", config.submitButton)
			.data("osa.validateForm.parentSelector", config.parentSelector)
			.data("osa.validateForm.tabSelector", config.tabSelector)
			.data("osa.validateForm.modal", config.modal 
					|| {selector:{
							id: "#modal_generic", 
							title: ".modal-title", 
							message: "#generic-message", 
							errortable: "#generic-table-errors"},
						message:{
							title: "Input Errors",
							body: "Kindly review highlighted fields for errors.",
							generic_title: "Input Errors",
							generic_body: "Kindly review highlighted fields for errors."
						}})
			.data("osa.validateForm.beforeSubmit", config.beforeSubmit || function(){})
			.data("osa.validateForm.submitSuccess", config.submitSuccess || function(){});
		
		_thisForm.updateTabsErrorCount = function(){
			$(_thisForm.data("osa.validateForm.tabSelector"))
			 	.each(function(){
					var _this = $(this);
					var _errCnt = _this.find(".has-error").length;
			        $("a[href=#" + _this.attr("id") + "]").find(".badge")
			        	.tooltip({title: _errCnt + ' error' + (_errCnt > 1 ? 's' : '') +  ' found'})
			        	.text(_this.find(".has-error").length ? _this.find(".has-error").length : "");
			    });
		}   

		_thisForm.showGenericModal = function(_title, _message, _errorArr){
			var _modalSelectors = _thisForm.data("osa.validateForm.modal").selector;
			var _modal = $(_modalSelectors.id);
			_modal.find(_modalSelectors.title).text(_title);
			if(_message){
				_modal.find(_modalSelectors.message)
					.show()
					.text(_message);	
			} else {
				_modal.find(_modalSelectors.message)
					.hide();
			}
			if(_errorArr){
				_modal.find(".bootstrap-table, " + _modalSelectors.errortable).show();
				_modal.find(_modalSelectors.errortable).bootstrapTable('load', _errorArr);	
			} else {
				_modal.find(".bootstrap-table, " + _modalSelectors.errortable).hide();
			}
			_modal.modal({ backdrop: 'static', keyboard: false });
		}
		
		_thisForm.validate({
			//validate non-present or visible fields.
			ignore: "",
			errorPlacement: function(label, element) {
				$(element)
					.closest(_thisForm.data("osa.validateForm.parentSelector"))
			    		.removeClass('has-success')
			    		.addClass('has-error')
			    	.tooltip({ 
			    		animation : true, 
			    		placement : "auto"})
			    	.attr("data-original-title", $(label).text());
				$(label).hide();
			},
	        success: function (label, element) {
	        	$(element)
	        		.tooltip('destroy')
	        		.closest(_thisForm.data("osa.validateForm.parentSelector"))
	        			.removeClass('has-error')
	        			.addClass('has-success');
	        	$(label).hide();
	        }
	    });
		
		$(_thisForm.data("osa.validateForm.submitButton")).click(function(e){
			$(".se-pre-con").show();
			if(_thisForm.valid()){
				 //convert every input type text into uppercase
				 _thisForm.data("osa.validateForm.beforeSubmit").apply(_thisForm);
				 
				 $.ajax({
					 url : _thisForm.attr("action"),
					 method: "POST",
					 data : _thisForm.serialize(),
					 success : function(data){
						 		var _modalMessage = _thisForm.data("osa.validateForm.modal").message;
						 		$('.has-error')
					 				.children()
				        			.tooltip('destroy')
				        			.removeClass('has-error')
				        			.addClass('has-success');
						 		
						 		if(data && !data.success){
						 			if(data.showInModal){
						 				_thisForm.showGenericModal(_modalMessage.title, _modalMessage.body, data.dataEntryError);
						 			} else {
						 				_thisForm.showGenericModal(_modalMessage.generic_title, _modalMessage.generic_body);
						 			}
						 			
						 			$.each(data.dataEntryError, function(i, _val){
						 				$("*[name='" + _val.property + "']")
						 					.tooltip({title: _val.error || "Invalid input", animation: true, placement: "auto"})
					 						.closest(_thisForm.data("osa.validateForm.parentSelector"))
						 					.removeClass('has-success')
						 					.addClass('has-error');
						 			});
						 			_thisForm.updateTabsErrorCount();
						 		} else {
						 			_thisForm.data("osa.validateForm.submitSuccess").apply(_thisForm, data);
						 		}
						 		$(".se-pre-con").hide();
							},
					 dataType : "json" 
				 });	 
			 } else {
				 $(".se-pre-con").hide();
				 _thisForm.updateTabsErrorCount();
				 _thisForm.showGenericModal("Input Errors", "Kindly review highlighted fields for errors");
			 }
		});
		
		$(_thisForm.data("osa.validateForm.modal").selector.id).find(_thisForm.data("osa.validateForm.modal").selector.errortable).bootstrapTable();
	} 
})(jQuery);