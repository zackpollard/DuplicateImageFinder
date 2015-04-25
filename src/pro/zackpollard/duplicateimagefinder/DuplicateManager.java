package pro.zackpollard.duplicateimagefinder;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Zack on 24/04/2015.
 */
public class DuplicateManager {

    private final File appLocation;
    private final Main instance;

    private final Map<String, LinkedList<String>> fileCache;
    private final LinkedList<String> hashCache;

    private int currentDuplicate;

    public DuplicateManager(Main instance) {

        this.appLocation = new File("./");
        this.instance = instance;
        this.fileCache = new HashMap<>();
        this.hashCache = new LinkedList<>();
        findDuplicates(appLocation);
        currentDuplicate = 0;

        Map<Integer, Integer> duplicateAmounts = new HashMap<>();

        for(LinkedList<String> paths : fileCache.values()) {

            Integer amount = duplicateAmounts.get(paths.size());
            if(amount == null) {

                amount = 1;
            } else {

                ++amount;
            }

            duplicateAmounts.put(paths.size(), amount);
        }

        for(Map.Entry<Integer, Integer> amounts : duplicateAmounts.entrySet()) {

            System.out.println("Duplicate Amount: " + amounts.getKey() + " - Occurrences: " + amounts.getValue());
        }
    }

    private String generateHash(File file) {

        Directory directory = null;

        try {

            directory = ImageMetadataReader.readMetadata(file).getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        } catch (ImageProcessingException | IOException e) {
        }

        if(directory != null) {

            Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

            if(date != null) {

                return date.toString() + "-" + file.length();
            }
        }

        return null;
    }

    private void findDuplicates(File root) {

        File[] faFiles = root.listFiles();

        for(File file: faFiles){

            if(file.getName().toLowerCase().endsWith(".jpg")) {

                String hash = generateHash(file);

                LinkedList<String> paths = fileCache.get(hash);

                if(paths == null) {

                    paths = new LinkedList<>();
                    paths.add(file.getAbsolutePath());
                    fileCache.put(hash, paths);
                } else {

                    paths.add(file.getAbsolutePath());
                }

                if(!hashCache.contains(hash)) {

                    hashCache.add(hash);
                }
            }

            if(file.isDirectory()){

                findDuplicates(file);
            }
        }

        for(String hash : new HashSet<>(fileCache.keySet())) {

            if(fileCache.get(hash).size() <= 1) {

                fileCache.remove(hash);
                hashCache.remove(hash);
            }
        }
    }

    public LinkedList<String> getNextDuplicate() {

        if(++currentDuplicate < hashCache.size()) {

            return fileCache.get(hashCache.get(currentDuplicate));
        } else {

            return fileCache.get(hashCache.get(--currentDuplicate));
        }
    }

    public LinkedList<String> getPreviousDuplicate() {

        if(--currentDuplicate >= 0) {

            return fileCache.get(hashCache.get(currentDuplicate));
        } else {

            return fileCache.get(hashCache.get(++currentDuplicate));
        }
    }

    public LinkedList<String> getCurrentDuplicate() {

        return fileCache.get(hashCache.get(currentDuplicate));
    }

    public int getCurrentDuplicateID() {

        return currentDuplicate;
    }

    public int getTotalDuplicates() {

        return hashCache.size();
    }
}