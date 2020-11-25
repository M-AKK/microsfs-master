package net.wenz.service.fs.controller;

import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.model.entity.FileEntity;
import net.wenz.service.fs.model.vo.FileTreeNode;
import net.wenz.service.fs.service.FileService;
import net.wenz.service.fs.service.NodeService;
import net.wenz.service.fs.utils.JsonUtil;
import net.wenz.service.fs.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "api/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public String list() {
        List<DataNode> nodes = nodeService.listNodes();
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("list", nodes);
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public String get(@RequestParam("mcode") String mcode) {
        DataNode node = nodeService.getNode(mcode);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("data", node);
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ResponseBody
    public String register(HttpServletRequest request) {
        String mcode = request.getParameter("mcode");
        String ip = RequestUtil.getIpAddr(request);
        long port = Long.parseLong(request.getParameter("port"));

        DataNode node = nodeService.getNode(mcode);
        if (node == null) {
            nodeService.register(mcode, ip, port);
        } else {
            if ( !(ip.equals(node.getIp()) && port == node.getPort()) ) {
                nodeService.updateAddress(mcode, ip, port);
            }
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/unregister", method = {RequestMethod.DELETE})
    @ResponseBody
    public String unregister(@RequestParam("mcode") String mcode) {
        nodeService.unregister(mcode);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/active", method = {RequestMethod.PUT})
    @ResponseBody
    public String active(@RequestParam("mcode") String mcode) {
        nodeService.active(mcode);
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        return JsonUtil.toJson(ret);
    }
}
