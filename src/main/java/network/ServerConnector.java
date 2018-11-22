package network;

import model.DownloadRequestDTO;
import model.FileResponseDTO;
import model.UpdateInfoDTO;

import java.io.*;
import java.net.*;

/**
 * 서버와 통신을 담당하는 클래스
 * Update Info Tree 를 서버로 부터 다운로드 하는 기능과
 * DownloadRequestDTO 를 보내 서버로 부터 FileResponseDTO 를 다운로드 하는 기능이 있다.
 */
public class ServerConnector {

    private static final String REQUEST_UPDATE_INFO_TREE = "updateInfoDTO";
    private static final String REQUEST_DOWNLOAD_DTO = "downloadRequestDTO";

    private static final String NEW_LINE = "\n";

    private static final String MSG_TRY_CONNECT_SERVER = "connecting to update server ...";
    private static final String MSG_CONNECTED_SERVER = "connected update server";
    private static final String MSG_SEND_UPDATE_TREE_REQUEST = "send UpdateInfoDTO download request";
    private static final String MSG_DOWNLOAD_UPDATE_TREE = "download UpdateInfoDTO...";
    private static final String MSG_UPDATE_TREE_DOWNLOADED = "download UpdateInfoDTO complete";
    private static final String MSG_SEND_DOWNLOAD_REQUEST = "send DownloadRequest request";
    private static final String MSG_DOWNLOAD_FILE_RESPONSE = "download FileResponseDTO...";
    private static final String MSG_FILE_RESPONSE_DOWNLOADED = "download FileResponseDTO complete";
    private static final String MSG_DISCONNECTED_SERVER = "Disconnected from update server";

    private static final String ERR_SERVER_NOT_FOUND = "server not found. check IP or Network state";
    private static final String ERR_UPDATE_INFO_TREE_CLASS_NOT_FOUND = "UpdateInfoTree class not found. check Java class";
    private static final String ERR_CONNECTION_TIME_OUT = "server connection time out. check Network state";
    private static final String ERR_IO_FAILED = "I/O failed. check stack trace";
    private static final String ERR_STREAM_CLOSED_FAILED = "Stream close Failed! check stack trace";
    private static final String ERR_SERVER_IS_BUSY = "server is busy now";

    private static ServerConnector instance;
    private Socket socket;
    private BufferedWriter bw = null;
    private OutputStreamWriter osw = null;

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    /**
     * Single-tone 패턴을 위해서 생성자는 외부에서 호출하지 못하게 한다.
     */
    private ServerConnector() {
    }

    /**
     * Singloe-tone 패턴으로 구현, Instance 를 얻는 메소드
     *
     * @return ServerConnectUtil 의 Instance
     */
    public static synchronized ServerConnector getInstance() {

        if (instance == null) {

            instance = new ServerConnector();

        }

        return instance;
    }

    /**
     * Update Info DTO 를 서버로 부터 받아오는 메소드
     *
     * @param serverIP String 형태의 서버 IP
     * @param port     int 형태의 서버 port 번호
     * @param timeout  통신에 대한 timeout
     * @return UpdateInfoDTO 형태의 서버 에서 받아온 업데이트 정보
     */
    public UpdateInfoDTO getUpdateInfoDTO(String serverIP, int port, int timeout) {

        UpdateInfoDTO result = null;

        try {

            System.out.println(MSG_TRY_CONNECT_SERVER);

            SocketAddress target = new InetSocketAddress(serverIP, port);

            socket = new Socket();
            socket.setSoTimeout(timeout);
            socket.connect(target, timeout);

            osw = new OutputStreamWriter(socket.getOutputStream());
            bw = new BufferedWriter(osw);

            System.out.println(MSG_CONNECTED_SERVER);

            bw.write(REQUEST_UPDATE_INFO_TREE + NEW_LINE);
            bw.flush();

            System.out.println(MSG_SEND_UPDATE_TREE_REQUEST);

            ois = new ObjectInputStream(socket.getInputStream());

            System.out.println(MSG_DOWNLOAD_UPDATE_TREE);

            result = (UpdateInfoDTO) ois.readObject();

            System.out.println(MSG_UPDATE_TREE_DOWNLOADED);

        } catch (UnknownHostException e) {

            System.out.println(ERR_SERVER_NOT_FOUND);

        } catch (ClassCastException e) {

            System.out.println(ERR_SERVER_IS_BUSY);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            System.out.println(ERR_UPDATE_INFO_TREE_CLASS_NOT_FOUND);

        } catch (SocketTimeoutException e) {

            System.out.println(ERR_CONNECTION_TIME_OUT);

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);

        } finally {

            try {

                if (socket.isConnected()) {

                    System.out.println(MSG_DISCONNECTED_SERVER);

                }

                if (socket != null) {

                    socket.close();

                }

                if (osw != null) {

                    osw.close();

                }

                if (bw != null) {

                    bw.close();

                }

                if (ois != null) {

                    ois.close();

                }

                if (oos != null) {

                    oos.close();

                }

            } catch (IOException e) {

                e.printStackTrace();
                System.out.println(ERR_STREAM_CLOSED_FAILED);

            }
        }

        return result;
    }

    /**
     * DownloadRequestDTO 를 서버에 보낸 후
     * 서버에서 FileResponseDTO 를 다운로드 받는 메소드
     * FileResponseDTO 는 FileResponse List 를 가지고 있다.
     * FileResponse 에는 파일 이름, 경로, 해쉬값, 디렉토리 여부, 파일에 대한 바이너리 byte[] 가 있다.
     *
     * @param serverIP String 형태로 서버의 아이피를 받는다.
     * @param port     int 형태의 포트 번호로 서버의 포트 번호를 받는다.
     * @param timeout  int 형태의 타임아웃 으로 서버와 통신시 타임아웃을 지정 한다. 단위는 밀리세컨드(millisecond) 이다.
     * @param dto      DownloadRequestDTO 형태로 서버에 보낼 DownloadRequestDTO 를 받는다.
     * @return FileResponseDTO 형태로 서버에서 받은 FileResponseDTO 를 반환 한다.
     */
    public FileResponseDTO sendDownloadRequestDTOAndGetFileResponseDTO(String serverIP, int port, int timeout, DownloadRequestDTO dto) {

        FileResponseDTO result = null;

        try {

            System.out.println(MSG_TRY_CONNECT_SERVER);

            SocketAddress target = new InetSocketAddress(serverIP, port);

            socket = new Socket();
            socket.setSoTimeout(timeout);
            socket.connect(target, timeout);

            osw = new OutputStreamWriter(socket.getOutputStream());
            bw = new BufferedWriter(osw);

            System.out.println(MSG_CONNECTED_SERVER);

            bw.write(REQUEST_DOWNLOAD_DTO + NEW_LINE);
            bw.flush();

            System.out.println(MSG_SEND_DOWNLOAD_REQUEST);

            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println(MSG_DOWNLOAD_FILE_RESPONSE);

            oos.writeObject(dto);

            result = (FileResponseDTO) ois.readObject();

            System.out.println(MSG_FILE_RESPONSE_DOWNLOADED);

        } catch (UnknownHostException e) {

            System.out.println(ERR_SERVER_NOT_FOUND);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            System.out.println(ERR_UPDATE_INFO_TREE_CLASS_NOT_FOUND);

        } catch (SocketTimeoutException e) {

            System.out.println(ERR_CONNECTION_TIME_OUT);

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_IO_FAILED);

        } finally {

            try {

                if (socket.isConnected()) {

                    System.out.println(MSG_DISCONNECTED_SERVER);

                }

                if (socket != null) {

                    socket.close();

                }

                if (osw != null) {

                    osw.close();

                }

                if (ois != null) {

                    ois.close();

                }

                if (bw != null) {

                    bw.close();

                }

            } catch (IOException e) {

                e.printStackTrace();
                System.out.println(ERR_STREAM_CLOSED_FAILED);

            }
        }

        return result;
    }
}
