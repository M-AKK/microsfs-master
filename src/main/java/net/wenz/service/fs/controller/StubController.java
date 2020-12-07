package net.wenz.service.fs.controller;

import net.wenz.service.fs.service.StubService;
import net.wenz.service.fs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "fs/stub")
public class StubController {

    @Autowired
    private StubService stubService;

    @Autowired
    private ServletContext context;

    @RequestMapping(value = "/put", method = {RequestMethod.POST})
    @ResponseBody
    public String put(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) throws
            IOException {

        File dest = File.createTempFile("upload-", ".tmp");//新生成文件的路径
        file.transferTo(dest);

        stubService.put(path, dest);

        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }


    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<FileSystemResource> get(@RequestParam("path") String path) throws IOException {

        File file = stubService.get(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

}
