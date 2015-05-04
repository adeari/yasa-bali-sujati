$.ajaxSetup({
	method : 'POST',
	beforeSend: function(xhr) {
			$('body').css({'overflow':'hidden'});
         	$('#loading').remove();
        	$('#content').fadeOut().parent().prepend('<div id="loading" class="center"><div class="center"></div></div>');
     },
     complete: function(xhr,status) {
     	$('#loading').remove();
        $('#content').fadeIn();
     }
});

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}