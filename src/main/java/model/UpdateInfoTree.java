package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * UpdateInfoTree 클래스 는 UpdateInfo 라는 Node 를 가지는 Tree 자료구조 이다.
 * 또한 해당 클래스는 UpdateInfoTree 의 root 이기도 하다.
 * 클라이언트 에서 요청하는 정보 역시 서버에 대한 UpdateInfoTree 이다.
 * 클라이언트 에서 서버에 대한 UpdateInfoTree 와 비교 시에도 클라이언트에 대한 UpdateInfoTree 가 필요하다.
 * 해당 클래스에 객체를 추가 하려면 UpdateInfoTreeWrapper 를 이용 한다.
 */
public class UpdateInfoTree implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientPath;
    private ArrayList<UpdateInfo> childList;

    /**
     * 기본 생성자로써 자식 리스트를 초기화 한다.
     */
    public UpdateInfoTree() {
        childList = new ArrayList<>();
    }

    /**
     * 클라이언트의 저장될 경로를 설정하는 메소드
     * @param clientPath String 형태의 클라이언트 저장 경로
     */
    public void setClientPath(String clientPath) {
        this.clientPath = clientPath;
    }

    /**
     * 클라이언트의 저장될 경로를 반환하는 메소드
     * @return String 형태의 클라이언트의 저장될 경로
     */
    public String getClientPath() {
        return clientPath;
    }

    /**
     * 자식 리스트를 반환하는 메소드
     * @return ArrayList 형태의 자식 리스트
     */
    public ArrayList<UpdateInfo> getChildList() {
        return childList;
    }

}
