<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<%@ include file="/common/commonpage.jsp"%>
<title>qaapi overview</title>
</head>
<body>
	<div class="input-group">
		<span class="input-group-addon">请输入域名</span> 
		<input type="text" class="form-control" placeholder="域名" id="domainName" value="" /> 
		<span class="input-group-btn">
			<button id="confirm" class="btn btn-default" type="button">确认</button>
		</span>
	</div>
	<script>
	$(document).ready(function() {
		$("#confirm").click(function(){
			confirm();
		});
	});
	function confirm(){
		$.ajax({
			url : '${ctx}/admin-config!adminQuestion.action',
			type : 'post',
			data : {
				domainName : $("#domainName").val()
			},
			dataType : 'json'
		}).done(function(data) {
			if(data.status== 200){
				location.href = "${ctx}/question_config.jsp";
			}else{
				alert(data.msg);
				$("#domainName").val("");
			}
		});
	}
	</script>
</body>
</html>