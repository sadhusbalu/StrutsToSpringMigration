<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<script type ="text/javascript">


function doAjaxPost() {
    // get the form values
    alert("ajax post")
    var username = $('#un').val();
    alert("username "+username);
    $.ajax({
        type: "POST",
        url: "executeTest.action",
        data: "username=" + username,
        success: function(response){
            // we have the response
            $('#logdisplay').html(response);
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}

</script>
<html>
<head>
<title>Security Properties</title>
</head>
<body>
	<s:actionerror />
	
	
	<!--<s:form id="sf" action="executeTest" method="post">-->
		<s:label style="font-size:10px" name="testName"/>
		<s:hidden id="un" name="username"/>
		<input type="button" value="Run" onclick="doAjaxPost()"/>
		<!--<s:submit value="Run" align="center"/>-->
		<div id="logdisplay">
		<!--<s:textarea id="display" name="logMessage" style="width:400;height:400;font-size::10px"></s:textarea>-->
		</div>
		<s:textarea id="display" name="logMessage" style="width:400;height:400;font-size::10px"></s:textarea>
		
		
	<!--</s:form>-->
</body>
</html>