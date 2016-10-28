package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.base.database.trading.model.TradingPicFile;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.imageManage.service.ImageService;
import com.trading.service.ITradingPicFile;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}


			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			//String physicalPath = (String) conf.get("rootPath") + savePath;
            //todo 获取存放文件的路径
            ImageService imageService= (ImageService) ApplicationContextUtil.getBean(ImageService.class);

            String p= imageService.getImageUserDir();
            //String physicalPath = p + savePath;
            String physicalPath = p+"/"+request.getParameter("_sku")+savePath;

			InputStream is = fileStream.openStream();
            //文件MD5判断
            State storageState = null;
            ITradingPicFile iTradingPicFile= (ITradingPicFile) ApplicationContextUtil.getBean(ITradingPicFile.class);
            TradingPicFile tpf = null;
            String fileMd5="";
            List<InputStream> liin = null;
            try {
                liin = ObjectUtils.copyInputStream(is);
                is.close();
                BufferedImage sourceImg = ImageIO.read(liin.get(2));
                int h = sourceImg.getHeight();
                int w = sourceImg.getWidth();
                if (h<500 || w<500){
                    return new BaseState(false, "大小不能小于500px");
                }


                InputStream is1 = liin.get(0);
                fileMd5 = EncryptionUtil.md5ForFile(is1);
                is1.close();
                tpf = iTradingPicFile.selectByMd5id(fileMd5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(tpf!=null){//如果能够找到MD5文件，那么查询数据库中的URL
                File targetFile = new File(imageService.getImageDir()+"/"+physicalPath);
                storageState = new BaseState(true);
                storageState.putInfo( "size", targetFile.length() );
                storageState.putInfo( "title", targetFile.getName() );
                storageState.putInfo("url", tpf.getPicUrl());
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);

            }else{
                InputStream is2 = liin.get(1);
                storageState = StorageManager.saveFileByInputStream(is2,
                        physicalPath, maxSize);
                is2.close();
                if (storageState.isSuccess()) {
                    storageState.putInfo("url", PathFormat.format(physicalPath));
                    storageState.putInfo("type", suffix);
                    storageState.putInfo("original", originFileName + suffix);
                    tpf = new TradingPicFile();
                    tpf.setPicUrl(PathFormat.format(physicalPath));
                    tpf.setMd5Str(fileMd5);
                    iTradingPicFile.saveTradingPicFile(tpf);
                }
            }
            /*State storageState = StorageManager.saveFileByInputStream(is,
                    physicalPath, maxSize);
            is.close();
            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(physicalPath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }*/

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
