<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<%@ include file="/common/commonpage.jsp"%>
<title>universal qa page</title>
</head>
<body>
	<%@ include file="/common/universal_qa_body.jsp"%>
	<script>
		$(document).ready(function() {
			$("#question").keydown(function(e) {
				if (e.which == 13) {
					submitQuestion();
				}
			});
			$("#bSubmit").click(function() {
				submitQuestion();
			});
		})
		
		function submitQuestion(){
			var question = $("#question").val();
			var schemaname = $("#schemaname").val();
			$.ajax({
				url : '{ctx}/answer!answer.action',
				type : 'post',
				data : {
					schemaName : schemaname,
					question : question
				},
				dataType : 'json'
			}).done(function(data) {
				
				$("#rst").append(questionString(question));
				$("#rst").append(rstString(data.data));
				$("#question").val("");
				
			});
		}
		function rstString(faqEntry){
			return'<div id="answer_div" class="alert alert-success">' + faqEntry.question + "<br>" +faqEntry.answer + '</div>';
		}
		function questionString(str){
			return'<div id="question_div" class="alert alert-warning">' + str + '</div>';
		}
	</script>
</body>
</html>