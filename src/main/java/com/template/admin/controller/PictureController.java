package com.xxl.admin.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.template.admin.base.MPicture;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.file.FileUtil;
import com.ich.core.file.ImageCut;
import com.ich.core.file.MimeType;
import com.ich.core.http.controller.CoreController;
import com.ich.core.http.other.IPUtils;
import com.ich.core.plug.jedis.JedisClient;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("management/file")
public class PictureController extends CoreController{

	@Autowired
	protected JedisClient jedisClient;
	/** JEDIS 零时图片库 */
	@Value("${JEDIS_TMP_IMG}")
	private Integer JEDIS_TMP_IMG;

	@Value("${FILE_UPLOAD_ROOT}")
	private String FILE_UPLOAD_ROOT;
	@Value("${FILE_MIME_TYPE}")
	private String FILE_MIME_TYPE;

	@Value("${IMG_UPLOAD_ROOT}")
	private String IMG_UPLOAD_ROOT;
	@Value("${IMG_MIME_TYPE}")
	private String IMG_MIME_TYPE;
	@Value("${FILE_SERVER_HTTP}")
	private String FILE_SERVER_HTTP;

	@RequestMapping(value="doUpload",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public void upload(HttpServletRequest request, PrintWriter writer,
					   HttpServletResponse response,String callback) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获取传入文件
		multipartRequest.setCharacterEncoding("utf-8");
		MultipartFile file = multipartRequest.getFile("file");

		this.SaveAs(file.getOriginalFilename(), file, request, response);
		// 设置返回值
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", file.getOriginalFilename());
		response.setStatus(200);
		writer.write(JsonUtils.objectToJson(map));
	}

	private void SaveAs(String saveFilePath, MultipartFile file,
						HttpServletRequest request, HttpServletResponse response)  throws Exception {
		long lStartPos = 0;
		int startPosition = 0;
		int endPosition = 0;
		int fileLength = 100000;
		OutputStream fs = null;
		String contentRange = request.getHeader("Content-Range");
		System.out.println(contentRange);
		if (!new File("uploadDemo").exists()) {
			new File("uploadDemo").mkdirs();
		}
		if (contentRange == null) {
			FileUtils.writeByteArrayToFile(new File(saveFilePath),
					file.getBytes());

		} else {
			// bytes 10000-19999/1157632     将获取到的数据进行处理截取出开始跟结束位置
			if (contentRange != null && contentRange.length() > 0) {
				contentRange = contentRange.replace("bytes", "").trim();
				contentRange = contentRange.substring(0,
						contentRange.indexOf("/"));
				String[] ranges = contentRange.split("-");
				startPosition = Integer.parseInt(ranges[0]);
				endPosition = Integer.parseInt(ranges[1]);
			}

			//判断所上传文件是否已经存在，若存在则返回存在文件的大小
			if (new File(saveFilePath).exists()) {
				fs = new FileOutputStream(saveFilePath, true);
				FileInputStream fi = new FileInputStream(saveFilePath);
				lStartPos = fi.available();
				fi.close();
			} else {
				fs = new FileOutputStream(saveFilePath);
				lStartPos = 0;
			}

			//判断所上传文件片段是否存在，若存在则直接返回
			if (lStartPos > endPosition) {
				fs.close();
				return;
			} else if (lStartPos < startPosition) {
				byte[] nbytes = new byte[fileLength];
				int nReadSize = 0;
				file.getInputStream().skip(startPosition);
				nReadSize = file.getInputStream().read(nbytes, 0, fileLength);
				if (nReadSize > 0) {
					fs.write(nbytes, 0, nReadSize);
					nReadSize = file.getInputStream().read(nbytes, 0,
							fileLength);
				}
			} else if (lStartPos > startPosition && lStartPos < endPosition) {
				byte[] nbytes = new byte[fileLength];
				int nReadSize = 0;
				file.getInputStream().skip(lStartPos);
				int end = (int) (endPosition - lStartPos);
				nReadSize = file.getInputStream().read(nbytes, 0, end);
				if (nReadSize > 0) {
					fs.write(nbytes, 0, nReadSize);
					nReadSize = file.getInputStream().read(nbytes, 0, end);
				}
			}
		}
		if (fs != null) {
			fs.flush();
			fs.close();
			fs = null;
		}

	}

	@RequestMapping(value="download",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
										   @RequestParam("filename") String filename,
										   @RequestParam("filepath") String filepath)throws Exception {
		//下载文件路径
		String root = request.getSession().getServletContext().getRealPath("")+File.separator+"WEB-INF"+File.separator+FILE_UPLOAD_ROOT;
		File file = new File(root + File.separator + filepath + File.separator + filename);
		HttpHeaders headers = new HttpHeaders();
		//下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
		//通知浏览器以attachment（下载方式）打开图片
		headers.setContentDispositionFormData("attachment", downloadFielName);
		//application/octet-stream ： 二进制流数据（最常见的文件下载）。
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}

	@RequestMapping(value="upload", method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public void fileUpload(HttpServletRequest request,HttpServletResponse response,String callback) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String root = request.getSession().getServletContext().getRealPath("")+File.separator+FILE_UPLOAD_ROOT;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		List<MPicture> list = new ArrayList<>();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile multipartFile = entity.getValue();//保存原图
			String fileName = multipartFile.getOriginalFilename();//检查文件MIME_TYPE类型是否存在与白名单
			String ext = FileUtil.getFileExt(fileName).substring(1);
			if(ObjectHelper.isNotEmpty(FILE_MIME_TYPE)){
				String typeArray[] = FILE_MIME_TYPE.split(",");
				Boolean flag = false;
				for(String type : typeArray) if(ext.equals(type.toLowerCase())) flag = true;
				if(!flag){
					response.getWriter().write(callback(callback, getFailMap("服务器仅支持下类文件上传："+FILE_MIME_TYPE)));
					return ;
				}
			}
/*			if(!MimeType.filter(multipartFile.getBytes(), ext)){
				response.getWriter().write(callback(callback, getFailMap("服务器仅支持下类文件上传："+FILE_MIME_TYPE)));
				return ;
			}*/
			String ip = IPUtils.findRequestIP(request);
			String randomname = FileUtil.createRandomFileName(fileName);
			String filePath = FileUtil.createTimesDirs(root);
			File file = new File(filePath+"/"+randomname.toLowerCase());
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			String dataUrl = filePath.substring(filePath.indexOf(FILE_UPLOAD_ROOT));
			MPicture picture = new MPicture();
			picture.setDataName(randomname);
			picture.setDataUrl(dataUrl);
			picture.setUrl(FILE_SERVER_HTTP + dataUrl +"/"+ randomname);
			list.add(picture);
		}
		response.getWriter().write(callback(callback, getSuccessMap(list)));
	}

	/** 图片上传【文件流】 支持多图片上传
	 * @throws IOException */
	@RequestMapping(value="/img/upload", method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public void imgUpload(HttpServletRequest request,HttpServletResponse response,String callback) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String root = request.getSession().getServletContext().getRealPath("")+File.separator+IMG_UPLOAD_ROOT;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		List<MPicture> list = new ArrayList<>();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile multipartFile = entity.getValue();//保存原图
			String fileName = multipartFile.getOriginalFilename();//检查文件MIME_TYPE类型是否存在与白名单
			String ext = FileUtil.getFileExt(fileName).substring(1);
			if(ObjectHelper.isNotEmpty(IMG_MIME_TYPE)){
				String typeArray[] = IMG_MIME_TYPE.split(",");
				Boolean flag = false;
				for(String type : typeArray) if(ext.equals(type.toLowerCase())) flag = true;
				if(!flag){
					response.getWriter().write(callback(callback, getFailMap("服务器仅支持下类文件上传："+IMG_MIME_TYPE)));
					return ;
				}
			}
			if(!MimeType.filter(multipartFile.getBytes(), ext)){
				response.getWriter().write(callback(callback, getFailMap("服务器仅支持下类文件上传："+IMG_MIME_TYPE)));
				return ;
			}
			String ip = IPUtils.findRequestIP(request);
			String randomname = FileUtil.createRandomFileName(fileName);
			String filePath = FileUtil.createTimesDirs(root);
			File file = new File(filePath+"/"+randomname.toLowerCase());
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			String dataUrl = filePath.substring(filePath.indexOf(IMG_UPLOAD_ROOT));
			MPicture picture = new MPicture();
			picture.setDataName(randomname);
			picture.setUploadIP(ip);
			picture.setDataUrl(dataUrl);
			picture.setUrl(FILE_SERVER_HTTP + dataUrl +"/"+ randomname);
			picture.setStatus(false);
			BufferedImage image = ImageIO.read(file);//配置图片信息
			picture.setHeight(image.getHeight());
			picture.setWidth(image.getWidth());
			jedisClient.set(JEDIS_TMP_IMG,picture.getUrl(),JsonUtils.objectToJson(picture));
			jedisClient.expire(JEDIS_TMP_IMG,picture.getUrl(),600);//有效时间10分钟
			list.add(picture);
		}
		response.getWriter().write(callback(callback, getSuccessMap(list)));
	}

	@RequestMapping(value="/img/base64",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Map<String,Object> imgBase64(HttpServletRequest request,String base64File,String fileName) throws IOException {
		if(ObjectHelper.isEmpty(base64File))return getFailMap("请上传字节码");
		List<MPicture> list = new ArrayList<>();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes = decoder.decodeBuffer(base64File);//Base64解码
		for(int i=0;i<bytes.length;++i) {
			if(bytes[i]<0) bytes[i]+=256;
		}
		String root = request.getSession().getServletContext().getRealPath("")+File.separator+IMG_UPLOAD_ROOT;
		String ext = FileUtil.getFileExt(fileName).substring(1);
		if(ObjectHelper.isNotEmpty(IMG_MIME_TYPE)){
			String typeArray[] = IMG_MIME_TYPE.split(",");
			Boolean flag = false;
			for(String type : typeArray) if(ext.equals(type.toLowerCase())) flag = true;
			if(!flag)return getFailMap("服务器仅支持下类文件上传：" + IMG_MIME_TYPE);
		}
		if(!MimeType.filter(bytes, ext))return getFailMap("服务器仅支持下类文件上传："+IMG_MIME_TYPE);//检查文件头是否和MIME_TYPE一致
		String ip = IPUtils.findRequestIP(request);
		String randomname = FileUtil.createRandomFileName(fileName);
		String filePath = FileUtil.createTimesDirs(root);
		File file = new File(filePath+"/"+randomname.toLowerCase());
		FileCopyUtils.copy(bytes, file);
		String dataUrl = filePath.substring(filePath.indexOf(IMG_UPLOAD_ROOT));
		MPicture picture = new MPicture();
		picture.setDataName(randomname);
		picture.setUploadIP(ip);
		picture.setDataUrl(dataUrl);
		picture.setUrl(FILE_SERVER_HTTP + dataUrl +"/"+ randomname);
		picture.setStatus(false);
		BufferedImage image = ImageIO.read(file);//配置图片信息
		picture.setHeight(image.getHeight());
		picture.setWidth(image.getWidth());
		jedisClient.set(JEDIS_TMP_IMG,picture.getUrl(),JsonUtils.objectToJson(picture));
		jedisClient.expire(JEDIS_TMP_IMG,picture.getUrl(),600);//有效时间10分钟
		list.add(picture);
		return getSuccessMap(list);
	}

	/** 裁剪图片 */
	@RequestMapping(value="/upload/crop",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Map<String, Object> crop(HttpServletRequest request, HttpServletResponse response
			,String id,String ratios,Integer cut_x,Integer cut_y,Integer cut_w,Integer cut_h) {
		String jsonData = jedisClient.get(JEDIS_TMP_IMG,id);
		MPicture img = JsonUtils.jsonToPojo(jsonData, MPicture.class);
		if(ObjectHelper.isEmpty(img))return getFailMap("错误的图片信息！");
		String ip = IPUtils.findRequestIP(request);
		if(!ip.equals(img.getUploadIP()))return getFailMap("客户端验证失效，请重新上传原图！");//强制要求上传IP等于裁剪IP，可以起到一定防护作用
		String ratiosArray[] = null;
		if(ObjectHelper.isNotEmpty(ratios)){
			ratiosArray = ratios.split(",");//获取需要压缩的宽高比
		}
		if(ObjectHelper.isNotEmpty(ratiosArray)){
			for(String ratio : ratiosArray){
				try{
					String ratiox = ratio.split("\\*")[0];
					String ratioy = ratio.split("\\*")[1];
					if(ratiox.equals("MX")){
						//最长边固定比例压缩
						Integer width = Integer.valueOf(ratioy);
						if(ObjectHelper.isNotEmpty(ratioy)&&width>0){
							continue;
						}else{
							return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}
					}else if(ratiox.equals("MN")){
						//最短边固定比例压缩
						Integer width = Integer.valueOf(ratioy);
						if(ObjectHelper.isNotEmpty(ratioy)&&width>0){
							continue;
						}else{
							return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}
					}else if(ratiox.equals("X")){
						Integer height = Integer.valueOf(ratioy);
						if(ObjectHelper.isNotEmpty(height)&&height>0){
							if(cut_h<height&&cut_h!=0) return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}else{
							return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}
					}else if(ratioy.equals("X")){
						Integer width = Integer.valueOf(ratiox);
						if(ObjectHelper.isNotEmpty(width)&&width>0){
							if(cut_w<width&&cut_w!=0) return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}else{
							return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}
					}else{
						Integer width = Integer.valueOf(ratiox);
						Integer height = Integer.valueOf(ratioy);
						if(ObjectHelper.isNotEmpty(width)&&ObjectHelper.isNotEmpty(height)&&height>0&&width>0){
							if(cut_w<width&&cut_w!=0) return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
							if(cut_h<height&&cut_h!=0) return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}else{
							return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
						}
					}
				}catch (Exception e) {
					return getFailMap("要求压缩比例参数符合格式，并原图大小大于每个压缩后大小！");
				}
			}
		}
		String uploadRoot = request.getSession().getServletContext().getRealPath("");//系统根目录
		String srcImageFile = uploadRoot+"/"+img.getDataUrl()+"/"+img.getDataName();
		String imgName = img.getDataName().substring(0,img.getDataName().lastIndexOf("."));
		String path = img.getDataUrl();
		String ext = FileUtil.getFileExt(img.getDataName()).substring(1);
		String dirImageFile = uploadRoot+"/"+path+"/"+imgName+"_"+cut_w+"_"+cut_h+"."+ext;
		//定义返回值
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(cut_w+"*"+cut_h, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+cut_w+"_"+cut_h+"."+ext);

		BufferedImage image;
		try {
			if(cut_w!=0&&cut_h!=0){
				File dirFile = new File(dirImageFile);
				ImageCut.abscut(srcImageFile,dirImageFile,
						cut_x,cut_y,cut_w,cut_h);//图片裁剪
				image = ImageIO.read(dirFile);
			}else{
				dirImageFile = srcImageFile;
				File dirFile = new File(dirImageFile);
				image = ImageIO.read(dirFile);
			}
		} catch (IOException e) {
			return getFailMap("文件读取失败！");
		}
		//对裁剪后的图片压缩
		if(ObjectHelper.isNotEmpty(ratiosArray)){
			for(String ratio : ratiosArray){//开始压缩保存
				String ratiox = ratio.split("\\*")[0];
				String ratioy = ratio.split("\\*")[1];
				if(ratiox.equals("MX")){
					//最长边固定比例压缩
					if(image.getHeight()>image.getWidth()){
						Integer width = 0;
						Integer height = Integer.valueOf(ratioy);
						width = (int)(height/(double)image.getHeight()*(double)image.getWidth());
						String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext;
						ImageCut.scale(dirImageFile, dirImageFile2, width, height);
						data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
					}else{
						Integer width =  Integer.valueOf(ratioy);
						Integer height = 0;
						height = (int)(width/(double)image.getWidth()*(double)image.getHeight());
						String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext;
						ImageCut.scale(dirImageFile, dirImageFile2, width, height);
						data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
					}

				}else if(ratiox.equals("MN")){
					//最短边固定比例压缩
					if(image.getHeight()>image.getWidth()){
						Integer width =  Integer.valueOf(ratioy);
						Integer height = 0;
						height = (int)(width/(double)image.getWidth()*(double)image.getHeight());
						String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext;
						ImageCut.scale(dirImageFile, dirImageFile2, width, height);
						data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
					}else{
						Integer width = 0;
						Integer height = Integer.valueOf(ratioy);
						width = (int)(height/(double)image.getHeight()*(double)image.getWidth());
						String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext;
						ImageCut.scale(dirImageFile, dirImageFile2, width, height);
						data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
					}
				}else if(ratiox.equals("X")){
					Integer width = 0;
					Integer height = Integer.valueOf(ratioy);
					width = (int)(height/(double)image.getHeight()*(double)image.getWidth());
					String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext;
					ImageCut.scale(dirImageFile, dirImageFile2, width, height);
					data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
				}else if(ratioy.equals("X")){
					Integer width =  Integer.valueOf(ratiox);
					Integer height = 0;
					height = (int)(width/(double)image.getWidth()*(double)image.getHeight());
					String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext;
					ImageCut.scale(dirImageFile, dirImageFile2, width, height);
					data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
				}else{
					Integer width = Integer.valueOf(ratiox);
					Integer height = Integer.valueOf(ratioy);
					String dirImageFile2 = uploadRoot+"/"+path+"/"+imgName+"_"+width+"_"+height+"."+ext;
					ImageCut.scale(dirImageFile, dirImageFile2, width, height);
					data.put("W"+ratiox+"H"+ratioy, FILE_SERVER_HTTP+"/"+path+"/"+imgName+"_"+ratiox+"_"+ratioy+"."+ext);
				}

			}
		}
		return getSuccessMap(data);
	}
	/** kindeditor 的跨域上传*/
	@RequestMapping(value="/kindeditor/domain/uploadJson")
	public String domainUploadJson(HttpServletRequest request,HttpServletResponse response,String callBackPath) throws IOException {
		String resultUrl = "";
		if(!ServletFileUpload.isMultipartContent(request)) {
			resultUrl = "redirect:" + callBackPath + "?error=1&message=请上传文件";
		}
		String root = request.getSession().getServletContext().getRealPath("")+File.separator+IMG_UPLOAD_ROOT;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile multipartFile = entity.getValue();//保存原图
			String fileName = multipartFile.getOriginalFilename();//检查文件MIME_TYPE类型是否存在与白名单
			String ext = FileUtil.getFileExt(fileName).substring(1);
			if(ObjectHelper.isNotEmpty(IMG_MIME_TYPE)){
				String typeArray[] = IMG_MIME_TYPE.split(",");
				Boolean flag = false;
				for(String type : typeArray) if(ext.equals(type.toLowerCase())) flag = true;
				if(!flag) {
					resultUrl = "redirect:" + callBackPath + "?error=1&message=服务器仅支持下类文件上传："+IMG_MIME_TYPE;
				}
			}
			if(!MimeType.filter(multipartFile.getBytes(), ext)){
				resultUrl = "redirect:" + callBackPath + "?error=1&message=服务器仅支持下类文件上传："+IMG_MIME_TYPE;
			}
			String randomname = FileUtil.createRandomFileName(fileName);
			String filePath = FileUtil.createTimesDirs(root);
			File file = new File(filePath+"/"+randomname.toLowerCase());
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			String dataUrl = filePath.substring(filePath.indexOf(IMG_UPLOAD_ROOT));
			resultUrl = "redirect:" + callBackPath + "?error=0&url="+FILE_SERVER_HTTP + dataUrl +"/"+ randomname;
		}
		return resultUrl;
	}

	@RequestMapping(value="/kindeditor/uploadJson",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Map<String,Object> uploadJson(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("error", 1);
		if(!ServletFileUpload.isMultipartContent(request)) return map;
		String root = request.getSession().getServletContext().getRealPath("")+File.separator+IMG_UPLOAD_ROOT;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile multipartFile = entity.getValue();//保存原图
			String fileName = multipartFile.getOriginalFilename();//检查文件MIME_TYPE类型是否存在与白名单
			String ext = FileUtil.getFileExt(fileName).substring(1);
			if(ObjectHelper.isNotEmpty(IMG_MIME_TYPE)){
				String typeArray[] = IMG_MIME_TYPE.split(",");
				Boolean flag = false;
				for(String type : typeArray) if(ext.equals(type.toLowerCase())) flag = true;
				if(!flag) {
					map.put("url","服务器仅支持下类文件上传："+IMG_MIME_TYPE);
					return map;
				}
			}
			if(!MimeType.filter(multipartFile.getBytes(), ext)){
				map.put("url","服务器仅支持下类文件上传："+IMG_MIME_TYPE);
				return map;
			}
			String randomname = FileUtil.createRandomFileName(fileName);
			String filePath = FileUtil.createTimesDirs(root);
			File file = new File(filePath+"/"+randomname.toLowerCase());
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			String dataUrl = filePath.substring(filePath.indexOf(IMG_UPLOAD_ROOT));
			map.put("error", 0);
			map.put("url", FILE_SERVER_HTTP + dataUrl +"/"+ randomname);

		}
		return map;
	}
}
