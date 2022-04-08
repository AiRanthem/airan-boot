package cn.airanthem.aboot.utils;

import java.nio.charset.StandardCharsets;

public class DataUtil {
    public static byte[] encode(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] ans = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, ans, 0, bytes.length);
        ans[ans.length - 1] = -1;
        return ans;
    }

    public static String decode(byte[] bts) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bts) {
            if (b == -1) {
                break;
            }
            sb.append(Character.valueOf((char) b));
        }
        return sb.toString();
    }
}
