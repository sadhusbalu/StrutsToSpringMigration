package com.cts.integration.ui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

import com.cts.integration.dto.IJunitConstants;
import com.cts.integration.ui.constants.UIConstants;
import com.cts.integration.util.FileUtil;
import com.opensymphony.xwork2.ActionSupport;

public class SecurityAction extends ActionSupport{
	
	
	String confContent;
	String username;
	String testName;
	String targetPath;
	File certFile;
	
	
	
	public String loadSecurityProperties() {
		FileInputStream fis = null;
		
		 try {
			
			
			username = ServletActionContext.getRequest().getParameter("username");
			testName = ServletActionContext.getRequest().getParameter("testName");
			
			
			targetPath=UIConstants.WEBROOT+File.separator+username+File.separator+testName+File.separator+testName+"Security.properties";
			String samplePath=UIConstants.WEBROOT+File.separator+"Security.properties";
			System.out.println("path --"+targetPath);
			File sourcePropertiesFile = new File(targetPath);
			if(!sourcePropertiesFile.exists()){
				sourcePropertiesFile = new File(samplePath);
				confContent=new String(Files.readAllBytes(Paths.get(samplePath)),Charset.defaultCharset());
				//confContent= FileUtil.readFileAsString(samplePath);
			}else{
				confContent=new String(Files.readAllBytes(Paths.get(targetPath)),Charset.defaultCharset());
				//confContent= FileUtil.readFileAsString(targetPath);
			}
			
			//confContent=protocolProperties.toString();
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error "+e.getMessage());
			return "error";
		}finally{
			try {
				if(fis!=null){
					
						fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public String saveSecurityProperties() {
		
		FileInputStream fis = null; 
		try {
			
			
			FileUtil.writeToFile(targetPath, confContent);
			
			String targetDir = UIConstants.WEBROOT+File.separator+username+File.separator+testName+File.separator;
			String certFileName=null;
			System.out.println("CertFile "+certFile);
			if(certFile!=null){
				fis=new FileInputStream(new File(targetPath));
				Properties securityProperties= new Properties();
				securityProperties.load(fis);
				
				certFileName=securityProperties.getProperty("CERTNAME");
				System.out.println("certFileName "+certFileName);
				File targetFile = new File(targetDir+certFileName);
				org.apache.commons.io.FileUtils.copyFile(certFile, targetFile);
			}
			
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}finally{
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public String reloadSecurityProperties() {
		
		
		 try {
			
			 String samplePath=UIConstants.WEBROOT+File.separator+"Security.properties";
			 System.out.println(" reloading from "+samplePath);
			confContent=new String(Files.readAllBytes(Paths.get(samplePath)),Charset.defaultCharset());
			
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}finally{
			
			
		}
	}


	public String getTargetPath() {
		return targetPath;
	}


	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}


	

	public String getConfContent() {
		return confContent;
	}


	public void setConfContent(String confContent) {
		this.confContent = confContent;
	}


	


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getTestName() {
		return testName;
	}


	public void setTestName(String testName) {
		this.testName = testName;
	}

	public File getCertFile() {
		return certFile;
	}

	public void setCertFile(File certFile) {
		this.certFile = certFile;
	}

	
	
	
	
	
	

}

