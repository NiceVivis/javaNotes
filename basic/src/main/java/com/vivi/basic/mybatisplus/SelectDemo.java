package com.vivi.basic.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javafx.geometry.VPos;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangwei
 * @date 2021/2/3 10:27 上午
 */

public class SelectDemo {

    @Autowired
    private UserSelectMapper userSelectMapper;

    /**
     * 普通查询
     */
    @Test
    public void selectById(){
        UserSelect userSelect = userSelectMapper.selectById(1);
        System.out.println(userSelect);
    }

    /**
     * 批量查询
     */
    @Test
    public void selectIds(){
        List<Long> ids = Arrays.asList(123L,124L,125L);
        List<UserSelect> userSelects = userSelectMapper.selectBatchIds(ids);
        System.out.println(userSelects);
    }

    /**
     * 名字包含hua并且年龄小于30
     */
    @Test
    public void selectByWrapper1(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","hua").lt("age",30);
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        System.out.println(userSelects);
    }

    /**
     * 名字包含hua并且年龄大雨等于20且小于等于40并且email不为空
     */
    @Test
    public void selectByWrapper2(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","hua")
                .between("age",20,30)
                .isNotNull("email");
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 名字姓肖或者年龄大于等于20，按照年龄降序排列，年龄相同按照id生序排列
     */
    @Test
    public void selectByWrapper3(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("username","肖")
                .or().ge("age",20)
                .orderByDesc("age").orderByAsc("id");

        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年10月2日并且直属上级名字为王姓
     */
    @Test
    public void selectByWrapper4(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2020-09-10")
                .inSql("parent_id","select id from user where username like '王%'");
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40或者邮箱不为空）
     */
    @Test
    public void selectByWrapper5(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("username","王")
                .and(wq->wq.lt("age","40"))
                .or().isNotNull("email");
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40并且大于20或者邮箱不为空）
     */
    @Test
    public void selectByWrappe6(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("username","王")
                .and(wq->wq.lt("age",40))
                .or().isNotNull("email");
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * （年龄小于40并且大于20或邮箱不为空）并且姓名为王姓
     */
    @Test
    public void selectByWrapper7(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(wq->wq.lt("age",40).
                or().isNotNull("email"))
                .likeRight("username","王");
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 年龄是23，30，40
     */
    @Test
    public void selectByWrapper8(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(20,30,40));
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     */
    @Test
    public void selectByWrapper9(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age",Arrays.asList(20,30,40)).last("limit 1");
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 名字中包含雨并且年龄小于40（只读取id，name）
     */
    @Test
    public void selectByWrapper10(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","username").like("username","雨").lt("age",40);
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 名字中包含雨并且年龄小于40（不取create_time，parent_id两个字段，即不列出全部字段）
     */
    @Test
    public void selectByWrapper11(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","雨").lt("age",40)
                .select(UserSelect.class,info->!info.getColumn().equals("create_time") &&
                        !info.getColumn().equals("parent_id"));
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 姓名和邮箱不为空
     */
    @Test
    public void testCondition(){
        String username = "hua";
        String email = "";
        condition(username,email);
    }

    public void condition(String username,String email){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isBlank(username),"username",username)
                .like(StringUtils.isBlank(email),"email",email);
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 实体作为条件构造器方法的参数
     */
    @Test
    public void selectByWrapperEntity(){
        UserSelect userSelect = new UserSelect();
        userSelect.setUsername("xiaoming");
        userSelect.setAge(22);
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>(userSelect);
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * AllEq用法
     */
    @Test
    public void selectByWrapperAllEq(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("username","hua");
        params.put("age",null);
        queryWrapper.allEq(params);
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * AllEq用法(排除不是条件的字段)
     */
    @Test
    public void selectByWrapperAllEq2(){
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("username","hua");
        params.put("age",null);
        queryWrapper.allEq((k,v)->!k.equals("username"),params);
        List<UserSelect> userSelects = userSelectMapper.selectList(queryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * selectMaps
     */
    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","小").lt("age",40);
        List<Map<String,Object>> userInfoItem = userSelectMapper.selectMaps(queryWrapper);
        userInfoItem.forEach(System.out::println);
    }

    /**
     *  按照直属上级分组，查询每组的平均年龄，最大年龄，最小年龄，并且只取年龄总和小于500的组
     */
    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) ave_age","min(min) min_age","max(max) max_age")
                .groupBy("parent_id").having("sum(age)<{0}",500);
        List<Map<String, Object>> maps = userSelectMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    /**
     * selectObjs
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","username").like("username","xiao").lt("age",40);
        List<Object> objects = userSelectMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }

    /**
     * selectCount
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","xiao").lt("age",40);
        Integer count = userSelectMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * selectOne
     */
    @Test
    public void selectByWrapperSelectOne() {
        QueryWrapper<UserSelect> queryWrapper = new QueryWrapper<UserSelect>();
        queryWrapper.like("name","肖").lt("age",40);
        UserSelect user = userSelectMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * 使用Lambda
     */
    @Test
    public void selectLambda() {
        LambdaQueryWrapper<UserSelect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(UserSelect::getUsername,"hua").lt(UserSelect::getAge,40);
        List<UserSelect> userSelects = userSelectMapper.selectList(lambdaQueryWrapper);
        userSelects.forEach(System.out::println);
    }

    /**
     * 使用Lambda,名字为王姓（年龄小于40或邮箱不为空）
     */
    @Test
    public void selectLambd2() {
        LambdaQueryWrapper<UserSelect> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(UserSelect::getUsername,"hua")
                .and(lqw->lqw.lt(UserSelect::getAge,40).or().isNotNull(UserSelect::getEmail));
        List<UserSelect> userSelects = userSelectMapper.selectList(lambdaQueryWrapper);
        userSelects.forEach(System.out::println);
    }
}
