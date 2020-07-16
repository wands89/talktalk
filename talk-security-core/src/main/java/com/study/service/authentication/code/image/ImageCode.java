package com.study.service.authentication.code.image;

import com.study.service.authentication.code.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码
 */
@Data
public class ImageCode extends ValidateCode {
    private BufferedImage image;
    ImageCode (BufferedImage image,String code,LocalDateTime expireTime){
       super(code,expireTime);
        this.image=image;

    }
    ImageCode (BufferedImage image,String code,Integer expireIn){
      super(code,expireIn);
        this.image=image;
    }

}
