package cn.oy.szys;

import cn.oy.constant.StringConstant;

import java.io.*;

/**
 * 辅助类，用于获取某类文件的所有文件名
 */
public class FileName {

    //用于存放题目的文件名
    String[] Exercises;
    //用于存放答案的文件名
    String[] Answers;

    //最后一个题目文件索引
    int i;
    //最后一个答案文件索引
    int j;

    /**
     * 利用构造方法初始化
     */
    public FileName() {
        //存储题目文件名
        this.Exercises = getListFile(StringConstant.EXERCISES_PREFIXX);
        this.i = Exercises.length - 1;

        //存储答案文件名
        this.Answers = getListFile(StringConstant.ANSWERS_PREFIXX);
        this.j = Answers.length - 1;
    }

    /**
     * 得到所有题目文件名
     */
    public void getExercisesName() {
        for (String str : getListFile(StringConstant.EXERCISES_PREFIXX)) {
            System.out.println(str);
        }

    }

    /**
     * 得到所有答案文件名
     */
    public void getAnswersName() {
        for (String str : getListFile(StringConstant.ANSWERS_PREFIXX)) {
            System.out.println(str);
        }
    }

    /**
     * 得到某个目录下的文件
     * @param str
     * @return
     */
    private String[] getListFile(String str){
        File file = new File(str);
        return file.list();
    }
}