package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 해시 알고리즘을 사용하여 해시값을 만들어주는 클래스
 */
public class SHA256HashGenerator {
    private static final String SHA_256_ALGORITHM = "SHA-256";

    private static final String ERR_MSG_NOT_FOUND_ALGORITHM = "SHA-256 Algorithm not supported";
    private static final String ERR_MSG_NO_SEARCH_FILE = "File not found. check file path";

    /**
     * filePath 에 있는 파일에 대한 SHA256 해시를 반환하는 메소드
     *
     * @param filePath 파일 경로
     * @return 파일 데이터에 대한 SHA256 해시화 값
     */
    public static String getHash(String filePath) {
        MessageDigest sha;
        StringBuilder result;
        byte[] data;

        try {

            sha = MessageDigest.getInstance(SHA_256_ALGORITHM);
            sha.update(Files.readAllBytes(new File(filePath).toPath()));

            data = sha.digest();

            result = new StringBuilder();

            for (byte b : data) {
                result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));   //  16진수를 얻는 과정
            }

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            System.out.println(ERR_MSG_NOT_FOUND_ALGORITHM);

            return "";

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(ERR_MSG_NO_SEARCH_FILE);

            return "";
        }

        return result.toString();
    }
}
