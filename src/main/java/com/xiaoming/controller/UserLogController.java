package com.xiaoming.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoming.config.AuthHandlerInterceptor;
import com.xiaoming.mapper.UserLogMapper;
import com.xiaoming.model.UserLog;
import com.xiaoming.utils.TokenUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
/***
 * 用户日记
 * @author 86155
 *
 */
public class UserLogController {
@Autowired
UserLogMapper userLogMapper;
@Autowired
TokenUtil tokenUtil;
//图片存储位置
@Value("${file.path}")
private String path;
//域名映射图片存储的位置
@Value("${file.root}")
private String root; 

	/**
	 * 根据用户id获取日志的标题，时间，id
	 * @param request
	 * @return
	 */
	@GetMapping("/userlog")
	public List<UserLog> userlogList(HttpServletRequest request){
		String setToken = request.getHeader("setToken");
	   	Map<String,String> token= tokenUtil.decodeToken(setToken);
		return userLogMapper.getUserLogList(token.get("Id"));
	}
	
	/***
	 * 上传图片
	 * @param file
	 * @param request
	 * @return
	 */
    @PostMapping("/userlog/image")
    public Map<String,Object> userlogImage(@RequestParam("wangeditor-uploaded-image") MultipartFile file, HttpServletRequest request) {
    	Map<String,Object> images=new HashMap<>();
    	Map<String,String> imagesUrl=new HashMap<>();
    	//获取项目classes/static的地址
        //String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        //System.err.println(staticPath);
        //String staticPath=src/main/resources/static/""
        String fileName = file.getOriginalFilename();  //获取文件名
        //文件后缀
        String fileTyle=fileName.substring(fileName.lastIndexOf("."),fileName.length()); 
        //判断文件的类型，只能是image类型
        boolean fileType=file.getContentType().startsWith("image");
        if(!fileType) {
        	images.put("errno", 1);
        	images.put("message", "只支持image类型！");
        	return images;
        }
        //使用时间戳加uuid作为文件名
       SimpleDateFormat sf = new SimpleDateFormat("MM_dd_HH_mm_ss_");
	   String date = sf.format(System.currentTimeMillis())+UUID.randomUUID()+fileTyle;
       //获取用户的id作为文件夹作为区分
	   String setToken = request.getHeader("setToken");
       Map<String,String> token= tokenUtil.decodeToken(setToken);    
        // 图片名称
        String url_path = File.separator + date;
        //图片存储路径 path图片存储位置
        String savePath = path + File.separator + token.get("Id");
        // 访问路径用户id+图片名
        String visitPath = token.get("Id")+url_path;
        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        //存储位置加图片名称
        saveFile=new File(savePath+url_path);
        try {
        	Thread.sleep(1);
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
            //前端所需结构
        	images.put("errno", 0);
            imagesUrl.put("url", root+"images/"+visitPath);
            imagesUrl.put("alt", date);
            imagesUrl.put("href", root+"images/"+visitPath);
        	images.put("data", imagesUrl);
        } catch (IOException | InterruptedException e) {
        	images.put("errno", 1);
        	images.put("message", "上传失败");
            e.printStackTrace();
        }
        return images;
    }
    
    
    /***
     * 增加一章日记
     * @param userLog
     * @param request
     * @return
     */
    @PostMapping("/userlog/insert")
    public String userlogInsert(@RequestBody UserLog userLog,HttpServletRequest request){
    	Date date = new Date(); 
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	userLog.setUserlogTime(formatter.format(date));
    	userLog.setUserlogUpTime(formatter.format(date));
    	 String setToken = request.getHeader("setToken");
    	 Map<String,String> token= tokenUtil.decodeToken(setToken);
    	 userLog.setUserId(token.get("Id"));
    	 userLogMapper.insertAccountNumber(userLog);
    	return "success";
    }
    
    
    /***
     * 删除图片 
     * @param userLog
     * @param request
     * @return
     */
    @PostMapping("/userlog/deleteImage")
    public String deleteImage(HttpServletRequest request,@RequestBody List<String> flie){
    	String massge="";
    	String setToken = request.getHeader("setToken");
	   	 Map<String,String> token= tokenUtil.decodeToken(setToken);
    	for(int i=0;i<flie.size();i++) {
	    	try {
	    		//根据存储位置加用户id加文件删除
	    		File saveFile = new File(path+"\\"+token.get("Id")+"\\"+flie.get(i));
				if(saveFile.delete()) {
				}else {
					//代表有文件没有被删除
					massge+=i;
				}
			}
			catch (Exception e) {
				log.info(e.getMessage());
			}
    	}
    	
    	if(massge.equals("")) {
        	return "success";
    	}else {
    		return "no";
    	}
    	
    }
    /**
     * 根据用户日志id获取用户日志
     * @param userlogId
     * @return
     */
    @GetMapping("/userlog/diary")
    public UserLog userLog(@RequestParam String userlogId){
    	return userLogMapper.getUserLog(userlogId);
    }
    
    /***
     * 根据用户日志id删除用户日志
     * @param userlogId
     * @return
     */
    @DeleteMapping("/userlog/delete")
    public String deleteLog(@RequestBody Map<String,String> userlogId){
    	System.err.println(userlogId);
    	userLogMapper.deUserLog(userlogId.get("userlogId"));
    	return "success";
    }
}
