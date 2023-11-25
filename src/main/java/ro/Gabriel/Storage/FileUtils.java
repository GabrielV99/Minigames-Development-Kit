package ro.Gabriel.Storage;

import ro.Gabriel.Storage.DataStorage.Implementations.YmlDataStorage;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.BuildBattle.Main;

import java.io.IOException;
import java.io.File;

public class FileUtils {

    public static File getFile(String path, boolean resource) {
        try {
            String extension = FileUtils.getFileExtension(DataStorage.getMainStoragePath() + path);
            if(resource) {
                File file = new File(DataStorage.getMainStoragePath() + path + (!path.contains(extension != null ? extension : YmlDataStorage.getExtension()) ? "." + (extension != null ? extension : YmlDataStorage.getExtension()) : ""));
                if(!file.exists()) {
                    try {
                        Main.getInstance().saveResource(path + (!path.contains(extension != null ? extension : YmlDataStorage.getExtension()) ? "." + (extension != null ? extension : YmlDataStorage.getExtension()) : ""), true);
                    } catch (Exception e) {
                        return getFile(path, extension);
                    }
                }
                return file;
            }

            return getFile(path, extension);
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    private static File getFile(String path, String extension) throws IOException {
        if(extension != null) {
            File file = new File(DataStorage.getMainStoragePath() + path + (!path.contains(extension) ? "." + extension : ""));

            if(!file.exists()) {
                File parentDirectory = file.getParentFile();
                if (parentDirectory != null && !parentDirectory.exists()) {
                    parentDirectory.mkdirs();
                }
                file.createNewFile();
            }
            return file;
        }
        return null;
    }

    public static String getFileExtension(String path) {
        path = path.replace("/", File.separator);

        int indexOf = path.lastIndexOf('.');
        if(indexOf != -1) {// abc.yml
            return indexOf + 1 < path.length() ? path.substring(indexOf + 1) : null;
        }

        int endIndex = path.lastIndexOf(File.separator);

        File directoryFile = new File(path.substring(0, endIndex != -1 ? endIndex : path.length()));

        String fileName = path.substring(endIndex + 1);

        File[] files = directoryFile.listFiles((dir, name) -> {
            final int indexOfExtensionSeparator = name.lastIndexOf('.');
            return indexOfExtensionSeparator != -1 && name.substring(0, indexOfExtensionSeparator).equals(fileName);
        });

        if(files != null && files.length == 1) {
            return files[0].toString().substring(files[0].toString().indexOf('.') + 1);
        }

        return null;
    }

    public static void createFile(String path) {
        getFile(path, false);
    }
}