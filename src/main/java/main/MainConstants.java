package main;

/**
 * Main 클래스에서 사용할 상수들을 모아놓은 클래스
 * <p>
 * PORT_NUMBER : 연결할 서버에 포트 번호 이다.
 * TIME_OUT : 서버와 연결시 Time out 을 나타 낸다.
 * ERR_NO_SERVER_IP : 서버 아이피를 매개변수로 넘기지 않았을 경우 출력 되는 에러 메시지 이다.
 */
public class MainConstants {

    static final int PORT_NUMBER = 8080;
    static final int TIME_OUT = 10;

    static final String ERR_NO_SERVER_IP = "update Client is required Server IP!";
}
