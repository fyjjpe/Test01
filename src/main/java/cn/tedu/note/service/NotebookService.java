package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Notebook;
import cn.tedu.note.exception.NotebookNotFoundException;
import cn.tedu.note.exception.UserNotFoundException;

public interface NotebookService {
	
	List<Map<String,Object>> listNotebooks(String userId) throws UserNotFoundException;
	
	Notebook addNotebook(String userId,String name) 
			throws UserNotFoundException,NotebookNotFoundException;
	
	int deleteNotebooks(String... id);
	
	List<Map<String,Object>> listNotebooks(String userId,int pageNum) throws UserNotFoundException;
}

