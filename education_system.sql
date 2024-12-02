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

 Date: 02/12/2024 02:31:07
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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '123456', NULL, NULL, NULL, '2024-11-18 15:26:33', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (5, 'admin', 'Learn about data structures.', 1, '2024-11-18 14:51:36');
INSERT INTO `course` VALUES (6, 'Math', 'Basic Mathematics', 1, '2024-11-18 15:14:59');
INSERT INTO `course` VALUES (7, 'Mathematics', 'An advanced math course', 1, '2024-11-18 18:04:49');

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
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enrollment
-- ----------------------------
INSERT INTO `enrollment` VALUES (4, 1, 6, 90.00, '2024-11-18 15:16:03');
INSERT INTO `enrollment` VALUES (8, 3, 6, 90.00, '2024-11-18 15:21:48');
INSERT INTO `enrollment` VALUES (9, 5, 7, 74.00, '2024-11-18 22:34:42');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'wqz', 'wqz', 'wqz', '123123', '12312@as.com', 2024, '英语', '2024-11-18 02:08:30');
INSERT INTO `student` VALUES (3, 'john_doe', '123456', 'Updated Name', '1234567890', 'updated.email@example.com', 2024, 'Software Engineering', '2024-11-18 02:33:55');
INSERT INTO `student` VALUES (4, 'student1', '123456', 'Student One', NULL, NULL, NULL, NULL, '2024-11-18 15:14:29');
INSERT INTO `student` VALUES (5, 'student123', 'password123', 'John Doe', '123456789', 'john.doe@example.com', 2024, 'Computer Science', '2024-11-18 16:16:31');

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'wqz', 'wqz', 'wqz', '2131231', '123123@sad.com', '2024', NULL, '2024-11-18 13:32:16');
INSERT INTO `teacher` VALUES (5, 'jane', '123456', 'Jane', '8654210', 'jane@example.com', 'Mathematics', '00:00:00', '2024-11-18 14:02:07');
INSERT INTO `teacher` VALUES (6, 'jane_doe', '123456', 'Jane Doe', '9876543210', 'jane.doe@example.com', 'Mathematics', NULL, '2024-11-18 14:06:23');

SET FOREIGN_KEY_CHECKS = 1;
