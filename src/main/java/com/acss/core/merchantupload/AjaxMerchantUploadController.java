package com.acss.core.merchantupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.acss.core.model.application.ApplicationSeqNo;
import com.acss.core.model.image.ApplicationImage;
import com.acss.core.model.image.ImageBuilder;

/**
 * Controller for the merchant upload process which controls the page transition, handles validation
 * and delegate process to appropriate service.
 * Controllers should have minimal process/code.
 * 
 * @author gvargas
 */
@Controller
public class AjaxMerchantUploadController {
	
	@Autowired
	private Environment env;
	private final static String UPLOAD_DIRECTORY_KEY = "upload.directory";
	private final static String INPUT_FILE_PARAM = "files[]";
	private final static String IMAGECODE_NUMTYPE_ENTRY = "T_IMAGE_IMAGECODE";
	private final static String APPCD_PARAMNAME = "applicationNo";
	private final static String SEQNO_PARAMNAME = "seqNo";
	private final static String GROUPID_PARAMNAME = "groupId";
	
	
	@Autowired
	private FileUploadService uploadService;
	
	@RequestMapping(value = "home" , method = RequestMethod.GET)
	public String ajaxuploads() {
		return "upload/ajaxupload";
	}
	
	@RequestMapping(value = "ajaxUpload" , method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> list() {
		
		List<Image> list = new ArrayList<>();
		 Map<String, Object> files = new HashMap<>();
	        files.put("files", list);
		return files;
	}
	
	@RequestMapping(value = "application-seqno",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> newApplication(HttpServletRequest request){
		ApplicationSeqNo appSeqNo = new ApplicationSeqNo();
		appSeqNo.setAppCd((String) request.getParameter(APPCD_PARAMNAME));
		appSeqNo.setAppSeqNo((String) request.getParameter(SEQNO_PARAMNAME));
		uploadService.createNewApplicationWithSeqNo(appSeqNo);
		return new HashMap<>();
	}
	
	/**
	 * Saves the images from the upload request.
	 * @param request
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "ajaxUpload" , method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest) throws IOException{
		Iterator<MultipartFile> itr = multipartRequest.getFiles(INPUT_FILE_PARAM).iterator();
		
        MultipartFile mpf;
        List<Image> list = new LinkedList<>();
        
        while (itr.hasNext()) {
            mpf = itr.next();
            String imageType = multipartRequest.getParameter(mpf.getOriginalFilename());
            String appNo =  multipartRequest.getParameter(APPCD_PARAMNAME);
            String groupId = multipartRequest.getParameter(GROUPID_PARAMNAME);
            
            ApplicationImage imageDTO = 
    				new ImageBuilder().withDefaultValues().build();
            
    		imageDTO.setDataCd(appNo);
    		imageDTO.setGroupId(groupId);
    		imageDTO.setImageType(new BigDecimal(imageType));
    		
    		uploadService.saveImage(mpf,imageDTO);
            /*
            String newFilenameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = newFilenameBase + originalFileExtension;
            String storageDirectory = env.getProperty(UPLOAD_DIRECTORY_KEY);
            String contentType = mpf.getContentType();
            String imageType = multipartRequest.getParameter(mpf.getOriginalFilename());
            File newFile = new File(storageDirectory + "/" + newFilename);
            
                mpf.transferTo(newFile);
                
                BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
                String thumbnailFilename = newFilenameBase + "-thumbnail.png";
                File thumbnailFile = new File(storageDirectory + "/" + thumbnailFilename);
                ImageIO.write(thumbnail, "png", thumbnailFile);
                
                Image image = new Image();
                image.setName(mpf.getOriginalFilename());
                image.setThumbnailFilename(thumbnailFilename);
                image.setNewFilename(newFilename);
                image.setContentType(contentType);
                image.setSize(mpf.getSize());
                image.setThumbnailSize(thumbnailFile.length());
                image.setImageType(imageType);
                //image = imageDao.create(image);
                
                image.setUrl("/picture/"+image.getId());
                image.setThumbnailUrl("/thumbnail/"+image.getId());
                image.setDeleteUrl("/delete/"+image.getId());
                image.setDeleteType("DELETE");
                */
            	
                //list.add(image);
            
        }
        
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        return files;
		
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "initialize", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> generateAppCdAndGroupId(){
		 Map<String, String> initMap = new HashMap<>();
		 String appCd = uploadService.generateAppNo();
		 //generates group id for images.
		 String groupId = uploadService.generateRequestedNumType(IMAGECODE_NUMTYPE_ENTRY);
		 initMap.put("appCd", appCd);
		 initMap.put("groupId",groupId);
		 
		return initMap;
	}
	
	@RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @PathVariable Long id) {
        File imageFile = new File(env.getProperty(UPLOAD_DIRECTORY_KEY)+"/7771d432-f71e-4b5c-bafa-efced35bfdbd-thumbnail.png");
        response.setContentType("image/jpeg");
        response.setContentLength(144388);
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            
        }
    }
	
	@RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, @PathVariable Long id) {
        File imageFile = new File(env.getProperty(UPLOAD_DIRECTORY_KEY)+"/7771d432-f71e-4b5c-bafa-efced35bfdbd.jpg");
        response.setContentType("image/jpeg");
        response.setContentLength(879394);
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            
        }
    }

}
