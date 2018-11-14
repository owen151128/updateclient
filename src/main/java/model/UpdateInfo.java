package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 파일에 대한 업데이트 정보를 가지고 있는 클래스
 * UpdateInfoTree 에 대한 Node 이다.
 */
public class UpdateInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String filePath;
    private String fileName;
    private String fileHash;
    private boolean isDirectory;
    private ArrayList<UpdateInfo> childList;

    /**
     * 기본 생성자로써 자식 리스트를 초기화 한다.
     */
    public UpdateInfo() {
        childList = new ArrayList<>();
    }

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
     * 해당 클래스의 자식 리스트를 얻는 메소드
     *
     * @return ArrayList 형태의 자식 리스트
     */
    public ArrayList<UpdateInfo> getChildList() {
        return childList;
    }
}
