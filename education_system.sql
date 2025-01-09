/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44-log)
 Source Host           : localhost:3306
 Source Schema         : education_system

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44-log)
 File Encoding         : 65001

 Date: 17/12/2024 03:56:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (2, 'admin', 'admin', 'admin', '12345678910', '312@qq.com', '2024-12-25 23:56:21', 1);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `teacher_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'Java程序设计', 'Java编程基础与进阶', 1, '2024-12-04 02:59:56');
INSERT INTO `course` VALUES (3, '软件工程', '软件开发流程与管理', 2, '2024-12-04 02:59:56');
INSERT INTO `course` VALUES (4, 'Web开发', 'Web应用开发技术', 2, '2024-12-04 02:59:56');
INSERT INTO `course` VALUES (17, 'J2EE', 'j2ee', 5, '2024-12-03 00:57:45');
INSERT INTO `course` VALUES (29, 'aaa', 'aaa', 5, '2024-12-07 02:10:40');
INSERT INTO `course` VALUES (32, 'aaa', 'aaaaa', 5, '2024-12-14 02:41:09');
INSERT INTO `course` VALUES (33, 'bbb', 'bbb', 5, '2024-12-17 02:46:15');
INSERT INTO `course` VALUES (36, 'ccc', 'ccc', 5, '2024-12-17 03:15:13');
INSERT INTO `course` VALUES (38, 'ddd', 'ddd', 5, '2024-12-17 03:37:28');

-- ----------------------------
-- Table structure for enrollment
-- ----------------------------
DROP TABLE IF EXISTS `enrollment`;
CREATE TABLE `enrollment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `grade` decimal(5, 2) NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_id`(`student_id`, `course_id`) USING BTREE,
  INDEX `enrollment_ibfk_2`(`course_id`) USING BTREE,
  CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enrollment
-- ----------------------------
INSERT INTO `enrollment` VALUES (12, 3, 17, NULL, '2024-12-04 01:33:11');
INSERT INTO `enrollment` VALUES (13, 5, 17, NULL, '2024-12-04 01:33:11');
INSERT INTO `enrollment` VALUES (21, 1, 1, NULL, '2024-12-04 03:01:15');
INSERT INTO `enrollment` VALUES (26, 1, 29, NULL, '2024-12-07 02:10:46');
INSERT INTO `enrollment` VALUES (30, 6, 17, NULL, '2024-12-17 03:33:27');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `grade` decimal(5, 2) NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_grade`(`student_id`, `course_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `grade_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, 3, 17, 100.00, '2024-12-04 01:22:27', '2024-12-17 02:46:53');
INSERT INTO `grade` VALUES (2, 5, 17, 100.00, '2024-12-04 01:40:23', '2024-12-04 01:40:23');
INSERT INTO `grade` VALUES (3, 6, 17, 100.00, '2024-12-04 01:40:32', '2024-12-17 03:38:13');
INSERT INTO `grade` VALUES (6, 1, 1, 85.50, '2024-12-04 03:01:20', '2024-12-04 03:01:20');
INSERT INTO `grade` VALUES (8, 1, 29, 100.00, '2024-12-07 02:11:01', '2024-12-07 02:11:01');
INSERT INTO `grade` VALUES (9, 3, 29, 90.00, '2024-12-07 02:11:04', '2024-12-07 02:11:04');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN');
INSERT INTO `role` VALUES (2, 'STUDENT');
INSERT INTO `role` VALUES (3, 'TEACHER');

-- ----------------------------
-- Table structure for spring_session
-- ----------------------------
DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE `spring_session`  (
  `PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`) USING BTREE,
  INDEX `SPRING_SESSION_IX1`(`EXPIRY_TIME`) USING BTREE,
  INDEX `SPRING_SESSION_IX2`(`SESSION_ID`) USING BTREE,
  INDEX `SPRING_SESSION_IX3`(`PRINCIPAL_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spring_session
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `enrollment_year` int(11) NULL DEFAULT NULL,
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'student1', '123456', '王同学', '13900139001', 'wang@example.com', 2023, '计算机科学', '2024-12-04 03:00:01');
INSERT INTO `student` VALUES (2, 'student2', '123456', '李同学', '13900139002', 'li@example.com', 2023, '软件工程', '2024-12-04 03:00:01');
INSERT INTO `student` VALUES (3, 'john_doe', '123456', 'Updated Name', '1234567890', 'updated.email@example.com', 2024, 'Software Engineering', '2024-11-18 02:33:55');
INSERT INTO `student` VALUES (5, 'student123', 'password123', 'John Doe', '123456789', 'john.doe@example.com', 2024, 'Computer Science', '2024-11-18 16:16:31');
INSERT INTO `student` VALUES (6, 'student', 'student', '学生', '15261433305', '2158588419@qq.com', NULL, '软件工程', '2024-12-03 00:17:28');
INSERT INTO `student` VALUES (12, 'wqz', 'wqzwqz', NULL, NULL, '2158588419@qq.com', NULL, NULL, '2024-12-17 03:32:06');
INSERT INTO `student` VALUES (13, 'studentt', 'student', NULL, NULL, '2158588419@qq.com', NULL, NULL, '2024-12-17 03:35:20');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `hireDate` time NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'teacher1', '123456', '张老师', '13800138001', 'zhang@example.com', '计算机系', '01:17:43', '2024-12-04 02:59:13');
INSERT INTO `teacher` VALUES (2, 'teacher2', '123456', '李老师', '13800138002', 'li@example.com', '软件工程系', '01:17:40', '2024-12-04 02:59:13');
INSERT INTO `teacher` VALUES (3, 'teacher', 'teacher', 'teacher', '12121212', '1212@2112.com', 'teacher', '01:17:32', '2024-12-07 01:17:34');
INSERT INTO `teacher` VALUES (5, 'jane', '123456', 'Jane', '8654210', 'jane@example.com', 'Mathematics', '00:00:00', '2024-11-18 14:02:07');
INSERT INTO `teacher` VALUES (6, 'jane_doe', '123456', 'Jane Doe', '98765432101', 'jane.doe@example.com', 'Mathematics', '01:17:37', '2024-11-18 14:06:23');
INSERT INTO `teacher` VALUES (16, 'zls', 'zlszls', '张老师', '12332112332', '123312@aw.com', '计算机系', '00:00:00', '2024-12-09 01:56:21');
INSERT INTO `teacher` VALUES (18, 'teacher5', 'teacher1', NULL, NULL, '2158588419@qq.com', NULL, NULL, '2024-12-15 23:07:41');
INSERT INTO `teacher` VALUES (19, 'jane2', '123456', NULL, NULL, '2158588419@qq.com', NULL, NULL, '2024-12-16 20:49:56');
INSERT INTO `teacher` VALUES (20, 'teacher3', '123456', NULL, NULL, '2158588419@qq.com', NULL, NULL, '2024-12-17 01:20:01');
INSERT INTO `teacher` VALUES (21, 'jane3', '123456', NULL, NULL, '2158588419@qq.com', NULL, NULL, '2024-12-17 02:45:09');

SET FOREIGN_KEY_CHECKS = 1;
