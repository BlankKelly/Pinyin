package util;

import java.io.*;
import java.util.HashMap;

/**
 * Created by zk on 2015/6/28.
 *
 * @author zk
 */
public class Pinyin {
    private static HashMap<String, String> PINYIN = new HashMap<>();

    static {
        try {
            PINYIN = pinyinToDic(new File("pinyin.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼音文件转成字典
     *
     * @param file 拼音文件
     * @return 字典
     * @throws IOException
     */
    private static HashMap<String, String> pinyinToDic(File file) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        BufferedReader br = new BufferedReader(isr);
        HashMap<String, String> result = new HashMap<>();
        String line = "";

        while ((line = br.readLine()) != null) {
            String[] ret = line.split("=");
            result.put(ret[0].replace('\'', ' ').replace(',', ' ').trim(),
                    ret[1].replace('\'', ' ').replace(',', ' ').trim());
        }
        return result;
    }

    /**
     * 将字符串转换成单个字组成的数组
     *
     * @param str 字符串
     * @return 单个字符串组成的数组
     */
    private static String[] stringToArray(String str) {
        int len = str.length();
        String[] ret = new String[len];

        for (int i = 0; i < len; i++) {
            ret[i] = str.substring(i, i + 1);
        }

        return ret;
    }

    /**
     * 单个字符转成拼音
     *
     * @param str 单个字符
     * @return 字符对应的拼音（若存在对应的拼音，西文字符保持不变）
     */
    private static String chineseToPin(String str) {
        String ret = "";

        if (PINYIN.containsKey(str)) {
            ret = PINYIN.get(str);
        } else {
            ret = str;
        }

        return ret;
    }

    /**
     * 汉字转拼音
     *
     * @param str 汉字
     * @return 拼音
     */
    public static String getPinyin(String str) {
        String[] strArr = stringToArray(str);
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < strArr.length; i++) {
            ret.append(chineseToPin(strArr[i]));
            ret.append(" ");
        }

        return ret.toString();
    }

    /**
     * 汉字转短拼音
     *
     * @param str 汉字
     * @return 短拼音
     */
    public static String getShortString(String str) {
        String[] strArr = stringToArray(str);
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < strArr.length; i++) {
            ret.append(chineseToPin(strArr[i]).substring(0, 1));
            ret.append(" ");
        }

        return ret.toString();
    }

    /*
    public static void main(String[] args) {
        String s = "中华人民共和国abc";
        String[] result = stringToArray(s);

        System.out.println(chineseToPin("早"));
        System.out.println(getPinyin(s));
        System.out.println(getShortString(s));
        System.out.println(PINYIN.size());
    }*/
}
