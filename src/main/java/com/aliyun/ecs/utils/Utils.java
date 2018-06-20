package com.aliyun.ecs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company:    http://www.ronglian.com</p>
 * <p>Description: 工具类 </p>
 * <p>Author:chenyan/陈燕</p>
 */
public class Utils {
    static Log log = LogFactory.getLog(Utils.class);

    /**
     * <br/>Description:是否以Docker方式启动
     * <p>Author:chenyan/陈燕</p>
     *
     * @return
     */
    public static boolean isDockerStartMode() {
        boolean isDocker = false;
        //程序启动方式,可以取值docker，java;默认为java
        String startProgramMode = System.getenv("UEC_STARTUP_MODE");
        if (StringUtils.isBlank(startProgramMode)) {
            startProgramMode = "java";
        } else if (startProgramMode.equalsIgnoreCase("docker")) {
            isDocker = true;
        }
        return isDocker;
    }

    public static void initArgs(String[] args) {
        boolean isDocker = Utils.isDockerStartMode();
        //其中：spring.rabbitmq.host、uec.mysql.host、spring.redis.host的取值顺序：
        //如果配置文件中没有值，就用传进去的值；如果配置文件中有值，就用配置文件中的值
        if (isDocker) {
            //Docker 方式启动项目：
            //docker run --rm -d --name uec_log -v /Users/chenyan/tmp/log_operation:/opt/data/log_operation --link uec_config_server:config --link rabbit:rabbit --link redis:redis --link mysql:mysql
            //-e UEC_STARTUP_MODE=docker -e UEC_CONFIG_SERVER_HOST=config -e UEC_CONFIG_SERVER_PORT=8888 -e UEC_PROFILE=local uec/uec_log:0.0.1-SNAPSHOT
            //-e UEC_RABBITMQ_HOST='localhost' -e UEC_MYSQL_HOST='localhost' -e UEC_REDIS_HOST='localhost'
            log.info("The program will start in docker mode...");
            //config服务的主机ip地址
            String configServerHost = System.getenv("UEC_CONFIG_SERVER_HOST");
            if (StringUtils.isBlank(configServerHost)) {
                configServerHost = "config";
            }
            System.setProperty("uec.configServer.host", configServerHost);
            String configServerPort = System.getenv("UEC_CONFIG_SERVER_PORT");
            if (StringUtils.isBlank(configServerPort)) {
                configServerPort = "8888";
            }
            System.setProperty("uec.configServer.port", configServerPort);

            String profile = System.getenv("UEC_PROFILE");
//			if(StringUtils.isBlank(configServerPort)){
//				profile = "docker";
//			}
            if (StringUtils.isNoneBlank(profile)) {
                System.setProperty("uec.profile", profile);
            }

            String rabbitmqHost = System.getenv("UEC_RABBITMQ_HOST");
            if (StringUtils.isBlank(rabbitmqHost)) {
                rabbitmqHost = System.getProperty("spring.rabbitmq.host");
                if (StringUtils.isBlank(rabbitmqHost)) {
                    rabbitmqHost = "127.0.0.1";
                }
            }
            System.setProperty("spring.rabbitmq.host", rabbitmqHost);

            String mysqlHost = System.getenv("UEC_MYSQL_HOST");
            if (StringUtils.isBlank(mysqlHost)) {
                mysqlHost = System.getProperty("uec.mysql.host");
                if (StringUtils.isBlank(mysqlHost)) {
                    mysqlHost = "127.0.0.1";
                }
            }
            System.setProperty("uec.mysql.host", mysqlHost);

            String redisHost = System.getenv("UEC_REDIS_HOST");
            if (StringUtils.isBlank(redisHost)) {
                redisHost = System.getProperty("spring.redis.host");
                if (StringUtils.isBlank(redisHost)) {
                    redisHost = "127.0.0.1";
                }
            }
            System.setProperty("spring.redis.host", redisHost);
        } else {
            //普通项目启动方式，启动命令：
            //前台方式运行
            //java -Duec.profile='local' -Duec.configServer.host='localhost' -Duec.configServer.port='8888' -Dspring.rabbitmq.host='localhost' -Duec.mysql.host='localhost' -Dspring.redis.host='localhost' -jar xxxx.jar
            //后台方式运行，加上&符号
            //java -Duec.profile='local' -Duec.configServer.host='localhost' -Duec.configServer.port='8888' -Dspring.rabbitmq.host='localhost' -Duec.mysql.host='localhost' -Dspring.redis.host='localhost' - -jar xxxx.jar &
            //或者如果在eclipse里启动项目的话，需要在"Run/Debug Configuration..."里的"Arguments"里的"VM agruments"里加入配置：
            //-Duec.profile="dev" -Duec.configServer.host='127.0.0.1' -Duec.configServer.port='8888' -Dspring.rabbitmq.host='localhost' -Duec.mysql.host='localhost' -Dspring.redis.host='localhost'
            //程序将以普通java项目方式启动...
            log.info("The program will start in ordinary java project mode ...");
            if (StringUtils.isBlank(System.getProperty("uec.profile"))) {
                System.setProperty("uec.profile", "local");
            }
            if (StringUtils.isBlank(System.getProperty("uec.configServer.host"))) {
                System.setProperty("uec.configServer.host", "127.0.0.1");
            }
            if (StringUtils.isBlank(System.getProperty("uec.configServer.port"))) {
                System.setProperty("uec.configServer.port", "8888");
            }
            if (StringUtils.isBlank(System.getProperty("spring.rabbitmq.host"))) {
                System.setProperty("spring.rabbitmq.host", "127.0.0.1");
            }
            if (StringUtils.isBlank(System.getProperty("uec.mysql.host"))) {
                System.setProperty("uec.mysql.host", "127.0.0.1");
            }
            if (StringUtils.isBlank(System.getProperty("spring.redis.host"))) {
                System.setProperty("spring.redis.host", "127.0.0.1");
            }
            if (StringUtils.isBlank(System.getProperty("log.path.prefix"))) {
                //System.getProperty("user.home");//获取当前用户的home目录
                System.setProperty("log.path.prefix", System.getProperty("user.dir"));//如果没有传的话，将数据存放在当前项目的根目录下面
            }
        }

        log.info("uec.profile:" + System.getProperty("uec.profile"));
        log.info("uec.configServer.host:" + System.getProperty("uec.configServer.host"));
        log.info("uec.configServer.port:" + System.getProperty("uec.configServer.port"));
//		log.info("spring.rabbitmq.host:"+System.getProperty("spring.rabbitmq.host"));
//		log.info("uec.mysql.host:"+System.getProperty("uec.mysql.host"));
//		log.info("spring.redis.host:"+System.getProperty("spring.redis.host"));
        log.info("log.path.prefix:" + System.getProperty("log.path.prefix"));
        //添加对Config项目的判断
        StringBuffer urlStrSb = new StringBuffer("http://");
        urlStrSb.append(System.getProperty("uec.configServer.host"));
        urlStrSb.append(":");
        urlStrSb.append(System.getProperty("uec.configServer.port"));
        urlStrSb.append("/env");
        String urlStr = urlStrSb.toString();
        boolean isOk = false;
        int i = 0;
        while (isOk == false && i <= 4) {
            log.info("curl " + urlStr);
            isOk = isConnectable(urlStr);
            if(isOk){
                log.info("Config server is started.");
            }else {
                log.error("Config server is not started,sleep 5s.");
                try {
                    Thread.sleep(5 * 1000);//停止五秒后，再次访问
                } catch (InterruptedException e) {
                    log.error(e);
                    System.exit(0);
                }
            }
            i++;
        }
        if (isOk == false && i > 4) {
            log.error("Config server is not started,exit current program.Please start the config service.");
            System.exit(0);
        }
    }

//	public static void main(String[] args){
//		try{
//			String urlStr = "http://localhost:8888/env";
//			String result= getURLContent(urlStr);
//			System.out.println(result);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

    public static String getURLContent(String urlStr) throws MalformedURLException, UnsupportedEncodingException, IOException {
        URL url = null;
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (MalformedURLException e1) {
            throw e1;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw e;
            }
        }
        String result = sb.toString();
        return result;
    }

//    /**
//     * post方式请求http服务
//     * @param urlStr
//     * @param params   name=yxd&age=25
//     * @return
//     * @throws Exception
//     */
//    public static String getURLByPost(String urlStr,String params)throws Exception{
//        URL url=new URL(urlStr);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        conn.setDoInput(true);
//        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
//        printWriter.write(params);
//        printWriter.flush();        
//        BufferedReader in = null; 
//        StringBuilder sb = new StringBuilder(); 
//        try{   
//            in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8") ); 
//            String str = null;  
//            while((str = in.readLine()) != null) {  
//                sb.append( str );   
//            }   
//         } catch (Exception ex) { 
//            throw ex; 
//         } finally{  
//          try{ 
//              conn.disconnect();
//              if(in!=null){
//                  in.close();
//              }
//              if(printWriter!=null){
//                  printWriter.close();
//              }
//          }catch(IOException ex) {   
//              throw ex; 
//          }   
//         }   
//         return sb.toString(); 
//    }


    public static void initArgs2(String[] args) {
        final String ENV_KEY = "UEC_OPTS";//环境变量key
        final String DOCKER_CMD = "--uec.docker";//命令
        final String CONFIG_SERVER_HOST_CMD = "--uec.configServer.host";//统一配置服务的主机
        final String CONFIG_SERVER_PORT_CMD = "--uec.configServer.port";//统一配置服务的端口
        final String PROFILE_CMD = "--uec.profile";//指定环境，value可以取值：dev/prod/test
        final String API_URL = "--uec.api.url";//api服务的地址

//		String configServerHostEnvKey = "UEC_CONFIG_SERVER_HOST";//环境变量的key —— 统一Config服务的主机
//		String configServerPostEnvKey = "UEC_CONFIG_SERVER_PORT";//环境变量的key —— 统一Config服务的端口
//		String profileEnvKey = "UEC_PROFILE";//环境变量的key —— 环境；value可以取值：dev/prod/test


        if (args.length > 0) {
            boolean isDocker = Arrays.asList(args).contains(DOCKER_CMD);
//			boolean setHost = Arrays.asList(args).contains("--configServer.host");
//			boolean setPort = Arrays.asList(args).contains("--configServer.port");
            if (isDocker) {//以Docker方式启动，从系统环境变量中读取Config服务的配置
//				if(setHost || setPort){
//					if(!setHost){
//						System.out.println("没有传入参数--configServer.host，默认将设置该值为127.0.0.1");
//					}
//					if(!setPort){
//						System.out.println("没有传入参数--configServer.port，默认将设置该值为8888");
//					}
//				}else{
                System.out.println("程序将以Docker方式启动...");
                String envValue = System.getenv(ENV_KEY);
                System.out.println(envValue);
                if (StringUtils.isBlank(envValue)) {
                    log.error("当传入参数" + DOCKER_CMD + "时，必须先配置环境变量：" + ENV_KEY);
                    log.error("程序退出！");
                    System.exit(0);
                }
                String[] subEnvValue = envValue.split(" ");
                for (String string : subEnvValue) {
                    if (!string.contains("=")) {
                        log.error("环境变量：" + ENV_KEY + "；不符合规则，环境变量的形式，比如：--configServer.host=172.16.153.153 --configServer.port=8888 –-profiles=dev");
                        log.error("程序退出！");
                        System.exit(0);
                    } else {
                        String key = string.split("=")[0];
                        String val = string.split("=")[1];
                        switch (key) {
                            case CONFIG_SERVER_HOST_CMD:
                                System.setProperty("uec.configServer.host", val);
                                break;
                            case CONFIG_SERVER_PORT_CMD:
                                System.setProperty("uec.configServer.port", val);
                                break;
                            case PROFILE_CMD:
                                System.setProperty("uec.profile", val);
                                break;
                            case API_URL:
                                System.setProperty("api.url", val);
                                break;
                            default:
                                break;
                        }
                    }
                }
//					String host = System.getenv(configServerHostEnvKey);
//					String port = System.getenv(configServerPostEnvKey);
//					String profile = System.getenv(profileEnvKey);
//					System.out.println(StringUtils.isBlank(host));
//					System.out.println(StringUtils.isBlank(port));
//					if(StringUtils.isBlank(host) || StringUtils.isBlank(port)){
//						log.error("当传入参数"+DOCKER_CMD+"时，必须先配置环境变量："+configServerPostEnvKey+"和"+configServerPostEnvKey);
//						log.error("程序退出！");
//						System.exit(0);
//					}
//					System.setProperty("configServer.host",host);
//					System.setProperty("configServer.port",port);
//					if(StringUtils.isBlank(profile)){
//						log.info("没有配置环境变量UEC_PROFILE，默认取值为dev");
//					}else{
//						System.setProperty("config.profile",profile);
//					}
//				}
            } else {//以普通项目方式启动
                System.out.println("程序将以普通java项目方式启动...");
                List<String> params = Arrays.asList(args);
                if (params.size() > 0) {
                    Map<String, String> paramsMap = new HashMap<>();
                    for (String param : params) {
                        if (param.contains("=")) {
                            String[] paramSplit = param.split("=");
                            if (paramSplit.length > 1) {
                                paramsMap.put(paramSplit[0], paramSplit[1]);
                            } else {
                                paramsMap.put(paramSplit[0], null);
                            }
                        }
                    }
                    Set<String> keySet = paramsMap.keySet();
                    for (String key : keySet) {
                        switch (key) {
                            case CONFIG_SERVER_HOST_CMD:
                                System.setProperty("uec.configServer.host", paramsMap.get(CONFIG_SERVER_HOST_CMD));
                                break;
                            case CONFIG_SERVER_PORT_CMD:
                                System.setProperty("uec.configServer.port", paramsMap.get(CONFIG_SERVER_PORT_CMD));
                                break;
                            case PROFILE_CMD:
                                System.setProperty("uec.profile", paramsMap.get(PROFILE_CMD));
                                break;
                            case API_URL:
                                System.setProperty("uec.api.url", paramsMap.get(API_URL));
                                break;
                            default:
                                break;
                        }
                        if (StringUtils.isBlank(paramsMap.get(CONFIG_SERVER_HOST_CMD))) {
                            System.setProperty("uec.configServer.host", "127.0.0.1");
                        }
                        if (StringUtils.isBlank(paramsMap.get(CONFIG_SERVER_PORT_CMD))) {
                            System.setProperty("uec.configServer.port", "8888");
                        }
                        if (StringUtils.isBlank(paramsMap.get(PROFILE_CMD))) {
                            System.setProperty("uec.profile", "dev");
                        }
                    }
                }
            }
//			System.out.println("设置config服务的相关配置完毕，config服务的host是"+System.getProperty("configServer.host")+"，config服务的port是"+System.getProperty("configServer.port")+"，准备启动应用程序...");
        } else {
            log.info("没有传入参数，项目启动默认的Config服务的Host为：127.0.0.1；Config服务的端口号为：8888；默认环境为：dev");
        }
        System.out.println("----------------------------------------------");
        System.out.println("uec.profile:" + System.getProperty("uec.profile"));
        System.out.println("uec.configServer.host:" + System.getProperty("uec.configServer.host"));
        System.out.println("uec.configServer.port:" + System.getProperty("uec.configServer.port"));
        System.out.println("----------------------------------------------");
    }

    public static String toJson(Object obj) {
//		return JSON.toJSONStringWithDateFormat(obj,Constant.SYSTEM_DATE_PATTERN,SerializerFeature.WriteDateUseDateFormat);
        if (null == obj) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> parseJson2List(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    public static <T> T parseJsonTypeReference(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference, Feature.AutoCloseSource);
    }


    /**
//     * <br/>Description:根据相对路径(相对于classpath的路径)，获取properties文件里的key对应的value
//     * <p>Author:chenyan/陈燕</p>
//     *
//     * @param relativePath 相对路径(相对于classpath的路径)；比如：errorcode.properties
//     * @param key
//     * @return value
//     * @throws IOException
//     */
//    public static String getPropertiesValue(String relativePath, String key) throws IOException {
//        Properties properties = PropertiesLoaderUtils.loadAllProperties(relativePath);
////		String value = new String(properties.getProperty(key).getBytes("iso-8859-1"),"utf-8");
//        String value = properties.getProperty(key);
//        return value;
//    }

    /**
     * <br/>Description:获取MD5值
     * <p>Author:chenyan/陈燕</p>
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <br/>Description:ip转数字
     * <p>Author:xpguo/郭晓鹏</p>
     *
     * @param ip
     * @return
     */
    public static long ipToLong(String ip) {
        long l = 0;
        String[] ipAddressInArray = ip.split("\\.");
        for (int i = 3; i >= 0; i--) {

            long ipNum = Long.parseLong(ipAddressInArray[3 - i]);

            l |= ipNum << (i * 8);
        }
        return l;
    }

    /**
     * <br/>Description:数字转ip
     * <p>Author:xpguo/郭晓鹏</p>
     *
     * @param i
     * @return
     */
    public static String longToIp(long i) {

        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);

    }

    /**
     * <br/>Description:验证邮箱格式
     * <p>Author:zhangliang03/张亮</p>
     *
     * @param mail
     * @return
     */
    public static boolean checkMail(String mail) {
        boolean result = false;
        String check = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pattern = Pattern.compile(check);
        Matcher matcher = pattern.matcher(mail);
        result = matcher.matches();
        return result;
    }

    /**
     * <br/>Description:获取本机的IP地址
     * <p>Author:chenyan/陈燕</p>
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getLocalHostIpAddress() {
        if (Utils.isDockerStartMode()) {
            String hostIp = System.getenv("UEC_HOST_IP");
            if (StringUtils.isBlank(hostIp)) {
                return "localhost";
            } else {
                if (Utils.ping(hostIp)) {
                    return hostIp;
                } else {
                    return null;
                }
            }
        } else {
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                return addr.getHostAddress().toString();
            } catch (UnknownHostException e) {
                log.error("get local host ip address failed!");
                log.error(Utils.class, e);
            }
            return "localhost";
        }
    }

    /**
     * Created by IntelliJ IDEA.
     * @Author: chenyan
     * @Description: 测试Http请求是否可以通
     * @param: http的请求
     * @return:
     * @Date: 2018/5/17 下午2:08
     */
    public static boolean isConnectable(String httpUrl){
        try {
            URL urlObj = new URL(httpUrl);
            HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();
            oc.setUseCaches(false);
            oc.setConnectTimeout(3000); // 设置超时时间
            int status = oc.getResponseCode();// 请求状态
            if (200 == status) {
                // 200是请求地址顺利连通。。
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return false;
    }

//    public static boolean isConnectable(String urlStr){
//        try {
//            String result = getURLContent(urlStr);
//            if (StringUtils.isNotBlank(result)) {
//                log.info("curl " + urlStr);
//                log.info("url is connectable.url:"+urlStr);
//                return true;
//            }
//            return false;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    /**
     * <br/>Description:获取API服务地址
     * <p>Author:chenyan/陈燕</p>
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getApiUrl() {
        String apiUrl;
        if (Utils.isDockerStartMode()) {
            apiUrl = System.getenv("UEC_API_URL");
        } else {
            apiUrl = System.getProperty("api.url");
        }

        if (StringUtils.isNotBlank(apiUrl)) {
            if (isConnectable(apiUrl)) {
                log.info("Connect to the API service successful!");
                return apiUrl;
            } else {
                log.error("Failed to connect API service!");
                return apiUrl;
            }
        }else{
            log.error("api service url is null .");
            return apiUrl;
        }
    }


    /**
     * <br/>Description:生成指定位数的随机密码
     * <p>Author:chenyan/陈燕</p>
     *
     * @param length 位数
     * @return 密码
     */
    public static String getRandomPassword(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * <br/>Description:生成一个OpensStack的UUID(不带"-"的32位字符串)
     * <p>Author:chenyan/陈燕</p>
     *
     * @return
     */
    public static String getOSUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    /**
     * <br/>Description:成一个UUID(带"-")
     * <p>Author:chenyan/陈燕</p>
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * <br/>Description:格式化当前时间,时间格式默认为：yyyy-MM-dd HH:mm:ss
     * <p>Author:chenyan/陈燕</p>
     * @return
     */
//    public static String formatDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat(Constant.SYSTEM_DATE_PATTERN);
//        return sdf.format(new Date());
//    }

    /**
     * <br/>Description:格式化时间,时间格式默认为：yyyy-MM-dd HH:mm:ss
     * <p>Author:chenyan/陈燕</p>
     *
     * @param date
     * @return
     */
//    public static String formatDate(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat(Constant.SYSTEM_DATE_PATTERN);
//        return sdf.format(date);
//    }

    /**
     * <br/>Description:格式化时间
     * <p>Author:chenyan/陈燕</p>
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

//    public static String encrypt(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
//    	//用DES或者AES就可以了，Java都内置了支持
//    	//生成一个key，只应该生成一次。
//    	SecretKey key = KeyGenerator.getInstance("DES").generateKey();
//		Cipher cipher = Cipher.getInstance("DES");
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//		byte[] src = str.getBytes();
//		//注意，encrypt不能直接new String，
//		//如需要变成可见字符可用new sun.misc.BASE64Encoder().encode(encrypt)
//		byte[] encrypt = cipher.doFinal(src);
//
//		//这里是保存key的方法，应该通过保存keyCode来使用同一个key
//		byte[] keyCode = key.getEncoded();
//		key = new SecretKeySpec(keyCode, "DES");
//
//		cipher.init(Cipher.DECRYPT_MODE, key);
//		src = cipher.doFinal(encrypt);
//		return str;
//    }

    private static final String Algorithm = "DESede"; // 定义 加密算法,可用DES,DESede,Blowfish

    /**
     * 根据加密密钥 获取 加密后的byte数组
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param src     被加密的数据缓冲区（源）
     * @return 加密后的数组
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

        // 加密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    /**
     * 根据加密密钥 和 加密后的byte数组 获取 加密之前的byte数组
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param src     加密后的缓冲区
     * @return 解密后的数组
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

        // 解密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    /**
     * 生成 加密密钥，长度为24字节
     *
     * @param username
     * @return
     */
    public static byte[] hex(String username) {
        String key = "uec";//关键字  
        String f = DigestUtils.md5Hex(username + key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i = 0; i < 24; i++) {
            enk[i] = bkeys[i];
        }
        return enk;
    }

    public static byte[] uecHex() {
        return hex("test");
    }


//    @SuppressWarnings("restriction")
//    public static String encrypt(String str) throws UecEncryptException {
//        if (StringUtils.isEmpty(str)) {
//            return null;
//        }
//        final byte[] enk = uecHex();
//        byte[] encoded;
//        try {
//            encoded = encryptMode(enk, str.getBytes());
//            String pword = new sun.misc.BASE64Encoder().encode(encoded);
//            return pword;
//        } catch (InvalidKeyException | NoSuchAlgorithmException
//                | NoSuchPaddingException | IllegalBlockSizeException
//                | BadPaddingException e) {
//            throw new UecEncryptException(e);
//        }
//    }

    @SuppressWarnings("restriction")
//    public static String decrypt(String str) throws UecDecryptException {
//        if (StringUtils.isEmpty(str)) {
//            return null;
//        }
//        final byte[] enk = uecHex();
//        byte[] reqPassword;
//        try {
//            reqPassword = new sun.misc.BASE64Decoder().decodeBuffer(str);
//            byte[] srcBytes;
//            srcBytes = decryptMode(enk, reqPassword);
//            return new String(srcBytes);
//        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException
//                | NoSuchPaddingException | IllegalBlockSizeException
//                | BadPaddingException e) {
//            throw new UecDecryptException(e);
//        }
//    }

//	public static void main(String[] args) throws UecDecryptException{
//		System.out.println(decrypt("SSCN+TNAvrmfg9zwJHyyeQ=="));
//	}

//	public static void main(String[] args) throws UecEncryptException, UecDecryptException {
//	// 添加新安全算法,如果用JCE就要把它添加进去
//	Security.addProvider(new com.sun.crypto.provider.SunJCE());
//
//	// 24字节的密钥（我们可以取apk签名的指纹的前12个byte和后12个byte拼接在一起为我们的密钥）
////	final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 };
//	final byte[] enk = hex("test");
//	String password = "This is a 3DES test.测试";
//
//	System.out.println("加密前的字符串:" + password);
//
//	byte[] encoded = encryptMode(enk,password.getBytes());  
//    String pword = new sun.misc.BASE64Encoder().encode(encoded);
//    System.out.println("加密后的字符串:" + pword); 
//
//    byte[] reqPassword = new sun.misc.BASE64Decoder().decodeBuffer(pword);  
//    byte[] srcBytes = decryptMode(enk,reqPassword);  
//    System.out.println("解密后的字符串:" + (new String(srcBytes)));

//		String str = "0DdiygzFcrmi";
//		System.out.println("加密前的字符串:" + str);
//		String encryptedStr = encrypt(str);
//		System.out.println("加密后的字符串:" + encryptedStr); 
//		String decryptedStr = decrypt(encryptedStr);

//		String str = "b9b+kxyDGH7pTTWDkVej+Q==";
//		String encryptedStr = decrypt(str);
//		System.out.println("解密后的字符串:" + encryptedStr); 


    //------测试环境--------
//		加密前的字符串:i2YrrxdUb1oa
//		加密后的字符串:NGMZDSe4wFtAScM8TYMf/g==
//		解密后的字符串:i2YrrxdUb1oa

//		加密前的字符串:5P3GllAnefJJ
//		加密后的字符串:1p4pbFa5XnmYIkP5ineX8A==
//		解密后的字符串:5P3GllAnefJJ

//		加密前的字符串:uMTbHykBAZmW
//		加密后的字符串:YjFbyNyaaUBM9NI9xqIFng==
//		解密后的字符串:uMTbHykBAZmW

//		加密前的字符串:rbRkcY1UNHKg
//		加密后的字符串:jwZh2IRU6Jd6UxtJR1lDrQ==
//		解密后的字符串:rbRkcY1UNHKg

//		加密前的字符串:iUc8yXrw5Cdp
//		加密后的字符串:/Gu+H5Nt+Srtivo55dB01w==
//		解密后的字符串:iUc8yXrw5Cdp

//		加密前的字符串:O7kgkE5hENol
//		加密后的字符串:vOkYP9xCRvPzXFt0mginvg==
//		解密后的字符串:O7kgkE5hENol

//		加密前的字符串:y40L8K04XlU4
//		加密后的字符串:wUjsFP7D4JN3n3k7syuhMA==
//		解密后的字符串:y40L8K04XlU4

//		加密前的字符串:0DdiygzFcrmi
//		加密后的字符串:8GgsjtTYPfYi4MVoLsO55w==
//		解密后的字符串:0DdiygzFcrmi

    //-------研发环境---------
//		加密前的字符串:fk3peffyaTs2
//		加密后的字符串:zoUEbjY7IJjjh3SJCMnltQ==
//		解密后的字符串:fk3peffyaTs2

//		加密前的字符串:0xIdWtfdWVb1
//		加密后的字符串:lm3tsgVgBG0A2VICOA+QFg==
//		解密后的字符串:0xIdWtfdWVb1

//		加密前的字符串:qo84k7bbFr3d
//		加密后的字符串:mbNUjTVrtrmfpVYOA1qi5g==
//		解密后的字符串:qo84k7bbFr3d

//		加密前的字符串:RkcvtPZWgkD1
//		加密后的字符串:RsFaB9VIfLmHGTmiE0gPrQ==
//		解密后的字符串:RkcvtPZWgkD1

//		加密前的字符串:wQPcwMCoMRWE
//		加密后的字符串:LpbUpo96l1Tq8E+VKP3Jlg==
//		解密后的字符串:wQPcwMCoMRWE
//	}

    public static boolean ping(String ip) {
        Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
        Process process = null; // 声明处理类对象
        String line = null; // 返回行信息
        InputStream is = null; // 输入流
        InputStreamReader isr = null;// 字节流
        BufferedReader br = null;
        boolean res = false;// 结果
        try {
            process = runtime.exec("ping " + ip); // PING
            is = process.getInputStream(); // 实例化输入流
            isr = new InputStreamReader(is);// 把输入流转换成字节流
            br = new BufferedReader(isr);// 从字节中读取文本
            while ((line = br.readLine()) != null) {
                if (line.contains("TTL") || line.contains("ttl")) {
                    res = true;
                    break;
                }
            }
            is.close();
            isr.close();
            br.close();
            if (res) {
                log.info("ping \"" + ip + "\" is OK!");
            } else {
                log.info("ping \"" + ip + "\" is failed!");
            }
        } catch (IOException e) {
            log.error(e);
            runtime.exit(1);
        }
        return res;
    }

    /**
     * <br/>Description:获取指定月后的Date
     * <p>Author:chenyan/陈燕</p>
     *
     * @param months
     * @return
     */
    public static Date getDateByMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, months);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * <br/>Description:获取指定日期的指定月份后的Date
     * <p>Author:chenyan/陈燕</p>
     *
     * @param months
     * @return
     */
    public static Date getDateByMonths(Date originalDate, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.MONTH, months);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * <br/>Description:判断是否是一个ip地址
     * <p>Author:chenyan/陈燕</p>
     *
     * @param ip
     * @return
     */
    public static boolean isIpAddress(String ip) {
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * <br/>Description: byte[] 转 InputStream
     * <p>Author:zhangliang03/张亮</p>
     *
     * @param buf
     * @return
     */
    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    /**
     * <br/>Description: InputStream 转 byte[]
     * <p>Author:zhangliang03/张亮</p>
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String getCurrentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(c);
    }

    /**
     * <br/>Description:获取指定长度的随机数
     * <p>Author:zhangliang03/张亮</p>
     *
     * @param length 需要的长度
     * @return
     */
    public static String getRandom(int length) {
        //获取一个随机数，小数点后17位
        double rand = Math.random();
        //将随机数转换为字符串
        String str = String.valueOf(rand).replace("0.", "");
        //截取字符串
        String newStr = str.substring(0, length);
        return newStr;
    }

    //分隔符号
    private final static String SPLIT_MARK = "_&";
    private final static String TENANT_MARK = "tenantId#";
    private final static String PROJECT_MARK = "projectId#";

    /**
     * <br/>Description:根据用户名、租户id、项目id获取咱们拼接的用户名(uec特殊业务:租户、项目)；得到一个形如：chenyan_&tenantId#1_&projectId#1的字符串
     * <p>Author:chenyan/陈燕</p>
     *
     * @param username
     * @param tenantId
     * @param projectId
     * @return
     */
    public static String getUserNameWith(String username, Long tenantId, Long projectId) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isEmpty(username)) {
            return null;
        } else {
            sb.append(username);
        }
        if (null != tenantId) {
            sb.append(SPLIT_MARK);
            sb.append(TENANT_MARK);
            sb.append(tenantId);
        }
        if (null != projectId) {
            sb.append(SPLIT_MARK);
            sb.append(PROJECT_MARK);
            sb.append(projectId);
        }
        return sb.toString();
    }

    /**
     * <br/>Description:从拼接字符串中获取用户名、租户id、项目id
     * <p>Author:chenyan/陈燕</p>
     *
     * @param usernameWith
     * @return
     */
//    public static Map<String, String> getUserInfoMap(String usernameWith) {
//        Map<String, String> map = new HashMap<>();
//        String username = null;
//        String tenantId = null;
//        String projectId = null;
//        if (usernameWith.contains(SPLIT_MARK)) {
//            String[] strArray = usernameWith.split(SPLIT_MARK);
//            username = strArray[0];
//            map.put("username", username);
//            for (String string : strArray) {
//                if (string.contains(TENANT_MARK)) {
//                    tenantId = string.split("#")[1];
//                    map.put("tenantId", tenantId);
//                }
//                if (string.contains(PROJECT_MARK)) {
//                    projectId = string.split("#")[1];
//                    map.put("projectId", projectId);
//                }
//            }
//        } else {
//            map.put("username", usernameWith);
//        }
//        return map;
//    }

    /**
     * <br/>Description:从拼接字符串中获取用户名
     * <p>Author:chenyan/陈燕</p>
     *
     * @param usernameWith
     * @return
     */
//    public static String getUserName(String usernameWith) {
//        Map<String, String> map = getUserInfoMap(usernameWith);
//        return map.get("username");
//    }

    /**
     * <br/>Description:从拼接字符串中获取租户id
     * <p>Author:chenyan/陈燕</p>
     *
     * @param usernameWith
     * @return
     */
//    public static Long getTenantId(String usernameWith) {
//        Long tenantId = null;
//        Map<String, String> map = getUserInfoMap(usernameWith);
//        if (StringUtils.isNotBlank(map.get("tenantId"))) {
//            tenantId = Long.valueOf(map.get("tenantId"));
//        }
//        return tenantId;
//    }

    /**
     * <br/>Description:从拼接字符串中获取项目id
     * <p>Author:chenyan/陈燕</p>
     *
     * @param usernameWith
     * @return
     */
//    public static Long getProjectId(String usernameWith) {
//        Long projectId = null;
//        Map<String, String> map = getUserInfoMap(usernameWith);
//        if (StringUtils.isNotBlank(map.get("projectId"))) {
//            projectId = Long.valueOf(map.get("projectId"));
//        }
//        return projectId;
//    }
////
//    /**
//     * <br/>Description: 裸金属，对附加属性进行处理和验证
//     * <p>Author:zhangliang03/张亮</p>
//     *
//     * @return
//     */
//    public static Map<String, String> checkExtra(String extra) throws UecBaseException {
//        Map<String, String> extraMap = new HashMap<>();
//        if(StringUtils.isNotBlank(extra)) {
//        	String[] extraList = extra.split(",");
//        	for (int i = 0; i < extraList.length; i++) {
//        		String[] extraTemp = extraList[i].split("=");
//        		if(extraTemp.length != 2){
//        			throw new UecBaseException("000000013");
//        		}
//        		extraMap.put(extraTemp[0], extraTemp[1]);
//        	}
//        }
//        return extraMap;
//    }


    /**
     * Created by IntelliJ IDEA.
     * @Author: chenyan
     * @Description: 将id字符串转换为List<Long>
     * @param: ids字符串，一般用","分隔
     * @param: 分隔符，一般用","分隔
     * @return: List<Long>
     * @Date: 2018/5/15 下午8:16
//     */
//    public static List<Long> idsString2LongList(String ids, String regex){
//        if(StringUtils.isBlank(ids)){
//            return null;
//        }
//        if(null == regex){
//            regex = ",";
//        }
//        List<Long> idList = new ArrayList<>();
//        String[] idsArrray = ids.split(regex);
//        for (String idStr : idsArrray){
//            idList.add(Long.valueOf(idStr));
//        }
//        return idList;
//    }

    /**
     * Created by IntelliJ IDEA.
     * @Author: chenyan
     * @Description: 将id字符串转换为List<Long>
     * @param: ids字符串，用","分隔
     * @return: List<Long>
     * @Date: 2018/5/15 下午8:16
     */
//    public static List<Long> idsString2LongList(String ids){
//        return idsString2LongList(ids,null);
//    }
}
