package cn.tedu.note.dao;
//测试myBatis支持自增类型
import cn.tedu.note.entity.Person;

public interface PersonDao {
	int savePerson(Person person);
}
