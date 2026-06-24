package ynu.edu;

import org.junit.jupiter.api.Test;
import ynu.edu.entity.Business;
import ynu.edu.entity.Food;
import ynu.edu.service.AdminService;
import ynu.edu.service.FoodService;
import ynu.edu.service.Impl.AdminServiceImpl;
import ynu.edu.service.Impl.FoodServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 事务测试。
 * 验证删除商家时同时删除其食品数据，体现 JDBC 手动事务控制。
 */
public class TransactionTest {

    private final AdminService adminService = new AdminServiceImpl();
    private final FoodService foodService = new FoodServiceImpl();

    @Test
    public void testDeleteBusinessTransaction() {
        Business business = buildBusiness();
        Integer businessId = adminService.addBusiness(business);
        assertNotNull(businessId);

        Food food = buildFood(businessId);
        Integer foodId = foodService.addFood(food);
        assertNotNull(foodId);
        assertNotNull(foodService.getFoodById(foodId, businessId));

        boolean deleted = adminService.deleteBusiness(businessId);
        assertTrue(deleted);

        assertNull(adminService.getBusinessById(businessId));
        assertTrue(foodService.listFoodsByBusinessId(businessId).isEmpty());
    }

    private Business buildBusiness() {
        Business business = new Business();
        business.setPassword("123456");
        business.setBusinessName("JUnit事务测试商家");
        business.setBusinessAddress("云南大学事务测试地址");
        business.setBusinessExplain("测试删除商家时联动删除食品");
        business.setStarPrice(new BigDecimal("15.00"));
        business.setDeliveryPrice(new BigDecimal("2.00"));
        return business;
    }

    private Food buildFood(Integer businessId) {
        Food food = new Food();
        food.setBusinessId(businessId);
        food.setFoodName("JUnit事务测试食品");
        food.setFoodExplain("应随商家一起删除");
        food.setFoodPrice(new BigDecimal("8.80"));
        return food;
    }
}
