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
    }
	
});

//defined a customized function to avoid duplication.
function validateForm(arg){
	_form = arg;
	$(_form).validate({
		highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.addClass('valid').closest('.form-group').removeClass('has-error').addClass('has-success');
        }
        
    });
}