 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/commonpage.jsp"%>
<title>feedback config</title>
</head>
<body>

	<ul class="nav nav-pills" id="schemasSelect">
	</ul>
	
	<!-- 要配置的schema -->
	<input id="schemaName" type="hidden" />
	
    <div class="input-group">
       <span class="input-group-addon" id="basic-addon2">当前页：</span>

      <input type="text" class="form-control" value="1" placeholder="页码" id="nowpage" />
      <span class="input-group-btn">
        <button id="jumpto" class="btn btn-default" type="button">跳转</button>
      </span>
        <span class="input-group-addon" id="basic-addon2">总页数：<span id="pagecount"></span></span>
       <span class="input-group-addon" id="basic-addon2">数据总条数：<span id="datacount"></span></span>
      
    </div>
  
    <div class="input-group">
       <span class="input-group-addon" id="basic-addon2">每页显示条数</span>

      <input type="text" class="form-control" value="10" placeholder="每页条数" id="pagesize" />
      <span class="input-group-btn">
        <button id="pageup" class="btn btn-default" type="button">&nbsp<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"/>上一页</button>
        <button id="pagedown" class="btn btn-default" type="button">&nbsp下一页<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"/></button>
      </span>
    </div>
    
  <!-- 应该是浮动层，用于修改问答的编辑框 -->
  <div id="confirmUpdateForm">
<input id="updateId" type="hidden"/>
  <div class="input-group" >
 <span class="input-group-addon" id="basic-addon2">输入问题</span>
		<input type="text" id="updateQuestion" class="form-control"/>
  </div>
  <div class="input-group" >
 <span class="input-group-addon" id="basic-addon2">输入回答</span>
		<input type="text" id="updateAnswer" class="form-control"/>
      <span class="input-group-btn">
		<button id="updateConfirm" class="btn btn-default" type="button">修改</button>
      </span>
      <span class="input-group-btn">
		<button id="updateCancel" class="btn btn-default" type="button">取消</button>
		</span>
  </div>
	</div>
	
	<div id="saveForm">
<input id="updateId" type="hidden"/>
  <div class="input-group" >
 <span class="input-group-addon" id="basic-addon2">输入问题</span>
		<input type="text" id="saveQuestion" class="form-control"/>
  </div>
  <div class="input-group" >
 <span class="input-group-addon" id="basic-addon2">输入回答</span>
		<input type="text" id="saveAnswer" class="form-control"/>
      <span class="input-group-btn">
		<button id="saveQuestionConfirm" class="btn btn-default" type="button">新增</button>
		</span>
  </div>
	</div>
	
	<div>
		<table class="table">
			<thead>
				<tr>
					<td>id</td>
					<td>用户提问</td>
					<td>系统回答</td>
					<td>标准问题</td>
					<td>评论</td>
					<td>评论时间</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="feedbackentries">
				
			</tbody>
		</table>
	</div>

	<script>
		$(document).ready(function() {
			
			$.ajax({
				url : '${ctx}/unlimited!avaliableSchemas.action',
				async: false,
				type : 'post',
				dataType : 'json'
			}).done(function(data) {
				if(data.status == 200){
					var schemas = data.data;
					for(schema in schemas){
						if(schema == 0){
							$("#schemaName").val(schemas[schema].name);
							$("#schemasSelect").append(schemaString(schemas[schema], true));
						}else{
							$("#schemasSelect").append(schemaString(schemas[schema], false));
						}
					}
				}else{
					alert(data.msg);
				}
			});
			
			$("#confirmUpdateForm").hide();
			query(0);
			
			$("#pageup").click(function(){
				query(-1);
			});
			
			$("#pagedown").click(function(){
				query(1);
			});
			
			$("#jumpto").click(function(){
				query(0);
			});
			// 更新
			$("#updateConfirm").click(function (){
				var entry = {
					id : $("#updateId").val(),
					question : $("#updateQuestion").val(),
					answer : $("#updateAnswer").val()
				};
				updateEntry(entry);
			});
			// 更新取消
			$("#updateCancel").click(function (){
				$("#confirmUpdateForm").hide();
			});
			// 新增
			$("#saveQuestionConfirm").click(function (){
				var entry = {
					question : $("#saveQuestion").val(),
					answer : $("#saveAnswer").val()
				};
				saveEntry(entry);
			});
			
		})
		
		function saveEntry(entry){
			$.ajax({
				url : '${ctx}/question-config!save.action',
				type : 'post',
				data : {
					'entry.question' : entry.question,
					'entry.answer' : entry.answer,
						
					schemaName : $("#schemaName").val()
				},
				dataType : 'json'
			}).done(function(data) {
				if(data.status == 200){
					alert(data.msg);
					query(0);// 重新进行一次查询
					// 清空表单
					$("#saveQuestion").val("");
					$("#saveAnswer").val("");
				}else{
					alert(data.msg);
				}
			});
		}
		
		function updateEntry(entry){
			$.ajax({
				url : '${ctx}/question-config!update.action',
				type : 'post',
				data : {
					'entry.id' : entry.id,
					'entry.question' : entry.question,
					'entry.answer' : entry.answer,
						
					schemaName : $("#schemaName").val()
				},
				dataType : 'json'
			}).done(function(data) {
				if(data.status == 200){
					alert(data.msg);
					query(0);// 重新进行一次查询
					$("#confirmUpdateForm").hide();
				}else{
					alert(data.msg);
				}
			});
		}
		
		function deleteEntry(parentsTr){
			$.ajax({
				url : '${ctx}/question-config!delete.action',
				type : 'post',
				data : {
					'entry.id' : parentsTr.attr("id"),
						
					schemaName : $("#schemaName").val()
				},
				dataType : 'json'
			}).done(function(data) {
				if(data.status == 200){
					parentsTr.remove;
					alert(data.msg);
					query(0);// 重新进行一次查询
				}else{
					alert(data.msg);
				}
			});
		}
			
		function query(pageChange){
			var nowPage = parseInt($("#nowpage").val());
			var pageSize = $("#pagesize").val();
			
			nowPage += pageChange;
			
			$.ajax({
				url : '${ctx}/feedback!findByPage.action',
				type : 'post',
				data : {
					'cutPage.nowPage' : nowPage,
					'cutPage.pageSize' : pageSize,
						
					schemaName : $("#schemaName").val()
				},
				dataType : 'json'
			}).done(function(data) {
				var entries = data.data;
				var cutPage = data.other;
				
				 $("#nowpage").val(cutPage.nowPage);
				 $("#pagesize").val(cutPage.pageSize);
				 
				 $("#datacount").html(cutPage.dataCount);
				 $("#pagecount").html(cutPage.pageCount);
				 
				 var tbody = $("#feedbackentries");
				 tbody.empty();
				 if(entries.length != 0){
					 
					 for(entry in entries){
						 tbody.append(entryString(entries[entry]));
					 }
					 
					 tbody.on("click", ".deletebtn", function(){
						 deleteEntry($(this).parents("tr"));
					 });
					 tbody.on("click", ".updatebtn", function(){
						 var parentsTr = $(this).parents("tr");
						 $("#updateId").val(parentsTr.children(".faqanswer").attr("id"));
						 $("#updateQuestion").val(parentsTr.children(".faqquestion").html());
						 $("#updateAnswer").val(parentsTr.children(".faqanswer").html());
						 $("#confirmUpdateForm").show();
					 });
					 
				 }
			});
			
		}
		function entryString(entry){
			return '<tr id="'+ entry.id +'" />\
							<td class="">'+ entry.id +'</td>\
							<td class="">'+ entry.userQuestion +'</td>\
							<td id="'+ entry.faqEntry.id +'" class="faqanswer">'+ entry.faqEntry.answer +'</td>\
							<td class="faqquestion">'+ entry.faqEntry.question +'</td>\
							<td class="">'+ entry.comment +'</td>\
							<td class="">'+ (new Date(entry.createTime.time)).toLocaleString() +'</td>\
							<td>\
								<button class="updatebtn btn btn-xs btn-warning">修改</button>\
							</td>\
						</tr>';	
		}
		function schemaString(schema, isActive){
			var activeClass = "";
			if(isActive){
				activeClass = 'class="active"';
			}
			return '<li role="presentation" ' + activeClass + ' ><a id="' + schema.name + '">' + schema.nickname + '</a></li>';
		}
		
	</script>
</body>
</html>