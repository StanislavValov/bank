package com.clouway.core;

import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hisazzul@gmail.com on 7/9/14.
 */

@At("/blogs")
public class Blogs {

    private Blog newBlog = new Blog();
    private List<Blog> blogs;

    @Post
    public String postEntry() {
        listBlogs();
        return "/blogs";
    }

    @Get
    public void listBlogs() {
        List<Blog>list = new ArrayList<Blog>();
        Blog blog = new Blog();
        blog.setSubject("someSubject");
        blog.setText("someText");
        list.add(blog);
        this.blogs = list;
    }
}