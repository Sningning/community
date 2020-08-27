package sningning.community;

import java.io.IOException;

/**
 * @author: Song Ningning
 * @date: 2020-08-27 22:36
 */
public class WkTests {

    public static void main(String[] args) {
        String path = "D:/Program Files/wkhtmltopdf/bin/";
        String cmd = "wkhtmltoimage --quality 75 https://www.nowcoder.com D:/IDEA_projects/community_info/wk-image/2.png";
        try {
            Runtime.getRuntime().exec(path + cmd);
            System.out.println("OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
