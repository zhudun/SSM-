package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import com.google.common.base.Strings;

import org.apache.commons.lang.StringUtils;

public class DesensitizationUtil {

    /**

     * 身份证

     */

    public static final String CERTIFICATE_TYPE_0 = "0";

    /**

     * 护照

     */

    public static final String CERTIFICATE_TYPE_1 = "1";

    /**

     * 军官证

     */

    public static final String CERTIFICATE_TYPE_2 = "2";

    /**

     * 驾照

     */

    public static final String CERTIFICATE_TYPE_3 = "3";

    /**

     * 户口本

     */

    public static final String CERTIFICATE_TYPE_4 = "4";

    /**

     * 学生证

     */

    public static final String CERTIFICATE_TYPE_5 = "5";

    /**

     * 工作证

     */

    public static final String CERTIFICATE_TYPE_6 = "6";

    /**

     *

     */

    public static final String CERTIFICATE_TYPE_7 = "7";

    /**

     * 其他

     */

    public static final String CERTIFICATE_TYPE_8 = "8";

    /**

     * 无证件

     */

    public static final String CERTIFICATE_TYPE_9 = "9";

    /**

     * 社保号

     */

    public static final String CERTIFICATE_TYPE_a = "a";

    /**

     * 出生证

     */

    public static final String CERTIFICATE_TYPE_b = "b";

    /**

     * 士兵证

     */

    public static final String CERTIFICATE_TYPE_c = "c";

    /**

     * 港澳通行证

     */

    public static final String CERTIFICATE_TYPE_d = "d";

    /**

     * 台湾通行证

     */

    public static final String CERTIFICATE_TYPE_e = "e";

    /**

     * 港澳台同胞证

     */

    public static final String CERTIFICATE_TYPE_f = "f";

    /**

     * 出生医学证明

     */

    public static final String CERTIFICATE_TYPE_g = "g";

    /**

     * 外国人永久居留身份证

     */

    public static final String CERTIFICATE_TYPE_h = "h";

    /**

     * 港澳台居民居住证

     */

    public static final String CERTIFICATE_TYPE_i = "i";

    /**

     * 港澳居民来往内地通行证

     */

    public static final String CERTIFICATE_TYPE_j = "j";

    /**

     * 台湾居民往来大陆通行证

     */

    public static final String CERTIFICATE_TYPE_k = "k";

    /**

     * 手机号掩码中间四位

     * mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

     * @param mobile

     * @return

     */

    public static String maskMobile(String mobile){

        return DesensitizationUtil.desensitizedMobileNumber(mobile);

    }

    /**

     * 身份证掩码后四位

     * @param card

     * @return

     */

    public static String maskCard(String card){

        return DesensitizationUtil.desensitizedCertificateNumber(card,CERTIFICATE_TYPE_0);

    }

    /**

     *

     * @param common

     * @return

     */

    public static String desensitizedCommon(String common){

        if (!Strings.isNullOrEmpty(common)) {

            int len = common.length();

            int lastLen = 0;

            int midLen = len > 4 ? 4 : len;

            return StringUtils.left(common, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(common, lastLen), midLen + lastLen, "*"));

        }

        return common;

    }

    /**

     * 固定电话号码脱敏（一级客户信息）

     * 从倒数第 5 位开始脱敏 4 位，其余保留。

     * 示例：0759-4113524 -> 0759****3524

     * 0531-83590115 -> 0531-****0115

     * 4113524 -> ***3524

     * @param phoneNumber 固定电话号码

     * @return

     */

    public static String desensitizedPhoneNumber(String phoneNumber){

        if (!Strings.isNullOrEmpty(phoneNumber)) {

            int len = phoneNumber.length();

            int midLen = 4;

            int lastLen = len - midLen > 4 ? 4 : len;

            return StringUtils.left(phoneNumber, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(phoneNumber, lastLen), midLen + lastLen, "*"));

        }

        return phoneNumber;

    }

    /**

     * 手机号码脱敏（一级客户信息）

     * 从倒数第 5 位开始脱敏 4 位，其余保留。

     * 示例：13812341111 -> 138****1111

     * 013841135245 -> 0138****5245

     * 8613841135245 -> 86138****5245

     * @param mobileNumber 手机号码

     * @return

     */

    public static String desensitizedMobileNumber(String mobileNumber){

        if (!Strings.isNullOrEmpty(mobileNumber)) {

            int len = mobileNumber.length();

            int lastLen = 4;

            int midLen = len - lastLen > 4 ? 4 : len;

            return StringUtils.left(mobileNumber, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(mobileNumber, lastLen), midLen + lastLen, "*"));

        }

        return mobileNumber;

    }

    /**

     * 邮箱信息脱敏（一级客户信息）

     * 默认应将@符号前三位进行脱敏。

     * 示例：zhyl@163.com -> z***@163.com

     * @param email 邮箱

     * @return

     */

    public static String desensitizedEmail(String email){

        if (!Strings.isNullOrEmpty(email)) {

            int pos = email.indexOf('@');

            if(pos > -1){

                int len = email.length();

                int lastLen = len - pos;

                int midLen = len - lastLen > 3 ? 3 : len;

                return StringUtils.left(email, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(email, lastLen), midLen + lastLen, "*"));

            }

        }

        return email;

    }

    /**

     * 规则通讯地址脱敏（一级客户信息）

     * 脱敏除省名称与门牌号以外的市、县、镇、区等名称，用*代替

     * 示例：“江西省吉安市安福县枫田镇安乐区 128 号” -> “江西省**市**县**镇**区 128 号”

     * @param address 通讯地址

     * @return

     */

    public static String desensitizedAddress(String address){

// todo

        return address;

    }

    /**

     * 不规则通讯地址脱敏（一级客户信息）

     * 脱敏最后八位，用*代替，其余保留

     * 示例：枫田镇安乐区 128 号 ->枫田********

     * @param address 通讯地址

     * @return

     */

    public static String desensitizedAddressIrregular(String address){

        if (!Strings.isNullOrEmpty(address)) {

// 去除所有空格

            address = address.replaceAll("\\s","");

            int len = address.length();

            int lastLen = 0;

            int midLen = len - lastLen > 8 ? 8 : len;

            return StringUtils.left(address, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(address, lastLen), midLen + lastLen, "*"));

        }

        return address;

    }

    /**

     * 银行账号信息-银行卡号脱敏（一级客户信息）

     * 应将保留银行卡号前六位与后四位。

     * 注：银行卡号格式，借记卡通常为 16-19 位，信用卡号为 16 位，统一保留前六位与后四位。

     * 示例：“6217001111111114874”->“621700*********4874”

     * @param bankCardNumber 银行卡号

     * @return

     */

    public static String desensitizedBankCardNumber(String bankCardNumber){

        if (!Strings.isNullOrEmpty(bankCardNumber)) {

            int len = bankCardNumber.length();

            int lastLen = 4;

            int midLen = len - lastLen > 6 ? len - lastLen - 6 : 0;

            return StringUtils.left(bankCardNumber, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(bankCardNumber, lastLen), midLen + lastLen, "*"));

        }

        return bankCardNumber;

    }

    /**

     * 银行账号信息-存折账号脱敏（一级客户信息）

     * 存折账号，通常为 14-19 位，应将保留存折账号前四位与后四位。

     * 示例：“17039001100413825”->“1703*********3825”

     * @param passbookAccountNumber 存折账号

     * @return

     */

    public static String desensitizedPassbookAccountNumber(String passbookAccountNumber){

        if (!Strings.isNullOrEmpty(passbookAccountNumber)) {

            int len = passbookAccountNumber.length();

            int lastLen = 4;

            int midLen = len - lastLen > 4 ? len - lastLen - 4 : 0;

            return StringUtils.left(passbookAccountNumber, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(passbookAccountNumber, lastLen), midLen + lastLen, "*"));

        }

        return passbookAccountNumber;

    }

    /**

     * 证件信息脱敏（一级客户信息）

     * （1）身份证以及驾驶证号码

     * 号码至少最后四位用*替换。

     * 注：通常情况下驾驶证号码与身份证号码一致。

     * 示例：“440524188001012233”->“44052418800101****”

     * （2）社保号码

     * 通常情况下，国内常见社保号码与身份证号码保持一致，因此脱敏方法应采取与身份证号码同样的方法，脱敏身份证号/社保号码后四位。

     * 注：鉴于医养行业特点及其特殊性，对医养相关的应用系统设计、开发时还应考虑对“社保号码”的脱敏。

     * 示例：“440524188001010014” -> “44052418800101****”

     * （3）军人证号

     * 应将保留军人证号前三位和最后 3 位。

     * 示例：“空字第 12345678” -> “空字第*****678”

     * （4）护照号

     * 应将保留护照号第 1 位字母和最后 3 位数字。

     * 示例：“G12345678” ->“G*****678”

     * （5）香港永久性居民身份证

     * 香港身份证号码由三部分组成：一个英文字母；6个数字；括号及 0-9 中的任一个数字，或者字母 A，括号中为校验位。应脱敏最后除校验位的三位数字，其他保留。

     * 示例：“C668668（A）”->“ C668***（A）”

     * （6）香港车牌号

     * 香港车牌号由两个英文字母加一组 1 至 4 位数字组成。应脱敏最后四位，其他保留。

     * 示例：“BC2865”->“BC****”

     * （7）港澳居民往来内地通行证号

     * 港澳居民往来内地通行证号码由1位字母和10位数字组成。应将保留港澳往来内地通行证号最后 3 位数字。

     * 示例：“H0019843501” ->“********501”

     * （8）台胞证号

     * 台胞证号码由 8 位终身号组成。应将保留台胞证号第 5-8 位数字。

     * 示例：“00730189” -> “****0189”

     * @param certificateNumber 证件号码

     * @param certificateType 证件类型

     * @return

     */

    public static String desensitizedCertificateNumber(String certificateNumber,String certificateType){

        if (!Strings.isNullOrEmpty(certificateNumber)) {

            int len = certificateNumber.length();

            int lastLen = 0;

            int midLen = len > 4 ? 4 : len;

// (1)身份证以及驾驶证号码

            if(CERTIFICATE_TYPE_0.equals(certificateType)){

            }

// (2)社保号码

// (3)军人证号

// (4)护照号

// (5)香港永久性居民身份证

// (6)香港车牌号

// (7)港澳居民往来内地通行证号

// (8)台胞证号

            return StringUtils.left(certificateNumber, len - midLen - lastLen).concat(StringUtils.leftPad(StringUtils.right(certificateNumber, lastLen), midLen + lastLen, "*"));

        }

        return certificateNumber;

    }

    public static void main(String[] args) {

        String str = "zhyl@163.com";

        System.out.println(desensitizedPhoneNumber("0531-83590115"));

        System.out.println(desensitizedMobileNumber("13812341111"));

        System.out.println(desensitizedEmail("zhyl@qq.com"));

        System.out.println(desensitizedAddressIrregular("枫田镇安乐区 128 号"));

        System.out.println(desensitizedBankCardNumber("6217001111111114874"));

        System.out.println(desensitizedPassbookAccountNumber("17039001100413825"));

        System.out.println(desensitizedCertificateNumber("440524188001012233",DesensitizationUtil.CERTIFICATE_TYPE_0));

    }

}
