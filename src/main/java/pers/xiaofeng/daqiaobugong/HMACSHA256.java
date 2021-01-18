package pers.xiaofeng.daqiaobugong;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * 签名 HmacSHA256 加密
 */
public class HMACSHA256 {

    /**
     * 生成签名数据_HmacSHA256加密
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     */
    public static String getSignature(String data, String key) throws Exception {
        try {
            String secret = key;
            String message = data;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("utf-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes("utf-8")));
            return hash;
        } catch (Exception e) {
            System.out.println("生成签名数据失败！！！");
            return null;
        }
    }

}