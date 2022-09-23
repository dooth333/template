package com.wenc.template.utils;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Component
public class GithubUploader {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubUploader.class);

    public static final String URI_SEPARATOR = "/";

    //添加或删除后缀时一定要把下面的错误提示也改一下
    public static final Set<String> ALLOW_FILE_SUFFIX = new HashSet<>(Arrays.asList("jpg", "png", "jpeg", "gif"));

    @Value("${github.bucket.url}")
    private String url;

    @Value("${github.bucket.api}")
    private String api;

    @Value("${github.bucket.access-token}")
    private String accessToken;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 上传文件到Github
     * @param multipartFile
     * @return 文件的访问地址
     * @throws IOException
     */
    public Map<String,Object> upload (MultipartFile multipartFile) throws IOException {

        Map<String,Object> map = new HashMap<>();
        String suffix = this.getSuffix(multipartFile.getOriginalFilename()).toLowerCase();
        //判断文件后缀是否符和
        if (!ALLOW_FILE_SUFFIX.contains(suffix)) {
            map.put("error","选择的文件后缀不符合图片后缀,请选择jpg,png,jpeg,gif后缀图片提交");
            return map;
            /*throw new IllegalArgumentException("不支持的文件后缀：" + suffix);*/
        }else {
            // 重命名文件
            String fileName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;

            // 目录按照日期打散
            String[] folders = this.getDateFolder();

            // 最终的文件路径
            String filePath = new StringBuilder(String.join(URI_SEPARATOR, folders)).append(fileName).toString();

            LOGGER.info("上传文件到Github：{}", filePath);

            JsonObject payload = new JsonObject();
            payload.add("message", new JsonPrimitive("file upload"));
            payload.add("content", new JsonPrimitive(Base64.getEncoder().encodeToString(multipartFile.getBytes())));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + this.accessToken);

            if (payload != null){
                ResponseEntity<String> responseEntity = this.restTemplate.exchange(this.api + filePath, HttpMethod.PUT,
                        new HttpEntity<String>(payload.toString(), httpHeaders), String.class);

                if (responseEntity.getStatusCode().isError()) {
                    // TODO 上传失败
                }

                JsonObject response = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
                LOGGER.info("上传完毕: {}", response.toString());

/*            System.out.println("response:"+response);
            System.out.println("content:"+response.get("content"));
            JsonElement element = response.get("content");
            JsonObject asJsonObject = element.getAsJsonObject();
            String download_url = asJsonObject.get("download_url").getAsString();
            System.out.println("download_url:"+download_url);*/

            }

            map.put("newUrl",this.url + filePath);
            return map;
        }

    }

    /**
     * 获取文件的后缀
     * @param fileName
     * @return
     */
    protected String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法的文件名称：" + fileName);
    }

    /**
     * 按照年月日获取打散的打散目录
     * yyyy/mmd/dd
     * @return
     */
    protected String[] getDateFolder() {
        String[] retVal = new String[3];

        LocalDate localDate = LocalDate.now();
        retVal[0] = localDate.getYear() + "";

        int month = localDate.getMonthValue();
        retVal[1] = month < 10 ? "0" + month : month + "";

        int day = localDate.getDayOfMonth();
        retVal[2] = day < 10 ? "0" + day : day + "";

        return retVal;
    }
}
