package com.example.signin.service.impl;

import com.example.signin.entity.Course;
import com.example.signin.mapper.ConfigMapper;
import com.example.signin.mapper.CourseMapper;
import com.example.signin.mapper.StudentMapper;
import com.example.signin.service.TestPageService;
import com.example.signin.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TestPageServiceImpl
 * @Description: TODO
 */
@Service
public class TestPageServiceImpl implements TestPageService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public AjaxResult page(int pageNo, int pageSize) {

        List<Course> list = courseMapper.selectAllCourse();
        boolean hasMore = true;
        int totalCount = list.size();
        int startIndex = (pageNo - 1) * pageSize;
        if(startIndex + pageSize >= totalCount){
            hasMore = false;
        }
        int endIndex = hasMore ? startIndex + pageSize : totalCount;
        List<Course> res = list.subList(startIndex,endIndex);
        return new AjaxResult().ok(res);
    }
}
