package com.comichentai.bo;

import com.comichentai.dto.JumpDto;
import com.comichentai.dto.SpecialDto;
import com.comichentai.entity.ResultSupport;

/**
 * Created by Dintama on 2016/3/13.
 */
public interface JumpBusiness {


    /**
     * 获取专辑内容-关联内容拼接
     *
     * @param specialDto 专辑对象
     * @return ResultSupport.module = 专辑
     * */
    ResultSupport<JumpDto> getJumpBySpecial(SpecialDto specialDto);

}
