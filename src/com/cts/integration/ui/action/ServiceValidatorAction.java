package com.cts.integration.ui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.junit.runner.JUnitCore;

import com.cts.integration.dto.ExcelColumnConstants;
import com.cts.integration.dto.IJunitConstants;
import com.cts.integration.dto.IJunitTestCase;
import com.cts.integration.dto.TestCaseDTO;
import com.cts.integration.dto.TestConfDTO;
import com.cts.integration.factory.TestCaseFactory;
import com.cts.integration.ui.constants.UIConstants;
import com.cts.integration.unitTest.executer.IJunitExecuter;
import com.cts.integration.util.DBUtil;
import com.cts.integration.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ServiceValidatorAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	
	private String password;
	private List<TestCaseDTO> testCases = new ArrayList<TestCaseDTO>();
	List<TestCaseDTO> testReports = new ArrayList<TestCaseDTO>();
	private String deletedTestCaseName;
	private List<Boolean> checkLists = new ArrayList<Boolean>();
	private List<String> protocolList = new ArrayList<String>(Arrays.asList("",IJunitConstants.PROTOCOL_DB, IJunitConstants.PROTOCOL_FILE, IJunitConstants.PROTOCOL_FILE_ADVANCED,
			IJunitConstants.PROTOCOL_FTP,IJunitConstants.PROTOCOL_HTTP,IJunitConstants.PROTOCOL_HTTP_GET
			,IJunitConstants.PROTOCOL_HTTP_POST,IJunitConstants.PROTOCOL_JMS,IJunitConstants.PROTOCOL_MQ,IJunitConstants.PROTOCOL_SFTP));
	private List<String> formatList = new ArrayList<String>(Arrays.asList("",IJunitConstants.FORMAT_XML,IJunitConstants.FORMAT_JSON, IJunitConstants.FORMAT_TXT,IJunitConstants.FORMAT_UNKNOWN));
	private List<String> patternList = new ArrayList<String>(Arrays.asList(IJunitConstants.PATTERN_SYNCH,IJunitConstants.PATTERN_ASYNCH));
	private List<String> activeList = new ArrayList<String>(Arrays.asList(IJunitConstants.VALUE_ACTIVE,IJunitConstants.VALUE_INACTIVE));
	private TestCaseDTO newTestCase = new TestCaseDTO();
	String newIgnoreField;
	private boolean added=false;
	List<String> ignoreFields = new ArrayList<String>();
	private String logMessage = new String();
	String profileRoot;
	String csvReportName;
	String xmlReportName;
	String quickReportName;
	String csvReportPath;
	String xmlReportPath;
	String quickReportPath;
	
	String downloadReportName;
	FileInputStream reportInputStream;
	List<String> reportList = new ArrayList<String>();
	private SessionMap<String,Object> sessionMap;  
	private String infoContent;
	//private List
	

	public String login() {
		File dir = new File(UIConstants.WEBROOT);
	    File[] files = dir.listFiles();
	    boolean foundUser = false;
	    System.setProperty("javax.net.ssl.trustStore","C:\\Program Files\\Java\\jre7\\lib\\security\\cacerts");
	    System.out.println("Trust store =>"+System.getProperty("javax.net.ssl.trustStore"));
	    try {
			for(File file:files){
				if(file.isDirectory() && file.getName().equals(this.username)){
					foundUser=true;
					String tesPlanFile=UIConstants.WEBROOT+File.separator+this.username+File.separator+IJunitConstants.DEFAULT_TEST_INPUT_EXCEL;
					File testPlan =new File(tesPlanFile);
					if(testPlan.exists()){
						testCases = ExcelUtil.readExcel(tesPlanFile);
						if(testCases.size()>0){
							
							for(int i=1;i<testCases.size();i++){
								List<String> ignoredList=testCases.get(i).getIgnoreList();
								StringBuffer sb= new StringBuffer();
								int count =0;
								for(String element:ignoredList){
									if(count==0){
										sb.append(element);
									}else{
										sb.append(",");
										sb.append(element);
									}
									count++;
								}
								ignoreFields.add(sb.toString());
							}
							
							//removing excel header
							testCases.remove(0);
						}
					}
					
					if(testCases.size()==0){
						//adding empty first row
						TestCaseDTO emptyTest = new TestCaseDTO();
						testCases.add(emptyTest);
					}
					
					System.out.println("No of test cases "+testCases.size());
					break;
				}
			}
			
			if (foundUser==true) {
				return "success";
			} else {
				addActionError(getText("error.login"));
				return "error";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}
	
	public String save() {
		System.out.println("List size "+testCases.size());
		 try {
			 //System.out.println("name :"+testCases.get(0).getTestCase());
			 writeXLSXFile();
			setAdded(false);
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}
	public String add() {
			    
	    try {
	    	 	setAdded(true);
	 	    	if(newTestCase.getTestCase() != null && newTestCase.getTestCase().trim().length()>0){
	 	    		testCases.add(newTestCase);
	 	    		ignoreFields.add(newIgnoreField);
	 	    		if(newIgnoreField!=null && newIgnoreField.trim().length()>0){
	 	    			String ignoreSplits[]= newIgnoreField.split(",");
	 	    			newTestCase.setIgnoreList(Arrays.asList(ignoreSplits));
	 	    		}
	 	    		newIgnoreField=new String();
	 	    		File testDir = new File(UIConstants.WEBROOT+File.separator+this.username+File.separator+newTestCase.getTestCase());
	 	    		System.out.println("directory exists "+testDir.exists());
	 	    		System.out.println("directory localtion "+testDir.getAbsolutePath());
	 	    		if(!testDir.exists()){
	 	    			testDir.mkdir();
	 	    		}
	 	    	}
	 	    	newTestCase = new TestCaseDTO();
	 	    	System.out.println(" List size "+testCases.size());
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}
	
	public String delete() {
		 try {
				///System.out.println("invoked delete for : "+checkLists );
				System.out.println("no of test cases : "+testCases.size() );
				System.out.println("deletedTestCaseName index : "+deletedTestCaseName );
				testCases.remove(Integer.parseInt(deletedTestCaseName));
				//System.out.println("checkiList size : "+checkLists.size() );
				/*List<TestCaseDTO> copyTestCases = new ArrayList<TestCaseDTO>();
				int i=0;
				for(Boolean check:checkLists){
					if(!check.booleanValue()){
						copyTestCases.add(testCases.get(i));
					}
					i++;
					
					
				}
				testCases=copyTestCases;
				checkLists.clear();*/
			   System.out.println("Returning success");
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}

	public void writeXLSXFile() throws Exception { 
		
		XSSFWorkbook wb=null;
		FileOutputStream fileOut=null;
		try{
				 
		 		String excelFileName = UIConstants.WEBROOT+File.separator+username+File.separator+IJunitConstants.DEFAULT_TEST_INPUT_EXCEL;//name of excel file 
		 
		 
		 		String sheetName = ExcelColumnConstants.DEFAULT_SHEET_NAME;//name of sheet 
		 
		 
		 		wb = new XSSFWorkbook(); 
		 		XSSFSheet sheet = wb.createSheet(sheetName) ; 
		 		int noOfrows=testCases.size();
		 		//create header
		 		XSSFRow row = sheet.createRow(0); 
		 		XSSFCell cell = row.createCell(0);
		 		cell.setCellValue("Sl No"); 
		 		cell = row.createCell(1);
		 		cell.setCellValue("Test Case"); 
		 		cell = row.createCell(2);
		 		cell.setCellValue("Description");
		 		cell = row.createCell(3);
		 		cell.setCellValue("Pattern");
		 		cell = row.createCell(4);
		 		cell.setCellValue("Source Protocol");
		 		cell = row.createCell(5);
		 		cell.setCellValue("Source Format");
		 		cell = row.createCell(6);
		 		cell.setCellValue("Target Protocol");
		 		cell = row.createCell(7);
		 		cell.setCellValue("Target Format");
		 		cell = row.createCell(8);
		 		cell.setCellValue("Security Info");
		 		cell = row.createCell(9);
		 		cell.setCellValue("End Point");
		 		cell = row.createCell(10);
		 		cell.setCellValue("Legacy End Point");
		 		cell = row.createCell(11);
		 		cell.setCellValue("Source Info");
		 		cell = row.createCell(12);
		 		cell.setCellValue("Target Info");
		 		cell = row.createCell(13);
		 		cell.setCellValue("Input");
		 		cell = row.createCell(14);
		 		cell.setCellValue("Expected Output");
		 		cell = row.createCell(15);
		 		cell.setCellValue("Actual Output");
		 		cell = row.createCell(16);
		 		cell.setCellValue("Digital Signature");
		 		cell = row.createCell(17);
		 		cell.setCellValue("Ignore List");
		 		cell = row.createCell(18);
		 		cell.setCellValue("Active");
		 		
		 		
		 		//iterating r number of rows 
		 		for (int r=0;r < noOfrows; r++ ) 
		 		{ 
		 			row = sheet.createRow(r+1);
		 			cell = row.createCell(0);
		 			cell.setCellValue(r+1);
		 			cell = row.createCell(1);
		 			cell.setCellValue(testCases.get(r).getTestCase().trim());
		 			cell = row.createCell(2);
		 			cell.setCellValue(testCases.get(r).getDescription().trim());
		 			cell = row.createCell(3);
		 			cell.setCellValue(testCases.get(r).getPattern().trim());
		 			cell = row.createCell(4);
		 			cell.setCellValue(testCases.get(r).getSourceProtocol().trim());
		 			cell = row.createCell(5);
		 			cell.setCellValue(testCases.get(r).getSourceFormat().trim());
		 			cell = row.createCell(6);
		 			cell.setCellValue(testCases.get(r).getTargetProtocol().trim());
		 			cell = row.createCell(7);
		 			cell.setCellValue(testCases.get(r).getTargetFormat().trim());
		 			cell = row.createCell(8);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(9);
		 			cell.setCellValue(testCases.get(r).getEndpoint().trim());
		 			cell = row.createCell(10);
		 			cell.setCellValue(testCases.get(r).getLegacyEndPont().trim());
		 			cell = row.createCell(11);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(12);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(13);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(14);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(15);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(16);
		 			//cell.setCellValue(r+1);
		 			cell = row.createCell(17);
		 			cell.setCellValue(ignoreFields.get(r).trim());
		 			cell = row.createCell(18);
		 			cell.setCellValue(testCases.get(r).getIsActive().trim());
		 			
		 
		 			
		 		} 
		 
		 
		 		fileOut = new FileOutputStream(excelFileName); 
		 
		 
		 		//write this workbook to an Outputstream. 
		 		
		}catch(Exception e){
			throw e;
			
		}finally{
			try {
				wb.write(fileOut); 
				fileOut.flush(); 
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	} 

	public String executeDisplay(){
		username = ServletActionContext.getRequest().getParameter("username");
		profileRoot = UIConstants.WEBROOT+File.separator+this.username+File.separator;
		
		return "success";
	}
	
public String executeTest(){
	//log.info("================Testing initiated=================");
	 //HttpSession session=ServletActionContext.getRequest().getSession(false);  
	IJunitExecuter ex = new IJunitExecuter();
	String ls=System.getProperty("line.separator");
	StringBuffer sb= new StringBuffer();
	System.out.println("================Testing initiated=================");
	sb.append(logMessage+"================Testing initiated================="+ls);
	PrintWriter writer = null;
	PrintWriter csvWriter = null;
	JUnitCore junit = new JUnitCore();
	boolean hasFailure=false;
	boolean hasRerunFailure = false;
	Calendar now = Calendar.getInstance();
	String yymmddhhmmss=new Integer(now.get(Calendar.YEAR)).toString()
						+new Integer(now.get(Calendar.MONTH)+1).toString()
						+new Integer(now.get(Calendar.DAY_OF_MONTH)).toString()
						+new Integer(now.get(Calendar.HOUR)).toString()
						+new Integer(now.get(Calendar.MINUTE)).toString()
						+new Integer(now.get(Calendar.SECOND)).toString();
						
	try{
		String csvHeader = "testCase,description,pattern,"+
        "sourceProtocol,sourceFormat,targetProtocol,targetFormat,"+
        "securityInfo,endpoint,legacyEndPont,sourceInfo,input,"+
        "expectedOutput,actualOutput,passed,executedAt,rerun,reason";
		String source;
		String inputFileLocation;
		String configFileLocation;
		List<TestCaseDTO> testCases = new ArrayList<TestCaseDTO>();
		List<String> failedCases = new ArrayList<String>();
		List<TestCaseDTO> failedTestCases = new ArrayList<TestCaseDTO>();
		List<TestCaseDTO> testCaseReports = new ArrayList<TestCaseDTO>();
		List<String> testCaseXMLReports = new ArrayList<String>();
		TestConfDTO confDTO=null;
		
        source=UIConstants.WEBROOT+File.separator+this.username+File.separator;
        
        System.out.println("Root directory -> "+source);
        quickReportName="QuickReport.txt";
        quickReportPath=source+"QuickReport.txt";
        csvReportName="Report"+yymmddhhmmss+".csv";
        csvReportPath=source+"Report"+yymmddhhmmss+".csv";
        xmlReportName="Report.xml";
        xmlReportPath=source+"Report.xml";
        writer = new PrintWriter(source+"QuickReport.txt", "UTF-8");
        csvWriter = new PrintWriter(source+"Report"+yymmddhhmmss+".csv", "UTF-8");
        csvWriter.println(csvHeader);
        
        inputFileLocation = source+IJunitConstants.DEFAULT_TEST_INPUT_EXCEL;
        configFileLocation = source+IJunitConstants.DEFAULT_TEST_CONF_PROPERTIES;
        confDTO = ex.loadTestConf(configFileLocation);
        testCases = ExcelUtil.readExcel(inputFileLocation);
        int noOfTests = testCases.size();
        System.out.println("Total Test cases to be executed -> "+(noOfTests-1));
        sb.append("Total Test cases to be executed -> "+(noOfTests-1)+ls);
        for(int testIndex=1 ;testIndex<noOfTests; testIndex++){
        	TestCaseDTO testCase = testCases.get(testIndex);
        	if(!IJunitConstants.VALUE_INACTIVE.equalsIgnoreCase(testCase.getIsActive())){
	        	System.out.println("==Intialising Test case -> "+testCase.getTestCase());
	        	testCase.setSource(source);
	        	ex.setDefault(testCase,source);
	        	//IJunitTestCase.testCase = testCase;
	        	//Result result = junit.run(TestCaseFactory.getTestClass(testCase.getPattern(),testCase));
	        	//Result result=null;
	        	TestCase tc= TestCaseFactory.getTestClass(testCase.getPattern(),testCase) ;
	        	TestResult tr=tc.run();
	        		        	
	        	System.out.println(testCase.getTestCase()+" : "+testCase.translateResult(tr.wasSuccessful()));
	        	System.out.println("Test case result for : "+testCase.getTestCase()+" result "+testCase.translateResult(tr.wasSuccessful()));
	        	testCase.setPassed(tr.wasSuccessful());
	        	testCase.setExecutedAt(System.currentTimeMillis());
	        	writer.println("Test Case : "+testCase.getTestCase()+"| Result : "+testCase.translateResult(tr.wasSuccessful()));
	            if(!tr.wasSuccessful()){
	            	//System.out.println("Reason for failure : "+tr.getFailures());
	            	if(tr.failures().hasMoreElements()){
	            		System.out.println(" Failure reason "+tr.failures().nextElement());
		            	String reason=tr.failures().nextElement().toString().replaceAll("\\r\\n|\\r|\\n", " ");
		            	writer.println("Failure :"+tr.failures().nextElement().toString());
		            	String exMessage=tr.failures().nextElement().exceptionMessage().replaceAll("\\r\\n|\\r|\\n", " ");
		            	exMessage = exMessage.substring(0,exMessage.indexOf(" "));
		            	testCase.setReason(reason);
		            	testCase.setExceptionName(exMessage);
		            	testCase.setDescription(reason);
	            	}else{
	            		System.out.println("no failure info");
	            	}
	            	
	            	//System.out.println(" error "+tr.errors());
	            	if(tr.errors().hasMoreElements()){
	            		System.out.println(" Failure reason "+tr.errors().nextElement());
		            	String reason=tr.errors().nextElement().toString().replaceAll("\\r\\n|\\r|\\n", " ");
		            	writer.println("Failure :"+tr.errors().nextElement().toString());
		            	String exMessage=tr.errors().nextElement().getClass().getName();
		            	//exMessage = exMessage.substring(0,exMessage.indexOf(" "));
		            	//System.out.println(" exMessage "+exMessage);
		            	testCase.setReason(reason);
		            	testCase.setExceptionName(exMessage);
		            	testCase.setDescription(reason);
	            	}else{
	            		System.out.println("No error info");
	            	}
	            	
	            	/*if(tr.failureCount()>0){
	            		if(tr..get(0).getException()!=null){
	            			testCase.setExceptionName(result.getFailures().get(0).getException().getClass().toString());
	            		}
	            		testCase.setErrorDescription(result.getFailures().get(0).getDescription().toString());
	            	}*/
	            	hasFailure = true;
	            	//failedCases.add(testCase.getTestCase());
	            	failedTestCases.add(testCase);
	            	if(!IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry())){
	            		failedCases.add(testCase.getTestCase());
	            	}
	            	
	            }
	            csvWriter.println(testCase.toString());
	            testCaseReports.add(new TestCaseDTO(testCase));
	            if(tr.wasSuccessful() || (!tr.wasSuccessful()&&!IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry()))){
	            	testCaseXMLReports.add(new TestCaseDTO(testCase).toXML());
	            }
	            System.out.println("==Completed Test Case : "+testCase.getTestCase());
        	}
        }
        
        //rerun failed test cases
        if(IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry())&&hasFailure){
        	 System.out.println("==Rerunning failed test cases");
        	int failedCasesSize = failedTestCases.size();
        	for(int testIndex=0 ;testIndex<failedCasesSize; testIndex++){
	        	TestCaseDTO testCase = failedTestCases.get(testIndex);
	        	 System.out.println("==Rerunning Test case -> "+testCase.getTestCase());
	        	testCase.setRerun(true);
	        	//IJunitTestCase.testCase = testCase;
	        	//Result result = junit.run(TestCaseFactory.getTestClass(testCase.getPattern()));
	        	//Result result=null;
	        	TestCase tc= TestCaseFactory.getTestClass(testCase.getPattern(),testCase) ;
	        	TestResult tr=tc.run();
	        	System.out.println(testCase.getTestCase()+" Rerun : "+testCase.translateResult(tr.wasSuccessful()));
	        	System.out.println("Rerun Test case result for : "+testCase.getTestCase()+" result "+testCase.translateResult(tr.wasSuccessful()));
	        	testCase.setPassed(tr.wasSuccessful());
	        	testCase.setExecutedAt(System.currentTimeMillis());
	        	writer.println("Rerun Test Case : "+testCase.getTestCase()+"| Result : "+testCase.translateResult(tr.wasSuccessful()));
	            if(!tr.wasSuccessful()){
	            	if(tr.failures().hasMoreElements()){
	            		 System.out.println(" Failure reason "+tr.failures().nextElement());
		            	String reason=tr.failures().nextElement().toString().replaceAll("\\r\\n|\\r|\\n", " ");
		            	writer.println("Failure :"+tr.failures().nextElement().toString());
		            	String exMessage=tr.failures().nextElement().exceptionMessage().replaceAll("\\r\\n|\\r|\\n", " ");
		            	exMessage = exMessage.substring(0,exMessage.indexOf(" "));
		            	testCase.setReason(reason);
		            	testCase.setExceptionName(exMessage);
		            	testCase.setDescription(reason);
	            	}else{
	            		 System.out.println("no failure info");
	            	}
	            	
	            	//System.out.println(" error "+tr.errors());
	            	if(tr.errors().hasMoreElements()){
	            		 System.out.println(" Failure reason "+tr.errors().nextElement());
		            	String reason=tr.errors().nextElement().toString().replaceAll("\\r\\n|\\r|\\n", " ");
		            	writer.println("Failure :"+tr.errors().nextElement().toString());
		            	String exMessage=tr.errors().nextElement().getClass().getName();
		            	//exMessage = exMessage.substring(0,exMessage.indexOf(" "));
		            	//System.out.println(" exMessage "+exMessage);
		            	testCase.setReason(reason);
		            	testCase.setExceptionName(exMessage);
		            	testCase.setDescription(reason);
	            	}else{
	            		 System.out.println("No error info");
	            	}
	            	hasRerunFailure = true;
	            	failedCases.add(testCase.getTestCase());
	            }
	            csvWriter.println(testCase.toString());
	            testCaseReports.add(new TestCaseDTO(testCase));
	            testCaseXMLReports.add(new TestCaseDTO(testCase).toXML());
	            System.out.println("==Completed Test Case rerun : "+testCase.getTestCase());
	        }
        	
        }
        System.out.println("Creating XMLReport");
        sb.append("Creating XMLReport"+ls);
        ex.createXMLReport(testCaseXMLReports,source,failedCases.size(),yymmddhhmmss);
        if(IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getDbReporting())){
        	DBUtil.reportToDB(confDTO, testCaseReports);
        }
        sessionMap.put("currentReport",testCaseReports);  
        sessionMap.put("xmlReportName", xmlReportName);
        sessionMap.put("quickReportName", quickReportName);
        sessionMap.put("csvReportName", csvReportName);
        //session.setAttribute("currentReport",testCaseReports);  
        if((hasRerunFailure&&IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry()))){
        	//throw new Exception("One or more test cases have failed with rerun attempt. Please check report. Falied Test cases are "+failedCases.toString());
        	 sb.append("One or more test cases have failed with rerun attempt. Please check report. Falied Test cases are "+failedCases.toString()+ls);
        }else if(hasFailure && !IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry())){
        	//throw new Exception("One or more test cases have failed. Please check report. Falied Test cases are "+failedCases.toString());
        	 sb.append("One or more test cases have failed. Please check report. Falied Test cases are "+failedCases.toString()+ls);
        }
        logMessage=sb.toString();
        return "success";
     			
	}catch(Exception e){
		logMessage=sb.toString();
		addActionError(e.getMessage());
		return "error";
	}finally{
		
		if(writer != null){
			writer.close();
		}
		if(csvWriter != null){
			csvWriter.close();
		}
	
	}

}
	
	public String downloadReport(){
		try{
			
			String path=ServletActionContext.getRequest().getParameter("reportPath");
			reportInputStream = new FileInputStream(path);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			addActionError("System error "+e.getMessage());
			return "error";
		}
	}
	
	public String downloadReportByName(){
		try{
			
			/*String name=ServletActionContext.getRequest().getParameter("reportName");
			if(this.username==null||this.username.trim().length()==0){
				System.out.println("Username not obtained");
				this.username=ServletActionContext.getRequest().getParameter("username");
				System.out.println("Username obtained from query"+username);
			}*/
			reportInputStream = new FileInputStream(UIConstants.WEBROOT+File.separator+this.username+File.separator+downloadReportName);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			addActionError("System error "+e.getMessage());
			return "error";
		}
	}
	
	public String displayReportPage(){
		try{
			username=ServletActionContext.getRequest().getParameter("username");
			File rootDir= new File(UIConstants.WEBROOT+File.separator+this.username+File.separator); 
			String fileList[]=rootDir.list();
			for(String fileName:fileList){
				//System.out.println(" name : "+fileName);
				if(fileName.endsWith(".csv")){
					//System.out.println(" csv : "+fileName);
					reportList.add(fileName);
				}
			}
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			addActionError("System error "+e.getMessage());
			return "error";
		}
	}
	
	public String currentReport(){
		try{
			 HttpSession session=ServletActionContext.getRequest().getSession(false);  
			testReports = (List<TestCaseDTO>)session.getAttribute("currentReport");  
			xmlReportName=(String)session.getAttribute("xmlReportName");  
			quickReportName=(String)session.getAttribute("quickReportName");  
			csvReportName=(String)session.getAttribute("csvReportName");  
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			addActionError("System error "+e.getMessage());
			return "error";
		}
	}

	public String showInfo(){
		try{
			System.out.println("Show Info Invoked");
			 HttpSession session=ServletActionContext.getRequest().getSession(false);  
			String testName = ServletActionContext.getRequest().getParameter("testName");
			String type = ServletActionContext.getRequest().getParameter("type");
			String count = ServletActionContext.getRequest().getParameter("count");
			testReports = (List<TestCaseDTO>)session.getAttribute("currentReport");  
			TestCaseDTO testCase = testReports.get(Integer.parseInt(count));
			if("desc".equalsIgnoreCase(type)){
				infoContent=testCase.getDescription();
			}else{
				infoContent=testCase.getReason();
			}
			System.out.println("Retruning success");
			
		return "success";
	}catch(Exception e){
		e.printStackTrace();
		addActionError("System error "+e.getMessage());
		return "error";
	}
}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TestCaseDTO> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCaseDTO> testCases) {
		this.testCases = testCases;
	}

	public String getDeletedTestCaseName() {
		return deletedTestCaseName;
	}

	public void setDeletedTestCaseName(String deletedTestCaseName) {
		this.deletedTestCaseName = deletedTestCaseName;
	}



	public List<Boolean> getCheckLists() {
		return checkLists;
	}

	public void setCheckLists(List<Boolean> checkLists) {
		this.checkLists = checkLists;
	}

	
	public List<String> getProtocolList() {
		return protocolList;
	}

	public void setProtocolList(List<String> protocolList) {
		this.protocolList = protocolList;
	}

	public List<String> getFormatList() {
		return formatList;
	}

	public void setFormatList(List<String> formatList) {
		this.formatList = formatList;
	}

	public List<String> getPatternList() {
		return patternList;
	}

	public void setPatternList(List<String> patterList) {
		this.patternList = patterList;
	}

	public TestCaseDTO getNewTestCase() {
		return newTestCase;
	}

	public void setNewTestCase(TestCaseDTO newTestCase) {
		this.newTestCase = newTestCase;
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	public List<String> getIgnoreFields() {
		return ignoreFields;
	}

	public void setIgnoreFields(List<String> ignoreFields) {
		this.ignoreFields = ignoreFields;
	}

	public String getNewIgnoreField() {
		return newIgnoreField;
	}

	public void setNewIgnoreField(String newIgnoreField) {
		this.newIgnoreField = newIgnoreField;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String getProfileRoot() {
		return profileRoot;
	}

	public void setProfileRoot(String profileRoot) {
		this.profileRoot = profileRoot;
	}

	public String getCsvReportName() {
		return csvReportName;
	}

	public void setCsvReportName(String csvReportName) {
		this.csvReportName = csvReportName;
	}

	public String getXmlReportName() {
		return xmlReportName;
	}

	public void setXmlReportName(String xmlReportName) {
		this.xmlReportName = xmlReportName;
	}

	public String getQuickReportName() {
		return quickReportName;
	}

	public void setQuickReportName(String quickReportName) {
		this.quickReportName = quickReportName;
	}

	public String getCsvReportPath() {
		return csvReportPath;
	}

	public void setCsvReportPath(String csvReportPath) {
		this.csvReportPath = csvReportPath;
	}

	public String getXmlReportPath() {
		return xmlReportPath;
	}

	public void setXmlReportPath(String xmlReportPath) {
		this.xmlReportPath = xmlReportPath;
	}

	public String getQuickReportPath() {
		return quickReportPath;
	}

	public void setQuickReportPath(String quickReportPath) {
		this.quickReportPath = quickReportPath;
	}

	public String getDownloadReportName() {
		return downloadReportName;
	}

	public void setDownloadReportName(String downloadReportName) {
		this.downloadReportName = downloadReportName;
	}

	public FileInputStream getReportInputStream() {
		return reportInputStream;
	}

	public void setReportInputStream(FileInputStream reportInputStream) {
		this.reportInputStream = reportInputStream;
	}

	

	public List<String> getReportList() {
		return reportList;
	}

	public void setReportList(List<String> reportList) {
		this.reportList = reportList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		
		sessionMap=(SessionMap)map;  
	}

	public List<TestCaseDTO> getTestReports() {
		return testReports;
	}

	public void setTestReports(List<TestCaseDTO> testReports) {
		this.testReports = testReports;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public List<String> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<String> activeList) {
		this.activeList = activeList;
	}
	
	
	
	
	
	
	
	

	
	
}

