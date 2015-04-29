<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	
	var invTrActDataList;
	
	function errorMsg(keyCode){
		alert("請勿輸入中文");
	}
	
	function checkNumber(obj,trQty,inQty,id){
		var number = $(obj).val();
		var max = parseInt(trQty);
		
		inQty = inQty==null?0:inQty;
		
		var invTrDtlData = invTrActDataList[(id-1)];
		
		if(number == ""){
			number = 0;
		}
		
		if((parseInt(number)+parseInt(inQty)) > max){
			alert("調入數不可大於已調入數。");
			$(obj).val(0);
			invTrDtlData.exportNumber = 0;
			return;
		}
		
		invTrDtlData.exportNumber = number;
		
	}
	
	function getGridWidth(){
		return (parseInt($(window).width()) - 100);
	}
	
	$(document).ready(function() {
		
		console.log('${invTrDto}');
		
		$('#inv009MDtlResult').css('width',getGridWidth());
		
		mountGrid();
		
		$("#inv009MDtlGrid").setGridParam({datatype:"json",postData:{'trNo':'${invTrDto.tr_no}'}, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");

		mountButEvent();

	});
	
	//掛載表格
	function mountGrid(){
		
// 		var selectID = null;
		
		$('#inv009MDtlGrid').jqGrid({
			datatype: "local",
			//pager: '#inv009MDtlPager',
			height:'120px',
			url : CONTEXT_URL+"/inv/inv009M/getInvTrExportDtlData",
// 			colNames : [ '料號', '品名','廠商序號','貼標號碼', '物料狀態', '申請調撥數', '已調入數', '本次調入數'],
			colNames : [ '料號', '品名','廠商序號','貼標號碼', '物料狀態', '已調出數','已調入數', '本次調入數'],
			colModel : [ {name : 'mat_NO',index : 'mat_no',align : 'center',sortable : false},
			             {name : 'matName',index : 'mat_name',align : 'center',sortable : false},
			             {name : 'venSn',index : 'venSn',align : 'center',sortable : false},
						 {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false},
			             {name : 'matStatusStr',index : 'matStatusStr',align : 'center',sortable : false},
			             //{name : 'appQty',index : 'appQty',align : 'center',sortable : false},
			             {name : 'tr_QTY',index : 'trQty',align : 'center',sortable : false},
			             {name : 'dtlTrQty',index : 'trQty',align : 'center',sortable : false},
			             {name : 'exportNumber',index : 'exportNumber',align : 'center',sortable : false,
			            	 formatter:function(cellvalue, options, rowObject){
	   							var trQty = rowObject.tr_QTY;
	   							var inQty = rowObject.dtlTrQty;
	   							var id = options.rowId;
	   							if(parseInt(rowObject.tr_QTY)>parseInt(rowObject.dtlTrQty) || rowObject.dtlTrQty==null){
			   						return "<input id='inv009MDtlGrid_"+id+"' type='text' value='0' onkeypress='return event.charCode >= 48 && event.charCode <= 57' onkeyDown='if(event.keyCode==229)errorMsg()' onkeyup='checkNumber(this,"+trQty+","+inQty+","+id+")'></label>";
	   							}
	   							return "";
				             }
			            	}
			            ],
			viewrecords : true,
			caption : "申請明細",
			loadonce: true,
			rowNum:-1,
			onSelectRow : function(id) {
				var invTrActData = invTrActDataList[(id-1)];
				
				var data = {
					'trNo':invTrActData.tr_NO,
					'matNo':invTrActData.mat_NO,
					'dtlNo':invTrActData.tr_DTL_NO,
					'trIn':'1',
					'outActNo':invTrActData.tr_ACT_NO
				};
				
				$("#inv009MHistoryGrid").setGridParam({datatype:"json",postData : data}).trigger("reloadGrid");
			},
			gridComplete : function() {},
			ondblClickRow : function(rowId) {},
			gridComplete:function(){},
			beforeProcessing: function (data, status, xhr) {
				invTrActDataList = data.rows;
            }
		});
		
		$('#inv009MHistoryGrid').jqGrid({
			datatype : "local",
			pager: '#inv009MHistoryPager',
			url : CONTEXT_URL+"/inv/inv008M/exportHistorySearch",
			colNames : [ '調入日期', '廠商序號','貼標號碼','調出數量','調入人員' ],
			colModel : [ {name : 'tr_IN_DATE',index : 'tr_out_date',align : 'center',sortable : false,formatter: "date", formatoptions:{newformat: "Y-m-d"}},
			             {name : 'venSn',index : 'venSn',align : 'center',sortable : false}, 
			             {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false}, 
			             {name : 'tr_QTY',index : 'tr_qty',align : 'center',sortable : false},
			             {name : 'tr_IN_USER',index : 'tr_out_user',align : 'center',sortable : false} 
			            ],
			viewrecords : true,
			caption : "調入歷程",
			rownumbers : true,
			onSelectRow : function(id) {
			},
			gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
			}
		});
		
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#inv009MDtlGrid,.grid").setGridWidth($("#inv009MJQgrid").width() - 10);
			$("#inv009MHistoryGrid,.grid").setGridWidth($("#inv009MJQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
	}
	
	//掛載按鈕事件
	function mountButEvent(){
		
		$("#saveBtn").click(function(e){
			$.ajax({			
				url : CONTEXT_URL+"/inv/inv009M/executionImportWork",		
				data: {
						'action':'1',
						'invTrDto':JSON.stringify(invTrDtoToJson()),
						'TbInvTrActDto':JSON.stringify(invTrActDataList)
					},
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if(data.success){
						parent.$.fancybox.close();
					}
					alert(data.msg);
				}
			});
		});
		
		$("#closeBtn").click(function(e){
			
			var number = 0;
			
			//取的此單號申請數
			$.ajax({			
				url : CONTEXT_URL+"/inv/inv009M/getInvTrApplyNumber",		
				data: {
						'trNo':'${invTrDto.tr_no}'
					},
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if (data.success) {
						if (data.rows.length > 0) {
							for (var i = 0; i < data.rows.length; i++) {
								number += parseInt(data.rows[i].appQty);
							}
						}
					}
				}
			});
			
			var trQty = 0 ;
			var importNumber = 0 ;
			
			for(var i=0 ; i<invTrActDataList.length ; i++){
				trQty += invTrActDataList[i].dtlTrQty==null? 0 : parseInt(invTrActDataList[i].dtlTrQty);
				importNumber += parseInt(invTrActDataList[i].exportNumber);
			}
			
			console.log(number + " - "+(trQty+importNumber));
			var action = parseInt(number) == parseInt(trQty+importNumber)?'2':'3';
			
			var enforcementClose = action =='3' ? confirm('尚未有料號調入，是否強制結案?') : true;

			if(enforcementClose){
				$.ajax({			
					url : CONTEXT_URL+"/inv/inv009M/executionImportWork",		
					data: {
							'action':action,
							'invTrDto':JSON.stringify(invTrDtoToJson()),
							'TbInvTrActDto':JSON.stringify(invTrActDataList)
						},
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							parent.$.fancybox.close();
						}
						alert(data.msg);
					}
				});
			}
		});
		
	}
	
	//TB_INV_TR To JSON
	function invTrDtoToJson(){
		
		var serviceMap = {};
		serviceMap["tr_no"]='${invTrDto.tr_no}';
		serviceMap["tr_in_wh_id"]='${invTrDto.tr_in_wh_id}';
		serviceMap["tr_out_wh_id"]='${invTrDto.tr_out_wh_id}';
		serviceMap["status"]='${invTrDto.status}';
		return serviceMap;
	}
</script>
</head>
<body>
	<!-- Table Header -->
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i>調入作業 執行調入
		</div>
		<div class="no-move"></div>
	</div>
	
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn" >
						<span>
							<i class="fa fa-save"></i>
						</span> 
						執行調入
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="closeBtn" >
						<span>
							<i class="fa fa-times"></i>
						</span> 
						結案
					</button>
				</div>
			</ol>
		</div>
	</div>
	
	<div class="row" id="sdf">
		<div class="box-content">
			<form role="form" class="form-horizontal bootstrap-validator-form"
				novalidate="novalidate" id="searchForm">
				<div class="form-group">
					<table style="width: 95%">
						<tr>
							<td class="col-sm-2 control-label"><label>調撥單號 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.tr_no}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>調撥進度：</label></td>
							<td class="col-sm-3"><label>${invTrDto.statusValue}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>調出倉庫 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.outWhName}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>調入倉庫：</label></td>
							<td class="col-sm-3"><label>${invTrDto.inWhName}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>需求單位 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.needDeptName}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>需求日期：</label></td>
							<td class="col-sm-3"><label>${invTrDto.needDateToString}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>申請人單位 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.applyDeptName}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>申請人：</label></td>
							<td class="col-sm-3"><label>${invTrDto.appUserName}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>備註 :</label></td>
							<td class="col-sm-3" colspan="4"><textarea id="memo"
									name="LOC_DESC" style="resize: none; width: 100%" rows="3" disabled>${invTrDto.memo}</textarea>
							</td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2"></td>
							<td class="col-sm-3"></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
					</table>
				</div>
			</form>
		</div>

		<div id="inv009MDtlResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv009MJQgrid">
				<table id="inv009MDtlGrid"></table>
				<div id="inv009MDtlPager"></div>
			</div>
		</div>

		<div class="clearfix">&nbsp;</div>
		<div class="clearfix">&nbsp;</div>
		
		<div id="inv009MDtlResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv009MJQgrid">
				<table id="inv009MHistoryGrid"></table>
				<div id="inv009MHistoryPager"></div>
			</div>
		</div>
	</div>
	
</body>
</html>