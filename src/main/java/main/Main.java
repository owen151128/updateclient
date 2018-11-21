package main;

import model.*;

import module.UpdateModule;

import java.util.ArrayList;

/**
 * update Client 의 Main 클래스 이다.
 * <p>
 * Main 메소드가 존재하는 클래스
 */
public class Main {

    /**
     * update Client 의 Main 메소드 이다.
     * 서버에 updateInfoTree 를 요청 한 후
     * 서버에서 받은 값으로 로컬 과 비교해서 업데이트 할 파일들을 요청하여
     * 다운로드 받는다.
     *
     * @param args String[] 형태의 클라이언트 시작시 매개변수가 저장되는 변수 이다.
     */
    public static void main(String[] args) {

        String serverIP;

        if (args.length == 0) {

            System.out.println(MainConstants.ERR_NO_SERVER_IP);

            return;

        } else {

            serverIP = args[0];

        }

        UpdateModule module = UpdateModule.getInstance();

        ArrayList<ArrayList<UpdateInfo>> result = module.checkUpdate(serverIP, MainConstants.PORT_NUMBER, MainConstants.TIME_OUT);

        module.deleteFiles(result.get(0));

        ArrayList<UpdateInfo> downloadList = result.get(1);

        if (!downloadList.isEmpty()) {

            module.updateFiles(serverIP, MainConstants.PORT_NUMBER, MainConstants.TIME_OUT, result.get(1));

        }

    }
}
