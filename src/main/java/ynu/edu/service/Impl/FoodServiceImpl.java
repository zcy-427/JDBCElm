package ynu.edu.service.Impl;

import ynu.edu.dao.FoodDao;
import ynu.edu.dao.Impl.FoodDaoImpl;
import ynu.edu.entity.Food;
import ynu.edu.service.FoodService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class FoodServiceImpl implements FoodService {
    private final FoodDao foodDao = new FoodDaoImpl();

    /**
     * 查询指定商家的食品列表
     * @param businessId 商家编号
     * @return 食品列表
     */
    @Override
    public List<Food> listFoodsByBusinessId(Integer businessId) {
        if (businessId == null || businessId <= 0) {
            return Collections.emptyList();
        }
        return foodDao.findByBusinessId(businessId);
    }

    /**
     * 根据食品编号和商家编号查询食品信息
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 食品信息
     */
    @Override
    public Food getFoodById(Integer foodId, Integer businessId) {
        if (foodId == null || foodId <= 0 || businessId == null || businessId <= 0) {
            return null;
        }
        return foodDao.findByIdAndBusinessId(foodId, businessId);
    }

    /**
     * 新增食品
     * @param food 食品信息
     * @return 新增食品的编号
     */
    @Override
    public Integer addFood(Food food) {
        if (!isValidFoodForAdd(food)) {
            return null;
        }
        return foodDao.insert(food);
    }

    /**
     * 修改食品信息
     * @param food 食品信息
     * @return 修改是否成功
     */
    @Override
    public boolean updateFood(Food food) {
        if (!isValidFoodForUpdate(food)) {
            return false;
        }
        return foodDao.update(food) > 0;
    }

    /**
     * 删除指定商家的食品
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 删除是否成功
     */
    @Override
    public boolean deleteFood(Integer foodId, Integer businessId) {
        if (foodId == null || foodId <= 0 || businessId == null || businessId <= 0) {
            return false;
        }
        return foodDao.deleteByIdAndBusinessId(foodId, businessId) > 0;
    }

    /**
     * 校验新增食品信息
     * @param food 食品信息
     * @return 是否合法
     */
    private boolean isValidFoodForAdd(Food food) {
        if (!isValidFoodBase(food)) {
            return false;
        }
        return food.getFoodId() == null;
    }

    /**
     * 校验修改食品信息
     * @param food 食品信息
     * @return 是否合法
     */
    private boolean isValidFoodForUpdate(Food food) {
        if (!isValidFoodBase(food)) {
            return false;
        }
        return food.getFoodId() != null && food.getFoodId() > 0;
    }

    /**
     * 校验食品基础信息
     * @param food 食品信息
     * @return 是否合法
     */
    private boolean isValidFoodBase(Food food) {
        if (food == null || food.getBusinessId() == null || food.getBusinessId() <= 0) {
            return false;
        }
        if (isBlank(food.getFoodName())) {
            return false;
        }
        return food.getFoodPrice() != null && food.getFoodPrice().compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * 判断字符串是否为空
     * @param value 字符串
     * @return 是否为空
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
