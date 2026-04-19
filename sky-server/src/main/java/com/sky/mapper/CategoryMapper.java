package com.sky.mapper;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 修改菜品分类信息
     * @param  category
     * @return
     */
    public void update(Category category);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    List<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param category
     */
    void insert(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from sky_take_out.category where id = #{id}")
    void delete(Long id);

    /**
     * 查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
