 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/common/commonpage.jsp"%>
<script src="common/jquery.min.js"></script>
<title>question config</title>
</head>
<body>


	当前页：<input id="nowpage" value="1"/>
	<button id="jumpto">转到</button>
	每页显示条数：<input id="pagesize"/>
	<button id="pageup">上一页</button>
	<button id="pagedown">下一页</button>
	
	数据总条数：<span id="datacount"></span>
	总页数：<span id="pagecount"></span>
	
	<!-- 应该是浮动层，用于修改问答的编辑框 -->
	<div id="confirmUpdateForm">
		<input id="updateId" type="hidden"/>
		<input id="updateQuestion"/>
		<input id="updateAnswer"/>
		<button id="updateConfirm">确认</button>
	</div>
	
	<div>
		<table>
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
			
			$("#updateConfirm").click(function (){
				var entry = {
					id : $("#updateId").val(),
					question : $("#updateQuestion").val(),
					answer : $("#updateAnswer").val()
				};
				updateEntry(entry);
			});
			
		})
		
		function updateEntry(entry){
			$.ajax({
				url : '${ctx}/question-config!update.action',
				type : 'post',
				data : {
					'entry.id' : entry.id,
					'entry.question' : entry.question,
					'entry.answer' : entry.answer,
						
					domainName : "localhost",
					schemaName : "qaapi"
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
						
					domainName : "localhost",
					schemaName : "qaapi"
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
			
			//var dataCount = $("#datacount").html();
			//var pageCount = $("#pagecount").html();
			
			nowPage += pageChange;
			
			$.ajax({
				url : '${ctx}/question-config!findByPage.action',
				type : 'post',
				data : {
					'cutPage.nowPage' : nowPage,
					'cutPage.pageSize' : pageSize,
				//	'cutPage.dataCount' : dataCount,
				//	'cutPage.pageCount' : pageCount,
						
					domainName : "localhost",
					schemaName : "qaapi"
				},
				dataType : 'json'
			}).done(function(data) {
				var entries = data.data;
				var cutPage = data.other;
				
				 $("#nowpage").val(cutPage.nowPage);
				 $("#pagesize").val(cutPage.pageSize);
				 
				 $("#datacount").html(cutPage.dataCount);
				 $("#pagecount").html(cutPage.pageCount);
				 
				 if(entries.length != 0){
					 var tbody = $("#faqentries");
					 tbody.empty();
					 
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
						"<button class='updatebtn'>修改</button>"+
						"<button class='deletebtn'>删除</button>"+
						"</td></tr>";
		}
	</script>
</body>
</html>