package cn.oy.szys;

import java.io.IOException;
import java.util.Scanner;

/**
 * 对外开放主类
 */
public class OpenMain {
    //数值范围，默认为 0
    int range = 0;
    //题目数量，默认为 0
    int questions = 0;

    public void test() {

        FileName question = new FileName();

        System.out.println("当前已有的题目文件：");
        //获取所有的题目文件数
        question.getExercisesName();
        //question.get_AnswersName();

        System.out.println("生成题目的个数，示例：-n 10 ");
        System.out.println("题目中数值的范围，示例：-r 10");
        System.out.println("对给定的题目文件和答案文件，判定答案中的对错并进行数量统计，示例：-e <exercisefile>.txt -a <answerfile>.txt");

        while (true) {
            //输入操作指令
            inputOpStrs();
            if(range < 0 || questions < 0){
                System.out.println("参数值必须为正数");
                continue;
            }
            if (range == 0 || questions == 0) {
                if (questions == 0)
                    System.out.println("未输入题目数量");
                else{
                    System.out.println("将生成题目数量为：" + questions);
                }
                if (range == 0)
                    System.out.println("未输入数值范围");
                else{
                    System.out.println("生成的题目的数值范围为：" + range);
                }
            } else {
                new Create(range, questions);//题目生成
                System.out.println("题目生成完毕，题目文件存放在项目目录的 Exercises 文件夹中,答案文件存放在 Answers 文件夹中");
                this.range = 0;               //初始化数组范围和题目数量范围
                this.questions = 0;
                System.out.println("数值范围与题目数量已初始化");
                System.out.println("当前已有的题目文件：");
                question.getExercisesName();
            }

        }
    }
    private void inputOpStrs(){
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);

        System.out.print("输入指令：");
        // 获取操作指令
        String opStr = scanner.nextLine();
        //操作指令分割，通过空格分割，变成操作指令字符串
        String[] opStrs = opStr.split("\\s+");

        //根据不同的情况进行处理
        switch (opStrs[0]) {
            case "-n":
                this.questions = Integer.parseInt(opStrs[1]);
                break;
            case "-r":
                this.range = Integer.parseInt(opStrs[1]);
                break;
            case "-e":
                //-e Exercises1.txt -a Answers1.txt
                try {
                    //opStrs[1]是题目文件名，opStrs[3]是答案文件名，FileOperate：文件操作类
                    //根据传入的题目文件名 和 答案文件名 进行 匹配校验
                    new FileOperate().proFile(opStrs[1], opStrs[3], questions);
                    System.out.println("对错判断完毕,请在 Finally 文件夹中查看");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default: break;
        }
    }
}
