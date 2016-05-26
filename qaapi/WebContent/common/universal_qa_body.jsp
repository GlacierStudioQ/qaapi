<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/common/bootstrap-3.0.3-dist/css/bootstrap.min.css">
<script src="${ctx}/common/jquery.min.js"></script>
<script src="${ctx}/common/bootstrap-3.0.3-dist/js/bootstrap.min.js"></script>
</head>
<style>
#rst {
	height: auto;
	overflow: auto;
}

#question_div {
	width: 70%;
	float: right;
}

#answer_div {
	width: 70%;
	float: left;
}
</style>
<body>
	
	<div id="rst" class="alert alert-info"></div>
	<div id="questions_div" class="alert alert-danger">

		<div class="input-group">
			<input type="hidden" id="schemaname" value="${sessionScope.schemaName}" />
			 <input type="text" class="form-control" id="question" placeholder="输入您的问题">
			<span class="input-group-btn">
				<button id="bSubmit" class="btn btn-default" type="button">发送</button>
			</span>
		</div>

	</div>

</body>
</html>