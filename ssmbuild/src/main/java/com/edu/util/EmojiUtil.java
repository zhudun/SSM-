package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情处理基类
 */
@Component
public class EmojiUtil {

    public  String filterEmoji(String param) {
        //nick_name signature所获取的用户昵称及个性签名
        if (param == null) {
            return param;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(param);
        if (emojiMatcher.find()) {
            //将所获取的表情转换为*
            param = emojiMatcher.replaceAll("(表情)");
            return param;
        }
        return param;
    }
}
