package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 서버의 파일을 요청할때 사용할 DTO
 * UpdateInfo 리스트를 가지고 있다.
 */
public class DownloadRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<UpdateInfo> list;

    /**
     * ArrayList 를 반환 하는 메소드 이다. UpdateInfo 형태의 서버에 요청할 파일들의 정보가 담겨저 있는 리스트 가 반환 된다.
     *
     * @return ArrayList 형태의 서버에 요청할 파일들의 정보가 담겨저 있는 리스트
     */
    public ArrayList<UpdateInfo> getList() {

        return list;
    }

    /**
     * 서버에 요청할 파일들의 정보가 담겨저 있는 리스트 를 설정하는 메소드
     *
     * @param list ArrayList 형태의 리스트
     */
    public void setList(ArrayList<UpdateInfo> list) {

        this.list = list;
    }
}
