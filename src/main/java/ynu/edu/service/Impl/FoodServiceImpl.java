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
        // TODO 后续接入 FoodDao 查询当前商家的食品列表
        return Collections.emptyList();
    }

    /**
     * 根据食品编号和商家编号查询食品信息
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 食品信息
     */
    @Override
    public Food getFoodById(Integer foodId, Integer businessId) {
        // TODO 后续接入 FoodDao 按食品编号和商家编号查询食品
        return null;
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
        // TODO 后续接入 FoodDao 修改食品信息
        return false;
    }

    /**
     * 删除指定商家的食品
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 删除是否成功
     */
    @Override
    public boolean deleteFood(Integer foodId, Integer businessId) {
        // TODO 后续接入 FoodDao 删除当前商家的指定食品
        return false;
    }

    /**
     * 校验新增食品信息
     * @param food 食品信息
     * @return 是否合法
     */
    private boolean isValidFoodForAdd(Food food) {
        if (food == null || food.getBusinessId() == null || food.getBusinessId() <= 0) {
            return false;
        }
        if (food.getFoodId() != null) {
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
