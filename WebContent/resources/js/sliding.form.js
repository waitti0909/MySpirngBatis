$(function() {
	// sum and save the widths of each one of the fieldsets
	// set the final sum as the total width of the steps element
	var stepsWidth = 0;
    var widths = new Array();
	$('#tab_container .tab_content').each(function(i){
		widths[i] = stepsWidth;
		$(this).width($(window).width());
        stepsWidth += $(this).width();
    });
	$('#tab_container').width(stepsWidth);
	
	// show the navigation bar
	$('#tab_navigation').show();
	$('#tab_navigation ul li').first().addClass("selected");
	
	// when clicking on a navigation link 
	// the form slides to the corresponding fieldset
    $('#tab_navigation ul li').click(function(e){
//		$('#tab_navigation ul li').removeClass('selected');
    	$(this).closest('ul').find('li').removeClass('selected');
		$(this).addClass('selected');
        $('#tab_container').stop().animate({
            marginLeft: '-' + widths[$(this).index()] + 'px'
        }, 500, function(){});
        e.preventDefault();
    });
	
});