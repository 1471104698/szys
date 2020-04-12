package cn.oy.szys;

import java.io.IOException;
import java.util.Scanner;

//对外开放主类
public class OpenMain {

    public void test() {
        //数字范围
        int range = 0;
        //题目数量
        int number_of_questions = 0;
        //题目文件路径
        String Exercises_path = "";
        //答案文件路径
        String Answers_path = "";

        FileName question = new FileName();

        System.out.println("当前已有的题目文件：");
        //获取所有的题目文件数
        question.get_ExercisesName();
        //       question.get_AnswersName();

        System.out.println("生成题目的个数，示例：-n 10 ");
        System.out.println("题目中数值的范围，示例：-r 10");
        System.out.println("对给定的题目文件和答案文件，判定答案中的对错并进行数量统计，示例：-e <exercisefile>.txt -a <answerfile>.txt");

        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);

    }
}
