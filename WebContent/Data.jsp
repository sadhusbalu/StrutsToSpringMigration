<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type ="text/javascript">



function reLoad()
{
 
 //alert(type)
 //alert(testName)
	var form= document.getElementById('df');
	
	form.action="loadData.action?rand="+Math.random();;
	form.submit();
	return false;


  
 
 }
 
function download()
{
 
 //alert(type)
 //alert(testName)
	var form= document.getElementById('df');
	
	form.action="downloadData.action?rand="+Math.random();;
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
	
	
	<s:form id="df" action="saveData" method="post" enctype="multipart/form-data">
		<s:label style="font-size:10px" name="testName"/>
		<s:hidden name="username"/>
		<s:hidden name="testName"/>
		<s:hidden name="uploadFilePath"/>
		<s:hidden name="uploadDirPath"/>
		<s:set name="theme" value="'simple'" scope="page" var=""/>
		<table>
				<TR style="font-size:10px"><TD><s:select list="displayList" name="type" style="font-size:10px" onchange="reLoad()"/></TD></TR>
				
		</table></br>		
		<table id="dt" border="1" style="font-size:10px">
				<TR><TD colspan="2"><s:textarea name="content" style="font-size:10px;width:400px;height:350px"/></TD></TR>
		</table></br>
		<s:if test="%{type!='Actual'}">
			<table>
					<TR style="font-size:10px"><TD>Upload File</TD><TD><input type="file" name="uploadFile" /></TD></TR>
			</table>
		</s:if>
		<table>
				<TR style="font-size:10px"><TD>Store content</TD><TD><s:textfield name="fileList" style="font-size:10px;width:300px" readonly="true"/></TD></TR>
		</table>
		
		<table>
			<tr style="font-size:10px">
				<s:if test="%{type!='Actual'}">
				 <td align="center">
				 	<s:submit value="save/upload" align="center" style="font-size:10px"/>
				</td>
				</s:if>
				<td>
					<input type="button" value="downLoad" onclick="download()" style="font-size:10px"/>
				</td>
				<td>
					<input type="button" value="Close" onclick="javascript:self.close()" style="font-size:10px"/>
				</td>
			</tr>
		</table>
		
	</s:form>
</body>
</html>