package com.sintmo.scsb.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sintmo.scsb.clients.ScsaClient;

@RestController
@RequestMapping("/scsb")
public class ScsbController {

	@Autowired
	private ScsaClient scsaClient;

	@RequestMapping("/who")
	public String who(@RequestParam("name") String name) {
		return String.format("I'm scsb,are you %s?", name);
	}

	@RequestMapping("/scsa")
	public String scsa() {
		return scsaClient.who("scsb");
	}

	@RequestMapping("/transfer")
	public String buss(@RequestParam("filePath") String filePath) {
		MultipartFile multipartFile = null;
		try {
			Path path = Paths.get(filePath);
			FileItemFactory factory = new DiskFileItemFactory();
			FileItem fileItem = factory.createItem("file", Files.probeContentType(path), true,
					path.getFileName().toString());
			Streams.copy(new FileInputStream(path.toFile()), fileItem.getOutputStream(), true);

			multipartFile = new CommonsMultipartFile(fileItem);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scsaClient.fileUpload(multipartFile);
	}

	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		return scsaClient.fileUpload(file);
	}

}
