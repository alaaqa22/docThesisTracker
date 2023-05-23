package dtt.global.tansport;

import java.util.Map;

public class Attachments {
   private String fileName ;
   private String fileType;
   private  byte[] fileContent;

   public Attachments(){}

    public Attachments (String fileName, String fileType, byte[] fileContent) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
    }

    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public void setFileType (String fileType) {
        this.fileType = fileType;
    }

    public void setFileContent (byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName () {
        return fileName;
    }

    public String getFileType () {
        return fileType;
    }

    public byte[] getFileContent () {
        return fileContent;
    }
}
