//replace the obnoxious error message for required.
jQuery.extend(jQuery.validator.messages, {
    required: " ",
    remote: " ",
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
    regex: " "
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
   
   /**
    * <validation name>_<size>_<req or not req>
    */
   name_30_req: {
       maxlength: 30,
       regex: "^([ \u00c0-\u01ffa-zA-Z'\-])+$",
       required: true
   },
   
   name_30: {
       maxlength: 30,
       regex: "^([ \u00c0-\u01ffa-zA-Z'\-])+$"
   },
   
   any_2_req: {
       maxlength: 2,
       required: true
       //regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+"
   },
   
   any_3_req: {
       maxlength: 3,
       required: true
       //regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+"
   },
   
   any_4_req: {
       maxlength: 4,
       required: true
       //regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+"
   },
   any_5_req: {
       maxlength: 5,
       required: true,
       regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+"
   },
   any_6_req: {
       maxlength: 6,
       required: true
       //,regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+"
   },
   
   any_10_req: {
       maxlength: 10,
       //regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+",
       required: true
   },
   
   currency_10_req: {
       maxlength: 10,
       regex: "([0-9]{1,10})(\.[0-9]{1,2})?",
       required: true
   },
   
   any_20_req: {
       maxlength: 10,
       //regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+",
       required: true
   },
   
   any_50_req: {
       maxlength: 50,
       regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+",
       required: true
   },
   
   any_50: {
       maxlength: 50,
       regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+"
   },
   
   any_70_req: {
       maxlength: 70,
       regex: "[\p{Alpha}\p{Digit}\p{Space}\Ñ]+",
       required: true
   },
   
   selectField: {
	   required: true
   }
   
});

//defined a customized function to avoid duplication.
function validateForm(arg){
	_form = arg;
	$(_form).validate({
		highlight: function (element) {
            $(element).closest("*[class^='col-']").removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.addClass('valid').closest("*[class^='col-']").removeClass('has-error').addClass('has-success');
        }
    });
}