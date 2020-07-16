package com.study.service.properties;

import lombok.Data;

@Data
public class ImageCodeProperties extends SmsCodeProperties {
    public ImageCodeProperties(){setLength(4);}
    private Integer width=67;
    private Integer heigth=23;
}
