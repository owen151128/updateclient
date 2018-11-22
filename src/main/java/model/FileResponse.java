package model;

import java.io.Serializable;

/**
 * 파일에 대한 정보가 담겨저 있는 클래스
 * 파일 이름, 파일 경로, 파일 해쉬값, byte 형태의 바이너리, 디렉토리 여부 등이 담겨저 있다.
 */
public class FileResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String filePath;
    private String fileName;
    private String fileHash;
    private boolean isDirectory;

    private byte[] data;

    /**
     * 해당 파일의 경로를 반환하는 메소드
     *
     * @return String 형태의 해당 파일 경로
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 해당 파일의 경로를 설정하는 메소드
     *
     * @param filePath String 형태의 해당 파일 경로
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 해당 파일의 파일명을 반환하는 메소드
     *
     * @return String 형태의 해당 파일명
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 해당 파일의 파일명을 설정하는 메소드
     *
     * @param fileName String 형태의 해당 파일명
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 해당 파일의 Hash 값을 반환하는 메소드
     *
     * @return String 형태의 해당 Hash 값
     */
    public String getFileHash() {
        return fileHash;
    }

    /**
     * 해당 파일의 Hash 값을 설정하는 메소드
     *
     * @param fileHash String 형태의 해당 Hash 값
     */
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    /**
     * 해당 클래스가 담고있는 정보에 대해 디렉토리 여부를 반환하는 메소드
     *
     * @return boolean 형태의 값 디렉토리 일 경우 true, 파일 일 경우 false
     */
    public boolean isDirectory() {
        return isDirectory;
    }

    /**
     * 해당 클래스가 담고있는 정보에 대해 디렉토리 여부를 설정하는 메소드
     *
     * @param directory 디렉토리 일 경우 true 를 설정 하고, 아닐 경우 false 를 설정 한다.
     */
    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    /**
     * byte[] 형태의 바이너리 값 들을 반환하는 메소드
     *
     * @return byte[] 형태의 바이너리 값 들
     */
    public byte[] getData() {
        return data;
    }

    /**
     * byte[] 형태의 바이너리 값 들을 설정하는 메소드
     *
     * @param data byte[] 형태의 바이너리 값
     */
    public void setData(byte[] data) {
        this.data = data;
    }

}