package cn.tedu.note.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.Notebook;
import cn.tedu.note.exception.UserNotFoundException;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/notebook")
public class NotebookController extends AbstractController {
	
	@Resource
	private NotebookService notebookService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String userId){
		List<Map<String,Object>> list = notebookService.listNotebooks(userId);
		return new JsonResult(list);
	}
		
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult add(String userId,String name){
		Notebook notebook = notebookService.addNotebook(userId, name);
		return new JsonResult(notebook);
	}
	
	//∑÷“≥œ‘ æ
	@RequestMapping("/list0.do")
	@ResponseBody
	public JsonResult list0(String userId,int pageNum){
		List<Map<String,Object>> list = notebookService.listNotebooks(userId, pageNum);
		return new JsonResult(list);
	}
	
}
