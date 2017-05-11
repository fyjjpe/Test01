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
		//�мǶԴ����Ĳ��������пմ���
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("User ID ���ܿ�");
		}
		//�����û�id�����û�
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("�û�������");
		}
		return notebookDao.findNotebooksByUserId(userId);
	}

	@Transactional
	public Notebook addNotebook(String userId, String name) throws UserNotFoundException, NotebookNotFoundException {
		if(userId == null || userId.trim().isEmpty()){
			throw new UserNotFoundException("UserId ��Ϊ��");
		}
		if(name == null || name.trim().isEmpty()){
			throw new NotebookNotFoundException("�ʼǱ����ֲ���Ϊ��");
		}
		
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("User������");
		}
		String id = UUID.randomUUID().toString();
		String typeId = "1";
		String desc = null;
		Timestamp createtime = new Timestamp(System.currentTimeMillis());
		Notebook notebook = new Notebook(id, userId, typeId, name, desc, createtime);
		int n = notebookDao.addNotebook(notebook);
		if(n!=1){
			throw new RuntimeException("����ʧ��");
		}
		return notebook;
	}
	
	@Resource
	private NoteService noteService;
	
	@Transactional
	public int deleteNotebooks(String... ary) {
		int n = 0;
		for (String id : ary) {
			//1.���û�бʼǱ� ���׳��쳣
			Notebook notebook = notebookDao.findNotebookById(id);
			if(notebook==null){
				throw new NotebookNotFoundException("ID ������"+id);
			}
			//2.�ҵ��ʼǱ���ȫ���ʼǵ�id
			List<Map<String,Object>> list = noteService.listNotes(id);
			String[] idAry= new String[list.size()];
			
			int i = 0;
			for(Map<String,Object> map : list){
				//��list�е�Map��id����(����Map��key)����������
				idAry[i++] = (String)map.get("id");
			}
			
			//3.ɾ����Щ�ʼ�
			noteService.deleteNotes(idAry);//�˴��������񴫲���
			
			//4.ɾ������ʼǱ�
			n = notebookDao.deleteNoteBookById(id);
		}
		return n;
	}
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> listNotebooks(String userId, int pageNum) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("UserID ���ܿ�");
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("User������");
		}
		//�趨ÿ����ʾ����
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
