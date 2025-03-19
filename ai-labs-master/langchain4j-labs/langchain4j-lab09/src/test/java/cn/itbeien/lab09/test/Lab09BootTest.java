package cn.itbeien.lab09.test;

import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Lab09BootTest {
    /**
     * 文生图
     */
    @Test
    public void test(){
        WanxImageModel model =  WanxImageModel.builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .build();
        Response<Image> response = model.generate("画一辆车");
        System.out.println(response.content().url());
    }
}
