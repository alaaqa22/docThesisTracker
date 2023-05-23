package dtt.global.tansport;

import java.util.Map;

/**
 * Represents the attachments files of a circulation
 */
public class Attachments {
    private String fileName; // The name of the attachment file
    private String fileType; // The type of the attachment file
    private byte[] fileContent; // The content of the attachment file stored as a byte array

    public Attachments() {}

    public Attachments(String fileName, String fileType, byte[] fileContent) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }
}
