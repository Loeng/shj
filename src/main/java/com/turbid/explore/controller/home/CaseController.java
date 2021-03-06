package com.turbid.explore.controller.home;

import com.turbid.explore.pojo.Case;
import com.turbid.explore.pojo.Comment;
import com.turbid.explore.pojo.UserSecurity;
import com.turbid.explore.service.*;
import com.turbid.explore.tools.Info;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(description = "案例相关接口")
@RestController("/case")
@CrossOrigin
public class CaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private UserSecurityService userSecurityService;

    @ApiOperation(value = "新增案例", notes="新增案例")
    @PutMapping("/addcase")
    public Mono<Info> addcase(Principal principal, @RequestBody Case obj) {
        obj.setUserSecurity(userSecurityService.findByPhone(principal.getName()));
        obj.setCreate_time(new Date());
        return Mono.just(Info.SUCCESS(caseService.save(obj)));
    }

    @ApiOperation(value = "删除", notes="新增案例")
    @DeleteMapping("/remove")
    public Mono<Info> remove(Principal principal,@RequestParam("code")String code) {
        return Mono.just(Info.SUCCESS(caseService.remove(code)));
    }


    @ApiOperation( value ="获取案例列表", notes="获取案例列表")
    @PostMapping("/caseByPage")
    public Mono<Info> caseByPage(@RequestParam(name = "page")Integer page,
                                  @RequestParam(name = "subject", required = false)String subject,
                                  @RequestParam(name = "label", required = false)String label,
                                  @RequestParam(name = "search", required = false)String search) {
        try {
            return Mono.just(Info.SUCCESS(caseService.listByPage(page,subject,label,search)));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @ApiOperation(value = "获取我的案例列表", notes="获取我的案例列表")
    @PostMapping("/mycases")
    public Mono<Info> mycases(Principal principal,@RequestParam(name = "page")Integer page) {
        try {
            return Mono.just(Info.SUCCESS(caseService.mycases(page,principal.getName())));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @ApiOperation(value = "获取我的案例列表", notes="获取我的案例列表")
    @GetMapping("/cases")
    public Mono<Info> cases(Principal principal) {
        try {
            return Mono.just(Info.SUCCESS(caseService.mycases(0,principal.getName())));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @ApiOperation(value = "获取我的案例列表", notes="获取我的案例列表")
    @GetMapping("/casesByUsercode")
    public Mono<Info> casesByUsercode(@RequestParam("usercode")String usercode,@RequestParam("page")Integer page) {
        try {
            return Mono.just(Info.SUCCESS(caseService.casesByUsercode(usercode,page)));
        }catch (Exception e){
            return Mono.just(Info.SUCCESS(e.getMessage()));
        }
    }

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private FollowService followService;

    @ApiOperation(value = "查询案例信息", notes="查询案例信息")
    @PostMapping("/caseByCode")
    public Mono<Info> caseByCode(Principal principal,@RequestParam(name = "code")String code) {
        Map<String,Object> data=new HashMap<>();
        Case obj= caseService.caseByCode(code);
        obj.setCommentcount(commentService.listByCount(code));
        try {
            UserSecurity userSecurity= userSecurityService.findByPhone(principal.getName());
            obj.getBrowsersInfo().add(userSecurity);
            data.put("data",caseService.save(obj));
            data.put("isstar",obj.getStarsInfo().contains(userSecurity));
            if(0<followService.findByCount(principal.getName(),obj.getUserSecurity().getPhonenumber())){
                data.put("isfollow",true);
            }else{
                data.put("isfollow",false);
            }
           int ccount= collectionService.findByCount(principal.getName(),code);
            data.put("collectioncount",ccount);
            if(0<ccount){
                data.put("iscollection",true);
            }else{
                data.put("iscollection",false);
            }
            return Mono.just(Info.SUCCESS(data));
        }catch (Exception e){
            e.getStackTrace();
            return Mono.just(Info.ERROR(e.getMessage()));
        }
    }

    @ApiOperation(value = "点赞", notes="点赞")
    @PostMapping("/star")
    public Mono<Info> star(Principal principal,@RequestParam(name = "code")String code) {
        Case obj= caseService.caseByCode(code);
        try {
           UserSecurity userSecurity= userSecurityService.findByPhone(principal.getName());
           obj.getStarsInfo().add(userSecurity);
           caseService.save(obj);
           return Mono.just(Info.SUCCESS(""));
        }catch (Exception e){
           return Mono.just(Info.ERROR(e.getMessage()));
        }
    }


    @ApiOperation(value = "推荐案例", notes="推荐案例")
    @PostMapping("/recommend")
    public Mono<Info> recommend(@RequestParam(name = "code")String code) {
        try {
            return Mono.just(Info.SUCCESS(caseService.recommend(caseService.caseByCode(code))));
        }catch (Exception e){
            return Mono.just(Info.ERROR(e.getMessage()));
        }
    }


    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "通过店铺code获取案例", notes="通过店铺code获取案例")
    @PostMapping("/caseByShop")
    public Mono<Info> caseByShop(@RequestParam(name = "code")String code,@RequestParam(name = "page")Integer page) {
        try {
            return Mono.just(Info.SUCCESS(caseService.mycases(page,shopService.getByCode(code).getUserSecurity().getPhonenumber())));
        }catch (Exception e){
            return Mono.just(Info.ERROR(e.getMessage()));
        }
    }


    @ApiOperation(value = "数据统计", notes="数据统计")
    @PostMapping(value = "/countbyuser")
    public Mono<Info> countbyuser(Principal principal)  {
        Map<String,Object> data=new HashMap<>();
        UserSecurity userSecurity=userSecurityService.findByPhone(principal.getName());
        data.put("star",caseService.starcount(userSecurity.getCode()));
        data.put("casecount",caseService.casecount(userSecurity.getCode()));
        data.put("commentcount",caseService.commentcount(userSecurity.getCode()));
        return Mono.just(Info.SUCCESS(data));
    }
}
