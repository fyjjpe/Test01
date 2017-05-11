// scripts/login.js
// 登录页面中的脚本文件
$(function() {
	// 在页面加载完毕执行的脚本
	// console.log("OK");
	$('#login').click(loginAction);
	//用户登录就聚焦在用户名输入框
	//ajax方法大都返回的当前对象本身
	$('#count').focus().blur(checkUsername);
	$('#password').blur(checkPassword);
	
	//注册界面表单数据自动检验-----自己完成
	$('#regist_username').blur(checkRegist_username);
	$('#regist_password').blur(checkRegist_password);
	$('#final_password').blur(checkFinal_password);
	//绑定注册界面的事件
	$('#regist_button').click(registAction);
});

//验证注册账号
function checkRegist_username(){
	var name = $('#regist_username').val();
//	console.log(name);
	if(!name){
		$('#warning_1 span').empty().html('不能为空');
		$('#warning_1').show();
		return false;
	}
	var reg = /^\w{3,10}$/;
	if(reg.test(name)){
		$('#warning_1 span').empty();
		return true;
	}else{
		$('#warning_1 span').empty().html('必须3~10个字符');
		$('#warning_1').show();
		return false;
	}
}
//验证密码
function checkRegist_password(){
	var password = $('#regist_password').val();
	if(!password){
		$('#warning_2 span').empty().html('不能为空');
		$('#warning_2').show();
		return false;
	}
	var reg = /^\w{3,10}$/;
	if(reg.test(password)){
		$('#warning_2 span').empty();
		return true;
	}else{
		$('#warning_2 span').empty().html('必须3~10个字符');
		$('#warning_2').show();
		return false;
	}
}
//确认密码
function checkFinal_password(){
	var confirm = $('#final_password').val();
	console.log(confirm);
	if(!confirm){
		$('#warning_3 span').empty().html('不能为空');
		$('#warning_3').show();
		return false;
	}
	var password = $('#regist_password').val();
	console.log(password);
	if(confirm==password){
		console.log('验证密码对了');
		$('#warning_3 span').empty();
		return true;
	}else{
		$('#warning_3 span').empty().html('确认密码不一致');
		$('#warning_3').show();
		return false;
	}
}


function registAction(){
	console.log('registAction');
	
	//当注册用户名、密码和确认密码都正确后，才继续执行，否则直接返回
	if(checkRegist_username()+checkRegist_password()+checkFinal_password()!=3){
		console.log('registAction return');
		return;
	}
	
	var url = 'user/regist.do';
	var param = {
			name:$('#regist_username').val(),
			nick:$('#nickname').val(),
			password:$('#regist_password').val(),
			confirm:$('#final_password').val()
	};
//	console.log(param);
	$.post(url,param,function(result){
//		console.log(result.data);
		if(result.state==0){
			//注册成功
			console.log('注册成功!');
			//注册成功后将用户名保存至登录界面，焦点转移到密码输入框
			var user = result.data;
			$('#back').click();
			$('#count').val(user.name);
			$('#count-msg').empty();
			$('#password').focus();
		} else if(result.state==2) {
			$('#warning_1 span').html(result.message);
			$('#warning_1').show();
		} else if (result.state==3){
			$('#warning_2 span').html(result.message);
			$('#warning_2').show();
		} else {
			alert(result.message);
		}
	});
}


function checkUsername(){
//	console.log('checkUsername');	
	var name = $('#count').val();
	if(!name){
		$('#count-msg').empty().html('不能为空');//先清空，再写
		return false;
	}
	var reg = /^\w{3,10}$/;//用户名要求3-10为字符
	if(reg.test(name)){
		$('#count-msg').empty();
		return true;
	}else{
		$('#count-msg').empty().html('必须3~10个字符');
		return false;
	}
}

function checkPassword(){
//	console.log('checkPassword');
	var pwd = $('#password').val();
	if(!pwd){
		$('#password-msg').empty().html('不能为空');
		return false;
	}
	var reg = /^\w{3,10}$/;//密码要求3-10为字符
	if(reg.test(pwd)){
		$('#password-msg').empty();
		return true;
	}else{
		$('#password-msg').empty().html('必须3~10个字符');
		return false;
	}
}

function loginAction() {
	//点击登录时进行判定
	if(checkPassword()+checkUsername()!=2){
		return;
	}	
	
	// console.log('Login click');
	// 获取表单中的参数
	// 将表单参数利用AJAX发送到控制器
	// 检查控制器返回值,如果state==0,就成功转edit.html界面;
	// 如果state==1,不成功则提示错误信息
	var username = $('#count').val();
	var password = $('#password').val();
	// console.log(username+'---'+password);

	var url = 'user/login.do';
	var param = {
		name : username,
		password : password
	};
	$.post(url, param, function(result) {
		if(result.state==0){
			console.log('登录成功');
			console.log(result.data);
			//---------------------------------
			//第三天:登录成功以后保存userid到cookie
			var user = result.data;
			setCookie('userId',user.id);
			//---------------------------------
			//跳转到edit.html界面
			location.href ='edit.html';
		}else if(result.state==2){
			//用户名错误
			$('#count-msg').html(result.message);
		}else if(result.state==3){
			//密码错误
			$('#password-msg').html(result.message);
		}else{
			//其他错误
			alert(result.message);
		}
	});	
}


