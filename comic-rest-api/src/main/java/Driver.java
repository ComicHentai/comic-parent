import com.comichentai.controller.ComicController;
import com.comichentai.controller.LockerController;
import com.comichentai.controller.WelcomeController;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{ LockerController.class,ComicController.class, WelcomeController.class};
        SpringApplication.run(classes, args);
    }

}
