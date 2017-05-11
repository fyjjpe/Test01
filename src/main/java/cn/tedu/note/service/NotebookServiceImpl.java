package cn.tedu.note.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Notebook;
import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NotebookNotFoundException;
import cn.tedu.note.exception.UserNotFoundException;

@Service("notebookService")
public class NotebookServiceImpl implements NotebookService {
	@Resource
	private NotebookDao notebookDao;
	@Resource
	private UserDao userDao;
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException {
		//切记对传来的参数进行判空处理
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("User ID 不能空");
		}
		//根据用户id查找用户
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		return notebookDao.findNotebooksByUserId(userId);
	}

	@Transactional
	public Notebook addNotebook(String userId, String name) throws UserNotFoundException, NotebookNotFoundException {
		if(userId == null || userId.trim().isEmpty()){
			throw new UserNotFoundException("UserId 不为空");
		}
		if(name == null || name.trim().isEmpty()){
			throw new NotebookNotFoundException("笔记本名字不能为空");
		}
		
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("User不存在");
		}
		String id = UUID.randomUUID().toString();
		String typeId = "1";
		String desc = null;
		Timestamp createtime = new Timestamp(System.currentTimeMillis());
		Notebook notebook = new Notebook(id, userId, typeId, name, desc, createtime);
		int n = notebookDao.addNotebook(notebook);
		if(n!=1){
			throw new RuntimeException("保存失败");
		}
		return notebook;
	}
	
	@Resource
	private NoteService noteService;
	
	@Transactional
	public int deleteNotebooks(String... ary) {
		int n = 0;
		for (String id : ary) {
			//1.如果没有笔记本 则抛出异常
			Notebook notebook = notebookDao.findNotebookById(id);
			if(notebook==null){
				throw new NotebookNotFoundException("ID 不存在"+id);
			}
			//2.找到笔记本中全部笔记的id
			List<Map<String,Object>> list = noteService.listNotes(id);
			String[] idAry= new String[list.size()];
			
			int i = 0;
			for(Map<String,Object> map : list){
				//将list中的Map的id属性(就是Map的key)放入数组中
				idAry[i++] = (String)map.get("id");
			}
			
			//3.删除这些笔记
			noteService.deleteNotes(idAry);//此处发生事务传播了
			
			//4.删除这个笔记本
			n = notebookDao.deleteNoteBookById(id);
		}
		return n;
	}
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> listNotebooks(String userId, int pageNum) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("UserID 不能空");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("User不存在");
		}
		//设定每次显示行数
		int size = 6;
		int start = pageNum*size;
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("tableName", "cn_notebook");
		param.put("start", start);
		param.put("rows", size);
		return notebookDao.findNotebooksByParam(param);
	}
	

}
