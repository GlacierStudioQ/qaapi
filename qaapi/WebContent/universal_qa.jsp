<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<%@ include file="/common/commonpage.jsp"%>
<title>universal qa page</title>
<style>

.feedbackForm{
	display: none;
}
.feedbackComment{
	width: 70%;
}

</style>
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
				url : '${ctx}/answer!answer.action',
				type : 'post',
				data : {
					schemaName : schemaname,
					question : question
				},
				dataType : 'json'
			}).done(function(data) {
				
				$("#rst").append(questionString(question));
				$("#rst").append(rstString(data));
				$("#question").val("");
				
			});
		}
		
		// 提交反馈
		function feedbackCommit(that){
			
			var faqId = $(that).siblings(".feedbackFaqid").val();
			var userQuestion = $(that).siblings(".feedbackUserQuestion").val();
			var comment = $(that).siblings(".feedbackComment").val();
			var schemaname = $("#schemaname").val();
			
			$.ajax({
				url : '{ctx}/feedback!save.action',
				type : 'post',
				data : {
					"feedback.faqId" : faqId,
					"feedback.userQuestion" : userQuestion,
					"feedback.comment" : comment,
					schemaName : schemaname
				},
				dataType : 'json'
			}).done(function(data) {
				alert(data.msg);
			});
			
			$(that).parents(".feedbackForm").hide();
		}
		// 有帮助
		function feedbackGood(that){
			$(that).parents(".feedbackDiv").hide();
		}
		// 无帮助
		function feedbackBad(that){
			$(that).parents(".feedbackButtons").siblings(".feedbackForm").show();
			$(that).parents(".feedbackButtons").hide();
		}
		
		function rstString(data){
			var faqEntry = data.data;
			return'<div id="answer_div" class="alert alert-success">' + faqEntry.question + '<br>' +faqEntry.answer + feedbackString(data) + '</div>';
		}
		function feedbackString(data){
			var faqEntry = data.data;
			var others = data.other;
			return '<div class="feedbackDiv">\
						<div class="feedbackButtons">\
							<button class="feedbackgood btn btn-sm btn-success" type="button" onclick="feedbackGood(this)">有帮助</button>\
							<button class="feedbackbad btn btn-sm btn-danger" type="button" onclick="feedbackBad(this)">无帮助</button>\
						</div>\
						<div class="feedbackForm">\
							<input type="hidden" class="feedbackFaqid" value="' + faqEntry.id + '" />\
							<input type="hidden" class="feedbackUserQuestion" value="' + others + '" />\
							<input type="text" class="feedbackComment form-control" placeholder="填写您的意见，可不填" />\
							<button class="feedbackbad btn btn-warning" type="button" onclick="feedbackCommit(this)" >提交</button>\
						</div>\
					</div>';
		}
		function questionString(str){
			return'<div id="question_div" class="alert alert-warning">' + str + '</div>';
		}
	</script>
</body>
</html>