package com.cts.integration.ui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

import com.cts.integration.ui.constants.UIConstants;
import com.cts.integration.util.FileUtil;
import com.opensymphony.xwork2.ActionSupport;

public class PropertiesAction extends ActionSupport{
	
	//Properties protocolProperties = new Properties();
	String confContent;
	String protocol;
	String username;
	String testName;
	String type;
	String targetPath;
	
	
	
	public String loadProtocol() {
		//FileInputStream fis = null;
		
		 try {
			
			type = ServletActionContext.getRequest().getParameter("type");
			username = ServletActionContext.getRequest().getParameter("username");
			testName = ServletActionContext.getRequest().getParameter("testName");
			protocol = ServletActionContext.getRequest().getParameter("protocol");
			System.out.println(" type --- "+type);
			targetPath=UIConstants.WEBROOT+File.separator+username+File.separator+testName+File.separator+testName+type+".properties";
			String samplePath=UIConstants.WEBROOT+File.separator+"Sample"+protocol+".properties";
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
			//fis= new FileInputStream(sourcePropertiesFile);
			//protocolProperties.load(fis);
			//confContent=protocolProperties.toString();
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}finally{
			/*try {
				if(fis!=null){
					
						fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
	}
	
	public String saveProtocol() {
		FileInputStream fis = null;
		
		 try {
			
			FileUtil.writeToFile(targetPath, confContent);
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
	
	public String reloadProperties() {
		FileInputStream fis = null;
		
		 try {
			
			String samplePath=UIConstants.WEBROOT+File.separator+"Sample"+protocol+".properties";
			confContent=new String(Files.readAllBytes(Paths.get(samplePath)),Charset.defaultCharset());
			
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


	public String getTargetPath() {
		return targetPath;
	}


	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}


	/*public Properties getProtocolProperties() {
		return protocolProperties;
	}


	public void setProtocolProperties(Properties protocolProperties) {
		this.protocolProperties = protocolProperties;
	}*/


	public String getConfContent() {
		return confContent;
	}


	public void setConfContent(String confContent) {
		this.confContent = confContent;
	}


	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
	

}
