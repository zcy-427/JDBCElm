package ynu.edu;

import org.junit.jupiter.api.Test;
import ynu.edu.entity.Business;
import ynu.edu.entity.Food;
import ynu.edu.service.AdminService;
import ynu.edu.service.FoodService;
import ynu.edu.service.Impl.AdminServiceImpl;
import ynu.edu.service.Impl.FoodServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 食品模块测试。
 * 覆盖食品新增、查询、列表、修改、删除、非法参数校验。
 */
public class FoodServiceTest {

    private final AdminService adminService = new AdminServiceImpl();
    private final FoodService foodService = new FoodServiceImpl();

    @Test
    public void testFoodCrud() {
        Integer businessId = null;
        Integer foodId = null;
        try {
            businessId = adminService.addBusiness(buildBusiness());
            assertNotNull(businessId);

            Food food = buildFood(businessId, "JUnit食品测试菜品", "JUnit新增食品描述", "9.90");
            foodId = foodService.addFood(food);
            assertNotNull(foodId);
            assertTrue(foodId > 0);

            Food saved = foodService.getFoodById(foodId, businessId);
            assertNotNull(saved);
            assertEquals("JUnit食品测试菜品", saved.getFoodName());
            assertEquals(new BigDecimal("9.90"), saved.getFoodPrice());

            List<Food> foods = foodService.listFoodsByBusinessId(businessId);
            assertNotNull(foods);
            boolean containsInsertedFood = false;
            for (Food item : foods) {
                if (foodId.equals(item.getFoodId())) {
                    containsInsertedFood = true;
                    break;
                }
            }
            assertTrue(containsInsertedFood);

            saved.setFoodName("JUnit食品修改后菜品");
            saved.setFoodExplain("JUnit修改后的食品描述");
            saved.setFoodPrice(new BigDecimal("12.50"));
            assertTrue(foodService.updateFood(saved));

            Food updated = foodService.getFoodById(foodId, businessId);
            assertNotNull(updated);
            assertEquals("JUnit食品修改后菜品", updated.getFoodName());
            assertEquals(new BigDecimal("12.50"), updated.getFoodPrice());

            assertTrue(foodService.deleteFood(foodId, businessId));
            foodId = null;
            assertNull(foodService.getFoodById(updated.getFoodId(), businessId));
        } finally {
            if (foodId != null && businessId != null) {
                foodService.deleteFood(foodId, businessId);
            }
            if (businessId != null) {
                adminService.deleteBusiness(businessId);
            }
        }
    }

    @Test
    public void testFoodInvalidParam() {
        assertTrue(foodService.listFoodsByBusinessId(null).isEmpty());
        assertTrue(foodService.listFoodsByBusinessId(0).isEmpty());

        assertNull(foodService.getFoodById(null, 1));
        assertNull(foodService.getFoodById(1, null));
        assertNull(foodService.getFoodById(0, 1));
        assertNull(foodService.getFoodById(1, 0));

        assertNull(foodService.addFood(null));
        assertFalse(foodService.updateFood(null));
        assertFalse(foodService.deleteFood(null, 1));
        assertFalse(foodService.deleteFood(1, null));
    }

    @Test
    public void testFoodInvalidAddData() {
        Food noName = buildFood(1, "", "无名称", "8.00");
        assertNull(foodService.addFood(noName));

        Food negativePrice = buildFood(1, "负数价格食品", "价格不能小于0", "-1.00");
        assertNull(foodService.addFood(negativePrice));

        Food noBusiness = buildFood(null, "无商家食品", "缺少商家编号", "8.00");
        assertNull(foodService.addFood(noBusiness));
    }

    private Business buildBusiness() {
        Business business = new Business();
        business.setPassword("123456");
        business.setBusinessName("JUnit食品模块测试商家");
        business.setBusinessAddress("云南大学食品测试地址");
        business.setBusinessExplain("JUnit食品测试商家数据");
        business.setStarPrice(new BigDecimal("20.00"));
        business.setDeliveryPrice(new BigDecimal("3.00"));
        return business;
    }

    private Food buildFood(Integer businessId, String foodName, String foodExplain, String price) {
        Food food = new Food();
        food.setBusinessId(businessId);
        food.setFoodName(foodName);
        food.setFoodExplain(foodExplain);
        food.setFoodPrice(new BigDecimal(price));
        return food;
    }
}
