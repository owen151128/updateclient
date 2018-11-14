package model;

import util.SHA256HashGenerator;

import java.io.File;
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
    private UpdateInfo root;

    /**
     * 기본 생성자로써 root 노드를 설정한다.
     */
    public UpdateInfoTree(String local_path) {
        File rootFile = new File(local_path);
        root = new UpdateInfo();

        root.setDirectory(true);
        root.setFileName(rootFile.getName());
        root.setFilePath(rootFile.getAbsolutePath());
    }

    /**
     * root 노드를 반환하는 메소드
     *
     * @return UpdateInfo 형태의 root 노드
     */
    public UpdateInfo getRoot() {
        return root;
    }

    /**
     * UpdateInfoTree 를 생성하는 메소드
     * 메소드 실행시 local 경로 안을 순회 하며 UpdateInfoTree 를 만든다.
     * childList 는 root 의 자식 리스트를 필요로 한다.
     * prefixSize 는 local 경로의 길이를 필요로 한다.
     *
     * @param prefixSize int 형태의 local 경로에 대한 길이로 공통되지 않는 경로 부분을 자르기 위해서 사용 한다.
     * @param local      String 형태의 local 경로로 서버 에서는 업데이트 대상 파일들의 경로
     *                   클라이언트 에서는 업데이트 될 파일들이 저장 될 경로
     * @param childList  ArrayList 형태의 UpdateInfoTree root 의 자식 리스트
     */
    public static void createUpdateInfoTree(int prefixSize, String local, ArrayList<UpdateInfo> childList) {

        File target = new File(local);
        File list[] = target.listFiles();

        UpdateInfo newNode;

        if (list != null) {
            for (File f : list) {

                if (f.getName().substring(0, 1).equals(".")) {
                    continue;
                }

                if (f.isDirectory()) {

                    newNode = new UpdateInfo();

                    newNode.setFilePath(f.getAbsolutePath().substring(prefixSize));
                    newNode.setFileName(f.getName());
                    newNode.setDirectory(true);

                    childList.add(newNode);

                    createUpdateInfoTree(prefixSize, f.getAbsolutePath(), newNode.getChildList());
                } else {

                    newNode = new UpdateInfo();

                    newNode.setFilePath(f.getAbsolutePath().substring(prefixSize));
                    newNode.setFileName(f.getName());
                    newNode.setDirectory(false);
                    newNode.setFileHash(SHA256HashGenerator.getHash(f.getAbsoluteFile().toString()));

                    childList.add(newNode);
                }
            }
        }

    }
}
