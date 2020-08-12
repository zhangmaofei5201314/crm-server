package test;

import com.donbala.CrmServerApplication;
import com.donbala.userManagement.service.CmsUserServiceIntf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2020/8/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrmServerApplication.class)
public class TestC {

    @Autowired
    private CmsUserServiceIntf cmsUserServiceIntf;

    @Test
    public void test1(){
        List<Map<String, Object>> list = cmsUserServiceIntf.getAntdUserMenuByCode("001");

        System.out.println(list.toString());

    }


}
