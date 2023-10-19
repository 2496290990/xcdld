/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : xcdld

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 27/09/2023 17:38:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS tb_account;
CREATE TABLE tb_account
(
    id          int         NOT NULL AUTO_INCREMENT COMMENT '自增id',
    mac         varchar(32) NOT NULL COMMENT '网卡地址',
    account     varchar(10) NULL DEFAULT NULL COMMENT '账号',
    email       varchar(32) NULL DEFAULT NULL COMMENT '邮箱',
    password    varchar(32) NULL DEFAULT NULL COMMENT '密码',
    salt        varchar(8)  NULL DEFAULT NULL COMMENT '随机盐',
    status      int         NULL DEFAULT 1 COMMENT '0 小黑屋 1正常 2注销',
    nickname    varchar(12) NULL DEFAULT NULL COMMENT '昵称',
    create_by   int         NULL DEFAULT NULL COMMENT '创建人',
    create_time datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   int         NULL DEFAULT NULL COMMENT '更新人',
    update_time datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag    bit(1)      NULL DEFAULT b'0' COMMENT '删除标记',
    PRIMARY KEY (id) USING BTREE
) COMMENT = '账户表';


create table tb_player_level_exp
(
    id          int primary key auto_increment comment '自增id',
    level       int comment '等级',
    need_exp    int comment '所需经验',
    hp          int          default 50 comment '基础HP',
    attack      int          default 1 comment '基础攻击力',
    defender    int          default 1 comment '基础防御力',
    flee        double(5, 2) default 0.3 comment '基础闪避率',
    combo       double(5, 2) default 0.3 comment '基础连击',
    hit         double(5, 2) default 0.5 comment '基础命中率',
    create_by   int          default 0 comment '创建人',
    create_time datetime     default now() comment '创建时间',
    update_by   int          default 0 comment '更新人',
    update_time datetime     default now() comment '更新时间',
    del_flag    bit(1)       default false comment '删除标记'
) comment '角色等级数据表';

-- 初始化等级数据
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (1, 0, 0, 50, 3, 1, 0.25, 0.25, 0.40, 0, '2023-10-08 06:05:08', 0, '2023-10-08 06:05:08', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (2, 1, 50, 54, 3, 2, 0.26, 0.25, 0.40, 0, '2023-10-08 06:05:41', 0, '2023-10-08 06:05:41', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (3, 2, 100, 58, 5, 2, 0.26, 0.26, 0.40, 0, '2023-10-08 06:06:24', 0, '2023-10-08 06:06:24', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (4, 3, 150, 64, 7, 3, 0.26, 0.26, 0.41, 0, '2023-10-08 06:06:56', 0, '2023-10-08 06:06:56', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (5, 4, 200, 75, 12, 4, 0.26, 0.26, 0.42, 0, '2023-10-08 06:07:04', 0, '2023-10-08 06:07:04', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (6, 5, 251, 89, 19, 4, 0.26, 0.27, 0.42, 0, '2023-10-08 06:07:08', 0, '2023-10-08 06:07:08', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (7, 6, 300, 130, 31, 7, 0.27, 0.27, 0.42, 0, '2023-10-08 06:07:12', 0, '2023-10-08 06:07:12', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (8, 7, 350, 170, 50, 9, 0.27, 0.28, 0.42, 0, '2023-10-08 06:07:17', 0, '2023-10-08 06:07:17', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (9, 8, 400, 220, 60, 13, 0.28, 0.28, 0.42, 0, '2023-10-08 06:07:24', 0, '2023-10-08 06:07:24', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (10, 9, 450, 280, 71, 15, 0.28, 0.28, 0.43, 0, '2023-10-08 06:07:29', 0, '2023-10-08 06:07:29', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (11, 10, 500, 340, 80, 15, 0.29, 0.29, 0.44, 0, '2023-10-08 06:07:33', 0, '2023-10-08 06:07:33', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (12, 11, 1000, 400, 84, 18, 0.40, 0.39, 0.54, 0, '2023-10-08 06:07:36', 0, '2023-10-08 06:07:36', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (13, 12, 2000, 500, 88, 19, 0.40, 0.40, 0.54, 0, '2023-10-08 06:07:39', 0, '2023-10-08 06:07:39', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (14, 13, 3000, 580, 92, 21, 0.40, 0.40, 0.55, 0, '2023-10-08 06:07:42', 0, '2023-10-08 06:07:42', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (15, 14, 4000, 670, 100, 22, 0.40, 0.41, 0.55, 0, '2023-10-08 06:07:46', 0, '2023-10-08 06:07:46', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (16, 15, 5000, 800, 123, 23, 0.40, 0.41, 0.56, 0, '2023-10-08 06:07:49', 0, '2023-10-08 06:07:49', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (17, 16, 6000, 950, 135, 28, 0.41, 0.41, 0.56, 0, '2023-10-08 06:07:52', 0, '2023-10-08 06:07:52', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (18, 17, 7000, 1080, 156, 30, 0.41, 0.42, 0.56, 0, '2023-10-08 06:07:58', 0, '2023-10-08 06:07:58', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (19, 18, 8000, 1340, 188, 31, 0.41, 0.42, 0.57, 0, '2023-10-08 06:08:01', 0, '2023-10-08 06:08:01', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (20, 19, 9000, 1780, 191, 33, 0.42, 0.42, 0.57, 0, '2023-10-08 06:08:05', 0, '2023-10-08 06:08:05', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (21, 20, 10000, 2356, 202, 55, 0.52, 0.52, 0.65, 0, '2023-10-08 06:08:08', 0, '2023-10-08 06:08:08', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (22, 21, 20000, 2600, 222, 64, 0.53, 0.52, 0.65, 0, '2023-10-08 06:08:11', 0, '2023-10-08 06:08:11', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (23, 22, 30000, 2980, 245, 70, 0.53, 0.53, 0.65, 0, '2023-10-08 06:08:14', 0, '2023-10-08 06:08:14', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (24, 23, 40000, 3500, 278, 101, 0.53, 0.53, 0.66, 0, '2023-10-08 06:08:17', 0, '2023-10-08 06:08:17', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (25, 24, 50000, 4400, 300, 113, 0.53, 0.54, 0.66, 0, '2023-10-08 06:08:22', 0, '2023-10-08 06:08:22', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (26, 25, 60000, 5200, 312, 135, 0.53, 0.54, 0.67, 0, '2023-10-08 06:08:25', 0, '2023-10-08 06:08:25', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (27, 26, 70000, 6100, 344, 149, 0.54, 0.54, 0.67, 0, '2023-10-08 06:08:28', 0, '2023-10-08 06:08:28', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (28, 27, 80000, 7000, 358, 179, 0.55, 0.55, 0.67, 0, '2023-10-08 06:08:31', 0, '2023-10-08 06:08:31', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (29, 28, 90000, 8848, 372, 188, 0.55, 0.55, 0.70, 0, '2023-10-08 06:08:35', 0, '2023-10-08 06:08:35', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (30, 29, 100000, 9527, 399, 199, 0.60, 0.60, 0.70, 0, '2023-10-08 06:08:38', 0, '2023-10-08 06:08:38', b'0');
INSERT INTO xcdld.tb_player_level_exp(id, level, need_exp, hp, attack, defender, flee, combo, hit, create_by,
                                      create_time, update_by, update_time, del_flag)
VALUES (31, 30, 200000, 10086, 404, 200, 0.70, 0.70, 0.70, 0, '2023-10-08 06:08:43', 0, '2023-10-08 06:08:43', b'0');

create table tb_base_table
(
    id          int primary key auto_increment comment '自增主键',
    create_by   int      NULL DEFAULT NULL COMMENT '创建人',
    create_time datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   int      NULL DEFAULT NULL COMMENT '更新人',
    update_time datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag    bit(1)   NULL DEFAULT false COMMENT '删除标记'
) comment '基础表格框架';

create table tb_account_player
(
    id          int primary key auto_increment comment '自增主键',
    account_id  int         not null comment '账号id',
    nickname    varchar(32) not null comment '昵称',
    level       int              default 0 comment '当前等级',
    exp         int              default 0 comment '当前经验',
    create_by   int         NULL DEFAULT NULL COMMENT '创建人',
    create_time datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   int         NULL DEFAULT NULL COMMENT '更新人',
    update_time datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag    bit(1)      NULL DEFAULT false COMMENT '删除标记'
) comment '账号角色';

create table tb_weapon
(
    id           int primary key auto_increment comment '自增主键',
    weapon_name  varchar(20)  not null comment '武器名称',
    weapon_intro varchar(200) not null comment '武器描述',
    type         int          not null comment '武器类型 0小型武器 1中型武器 2大型武器',
    grade        int               default 0 comment '品级 0白 1绿 2蓝 3紫 4粉 5传说 6史诗',
    create_by    int          NULL DEFAULT NULL COMMENT '创建人',
    create_time  datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by    int          NULL DEFAULT NULL COMMENT '更新人',
    update_time  datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag     bit(1)       NULL DEFAULT false COMMENT '删除标记'
) comment '武器表';

create table tb_skill
(
    id          int primary key auto_increment comment '自增主键',
    skill_name  varchar(20)  not null comment '技能名称',
    skill_intro varchar(200) not null comment '技能描述',
    create_by   int          NULL DEFAULT NULL COMMENT '创建人',
    create_time datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   int          NULL DEFAULT NULL COMMENT '更新人',
    update_time datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag    bit(1)       NULL DEFAULT false COMMENT '删除标记'
) comment '技能表';

create table tb_player_skill
(
    id          int primary key auto_increment comment '自增主键',
    player_id   int      not null comment '角色id',
    skill_id    int      not null comment '技能id',
    skill_level int      not null comment '技能等级',
    create_by   int      NULL DEFAULT NULL COMMENT '创建人',
    create_time datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   int      NULL DEFAULT NULL COMMENT '更新人',
    update_time datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag    bit(1)   NULL DEFAULT false COMMENT '删除标记'
) comment '角色技能表';

create table tb_skill_level
(
    id          int primary key auto_increment comment '自增主键',
    skill_id    int      not null comment '技能id',
    level       int      not null comment '技能等级',
    need_level  int      not null comment '最低学习等级',
    min_damage  int           default 1 comment '最小攻击',
    max_damage  int           default 5 comment '最大攻击',
    effect_json text comment '增幅效果',
    create_by   int      NULL DEFAULT NULL COMMENT '创建人',
    create_time datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   int      NULL DEFAULT NULL COMMENT '更新人',
    update_time datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag    bit(1)   NULL DEFAULT false COMMENT '删除标记'
) comment '技能等级表';

create table tb_game_instance
(
    id            int primary key auto_increment comment '自增主键',
    instance_name varchar(30) not null comment '副本名称',
    instance_info text        not null comment '副本描述',
    access_level  int         not null comment '准入等级',
    create_by     int         NULL DEFAULT NULL COMMENT '创建人',
    create_time   datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by     int         NULL DEFAULT NULL COMMENT '更新人',
    update_time   datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag      bit(1)      NULL DEFAULT false COMMENT '删除标记'
) comment '游戏副本表';

create table tb_instance_npc
(
    id                 int primary key auto_increment comment '自增主键',
    instance_id        int         not null comment '副本id',
    npc_name           varchar(30) not null comment 'npc名称',
    npc_level          int         default 1 comment 'npc等级',
    bossFlag           boolean          default false comment '是否BOSS',
    increase_ratio     double           default 1.1 comment '属性增幅系数',
    floor              int              default 1 comment '层数',
    random_drop_skill  varchar(50) comment '随机掉落技能',
    random_drop_weapon varchar(50) comment '随机掉落武器',
    create_by          int         NULL DEFAULT NULL COMMENT '创建人',
    create_time        datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by          int         NULL DEFAULT NULL COMMENT '更新人',
    update_time        datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag           bit(1)      NULL DEFAULT false COMMENT '删除标记'
) comment '游戏副本NPC表';

create table tb_fight_log
(
    id                 int primary key auto_increment comment '自增主键',
    attacker_id        int not null comment '攻击者id',
    defender_id        int not null comment '防御者id',
    success            boolean default false comment '是否胜利',
    fight_date         date comment '战斗日期',
    create_by          int         NULL DEFAULT NULL COMMENT '创建人',
    create_time        datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by          int         NULL DEFAULT NULL COMMENT '更新人',
    update_time        datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag           bit(1)      NULL DEFAULT false COMMENT '删除标记'
) comment '战斗记录表';

create table tb_npc_drop
(
    id                 int primary key auto_increment comment '自增主键',
    npc_id             int not null comment 'npcId',
    type               int comment '0技能 1武器 2物品',
    obj_name           varchar(30) comment '物品名称',
    obj_id             int comment '物品id',
    drop_probability   double(8,5) comment '掉落概率',
    create_by          int         NULL DEFAULT NULL COMMENT '创建人',
    create_time        datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by          int         NULL DEFAULT NULL COMMENT '更新人',
    update_time        datetime    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag           bit(1)      NULL DEFAULT false COMMENT '删除标记'
) comment 'npc掉落物品表';