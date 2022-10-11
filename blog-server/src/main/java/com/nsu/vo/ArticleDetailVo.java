package com.nsu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Monica
 * @Date 2022/10/9 15:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;
    /**
     * 访问量
     */
    private Long viewCount;
}
