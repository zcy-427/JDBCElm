package ynu.edu.service.Impl;

import ynu.edu.dao.BusinessDao;
import ynu.edu.dao.Impl.BusinessDaoImpl;
import ynu.edu.entity.Business;
import ynu.edu.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
    private final BusinessDao businessDao = new BusinessDaoImpl();

    /**
     * 商家登录
     * @param businessId 商家编号
     * @param password 商家密码
     * @return 登录成功的商家信息
     */
    @Override
    public Business login(Integer businessId, String password) {
        if (businessId == null || businessId <= 0 || isBlank(password)) {
            return null;
        }
        return businessDao.login(businessId, password);
    }

    /**
     * 根据商家编号查询商家信息
     * @param businessId 商家编号
     * @return 商家信息
     */
    @Override
    public Business getBusinessById(Integer businessId) {
        // TODO 后续接入 BusinessDao 按编号查询商家
        return null;
    }

    /**
     * 修改商家信息
     * @param business 商家信息
     * @return 修改是否成功
     */
    @Override
    public boolean updateBusiness(Business business) {
        // TODO 后续接入 BusinessDao 修改商家信息
        return false;
    }

    /**
     * 修改商家登录密码
     * @param businessId 商家编号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改是否成功
     */
    @Override
    public boolean updatePassword(Integer businessId, String oldPassword, String newPassword) {
        // TODO 后续接入 BusinessDao 校验旧密码并修改密码
        return false;
    }
}
