package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 파일 전송시 사용되는 클래스
 * <p>
 * FileResponse 리스트를 가지고 있다.
 */
public class FileResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<FileResponse> list;

    /**
     * FileResponse 리스트 를 반환 하는 메소드
     *
     * @return ArrayList 형태의 FileResponse 리스트
     */
    public ArrayList<FileResponse> getList() {
        return list;
    }

    /**
     * FileResponse 리스트 를 설정하는 메소드
     *
     * @param list ArrayList 형태의 FileResponse 리스트
     */
    public void setList(ArrayList<FileResponse> list) {
        this.list = list;
    }
}
