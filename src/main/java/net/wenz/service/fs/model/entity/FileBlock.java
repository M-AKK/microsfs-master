package net.wenz.service.fs.model.entity;

import net.wenz.service.fs.constant.FileType;

import java.util.Date;
import java.util.List;

public class FileBlock {
    private String id;
    private String fileId;
    private long sequence;
    private long size;

    private List<FileDuplicate> duplicates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<FileDuplicate> getDuplicates() {
        return duplicates;
    }

    public void setDuplicates(List<FileDuplicate> duplicates) {
        this.duplicates = duplicates;
    }
}
