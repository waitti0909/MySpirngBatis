;
// $.log( xxxxxxxxxxxxxxxxxx );
(function(a) {
	function _logMsie(value){
		for(var key in value){
			if(typeof(value[key]) ==  'string'){
				console.log(key + ":" + value[key]);
			}else{
				arguments.callee(value[key]);
			}
		}
	}
	
	a.log = function() {
		if (window.console && window.console.log && window.console.log.apply) {
			if(!$.support.leadingWhitespace){
				console.log("{");
				a.each(arguments,function(index,value){
					if(typeof(value) ==  'string'){
						console.log(value);
					}else{
						_logMsie(value);
					}
				});
				console.log("}");
			}else{
				console.log.apply(window.console, arguments);
			}
		}
	};
	a.fn.log = function() {
		a.log(this);
		return this;
	};
})(jQuery);