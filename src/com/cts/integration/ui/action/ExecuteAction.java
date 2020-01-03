package com.cts.integration.ui.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.cts.integration.dto.IJunitConstants;
import com.cts.integration.dto.IJunitTestCase;
import com.cts.integration.dto.TestCaseDTO;
import com.cts.integration.dto.TestConfDTO;
import com.cts.integration.factory.TestCaseFactory;
import com.cts.integration.unitTest.executer.IJunitExecuter;
import com.cts.integration.util.DBUtil;
import com.cts.integration.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ExecuteAction extends ActionSupport {
	/*public String executeTest(){

		
		//System.out.println("================Testing initiated=================");
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
			
	        source=System.getProperty("RootSource");
	        if(source == null || source.trim().length()==0){
	        	source = System.getProperty("user.dir")+File.separator;
	        }
	        if(!source.endsWith(File.separator)){
	        	source = source+File.separator;
	        }
	       
	        writer = new PrintWriter(source+"QuickReport.txt", "UTF-8");
	        csvWriter = new PrintWriter(source+"Report"+yymmddhhmmss+".csv", "UTF-8");
	        csvWriter.println(csvHeader);
	        
	        inputFileLocation = source+IJunitConstants.DEFAULT_TEST_INPUT_EXCEL;
	        configFileLocation = source+IJunitConstants.DEFAULT_TEST_CONF_PROPERTIES;
	        confDTO = IJunitExecuter.loadTestConf(configFileLocation);
	        testCases = ExcelUtil.readExcel(inputFileLocation);
	        int noOfTests = testCases.size();
	        
	        for(int testIndex=1 ;testIndex<noOfTests; testIndex++){
	        	TestCaseDTO testCase = testCases.get(testIndex);
	        	
	        	
	        	testCase.setSource(source);
	        	
	        	IJunitTestCase.testCase = testCase;
	        	Result result = junit.run(TestCaseFactory.getTestClass(testCase.getPattern()));
	        	
	        	System.out.println("Test case result for : "+testCase.getTestCase()+" result "+result.wasSuccessful());
	        	testCase.setPassed(result.wasSuccessful());
	        	testCase.setExecutedAt(System.currentTimeMillis());
	        	writer.println("Test Case : "+testCase.getTestCase()+"| Result : "+result.wasSuccessful());
	            if(!result.wasSuccessful()){
	            	
	            	writer.println("Failure :"+result.getFailures());
	            	testCase.setReason(result.getFailures().toString());
	            	if(result.getFailures().size()>0){
	            		if(result.getFailures().get(0).getException()!=null){
	            			testCase.setExceptionName(result.getFailures().get(0).getException().getClass().toString());
	            		}
	            		testCase.setErrorDescription(result.getFailures().get(0).getDescription().toString());
	            	}
	            	hasFailure = true;
	            	//failedCases.add(testCase.getTestCase());
	            	failedTestCases.add(testCase);
	            	if(!IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry())){
	            		failedCases.add(testCase.getTestCase());
	            	}
	            	
	            }
	            csvWriter.println(testCase.toString());
	            testCaseReports.add(new TestCaseDTO(testCase));
	            if(result.wasSuccessful() || (!result.wasSuccessful()&&!IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry()))){
	            	testCaseXMLReports.add(new TestCaseDTO(testCase).toXML());
	            }
	            
	        }
	        
	        //rerun failed test cases
	        if(IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry())&&hasFailure){
	        	
	        	int failedCasesSize = failedTestCases.size();
	        	for(int testIndex=0 ;testIndex<failedCasesSize; testIndex++){
		        	TestCaseDTO testCase = failedTestCases.get(testIndex);
		        	
		        	testCase.setRerun(true);
		        	IJunitTestCase.testCase = testCase;
		        	Result result = junit.run(TestCaseFactory.getTestClass(testCase.getPattern()));
		        	
		        	System.out.println("Rerun Test case result for : "+testCase.getTestCase()+" result "+result.wasSuccessful());
		        	testCase.setPassed(result.wasSuccessful());
		        	testCase.setExecutedAt(System.currentTimeMillis());
		        	writer.println("Rerun Test Case : "+testCase.getTestCase()+"| Result : "+result.wasSuccessful());
		            if(!result.wasSuccessful()){
		            	
		            	writer.println("Failure :"+result.getFailures());
		            	testCase.setReason(result.getFailures().toString());
		            	if(result.getFailures().size()>0){
		            		if(result.getFailures().get(0).getException()!=null){
		            			testCase.setExceptionName(result.getFailures().get(0).getException().getClass().toString());
		            		}
		            		testCase.setErrorDescription(result.getFailures().get(0).getDescription().toString());
		            	}
		            	hasRerunFailure = true;
		            	failedCases.add(testCase.getTestCase());
		            }
		            csvWriter.println(testCase.toString());
		            testCaseReports.add(new TestCaseDTO(testCase));
		            testCaseXMLReports.add(new TestCaseDTO(testCase).toXML());
		            
		        }
	        	
	        }
	        IJunitExecuter.createXMLReport(testCaseXMLReports,source,failedCases.size(),yymmddhhmmss);
	        if(IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getDbReporting())){
	        	DBUtil.reportToDB(confDTO, testCaseReports);
	        }
	        if((hasRerunFailure&&IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry()))){
	        	throw new Exception("One or more test cases have failed with rerun attempt. Please check report. Falied Test cases are "+failedCases.toString());
	        }else if(hasFailure && !IJunitConstants.VALUE_TRUE.equalsIgnoreCase(confDTO.getRetry())){
	        	throw new Exception("One or more test cases have failed. Please check report. Falied Test cases are "+failedCases.toString());
	        }
	        return "success";
				
		}catch(Exception e){
			addActionError(e.getMessage());
			return "error";
		}
		finally{
			
			if(writer != null){
				writer.close();
			}
			if(csvWriter != null){
				csvWriter.close();
			}
		
		}

	
	}*/

}
