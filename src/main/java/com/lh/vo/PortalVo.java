package com.lh.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * ClassName: PortalVo
 * package: com.lh.vo
 * Description:
 *
 * @Author lh
 * @Create 2024/9/27 23:02
 * @Version 1.0
 */
@Data
public class PortalVo {
    private String keyWords;
    private Integer type;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
