(function ( $ ) {
    $.fn.FormCache = function( options ) {
        function on_change(event) {
            var input = $(event.target);
            var key = input.parents('form:first').attr('name');
        	var data = JSON.parse(sessionStorage[key]);
            
            if(input.attr('type') == 'radio' || input.attr('type') == 'checkbox') {
                data[input.attr('name')] = input.is(':checked');
            }else {
                data[input.attr('name')] = input.val();
            }
            
            sessionStorage[key] = JSON.stringify(data);

             
        }
        
        return this.each(function() {    
            var element = $(this);
            
            if(typeof(Storage)!=="undefined"){
	            var key = element.attr('name');
	            	
	            	var data = false;
	                if(sessionStorage[key]) {
	                    data = JSON.parse(sessionStorage[key]);
	                }
	                
	                if(!data) {
	                    sessionStorage[key] = JSON.stringify({});
	                    data = JSON.parse(sessionStorage[key]);
	                }
	                element.find('input, select').focusout(on_change);
	                //element.find('input, select').change(on_change);
	                
	                element.find('input, select').each(function(){
	                	//do not include _csrf token
	                    if($(this).attr('type') != 'submit' && $(this).attr('name') != '_csrf' && $(this).attr('name') != 'applicationNo') {
	                        var input = $(this);
	                        var value = data[input.attr('name')];
	                        if(input.attr('type') == 'radio' || input.attr('type') == 'checkbox') {
	                            if(value) {
	                                input.attr('checked', input.is(':checked'));
	                            } else {
	                                input.removeAttr('checked');
	                            }
	                        } else {
	                            input.val(value);
	                        }
	                    }
	                });

            }
            else {
                alert('local storage is not available');
            }
        });
    };     
}( jQuery ))