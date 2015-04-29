<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ page import="com.foya.noms.resources.AppConstants" %>
<!-- ==================== CSS plugins =================== -->
<link rel="shortcut icon" href="<s:url value="/resources/img/favicon.ico" />">

<!-- Bootstrap core CSS -->
<%-- <link href="<s:url value="/resources/css/text-style.css" />" rel="stylesheet"> --%>
<link href="<s:url value="/resources/plugins/bootstrap/bootstrap.css" />" rel="stylesheet">
<link href="<s:url value="/resources/plugins/fontawesome/css/font-awesome.css" />" rel="stylesheet">
<link href='<s:url value="/resources/css/righteous.css" />'	rel='stylesheet' type='text/css'>
<link href="<s:url value="/resources/plugins/fancybox/jquery.fancybox.css" />"	rel="stylesheet">
<link href="<s:url value="/resources/plugins/fullcalendar/fullcalendar.css" />"	rel="stylesheet">
<link href="<s:url value="/resources/plugins/xcharts/xcharts.min.css" />"	rel="stylesheet">
<link href="<s:url value="/resources/plugins/select2/select2.css" />"	rel="stylesheet">	
<link href="<s:url value="/resources/plugins/jquery-ui/jquery-ui.css" />" rel="stylesheet">		
<link href="<s:url value="/resources/plugins/jquery-ui/theme/redmond/jquery-ui-1.10.4.custom.css" />" rel="stylesheet">
<link href="<s:url value="/resources/plugins/jqGrid/ui.jqgrid.css" />" rel="stylesheet" type="text/css" media="screen">
<link href="<s:url value="/resources/plugins/jquery-multi-select/css/multi-select.css" />" rel="stylesheet" type="text/css" media="screen">	
<link href="<s:url value="/resources/css/tab.css" />" rel="stylesheet">
<link href="<s:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<s:url value="/resources/plugins/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.css" />" rel="stylesheet">
<link href="<s:url value="/resources/plugins/vakata-jstree/dist/themes/default/style.min.css" />" rel="stylesheet">
<link href="<s:url value="/resources/plugins/fancybox/jquery.fancybox.css?v=2.1.5" />" media="screen" rel="stylesheet">
<link href="<s:url value="/resources/plugins/fineuploader/fineuploader-5.0.1.css" />" media="screen" rel="stylesheet">
<%-- <link href="<s:url value="/resources/plugins/poshytip-1.2/src/tip-darkgray/tip-darkgray.css" />" rel="stylesheet"> --%>
<link href="<s:url value="/resources/plugins/poshytip-1.2/src/tip-violet/tip-violet.css" />" rel="stylesheet">
<link href="<s:url value="/resources/plugins/jquery-ui/ui.totop.css" />" rel="stylesheet">	
<link href="<s:url value="/resources/plugins/sweetalert-master/lib/sweet-alert.css" />" rel="stylesheet">
		
<style type="text/css">
tr.row_selected_highlight td {
	background-color:lightblue !important;
}
</style>
<!-- ==================== Javascript plugins =================== -->
<script	src="<s:url value="/resources/plugins/jquery/jquery-2.1.0.min.js" />"></script>
<script	src="<s:url value="/resources/plugins/jquery/jquery.mask.min.js" />"></script>
<script	src="<s:url value="/resources/plugins/jquery-ui/jquery-ui.js" />"></script>
<script	src="<s:url value="/resources/plugins/bootstrap/bootstrap.min.js" />"></script>
<script	src="<s:url value="/resources/plugins/datatables/jquery.dataTables.js" />"></script>
<script	src="<s:url value="/resources/plugins/datatables/ZeroClipboard.js" />"></script>
<script	src="<s:url value="/resources/plugins/datatables/TableTools.js" />"></script>
<script	src="<s:url value="/resources/plugins/datatables/dataTables.bootstrap.js" />"></script>
<script	src="<s:url value="/resources/js/jquery.log.js" />"></script>
<script	src="<s:url value="/resources/js/jquery.blockUI.js" />"></script>
<script	src="<s:url value="/resources/js/form2js.js" />"></script>
<script	src="<s:url value="/resources/js/sliding.form.js" />"></script>
<script src="<s:url value="/resources/plugins/fancybox/jquery.fancybox.js?v=2.1.5" />"></script> 
<script src="<s:url value="/resources/plugins/jqGrid/jquery.jqGrid.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-multi-select/js/jquery.multi-select.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-multi-select/js/jquery-quicksearch.js" />"></script>
<script src="<s:url value="/resources/plugins/vakata-jstree/dist/jstree.min.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-ui/i18n/jquery.ui.datepicker-zh-TW.min.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-ui-timepicker-addon/jquery-ui-sliderAccess.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-ui-timepicker-addon/i18n/jquery-ui-timepicker-zh-TW.js" />"></script>
<script src="<s:url value="/resources/plugins/moment/moment.min.js" />"></script>
<script	src="<s:url value="/resources/plugins/fineuploader/jquery.fineuploader-5.0.1.js" />"></script>
<script src="<s:url value="/resources/plugins/poshytip-1.2/src/jquery.poshytip.min.js" />"></script>
<script	src="<s:url value="/resources/plugins/jquery-ui/jquery.ui.totop.js" />"></script>
<script	src="<s:url value="/resources/js/workflow.js" />"></script>
<script	src="<s:url value="/resources/js/address.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script	src="<s:url value="/resources/js/locationValidate.js" />"></script>
<script	src="<s:url value="/resources/plugins/sweetalert-master/lib/sweet-alert.js" />"></script>
<script	src="<s:url value="/resources/plugins/jquery/jshashtable_src.js" />"></script>
<% 
    //String _sLocale = ((request.getLocale().toString()).substring(3)).toLowerCase();
    String jqGridI18n = request.getContextPath() + "/resources/i18n/grid.locale-tw.js";
%>
   
<script type="text/javascript" src="<%=jqGridI18n%>"></script>

<script type="text/javascript">
		var environment = "<%=AppConstants.ENVIRONMENT%>";
		if (environment == 'FOYA') {	// Charlie Test 20150311
			window.alert = function(txt){
				try {
					swal("", txt, "warning");
				} catch (e) {
					console.log(e);
				}
			}
			$.blockUI.defaults.message = '<img src="/noms/resources/img/loadingPlane.gif" />';
			$.blockUI.defaults.css = {
					padding:	0,
					margin:		0,
					width:		'30%',
					top:		'40%',
					left:		'35%',
					textAlign:	'center',
					color:		'#000',
					cursor:		'wait'
			}
		}
		
		var CONTEXT_URL = '<%= request.getContextPath()%>';
		var NOMS_GLOBAL_CONSTANT = {
		};
		$(function() {

			$(document).ajaxStart($.blockUI).ajaxComplete($.unblockUI);
			
			$(document).ajaxError(function(event, xhr, settings, thrownError) {
				$.unblockUI();
				var stack = "", errMsg = "";
				var data = xhr.responseText ;
				if (xhr.status == "500") {
					var obj = $.parseJSON(data);
// 					alertMsg = obj.msg; 
					errMsg = obj.msg;
					stack = obj.maps['stack'];
				}
				///alert("AJAX failed : " + thrownError);
				saySorry(xhr.status, settings.url, errMsg, stack);
				
			}).ajaxComplete(function( event, xhr, ajaxOptions ) {
				$.unblockUI();
				//$.log(event);
				//$.log(xhr);
				//$.log(ajaxOptions);
				var statusCode = xhr.status;
				if(statusCode == "511"){
					//ajax no auth
					//alert('Network session Timeout.');
					location.reload();
				}				
			});	
			
		});
		
		function saySorry(statusCode, requestUrl, errMsg, stack) {
			$('.ui-dialog').css('z-index',7000);
	    	$('.ui-widget-overlay').css('z-index',7000);
			$.fancybox.open({
	 			href : CONTEXT_URL + "/exception/sorryPage",
	 			type : 'ajax',
	 			width : $(window).width(),
	          	height : $(window).height(),
	 			autoSize : false,
	 			padding : [0,0,0,0],
	 			scrolling : 'yes',
	 			helpers:{
	 				overlay:{closeClick:false},
	 				title : {type : 'float'}
	 			},
	 			title : statusCode,
	 			openEffect : 'elastic',
	 			closeEffect : 'fade',
	 			ajax : {
	 				type : 'POST',
					data : {
						"statusCode" : statusCode,
						"requestUrl" : requestUrl,
						"errMsg" : errMsg,
						"stack" : stack
					}
	 	        },
	 			afterClose : function() {
	 				//location.reload();			
	 			}
	 		});	
		}
	</script>

<!-- All functions for this theme + document.ready processing -->
<script src="<s:url value="/resources/js/main.js" />"></script>
<script src="<s:url value="/resources/js/common.js" />"></script>
<script type="text/javascript">
	$(function() {
		$('#sidebar-left').resizable({
			maxWidth: 396,
			resize: function( event, ui ) {
				 var mainWidth = $(this).width();
					
			}
		});
		
		$(window).resize(function(){
		    $(window).unbind("onresize");
		    $("#grid").setGridWidth($(".container").width()-10);
		    $(window).bind("onresize", this);
		}).trigger('resize');

		$.extend($.ui.datepicker.defaults, {
			yearRange : "c-20:c+20"
		});	
		loadCityArea();
	});

	function loadCityArea(){
		$.ajax({
			type : "get",
			url : CONTEXT_URL + "/common/address/allCities",  
			contentType : 'application/json',
			dataType : 'json',  
			cache: false,
			async: false,
			success : function(jsonResponse) {
				var dataToStore = JSON.stringify(jsonResponse);
				localStorage.removeItem("cities");
				localStorage.setItem('cities', dataToStore);				
			},  
			error: function(jqXHR, textStatus, errorThrown){
				
			} 
		});

		$.ajax({
			type : "get",
			url : CONTEXT_URL + "/common/address/allTowns",  
			contentType : 'application/json',
			dataType : 'json',  
			cache: false,
			async: false,
			success : function(jsonResponse) {
				var dataToStore = JSON.stringify(jsonResponse);
				localStorage.removeItem("towns");
				localStorage.setItem('towns', dataToStore);				
			},  
			error: function(jqXHR, textStatus, errorThrown){
				
			} 
		});
		$.ajax({
			type : "get",
			url : CONTEXT_URL + "/common/address/allCityTownsDomainTeam",  
			contentType : 'application/json',
			dataType : 'json',  
			cache: false,
			async: false,
			success : function(jsonResponse) {
				var dataToStore = JSON.stringify(jsonResponse);
				localStorage.removeItem("cityTownsDomainTeam");
				localStorage.setItem('cityTownsDomainTeam', dataToStore);				
			},  
			error: function(jqXHR, textStatus, errorThrown){
				
			} 
		});		
	}
</script>