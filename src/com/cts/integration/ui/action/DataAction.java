package com.cts.integration.ui.action;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cts.integration.ui.constants.UIConstants;
import com.cts.integration.util.FileUtil;
import com.opensymphony.xwork2.ActionSupport;

public class DataAction extends ActionSupport {
	String testName;
	String username;
	String type;
	File uploadFile;
	String uploadFilePath;
	String uploadDirPath;
	String content;
	FileInputStream downLoadInputStream;
	String fileList;
	List<String> displayList = new ArrayList<String>(Arrays.asList("Input","Expected","Actual"));

	public String load() {
		
		 try {
			username = ServletActionContext.getRequest().getParameter("username");
			testName = ServletActionContext.getRequest().getParameter("testName");
			uploadDirPath = UIConstants.WEBROOT+File.separator+username+File.separator+testName+File.separator;
			if(type==null || type.trim().length()==0){
				type="Input";
			}
			content=null;
			
			uploadFilePath=UIConstants.WEBROOT+File.separator+username+File.separator+testName+File.separator+testName+type;
			File file = new File(uploadFilePath);
			if(file.exists()){
				content=new String(Files.readAllBytes(Paths.get(uploadFilePath)),Charset.defaultCharset());
			}
			
			File dir = new File(uploadDirPath);
			File stores[]=dir.listFiles();
			StringBuffer sb = new StringBuffer();
			for(File filestore:stores){
				sb.append(filestore.getName());
				sb.append(";");
			}
			fileList = sb.toString();
			
			
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}
	
	public String save() {
		
		 try {
			 if(uploadFile!=null){
				 org.apache.commons.io.FileUtils.copyFile(uploadFile, new File(uploadFilePath)); 
				 content=new String(Files.readAllBytes(Paths.get(uploadFilePath)),Charset.defaultCharset());
				 
				
				 
			 }else if(content!=null && content.trim().length()!=0){
				 FileUtil.writeToFile(uploadFilePath, content);
			 }
				
			File dir = new File(uploadDirPath);
			File stores[]=dir.listFiles();
			StringBuffer sb = new StringBuffer();
			for(File filestore:stores){
				sb.append(filestore.getName());
				sb.append(";");
			}
			fileList = sb.toString();
			
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}
	public String download() {
		
		 try {
			 File file = new File(uploadFilePath);
			if(file.exists()){
				System.out.println("Setting download file");
				downLoadInputStream = new FileInputStream(uploadFilePath);				
			}
			
	    	return "success";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addActionError("System error"+e.getMessage());
			return "error";
		}
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FileInputStream getDownLoadInputStream() {
		return downLoadInputStream;
	}

	public void setDownLoadInputStream(FileInputStream downLoadInputStream) {
		this.downLoadInputStream = downLoadInputStream;
	}

	public List<String> getDisplayList() {
		return displayList;
	}

	public void setDisplayList(List<String> displayList) {
		this.displayList = displayList;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getUploadDirPath() {
		return uploadDirPath;
	}

	public void setUploadDirPath(String uploadDirPath) {
		this.uploadDirPath = uploadDirPath;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
	
	
	
	
}
