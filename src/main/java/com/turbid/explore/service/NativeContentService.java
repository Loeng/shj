package com.turbid.explore.service;

import com.turbid.explore.pojo.NativeContent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NativeContentService {
    NativeContent save(NativeContent nativeContent);

    List<NativeContent> listByPage(Integer page);

    List<NativeContent> listByPage(Integer page,String username);

    NativeContent newsByCode(String code);
}
