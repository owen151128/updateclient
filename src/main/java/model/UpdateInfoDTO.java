package model;

import java.io.Serializable;

/**
 * 클라이언트에 보낼 데이터 클래스
 * UpdateInfoTree 의 root 와 client_path 를 가지고 있다.
 */
public class UpdateInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String client_path;
    private UpdateInfo root;

    /**
     * 기본 생성자로 클라이언트 저장 경로 인 client_path 와 업데이트 파일 정보 트리 루트 인 root 를 가지고 있다.
     *
     * @param client_path String 형태의 클라이언트에 저장될 파일들의 경로
     * @param root        UpdateInfo 형태의 업데이트 파일 정보 트리인 UpdateInfoTree 의 root
     */
    public UpdateInfoDTO(String client_path, UpdateInfo root) {
        this.client_path = client_path;
        this.root = root;
    }

    /**
     * 클라이언트에 저장될 파일들의 경로 를 얻는 메소드 이다.
     *
     * @return String 형태의 클라이언트에 저장될 파일들의 경로
     */
    public String getClient_path() {
        return client_path;
    }

    /**
     * 업데이트 파일 정보 트리인 UpdateInfoTree 의 root 를 반환 한다.
     *
     * @return UpdateInfo 형태의 업데이트 파일 정보 트리인 UpdateInfoTree 의 root
     */
    public UpdateInfo getRoot() {
        return root;
    }
}
