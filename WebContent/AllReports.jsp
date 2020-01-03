<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type ="text/javascript">
function downloadReport(fileName)
{
 	//alert(fileName);
	document.getElementById('dn').value=fileName;
	//alert("2");
	//alert('3');
	var form= document.getElementById('df');
	//alert(form);
	form.action="downloadReportByName.action";
	//alert(form);
	form.submit();
	return false;

  
 
 }

</script>
<html>
<head>
<title>Security Properties</title>
</head>
<body>
	<s:actionerror />
	
	
	<s:form id="df" action="downloadReportByName" method="post" enctype="multipart/form-data">
		<s:set name="theme" value="'simple'" scope="page" var=""/>
		<s:label style="font-size:10px" name="testName"/>
		<s:hidden name="username"/>
		<s:hidden id="dn" name="downloadReportName"/>
		
		<table>
		<s:iterator status="cnt" value="reportList">
		<tr style="font-size:10px"><td>
			<!--<s:label  style="font-size:10px" value="reportList[%{#cnt.count-1}]"/>-->
			<s:property/>
			</td>
			<td>
			<input type="button" value="..." style="font-size:10px" onclick="downloadReport('<s:property/>')"/>
		</td></tr>
			
		</s:iterator>
		</table>
		
	</s:form>
</body>
</html>