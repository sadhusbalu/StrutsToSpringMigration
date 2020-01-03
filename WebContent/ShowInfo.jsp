<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type ="text/javascript">



</script>
<html>
<head>
<title>Security Properties</title>
</head>
<body>
	<s:actionerror />
	
	
	<s:form id="df" action="saveData" method="post" enctype="multipart/form-data">
		
		<s:set var="theme" value="'simple'" scope="page" />
		
		<table id="dt" border="1" style="font-size:10px">
				<TR><TD colspan="2"><s:textarea name="infoContent" style="font-size:10px;width:400px;height:350px"/></TD></TR>
		</table></br>
		
	</s:form>
</body>
</html>