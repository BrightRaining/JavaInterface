package com.pumpink.demo.utils.file;

import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.demo.utils.LoggerUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadFolderParth {

    public static void main(String[] args) {

        File file = new File("voice/test-无本退朝.m4a");
        String absolutePath = file.getAbsolutePath();
        String path = file.getPath();
        LoggerUtil.info(absolutePath);

    }


    /**
     * 返回文件的路径
     * @param fileName
     * @return
     */
    public static String filePathAbsoult(String fileName){

        //File file = new File("test-无本退朝.m4a");
        File file = new File(fileName);
        String path = file.getPath();

        String canonicalPath = null;
        try {
             canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return canonicalPath;
    }

    /**
     * 读取指定文件下的所有目录文件
     * @param dirctPath
     * @return
     */
    public static List<String> readFileFolderName(String dirctPath){

        List<String> list = new ArrayList<>();
        URL url = ReadFolderParth.class.getClassLoader().getResource(dirctPath);
        File file = new File(url.getPath());
        File[] files = file.listFiles();
        for (File filePath : files) {
            if(!filePath.isDirectory()){
                String name = filePath.getName();
                if(name.contains("m4a")||name.contains("mp3")||name.contains("mp4")){
                    list.add(dirctPath+"/"+name);
                }
            }
        }
        return list;
    }



}
