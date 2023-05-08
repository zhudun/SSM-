package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */

import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**

 * Created by yangchd on 2017/7/10. 放置一些工具类

 */

public class FINJRPTTools {

    /**

     * @auther shixg

     * @date 2016-11-30 获取请求参数

     * @Param args（请求参数，json形式）

     * @return paramMap 参数map，存放参数

     */

    public static void handlerParam(String args, Map<String, String> paramMap) throws Exception {

        JSONObject jsonObj = new JSONObject(args);

        JSONObject jsonObject2 = jsonObj.getJSONObject("data");

        JSONObject jsonObject1 = jsonObject2.getJSONObject("servicecontext");

        JSONObject jsonObject = jsonObject1.getJSONObject("params");

        Iterator it = jsonObject.keys();

        while (it.hasNext()) {

            String key = (String)it.next();

            Object value = jsonObject.get(key);

            paramMap.put(key, value.toString());

        }

    }

    /**

     * 编码格式转换 由于idea工具编码问题，这里貌似不需要了

     *//*

public static String getByteStr(String arg) {

if (arg != null && !"".equals(arg)) {

return arg;

}

return "";

}

public String saveToImg(String IP , String imgpath, String user_id , String[] imgstream ) throws IOException {

String fastdfs_url = MaSysConfSingleton.getInstance().getValue("fastdfs.url");

FastDFSClient fastDFSClient = new FastDFSClient();

NameValuePair[] meta_list = null;

String WEBPATH = System.getProperty("user.dir");

imgpath = "/img/"+imgpath+"/";

String url = "";//把normalPath与smallPath组合为一个URL

String normalPath = "";//正常大小图片返回路径值

String smallPath = "";//缩略图返回路径值

String imgURL = WEBPATH + imgpath + user_id;

File files = new File(imgURL);

File dir = files.getParentFile();

if (!dir.exists()) {

dir.mkdirs();

}

if (!files.exists()) {

files.mkdirs();

}

String imgname = "";

for(int i = 0 ;i < imgstream.length ;i ++){

String filename = getFileName();

BASE64Decoder decoder = new BASE64Decoder();

byte[] flowbyte = decoder.decodeBuffer(imgstream[i].trim());

if (flowbyte != null && flowbyte.length > 0) {

try {

//正常大小图片上传文件服务器

imgname = filename + ".jpg";

InputStream in = new ByteArrayInputStream(flowbyte);

File file = new File(imgURL, imgname);// 可以是任何图片格式.jpg,.png等

FileOutputStream fos = new FileOutputStream(file);

byte[] b = new byte[1024];

int nRead = 0;

while ((nRead = in.read(b)) != -1) {

fos.write(b, 0, nRead);

}

fos.flush();

fos.close();

in.close();

String system = MaSysConfSingleton.getInstance().getValue("file.system");

if ("fastdfs".equals(system)) {

normalPath = fastdfs_url + "/" + FastDFSClient.uploadFile(flowbyte, "jpg", meta_list);

} else {

String host = MaSysConfSingleton.getInstance().getValue("file.hosts");

String[] hosts = host.split("&&");

String url_wan = MaSysConfSingleton.getInstance().getValue("file.slowdfs.wan");

Map result = ClientUtil.fileUploadToHosts(hosts, "", imgURL + File.separator + imgname, imgname,5*1000,15*1000);

normalPath = url_wan + result.get("downloadUrl");

}

} catch (Exception e) {

imgURL = "";

}

//生成缩略图

//-----------------------

try{

File file = new File(imgURL+"/"+imgname);

BufferedImage img = ImageIO.read(file);

//原图宽高

int originalWidth = img.getWidth();

int originalHeight = img.getHeight();

double scale = (double)originalWidth / (double)300; // 缩放的比例，新图宽度为300

int newHeight = (int)(originalHeight / scale);

ImageZipUtil imgzip = new ImageZipUtil();

imgzip.zipWidthHeightImageFile2(img,new File(imgURL+"/"+filename+"_min.jpg"),300,newHeight,1.0f);

//缩略图上传文件服务器

File fileSmall = new File(imgURL+File.separator+filename+"_min.jpg");

FileInputStream inSmall = new FileInputStream(fileSmall);

byte[] buffSmall = null;

if(inSmall != null){

int len = inSmall.available();

buffSmall = new byte[len];

inSmall.read(buffSmall);

}

String system = MaSysConfSingleton.getInstance().getValue("file.system");

if ("fastdfs".equals(system)) {

smallPath = fastdfs_url + "/" + FastDFSClient.uploadFile(flowbyte, "jpg", meta_list);

} else {

String host = MaSysConfSingleton.getInstance().getValue("file.hosts");

String[] hosts = host.split("&&");

String url_wan = MaSysConfSingleton.getInstance().getValue("file.slowdfs.wan");

Map result = ClientUtil.fileUploadToHosts(hosts, "", imgURL + File.separator + filename + "_min.jpg", filename + "_min.jpg",5*1000,15*1000);

smallPath = url_wan + result.get("downloadUrl");

}

if(i == imgstream.length-1){

url += smallPath+"@@@"+normalPath;

continue;

}

url += smallPath+"@@@"+normalPath +",";

}catch(Exception e){

e.getMessage();

}finally{

}

}

}

//删除临时文件

File tempFile = new File(imgURL);

deleteAll(tempFile);

return url;

}

public Map<String ,String> saveToImgMap(String IP , String imgpath, String user_id , String[] imgstream ) throws IOException {

String fastdfs_url = MaSysConfSingleton.getInstance().getValue("fastdfs.url");

Map<String ,String> imgMap = new HashMap<String ,String>();

FastDFSClient fastDFSClient = new FastDFSClient();

NameValuePair[] meta_list = null;

String WEBPATH = System.getProperty("user.dir");

imgpath = "/img/"+imgpath+"/";

String url = "";//把normalPath与smallPath组合为一个URL

String normalPath = "";//正常大小图片返回路径值

String smallPath = "";//缩略图返回路径值

String imgURL = WEBPATH + imgpath + user_id;

File files = new File(imgURL);

File dir = files.getParentFile();

if (!dir.exists()) {

dir.mkdirs();

}

if (!files.exists()) {

files.mkdirs();

}

String imgname = "";

for(int i = 0 ;i < imgstream.length ;i ++){

String filename = getFileName();

BASE64Decoder decoder = new BASE64Decoder();

byte[] flowbyte = decoder.decodeBuffer(imgstream[i].trim());

if (flowbyte != null && flowbyte.length > 0) {

try {

//正常大小图片上传文件服务器

imgname = filename + ".jpg";

InputStream in = new ByteArrayInputStream(flowbyte);

File file = new File(imgURL, imgname);// 可以是任何图片格式.jpg,.png等

FileOutputStream fos = new FileOutputStream(file);

byte[] b = new byte[1024];

int nRead = 0;

while ((nRead = in.read(b)) != -1) {

fos.write(b, 0, nRead);

}

fos.flush();

fos.close();

in.close();

String system = MaSysConfSingleton.getInstance().getValue("file.system");

if ("fastdfs".equals(system)) {

normalPath = fastdfs_url + "/" + FastDFSClient.uploadFile(flowbyte, "jpg", meta_list);

} else {

String host = MaSysConfSingleton.getInstance().getValue("file.hosts");

String[] hosts = host.split("&&");

String url_wan = MaSysConfSingleton.getInstance().getValue("file.slowdfs.wan");

Map result = ClientUtil.fileUploadToHosts(hosts, "", imgURL + File.separator + imgname, imgname,5*1000,15*1000);

normalPath = url_wan + result.get("downloadUrl");

}

//生成缩略图

} catch (Exception e) {

imgURL = "";

}

try{

File file = new File(imgURL+"/"+imgname);

BufferedImage img = ImageIO.read(file);

//原图宽高

int originalWidth = img.getWidth();

int originalHeight = img.getHeight();

double scale = (double)originalWidth / (double)300; // 缩放的比例，新图宽度为300

int newHeight = (int)(originalHeight / scale);

int newWidth = (int)(originalWidth / scale);

ImageZipUtil imgzip = new ImageZipUtil();

imgzip.zipWidthHeightImageFile2(img,new File(imgURL+"/"+filename+"_min.jpg"),300,newHeight,1.0f);

//缩略图上传文件服务器

File fileSmall = new File(imgURL+File.separator+filename+"_min.jpg");

FileInputStream inSmall = new FileInputStream(fileSmall);

byte[] buffSmall = null;

if(inSmall != null){

int len = inSmall.available();

buffSmall = new byte[len];

inSmall.read(buffSmall);

}

String system = MaSysConfSingleton.getInstance().getValue("file.system");

if ("fastdfs".equals(system)) {

smallPath = fastdfs_url + "/" + FastDFSClient.uploadFile(flowbyte, "jpg", meta_list);

} else {

String host = MaSysConfSingleton.getInstance().getValue("file.hosts");

String[] hosts = host.split("&&");

String url_wan = MaSysConfSingleton.getInstance().getValue("file.slowdfs.wan");

Map result = ClientUtil.fileUploadToHosts(hosts, "", imgURL + File.separator + filename + "_min.jpg", filename + "_min.jpg",5*1000,15*1000);

smallPath = url_wan + result.get("downloadUrl");

}

if(i == imgstream.length-1){

url += smallPath+"@@@"+normalPath;

imgMap.put("narrowHeight",Integer.toString(newHeight));

imgMap.put("narrowWidth",Integer.toString(newWidth));

imgMap.put("url",url);

continue;

}

url += smallPath+"@@@"+normalPath +",";

imgMap.put("narrowHeight",Integer.toString(newHeight));

imgMap.put("narrowWidth",Integer.toString(newWidth));

imgMap.put("url",url);

}catch(Exception e){

e.getMessage();

}finally{

}

}

}

*/

/*if(!(url.indexOf("http")==0)){

url = ""+url;

}*//*

//删除临时文件

File tempFile = new File(imgURL);

deleteAll(tempFile);

return imgMap;

}

public Map upload_file(byte [] file_buff, String file_ext_name){

Map map = new HashMap();

Map remap = new HashMap();

try {

//文件服务器相对路径

String absolute_path = FastDFSClient.uploadFile(file_buff, file_ext_name,null);

//文件服务器内网地址

String url_lan = MaSysConfSingleton.getInstance().getValue("fastdfs.url.lan");

String url_wan = MaSysConfSingleton.getInstance().getValue("fastdfs.url");

map.put("absolute_path",absolute_path);

map.put("url_lan",url_lan);

map.put("url_wan",url_wan);

remap.put("retflag","0");

remap.put("msg","上传成功");

remap.put("data",map);

} catch (Exception e) {

e.printStackTrace();

MALogger.error("文件上传失败："+e.getMessage());

remap.put("retflag","1");

remap.put("msg","上传失败");

}

return remap;

}

*/

// 获取文件名前缀

    public String getFileName() {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String filename = format.format(new Date());

        return filename;

    }

// 删除上传存在服务器上的临时文件

    public void deleteAll(File file) {

        if (file.isFile() || file.list().length == 0) {

            file.delete();

        } else {

            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {

                deleteAll(files[i]);

                files[i].delete();

            }

            if (file.exists()) { // 如果文件本身就是目录 ，就要删除目录

                file.delete();

            }

        }

    }

    public static String getParamValue(HttpServletRequest request, String key) {

        String keyvalue = request.getParameter(key);

        if (keyvalue == null || "".equals(keyvalue)) {

            Object value = request.getAttribute(key);

            if (value != null) {

                return value.toString();

            } else {

                return null;

            }

        } else {

            return keyvalue;

        }

    }

    public static String getMD5(byte[] source) {

        String s = null;

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};// 用来将字节转换成16进制表示的字符

        try {

            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");

            md.update(source);

            byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，

// 用字节表示就是 16 个字节

            char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16

// 进制需要 32 个字符

            int k = 0;// 表示转换结果中对应的字符位置

            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16

// 进制字符的转换

                byte byte0 = tmp[i];// 取第 i 个字节

                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>

// 为逻辑右移，将符号位一起右移

                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

            }

            s = new String(str);// 换后的结果转换为字符串

        } catch (NoSuchAlgorithmException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

        return s;

    }

    public byte[] file2byte(File file) {

        byte[] buffer = null;

        try {

            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {

                bos.write(b, 0, n);

            }

            fis.close();

            bos.close();

            buffer = bos.toByteArray();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return buffer;

    }

    /**

     * 解密

     *

     * @param data

     * 待解密数据

     * @param

     *

     * @return byte[] 解密数据

     * @throws Exception

     */

    public static byte[] decrypt(byte[] data, byte[] pwd) throws Exception {

        byte[] key = initKey(pwd);

        SecretKey k = new SecretKeySpec(key, "AES");

        IvParameterSpec iv = new IvParameterSpec("Ac4D83J%F8DWDDhu".getBytes());// 使用CBC模式，需要一个向量iv

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

// 初始化，设置为解密模式

        cipher.init(Cipher.DECRYPT_MODE, k, iv);

// 执行操作

        return cipher.doFinal(data);

    }

    /**

     * 生成密钥

     *

     * @return byte[] 二进制密钥

     * @throws Exception

     */

    private static byte[] initKey(byte[] pwd) throws Exception {

        KeyGenerator kg = KeyGenerator.getInstance("AES");

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        random.setSeed(pwd);

        kg.init(128, random);

// 生成秘密密钥

        SecretKey secretKey = kg.generateKey();

// 获得密钥的二进制编码形式

        return secretKey.getEncoded();

    }

}
