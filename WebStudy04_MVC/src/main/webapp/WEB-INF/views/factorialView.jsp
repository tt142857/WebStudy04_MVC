<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String expression = (String) request.getAttribute("expression");
%>
<h4> 연산 결과 : <%=expression %></h4>
</body>
</html>