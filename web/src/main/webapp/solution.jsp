<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.List, java.util.ArrayList"%>
<%@page import="dbManager.DBManager"%>
<%@page import="javabeans.Analyzer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	ArrayList<Analyzer> analyzerList = DBManager.dbUtil
			.getAnalyzerList();
%>

<html>
<head>
<link href="css/solution.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Overall Report</title>
</head>
<body>
	<h1 class="h_1">错误解决方案</h1>
	<hr class="hr4">
	<div id="container">
		<div id="header">
			<div id="filename">规则名称</div>
			<div id="description">出现场景</div>
			<div id="solution">解决方案</div>
		</div>
		<%
			int count = 0;
			for (Analyzer analyzer : analyzerList) {
				count++;
		%>

		<div id="content">
			<div id="file">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%=analyzer.getErrorId()%>:
				<%=analyzer.getName()%>
			</div>
			<div id="description_content">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%=analyzer.getDescription()%>
			</div>
			<div id="solution_content">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%=analyzer.getSolution()%>
			</div>
		</div>

		<%
			}
		%>

	</div>

	<div id="footer">
		规则数:
		<%=count%></div>

</body>
</html>

