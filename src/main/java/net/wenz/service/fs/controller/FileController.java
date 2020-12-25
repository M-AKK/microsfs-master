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

    //文件目录树
    @RequestMapping(value = "/ls", method = {RequestMethod.GET})
    @ResponseBody
    public String ls(@RequestParam("path") String path) {
        //使用FileTreeNode来装最后的数据，其中又path
        //List<FileTreeNode> nodes = new ArrayList<FileTreeNode>();
        List<FileEntity> nodes = new ArrayList<FileEntity>();
        for (FileTreeNode child : fileService.ls(path)) {
            //child.setPath("/"+child.getFileEntity().getName());
            FileEntity fileEntity = child.getFileEntity();
            if(fileEntity.getParentId().equals("00000000000000001111111100000000")){
                fileEntity.setPath("/"+fileEntity.getName());
            }else{//这种是有多级目录的情况
                //根据parentid查找上一级的id
                FileEntity fileEntity1 = fileService.getFileById(fileEntity.getParentId());
                fileEntity.setPath("/"+fileEntity1.getName()+"/"+fileEntity.getName());
            }

            nodes.add(fileEntity);
        }

        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("data", nodes);
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

    //返回所有节点和信息
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public String get(@RequestParam("path") String path) {
        List<FileEntity> nodes = new ArrayList<FileEntity>();
        for (FileTreeNode child : fileService.ls(path)) {
            FileEntity fileEntity = child.getFileEntity();
            fileEntity.setPermissions("xwr--r---");
            fileEntity.setSize("1000");
            fileEntity.setOwn("root");
            fileEntity.setGroup("root");
            nodes.add(fileEntity);
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("data", nodes);
        return JsonUtil.toJson(ret);
    }
}
