package com.nsu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Monica
 * @Date 2022/10/9 16:20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {

    private Long id;

    private String name;

    private String address;

    private String logo;

    private String description;
}
