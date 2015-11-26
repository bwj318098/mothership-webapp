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
    regex: " ",
    skip_or_fill_minimum: " "
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
	console.log(cutOffDate);
	if (cutOffDate > Date.now()) {
	    return false;
	} else {
	    return true;
	}
	//message in here
},"must be 18 years old to apply");


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
    return parseInt(value) > parseInt($min.val());
}, "Max must be greater than min");

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
   }
   
});

(function($) {
	$.fn.validateForm = function(){
		this.validate({
			//validate non-present or visible fields.
			ignore: "",
//			showErrors : function(_map, _list){
//				$('.has-error').removeClass('has-error').children().tooltip('destroy');
//				$.each(_list, function(_i, _val){
//					$(_val.element)
//						.tooltip({title: _val.message || "Invalid input", animation: true, placement: "auto"})
//						.closest("*[class^='col-']").addClass('has-error');
//				});
//			},
			highlight: function (element) {
	            $(element).closest("*[class^='col-']").removeClass('has-success').addClass('has-error');
	        },
	        success: function (element) {
	            element.addClass('valid').closest("*[class^='col-']").removeClass('has-error').addClass('has-success');
	        }
	    });
	} 
})(jQuery);