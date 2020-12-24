package net.wenz.service.fs.controller;

import net.wenz.service.fs.model.dao.DuplicateDao;
import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.entity.FileDuplicate;
import net.wenz.service.fs.model.entity.FileEntity;
import net.wenz.service.fs.model.vo.BlockInfo;
import net.wenz.service.fs.model.vo.FileTreeNode;
import net.wenz.service.fs.service.DuplicateService;
import net.wenz.service.fs.service.FileService;
import net.wenz.service.fs.service.NodeService;
import net.wenz.service.fs.utils.JsonUtil;
import net.wenz.service.fs.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping(value = "node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    DuplicateDao duplicateDao;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public String list() {
        List<DataNode> nodes = nodeService.listNodes();
        Map<String, Object> ret = new HashMap<>();
        ret.put("ret", "success");
        ret.put("list", nodes);
        return JsonUtil.toJson(ret);
    }

    @RequestMapping(value = "/list2", method = {RequestMethod.GET})
    @ResponseBody
    public String list2() {
        List<DataNode> nodes = nodeService.listNodes();
        List<BlockInfo> blockInfoList = new LinkedList<>();
        //根据id找到duplicate里面的内容
        for (int i=0;i<nodes.size(); i++){
            BlockInfo blockInfo = new BlockInfo();
            DataNode dataNode = nodes.get(0);
            //FileDuplicate fileDuplicate = duplicateService.getFileDuplicateByMachineCode(dataNode.getMachineCode());
            //FileDuplicate fileDuplicate = duplicateDao.getFileDuplicateByMachineCode(dataNode.getMachineCode());
            //根据bid查找file_block的id
            //FileBlock fileBlock = fileService.getBlockById("7a5d683f4738498daa3acc025035a8c3");
            blockInfo.setId("7a5d683f4738498daa3acc025035a8c3");
            blockInfo.setBid("61a33731dcd2450392ac4769976ee778");
            blockInfo.setSeq(1);
            blockInfo.setSize(1000);
            blockInfo.setDataNode(dataNode);
            blockInfoList.add(blockInfo);
        }
        return JsonUtil.toJson(blockInfoList);
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
