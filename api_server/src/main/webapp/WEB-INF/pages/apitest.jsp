<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
String defaultApiServer = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<c:set var="ctx" value="${defaultApiServer}"/>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>api测试工具</title>
</head>
<body>
<div>
	<h3>api测试工具</h3>
	<form id="testForm" method="post" action="<%=defaultApiServer %>/apitest/doApiTest">
		API &nbsp;&nbsp;&nbsp;KEY:
		<input type="text" id="apiKey" name="apiKey" value="android_client" size="50"/><br>
		API SECRET:
		<input type="text" id="apiSecret" name="apiSecret" value="5b2e1c483b4cf67c87399e1de4554cf9" size="50"/><br>
		API &nbsp;类目：
		<select id="catalog" name="catalog" style="width:385px">
			<option value=""></option>
			<c:forEach items="${functionCodeCatalogMap}" var="cata">
				<option value="${cata.key}">${cata.value}</option>
			</c:forEach>
		</select><br>
		API &nbsp;列表：
		<select id="functionCode" name="functionCode" style="width:385px">
			<option value=""></option>
		</select>
		&nbsp;&nbsp;&nbsp;<a href="http://192.168.5.100:8090/wiki/display/CCPLAYAPI/CCPLAY+API+Home" target="_blank"><u>API文档</u></a>&nbsp;&nbsp;&nbsp;<a href="#apiMsgDiv"><u>API消息代码</u></a>
		<br>
		API &nbsp;参数：&nbsp;<input type="button" value="增加一个参数" id="btnAddParam"/>
		<table style="margin-left:100px">
			<thead>
				<tr>
					<td width="50px" align="center">操作</td>
					<td width="150px" align="center">参数描述</td>
					<td width="150px" align="center">参数名称</td>
					<td width="150px" align="center">值</td>
				</tr>
			</thead>
			<tbody id="paramTable">
			</tbody>
		</table>
		<br>
		<input style="margin-left:300px;" type="button" value="提交测试" id="btnSubmit"/>
	</form>
</div>
<div>
	<textarea rows="40" cols="200" id="apiRsp"></textarea>
</div>
<div id="apiMsgDiv">
	<table border="1">
	<tr>
		<td align="center"><b>CODE</b></td>
		<td align="center"><b>消息</b></td>
	</tr>
	<c:forEach items="${apiMsgMap}" var="item">
		<tr>
			<td>${item.key}</td>
			<td>${item.value}</td>
		</tr>	
	</c:forEach>
	</table>
</div>
<script type="text/javascript" src="<%=defaultApiServer %>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=defaultApiServer %>/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=defaultApiServer %>/js/common.js"></script>
<script type="text/javascript">
$(function(){
	$("#catalog").change(function(){
		$("#paramTable").html("");
		$("#apiRsp").val("");
		$.ajax({
			url:"<%=defaultApiServer %>/apitest/getFunctionListByCatalog?catalog="+$(this).val(),
			success:function(event,param){
				var data=param.data;
				var options='<option value=""></option>';
				for(var index in data){
					options+='<option value="'+index+'">'+data[index]+'('+index+')'+'</option>';
				}
				$("#functionCode").html(options);
			}
		});
	});
	$("#functionCode").change(function(){
		$("#paramTable").html("");
		$("#apiRsp").val("");
		$.ajax({
			url:"<%=defaultApiServer %>/apitest/getParamsByFunctionCode?functionCode="+$(this).val(),
			success:function(event,param){
				var data=param.data;
				var options='';
				for(var index in data){
					options+='<tr>';
					options+='<td><input type="button" value="删除" onclick="javascript:$(this).parent().parent().remove();"/></td>';
					options+='<td><input type="text" name="paramDescription" value="'+data[index]+'"/></td>';
					options+='<td><input type="text" name="paramName" value="'+index+'"/></td>';
					options+='<td><input type="text" name="paramValue" value="" size="50"/></td>';
					options+='</tr>';
				}
				$("#paramTable").html(options);
			}
		});
	});
	var successCallback=function(event,param){
		$("#apiRsp").val(JSON.stringify(param.data, null, '\t'));
	};
	$("#testForm").validate({
		errorPlacement:function(error,element){
			var errorT = element.parent().next();
			error.appendTo(errorT);
		},
		rules:{
			apiKey:{
				required:true
			},
			apiSecret:{
				required:true
			},
			catalog:{
				required:true
			},
			functionCode:{
				required:true
			}
		},
		onkeyup: false,
		submitHandler:function(form){
			$("#apiRsp").val("");
			$(form).ajaxForm({success:successCallback});
		}
	});	
	$("#btnSubmit").click(function(){
		$("#testForm").submit();
	});
	$("#btnAddParam").click(function(){
		var html='';
		html+='<tr>';
		html+='<td><input type="button" value="删除" onclick="javascript:$(this).parent().parent().remove();"/></td>';
		html+='<td><input type="text" name="paramDescription" value=""/></td>';
		html+='<td><input type="text" name="paramName" value=""/></td>';
		html+='<td><input type="text" name="paramValue" value=""/></td>';
		html+='</tr>';
		$("#paramTable").append(html);
	});
});
</script>
</body>
</html>