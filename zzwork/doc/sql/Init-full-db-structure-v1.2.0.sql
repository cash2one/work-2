-- phpMyAdmin SQL Dump
-- version 3.3.10deb1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 07 月 12 日 14:49
-- 服务器版本: 5.1.54
-- PHP 版本: 5.3.5-1ubuntu7.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `zzwork`
--

-- --------------------------------------------------------

--
-- 表的结构 `attendance`
--

CREATE TABLE IF NOT EXISTS `attendance` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '姓名',
  `code` varchar(45) NOT NULL DEFAULT '' COMMENT '考勤表的登记号码',
  `account` varchar(45) NOT NULL DEFAULT '' COMMENT '系统中的账户',
  `gmt_work` datetime NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `schedule_id` int(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_code` (`code`),
  KEY `idx_account` (`account`),
  KEY `idx_gmt_work` (`gmt_work`),
  KEY `idx_schedule_id` (`schedule_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='考勤原始数据' AUTO_INCREMENT=26345 ;

-- --------------------------------------------------------

--
-- 表的结构 `attendance_analysis`
--

CREATE TABLE IF NOT EXISTS `attendance_analysis` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL DEFAULT '',
  `name` varchar(45) NOT NULL DEFAULT '',
  `account` varchar(45) NOT NULL DEFAULT '',
  `day_full` decimal(7,3) DEFAULT '0.000' COMMENT '当月应出勤天数',
  `day_actual` decimal(7,3) DEFAULT '0.000' COMMENT '实际出勤天数',
  `day_leave` decimal(7,3) DEFAULT '0.000' COMMENT '请假天数',
  `day_other` decimal(7,3) DEFAULT '0.000' COMMENT '其他天数(单位小时)',
  `day_unwork` decimal(7,3) DEFAULT '0.000' COMMENT '旷工天数',
  `day_unrecord` int(11) DEFAULT '0' COMMENT '未打卡次数',
  `day_late` int(11) DEFAULT '0' COMMENT '迟到次数',
  `day_early` int(11) DEFAULT '0' COMMENT '早退次数',
  `day_overtime` int(11) DEFAULT '0' COMMENT '加班次数',
  `gmt_target` datetime NOT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `schedule_id` int(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_name` (`name`),
  KEY `idx_account` (`account`),
  KEY `idx_gmt_target` (`gmt_target`),
  KEY `idx_schedule_id` (`schedule_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='考勤统计分析结果' AUTO_INCREMENT=6728 ;

-- --------------------------------------------------------

--
-- 表的结构 `attendance_schedule`
--

CREATE TABLE IF NOT EXISTS `attendance_schedule` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '班次名称',
  `isuse` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否正常使用\n1：正常（默认）\n0：未使用',
  `created_by` varchar(45) NOT NULL DEFAULT '' COMMENT '创建者',
  `modified_by` varchar(45) NOT NULL DEFAULT '' COMMENT '修改人',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_isuse` (`isuse`),
  KEY `idx_created_by` (`created_by`),
  KEY `idx_modified_by` (`modified_by`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='排班计划' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `attendance_schedule_detail`
--

CREATE TABLE IF NOT EXISTS `attendance_schedule_detail` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(20) NOT NULL DEFAULT '0' COMMENT '班次ID',
  `gmt_month` datetime NOT NULL COMMENT '排班时间，精确到月，例：2013-05-01表示2013年5月的详细排班计划',
  `day_of_year` int(11) DEFAULT NULL COMMENT '一年中第N天',
  `day_of_month` int(11) DEFAULT NULL COMMENT '一个月中的第N天',
  `day_of_week` int(11) DEFAULT NULL COMMENT '一个星期中的第N天',
  `day` datetime DEFAULT NULL COMMENT '工作日',
  `workf` int(20) DEFAULT NULL,
  `workt` int(20) DEFAULT NULL,
  `unixtime` int(20) DEFAULT NULL,
  `created_by` varchar(45) NOT NULL,
  `modified_by` varchar(45) NOT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_schedule_id` (`schedule_id`),
  KEY `idx_gmt_month` (`gmt_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='排班计划（详细）' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `auth_right`
--

CREATE TABLE IF NOT EXISTS `auth_right` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '权限code',
  `name` varchar(50) NOT NULL COMMENT '权限名',
  `sort` int(11) NOT NULL DEFAULT '0',
  `content` varchar(254) DEFAULT NULL COMMENT '权限',
  `menu` varchar(50) DEFAULT NULL COMMENT '菜单名',
  `menu_url` varchar(254) DEFAULT NULL COMMENT '菜单URL',
  `menu_css` varchar(50) DEFAULT NULL,
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_sort` (`sort`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=414 ;

-- --------------------------------------------------------

--
-- 表的结构 `auth_role`
--

CREATE TABLE IF NOT EXISTS `auth_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名字',
  `remark` varchar(254) DEFAULT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

-- --------------------------------------------------------

--
-- 表的结构 `auth_role_right`
--

CREATE TABLE IF NOT EXISTS `auth_role_right` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `right_id` int(20) NOT NULL,
  `role_id` int(20) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_right_id` (`right_id`),
  KEY `Index_role_id` (`role_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1689 ;

-- --------------------------------------------------------

--
-- 表的结构 `auth_user`
--

CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL,
  `steping` tinyint(4) DEFAULT '0' COMMENT '0：正常\n其他：不能正常登录',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=262 ;

-- --------------------------------------------------------

--
-- 表的结构 `auth_user_role`
--

CREATE TABLE IF NOT EXISTS `auth_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_role_id` (`role_id`),
  KEY `index_user_id` (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=412 ;

-- --------------------------------------------------------

--
-- 表的结构 `bs`
--

CREATE TABLE IF NOT EXISTS `bs` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL COMMENT '项目代号',
  `password` varchar(45) NOT NULL COMMENT '与keys对应，在单点登录中可能会用到',
  `name` varchar(250) NOT NULL COMMENT '业务系统名称',
  `right_code` varchar(45) NOT NULL DEFAULT '' COMMENT '对应权限父节点',
  `url` varchar(250) DEFAULT NULL COMMENT '连接',
  `avatar` varchar(250) DEFAULT NULL COMMENT '代表的图片',
  `note` varchar(1000) DEFAULT NULL COMMENT '详细描述',
  `types` varchar(45) NOT NULL COMMENT '系统类别：\n0：软件产品\n1：业务系统\n2：客户网站',
  `versions` varchar(45) NOT NULL COMMENT '当前版本',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `idx_types` (`types`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='公司的业务系统' AUTO_INCREMENT=59 ;

-- --------------------------------------------------------

--
-- 表的结构 `bs_dept`
--

CREATE TABLE IF NOT EXISTS `bs_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bs_id` int(20) NOT NULL,
  `dept_id` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bs_id` (`bs_id`),
  KEY `idx_dept_id` (`dept_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=316 ;

-- --------------------------------------------------------

--
-- 表的结构 `bs_staff`
--

CREATE TABLE IF NOT EXISTS `bs_staff` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bs_id` int(20) NOT NULL,
  `staff_id` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bs_id` (`bs_id`),
  KEY `idx_staff_id` (`staff_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='员工单独关联的业务系统' AUTO_INCREMENT=16 ;

-- --------------------------------------------------------

--
-- 表的结构 `contacts`
--

CREATE TABLE IF NOT EXISTS `contacts` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `keys` varchar(45) NOT NULL DEFAULT '0' COMMENT '通讯类型\n0:email\n1:手机\n2:固定电话\n3:qq\n4:其他',
  `values` varchar(45) NOT NULL DEFAULT '' COMMENT '通讯内容',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工通讯信息' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `dept`
--

CREATE TABLE IF NOT EXISTS `dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `code` varchar(50) NOT NULL DEFAULT '0' COMMENT '父ID，根为0',
  `note` varchar(1000) DEFAULT NULL COMMENT '部门信息，可以放在工作平台首页显示',
  `gmt_created` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='公司部门' AUTO_INCREMENT=43 ;

-- --------------------------------------------------------

--
-- 表的结构 `dept_right`
--

CREATE TABLE IF NOT EXISTS `dept_right` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `dept_id` int(20) NOT NULL,
  `auth_right_id` int(20) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_auth_right_id` (`auth_right_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='部门权限，可以设置每个部门对应的权限' AUTO_INCREMENT=2672 ;

-- --------------------------------------------------------

--
-- 表的结构 `feedback`
--

CREATE TABLE IF NOT EXISTS `feedback` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bs_id` int(20) NOT NULL DEFAULT '0',
  `account` varchar(50) NOT NULL DEFAULT '',
  `topic` varchar(100) DEFAULT '',
  `content` varchar(2000) DEFAULT '',
  `status` tinyint(4) DEFAULT '0' COMMENT '处理状态：\n0：未处理\n1：已解决\n2：不解决\n3：无法解决',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bs_id` (`bs_id`),
  KEY `idx_account` (`account`),
  KEY `idx_status` (`status`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='对系统的意见反鐀' AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- 表的结构 `param`
--

CREATE TABLE IF NOT EXISTS `param` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `types` varchar(64) NOT NULL,
  `names` varchar(254) DEFAULT NULL,
  `key` varchar(254) NOT NULL,
  `value` varchar(254) NOT NULL,
  `sort` tinyint(4) DEFAULT NULL,
  `used` tinyint(4) NOT NULL DEFAULT '0',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `param_type`
--

CREATE TABLE IF NOT EXISTS `param_type` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(45) NOT NULL,
  `name` varchar(254) DEFAULT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `scheduler_event`
--

CREATE TABLE IF NOT EXISTS `scheduler_event` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(254) DEFAULT NULL COMMENT '事件标题',
  `details` varchar(2000) DEFAULT NULL COMMENT '详细内容',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `assign_account` varchar(45) DEFAULT '' COMMENT '分配任务的账户',
  `owner_account` varchar(45) NOT NULL DEFAULT '' COMMENT '任务拥有者',
  `dept_code` varchar(50) DEFAULT '' COMMENT '事件创建或更新时所在的部门',
  `persent` tinyint(4) DEFAULT '0' COMMENT '任务完成百分比\n0-100',
  `importance` tinyint(4) DEFAULT '0' COMMENT '重要性\n不同程度用不同颜色在日历上表现',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_owner_account` (`owner_account`),
  KEY `idx_start_date` (`start_date`),
  KEY `idx_end_date` (`end_date`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='日程' AUTO_INCREMENT=236 ;

-- --------------------------------------------------------

--
-- 表的结构 `scheduler_report`
--

CREATE TABLE IF NOT EXISTS `scheduler_report` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `dept_code` varchar(45) NOT NULL COMMENT '提交周报时，员工所在部门',
  `account` varchar(45) NOT NULL,
  `text` varchar(254) DEFAULT '' COMMENT '日/周报标题',
  `details` varchar(2000) DEFAULT '' COMMENT '周报详细内容',
  `compose_date` datetime NOT NULL COMMENT '撰写日期',
  `year` char(5) DEFAULT NULL COMMENT '年份',
  `week` tinyint(4) DEFAULT NULL COMMENT '一年中第几周',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_dept_code` (`dept_code`),
  KEY `idx_account` (`account`),
  KEY `idx_week` (`week`),
  KEY `idx_compose_date` (`compose_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='日/周报' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `scheduler_report_event`
--

CREATE TABLE IF NOT EXISTS `scheduler_report_event` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `event_id` int(20) NOT NULL,
  `report_id` int(20) NOT NULL,
  `gmt_created` varchar(45) DEFAULT NULL,
  `gmt_modified` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '员工账号',
  `staff_no` varchar(45) NOT NULL COMMENT '员工工号',
  `dept_code` varchar(50) NOT NULL DEFAULT '0' COMMENT '账号信息',
  `name` varchar(45) NOT NULL COMMENT '员工姓名',
  `email` varchar(45) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(250) DEFAULT '' COMMENT '账号头像',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `jobs` varchar(45) DEFAULT '' COMMENT '职位\n0：员工\n1：组长\n2：主管\n3：经理\n4：boss',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '员工状态\n0：试用员工\n1：正式员工\n2：离职',
  `gmt_entry` datetime NOT NULL COMMENT '入职时间',
  `gmt_left` datetime DEFAULT NULL COMMENT '离职时间，在职员工留空',
  `note` varchar(1000) DEFAULT '' COMMENT '备注',
  `gmt_created` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account` (`account`),
  UNIQUE KEY `idx_staff_no` (`staff_no`),
  KEY `idx_status` (`status`),
  KEY `idx_dept_code` (`dept_code`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='员工信息' AUTO_INCREMENT=258 ;

-- --------------------------------------------------------

--
-- 表的结构 `tomcat_manager`
--

CREATE TABLE IF NOT EXISTS `tomcat_manager` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `warname` varchar(100) DEFAULT NULL,
  `appname` varchar(100) DEFAULT NULL,
  `start` tinyint(1) DEFAULT NULL,
  `start_test` tinyint(1) DEFAULT '0',
  `stop` tinyint(1) DEFAULT NULL,
  `stop_test` tinyint(1) DEFAULT '0',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `filename` varchar(30) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `tomcat_part` varchar(20) DEFAULT NULL,
  `server_part` varchar(20) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `ip` (`ip`),
  KEY `start` (`start`),
  KEY `start_test` (`start_test`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=91 ;
