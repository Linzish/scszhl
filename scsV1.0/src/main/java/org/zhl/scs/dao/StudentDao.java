package org.zhl.scs.dao;

import java.util.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.zhl.scs.dao.provider.StudentDynaSqlProvider;
import org.zhl.scs.domain.Student;

@Mapper
public interface StudentDao{

	@InsertProvider(type=StudentDynaSqlProvider.class,method="insertStudent")
	@Options(useGeneratedKeys=true,keyProperty="id")
	void save(Student entity);

	@UpdateProvider(type=StudentDynaSqlProvider.class,method="updateStudent")
	void update(Student entity);

	@Delete(" delete from tb_student where id = #{id} ")
	void deleteById(Integer id);

	@SelectProvider(type=StudentDynaSqlProvider.class,method="count")
	Integer count(Map<String, Object> params);

	@Select("select * from tb_student where ID = #{id}")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="name",property="name"),
		@Result(column="uid",property="uid"),
		@Result(column="sex",property="sex"),
		@Result(column="birthday",property="birthday",javaType=java.util.Date.class),
		@Result(column="admissiontime",property="admissiontime",javaType=java.util.Date.class),
		@Result(column="nativeplace",property="nativeplace"),
		@Result(column="address",property="address"),
		@Result(column="postcode",property="postcode"),
		@Result(column="profession",property="profession"),
		@Result(column="user_id",property="user",one=@One(select="org.zhl.scs.dao.UserDao.selectByIdWithUserId",fetchType=FetchType.EAGER)),
		@Result(column="image_id",property="image",one=@One(select="org.zhl.scs.dao.ImageDao.selectByIdWithImageId",fetchType=FetchType.EAGER)),
		@Result(column="clazz_id",property="clazz",one=@One(select="org.zhl.scs.dao.ClazzDao.selectByIdWithClazzId",fetchType=FetchType.EAGER)),
		@Result(column="id",property="courses",many=@Many(select="org.zhl.scs.dao.CourseDao.selectByStudentId",fetchType=FetchType.LAZY)),
		@Result(column="id",property="attendanceDetails",many=@Many(select="org.zhl.scs.dao.AttendanceDetailDao.selectByStudentId",fetchType=FetchType.LAZY))
	})
	Student selectById(Integer id);

	@SelectProvider(type=StudentDynaSqlProvider.class,method="selectWithParam")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="name",property="name"),
		@Result(column="uid",property="uid"),
		@Result(column="sex",property="sex"),
		@Result(column="birthday",property="birthday",javaType=java.util.Date.class),
		@Result(column="admissiontime",property="admissiontime",javaType=java.util.Date.class),
		@Result(column="nativeplace",property="nativeplace"),
		@Result(column="address",property="address"),
		@Result(column="postcode",property="postcode"),
		@Result(column="profession",property="profession"),
		@Result(column="user_id",property="user",one=@One(select="org.zhl.scs.dao.UserDao.selectByIdWithUserId",fetchType=FetchType.EAGER)),
		@Result(column="image_id",property="image",one=@One(select="org.zhl.scs.dao.ImageDao.selectByIdWithImageId",fetchType=FetchType.EAGER)),
		@Result(column="clazz_id",property="clazz",one=@One(select="org.zhl.scs.dao.ClazzDao.selectByIdWithClazzId",fetchType=FetchType.EAGER)),
		@Result(column="id",property="courses",many=@Many(select="org.zhl.scs.dao.CourseDao.selectByStudentId",fetchType=FetchType.LAZY)),
		@Result(column="id",property="attendanceDetails",many=@Many(select="org.zhl.scs.dao.AttendanceDetailDao.selectByStudentId",fetchType=FetchType.LAZY))
	})
	List<Student> selectByPage(Map<String, Object> params);

	@Select("select * from tb_student where user_id = #{id}")
	Student selectByUserId(Integer id);

	@Select("select * from tb_student where image_id = #{id}")
	Student selectByImageId(Integer id);

	@Select("select * from tb_student where clazz_id = #{id}")
	Student selectByClazzId(Integer id);

	@Select("select * from tb_student where id in (select student_id from student_course where course_id = #{id} )")
	Student selectByCourseId(Integer id);

	@InsertProvider(type=StudentDynaSqlProvider.class,method="insertCourses")
	void insertCourses(Student entity);

	@Select("select * from tb_student where id = #{student_id}")
	Student selectByIdWithStudentId(@Param("student_id") Integer id);

}