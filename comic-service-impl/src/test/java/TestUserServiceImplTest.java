import com.alibaba.fastjson.JSON;
import com.comichentai.dto.TestUserDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.helper.SpringTestHelper;
import com.comichentai.service.TestUserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by hope6537 on 16/2/15.
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class TestUserServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestUserService testUserService;

    @Test
    public void testGetTestUserById() {
        ResultSupport<TestUserDto> resultSupport = testUserService.getTestUserById(1);
        logger.error(JSON.toJSONString(resultSupport));
    }

}
