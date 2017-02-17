package co.com.business.service.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.business.service.utils.Constantes;
import co.com.business.service.utils.FileInformation;

@RestController
@RequestMapping(value = "/api/v1.0")
public class RestUploadFile {
	
	@RequestMapping(value   = "/files",
				    method  = RequestMethod.POST,
				    headers = ("content-type=multipart/*"))
	@ResponseBody
	public ResponseEntity<FileInformation> uploadFile(@RequestParam("file") MultipartFile file){
		
		if (file.isEmpty()){
			return new ResponseEntity<FileInformation>(HttpStatus.NO_CONTENT);
		}
		
		if (file.getSize() > Constantes.MAX_UPLOAD_SIZE){
			return new ResponseEntity<FileInformation>(HttpStatus.INSUFFICIENT_STORAGE);
		}
		
		 HttpHeaders headers = new HttpHeaders();
		
		try {
			
			byte[] bytes = file.getBytes();
			
			String name = file.getOriginalFilename();
			
			FileInformation fileInfo = new FileInformation();
			
			fileInfo.setSize(file.getSize());
			fileInfo.setName(file.getOriginalFilename());
			fileInfo.setContentType(file.getContentType());
			
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
																				new File(Constantes.WINDOWS_FILE_FOLDER + name )));
			stream.write(bytes);
			stream.close();
			
			headers.add("File Uploaded Successfully - ", name);
			
			return new ResponseEntity<FileInformation>(fileInfo, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<FileInformation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}

	@RequestMapping(value   = "/multifiles",
				    method  = RequestMethod.POST,
				    headers = ("content-type=multipart/*"))
	@ResponseBody
	public ResponseEntity<List<FileInformation>> uploadFiles(@RequestParam("files") MultipartFile[] files){
			
		if (files == null || files.length == 0){
			return new ResponseEntity<List<FileInformation>>(HttpStatus.NO_CONTENT);
		}
		
		try {
			
			List<FileInformation> filesInfo = new ArrayList<FileInformation>();
			
			for (int i = 0; i < files.length; i++) {
				
				if (files[i].getSize() > Constantes.MAX_UPLOAD_SIZE){
					return new ResponseEntity<List<FileInformation>>(HttpStatus.INSUFFICIENT_STORAGE);
				}
				
				String fileName = files[i].getOriginalFilename();
				
				FileInformation fileInfo = new FileInformation();
				
				fileInfo.setSize(files[i].getSize());
				fileInfo.setName(fileName);
				fileInfo.setContentType(files[i].getContentType());
				
				byte[] bytes = files[i].getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
																					new File(Constantes.WINDOWS_FILE_FOLDER + fileName)));
				
				stream.write(bytes);
				stream.close();
				
				filesInfo.add(fileInfo);
			}
			
			return new ResponseEntity<List<FileInformation>>(filesInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<FileInformation>>(HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	
	}
	
}