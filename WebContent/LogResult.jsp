<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<script type ="text/javascript">
function downloadReport(id)
{
 
	document.getElementById('dn').value=document.getElementById(id).value;
	//alert('3');
	var form= document.getElementById('displayForm');
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
<s:form id="displayForm" action="downloadReportByName" method="post" enctype="multipart/form-data">
<table><tr><td><s:textarea name="logMessage" style="width:400;height:400;font-size:10px"></s:textarea></td></tr></table>
<s:hidden id="un" name="username"></s:hidden>
<s:hidden id="xml" name="xmlReportName"></s:hidden>
<s:hidden id="csv" name="csvReportName"></s:hidden>
<s:hidden id="txt" name="quickReportName"></s:hidden>
<s:hidden id="dn" name="downloadReportName"/>
<s:a  style="font-size:10px" href="javascript:downloadReport('txt')">Quick txt Report</s:a><s:label style="font-size:10px" value="Save as .txt"></s:label></br>
<s:a  style="font-size:10px" href="javascript:downloadReport('xml')">XML</s:a><s:label style="font-size:10px" value="Save as .xml"></s:label></br>
<s:a  style="font-size:10px" href="javascript:downloadReport('csv')">CSV</s:a><s:label style="font-size:10px" value="Save as .csv"></s:label>

</s:form>
</body>