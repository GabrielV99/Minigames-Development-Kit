package ro.Gabriel.Storage.DataStorage;

import java.io.FilenameFilter;
import java.io.File;

public class FileNameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        return dir.getName().equals(name);
    }
}