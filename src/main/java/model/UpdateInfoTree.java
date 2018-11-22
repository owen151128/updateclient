package model;

import util.SHA256HashGenerator;

import java.io.File;
import java.util.ArrayList;

/**
 * UpdateInfoTree 클래스 는 UpdateInfo 라는 Node 를 가지는 Tree 자료구조 이다.
 * 해당 클래스는 UpdateInfoTree 의 root 를 가리킨다.
 * 클라이언트 에서 요청하는 정보 역시 서버에 대한 UpdateInfoTree 이다.
 * 클라이언트 에서 서버에 대한 UpdateInfoTree 와 비교 시에도 클라이언트에 대한 UpdateInfoTree 가 필요하다.
 */
public class UpdateInfoTree {

    private UpdateInfo root;

    /**
     * 기본 생성자로써 root 노드를 설정한다.
     * root 노드의 경로 즉, 최상위 경로는 client_path 가 된다.
     *
     * @param local_path root 노드의 경로가 될 String 형태의 경로
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
     * root 는 root 노드 를 필요로 한다.
     * prefixSize 는 local 경로의 길이를 필요로 한다.
     *
     * @param prefixSize int 형태의 local 경로에 대한 길이로 공통되지 않는 경로 부분을 자르기 위해서 사용 한다.
     * @param local      String 형태의 local 경로로 서버 에서는 업데이트 대상 파일들의 경로
     *                   클라이언트 에서는 업데이트 될 파일들이 저장 될 경로
     * @param root       root 노드를 필요로 한다.
     */
    public static void createUpdateInfoTree(int prefixSize, String local, UpdateInfo root) {

        File target = new File(local);
        File list[] = target.listFiles();
        ArrayList<UpdateInfo> childList = root.getChildList();

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

                    createUpdateInfoTree(prefixSize, f.getAbsolutePath(), newNode);

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

    /**
     * Hash 값을 비교해서 다운로드 요청할 파일과 삭제할 파일을 판별 해주는 메소드
     *
     * @param client           클라이언트의 UpdateInfoTree Root
     * @param server           서버의 UpdateInfoTree Root
     * @param downloadRequests ArrayList 형태의 다운로드 요청이 담길 리스트
     * @param deleteList       ArrayList 형태의 삭제 할 파일이 담길 리스트
     */
    public static void compareHash(UpdateInfo client, UpdateInfo server,
                                   ArrayList<UpdateInfo> downloadRequests,
                                   ArrayList<UpdateInfo> deleteList) {

        ArrayList<UpdateInfo> clientChild, serverChild;

        clientChild = client.getChildList();
        serverChild = server.getChildList();

        if (clientChild.isEmpty() && serverChild.isEmpty()) {

            return;

        }

        for (UpdateInfo c : clientChild) {

            boolean isExist = false;

            for (UpdateInfo s : serverChild) {

                if (c.equals(s)) {

                    isExist = true;
                    break;

                }
            }

            if (!isExist) {

                deleteList.add(c);

            }
        }

        for (UpdateInfo s : serverChild) {

            boolean isExist = false;

            for (UpdateInfo c : clientChild) {

                if (s.equals(c)) {

                    isExist = true;
                    break;

                }
            }

            if (!isExist) {

                downloadRequests.add(s);

            }
        }

        for (UpdateInfo c : clientChild) {


            for (UpdateInfo s : serverChild) {

                if (c.equals(s)) {
                    compareHash(c, s, downloadRequests, deleteList);
                }
            }
        }

    }

}
