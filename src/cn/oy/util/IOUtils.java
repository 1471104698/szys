package cn.oy.util;

import cn.oy.constant.IOConstant;

import java.io.*;

/**
 * @author 蒜头王八
 * @project: myapp
 * @Description:
 * @Date 2020/4/12 20:35
 */
public class IOUtils {

    //根据文件名获取字节流
    public static Closeable getIO(String fileName, String type){
        File file1 = new File(fileName);
        if(IOConstant.READER.name().equals(type)){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return reader;
        }else{
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return writer;
        }

    }

    public static void closeIO(Closeable... readers){
        for(Closeable io : readers){
            if(io != null){
                try {
                    io.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
