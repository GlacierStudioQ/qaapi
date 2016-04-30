<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/commonpage.jsp"%>
<script src="common/jquery.min.js"></script>

<title>main page</title>
</head>
<body>

	<!-- 查询 -->
	<input type="text" id="question">
	<button id="bSubmit">submit</button>
	<div id="rststat"></div>
	<div id="rst"></div>
	<script>
		$(document).ready(function() {
			$("#bSubmit").click(function() {
				var question = $("#question").val();
				$.ajax({
					url : '{ctx}/answer!answer.action',
					type : 'post',
					data : {
						schemaName : "qaapi",
						question : question
					},
					dataType : 'json'
				}).done(function(data) {

					$("#rststat").html(data.msg);
					if(data.status == 200){
						var qaEntry = data.data[0];
						$("#rst").html(qaEntry.answer);
					}

				});
			});
		})
	</script>
</body>
</html>