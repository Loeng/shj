package com.turbid.explore.controller.home;

import com.turbid.explore.pojo.Case;
import com.turbid.explore.pojo.Study;
import com.turbid.explore.pojo.StudyRelation;
import com.turbid.explore.repository.NoticeRepository;
import com.turbid.explore.repository.StudyNoticeRepository;
import com.turbid.explore.repository.StudyRelationRepository;
import com.turbid.explore.service.StudyService;
import com.turbid.explore.tools.CodeLib;
import com.turbid.explore.tools.Info;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "达人研习社")
@RestController
@RequestMapping("/study")
@CrossOrigin
public class StudyController {

    @Autowired
    private StudyService studyService;

    @ApiOperation(value = "添加课程", notes="添加课程")
    @PutMapping("/add")
    public Mono<Info> add(@RequestBody Study study) {
        study.setShb(study.getPrice()*100);
        study.setSeecount(0);
        return Mono.just(Info.SUCCESS(studyService.save(study)));
    }

    @ApiOperation(value = "删除", notes="删除")
    @PutMapping("/del")
    public Mono<Info> del(@RequestParam("code") String code) {
        return Mono.just(Info.SUCCESS(studyService.del(code)));
    }


    @ApiOperation(value = "获取课程详情", notes="获取课程详情")
    @GetMapping("/get")
    public Mono<Info> get(@RequestParam(name = "code")String code) {
        Study study= studyService.get(code);
        if(study.getPricetype()=="1"||study.getPricetype().equals("1")) {
            study.setVideourl(null);
        }
        return Mono.just(Info.SUCCESS(study));
    }

    @ApiOperation(value = "获取课程视频url", notes="获取课程视频url")
    @GetMapping("/getVideo")
    public Mono<Info> getVideo(Principal principal,@RequestParam(name = "code")String code) {
//        if(0<studyRelationRepository.issee(principal.getName(),code)){
            return Mono.just(Info.SUCCESS(studyService.get(code).getVideourl()));
//        }else {
//            return Mono.just(Info.SUCCESS("请购买此课程"));
//        }
    }

    @ApiOperation(value = "获取课程分页列表", notes="获取课程分页列表")
    @PostMapping(value = "/studyByPage")
    public Mono<Info> studyByPage(@RequestParam(name = "page")Integer page,
                                  @RequestParam(name = "style", required = false)String style) {
        try {
            return Mono.just(Info.SUCCESS(studyService.listByPage(page,style)));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @ApiOperation(value = "获取课程分页列表", notes="获取课程分页列表")
    @PostMapping(value = "/list")
    public Mono<Info> list(@RequestParam(name = "page")Integer page,
                                  @RequestParam(name = "style", required = false)String style) {
        try {
            return Mono.just(Info.SUCCESS(studyService.list(page,style)));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @ApiOperation(value = "热门课程", notes="热门课程")
    @PostMapping(value = "/hatstudyByPage")
    public Mono<Info> hatstudyByPage(@RequestParam(name = "page")Integer page) {
        try {
            return Mono.just(Info.SUCCESS(studyService.hatstudyByPage(page)));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @ApiOperation(value = "推荐", notes="推荐")
    @PostMapping(value = "/recommend")
    public Mono<Info> recommend(@RequestParam(name = "code")String code) {
        try {
            return Mono.just(Info.SUCCESS(studyService.hatstudyByPage(0)));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @Autowired
    private StudyNoticeRepository noticeRepository;

    @ApiOperation(value = "预告", notes="预告")
    @PostMapping(value = "/notice")
    public Mono<Info> notice() {
        try {

            return Mono.just(Info.SUCCESS(noticeRepository.findAll()));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @Autowired
    private StudyRelationRepository studyRelationRepository;

    @ApiOperation(value = "购买课程", notes="购买课程")
    @PostMapping(value = "/buyStudy")
    @Transactional
    public Mono<Info> buyStudy(Principal principal,@RequestParam("studycode")String studycode) {
        try {
            StudyRelation studyRelation=new StudyRelation();
            studyRelation.setOrderno(CodeLib.randomCode(12,1));
            studyRelation.setPhone(principal.getName());
            studyRelation.setStudycode(studycode);
            studyRelation.setStatus(0);
            return Mono.just(Info.SUCCESS(studyRelationRepository.saveAndFlush(studyRelation).getOrderno()));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }


    @ApiOperation(value = "是否可看", notes="是否可看")
    @PostMapping(value = "/issee")
    public Mono<Info> issee(Principal principal,@RequestParam("studycode")String studycode) {
        try {
            if(0<studyRelationRepository.issee(principal.getName(),studycode)){
                return Mono.just(Info.SUCCESS(true));
            }else {
                return Mono.just(Info.SUCCESS(false));
            }
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

}
