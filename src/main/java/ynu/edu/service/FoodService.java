package ynu.edu.service;

import ynu.edu.entity.Food;

import java.util.List;

public interface FoodService {
    /**
     * 查询指定商家的食品列表
     * @param businessId 商家编号
     * @return 食品列表
     */
    List<Food> listFoodsByBusinessId(Integer businessId);

    /**
     * 根据食品编号和商家编号查询食品信息
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 食品信息
     */
    Food getFoodById(Integer foodId, Integer businessId);

    /**
     * 新增食品
     * @param food 食品信息
     * @return 新增食品的编号
     */
    Integer addFood(Food food);

    /**
     * 修改食品信息
     * @param food 食品信息
     * @return 修改是否成功
     */
    boolean updateFood(Food food);

    /**
     * 删除指定商家的食品
     * @param foodId 食品编号
     * @param businessId 商家编号
     * @return 删除是否成功
     */
    boolean deleteFood(Integer foodId, Integer businessId);
}
