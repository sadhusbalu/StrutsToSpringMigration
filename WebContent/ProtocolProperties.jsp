<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type ="text/javascript">

function reloadProperties(){
	
	
	
	var form= document.getElementById('pf');
	
	form.action="reloadProperties.action";
	form.submit();
	return false;
}

</script>
<html>
<head>
<title>Connection Properties</title>
</head>
<body>
	<s:actionerror />
	
	
	<s:form id="pf" action="saveProtocol">
		<s:label style="font-size:10px" name="testName"/>
		<s:hidden name="type"/>
		<s:hidden name="username"/>
		<s:hidden name="protocol"/>
		<s:hidden name="testName"/>
		<s:hidden name="targetPath"/>
		<s:set name="theme" value="'simple'" scope="page" var=""/>
		<table id="dt" width="350px" border="1" style="font-size:10px">
		<!--<TR><TD>Key</TD><TD>Value</TD></TR>
		<s:iterator status="cnt" value="protocolProperties">
			<TR><TD><s:textfield name="key" style="font-size:10px" readonly="true"/></TD><TD><s:textfield name="value" style="font-size:10px" readonly="true"/></TD></TR>
			
		</s:iterator>-->
			<TR><TD><s:textarea name="confContent" style="font-size:10px;width:500Px;height:350px"/></TD></TR>
		</table>
		
		<table>
			<tr>
			
				 <td align="center">
				 	<s:submit value="save" align="center"/>
				</td>
				<td>
					<input type="button" value="Reload Properties" onclick="reloadProperties()"/>
				</td>
				<td>
					<input type="button" value="Close" onclick="javascript:self.close()"/>
				</td>
			</tr>
		</table>
		
	</s:form>
</body>
</html>