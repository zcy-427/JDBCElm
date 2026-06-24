package ynu.edu;

import org.junit.jupiter.api.Test;
import ynu.edu.entity.Business;
import ynu.edu.service.AdminService;
import ynu.edu.service.BusinessService;
import ynu.edu.service.Impl.AdminServiceImpl;
import ynu.edu.service.Impl.BusinessServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 商家模块测试。
 * 覆盖商家登录、商家信息查询、商家信息修改、密码修改、非法参数校验。
 */
public class BusinessServiceTest {

    private final AdminService adminService = new AdminServiceImpl();
    private final BusinessService businessService = new BusinessServiceImpl();

    @Test
    public void testBusinessLoginAndUpdate() {
        Integer businessId = null;
        try {
            businessId = adminService.addBusiness(buildBusiness("JUnit商家模块测试店", "old123"));
            assertNotNull(businessId);

            Business loginBusiness = businessService.login(businessId, "old123");
            assertNotNull(loginBusiness);
            assertEquals("JUnit商家模块测试店", loginBusiness.getBusinessName());

            loginBusiness.setBusinessName("JUnit商家模块修改后店名");
            loginBusiness.setBusinessAddress("云南大学修改后地址");
            loginBusiness.setBusinessExplain("修改后的商家介绍");
            loginBusiness.setStarPrice(new BigDecimal("25.00"));
            loginBusiness.setDeliveryPrice(new BigDecimal("4.00"));

            assertTrue(businessService.updateBusiness(loginBusiness));

            Business updated = businessService.getBusinessById(businessId);
            assertNotNull(updated);
            assertEquals("JUnit商家模块修改后店名", updated.getBusinessName());
            assertEquals("云南大学修改后地址", updated.getBusinessAddress());

            assertTrue(businessService.updatePassword(businessId, "old123", "new123"));
            assertNull(businessService.login(businessId, "old123"));
            assertNotNull(businessService.login(businessId, "new123"));
        } finally {
            if (businessId != null) {
                adminService.deleteBusiness(businessId);
            }
        }
    }

    @Test
    public void testBusinessInvalidParam() {
        assertNull(businessService.login(null, "123456"));
        assertNull(businessService.login(0, "123456"));
        assertNull(businessService.login(1, ""));

        assertNull(businessService.getBusinessById(null));
        assertNull(businessService.getBusinessById(0));

        assertFalse(businessService.updateBusiness(null));
        assertFalse(businessService.updatePassword(null, "old", "new"));
        assertFalse(businessService.updatePassword(1, "", "new"));
        assertFalse(businessService.updatePassword(1, "old", ""));
    }

    private Business buildBusiness(String businessName, String password) {
        Business business = new Business();
        business.setPassword(password);
        business.setBusinessName(businessName);
        business.setBusinessAddress("云南大学商家测试地址");
        business.setBusinessExplain("JUnit商家模块测试数据");
        business.setStarPrice(new BigDecimal("18.00"));
        business.setDeliveryPrice(new BigDecimal("2.50"));
        return business;
    }
}
