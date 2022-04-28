package Utility;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by SadiqMdAsif on 27/10/2016.
 */

public class CompressZip {
    private static final int BUFFER = 2048;

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;
        File dir = new File(Environment.getExternalStorageDirectory(), path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e("createDirIfNotExists: ", "Problem creating  folder");
                ret = false;
            }
        }
        return ret;
    }


    public void zip(@NonNull String[] _files, @NonNull String path, @NonNull String zipFileName) {
        try {

            //create target location folder if not exist
            createDirIfNotExists(path);
            File mZipFile = new File(path, zipFileName);
            if (mZipFile.exists()) {
                mZipFile.delete();
            } else {
                try {
                    mZipFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(path + File.separator + zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _files.length; i++) {
                Log.v("Compress", "Adding: " + _files[i]);
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unzipDB(String filePath, String TargetFolder) {
        try {
            ZipInputStream zipStream = new ZipInputStream(new FileInputStream(filePath));
            while (true) {
                ZipEntry nextEntry = zipStream.getNextEntry();
                ZipEntry zEntry = nextEntry;
                if (nextEntry == null) {
                    zipStream.close();
                    return;
                } else if (zEntry.isDirectory()) {
                    hanldeDirectory(TargetFolder + File.separator + zEntry.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(TargetFolder + File.separator + zEntry.getName());
                    BufferedOutputStream bufout = new BufferedOutputStream(fout);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int read = zipStream.read(buffer);
                        int read2 = read;
                        if (read == -1) {
                            break;
                        }
                        bufout.write(buffer, 0, read2);
                    }
                    zipStream.closeEntry();
                    bufout.close();
                    fout.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hanldeDirectory(String TargetFolder) {
        File f = new File(TargetFolder);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }

    public void unzip(@NonNull String _zipFile, @NonNull String _targetLocation) {

        //create target location folder if not exist
        createDirIfNotExists(_targetLocation);

        try {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {

                //create dir if required while unzipping
                if (ze.isDirectory()) {
                    createDirIfNotExists(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(Environment.getExternalStorageDirectory() + _targetLocation + File.separator + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
