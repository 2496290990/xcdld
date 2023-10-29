package net.lesscoding.utils;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import net.lesscoding.common.Consts;
import net.lesscoding.entity.Account;

/**
 * @author eleven
 * @date 2022/11/14 17:06
 * @description 密码工具类
 */
public class PasswordUtil {

    /**
     * 获取密码随机盐
     *
     * @return String 返回随机八位字符串
     */
    public static String salt() {
        return new RandomGenerator(Consts.BASE_STR, 8)
                .generate();
    }

    /**
     * 获取密文
     *
     * @param clearTextPwd 明文密码
     * @param salt         用户密码随机盐
     * @return String          32位md5加密字符串
     */
    public static String encrypt(String clearTextPwd, String salt) {
        int cycleIndex = getCycleIndex(clearTextPwd, salt);
        if (cycleIndex == 0 ) {
            cycleIndex = RandomUtil.randomInt(1, 10);
        }
        while (cycleIndex > 0) {
            cycleIndex--;
            clearTextPwd = SecureUtil.md5(clearTextPwd);
        }
        return clearTextPwd;
    }

    /**
     * 匹配密码是否一致
     *
     * @param sysUser      用户对象
     * @param clearTextPwd 明文密码
     * @return Boolean         返回密码是否一致
     */
    public static Boolean decrypt(Account account, String clearTextPwd) {
        return StrUtil.equals(encrypt(clearTextPwd, account.getSalt()), account.getPassword());
    }

    /**
     * 匹配密码是否一致
     *
     * @param password     数据库密码
     * @param salt         用户密码随机盐
     * @param clearTextPwd 前端明文密码
     * @return Boolean          返回前端密码和数据库密码是否一致
     */
    public static Boolean decrypt(String password, String salt, String clearTextPwd) {
        return StrUtil.equals(encrypt(clearTextPwd, salt), password);
    }

    /**
     * 获取加密循环次数
     *
     * @param clearTextPwd 明文密码
     * @param salt         密码随机盐
     * @return Intger            根据明文密码和密码随机盐的hashCode 与 256取模获取循环加密次数
     */
    public static int getCycleIndex(String clearTextPwd, String salt) {
        int hashCode = clearTextPwd.hashCode() + salt.hashCode();
        return Math.abs(hashCode % 256);
    }

    /**
     * 直接给用户换密码
     * @param user  用户
     */
    public static void encryptUserPassword(Account user){
        String salt = salt();
        String encrypt = encrypt(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(encrypt);
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.decrypt("ce454f2b15bec98de8d072637604cfea", "(#~+^*!x", "80640"));
    }
}
