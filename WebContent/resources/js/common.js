$(function() {

	$(window).resize(function() {
		$(window).unbind("onresize");

		$(window).bind("onresize", this);
	}).trigger('resize');

	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	$.extend($.jgrid.defaults, {
		mtype : 'POST',
		datatype : "json",
		rowNum : 10,
		rowList : [ 10, 20, 50, 100, 1000 ],
		height : 'auto',
		viewrecords : true,
		rownumbers : true,
		rownumWidth : 50,
		altRows : true,
		altclass : 'jqgrid-zebra',
		prmNames : {
			page : "page",
			rows : "size",
			sort : "sort",
			order : "order"
		},
		loadError : function(xhr, status, error) {
			alert(error);
		},
		loadComplete : function(data) {
			// $.log(data);
		}
	});
	
	jQuery.extend($.fn.fmatter , {
	    intMoney : function(cellvalue, opts, rowdata) {
	    
	    	var op = $.extend({},opts.integer);
	    	
	    	op = $.extend({},op,{thousandsSeparator: ",", defaultValue: '0'});
	    	
	    	if($.fmatter.isEmpty(cellvalue)) {
	    		return op.defaultValue;
	    	}
	    
	    	return $.fmatter.util.NumberFormat(cellvalue,op);
	}
	});
	jQuery.extend($.fn.fmatter.intMoney , {
	    unformat : function(cellvalue, options) {
	    return cellvalue.replace(/,/g,"");
	}
	});
	
	
	
	$.extend($.fancybox.defaults, {
		keys : {
			close : null
		}
	});

	$.extend($.ui.dialog.prototype.options, {
		closeOnEscape : false
	});
	
	$.datepicker.setDefaults({
		yearRange : "c-20:c+20"
	});

});

// Charlie Test 20150408 add
var intTimeStep=20;
var isIe=(window.ActiveXObject)?true:false;
var intAlphaStep=(isIe)?5:0.05;
var curSObj=null;
var curOpacity=null;
var stopTime=0;

function startObjMessage(objId){
	curSObj=document.getElementById(objId);
	if(isIe){
		curSObj.style.cssText='DISPLAY: none; Z-INDEX: 1; FILTER: alpha(opacity=0); POSITION: absolute;';
	}
	setMessage();
}

function setMessage(){
	if(isIe){
		curSObj.filters.alpha.opacity=0;
	}else{
		curOpacity=0;
		curSObj.style.opacity=0;
	}
	curSObj.style.display='';
	setMessageShow();
}

function setMessageShow(){
	if(isIe){
		curSObj.filters.alpha.opacity+=intAlphaStep;
		if (curSObj.filters.alpha.opacity<100) {
			setTimeout('setMessageShow()', intTimeStep);
		}else{
			stopTime+=10;
			if(stopTime<500){
				setTimeout('setMessageShow()', intTimeStep);
			}else{
				stopTime=0;
				setMessageClose();
			}
		}
	}else{
		curOpacity+=intAlphaStep;
		curSObj.style.opacity =curOpacity;
		if (curOpacity<1) {
			setTimeout('setMessageShow()',intTimeStep);
		}else{
			stopTime+=10;
			if(stopTime<200){
				setTimeout('setMessageShow()',intTimeStep);
			}else{
				stopTime=0;
				setMessageClose();
			}
		}
	}
}

function setMessageClose(){
	if(isIe){
		curSObj.filters.alpha.opacity-=intAlphaStep;
		if (curSObj.filters.alpha.opacity>0) {
			setTimeout('setMessageClose()',intTimeStep);
		}else {
			curSObj.style.display='none';
		}
	}else{
		curOpacity-=intAlphaStep;
		if (curOpacity>0) {
			curSObj.style.opacity =curOpacity;
			setTimeout('setMessageClose()',intTimeStep);
		}else {
			curSObj.style.display='none';
		}
	}
}

/**
 * 將金額轉為正規表示式
 * @param nStr
 * @returns
 */
function addCommas(nStr) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}
/**
 * 開啟Dialog元件
 * 
 * @param frameId
 * @param frameTitle
 * @param frameData
 */
function initialDialogFrame(frameId, frameTitle, frameData, winWidth, winHeight) {
	if (winWidth == undefined) {
		winWidth = $(window).width() - 50;
	}
	if (winHeight == undefined) {
		winHeight = $(window).height() - 50;
	}
	$("#" + frameId).html(frameData);
	$("#" + frameId).dialog({
		width : winWidth,
		height : winHeight,
		modal : true,
		title : frameTitle,
		draggable : false,
		position : {
			my : "center",
			at : "center"
		},
		closeOnEscape : false,
		close : function() {
			$("#" + frameId).html('');
			$("#" + frameId).dialog('destroy');
		},
		open : function(event, ui) {
			$('.ui-dialog').css('z-index', 10300);
			$('.ui-widget-overlay').css('z-index', 10200);
		}
	});
}

/**
 * 開啟共用地址頁函式
 * 
 * @param frameObj
 * @param addressData
 */
function openAddressDialogFrame(targetFrameId, addressJsonText) {
	$
			.ajax({
				url : CONTEXT_URL + "/common/address/initLoad",
				type : 'POST',
				contentType : "application/json",
				data : JSON.stringify(addressJsonText),
				dataType : "html",
				async : false,
				success : function(data) {
					var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
							+ targetFrameId + " />";
					initialDialogFrame(targetFrameId, "地址正規輸入", hiddenFrameId
							+ data);
				}
			});
}

/**
 * 開啟共用檔案上傳頁函式
 * 
 * @param targetFrameId
 * @param typePath
 * @param docNo
 * @param fileType
 */
function openFileDialogFrame(targetFrameId, typePath, docNo, fileType) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when
		// run from local file
		type : 'GET',
		url : CONTEXT_URL + "/common/file/initLoad",
		data : {
			"typePath" : typePath,
			"fileDoc" : docNo,
			"fileType" : fileType
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "檔案上傳/下載", data);
		}
	});
}

/**
 * 開啟共用檔案下載頁函式
 * 
 * @param targetFrameId
 * @param typePath
 * @param docNo
 * @param fileType
 */
function openFileDownFrame(targetFrameId, typePath, docNo, fileType) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when
		// run from local file
		type : 'GET',
		url : CONTEXT_URL + "/common/file/initDownload",
		data : {
			"typePath" : typePath,
			"fileDoc" : docNo,
			"fileType" : fileType
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "檔案下載", data, 950, 600);
			getFileList();
		}
	});
}

/**
 * 開啟基站檔案下載頁函式
 * 
 * @param targetFrameId
 * @param typePath
 * @param docNo
 * @param fileType
 */
function openSiteFileDownFrame(targetFrameId, siteId, btsSiteId) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when
		// run from local file
		type : 'GET',
		url : CONTEXT_URL + "/st/file/initDownload",
		data : {
			"siteId" : siteId,
			"btsSiteId" : btsSiteId,
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "基站附件下載", data, 950, 600);
			getFileList();
		}
	});
}

/**
 * 開啟共用使用者頁函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */
function openUserDialogFrame(targetFrameId, callBackFunName) {
	$
			.ajax({
				mimeType : 'text/html; charset=utf-8',
				type : 'POST',
				url : CONTEXT_URL + "/common/personnel/initLoad",
				data : {
					"callBackFun" : callBackFunName
				},
				dataType : "html",
				async : false,
				success : function(data) {
					var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
							+ targetFrameId + " />";
					initialDialogFrame(targetFrameId, "使用者選擇", hiddenFrameId
							+ data);
				}
			});
}
/**
 * 開啟共用基站查詢頁面函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */
function openSiteDialogFrame(targetFrameId, callBackFunName, domainId,
		selectMode,isClosed,siteType,locationId) {

	//isClosed為close視窗就不會關閉
	if (typeof locationId == 'undefined') {
		locationId = "";
	}
	if (typeof selectMode == 'undefined') {
		selectMode = "single";
	}
	// alert(siteCallBackFun );
	// alert(callBackFunName);
	// alert(domainId);
	// alert(selectMode);
	
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		type : 'POST',
		url : CONTEXT_URL + "/common/st002SP1/init",
		data : {
			"callBackFun" : callBackFunName,
			"domainId" : domainId,
			"locationId" : locationId,
			"targetFrameId" : targetFrameId,
			"selectMode" : selectMode,
			"isClosed" : isClosed,
			"siteType" : siteType
		},
		dataType : "html",
		async : false,
		success : function(data) {
			// alert("9999999999")
			initialDialogFrame(targetFrameId, "基站查詢", data);
		}
	});
}

/**
 * 開啟共用廠商查詢頁面函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */
function openCompanyQueryFrame(targetFrameId, callBackFunName) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		type : 'POST',
		url : CONTEXT_URL + "/common/companyQuery/initLoad",
		data : {
			"callBackFun" : callBackFunName,
			"targetFrameId" : targetFrameId,
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "廠商查詢", data);
		}
	});
}

/**
 * 開啟共用PO單查詢頁面函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */
function openCommonPoFrame(targetFrameId, callBackFunName) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		url : CONTEXT_URL + "/cm/po/common/init",
		type : 'POST',
		dataType : "html",
		data : {
			"callBackFun" : callBackFunName,
			"targetFrameId" : targetFrameId,
		},
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "PO單查詢", data);
		}
	});
}

/**
 * 開啟 加會簽頁面
 * 
 * @param targetFrameId
 * @param callBackFunName
 *            "showCountSignPage",'${isAgent}','${taskId}', '${processType}',
 *            '${applyNo}', '${taskOwnerId}','${taskOwnerGroupId}',$('#approvalComment').val(),
 *            '${taskStartTime}',approveType, jsonData
 */
function openCounterSignDialogFrame(targetFrameId, isAgent, taskId,
		processType, applyNo, taskOwnerId, taskOwnerGroupId, approvalComment,
		taskStartTime, approveType, jsonData) {
	$.ajax({
		type : 'POST',
		url : CONTEXT_URL + "/commom/counterSign",
		data : {
			"isAgent" : isAgent,
			"taskId" : taskId,
			"processType" : processType,
			"applyNo" : applyNo,
			"taskOwnerId" : taskOwnerId,
			"taskOwnerGroupId" : taskOwnerGroupId,
			"approvalComment" : approvalComment,
			"taskStartTime" : taskStartTime,
			"approveType" : approveType,
			"jsonData" : jsonData
		},
		dataType : "html",
		async : false,
		success : function(data) {
			var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
					+ targetFrameId + " />";
			initialDialogFrame(targetFrameId, "加/會簽", hiddenFrameId
					+ data);
		}
	});
}

/**
 * 開啟單筆簽核
 * 
 * @param params
 */
function openApproval(params) {
	$.fancybox.open({
		href : CONTEXT_URL + "/workflow/openTask",
		type : 'ajax',
		width : $(window).width(),
		height : $(window).height(),
		autoSize : false,
		padding : [ 0, 0, 0, 0 ],
		scrolling : 'yes',
		helpers : {
			overlay : {
				closeClick : false
			},
			title : {
				type : 'float'
			}
		},
		title : "簽核",
		openEffect : 'elastic',
		closeEffect : 'fade',
		ajax : {
			data : {
				'isAgent' : params[0],
				'taskId' : params[1],
				'applyNo' : params[2],
				'processType' : params[3],
				'processName' : params[4],
				'taskName' : params[5],
				'taskOwnerId' : params[6],
				'taskOwnerGroupId' : params[7],
				'taskStartTime' : params[8],
				'userTaskType' : params[9],
				'jsonData' : params[10]
			},
			type : "POST",
			success : function(data) {

			},
			error : function(jqXHR, testStatus, errorThrown) {
				alert(errorThrown);
			},
			async : false
		},
        afterClose : function() {
        	validateWFVerify = function (){return true;}
		}
	});
}

/**
 * 開啟單筆工單工作
 * 
 * @param params
 */
function openWorkPage(url, typeDesc, docNo) {
	$.fancybox.open({
		href : CONTEXT_URL + url + "&fromTODO=Y",
		type : 'ajax',
		width : $(window).width(),
		height : $(window).height(),
		autoSize : false,
		padding : [ 0, 0, 0, 0 ],
		scrolling : 'yes',
		helpers : {
			overlay : {
				closeClick : false
			},
			title : {
				type : 'float'
			}
		},
		title : typeDesc + " - " + docNo,
		openEffect : 'elastic',
		closeEffect : 'fade',
		ajax : {
			type : "POST",
			success : function(data) {

			},
			error : function(jqXHR, testStatus, errorThrown) {
				alert(jqXHR);
			},
			async : false
		},
        afterClose : function() {
        	LoadAjaxContent(CONTEXT_URL + '/profile/todoOrders');
       		
        }
	});
}

/**
 * 開啟共用料號查詢頁面函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */
function openMaterialFrame(targetFrameId) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		type : 'POST',
		url : CONTEXT_URL + "/inv/inv022SP/init",
		data : {
			"targetFrameId" : targetFrameId
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "料號查詢", data);
		}
	});
}

/**
 * 開啟退料站台頁函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */
function openMrPageDialogFrame(targetFrameId, callBackFunName, matNoList) {
	$
			.ajax({
				mimeType : 'text/html; charset=utf-8',
				type : 'POST',
				url : CONTEXT_URL + "/inv/inv012/mrLoad",
				data : {
					"callBackFun" : callBackFunName,
					"matNoList" : matNoList
				},
				dataType : "html",
				async : false,
				success : function(data) {
					var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
							+ targetFrameId + " />";
					initialDialogFrame(targetFrameId, "查詢退料站台", hiddenFrameId
							+ data);
				}
			});
}

/**
 * 工項設定
 * 
 * @param poId
 * @param callBackFunName
 */
function openOpItemDialogFrame(targetFrameId, poId) {

	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		type : 'POST',
		url : CONTEXT_URL + "/st/outSourcing/out/itemMainSearchTable",
		data : {
			"poId" : poId
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "工項設定", data);
		}
	});
}

/**
 * 主/中項新增
 * 
 * @param targetFrameId
 * @param parentId
 * @param typePath
 */
function openAddItemDialogFrame(targetFrameId, parentId, path) {

	$
			.ajax({
				mimeType : 'text/html; charset=utf-8',
				type : 'POST',
				url : CONTEXT_URL + "/basic/cm003/" + path,
				data : {
					parentId : parentId
				},
				dataType : "html",
				async : false,
				success : function(data) {
					var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
							+ targetFrameId + " />";
					initialDialogFrame(targetFrameId, "工項維護", hiddenFrameId
							+ data);
				}
			});
}

/**
 * 開啟站台頁函式
 * 
 * @param targetFrameId
 * @param callBackFunName
 */

function openPageDialogFrame(targetFrameId,  url,formName,gridId,totalAmt,backFun,edit) {
	$.ajax({
				mimeType : 'text/html; charset=utf-8',
				type : 'POST',
				url : CONTEXT_URL + url,
				data : {
					'backFun' : backFun,
					'gridId' : gridId,
					'totalAmt' : totalAmt,
					'edit' : edit
				},
				dataType : "html",
				async : false,
				success : function(data) {
					var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
							+ targetFrameId + " />";
					initialDialogFrame(targetFrameId, formName, hiddenFrameId
							+ data);
				}
			});
}

/**
 * 開啟專線查詢頁面函式
 * @param targetFrameId
 * @param callBackFunName
 * @param currentMenuId
 */
function openLineQueryFrame(targetFrameId, callBackFunName , currentMenuId) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		type : 'POST',
		url : CONTEXT_URL + "/st/line/lineApply/searchLine",
		data : {
			"callBackFun" : callBackFunName,
			"targetFrameId" : targetFrameId,
			"currentMenuId" : currentMenuId
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "專線查詢", data);
		}
	});
}

/**
 * 開啟工單查詢頁
 * @param targetFrameId
 * @param callBackFunName
 * @param currentMenuId
 */
function openWorkQueryFrame(targetFrameId, callBackFunName , currentMenuId) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		type : 'POST',
		url : CONTEXT_URL + "/otr/otr001/initOTR001SP1",
		data : {
			"callBackFun" : callBackFunName,
			"targetFrameId" : targetFrameId,
			"currentMenuId" : currentMenuId
		},
		dataType : "html",
		async : false,
		success : function(data) {
			initialDialogFrame(targetFrameId, "工單查詢", data);
		}
	});
}


function clear_form_elements(id) {
	  jQuery("#"+id).find(':input').each(function() {
	    switch(this.type) {
	        case 'password':
	        case 'text':
	        case 'number':
	        case 'hidden':
	        case 'textarea':
	        case 'file':
	        case 'select-one':
	        case 'select':
	        case 'select-multiple':
	            jQuery(this).val('');
	            break;
	        case 'checkbox':
	        case 'radio':
	            this.checked = false;
	    }
	  });
	}