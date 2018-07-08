<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>
</head>
<body> 
<form action="${pageContext.request.contextPath }/queryItem.action" method="post">
查询条件：
<table width="100%" border=1>
<tr>
<td><input type="submit" value="查询"/></td>
</tr>
</table>
商品列表：
<table width="100%" border=1>
<tr>
	<td>项目编号</td>
	<td>项目名称</td>
	<td>建设单位</td>
	<td>时间</td>
	<td>操作</td>
</tr>
<c:forEach items="${itemList }" var="item" varStatus="s">
<tr>
	<td>
		<input type="checkbox" name="ids" value="${item.id}">
		<input type="hidden" name="itemList[${s.index }].id" value="${item.id }">
	</td>
	<td><input type="text" name="itemList[${s.index }].name" style="width:200px; height:18px" value="${item.itemName }"></input></td>
	<td><input type="text" name="itemList[${s.index }].price" value="${item.companyName }"></td>
	<td>
		<input type="text" name="itemList[${s.index }].createtime" value="<fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">	
	</td>
	<td><a href="${pageContext.request.contextPath }/itemEdit.action?id=${item.id}">项目详情</a></td>

</tr>
</c:forEach>

</table>
</form>
</body>

</html>