package com.fbee.modules.match.model;

import com.fbee.modules.match.MatchModel;
import com.fbee.modules.match.anno.MatchPattern;
import com.fbee.modules.match.anno.Primary;

/**
 * 月嫂
 */
public class MaternityMatron implements MatchModel{

    /**
     * 类型：月嫂
     * 匹配：10
     * 不匹配：10->return
     */
    @Primary
    @MatchPattern
    private String serviceType = "1";


    /**
     * 住家／不住家／长期／临时／白班／夜班／24小时
     */
    @MatchPattern
    private String serviceNature;






}
