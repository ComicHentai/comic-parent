package com.comichentai.business.impl;

import com.comichentai.bo.JumpBusiness;
import com.comichentai.dto.JumpDto;
import com.comichentai.dto.SpecialDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.service.ComicService;
import com.comichentai.service.SpecialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Dintama on 2016/3/13.
 */

@Service("jumpBusiness")
public class JumpBusinessImpl implements JumpBusiness {

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "comicService")
    private ComicService comicService;

    @Override
    public ResultSupport<JumpDto> getJumpBySpecial(SpecialDto specialDto) {
        ResultSupport<JumpDto> result = new ResultSupport<>();
        ResultSupport<SpecialDto> special = specialService.getSpecialById(specialDto.getId());
        if(!special.isSuccess()){
            return null;
        }
        return null;
    }
}
