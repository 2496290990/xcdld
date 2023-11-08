package net.lesscoding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.mapper.WeaponMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eleven
 * @date 2023/10/23 8:34
 * @apiNote
 */
@SpringBootTest(classes = MainApp.class)
@RunWith(SpringRunner.class)
@RefreshScope
public class WeaponTest {

    @Autowired
    private WeaponMapper weaponMapper;

    @Autowired
    private AccountPlayerMapper playerMapper;

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;

    @Test
    public void initDamage() {
        List<Weapon> weapons = weaponMapper.selectList(new QueryWrapper<Weapon>().orderByAsc("id"));
        for (Weapon weapon : weapons) {
            weapon.setMinDamage(2 * weapon.getId() + 1);
            weapon.setMaxDamage(weapon.getMinDamage() + 2 * (weapon.getId() * weapon.getId()));
            weaponMapper.updateById(weapon);
        }
    }

    @Test
    public void initPlayerWeapon() {
        List<AccountPlayer> accountPlayerList = playerMapper.selectList(null);
        List<PlayerWeapon> insertList = new ArrayList<>(accountPlayerList.size());
        PlayerWeapon playerWeapon = null;
        for (AccountPlayer accountPlayer : accountPlayerList) {
            playerWeapon = new PlayerWeapon();
            playerWeapon.setPlayerId(accountPlayer.getId());
            playerWeapon.setWeaponId(0);
            playerWeapon.setAddLevel(0);
            insertList.add(playerWeapon);
        }
        System.out.println(playerWeaponMapper.insertBatch(insertList));
    }
}
