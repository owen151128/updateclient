package model;

import util.SHA256HashGenerator;

import java.io.File;
import java.util.ArrayList;

/**
 * UpdateInfoTree 자료구조를 사용하는데 필요한 메소드들이 모여 있는 Wrapper 클래스
 */
public class UpdateInfoTreeWrapper {

    /**
     * UpdateInfoTree 를 생성하는 메소드
     * 메소드 실행시 local 경로 안을 순회 하며 UpdateInfoTree 를 만든다.
     * childList 는 root 의 자식 리스트를 필요로 한다.
     * prefixSize 는 local 경로의 길이를 필요로 한다.
     *
     * @param prefixSize int 형태의 local 경로에 대한 길이로 공통되지 않는 경로 부분을 자르기 위해서 사용 한다.
     * @param local String 형태의 local 경로로 서버 에서는 업데이트 대상 파일들의 경로
     *              클라이언트 에서는 업데이트 될 파일들이 저장 될 경로
     * @param childList ArrayList 형태의 UpdateInfoTree root 의 자식 리스트
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

    public static void printAll(ArrayList<UpdateInfo> childList) {

        if(childList.isEmpty()) {
            return;
        }

        for(UpdateInfo n : childList) {
            System.out.println(n.getFilePath());
            System.out.println(n.getFilePath());
            System.out.println(n.getFileName());
            System.out.println("dir : " + n.isDirectory());
            if(!n.isDirectory()) {
                System.out.println(n.getFileHash());
            }
            System.out.println("\n\n");
            printAll(n.getChildList());
        }
    }
}
