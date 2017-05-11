/*
 * notebooks.js 封装与笔记本 有关的脚本
 */

function loadNotebooksAction(){
//	console.log('loadNotebooksAction');
	//发起Ajax请求 获取笔记本列表数据
	//实现分页显示(最后一版)
	var url = 'notebook/list0.do';//请求分页查询的URL
	if(!model.notebooks){
		//第一次登陆时
		model.pageNum=0;
	}else{
		model.pageNum++;//计算页号
	}
	var data = {userId: getCookie('userId'),
				 pageNum:model.pageNum};
//	console.log(data);
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks=result.data;
//			console.log(notebooks);
			//调用模型更新笔记本列表功能
			model.updataNotebooksView(notebooks);
		}
	});
}

//js对象可以动态增加方法，也可以动态增加属性
//下面方法中就是
model.updataNotebooksView = function(notebooks){
//	console.log('updataNotebooksView');
	
	//this 就是当前对象model
	//为model增加新属性notebooks
	if(!this.notebooks){//没有笔记本列表
		this.notebooks = notebooks;
	}else{
		//有笔记本列表，就追加
		this.notebooks = this.notebooks.concat(notebooks);
	}
//	console.log(model);
	
	//遍历每个notebook对象，显示到笔记本区域
	var ul = $('#notebooks').empty();
	var template='<li class="online notebook">'
				+'<a><i class="fa fa-book" title="online" rel="tooltip-bottom"></i>'
				+'#{name}'
				+'</a>'
				+'</li>';
	for(var i=0;i<this.notebooks.length;i++){
		var notebook = this.notebooks[i];
//		console.log(notebook);
		var li = $(template.replace('#{name}', notebook.name));
		
		//第5天将第4天的li变成jQuery对象
		li.data('index',i);//jQuery对象的方法,用于绑定序号
		ul.append(li);
	}
	//分页显示，增加more的点击节点，在查询结束后，加上more标签
	var li = '<li class="online more"><a>More...</a></li>';
	ul.append(li);
};

//增加笔记本按钮的事件
function addNotebookAction(){
//	console.log('addNotebookAction');
	
	var url = 'notebook/add.do';
	var param = {
		userId:getCookie('userId'),
		name:$('#input_notebook').val()
	};
//	console.log(param);
	//增加客户动态体验效果
	var btn = $(this).attr('disabled','disabled').html('创建中...');
	$.post(url,param,function(result){
//		console.log(result);
		if(result.state==0){
			//故意设一个延迟
			setTimeout(function(){
				btn.removeAttr('disabled').html('创建');
				//将增加笔记本窗口关闭
				closeAction();
				//更新笔记本列表
				loadNotebooksAction();
			}, 1000);
		}else{
			alert(result.message);
		}
	});
	
	

	
}















