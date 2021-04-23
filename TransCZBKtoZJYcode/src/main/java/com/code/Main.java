package com.code;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
  static File infile;
  static File outfile;
  static BufferedReader reader;
  static BufferedWriter output;
  static String infilepath = "C:\\Users\\cy\\Desktop\\trans\\1.txt";
  static String outfilepath = "C:\\Users\\cy\\Desktop\\trans\\1.xls";
  static int stemcount = 0;
  static int answercount = 0;
  static List<Map> dataList = new LinkedList<Map>();

  static {
    try {
      infile = new File(infilepath);
      outfile = new File(outfilepath);
      reader = new BufferedReader(new FileReader(infile));
      output = new BufferedWriter(new FileWriter(outfile, true));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) throws IOException {
    System.out.println("简答题判断题题目和答案都必须为单行,选择题答案必须为单行!");
    String tmp = "";
    String type = "";
    boolean isAnswer = false;
    StringBuffer stem = new StringBuffer();
    StringBuffer A = new StringBuffer();
    StringBuffer B = new StringBuffer();
    StringBuffer C = new StringBuffer();
    StringBuffer D = new StringBuffer();
    while ((tmp = reader.readLine()) != null) {
      if (tmp.equals("") || tmp.startsWith("//") || tmp.startsWith("得分")) continue;
      else if (tmp.startsWith("单选题")) {
        type = "单选题";
      } else if (tmp.startsWith("多选题")) {
        type = "多选题";
      } else if (tmp.startsWith("判断题")) {
        type = "判断题";
      } else if (tmp.startsWith("填空题")) {
        type = "填空题";
      } else if (tmp.startsWith("简答题")) {
        type = "简答题";
      } else if (tmp.startsWith("答案")) {
        readShortAnswer(stem, tmp);
        isAnswer = true;
      } else {
        if (isAnswer == false) {
          if (type.equals("单选题")) {
            readSingleChoise(tmp, type, stem, A, B, C, D);
          } else if (type.equals("多选题")) {
            readMultipleChoise(tmp, type, stem, A, B, C, D);
          } else if (type.equals("判断题")) {
            readJudge(tmp);
          } else if (type.equals("填空题")) {
            readFillBank(tmp);
          } else if (type.equals("简答题")) {
            readShortAnswer(stem, tmp);
          }
        } else {
          if (type.equals("单选题")) {
            readAnswer(tmp);
          } else if (type.equals("多选题")) {
            readAnswer(tmp);
          } else if (type.equals("判断题")) {
            readAnswer(tmp);
          } else if (type.equals("填空题")) {
            readAnswer(tmp);
          } else if (type.equals("简答题")) {
            readAnswer_short_answer(stem, tmp);
          }
        }
      }

    }
    //最后一个答案没有结束标志，就放在这里处理
    dataList.get(answercount++).put("答案", stem.substring(stem.indexOf("正确答案")));
    stem.setLength(0);
    WriteExcel.writeExcelSingleandMultiple(dataList, 11, outfilepath);

    reader.close();
    output.close();
  }

  private static void readAnswer(String tmp) {
    try {
      String ans = tmp.substring(tmp.indexOf("．") + 1);
      if (ans.equals("错")) ans = "错误";
      if (ans.equals("对")) ans = "正确";
      dataList.get(answercount++).put("答案", ans);
      System.out.println("写入第"+(answercount)+"答案:"+ans);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
  }

  //读单选题
  public static void readSingleChoise(String tmp, String type, StringBuffer stem, StringBuffer A
      , StringBuffer B, StringBuffer C, StringBuffer D) throws IOException {
    if (tmp.startsWith("A、")) {
      A.append(tmp.substring(tmp.indexOf("、") + 1));
    } else if (tmp.startsWith("B、")) {
      B.append(tmp.substring(tmp.indexOf("、") + 1));
    } else if (tmp.startsWith("C、")) {
      C.append(tmp.substring(tmp.indexOf("、") + 1));
    } else if (tmp.startsWith("D、")) {
      D.append(tmp.substring(tmp.indexOf("、") + 1));
      Map map = new HashMap();
      map.put("题干", stem.substring(stem.indexOf("．") + 1));
      map.put("题型", type);
      map.put("A", A.toString());
      map.put("B", B.toString());
      map.put("C", C.toString());
      map.put("D", D.toString());
      dataList.add(map);
      stemcount++;
      System.out.println("选择：写入第"+(stemcount)+"题干:"+stem.toString());
      stem.setLength(0);
      A.setLength(0);
      B.setLength(0);
      C.setLength(0);
      D.setLength(0);
    } else {
      stem.append(tmp);
    }
  }

  //读多选题
  public static void readMultipleChoise(String tmp, String type, StringBuffer stem, StringBuffer A
      , StringBuffer B, StringBuffer C, StringBuffer D) throws IOException {
    readSingleChoise(tmp, type, stem, A, B, C, D);
  }

  //读判断题
  public static void readJudge(String tmp) throws IOException {
    Map map = new HashMap();
    map.put("题干", tmp.substring(tmp.indexOf("．") + 1).toString());
    map.put("题型", "判断题");
    dataList.add(map);
    stemcount++;
    System.out.println("判断：写入第"+(stemcount)+"题干:"+tmp.toString());
  }

  //读填空题
  private static void readFillBank(String tmp) throws IOException {
    Map map = new HashMap();
    map.put("题干", tmp.substring(tmp.indexOf("．") + 1).toString());
    map.put("题型", "主观填空题");
    dataList.add(map);
    stemcount++;
    System.out.println("写入第"+(stemcount)+"题干:"+tmp.toString());
  }

  private static void readAnswer_short_answer(StringBuffer stem, String tmp) {

    if (tmp.startsWith("" + (answercount + 2))) {
      try {
        dataList.get(answercount++).put("答案", stem.substring(stem.indexOf("正确答案")));
        System.out.println("写入第"+(answercount)+"答案:"+stem.toString());
      } catch (StringIndexOutOfBoundsException e) {
        e.printStackTrace();
      }
      stem.setLength(0);
      stem.append(tmp);
    } else {
      stem.append(tmp);
    }

  }

  //读简答题
  public static void readShortAnswer(StringBuffer stem, String tmp) throws IOException {
    if (tmp.startsWith("答案")) {//当看到答案两个字就处理最后一个简答题的录入
      Map map = new HashMap();
      map.put("题干", stem.toString());
      map.put("题型", "问答题");
      dataList.add(map);
      stemcount++;
      System.out.println("最后简答：写入第"+(stemcount)+"题干:"+stem.toString());
      stem.setLength(0);
      return;
    }

    if (getStartNum(tmp) == (stemcount + 2) && stem.length() != 0) {
      Map map = new HashMap();
      map.put("题干", stem.toString());
      map.put("题型", "问答题");
      dataList.add(map);
      stemcount++;
      System.out.println("简答：写入第"+(stemcount)+"题干:"+stem.toString());
      stem.setLength(0);

    }
    stem.append(tmp);
  }

  public static int getStartNum(String tmp) {
    int i = 0;
    char c = tmp.charAt(i);
    String startNum = "";
    while (c >= '0' && c <= '9') {
      startNum += c;
      c = tmp.charAt(++i);
    }
    if (startNum.equals("")) return -1;
    return Integer.parseInt(startNum);
  }
}
