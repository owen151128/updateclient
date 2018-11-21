package util;

import model.FileResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 파일 I/O 관련 처리를 담당하는 클래스
 */
public class FileUtil {

    private static final String ERR_FILE_CREATE_FAILED = " file create failed. check stack trace ";
    private static final String ERR_FILE_WRITE_FAILED = " file write failed. check stack trace ";

    private static final String MSG_SUB_DIR_CREATE = "subdirectory created!";
    private static final String MSG_CREATE_FILE = "create File : ";
    private static final String MSG_SUB_DIR_EXIST = "subdirectory exist! : ";
    private static final String MSG_FILE_EXIST = "file exist!";
    private static final String MSG_FILE_DOWNLOADED = "File Downloaded : ";
    private static final String MSG_SUB_FILE = " sub file ";
    private static final String MSG_DELETE = "Delete : ";
    private static final String MSG_DELETE_FAILED = " delete failed.";
    private static final String MSG_INTEGRITY_CLEAR = " Integrity clear";
    private static final String MSG_INTEGRITY_PROBLEM = " Integrity problem";

    /**
     * 파일을 쓰는 기능을 담당 한다. FileResponse 에 정보를 기반하여 쓰기를 실행한다.
     *
     * @param response        FileResponse 형태의 response 로 FileResponse 정보를 사용해 쓰기 를 실행 한다.
     * @param local_file_path String 형태의 로컬 파일 경로로 root 디렉토리의 경로가 된다.
     */
    public static void writeFile(FileResponse response, String local_file_path) {

        File target = new File(local_file_path + response.getFilePath());
        File targetParent = new File(target.getParent());

        if (targetParent.mkdirs()) {

            System.out.println(MSG_SUB_DIR_CREATE);

        }

        if (response.isDirectory()) {

            if (target.mkdir()) {

                System.out.println(MSG_CREATE_FILE + target.getAbsolutePath());

            } else {

                System.out.println(MSG_SUB_DIR_EXIST + target.getAbsolutePath());

            }

        } else {

            try {

                if (target.createNewFile()) {

                    System.out.println(MSG_CREATE_FILE + target.getAbsolutePath());

                } else {

                    System.out.println(MSG_FILE_EXIST + target.getAbsolutePath());

                }

            } catch (IOException e) {

                e.printStackTrace();
                System.out.println(response.getFilePath() + ERR_FILE_CREATE_FAILED);

            }
        }

        try {
            if (!response.isDirectory()) {

                Files.write(target.toPath(), response.getData());
                System.out.println(MSG_FILE_DOWNLOADED + target.getAbsolutePath());

            }
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(response.getFilePath() + ERR_FILE_WRITE_FAILED);

        }
    }

    /**
     * 파일을 삭제하는 메소드 로 path 경로 파일을 삭제한다.
     *
     * @param path String 형태의 경로로 삭제할 파일의 경로를 나타낸다.
     */
    public static void deleteFile(String path) {

        File target = new File(path);

        if (target.isDirectory()) {

            File[] list = target.listFiles();

            if (list != null) {

                for (File f : list) {

                    if (f.isDirectory()) {

                        deleteFile(f.getAbsolutePath());

                    } else {

                        if (f.delete()) {

                            System.out.println(MSG_DELETE + " : " + target.getAbsolutePath() + File.separator + f.getName());

                        } else {

                            System.out.println(MSG_DELETE_FAILED + " : " + target.getAbsolutePath() + File.separator + f.getName());

                        }
                    }

                }
            }
        }

        if (target.delete()) {

            System.out.println(MSG_DELETE + " : " + target.getAbsolutePath());

        } else {

            System.out.println(MSG_DELETE_FAILED + " : " + target.getAbsolutePath());

        }
    }

    /**
     * 파일을 비교하는 메소드로 FileResponse hash 값과 target 파일의 hash 값을 비교한다.
     *
     * @param response FileResponse 형태로 해당 response에 hash 값을 사용한다.
     * @param target   String 형태의 파일 경로를 나타내는 target 으로 비교할 파일의 경로를 나타낸다.
     */
    public static void compareFile(FileResponse response, String target) {

        if (!response.isDirectory()) {

            if (response.getFileHash().equals(SHA256HashGenerator.getHash(target))) {

                System.out.println(target + MSG_INTEGRITY_CLEAR);

            } else {

                System.out.println(MSG_INTEGRITY_PROBLEM);

            }

        }

    }
}
