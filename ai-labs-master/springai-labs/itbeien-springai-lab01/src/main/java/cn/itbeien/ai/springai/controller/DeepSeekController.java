package cn.itbeien.ai.springai.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class DeepSeekController {

    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static final String API_KEY = "sk-423d40006c0e4e02840bd6fb69b7c6f0"; // 替换为你的 API Key

    public String askDeepSeek(String question) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // 创建 HTTP POST 请求
        HttpPost request = new HttpPost(API_URL);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + API_KEY);

        // 动态构建请求体
        String requestBody = String.format(
                "{\"model\": \"deepseek-chat\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"stream\": false}",
                question
        );
        System.out.println(requestBody);
        request.setEntity(new StringEntity(requestBody));

        // 发送请求并获取响应
        try (CloseableHttpResponse response = client.execute(request)) {
            // 返回响应内容
            return EntityUtils.toString(response.getEntity());
        }
    }

    public static void main(String[] args) {
        try {
            DeepSeekController controller = new DeepSeekController();
            String response = controller.askDeepSeek("你是谁");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
