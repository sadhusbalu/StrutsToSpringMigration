<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<script type ="text/javascript">


//function doAjaxPost() {
    // get the form values
    //alert("ajax post")
    //var username = $('#un').val();
    //alert("username "+username);
    //$.ajax({
        //type: "POST",
        //url: "executeTest.action",
        //data: "username=" + username,
        //success: function(response){
            // we have the response
            //$('#logdisplay').html(response);
        //},
        //error: function(e){
            //alert('Error: ' + e);
        //}
    //});
//}

</script>
<html>
<head>
<title>Security Properties</title>
</head>
<body>
	<s:actionerror />
	<img id="loadingImage" src="loading.jpg" style="display:none"/>
	
	<s:form action="executeTest" method="post">
		
		<s:hidden id="un" name="username"/>
		
		
		<br></br><br></br>
		<table>
				
				<TR style="font-size:10px"><TD colspan="2">the following commands can be used from command line</TD></TR>
				<TR style="font-size:10px"><TD colspan="2">java -DRootSource=<s:property value="%{profileRoot}"/> -jar PATH_TO_EXECUTABLE/IJunit.jar</TD></TR>
				<TR style="font-size:10px"><TD colspan="2">java -DRootSource=<s:property value="%{profileRoot}"/> -classpath LIB_DIR com.cts.integration.unitTest.executer.IJunitExecuter</TD></TR>
		</table>
		<sx:submit  targets="logdisplay" value="Run Online" align="center" showLoadingText="false" indicator="loading.jpg"/>
		
	</s:form>
	<sx:div id="logdisplay"></sx:div>
		
		
		
		
		
		
	
</body>
</html>