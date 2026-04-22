package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 添加购物车
     * @param shoppingCart
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 插入购物车数据
     * @param cart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void update(ShoppingCart cart);

    /**
     * 更新购物车数据， number = number + 1
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart set create_time = #{createTime}, dish_id = #{dishId}, dish_flavor = #{dishFlavor}, setmeal_id = #{setmealId}, name = #{name}, image = #{image}, user_id = #{userId}, number = #{number}, amount = #{amount}")
    void insert(ShoppingCart shoppingCart);
}
