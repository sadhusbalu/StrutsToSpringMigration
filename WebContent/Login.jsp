<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Service Validator</title>
</head>

<body>
<h2 align="center">Welcome to Service Validator</h2>
<div align="center"><s:actionerror/></div>
<s:form action="login.action" method="post" >
	<s:set var="theme" value="'simple'" scope="page"/>
	<table align="center" border="2"><tr>
	<td><s:textfield name="username" key="label.username" size="20"/></td>
	<!--<s:password name="password" key="label.password" size="20" />-->
	<td><s:submit method="execute" key="label.login" align="center" /></td>
	</tr></table>
</s:form>
</body>
</html>