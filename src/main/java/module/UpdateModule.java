package module;

import model.*;
import network.ServerConnector;
import util.FileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 업데이트 에 관한 전반적인 기능들을 담당하는 클래스 이다.
 */
public class UpdateModule {

    private static final String ERR_DOWNLOAD_DTO_FAILED = "download UpdateInfoTree failed!";
    private static final String ERR_RESPONSE_DTO_FAILED = "download FileResponseDTO failed!";
    private static final String MSG_POINT = ".";

    private static UpdateModule instance;
    private ServerConnector serverConnector;
    private String local_prefix_path;

    /**
     * 기본 생성자로 Single-Tone 패턴 적용을 위해 private 처리
     */
    private UpdateModule() {
        serverConnector = ServerConnector.getInstance();
    }

    /**
     * Single-Tone 패턴 적용시 사용하는 메소드
     * @return UpdateModule Instance
     */
    public static synchronized UpdateModule getInstance() {

        if (instance == null) {

            instance = new UpdateModule();

        }

        return instance;
    }

    /**
     * 서버로 부터 업데이트 정보 를 받아 업데이트 를 체크하는 메소드
     * @param serverIP String 형태 의 서버 아이피
     * @param portNumber int 형태 의 서버 포트 번호
     * @param timeout int 형태 의 Time out 으로 밀리세컨드(millisecond) 단위로 나타냄
     * @return ArrayList 형태의 결과로 인덱스0 에는 deleteList, 인덱스1 에는 downloadList 가 들어 있다.
     */
    public ArrayList<ArrayList<UpdateInfo>> checkUpdate(String serverIP, int portNumber, int timeout) {

        ArrayList<ArrayList<UpdateInfo>> resultList = new ArrayList<>();
        UpdateInfoDTO dto = serverConnector.getUpdateInfoDTO(serverIP, portNumber, timeout);

        if (dto == null) {

            System.out.println(ERR_DOWNLOAD_DTO_FAILED);

            return null;
        }

        local_prefix_path = new File(MSG_POINT + File.separator + dto.getClient_path()).getAbsolutePath();

        UpdateInfo updateInfo = dto.getRoot();

        UpdateInfoTree tree = new UpdateInfoTree(local_prefix_path);

        UpdateInfo clientInfo = tree.getRoot();

        UpdateInfoTree.createUpdateInfoTree(local_prefix_path.length(), local_prefix_path, clientInfo);

        ArrayList<UpdateInfo> downloadList = new ArrayList<>();
        ArrayList<UpdateInfo> deleteList = new ArrayList<>();

        UpdateInfoTree.compareHash(clientInfo, updateInfo, downloadList, deleteList);

        resultList.add(deleteList);
        resultList.add(downloadList);

        return resultList;
    }

    /**
     * deleteList 를 입력 받아 파일을 지우는 메소드 이다.
     * @param deleteList ArrayList 형태의 deleteList
     */
    public void deleteFiles(ArrayList<UpdateInfo> deleteList) {

        for (UpdateInfo u : deleteList) {
            FileUtil.deleteFile(local_prefix_path + u.getFilePath());
        }

    }

    /**
     * downloadList 를 입력 받아 파일을 다운로드 하는 메소드 이다.
     * @param serverIP String 형태 의 서버 아이피
     * @param portNumber int 형태 의 서버 포트 번호
     * @param timeout int 형태 의 Time out 으로 밀리세컨드(millisecond) 단위로 나타냄
     * @param updateInfos ArrayList 형태의 downloadList 이다.
     */
    public void updateFiles(String serverIP, int portNumber, int timeout, ArrayList<UpdateInfo> updateInfos) {

        DownloadRequestDTO downloadRequestDTO = new DownloadRequestDTO();

        downloadRequestDTO.setList(updateInfos);

        FileResponseDTO responseDTO = serverConnector.sendDownloadRequestDTOAndGetFileResponseDTO(serverIP,
                portNumber,
                timeout,
                downloadRequestDTO);

        if (responseDTO == null) {

            System.out.println(ERR_RESPONSE_DTO_FAILED);

            return;
        }

        for (FileResponse r : responseDTO.getList()) {

            String target = new File(local_prefix_path + File.separator + r.getFilePath()).getAbsolutePath();

            FileUtil.writeFile(r, local_prefix_path);

            FileUtil.compareFile(r, target);
        }

    }
}
