package pro.zackpollard.duplicateimagefinder;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zack on 24/04/2015.
 */
public class DuplicateManager {

    private final File appLocation;
    private final Main instance;

    private final List<Duplicate> duplicates;
    private final ConcurrentHashMap<String, Metadata> fileCache;

    private Duplicate currentDuplicate;

    public DuplicateManager(Main instance) {

        this.appLocation = new File("./");
        this.instance = instance;
        this.duplicates = new ArrayList<>();
        this.fileCache = new ConcurrentHashMap<>();
        cacheFiles(appLocation);
        findDuplicates();
        currentDuplicate = duplicates.get(0);
    }

    private void findDuplicates() {

        for(String path : fileCache.keySet()) {

            //System.out.println("path: " + path);

            Directory directory = fileCache.get(path).getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            File file = new File(path);

            fileCache.remove(path);

            if(directory != null) {

                for (String duplicatePath : fileCache.keySet()) {

                    File duplicateFile = new File(duplicatePath);

                    if(duplicateFile.length() != file.length()) {

                        continue;
                    }

                    //System.out.println("path2: " + duplicatePath);

                    Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    Directory duplicateDirectory = fileCache.get(duplicatePath).getFirstDirectoryOfType(ExifSubIFDDirectory.class);

                    if(duplicateDirectory != null) {

                        Date duplicateDate = duplicateDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

                        if(date != null && duplicateDate != null) {

                            if (date.equals(duplicateDate)) {

                                duplicates.add(new Duplicate(path, duplicatePath));
                                continue;
                            }
                        } else {

                            //System.out.println("Date was null!");
                            duplicates.add(new Duplicate(path, duplicatePath));
                        }
                    } else {

                        //System.out.println("Directory2 was null!");
                        duplicates.add(new Duplicate(path, duplicatePath));
                    }
                }
            } else {

                //System.out.println("Directory was null!");
            }
        }
    }

    private void cacheFiles(File root) {

        File[] faFiles = root.listFiles();

        for(File file: faFiles){

            if(file.getName().toLowerCase().endsWith(".jpg")) {

                try {

                    fileCache.put(file.getAbsolutePath(), ImageMetadataReader.readMetadata(file));
                } catch (ImageProcessingException | IOException e) {

                    //e.printStackTrace();
                }
            }

            if(file.isDirectory()){

                cacheFiles(file);
            }
        }
    }

    public Duplicate getNextDuplicate() {

        if(currentDuplicate != null) {

            duplicates.remove(currentDuplicate);
        }

        currentDuplicate = duplicates.get(0);

        return currentDuplicate;
    }

    public Duplicate getCurrentDuplicate() {

        return currentDuplicate;
    }
}