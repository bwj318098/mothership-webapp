/*! Customed function for string to cater format functions on scripts. */
String.prototype.format = String.prototype.f = function() {
    var s = this,
        i = arguments.length;

    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};

function addImageFunction(){
	/*! Customed function for dynamic addition of image with image type */
	$("#addLink").click(function () {
	    var id = ($('.additionalImage .row').length + 1).toString();
	    var index = ($('.additionalImage .row').length).toString();
	    
	    $('#additionalImage').append('<h4></h4><div class="row" id="row-image_'+id+'">'
	    		+'<div class="col-md-4 col-xs-6" th:classappend="${#fields.hasErrors(\'additionalImages['+index+'].imageType\')}? \'has-error\'"><div class="input-group svX3">'
	    		+'<label for="inputImageType_'+id+'" class="input-group-addon imagetype-width" id="imageType-addon_'+id+'">Image Type :&nbsp;</label>'
	    		+'<select class="form-control" th:field="*{additionalImages['+index+'].imageType}" aria-describedby="imageType-addon_'+id+'" id="inputImageType_'+id+'" name="imageType">'
	    		+ '<option value="0"></option><option value="1">Application Form</option><option value="2">ID Proof</option><option value="3">Proof of Billing</option><option value="4">Proof of Income</option>'
	    		+'</select>'
	    		+'</div>'
	    		+'<span class="help-block" th:if="${#fields.hasErrors(\'additionalImages['+index+'].imageType\')}" th:errors="*{additionalImages['+index+'].imageType}"></span>'
	    		+'</div>'
	    		+'<div class="col-md-8 col-xs-6" th:classappend="${#fields.hasErrors(\'additionalImages['+id+'].imageFile\')}? \'has-error\'"><div class="input-group">'
	    		+'<input type="file" class="form-control" id="image_'+id+'" th:field="*{additionalImages['+id+'].imageFile}" name="image" aria-describedby="image_'+id+'-addon" />'
	    		+'<span class="input-group-btn"><button type="button" class="btn btn-primary" onclick="resetForm(\'image_'+id+'\')">Clear</button></span></div>'
	    		+'<span class="help-block" th:if="${#fields.hasErrors(\'additionalImages['+id+'].imageFile\')}" th:errors="*{additionalImages['+id+'].imageFile}"></span> </div></div>');

	});

	$("#removeLink").click(function () {
		var id = ($('.additionalImage .row').length).toString();
	    $("#additionalImage #row-image_"+ id).remove();
	});
	/*! Customed function for dynamic addition of image with image type */
};

/* Prevents user to input character on fields marked with class numeric.*/
function enableNumericOnlyForNumericClass(){
	$(".numeric").numeric({ decimal: false, negative: false }, function() {this.value = ""; this.focus(); });
}

/* Prevents user to input character on fields marked with class numeric.*/
function enableDecimalOnlyForNumericClass(){
	//$(".numeric-decimal").numeric({ decimal: ".", negative: false ,scale: 3}, function() {this.value = ""; this.focus(); });
	$(".numeric-decimal").numeric({ decimal : ".",  negative : false, scale: 2 });
}

/* Show loading screen for every form submit only*/
function showLoadingGifOnFormSubmit(){
	$("form").on('submit',function(){$(".se-pre-con").show();});
}

/* Show tool-tip for user guide*/
function enableToolTipForHeader(){
	$('#merchantUpload').tooltip({title: "Upload new set of images for an application.", animation: true,placement: "auto"});
	$('[href="/search"]').tooltip({title: "Search existing or previously uploaded application from the system.", animation: true,placement: "auto"});
	$('[href="/followup"]').tooltip({title: "Search applications for document followup from Head office.", animation: true,placement: "auto"});
	$('[href="/signup"]').tooltip({title: "Register new merchant promoter or staff in Online Submission Application.", animation: true,placement: "auto"});
	$('[href="#logout"]').tooltip({title: "Logout to OSA-PH.", animation: true,placement: "auto"});
}

function enableToolTipForMerchantUpload(){
	$('[name="generate"]').tooltip({title: "Generate a new application number. Note that this will clear all currently uploaded image/s.", animation: true,placement: "auto"});
	$('#appNo-addon').tooltip({title: "Generated Application No.", animation: true,placement: "auto"});
	$('#seqNo-addon').tooltip({title: "Sequence No. stated in physical Application Form.", animation: true,placement: "auto"});
	$('#appForm-addon').tooltip({title: "Scanned Application Form", animation: true,placement: "auto"});
	$('#idProof-addon').tooltip({title: "Id Proof e.g. SSS, Govt ID, TIN, Office ID, Passport.", animation: true,placement: "auto"});
	$('#addressProof-addon').tooltip({title: "Proof of Billing e.g. Electric Bill, Water Bill, Internet Bill.", animation: true,placement: "auto"});
	$('#incomeProof-addon').tooltip({title: "Proof of Income e.g. Payslip, Bank Statement.", animation: true,placement: "auto"});
	
	$('[name="addImage"]').tooltip({title: "Add more images if the above default is not enough. Note that this will clear all currently uploaded image/s.", animation: true,placement: "auto"});
	$('#removeLink').tooltip({title: "Remove image/s that was previouly added.", animation: true,placement: "auto"});
	$('[name="clearUpload"]').tooltip({title: "Clears the uploaded file.", animation: true,placement: "auto"});
	$('[name="upload_images"]').tooltip({title: "Submit the application for Data Entry.", animation: true,placement: "auto"});
	$('[name="pending_images"]').tooltip({title: "Submit the application as Pending to be completed later. Only Application Form is required.", animation: true,placement: "auto"});
	$('[id^="imageType-addon_"]').tooltip({title: "Select the appropriate image type per image uploaded.", animation: true,placement: "auto"});
	$('[name="view_images"]').tooltip({title: "Show images pop-up.", animation: true,placement: "auto",html:true});	
	$('[name="completeSubmission"]').tooltip({title: "Submit the application as complete.", animation: true,placement: "auto"});
	$('[name="attach_images"]').tooltip({title: "Attach more image/s to this application", animation: true,placement: "auto"});

}

function enableTooltipForSignup(){
	$('[for="storeCd"]').tooltip({title: "Please enter a valid and existing store code otherwise this will lead to validation error.", animation: true,placement: "auto"});
	$('[for="username"]').tooltip({title: "Any existing username will result into validation error.", animation: true,placement: "auto"});
	$('[for="email"]').tooltip({title: "Token for reset password will be sent to this email. Please put a valid email.", animation: true,placement: "auto"});
	$('[type="submit"]').tooltip({title: "Register a new user with ROLE_USER privileges under the store code entered.", animation: true,placement: "auto"});
}

function convertTextInputToUpperCase(){
	$('input[type=text]').val (function () {
	    return this.value.toUpperCase();
	});
	
}
