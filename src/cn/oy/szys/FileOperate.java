package cn.oy.szys;

import cn.oy.constant.StringConstant;

import java.io.*;

/**
 * 对传入的答案 和 题目 文件进行批改处理
 */
public class FileOperate {//传式子跟答案进文件

    //将题目文件 和 答案文件 进行匹配校验，并写入 Grade 文件中
    public FileOperate()  {

    }

    /**
     * 生成 题目文件和 答案文件
     * @param exercises
     * @param answers
     * @param exercisesName
     * @param answersName
     * @param num
     */
    public void doCreate(String[] exercises, String[] answers, String exercisesName, String answersName, int num){
        try {
            //获取文件
            File file1 = new File(StringConstant.EXERCISES_PATH + exercisesName);
            BufferedWriter reader1 = new BufferedWriter(new FileWriter(file1));

            File file2 = new File(StringConstant.ANSWERS_PATH + answersName);
            BufferedWriter reader2 = new BufferedWriter(new FileWriter(file2));

            //将表达式写入到对应的文件中
            for (int i = 1; i <= num; i++) {
                reader1.write(i + "、" + exercises[i - 1] + "=" + "\n");
                reader1.flush();
                reader2.write(i + "、" + exercises[i - 1] + "=" + answers[i - 1] + "\n");
                reader2.flush();
            }
            //关闭流
            reader1.close();
            reader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 题目 和 答案进行校对，并将结果输入到 Finally 文件中
     * @param exercisesName
     * @param answersName
     * @throws IOException
     */
    public void proFile(String exercisesName, String answersName) throws IOException{
        //题目位置索引
        int idx = 0;
        //正确题数
        int correctCount = 0;
        //错误题数
        int wrongCount = 0;

        String str1 = "";
        String str2 = "";

        //存储正确题目的标号（题目数最多为 10000，从 0 开始计算，偏移量为 1）
        String[] correct = new String[10000];
        //存储错误题目的标号
        String[] wrong = new String[10000];

        //获取题目文件的序号
        int index = exercisesName.indexOf(".");
        char num = exercisesName.charAt(index - 1);
        //获取正确答案文件名
        String correctAnswerFileName =  StringConstant.ANSWERS_PREFIXX + num + ".txt";

        //读取正确答案文件
        File file1 = new File(StringConstant.ANSWERS_PATH + correctAnswerFileName);
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));

        //读取给定答案文件
        File file2 = new File(StringConstant.ANSWERS_PATH + answersName);
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        //将官方答案和给定答案进行匹配校验
        while ((str1 = reader1.readLine()) != null && (str2 = reader2.readLine()) != null) {
            idx++;
            if (str1.equals(str2) && !str1.equals("\n")) {
                correctCount++;
                correct[correctCount - 1] = String.valueOf(idx);
            } else {
                wrongCount++;
                wrong[wrongCount - 1] = String.valueOf(idx);
            }
        }


        //将结果输入到 Grade.txt 文件中
        File file3 = new File(StringConstant.FINALLY_PATH + "Finally.txt");
        BufferedWriter reader3 = new BufferedWriter(new FileWriter(file3));

        //输入正确的题号
        reader3.write("Correct:" + correctCount + "(");
        reader3.flush();

        for (int x = 0; x < correctCount; x++) {
            if (x != correctCount - 1)
                reader3.write(correct[x] + ",");
            else
                reader3.write(correct[x]);
            reader3.flush();
        }

        //输入错误的题号
        reader3.write(")" + "\n" + "Wrong:" + wrongCount + "(");
        reader3.flush();

        for (int x = 0; x < wrongCount; x++) {
            if (x != wrongCount - 1)
                reader3.write(wrong[x] + ",");
            else
                reader3.write(wrong[x]);
            reader3.flush();
        }
        reader3.write(")" + "\n");
        reader3.flush();
        //流的关闭
        reader3.close();
    }

}
