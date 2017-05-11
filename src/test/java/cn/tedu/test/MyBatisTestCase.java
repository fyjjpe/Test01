package cn.tedu.test;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.PersonDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Person;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.service.UserService;

public class MyBatisTestCase {
	ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml","spring-service.xml");
	}
	
	@Test
	public void testDataSource() throws SQLException{
		DataSource ds = ctx.getBean("dataSource",DataSource.class);
		Connection conn = ds.getConnection();
		String sql = "select 'Hello World!' as s";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("s"));
		}
		conn.close();
	}
	
	@Test
	public void testAddUser(){
		//mapperɨ������Mapper�ӿ�UserDao����ΪBean����(���÷����ڵײ㶯̬�Ĵ����ӿڵ�ʵ������)
		UserDao dao = ctx.getBean("userDao",UserDao.class);
		User user = new User("123","tom","123","","Tom");
		int n = dao.addUser(user);
		System.out.println(n);
		//spring-mvc�Զ��ύ����
	}
	
	@Test
	public void testFindUserByName(){
		UserDao dao = ctx.getBean("userDao",UserDao.class);
		User user = dao.findUserByName("tom");
		System.out.println(user);
	}
	
	@Test
	public void testLogin(){
		String name = "tom";
		String pwd = "123";
		UserService service = ctx.getBean("userService",UserService.class);
		User user = service.login(name, pwd);
		System.out.println(user);
	}
	
	@Test
	public void testMd5(){
		String str = "123";
		String md5 = DigestUtils.md5Hex(str);
		System.out.println(md5);//202cb962ac59075b964b07152d234b70
	}
	
	//����ժҪ����
	@Test
	public void testMdSalt(){
		String str = "123";
		String salt = "tedu.cn��һ����ѵ����";
		String md5 = DigestUtils.md5Hex(str+salt);
		System.out.println(md5);//92538b3c4bcbabf7ab3fd502f0f8ab55
	}
	
	//�����ļ�ժҪ
	@Test
	public void testFileMd5() throws Exception{
		String filename = "d:/readme.txt";
		FileInputStream fis = new FileInputStream(filename);
		String md5 = DigestUtils.md5Hex(fis);
		System.out.println(md5);//202cb962ac59075b964b07152d234b70
	}
	
	@Test
	public void testRegist(){
		UserService service = ctx.getBean("userService",UserService.class);
		User user = service.regist("Jerry", "JCK", "123", "123");
		System.out.println(user);
	}
	
	@Test
	public void testFindNotebooksByUserId(){
		String id = "ea09d9b1-ede7-4bd8-b43d-a546680df00b";
		NotebookDao dao = ctx.getBean("notebookDao",NotebookDao.class);
		List<Map<String,Object>> list = dao.findNotebooksByUserId(id);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testListNotebooks(){
		String id = "ea09d9b1-ede7-4bd8-b43d-a546680df00b";
		NotebookService service = ctx.getBean("notebookService",NotebookService.class);
		List<Map<String,Object>> list = service.listNotebooks(id);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testFindNotesByNotebookId(){
		String notebookId = "fa8d3d9d-2de5-4cfe-845f-951041bcc461";
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		List<Map<String,Object>> list = dao.findNotesByNotebookId(notebookId);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testFindNotebookById(){
		String id = "01e24d89-15ab-4b6a-bf6f-2e5ad10b2041";
		NotebookDao dao = ctx.getBean("notebookDao",NotebookDao.class);
		System.out.println(dao.findNotebookById(id));
	}
	
	@Test
	public void testListNotes(){
		String notebookId = "fa8d3d9d-2de5-4cfe-845f-951041bcc461";
		NoteService service = ctx.getBean("noteService",NoteService.class);
		List<Map<String,Object>> list = service.listNotes(notebookId);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testFindNoteById(){
		String id = "051538a6-0f8e-472c-8765-251a795bc88f";
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		System.out.println(dao.findNoteById(id));
	}
	
	@Test
	public void testGetNote(){
		String noteId = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		NoteService service = ctx.getBean("noteService",NoteService.class);
		Note note = service.getNote(noteId);
		System.out.println(note);
		
	}
	
	@Test
	public void testUpdateNote(){
		String id = "051538a6-0f8e-472c-8765-251a795bc88f";
		Map<String,Object> note = new HashMap<String, Object>();
		note.put("id", id);
		note.put("title", "Java ����");
		note.put("lastModifyTime", System.currentTimeMillis());
		note.put("userId", "123");
		note.put("body", "�����������ý�,һ��ȫ����......");
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		int n = dao.updateNote(note);
		System.out.println(n);
		Note one = dao.findNoteById(id);
		System.out.println(one);
	}
	
	
	@Test
	public void testSaveNote(){
		String id = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		String title = "Thinking In Java����";
		String body = "��С������ҳ���,��Ҫ����";
		NoteService service = ctx.getBean("noteService",NoteService.class);
		boolean flag = service.saveNote(id, title, body);
		System.out.println(flag);
		Note note = service.getNote(id);
		System.out.println(note);
	}
	
	@Test
	public void testReg(){
		String str = "<p>1234567890</p><p>��˹�ٷҰ�˹�ٷ�as��˹�ٷ�</p>";
		String reg = "<p>[^<>]+<\\/p>";
		//reg = "<p>[^<>]<\\/p>"  ����������ʽ����
		Pattern p = Pattern.compile(reg);
		//����������ʽƥ���ַ��� m ����ƥ��Ľ��
		Matcher m = p.matcher(str);
		//m.find() ����ֵ��ʾ����Ҫƥ�������
		if(m.find()){
			//m.group() ��ȡ�ҵ����ַ���
			String s = m.group();
			System.out.println(s);
//			s=s.substring(3, s.length()-4);
			s=s.substring(3, s.length()>17?13:s.length()-4);//Ҳ������Ϊָ�����ȵĽ�ȡ
			System.out.println(s);
		}
	}
	
	
	@Test
	public void testAddNote(){
		String id = UUID.randomUUID().toString();
		String notebookId = "fa8d3d9d-2de5-4cfe-845f-951041bcc461";
		String userId = "123";
		String statusId = "1";
		String typeId = null;
		String title = "Thinking צ��";
		String body = "��С����ȱ�";
		Long createTime = System.currentTimeMillis();
		Long lastModifyTime = System.currentTimeMillis();
		Note note = new Note(id, notebookId, userId, statusId, typeId, 
				title, body, createTime, lastModifyTime);
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		int n = dao.addNote(note);
		System.out.println(n);
		Note note1 = dao.findNoteById(id);
		System.out.println(note1);
	}
	
	
	//����Aop���������ע��
	@Test
	public void testDeleteNotes(){
		String id1 = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		String id2 = "046b0110-67f9-48c3-bef3-b0b23bda9d4e";
		String id3 = "123asaddsds";
		String id4 = "051538a6-0f8e-472c-8765-251a795bc88f";
		NoteService service = ctx.getBean("noteService",NoteService.class);
		int n = service.deleteNotes(id1,id2,id3,id4);
		System.out.println(n);
	}
	
	//����Aop���񴫲�
	@Test
	public void testDeleteNotebooks(){
		String id1 = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		String id2 = "12312asdasd";
		String id3 = "009380f9-f663-4cdf-817e-5ef0257dc220";
		NotebookService service = ctx.getBean("notebookService",NotebookService.class);
		int n = service.deleteNotebooks(id1,id2,id3);
		System.out.println(n);
	}
	

	//����NoteDao�еĶ�̬������ѯ
	@Test
	public void testFindNotesByParam(){
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("title", "%aa%");
		List<Map<String,Object>> list = dao.findNotesByParam(param);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	
	//����NotebookDao�еĶ�̬����ɾ��
	@Test
	public void testDeleteNotes1(){
		String[] idList = {"bd0cd90c-0d0b-4cbe-8954-85568a5e26c3",
							"c73f883f-4e27-4dfc-a68a-fd44fd7789ee",
							"ebd65da6-3f90-45f9-b045-782928a5e2c0",
							"fed920a0-573c-46c8-ae4e-368397846efd"};
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		Map<String,Object> map = new HashMap<String,Object>();
		//�����key������idList
		map.put("idList", idList);
		int n = dao.deleteNotes(map);
		System.out.println(n);
	}
	
	//���Զ�̬�ķ�ҳ��ѯ
	@Test
	public void testFindNotesByLimit(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", 0);
		map.put("length", 10);
		NoteDao dao = ctx.getBean("noteDao",NoteDao.class);
		List<Map<String,Object>> list = dao.findNotesByLimit(map);
		for (Map<String, Object> map2 : list) {
			System.out.println(map2);
		}
		
	}
	
	@Test
	public void testFindNotebooksByParam(){
		String id = "ea09d9b1-ede7-4bd8-b43d-a546680df00b";
		String table = "cn_notebook";
		int start = 0;
		int rows = 4;
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", id);
		param.put("tableName", table);
		param.put("start", start);
		param.put("rows", 4);
		NotebookDao dao = ctx.getBean("notebookDao",NotebookDao.class);
		List<Map<String, Object>> list = dao.findNotebooksByParam(param);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
		/*
		 * {name=push, id=1755013b-05fc-4218-83cf-956287a81b49}
			{name=action, id=1ecf916f-61b8-409a-8173-1e548ac65d89}
			{name=�ʼ�1, id=b6887c05-7316-4088-8d67-c451f1474575}
			{name=�ʼ�2, id=cce17f11-6b90-4a17-87a2-74df0f5d4991}
			{name=favorites, id=db31c93f-957f-4f3c-ae67-cd443d5c0d06}
			{name=recycle, id=e46239d6-4f54-426c-a448-f7a0d45f9425}
		 */
		
	}
	
	@Test
	public void testListNoteBooks2(){
		String id = "ea09d9b1-ede7-4bd8-b43d-a546680df00b";
		NotebookService svc = ctx.getBean("notebookService",NotebookService.class);
		List<Map<String,Object>> list = svc.listNotebooks(id, 0);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	//����100��0-9֮������������ж��ٸ�
	@Test
	public void testNumber(){
		int[] ary = new int[100];
		ary[1]=6;
		ary[8]=4;
		ary[9]=3;
		ary[2]=4;
		int[] counter = new int[10];
		for(int i:ary){
			counter[i]++;
		}
		for(int n:counter){
			System.out.println(n);
		}
	}
	
	//����myBatis֧����������
	@Test
	public void testSavePerson(){
		Person p = new Person(null,"Andy","10");
		PersonDao dao = ctx.getBean("personDao",PersonDao.class);
		int n = dao.savePerson(p);
		System.out.println(n);
		System.out.println(p);
	}
	
	
}

