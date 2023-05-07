package com.sky.business.vo.params;

import lombok.Data;

/**
 * @author kc
 * @create 2023-05-06 19:40
 */
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

}
