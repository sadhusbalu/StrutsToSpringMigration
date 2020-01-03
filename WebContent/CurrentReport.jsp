<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type ="text/javascript">
 
function addRow(tableID) {
 var table = document.getElementById(tableID);
 var rowCount = table.rows.length;
 var row = table.insertRow(rowCount); //to insert blank row
 
 //var cell1 = row.insertCell(0);   //to insert first column
 //var snoCol = document.createElement("input");
 //snoCol.type = "text";
 //snoCol.name="ticket.passengerDetail["+(rowCount-1)+"].sno";
 //snoCol.value=rowCount;
 //cell1.appendChild(snoCol);
 var cell9 = row.insertCell(0);  //to insert 4th column
 var rowRemoveCol = document.createElement("a");
 var text = document.createTextNode("delete");
 rowRemoveCol.appendChild(text);
 rowRemoveCol.setAttribute("href","javascript:removeRow('dt','"+(rowCount-1)+"')");
 rowRemoveCol.name="reqlink[]";
 rowRemoveCol.style.fontSize="10px";
 cell9.appendChild(rowRemoveCol);
 //cell9.appendChild(rowRemoveCol);
  //var dlCol = document.createElement("input");
  //dlCol.type = "checkbox";
  //dlCol.name="checkLists["+(rowCount-1)+"]";
  //cell9.appendChild(dlCol);
 
 var cell2 = row.insertCell(1); //to insert second column
 var nameCol = document.createElement("input");
 nameCol.type = "text";
 nameCol.name="testCases["+(rowCount-1)+"].testCase";
 nameCol.style.fontSize="10px";
 cell2.appendChild(nameCol);
 
var cell3 = row.insertCell(2); // to insert 3rd column
 var descCol = document.createElement("textarea");
 //descCol.type = "textarea";
 descCol.name="testCases["+(rowCount-1)+"].description";
 descCol.style.fontSize="10px";
 cell3.appendChild(descCol);
 
 var cell4 = row.insertCell(3);  //to insert 4th column
 var patternCol = document.createElement("input");
 patternCol.type = "text";
 patternCol.name="testCases["+(rowCount-1)+"].pattern";
  //var optionSynch = document.createElement("Synch");
 //var optionAsynch = document.createElement("Asynch");
 //patternCol.addChild(optionSynch);
 //patternCol.addChild(optionAsynch);
 //patternCol.type = "text";
  patternCol.style.fontSize="10px";
 cell4.appendChild(patternCol);
 
 var cell5 = row.insertCell(4);  //to insert 4th column
 var spCol = document.createElement("input");
 spCol.type = "text";
 spCol.name="testCases["+(rowCount-1)+"].sourceProtocol";
 spCol.style.fontSize="10px";
 cell5.appendChild(spCol);
 
 var cell6 = row.insertCell(5);  //to insert 4th column
 var sfCol = document.createElement("input");
 sfCol.type = "text";
 sfCol.name="testCases["+(rowCount-1)+"].sourceFormat";
 sfCol.style.fontSize="10px";
 cell6.appendChild(sfCol);
 
 var cell7 = row.insertCell(6);  //to insert 4th column
 var tpCol = document.createElement("input");
 tpCol.type = "text";
 tpCol.name="testCases["+(rowCount-1)+"].targetProtocol";
 tpCol.style.fontSize="10px";
 cell7.appendChild(tpCol);
 
 var cell8 = row.insertCell(7);  //to insert 4th column
 var tfCol = document.createElement("input");
 tfCol.type = "text";
 tfCol.name="testCases["+(rowCount-1)+"].targetFormat";
 tfCol.style.fontSize="10px";
 cell8.appendChild(tfCol);
 
 
 return false;
 
}

function removeRow(tableID,rowNum){
	
	//var table = document.getElementById(tableID);
	//alert(table)
	//table.deleteRow(parseInt(rowNum)+1);
	var deleteIndex=document.getElementById('df');
	
	deleteIndex.value=rowNum
	var form= document.getElementById('tf');
	
	form.action="delete.action";
	form.submit();
	return false;
}

function addTestCase(tableID,rowNum){
	
	//var table = document.getElementById(tableID);
	//alert(table)
	//table.deleteRow(parseInt(rowNum)+1);
	
	var form= document.getElementById('tf');
	
	form.action="add.action";
	form.submit();
	return false;
}

function goSubmit(deletedTestName)
{
 
 document.testForm.action="delete";
 document.testForm.submit();
 alert("submitted")
 
 }
 
function deleteRec()
{
 
 form=document.forms[0];
 form.action="delete";
 form.submit();
 
 
 }
 
function addProtocolProperties(type,testName,protocol)
{
 
 //alert(type)
 //alert(testName)
 username=document.getElementById("un").value;
 //alert(username)
 if(protocol==null || protocol==""){
	 alert("Select protocol for "+type);
	 return false;
 }else if(protocol=="HTTP" || protocol=="HTTPGET" || protocol=="HTTPPOST"){
	 alert("No extra configuraiton required for HTTP");
	 return false;
 }
 var url="loadProtocolProperties.action?type="+type+"&testName="+testName+"&username="+username+"&protocol="+protocol+"&rand="+Math.random();
 //alert(url)
 window.open(url,"_blank","directories=no, status=no,width=600, height=500,top=100,left=250");
  
 
 }
 
function addSecurityProperties(testName)
{
 
 //alert(type)
 //alert(testName)
 username=document.getElementById("un").value;

 var url="loadSecurityProperties.action?testName="+testName+"&username="+username+"&rand="+Math.random();
 //alert(url)
 window.open(url,"_blank","directories=no, status=no,width=600, height=500,top=50,left=250");
  
 
 }
 
function uploadData(testName)
{
 
 //alert(type)
 //alert(testName)
 username=document.getElementById("un").value;

 var url="loadData.action?testName="+testName+"&username="+username+"&rand="+Math.random();
 //alert(url)
 window.open(url,"_blank","directories=no, status=no,width=600, height=600,top=50,left=250");
  
 
 }
function executeTest(){
	
	
	username=document.getElementById("un").value;

	 var url="executeDisplay.action?username="+username+"&rand="+Math.random();
	 //alert(url)
	 window.open(url,"_blank","directories=no, status=no,width=600, height=600,top=50,left=250");
}
function loadReports(){
	
	
	username=document.getElementById("un").value;

	 var url="loadReports.action?username="+username+"&rand="+Math.random();
	 //alert(url)
	 window.open(url,"_blank","directories=no, status=no,width=600, height=600,top=50,left=250");
}
function currentReport(){
	
	//var table = document.getElementById(tableID);
	//alert(table)
	//table.deleteRow(parseInt(rowNum)+1);
	
	var form= document.getElementById('tf');
	
	form.action="currentReport.action";
	form.submit();
	return false;
}

function showInfo(testCase,type,count){
	
	//alert("showInfo")
	//username=document.getElementById("un").value;

	 var url="showInfo.action?testName="+testCase+"&type="+type+"&count="+count+"&rand="+Math.random();
	//alert(url);
	 //alert(url)
	 window.open(url,"_blank","directories=no, status=no,width=600, height=600,top=50,left=250");
}
function downloadReport(id)
{
 
	document.getElementById('dn').value=document.getElementById(id).value;
	//alert('3');
	var form= document.getElementById('tf');
	//alert(form);
	form.action="downloadReportByName.action";
	//alert(form);
	form.submit();
	return false;

}
 
</script>
<html>
<head>
<title>Welcome</title>
</head>
<body>
	<h2 align="center">Test Report</h2>
	<s:actionerror />
	
	
	<s:form action="save" method="post" form="testForm" id="tf">
		
		<s:hidden id="un" name="username"/>
		
		<s:hidden id="xml" name="xmlReportName"/>
		<s:hidden id="csv" name="csvReportName"/>
		<s:hidden id="txt" name="quickReportName"/>
		<s:hidden id="dn" name="downloadReportName"/>
		<s:hidden name="added"/>
		<s:set var="theme" value="'simple'" scope="page" />
		<!--  <table id="dt" width="350px" border="1" style="font-size:10px">-->
		<table id="dt" border="1" style="font-size:10px">
		
		<TR><TD>Test Case</TD><TD>Pattern</TD><TD>Source Protocol</TD>
		<TD>Source Format</TD><TD>Target Protocol</TD><TD>Target Format</TD><TD>Ignore List</TD>
		<TD>Security</TD><TD>data</TD><TD>Status</TD></TR>
		<s:iterator status="cnt" value="testReports">
		<TR style="font-size:10px">
			<!-- <TD><s:checkbox name="checkLists[%{#cnt.count-1}]"/></TD> -->
			<TD><table><tr style="font-size:10px">
			<td><s:property value="testCase"  /></td>
			<td><input type="button" value=".." style="font-size:10px" onclick="showInfo('<s:property value="%{testCase}"/>','desc','<s:property value="%{#cnt.count-1}"/>')"/></td>
			</tr></table></TD>
			<TD style="font-size:10px"><s:property value="pattern" /></TD>
			<TD><table><tr>
			<td style="font-size:10px"><s:property value="sourceProtocol" /></td>
			<td style="font-size:10px" ><input type="button" value=".." onclick="addProtocolProperties('Source','<s:property value="%{testCase}"/>','<s:property value="%{sourceProtocol}"/>')"/></td>
			</tr></table></TD>
			<TD style="font-size:10px"><s:property value="sourceFormat" /></TD>
			<TD><table><tr>
			<td style="font-size:10px"><s:property value="targetProtocol" /></td>
			<td style="font-size:10px"><input type="button" value=".."  onclick="addProtocolProperties('Target','<s:property value="%{testCase}"/>','<s:property value="%{targetProtocol}"/>')"/></td>
			</tr></table></TD>
			<TD style="font-size:10px"><s:property value="targetFormat" /></TD>
			<TD style="font-size:10px"><s:property value="ignoreList" /></TD>
			<!-- <TD style="font-size:10px"><s:property value="testReports[%{#cnt.count-1}].endpoint" /></TD>
			<TD style="font-size:10px"><s:property value="testReports[%{#cnt.count-1}].legacyEndPont" /></TD>-->
			<td><input type="button" value="sec.." style="font-size:10px" onclick="addSecurityProperties('<s:property value="%{testCase}"/>')"/></td>
			<td><input type="button" value="data" style="font-size:10px" onclick="uploadData('<s:property value="%{testCase}"/>')"/></td>
			<TD><table><tr>
			<td style="font-size:10px"><s:property value="passed" /></td>
			<td><input type="button" value=".." style="font-size:10px" onclick="showInfo('<s:property value="%{testCase}"/>','reason','<s:property value="%{#cnt.count-1}"/>')"/></td>
			</tr></table></TD>
			<!-- <td><input type="button" value="Source" style="font-size:10px" onclick="addSourceProperties('Source','<s:property value="%{testCase}"/>')"/></td> -->
			<!--  <td><a href="javascript:goSubmit('<s:property value="testCases[%{#cnt.count-1}].testCase"/>')">delete</a></td>-->
			<!--  <td> <input type="button" value="delete data" onclick="removeRow('dt','<s:property value="%{#cnt.count-1}"/>')"/></td>-->
			<!--  <td><a href="#" onclick="removeRow('dt','<s:property value="%{#cnt.count-1}"/>')">delete</a></td>-->
			
			
		</TR> 
		
		</s:iterator>
				
		</table>
		
	
		
	
		
		
		</br></br>
		<s:a  style="font-size:10px" href="javascript:downloadReport('txt')">Quick txt Report</s:a><s:label style="font-size:10px" value="Save as .txt"></s:label></br>
		<s:a  style="font-size:10px" href="javascript:downloadReport('xml')">XML</s:a><s:label style="font-size:10px" value="Save as .xml"></s:label></br>
		<s:a  style="font-size:10px" href="javascript:downloadReport('csv')">CSV</s:a><s:label style="font-size:10px" value="Save as .csv"></s:label>
		
		
		
		
	</s:form>	
	
</body>
</html>