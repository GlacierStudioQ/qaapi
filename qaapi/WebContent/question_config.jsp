 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/commonpage.jsp"%>
<title>question config</title>
</head>
<body>

<input id="schemaName" type="hidden" value="${sessionScope.schemaName}"/>
    <div class="input-group">
       <span class="input-group-addon" id="basic-addon2">当前页：</span>

      <input type="text" class="form-control" value="1" placeholder="输入页码" id="nowpage" />
      <span class="input-group-btn">
        <button id="jumpto" class="btn btn-default" type="button">跳转</button>
      </span>
       <span class="input-group-addon" id="basic-addon2">数据总条数：<span id="datacount"></span></span>
        <span class="input-group-addon" id="basic-addon2">总页数：<span id="pagecount"></span></span>
      
    </div>
  
    <div class="input-group">
       <span class="input-group-addon" id="basic-addon2">每页显示条数</span>

      <input type="text" class="form-control" value="10" placeholder="输入每页条数" id="pagesize" />
      <span class="input-group-btn">
        <button id="pageup" class="btn btn-default" type="button">上一页</button>
        <button id="pagedown" class="btn btn-default" type="button">下一页</button>
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
  </div>
  <div class="input-group" >
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
  </div>
  <div class="input-group" >
      <span class="input-group-btn">
		<button id="saveQuestionConfirm" class="btn btn-default" type="button">新增</button>
		</span>
  </div>
	</div>
	
	<div>
		<table class="table">
			<thead>
				<tr>
					<td>id</td><td>问题</td><td>回答</td><td>操作</td>
				</tr>
			</thead>
			<tbody id="faqentries">
				
			</tbody>
		</table>
	</div>

	<script>
		$(document).ready(function() {
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
				url : '${ctx}/question-config!findByPage.action',
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
				 
				 var tbody = $("#faqentries");
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
						 $("#updateId").val(parentsTr.children(".id").html());
						 $("#updateQuestion").val(parentsTr.children(".question").html());
						 $("#updateAnswer").val(parentsTr.children(".answer").html());
						 $("#confirmUpdateForm").show();
					 });
					 
				 }
			});
			
		}
		function entryString(entry){
			return "<tr id='"+ entry.id +"'><td class='id'>" + entry.id + "</td><td class='question'>" + entry.question + "</td><td class='answer'>" + entry.answer + "</td>" +
						"<td>"+
						"<button class='updatebtn btn btn-xs btn-warning'>修改</button>"+
						"<button class='deletebtn btn btn-xs btn-danger'>删除</button>"+
						"</td></tr>";
		}
	</script>
</body>
</html>