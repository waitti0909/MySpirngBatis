<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>庫存狀態異動作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>庫存狀態異動作業 - 明細</span>
			</div>
			<div class="box-icons">
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="ui-draggable ui-droppable">
					<div class="box-content form-horizontal">
						<div class="form-group">
							<div class="row">
								<label class="col-sm-2 control-label">
								異動單號：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.inv_tran_no}"
										class="form-control" disabled="disabled" />
								</div>
								<label class="col-sm-2 control-label">
								狀態：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.inv_tran_audit_status_dscr}"
										class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								倉庫：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.wh_name}"
										class="form-control" disabled="disabled" />
								</div>
								<label class="col-sm-2 control-label">
								料號：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.mat_no}"
										class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								異動前狀態：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.old_mat_status_dscr}"
										class="form-control" disabled="disabled" />
								</div>
								<label class="col-sm-2 control-label">
								異動後狀態：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.new_mat_status_dscr}"
											class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								貼標號碼：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.tag_no}"
											class="form-control" disabled="disabled" />
								</div>
								<label class="col-sm-2 control-label">
								廠商序號：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.ven_sn}"
											class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								異動數量：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.tran_qty}"
											class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								異動原因：</label>
								<div class="col-sm-2">
									<input type="text" value="${element.tran_reason_dscr}"
											class="form-control" disabled="disabled" />
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								備註：</label>
								<div class="col-sm-9">
									<textarea id="memo_L" class="form-control" rows="3" disabled="disabled">${element.memo}</textarea>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-2 control-label">
								簽核意見：</label>
								<div class="col-sm-9">
									<c:import url="/commom/signHistory">
										<c:param name="processType" value="InventoryStatusChange"></c:param>
										<c:param name="applyNo" value="${element.inv_tran_no}"></c:param>
									</c:import>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>