package org.javaboy.provider;

import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    /**
     * 接口合并请求（也可以单个请求）
     * @param ids
     * @return
     */
    @GetMapping("/user/{ids}")
    public List<User> getUserByIds(@PathVariable String ids) {
        System.out.println(ids);
        String[] split = ids.split(",");
        List<User> users = new ArrayList<>();
        for (String s : split) {
            User user = new User();
            user.setId(Integer.parseInt(s));
            users.add(user);
        }
        return users;
    }
}
