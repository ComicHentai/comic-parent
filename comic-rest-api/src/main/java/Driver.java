import com.alibaba.fastjson.JSON;
import com.comichentai.controller.ComicController;
import com.comichentai.controller.LockerController;
import com.comichentai.page.PageDto;
import com.comichentai.security.AESLocker;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{ComicController.class, LockerController.class};
        SpringApplication.run(classes, args);
    }

}
