package net.wenz.service.fs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static int BUFFER_SIZE = 1024;

    public static List<File> splitFile(File file, long blocksize) throws IOException {

        List<File> res = new ArrayList<File>();

        FileInputStream fis = new FileInputStream(file);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();

//        long average = fileSize / fileCount;//平均值
//        long bufferSize = BUFFER_SIZE; //缓存块大小，自行调整
//        ByteBuffer byteBuffer = ByteBuffer.allocate((int)bufferSize); // 申请一个缓存区
//
//        long startPosition = 0; //子文件开始位置
//        long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
//        for (int i = 0; i < fileCount; i++) {
//            if (i + 1 != fileCount) {
//                int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
//                readW:
//                while (read != -1) {
//                    byteBuffer.flip();//切换读模式
//                    byte[] array = byteBuffer.array();
//                    for (int j = 0; j < array.length; j++) {
//                        byte b = array[j];
//                        if (b == 10 || b == 13) { //判断\n\r
//                            endPosition += j;
//                            break readW;
//                        }
//                    }
//                    endPosition += bufferSize;
//                    byteBuffer.clear(); //重置缓存块指针
//                    read = inputChannel.read(byteBuffer, endPosition);
//                }
//            }else{
//                endPosition = fileSize; //最后一个文件直接指向文件末尾
//            }

        long startPosition = 0; //子文件开始位置
        long endPosition = fileSize < blocksize ? fileSize : blocksize; //子文件结束位置
        while (startPosition < fileSize) {
            File temp = File.createTempFile("blocktmp-", ".blk");
            FileOutputStream fos = new FileOutputStream(temp);
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
            outputChannel.close();
            fos.close();

            res.add(temp);

            startPosition = endPosition;
            endPosition = fileSize < (endPosition + blocksize) ? fileSize : (endPosition + blocksize);
        }
        inputChannel.close();
        fis.close();

        return res;
    }

    public static File mergeFiles(List<File> files) throws IOException {

        File resultFile = File.createTempFile("mergetmp-", ".tmp");

        FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();
        for (File file : files) {
            FileChannel blk = new FileInputStream(file).getChannel();
            resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
            blk.close();
        }
        resultFileChannel.close();
        return resultFile;
    }
}
