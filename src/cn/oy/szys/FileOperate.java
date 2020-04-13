package cn.oy.szys;

import cn.oy.util.IOUtils;
import cn.oy.constant.IOConstant;
import cn.oy.constant.StringConstant;

import java.io.*;
import java.util.*;

/**
 * 对传入的答案 和 题目 文件进行批改处理
 */
public class FileOperate {//传式子跟答案进文件

    String suffix = StringConstant.FILE_SUFFIX;

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
        String  line = "\n";
        try {
            //获取题目和答案字节流

            BufferedWriter writer1 = (BufferedWriter) IOUtils.getIO(StringConstant.EXERCISES_PATH + exercisesName, IOConstant.WRITER.name());

            BufferedWriter writer2 = (BufferedWriter) IOUtils.getIO(StringConstant.ANSWERS_PATH + answersName, IOConstant.WRITER.name());

            //将表达式写入到对应的文件中
            for (int i = 1; i <= num; i++) {
                writer1.write(i + "、" + exercises[i - 1] + "=" + line);
                writer1.flush();
                writer2.write(i + "、" + exercises[i - 1] + "=" + answers[i - 1] + line);
                writer2.flush();
            }
            //关闭流
            IOUtils.closeIO(writer1, writer2);
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
    public void proFile(String exercisesName, String answersName, int questions) throws IOException{
        //题目序号
        int idx = 1;
        //正确题数
        int correctCount = 0;
        //错误题数
        int wrongCount = 0;

        if(questions <= 0){
            questions = 10000;
        }
        //存储正确题目的标号（从 0 开始计算，偏移量为 1）
        String[] corrects = new String[questions];
        //存储错误题目的标号
        String[] wrongs = new String[questions];

        //获取题目文件的序号
        int index = exercisesName.indexOf(".");
        char num = exercisesName.charAt(index - 1);

        //获取官方 Answers.txt 路径 + 文件名
        String officialAnswerFile =  StringConstant.ANSWERS_PATH + StringConstant.ANSWERS_PREFIXX + num + suffix;
        //获取给定 Answers.txt 路径 + 文件名
        String givenAnswerFile = StringConstant.ANSWERS_PATH + answersName;

        //获取官方答案文件字节流
        BufferedReader reader1 = (BufferedReader) IOUtils.getIO( officialAnswerFile, IOConstant.READER.name());
        //获取给定答案文件字节流
        BufferedReader reader2 = (BufferedReader) IOUtils.getIO(givenAnswerFile, IOConstant.READER.name());

        String str1 = "";
        String str2 = "";

        //将官方答案和给定答案进行匹配校验
        while ((str1 = reader1.readLine()) != null && (str2 = reader2.readLine()) != null) {
            if (str1.equals(str2) && !str1.equals("\n")) {
                corrects[correctCount++] = String.valueOf(idx);
            } else {
                wrongs[wrongCount++] = String.valueOf(idx);
            }
            idx++;
        }
        //关闭流
        IOUtils.closeIO(reader1,reader2);

        //记录正确题号和错误题号到文件中
        inputFinally(correctCount, wrongCount, corrects, wrongs);

    }

    /**
     * 将正确的题号 和 错误的题号 输入到 Finally.txt 文件中
     * @param correctCount
     * @param wrongCount
     * @param corrects
     * @param wrongs
     * @throws IOException
     */
    private void inputFinally(int correctCount, int wrongCount, String[] corrects, String[] wrongs) throws IOException {
        String left = "(";
        String right = ")";
        String line = "\n";
        String comma = ",";
        String correctName = "Correct:";
        String wrongName = "Wrong:";

        //获取字节流
        BufferedWriter writer = (BufferedWriter) IOUtils.getIO(
                StringConstant.FINALLY_PATH + StringConstant.FINALLY_PREFIXX + suffix, IOConstant.WRITER.name());

        //输入正确的题号
        writer.write(correctName + correctCount + left);
        writer.flush();

        for (int x = 0; x < correctCount; x++) {
            if (x != correctCount - 1)
                writer.write(corrects[x] + comma);
            else
                writer.write(corrects[x]);
            writer.flush();
        }

        //输入错误的题号
        writer.write(right + line + wrongName + wrongCount + left);
        writer.flush();

        for (int x = 0; x < wrongCount; x++) {
            if (x != wrongCount - 1)
                writer.write(wrongs[x] + comma);
            else
                writer.write(wrongs[x]);
            writer.flush();
        }
        writer.write(right + line);
        writer.flush();
        //流的关闭
        IOUtils.closeIO(writer);
    }
}
