package cn.oy.szys;

import cn.oy.constant.StringConstant;

import java.io.IOException;
import java.util.*;

/**
 * 生成题目
 */
public class Create {

    String suffix = StringConstant.FILE_SUFFIX;

    Create(int range, int questions) {
        /**
         * 去重思路：
         * 我们将表达式长度和对应结果存储起来
         * 如果 答案 和 表达式 长度 对于另一个表达式同时存在重复，那么模糊地认为表达式重复，重新获取
         */
        //key 存储结果， value 存储表达式长度，因为一个结果可能对应不同的长度，因此使用 set 存储
        Map<String, Set<Integer>> map = new HashMap<>();

        CreateExercise exercise;
        int i;
        //存储题目
        String[] Exercises = new String[questions];
        //存储答案
        String[] Answers = new String[questions];
        for (i = 0; i < questions; i++) {
            exercise = new CreateExercise(range);
            //去重处理
            //判断是否存在结果一样
            if(map.containsKey(exercise.answer)){
                //遍历已经存在的表达式长度
                Set<Integer> set = map.get(exercise.answer);
                while(set.contains(exercise.createExercise.length())){
                    exercise = new CreateExercise(range);
                }
                set.add(exercise.createExercise.length());
            }else {
                map.put(exercise.answer, new HashSet<>(Arrays.asList(exercise.createExercise.length())));
            }
            //将不重复的表达式 和 结果存储起来
            Exercises[i] = exercise.createExercise;
            Answers[i] = exercise.answer;
        }

        String execPath = "Exercises/Exercises"; //"Exercises/Exercises"，题目文件路径 + 文件名前缀
        String execPreffix = "Exercises";    //Exercises    文件名前缀
        String ansPath = "Answers/Answers"; //"Answers/Answers"     答案文件路径 + 文件名前缀
        String ansPreffix = "Answers"; //Answers        文件名前缀

        FileName fileName = new FileName();
        //得到存在的最后一个题目文件的序号，然后当前生成的新文件序号 + 1
        String execSerialnumber = execPreffix + (fileName.i + 2) + suffix;
        String ansSerialnumber = ansPreffix + (fileName.j + 2) + suffix;
        //生成 题目文件和 答案文件
        new FileOperate().doCreate(Exercises, Answers, execSerialnumber, ansSerialnumber, i);
    }
}


/**
 * 随机数公式(int) (Math.random()*(max-min)+min)
 * 用于组合两个算式
 */
class CreateExercise {

    int value;
    //   String answer;
    String createExercise, answer;
    Exercise f1;
    Exercise f2;
    char symbol;
    public char[] symbols = new char[]{'+', '-', '*', '/'};

    public CreateExercise(int range) {
        this.f1 = new Exercise(range);
        this.f2 = new Exercise(range);
        symbol = symbols[(int) (Math.random() * 4)];
        if (!f1.isFraction && !f2.isFraction) {    //都不是分式
            if (f2.value == 0 && symbol == '/') {
                do {
                    this.f2 = new Exercise(range);
                } while (f2.value != 0);
            }             //除数为0
            else {
                if (symbol == '/' && f1.value % f2.value != 0) {       //两个式子不能整除时
                    answer = f1.proFraction(f1.value, f2.value);//补充个标记
                    createExercise = this.f1.createExercise + symbol + this.f2.createExercise;
                } else {                                             //普通情况
                    value = f1.getValue(f1.value, f2.value, symbol); //得到答案，仅仅是整数   （之后分式功能写完后要分分式的运算和整数的运算）

                    if (value < 0) {                 //结果为负数，答案取反，调换两个算式的位置
                        value = -value;
                        answer = "" + value;
                        createExercise = this.f2.createExercise + symbol + this.f1.createExercise;
                    } else {
                        answer = "" + value;
                        createExercise = this.f1.createExercise + symbol + this.f2.createExercise;
                    }
                }
            }
        } else if (f1.isFraction && f2.isFraction) {  //两个都是分式的情况
            if (symbol == '-' && f1.value1 * f2.value2 < f2.value1 * f1.value2) {
                createExercise = this.f2.createExercise + symbol + this.f1.createExercise;
            } else {
                createExercise = this.f1.createExercise + symbol + this.f2.createExercise;
            }
            answer = f1.getAnswer(f1.value1, f1.value2, f2.value1, f2.value2, symbol);
        } else if (f1.isFraction) {
            if (symbol == '-' && f1.value1 < f2.value * f1.value2) {
                createExercise = this.f2.createExercise + symbol + this.f1.createExercise;
            } else {
                createExercise = this.f1.createExercise + symbol + this.f2.createExercise;
            }
            answer = f1.getAnswer(f1.value1, f1.value2, f2.value, 1, symbol);
        } else {
            if (symbol == '-' && f1.value * f2.value2 < f2.value1) {
                createExercise = this.f2.createExercise + symbol + this.f1.createExercise;
            } else {
                createExercise = this.f1.createExercise + symbol + this.f2.createExercise;
            }
            answer = f1.getAnswer(f1.value, 1, f2.value1, f2.value2, symbol);
        }

    }
}


class Exercise {
    int value;  //数的和，单个数的时候是本身，两个数的时候是两数的运算结果
    int value1; //两个数的时候第一个数
    int value2; //两个数的时候第二个数
    int sign = 0;//符号标志，默认为 0，整数
    String createExercise; //createExercise 用来存放算式
    String fraction; //fraction 用来存放分数结果
    char symbol;
    boolean swap = false;                            //是否出现负数需要调换
    boolean isFraction = false;                        //是否是分式
    char[] symbols = new char[]{'+', '-', '*', '/'};

    public Exercise(int range) {
        if ((Math.random() * 2) <= 1) {            //只有一数的情况
            value = (int) (Math.random() * (range - 1) + 1);
            createExercise = String.valueOf(value);
            symbol = '\0';
        } else {
            //两个数的情况
            value1 = (int) (Math.random() * range + 1);
            value2 = (int) (Math.random() * range + 1);
            //随机选取一个符号 + - * /
            symbol = symbols[(int) (Math.random() * 4)];

            //如果是 / ，那么特殊处理
            if (symbol == '/' && value1 % value2 != 0) {
                isFraction = true;
                fraction = proFraction(value1, value2);
            } else {
                value = getValue(value1, value2, symbol);
            }
            if (swap) {
                value = -value;
                createExercise = "(" + value2 + symbol + value1 + ")";
            } else {
                createExercise = "(" + value1 + symbol + value2 + ")";
            }
        }
    }

    public int getValue(int a, int b, char symbol) {
        switch (symbol) {
            case '+':
                return a + b;
            case '-':
                if (a < b)
                    swap = true;
                return a - b;
            case '/':
                return a / b;
            case '*':
                return a * b;
        }
        return 0;
    }

    /**
     * 计算结果，得到表达式的答案
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param symbol
     * @return
     */
    public String getAnswer(int a, int b, int c, int d, char symbol) {
        int molecule;
        int denominator;
        switch (symbol) {
            case '+':
                molecule = a * d + b * c;
                denominator = b * d;
                return proFraction(molecule, denominator);
            case '-':
                molecule = a * d - b * c;
                denominator = b * d;
                if (molecule < 0) {
                    this.sign = 1;
                    return proFraction(-molecule, denominator);
                }
                return proFraction(molecule, denominator);

            case '/':
                molecule = a * d;
                denominator = b * c;
                return proFraction(molecule, denominator);
            case '*':
                molecule = a * c;
                denominator = b * d;
                return proFraction(molecule, denominator);
        }
        return null;
    }

    /**
     * 处理分数
     *
     * @param molecule    分子
     * @param denominator 分母
     * @return
     */
    public String proFraction(int molecule, int denominator) {
        //商
        int merchant = molecule / denominator;
        //将分数化为真分数，那么需要将分子减去多余的分母的倍数
        molecule -= merchant * denominator;

        //得到分子和分母的最大公约数
        int gcdNum = gcd(molecule, denominator);

        //存储化简分数结果
        String res = "";

        //分子为 0，那么结果直接就是 商
        if (molecule == 0) {
            res = String.valueOf(0);
        } else {
            //分子不为 0，那么根据商的结果判断是真分数还是假分数，对应做处理
            res = String.valueOf(molecule / gcdNum) + '/' + denominator / gcdNum;

            //如果商不为 0，那么我们将商的结果也加到结果字符串中
            if (merchant != 0) {
                res = String.valueOf(merchant) + '’' + res;
            }
        }
        return res;
    }

    private int gcd(int a, int b) {
        if (a % b == 0) {
            return b;
        }
        return gcd(b, a % b);
    }
}