package model;

import java.io.Serializable;

/**
 * 파일에 대한 정보가 담겨저 있는 클래스
 * 파일 이름, 파일 경로, 파일 해쉬값, byte 형태의 바이너리, 디렉토리 여부 등이 담겨저 있다.
 */
public class FileResponse extends UpdateInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private byte[] data;

    /**
     * byte[] 형태의 바이너리 값 들을 반환하는 메소드
     *
     * @return byte[] 형태의 바이너리 값 들
     */
    public byte[] getData() {
        return data;
    }

    /**
     * byte[] 형태의 바이너리 값 들을 설정하는 메소드
     *
     * @param data byte[] 형태의 바이너리 값
     */
    public void setData(byte[] data) {
        this.data = data;
    }

}