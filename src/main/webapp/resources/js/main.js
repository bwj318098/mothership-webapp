/*
 * jQuery File Upload Plugin JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */


function disableUploadOnIncompleteDocuments(){
	var isValid = false;
	var isComplete = new RegExp('^(?=.*[0]{1,}.*).*$');
	var num_matches = 0;
	var concatenatedImageType = '';
	
	var images = 0;
	$('#imageType\\[\\]').each(function( index ) {
		concatenatedImageType += ($(this).val());
		images += 1;
	});
	//console.log(concatenatedImageType);
	if(concatenatedImageType.length > 0 ){
		num_matches = concatenatedImageType.match(/0/gi) != null ?
				concatenatedImageType.match(/0/gi).length : 0;
	}
	
	//required docs should be selected
	if(isComplete.test(concatenatedImageType) && 
			//only one copy of application form is allowed
			num_matches==1 && 
			//no error is found
				$('.error').text().length==0 &&
					//check if all images are tagged
						images == concatenatedImageType.length &&
							//check if the seqno is already valid as well
								$('#fileupload').valid()){
		$('#upload-start').prop('disabled', false);
		isValid=true;
	}else{
		$('#upload-start').prop('disabled', true);
	}
	
	return isValid;
}    

var osa = {};

/* global $, window */
$(function () {
    'use strict';
    osa.apptype = {'Normal':[0,1,2,3], 'Fastlane SSS':[0,7], 'Fastlane Reprocess':[0]};
    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: '/ajaxUpload',
        //filesContainer: $('.files'),
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator && navigator.userAgent),
        imageForceResize: true,
        imageMaxWidth: 1080,
        imageMaxHeight: 1768,
        imageQuality: 0.8,
        imageMinWidth: 800,
        imageMinHeight: 600,
        imageOrientation: true,
        previewOrientation:true,
        completed: function(e, data) {
        	  
        }
        
    }).on("fileuploadchange",function(){;
    	disableUploadOnIncompleteDocuments();
    	console.log(osa);
    	console.log($(this).find('select option'));
    	
    });
    
    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/home%s'
        )
    );
        
    // Load existing files:
    $('#fileupload').addClass('fileupload-processing');
    $.ajax({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: $('#fileupload').fileupload('option', 'url'),
        dataType: 'json',
        context: $('#fileupload')[0]
    }).always(function () {
        $(this).removeClass('fileupload-processing');
    }).done(function (result) {
        $(this).fileupload('option', 'done')
            .call(this, $.Event('done'), {result: result});    	
    }); 
    
});



$(document).ready(function(e){
	
	//init form
	$('#applicationNo').val('');
	$('#groupId').val('');
	$('#seqNo').val('');
	//generate application number once and for all.
	$('#generate').one('click',function(e){

		$.ajax({
	        // Uncomment the following to send cross-domain cookies:
	        //xhrFields: {withCredentials: true},
	        url: '/initialize',
	        method: "POST",
	        dataType: 'json',
	        data: $('#fileupload').serialize()
	    }).success(function (data) {
	    	//put in here.
	    	$('#applicationNo').val(data.appCd);
			$('#groupId').val(data.groupId);
	    }).error(function (errorThrown) {
	    	console.log(errorThrown);
	    	//something   	
	    });
		
	});
	//see core.js for the numeric js function declaration
	enableNumericOnlyForNumericClass();
	//disable the upload button on these scenarios. please see the main.js for the function implementation
	$( document ).on('change', '#seqNo', function(e) {disableUploadOnIncompleteDocuments();});
	$( document ).on('change', '#imageType\\[\\]', function(e) {disableUploadOnIncompleteDocuments();});
	$( document ).on('change', '#appProcess', function(e) {disableUploadOnIncompleteDocuments();});
	$( document ).on('click', '.start', function(e) {
		var isValid = disableUploadOnIncompleteDocuments();
		if(isValid){
	    	//disable all click-able stuff
	    	$('#imageType\\[\\]').attr('readonly',true);
	    	$('#upload-start').prop('disabled',true);
	    	$('.add-file').prop('disabled',true);
	    	$('.cancel').prop('disabled',true);
    	}
	});
	
	var validator = 
		$('#fileupload').validate({
			//validate non-present or visible fields.
			ignore: "",
			errorPlacement: function(label, element) {
				$(element)
					.closest("*[class^='col-']")
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
	        		.closest("*[class^='col-']")
	        			.removeClass('has-error')
	        			.addClass('has-success');
	        	$(label).hide();
	        }
	    });
		
		//individually validate this field.
		if($('#seqNo').val().length>0){
			validator.element( "#seqNo" );
		}
		
});
//listen for any dom inserts so we can check every time a new element is either inserted or moved.
$(document).on('DOMNodeInserted', function(e) {
	if ($(e.target).is('.template-upload')) {
		disableUploadOnIncompleteDocuments();
	  }
});

//custom event triggered when progress-all-bar becomes 100 % to trigger finish ALL.
$(document).on('upload-complete', function() {
	//create new application and seq no for this application
	$.ajax({
        // Uncomment the following to send cross-domain cookies:
        url: '/application-seqno',
        method: "POST",
        dataType: 'json',
        data: $('#fileupload').serialize()
    }).success(function () {
		//do a location update to application detail endpoint
		//var url = '/detail/'+$('#applicationNo').val();
		//setTimeout(function(){ document.location = url;}, 3000 ); 
    	var url = '/?appcd='+$('#applicationNo').val();
		$(location).attr('href',url);
		
    }).error(function (errorThrown) {
    	console.log(errorThrown);
    	//something wrong happened.   	
    });
});
