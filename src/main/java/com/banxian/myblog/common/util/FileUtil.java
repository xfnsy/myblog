package com.banxian.myblog.common.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件常见操作类，包括复制，删除
 *
 * @author wangpeng
 * @since 2021-12-16 13:32:17
 */
public class FileUtil {

    public static void main(String[] args) throws Exception {
        File file = new File("D:/cc.pdf");
        System.out.println(getExtName(file));
        System.out.println(getMimeType(file));
        copyFile(file, "d:/test/test3/b.pdf");
        delFiles("d:/test");
    }

    /**
     * 创建父级目录
     *
     * @param file 文件
     */
    public static void tryMkParentDirs(File file) {
        File parentFile = file.getParentFile();
        parentFile.mkdirs();
    }


    public static void tryMkParentDirs(String filePath) {
        tryMkParentDirs(new File(filePath));
    }

    public static void copyFile(String srcPath, String targetPath) {
        try {
            tryMkParentDirs(targetPath);
            Files.copy(Paths.get(srcPath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 复制文件
     */
    public static void copyFile(File srcFile, File targetFile) {
        if (srcFile == null || targetFile == null) {
            System.out.println("文件为空不能复制");
            return;
        }
        copyFile(srcFile.getAbsolutePath(), targetFile.getAbsolutePath());
    }

    public static void copyFile(File srcFile, String targetPath) {
        if (srcFile == null) {
            System.out.println("文件为空不能复制");
            return;
        }
        copyFile(srcFile.getAbsolutePath(), targetPath);
    }

    /**
     * 获取文件mime类型
     */
    public static String getMimeType(String path) {
        try {
            return Files.probeContentType(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMimeType(File file) {
        return getMimeType(file.getAbsolutePath());
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件
     * @return 文件扩展名 到.的如 .jgp .pdf
     */
    public static String getExtName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getExtName(File file) {
        return getExtName(file.getName());
    }

    /**
     * 删除文件或目录，目录下若有文件一并删除
     *
     * @param file 文件
     */
    public static void delFiles(File file) {
        try {
            if (file == null || !file.exists()) return;
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File value : files) {
                        delFiles(value);
                    }
                }
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void delFiles(String filePath) {
        delFiles(new File(filePath));
    }


}
