package pro.zackpollard.duplicateimagefinder;

import java.io.File;

/**
 * @author Zack Pollard
 */
public class Utils {

    public static int getFilesCount(File file) {

        File[] files = file.listFiles();
        int count = 0;

        for (File f : files) {

            if (f.isDirectory()) {

                count += getFilesCount(f);
            } else {

                if(file.getName().toLowerCase().endsWith(".jpg")) {

                    ++count;
                }
            }
        }
        return count;
    }
}
