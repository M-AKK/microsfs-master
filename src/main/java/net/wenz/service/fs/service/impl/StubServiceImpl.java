package net.wenz.service.fs.service.impl;

import net.wenz.service.fs.config.ApplicationCache;
import net.wenz.service.fs.exception.DirectoryDontEmptyException;
import net.wenz.service.fs.exception.FileTreeNodeNullException;
import net.wenz.service.fs.exception.PathException;
import net.wenz.service.fs.model.dao.DataNodeDao;
import net.wenz.service.fs.model.dao.FileDao;
import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.entity.FileDuplicate;
import net.wenz.service.fs.model.vo.BlockInfo;
import net.wenz.service.fs.model.vo.FileTree;
import net.wenz.service.fs.model.vo.FileTreeNode;
import net.wenz.service.fs.service.FileService;
import net.wenz.service.fs.service.StubService;
import net.wenz.service.fs.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class StubServiceImpl implements StubService {

    @Autowired
    private FileService fileService;

    @Value("${blockSzie}")
    private long blockSzie;

    @Override
    public boolean put(String path, File file) throws IOException {

        List<BlockInfo> list = fileService.put(path, file.length());
        List<File> flist = FileUtil.splitFile(file, blockSzie);
        if (list.size() != flist.size())
            return false;

        for (int i=0; i<list.size(); i++) {

            BlockInfo _blockinfo = list.get(i);
            File _blockfile = flist.get(i);

            String url = String.format("http://%s:%d/fs/put", _blockinfo.getDataNode().getIp(), _blockinfo.getDataNode().getPort());

            RestTemplate restTemplate = new RestTemplate();

            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("multipart/form-data");
            headers.setContentType(type);

            //设置请求体，注意是LinkedMultiValueMap
            FileSystemResource fileSystemResource = new FileSystemResource(_blockfile);
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("file", fileSystemResource);
            form.add("blockid", _blockinfo.getId());

            //用HttpEntity封装整个请求报文
            HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

            String s = restTemplate.postForObject(url, files, String.class);
            System.out.println(s);

            fileService.ackput(path, _blockinfo.getBid(), _blockinfo.getId(), _blockinfo.getDataNode().getMachineCode());
        }

        return true;
    }

    @Override
    public File get(String path) throws IOException {

        FileTreeNode filenode = fileService.get(path);
        List<FileBlock> blockinfos = filenode.getFileEntity().getBlocks();

        List<File> blocks = new ArrayList<File>();
        for (FileBlock info : blockinfos) {
            FileDuplicate duplicate = info.getDuplicates().get(0);
            String url = String.format("http://%s:%d/fs/get?blockid=%s",
                    duplicate.getDataNode().getIp(),
                    duplicate.getDataNode().getPort(),
                    duplicate.getId());

            RestTemplate restTemplate = new RestTemplate();

            final String APPLICATION_STREAM = "application/octet-stream";
            HttpHeaders headers = new HttpHeaders();
            File temp = File.createTempFile("blocktmp-", ".blk");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                List list = new ArrayList<>();
                list.add(MediaType.valueOf(APPLICATION_STREAM));
                headers.setAccept(list);

                ResponseEntity<byte[]> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<byte[]>(headers),
                        byte[].class);
                byte[] result = response.getBody();
                inputStream = new ByteArrayInputStream(result);
                outputStream = new FileOutputStream(temp);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.flush();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            blocks.add(temp);
        }
        File ret = FileUtil.mergeFiles(blocks);
        return ret;
    }
}
