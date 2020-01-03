<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type ="text/javascript">

function reloadSecurityProperties(){
			
	var form= document.getElementById('sf');
	
	form.action="reloadSecurityProperties.action";
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
	
	
	<s:form id="sf" action="saveSecurityProperties" method="post" enctype="multipart/form-data">
		<s:label style="font-size:10px" name="testName"/>
		<s:hidden name="username"/>
		<s:hidden name="testName"/>
		<s:hidden name="targetPath"/>
		<s:set name="theme" value="'simple'" scope="page" var=""/>
		<table>
				<TR style="font-size:10px"><TD>Applicable for HTTP Basic Authentication and certificate based 2 way Authentication</TD></TR>
				
		</table></br></br>
		
		<table id="dt" border="1" style="font-size:10px">
				<TR><TD colspan="2"><s:textarea name="confContent" style="font-size:10px;width:200px;height:100px"/></TD></TR>
		</table></br></br>
		<table>
				<TR style="font-size:10px"><TD>Upload client Certificate(if any)</TD><TD><input type="file" name="certFile" /></TD></TR>
				<!-- <TR style="font-size:10px"><TD colspan="2">n.b.:Server(Service) SSL Certificate should be imported in JVM(used by this UI) trust store</TD></TR>
				<TR style="font-size:10px"><TD colspan="2">Use command: keytool -import -noprompt -trustcacerts -alias certicate_alias -file certificate_file -keystore "JAVA_HOME\jre\lib\security\cacerts" -storepass password</TD></TR> -->
		</table>
		
		<table>
			<tr style="font-size:10px">
			
				 <td align="center">
				 	<s:submit value="save/upload" align="center" style="font-size:10px"/>
				</td>
				<td>
					<input type="button" value="Refresh" onclick="reloadSecurityProperties()" style="font-size:10px"/>
				</td>
				<td>
					<input type="button" value="Close" onclick="javascript:self.close()" style="font-size:10px"/>
				</td>
			</tr>
		</table>
		
	</s:form>
</body>
</html>