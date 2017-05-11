package cn.tedu.note.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.Note;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController {
	
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String notebookId){
		List<Map<String,Object>> list = noteService.listNotes(notebookId);
		return new JsonResult(list);
	}

	@RequestMapping("/get.do")	
	@ResponseBody
	public JsonResult get(String noteId){
		Note note = noteService.getNote(noteId);
		return new JsonResult(note);
	}
	
	@RequestMapping("/save.do")
	@ResponseBody
	public JsonResult save(String id,String title,String body){
		//有的企业会在此再检查传过来的参数，多次检查
		Boolean b = noteService.saveNote(id, title, body);
		return new JsonResult(b);
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult add(String notebookId, String userId, String title){
		Note note = noteService.addNote(notebookId, userId, title);
		return new JsonResult(note);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String noteId){
		List<Map<String,Object>> notes = noteService.deleteNote(noteId);
		return new JsonResult(notes);
	}
	
	
	
}
