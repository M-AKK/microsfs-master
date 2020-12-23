package net.wenz.service.fs.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping(value = "")
public class TestController {

    @RequestMapping(value = "/currentUser", method = {RequestMethod.GET})
    @ResponseBody
    public String currentUser() throws IOException {
        JSONObject object = new JSONObject();

        //string
        object.put("name","Serati Ma");
        object.put("avatar","https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        object.put("userid","00000001");
        object.put("email","antdesign@alipay.com");
        object.put("signature","antdesign@alipay.com");
        object.put("title","antdesign@alipay.com");
        object.put("title","交互专家");
        object.put("group","蚂蚁金服－某某某事业群－某某平台部－某某技术部－UED");

        HashMap<String , String> tags = new HashMap<String , String>();
        tags.put("0","很有想法的");
        tags.put("1","专注设计");
        tags.put("2","辣~");
        tags.put("3","大长腿");
        tags.put("4","川妹子");
        tags.put("5","海纳百川");
        String result = hashMapToJson(tags);//hashmap转json
        object.put("tags",result);

        object.put("notifyCount",12);
        object.put("unreadCount",11);
        object.put("country","China");
        object.put("access","getAccess()");

        HashMap<String , Map<String,String>> tags1 = new HashMap<String , Map<String,String>>();
        Map<String, String> ret1 = new HashMap<>();
        ret1.put("浙江省","330000");
        tags1.put("province",ret1);
        Map<String, String> ret2 = new HashMap<>();
        ret2.put("杭州市","330100");
        tags1.put("city",ret2);
        String result1 = hashMapToJson(tags1);//hashmap转json
        object.put("geographic",result1);
        object.put("address","西湖区工专路 77 号");
        object.put("phone","0752-268888888");

        return object.toJSONString();
    }

    @RequestMapping(value = "/login/account", method = {RequestMethod.POST})
    @ResponseBody
    public String account(@RequestParam(name="username",required=false) String username,
                          @RequestParam(name="password",required=false) String password,
                          @RequestParam(name="autoLogin",required=false) String autoLogin,
                          @RequestParam(name="type",required=false) String type) throws IOException {
        JSONObject object = new JSONObject();
        //string
        object.put("status","ok");
        object.put("type","account");
        object.put("currentAuthority","admin");

        return object.toJSONString();
    }
    public static String hashMapToJson(HashMap map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry e = (Map.Entry) it.next();
            string += "'" + e.getKey() + "':";
            string += "'" + e.getValue() + "',";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }
}
