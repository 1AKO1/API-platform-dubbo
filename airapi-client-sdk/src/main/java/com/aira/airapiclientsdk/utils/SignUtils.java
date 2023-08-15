package com.aira.airapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;

public class SignUtils {
    public static String getSign(String body, String secretKey){
        System.out.println("加密参数：body = " + body + ", secretKey = " + secretKey);
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String context = body + "." + secretKey;

        // 5393554e94bf0eb6436f240a4fd71282
        String digestHex = md5.digestHex(context);
        return digestHex;
    }
}
