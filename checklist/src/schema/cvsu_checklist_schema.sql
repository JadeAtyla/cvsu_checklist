-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cvsu_checklist
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cvsu_checklist
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cvsu_checklist` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cvsu_checklist` ;

-- -----------------------------------------------------
-- Table `cvsu_checklist`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`student` (
  `student_number` BIGINT NOT NULL,
  `first_name` VARCHAR(55) NULL DEFAULT NULL,
  `last_name` VARCHAR(55) NULL DEFAULT NULL,
  `middle_initial` VARCHAR(55) NULL DEFAULT NULL,
  `date_of_admission` DATE NULL DEFAULT NULL,
  `contact_number` VARCHAR(12) NULL DEFAULT NULL,
  `program` VARCHAR(55) NULL DEFAULT NULL,
  `year` INT NULL DEFAULT NULL,
  `section` INT NULL DEFAULT NULL,
  `name_of_adviser` VARCHAR(55) NULL DEFAULT NULL,
  PRIMARY KEY (`student_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cvsu_checklist`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`address` (
  `address_ID` INT NOT NULL,
  `street_address` VARCHAR(55) NULL DEFAULT NULL,
  `barangay` VARCHAR(55) NULL DEFAULT NULL,
  `city` VARCHAR(55) NULL DEFAULT NULL,
  `province` VARCHAR(55) NULL DEFAULT NULL,
  `student_number` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`address_ID`),
  INDEX `student_number` (`student_number` ASC) VISIBLE,
  CONSTRAINT `address_ibfk_1`
    FOREIGN KEY (`student_number`)
    REFERENCES `cvsu_checklist`.`student` (`student_number`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cvsu_checklist`.`professor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`professor` (
  `professor_ID` INT NOT NULL,
  `first_name` VARCHAR(55) NULL DEFAULT NULL,
  `last_name` VARCHAR(55) NULL DEFAULT NULL,
  PRIMARY KEY (`professor_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cvsu_checklist`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`course` (
  `course_code` VARCHAR(10) NOT NULL,
  `course_title` VARCHAR(55) NULL DEFAULT NULL,
  `credit_unit_lab` INT NULL DEFAULT NULL,
  `credit_unit_lec` INT NULL DEFAULT NULL,
  `contact_hr_lab` INT NULL DEFAULT NULL,
  `contact_hr_lec` INT NULL DEFAULT NULL,
  `pre_requisite` VARCHAR(55) NULL DEFAULT NULL,
  `year` INT NULL DEFAULT NULL,
  `semester` INT NULL DEFAULT NULL,
  `professor_ID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`course_code`),
  INDEX `professor_ID` (`professor_ID` ASC) VISIBLE,
  CONSTRAINT `course_ibfk_1`
    FOREIGN KEY (`professor_ID`)
    REFERENCES `cvsu_checklist`.`professor` (`professor_ID`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cvsu_checklist`.`grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`grades` (
  `grade_id` INT NOT NULL AUTO_INCREMENT,
  `grade` VARCHAR(55) NOT NULL,
  `equivalent` VARCHAR(55) NULL DEFAULT NULL,
  `course_code` VARCHAR(10) NULL DEFAULT NULL,
  `student_number` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  INDEX `student_number` (`student_number` ASC) VISIBLE,
  INDEX `grades_ibfk_1` (`course_code` ASC) VISIBLE,
  CONSTRAINT `grades_ibfk_1`
    FOREIGN KEY (`course_code`)
    REFERENCES `cvsu_checklist`.`course` (`course_code`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 244
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cvsu_checklist`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`user` (
  `id` INT NOT NULL,
  `username` VARCHAR(55) NULL DEFAULT NULL,
  `password` VARCHAR(55) NULL DEFAULT NULL,
  `age` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `cvsu_checklist` ;

-- -----------------------------------------------------
-- Placeholder table for view `cvsu_checklist`.`grade_professor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`grade_professor` (`grade_id` INT, `grade` INT, `equivalent` INT, `student_number` INT, `course_code` INT, `course_title` INT, `credit_unit_lab` INT, `credit_unit_lec` INT, `contact_hr_lab` INT, `contact_hr_lec` INT, `pre_requisite` INT, `year` INT, `semester` INT, `professor_name` INT);

-- -----------------------------------------------------
-- Placeholder table for view `cvsu_checklist`.`student_info_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cvsu_checklist`.`student_info_address` (`student_number` INT, `student_name` INT, `date_of_admission` INT, `contact_number` INT, `program` INT, `year` INT, `section` INT, `name_of_adviser` INT, `full_address` INT);

-- -----------------------------------------------------
-- procedure getGradeAndProfessor
-- -----------------------------------------------------

DELIMITER $$
USE `cvsu_checklist`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getGradeAndProfessor`(in studentNum varchar(20))
begin
		select *
		from grade_professor where student_number = studentNum;
	end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getStudentAddress
-- -----------------------------------------------------

DELIMITER $$
USE `cvsu_checklist`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStudentAddress`(in studentNum varchar(20))
begin
select concat_ws(", ", street_address, barangay, city, province) as full_address from address where student_number = studentNum;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getStudentCourseInfo
-- -----------------------------------------------------

DELIMITER $$
USE `cvsu_checklist`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStudentCourseInfo`(in studentNum varchar(20))
begin
	select 
	-- Course Taken
	(select count(*) from course where course_code = any(select course_code from grades where student_number = studentNum and grade is not null)) as course_taken,
	-- Course Left
	(select count(*) from course where course_code not in (select course_code from grades where student_number = studentNum and grade is not null)) as course_left,
	-- Course Failed
	(select count(*) from grades where student_number = studentNum and grade > 3.00) as course_failed;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getStudentProgram
-- -----------------------------------------------------

DELIMITER $$
USE `cvsu_checklist`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStudentProgram`(in studentNum varchar(20))
begin
select concat_ws(" ", concat_ws(", ", last_name, first_name), concat(middle_initial, ".")) as student_name, student_number, program from student where student_number = studentNum;
end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure searchingStash
-- -----------------------------------------------------

DELIMITER $$
USE `cvsu_checklist`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchingStash`(in studentNum varchar(20), in searchTxt varchar(55), in sortCondition varchar(55))
begin
	set @sql = concat("select * from grade_professor 
where student_number = '",studentNum,"'
and (professor_name LIKE '%",searchTxt,"%'
OR course_code LIKE '%",searchTxt,"%' 
OR grade LIKE '%",searchTxt,"%' OR year LIKE '%",searchTxt,"%' OR semester LIKE '%",searchTxt,"%')
",sortCondition,";");
    prepare statement from @sql;
    execute statement;
    deallocate prepare statement;
    end$$

DELIMITER ;

-- -----------------------------------------------------
-- View `cvsu_checklist`.`grade_professor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cvsu_checklist`.`grade_professor`;
USE `cvsu_checklist`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `cvsu_checklist`.`grade_professor` AS select `cvsu_checklist`.`grades`.`grade_id` AS `grade_id`,`cvsu_checklist`.`grades`.`grade` AS `grade`,`cvsu_checklist`.`grades`.`equivalent` AS `equivalent`,`cvsu_checklist`.`grades`.`student_number` AS `student_number`,`course_professor`.`course_code` AS `course_code`,`course_professor`.`course_title` AS `course_title`,`course_professor`.`credit_unit_lab` AS `credit_unit_lab`,`course_professor`.`credit_unit_lec` AS `credit_unit_lec`,`course_professor`.`contact_hr_lab` AS `contact_hr_lab`,`course_professor`.`contact_hr_lec` AS `contact_hr_lec`,`course_professor`.`pre_requisite` AS `pre_requisite`,`course_professor`.`year` AS `year`,`course_professor`.`semester` AS `semester`,`course_professor`.`professor_name` AS `professor_name` from ((select `cvsu_checklist`.`course`.`course_code` AS `course_code`,`cvsu_checklist`.`course`.`course_title` AS `course_title`,`cvsu_checklist`.`course`.`credit_unit_lab` AS `credit_unit_lab`,`cvsu_checklist`.`course`.`credit_unit_lec` AS `credit_unit_lec`,`cvsu_checklist`.`course`.`contact_hr_lab` AS `contact_hr_lab`,`cvsu_checklist`.`course`.`contact_hr_lec` AS `contact_hr_lec`,`cvsu_checklist`.`course`.`pre_requisite` AS `pre_requisite`,`cvsu_checklist`.`course`.`year` AS `year`,`cvsu_checklist`.`course`.`semester` AS `semester`,concat(`cvsu_checklist`.`professor`.`last_name`,', ',`cvsu_checklist`.`professor`.`first_name`) AS `professor_name`,`cvsu_checklist`.`professor`.`professor_ID` AS `professor_ID` from (`cvsu_checklist`.`professor` left join `cvsu_checklist`.`course` on((`cvsu_checklist`.`course`.`professor_ID` = `cvsu_checklist`.`professor`.`professor_ID`)))) `course_professor` left join `cvsu_checklist`.`grades` on((`cvsu_checklist`.`grades`.`course_code` = `course_professor`.`course_code`)));

-- -----------------------------------------------------
-- View `cvsu_checklist`.`student_info_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cvsu_checklist`.`student_info_address`;
USE `cvsu_checklist`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `cvsu_checklist`.`student_info_address` AS select `cvsu_checklist`.`student`.`student_number` AS `student_number`,concat(`cvsu_checklist`.`student`.`last_name`,', ',`cvsu_checklist`.`student`.`first_name`,`cvsu_checklist`.`student`.`middle_initial`) AS `student_name`,`cvsu_checklist`.`student`.`date_of_admission` AS `date_of_admission`,`cvsu_checklist`.`student`.`contact_number` AS `contact_number`,`cvsu_checklist`.`student`.`program` AS `program`,`cvsu_checklist`.`student`.`year` AS `year`,`cvsu_checklist`.`student`.`section` AS `section`,`cvsu_checklist`.`student`.`name_of_adviser` AS `name_of_adviser`,concat(`cvsu_checklist`.`address`.`street_address`,' ',`cvsu_checklist`.`address`.`barangay`,' ',`cvsu_checklist`.`address`.`city`,' ',`cvsu_checklist`.`address`.`province`) AS `full_address` from (`cvsu_checklist`.`address` left join `cvsu_checklist`.`student` on((`cvsu_checklist`.`address`.`student_number` = `cvsu_checklist`.`student`.`student_number`)));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
