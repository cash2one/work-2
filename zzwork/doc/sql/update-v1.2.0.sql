CREATE  TABLE IF NOT EXISTS `attendance_schedule` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '班次名称' ,
  `isuse` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '是否正常使用\n1：正常（默认）\n0：未使用' ,
  `created_by` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '创建者' ,
  `modified_by` VARCHAR(45) NOT NULL DEFAULT '' COMMENT '修改人' ,
  `gmt_created` DATETIME NULL DEFAULT NULL ,
  `gmt_modified` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_isuse` (`isuse` ASC) ,
  INDEX `idx_created_by` (`created_by` ASC) ,
  INDEX `idx_modified_by` (`modified_by` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '排班计划';

CREATE  TABLE IF NOT EXISTS `attendance_schedule_detail` (
  `id` INT(20) NOT NULL AUTO_INCREMENT ,
  `schedule_id` INT(20) NOT NULL DEFAULT 0 COMMENT '班次ID' ,
  `gmt_month` DATETIME NOT NULL COMMENT '排班时间，精确到月，例：2013-05-01表示2013年5月的详细排班计划' ,
  `day_of_year` INT(11) NULL DEFAULT NULL COMMENT '一年中第N天' ,
  `day_of_month` INT(11) NULL DEFAULT NULL COMMENT '一个月中的第N天' ,
  `day_of_week` INT(11) NULL DEFAULT NULL COMMENT '一个星期中的第N天' ,
  `day` INT(20) NULL DEFAULT NULL ,
  `workf` INT(20) NULL DEFAULT NULL ,
  `workt` INT(20) NULL DEFAULT NULL ,
  `unixtime` INT(20) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NOT NULL ,
  `modified_by` VARCHAR(45) NOT NULL ,
  `gmt_created` DATETIME NULL DEFAULT NULL ,
  `gmt_modified` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `idx_schedule_id` (`schedule_id` ASC) ,
  INDEX `idx_gmt_month` (`gmt_month` ASC) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '排班计划（详细）';


ALTER TABLE `attendance` ADD COLUMN `schedule_id` INT(20) NULL DEFAULT 0  AFTER `gmt_modified`, ADD INDEX `idx_schedule_id` (`schedule_id` ASC) ;
ALTER TABLE `attendance_analysis` ADD COLUMN `schedule_id` INT(20) NULL DEFAULT 0  AFTER `gmt_modified`, ADD INDEX `idx_schedule_id` (`schedule_id` ASC) ;

