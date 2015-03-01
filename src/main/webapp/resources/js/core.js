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
	    $('#additionalImage').append('<h4></h4><div class="row" id="row-image_'+id+'">'
	    		+'<div class="col-md-4 col-xs-6"><div class="input-group svX3">'
	    		+'<label for="inputImageType_'+id+'" class="input-group-addon imagetype-width" id="imageType-addon_'+id+'">Image Type :&nbsp;</label>'
	    		+'<select class="form-control" aria-describedby="imageType-addon_'+id+'" id="inputImageType_'+id+'" name="imageType_'+id+'">'
	    		+ '<option value="0"></option><option value="1">Application Form</option><option value="2">ID Proof</option><option value="3">Proof of Billing</option><option value="4">Proof of Income</option>'
	    		+'</select>'
	    		+'</div>'
	    		+'</div>'
	    		+'<div class="col-md-8 col-xs-6"><div class="input-group">'
	    		+'<input type="file" class="form-control" id="image_'+id+'" name="image_'+id+'" aria-describedby="image_'+id+'-addon" />'
	    		+'<span class="input-group-btn"><button type="button" class="btn btn-primary" onclick="resetForm(\'image_'+id+'\')">Clear</button></span></div></div></div>');

	});

	$("#removeLink").click(function () {
		var id = ($('.additionalImage .row').length).toString();
	    $("#additionalImage #row-image_"+ id).remove();
	});
	/*! Customed function for dynamic addition of image with image type */
};