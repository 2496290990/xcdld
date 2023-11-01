package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.GameInstance;
import net.lesscoding.entity.InstanceNpc;
import net.lesscoding.entity.InstanceRecord;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.GameInstanceMapper;
import net.lesscoding.mapper.InstanceNpcMapper;
import net.lesscoding.mapper.InstanceRecordMapper;
import net.lesscoding.model.dto.JoinInstanceDto;
import net.lesscoding.model.vo.ChallengeInstanceVo;
import net.lesscoding.service.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author eleven
 * @date 2023/10/24 8:39
 * @apiNote
 */
@Service
public class GameInstanceServiceImpl extends ServiceImpl<GameInstanceMapper, GameInstance> implements GameInstanceService {
    @Autowired
    private GameInstanceMapper instanceMapper;

    @Autowired
    private InstanceRecordMapper recordMapper;

    @Autowired
    private AccountPlayerMapper playerMapper;

    @Autowired
    private InstanceNpcMapper npcMapper;

    @Override
    public List<GameInstance> getInstanceList() {
        return instanceMapper.selectList(new QueryWrapper<GameInstance>()
                .eq("del_flag", false)
                .orderByAsc("access_level"));
    }

    @Override
    public ChallengeInstanceVo joinInstance(JoinInstanceDto dto) {
        InstanceRecord record = recordMapper.getNotCompletedInstance(dto);
        if (record == null) {
            AccountPlayer player = playerMapper.selectOne(new QueryWrapper<AccountPlayer>()
                    .eq("id", dto.getPlayerId()));
            record = new InstanceRecord();
            record.setInstanceId(dto.getInstanceId());
            record.setCompleteFlag(false);
            record.setChallengeDate(LocalDate.now());
            record.setSuccessFlag(false);
            record.setPlayerId(player.getId());
            record.setCurrentFloor(0);
            recordMapper.insert(record);
        }
        List<InstanceNpc> npcList = npcMapper.selectInstanceNpcList(dto);
        InstanceRecord finalRecord = record;
        npcList.forEach(item -> item.setDefeatFlag(finalRecord.getCurrentFloor() >= item.getFloor()));
        return new ChallengeInstanceVo(record, npcList);
    }
}
