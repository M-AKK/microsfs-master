package net.wenz.service.fs.controller;

import net.wenz.service.fs.model.entity.FileEntity;
import net.wenz.service.fs.model.vo.BlockInfo;
import net.wenz.service.fs.model.vo.FileTreeNode;
import net.wenz.service.fs.service.FileService;
import net.wenz.service.fs.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "fs")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ServletContext context;

    @RequestMapping(value = "/mkdir", method = {RequestMethod.POST})
    @ResponseBody
    public String mkdir(@RequestParam("path") String path, @RequestParam("name") String name) {
        fileService.mkdir(path, name);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/rmdir", method = {RequestMethod.DELETE})
    @ResponseBody
    public String rmdir(@RequestParam("path") String path) {
        fileService.rmdir(path);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/ls", method = {RequestMethod.GET})
    @ResponseBody
    public String ls(@RequestParam("path") String path) {
        List<FileEntity> nodes = new ArrayList<FileEntity>();
        for (FileTreeNode child : fileService.ls(path)) {
            nodes.add(child.getFileEntity());
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("list", nodes);
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/put", method = {RequestMethod.POST})
    @ResponseBody
    public String put(@RequestParam("path") String path, @RequestParam("size") long size) {
        List<BlockInfo> list = fileService.put(path, size);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("list", list);
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/ackput", method = {RequestMethod.PUT})
    @ResponseBody
    public String ackput(@RequestParam("path") String path, @RequestParam("bid") String bid, @RequestParam("id") String id, @RequestParam("mcode") String mcode) {
//        List<BlockInfo> ret = fileService.put(path, size);
        fileService.ackput(path, bid, id, mcode);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public String get(@RequestParam("path") String path) {
        List<FileEntity> nodes = new ArrayList<FileEntity>();
        for (FileTreeNode child : fileService.ls(path)) {
            nodes.add(child.getFileEntity());
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("list", nodes);
        return JsonUtil.toJson(ret);
    }
}
