package cn.bluemobi.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.util.file.FileHelper;
import cn.bluemobi.util.file.ImageUtil;
import cn.bluemobi.util.helper.ValidateHelper;
/**
 * 文件上传管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
public class FileController extends BaseController{
	
	/**
	 * 文件上传
	 * @param fileType 
	 * 					上传文件的类型
	 * 					由此判断存放位置
	 * @param file
	 * 					上传的文件
	 */
	@RequestMapping(value="admin/uploadFile",method = RequestMethod.POST)
	public void fileUploadF(MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String type = null, fileName = null;
			File image = null;

			if (file == null || file.getSize() == 0) {
				map.put(STATUS, EMPTY);
				return;
			}

			type = file.getOriginalFilename().toLowerCase();
			type = type.substring(type.lastIndexOf("."));
			BufferedImage img = ImageIO.read(file.getInputStream());
			String qq = formatDate(new Date(),"yyyy-MM");
			String fileDir = "upload/";
				fileDir += "img/";
			fileDir += qq+"/";
			String fd =  fileDir+FileHelper.getTimeFileName()+"_"+img.getWidth()+"x"+img.getHeight();
			//写入图片宽和高到文件名中
		    fileName = fd + type;
			image = new File(BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH + fileName);
			File dir = image.getParentFile();
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			FileCopyUtils.copy(file.getBytes(), image);
			ImageUtil.scaleImageInRatio(BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH + fileName, BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH +fd+"_small"+type, 400, 400);
			map.put("realSrc", BlueMobiConstant.upload +fd+"_small"+type);
			map.put("src", BlueMobiConstant.upload+fileName);
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, FAIL);
		} finally {
			outJSON(map);
		}
	}
	
	public static Map<String,Object> uploadFile(MultipartFile file,String fileType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!ValidateHelper.isNullOrEmpty(file)){
				String suffix = file.getOriginalFilename().toLowerCase();
				suffix = suffix.substring(suffix.lastIndexOf("."));
				String qq = formatDate(new Date(),"yyyy-MM");
				String fileDir = "upload/";
				if("2".equals(fileType)){
					fileDir += "img/";
				}else if("3".equals(fileType)){
					fileDir += "voice/";
				}else if("4".equals(fileType)){
					fileDir += "video/";
				}
				fileDir += qq+"/";
				String fd =  fileDir+FileHelper.getTimeFileName()+"_";
				BufferedImage img = ImageIO.read(file.getInputStream());
				if("2".equals(fileType)){
					fd = fd +img.getWidth() +"x"+img.getHeight();
				}
				String fileName =  fd + suffix;
				File newFile = new File(BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH + fileName);
				File dir = newFile.getParentFile();
				if (!dir.isDirectory()) {
					dir.mkdirs();
				}
				FileCopyUtils.copy(file.getBytes(),newFile);
				String coverPath="";
				if("2".equals(fileType)){
					ImageUtil.scaleImageInRatio(BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH + fileName, BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH +fd+"_small"+suffix, 400, 400);
					fileName = fd+"_small"+suffix;
				}/*else if("4".equals(fileType)){
					 coverPath = CreatePh.processImg(BlueMobiConstant.WEB_SITE_ROOT_SIBLINGS_PATH + fileName);
				}*/
				map.put(DATA, BlueMobiConstant.upload+fileName);
				map.put("realSrc", BlueMobiConstant.upload+fd+suffix);
				map.put("coverPath", coverPath);
				map.put(STATUS, SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map; 
	}
	
	/**
	 * 判断文件是不是有效的图片文件
	 * @param file 源文件
	 */
	public static boolean checkImg(MultipartFile file){
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {//文件不是图片
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			return true;
	}
	
}
