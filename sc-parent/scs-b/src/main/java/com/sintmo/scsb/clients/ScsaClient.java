package com.sintmo.scsb.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "scs-a", path = "/scsa")
public interface ScsaClient {

    @RequestMapping("/who")
    public String who(@RequestParam("name") String name);

    @RequestMapping(value = "/file/upload", method = {
            RequestMethod.POST }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestPart("file") MultipartFile file);

}
