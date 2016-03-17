import com.alibaba.fastjson.JSON;
import com.comichentai.controller.ComicController;
import com.comichentai.page.PageDto;
import com.comichentai.security.AESLocker;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{ComicController.class};
        SpringApplication.run(classes, args);
    }

    private static final String serverPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALIZ98KqgLW8IMt4G+N+4d3DiOiEa+5s6lCMSGE/NbU9stJEqw0EuCP54MY6JkT0HCYTCrLXqww6rSQyWF7BNCVGssk2XDcvSKiCz1ZMgabd6XVK5kvIycySydXQ0Ky6rnfxw8w2mllHABFvs1eamaHQozv18n/XGqemjW2BFy/jAgMBAAECgYAxT3FCi3SBXKnzy7hk/z9H6Bhi0C8V3z/stzpe+mJDYOa+wtZdD15wT4HFQFpSIwgcHo+Kvp2UEDbZ27qN2Y43AZbF9LOalWTRUzYtr8wL8MIbgtew/QQ9YFNWdkTZ6MxCItjD/mSz3Lrkcphvbsx4VoCVYIJ04r+Loi0t9g0guQJBANvkpfrq0bLVRYWfaigjkx47mr0trJkB7mjADe69IqtsM/2x5dHPpClDK78yzAWxU2BrYzOd31QIOm32iMIvRxUCQQDPWJPMOzcq8Jqs1PAM7D0hxnvF3tSJB0CJCQWdGFkJiuIYSbrWnCVF78jJyU2AK1H3RDi9BzGPL2Z3i2Si+9kXAkAPnKtAJl3fEY9PDmNuGCCA3AB/f/eqIV345/HVSm5kt1j1oSTNAa4JE/DOMWAU42MlDFrNtl69y5vCZOeOyeaFAkBOJieGmWcAozDZJWTYqg2cdk/eU08t2nLjc2gPPscIRrVSzC9EhhOyWV8HVv0D6s/471inPlfajNYFBp/Goj+/AkEAiejHX/58Vv8+ccW22RMZmyxiHcZpTw9hz7vHUCWv03+fyVGtGMhJ4xuPt8UaZm91yHSPWWarM8Xa7errKaXN9A==";
    private static final String serverPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyGffCqoC1vCDLeBvjfuHdw4johGvubOpQjEhhPzW1PbLSRKsNBLgj+eDGOiZE9BwmEwqy16sMOq0kMlhewTQlRrLJNlw3L0iogs9WTIGm3el1SuZLyMnMksnV0NCsuq538cPMNppZRwARb7NXmpmh0KM79fJ/1xqnpo1tgRcv4wIDAQAB";

    @Test
    public void testRSA() throws Exception {
        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(1);
        pageDto.setPageSize(20);
        String message = JSON.toJSONString(pageDto);
        message = "{\"token\":\"b7ad63ea0cf786a9ddf40d07b0003eea07352db3840325b9fc7092cdcecae1ef773fc55cc129169dc2802a43b6c34730e9800373f6ad7ebf5708bb1078591324\",\"pageMap\":\"b7ad63ea0cf786a9ddf40d07b0003eea07352db3840325b9fc7092cdcecae1ef773fc55cc129169dc2802a43b6c34730e9800373f6ad7ebf5708bb1078591324\"}";
        String encrypt1 = AESLocker.encrypt(message);
        System.out.println(encrypt1);
        String decrypt1 = AESLocker.decrypt(encrypt1);
        System.out.println(decrypt1);
        /*String encrypt = RSALocker.encrypt(message, serverPublicKey);
        System.out.println(encrypt);
        String decrypt = RSALocker.decrypt(encrypt, serverPrivateKey);
        System.out.println(decrypt);*/
    }


}
