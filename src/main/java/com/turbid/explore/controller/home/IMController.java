package com.turbid.explore.controller.home;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.turbid.explore.pojo.bo.Message;
import com.turbid.explore.repository.NoticeRepository;
import com.turbid.explore.tools.CodeLib;
import com.turbid.explore.tools.Info;
import com.turbid.explore.tools.TLSSigAPIv2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.*;

@Api(description = "即时通讯接口")
@RestController
@RequestMapping("/im")
@CrossOrigin
public class IMController {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl="https://console.tim.qq.com/";

    private String account_import_url="v4/im_open_login_svc/account_import";

    private String push_="v4/openim/im_push";

    private String send_msg="v4/openim/sendmsg";

    private String portrait_set="v4/profile/portrait_set";

    private long appid=1400334582;

    private String key="72d736f1307d531aeb773c80f55a891a2c30a360a4a9d3841deb893c2cb54551";

    public String config(){
        return "?sdkappid="+appid+"&identifier=administrator"+"&usersig="+TLSSigAPIv2.genSig("administrator",680000000)
                +"&random="+ UUID.randomUUID().toString().replace("-", "").toLowerCase()+"&contenttype=json";
    }

    @ApiOperation(value = "签名", notes="签名")
    @PutMapping("/sig")
    public Mono<Info> sig(@RequestParam("identifier") String identifier) {

        return Mono.just(Info.SUCCESS(TLSSigAPIv2.genSig(identifier,680000000)));
    }

    @ApiOperation(value = "导入账号", notes="导入账号")
    @PutMapping("/account_import")
    public Mono<Info> account_import(@RequestParam("identifier") String identifier,@RequestParam("nikename") String nikename) {
        Map<String, Object> requestBody = ImmutableMap.of(
                "Identifier", identifier,
                "Nick", nikename,
                "FaceUrl","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588763946885&di=11fc71844ac400e62d61e5abbff4e4fb&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F67%2F59%2F63%2F58e89bee922a2.png");
        JSONObject jsonObject= restTemplate.postForObject(baseUrl+account_import_url+config()
               ,requestBody, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }


    @ApiOperation(value = "设置资料", notes="设置资料")
    @PutMapping("/changename")
    public Mono<Info> changename(@RequestParam("identifier") String identifier,@RequestParam("name") String name) {
        JSONArray data =new JSONArray();
        JSONObject item=new JSONObject();
        item.put("Tag","Tag_Profile_IM_Nick");
        item.put("Value",name);
        data.add(item);

        Map<String, Object> requestBody = ImmutableMap.of(
                "From_Account", identifier,
                "ProfileItem",data
        );
        JSONObject jsonObject= restTemplate.postForObject(baseUrl+portrait_set+config()
                ,requestBody, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }

    @ApiOperation(value = "设置资料", notes="设置资料")
    @PutMapping("/deleteuser")
    public Mono<Info> deleteuser(@RequestParam("identifier") String identifier) {
        JSONArray data =new JSONArray();
        JSONObject item=new JSONObject();
        item.put("UserID",identifier);
        data.add(item);

        Map<String, Object> requestBody = ImmutableMap.of(
                "DeleteItem", data
        );
        JSONObject jsonObject= restTemplate.postForObject(baseUrl+"v4/im_open_login_svc/account_delete"+config()
                ,requestBody, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }

    @ApiOperation(value = "设置资料", notes="设置资料")
    @PutMapping("/portrait_set")
    public Mono<Info> portrait_set(@RequestParam("identifier") String identifier,@RequestParam("type") String type) {
        JSONArray data =new JSONArray();
        JSONObject item=new JSONObject();
        item.put("Tag","Tag_Profile_Custom_usertype");
        item.put("Value",type);
        data.add(item);
        item=new JSONObject();
        item.put("Tag","Tag_Profile_Custom_yx");
        item.put("Value","严选");
        data.add(item);

        Map<String, Object> requestBody = ImmutableMap.of(
                "From_Account", identifier,
                "ProfileItem",data
        );
        JSONObject jsonObject= restTemplate.postForObject(baseUrl+portrait_set+config()
                ,requestBody, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }

    @ApiOperation(value = "单发单聊", notes="单发单聊")
    @PostMapping("/sendmsg")
    public Mono<Info> sendmsg(@RequestBody Message message) {

        JSONObject jsonObject= restTemplate.postForObject(baseUrl+send_msg+config(),message, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }

    @ApiOperation(value = "推送", notes="推送")
    @PostMapping("/imPush")
    public Mono<Info> imPush(@RequestBody Message message) {

        JSONObject jsonObject= restTemplate.postForObject(baseUrl+push_+config(),message, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }

    @ApiOperation(value = "推送报告", notes="推送报告")
    @PostMapping("/imGetPushReport")
    public Mono<Info> imGetPushReport(@RequestParam("taskIds") String taskIds) {
        Map<String, Object> requestBody = ImmutableMap.of("TaskIds", taskIds);
        JSONObject jsonObject= restTemplate.postForObject(baseUrl+push_+config(),requestBody, JSONObject.class);
        return Mono.just(Info.SUCCESS(jsonObject));
    }

    @Autowired
    private NoticeRepository noticeRepository;

    @ApiOperation(value = "通知", notes="通知")
    @PostMapping("/notice")
    public Mono<Info> notice(Principal principal) {
        Map<String,List<Map<String,String>>> data=new HashMap<>();
        List sys=new ArrayList<>();
        List msg=new ArrayList<>();
        noticeRepository.findByUserphoneAndTypeAndStatus(principal.getName(),0,0).forEach(v->{
            v.setStatus(1);
            sys.add(noticeRepository.saveAndFlush(v));
        });
        noticeRepository.findByUserphoneAndTypeAndStatus(principal.getName(),1,0).forEach(v->{
            v.setStatus(1);
            msg.add(noticeRepository.saveAndFlush(v));
        });
        data.put("sys",sys);
        data.put("msg",msg);
        return Mono.just(Info.SUCCESS(data));
    }
}
