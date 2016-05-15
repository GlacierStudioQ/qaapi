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
	Q<input type="text" id="question" /><br>
	schema<input type="text" id="schemaname" value="qaapi" /><br>
	domian<input type="text" id="domainname" value="localhost" /><br>
	<button id="bSubmit">submit</button><br>
	<pre id="rst"></pre>
	
	<a href="question_config.jsp">question config</a>
	
	<script>
		$(document).ready(function() {
			$("#bSubmit").click(function() {
				var question = $("#question").val();
				var schemaname = $("#schemaname").val();
				var domainname = $("#domainname").val();
				$.ajax({
					url : '{ctx}/answer!answer.action',
					type : 'post',
					data : {
						domainName : domainname,
						schemaName : schemaname,
						question : question
					},
					dataType : 'json'
				}).done(function(data) {

					$("#rst").html(JSON.stringify(data, null, '\t'));

				});
			});
		})
	</script>
</body>
</html>