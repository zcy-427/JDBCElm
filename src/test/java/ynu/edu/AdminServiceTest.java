package ynu.edu;

import org.junit.jupiter.api.Test;
import ynu.edu.entity.Business;
import ynu.edu.service.AdminService;
import ynu.edu.service.Impl.AdminServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 管理员模块测试。
 * 覆盖管理员登录、商家新增、商家查询、商家搜索、商家删除。
 */
public class AdminServiceTest {
    private final AdminService adminService = new AdminServiceImpl();

    @Test
    public void testAdminLoginSuccess() {
        assertTrue(adminService.login("admin", "123456"));
    }

    @Test
    public void testAdminLoginFail() {
        assertFalse(adminService.login("admin", "wrong_password"));
    }

    @Test
    public void testAdminLoginBlankParam() {
        assertFalse(adminService.login("", "123456"));
        assertFalse(adminService.login("admin", ""));
        assertFalse(adminService.login(null, "123456"));
    }

    @Test
    public void testAddFindSearchAndDeleteBusiness() {
        Integer businessId = null;
        try {
            Business business = buildBusiness("JUnit管理员测试商家");

            businessId = adminService.addBusiness(business);
            assertNotNull(businessId);
            assertTrue(businessId > 0);

            Business saved = adminService.getBusinessById(businessId);
            assertNotNull(saved);
            assertEquals("JUnit管理员测试商家", saved.getBusinessName());
            assertEquals("云南大学JUnit测试地址", saved.getBusinessAddress());

            List<Business> searchResult = adminService.searchBusinesses("JUnit管理员", "云南大学");
            assertNotNull(searchResult);
            boolean containsInsertedBusiness = false;
            for (Business item : searchResult) {
                if (businessId.equals(item.getBusinessId())) {
                    containsInsertedBusiness = true;
                    break;
                }
            }
            assertTrue(containsInsertedBusiness);
        } finally {
            if (businessId != null) {
                assertTrue(adminService.deleteBusiness(businessId));
                assertNull(adminService.getBusinessById(businessId));
            }
        }
    }

    private Business buildBusiness(String businessName) {
        Business business = new Business();
        business.setPassword("123456");
        business.setBusinessName(businessName);
        business.setBusinessAddress("云南大学JUnit测试地址");
        business.setBusinessExplain("JUnit测试插入商家数据");
        business.setStarPrice(new BigDecimal("20.00"));
        business.setDeliveryPrice(new BigDecimal("3.00"));
        return business;
    }
}
