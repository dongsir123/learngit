<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>欢迎注册EasyMall</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${ app }/css/regist.css"/>
		<script type="text/javascript" src="${ app }/js/jquery-1.4.2.js"></script>
		 	
		<script type="text/javascript">
			var formObj = {
				"checkNull" : function(name,msg){
					var value = $("input[name='"+name+"']").val().trim();
					//清空之前的提示信息
					formObj.setMsg(name,"");
					if(value == ""){
						formObj.setMsg(name,msg);
						return false;
					}
					return true;
				},
				/* 设置错误提示消息*/
				"setMsg" : function(name,msg){
					$("#"+name+"_msg").html(msg);
					$("#"+name+"_msg").css("color","red");
				},
				/* 注册表单js校验*/
				"checkForm" : function(){
					//1.非空校验
					var res1 = formObj.checkNull("username","用户名不能为空");
					var res2 = formObj.checkNull("password","密码不能为空");
					var res3 = formObj.checkNull("password2","确认密码不能为空");
					var res4 = formObj.checkNull("nickname","昵称不能为空");
					var res5 = formObj.checkNull("email","邮箱不能为空");
					var res6 = formObj.checkNull("valistr","验证码不能为空");
					
					//2.两次密码是否一致
					var res7 = formObj.checkPassword("password","两次密码不一致");
					
					//3.邮箱格式是否正确
					var res8 = formObj.checkEmail("email","邮箱格式不正确");
					
					return res1 && res2 && res3 && res4 && res5 && res6 &&res7 &&res8;
					
				},
				"checkEmail" : function(name,msg) {
					var email = $("input[name='"+name+"']").val().trim();
			 		if(email == ""){
						formObj.setMsg("email","邮箱不能为空");
					} 
					if(email != ""){
						//email = "admin123@163.com"
						var reg = /^\w+@\w+(\.\w+)+$/;
						if(!reg.test(email)){
							formObj.setMsg(name,msg);
							return false;
						}
					}
					return true;
				},
				/*检查两次密码是否一致*/
				"checkPassword" : function(name,msg){
					var psw1 = $("input[name='"+name+"']").val().trim();
					var psw2 = $("input[name='"+name+"2']").val().trim();
					
					if(psw2 == ""){
						formObj.setMsg(name+"2","确认密码不能为空");
					}
					if(psw1!="" && psw2!=""){
						if(psw1 != psw2){
							formObj.setMsg(name+"2",msg);
							return false;
						}
					}
					return true;
				}
			}
			/* 利用ajax实现用户名是否存在的校验*/
			function ajaxCheckUsername(thisobj){
				//非空校验
				if(!formObj.checkNull("username","用户名不能为空")){
					return ;
				}
				//获取用户名
				var username = thisobj.value;
				//使用ajax检查用户名是否存在
				$("#username_msg").load("${ app }/servlet/AjaxCheckUsernameServlet",{"username":username});
			}
		</script>
	</head>
	<body>
		<form onsubmit="return formObj.checkForm()" action="${ app }/servlet/RegistServlet" method="GET">
			<h1>欢迎注册EasyMall</h1>
			<table>
				<tr>
					<td colspan="2" style="color:red;text-align:center">
					${ requestScope.msg }
					</td>
				</tr>
				<tr>
				<%-- <caption style="color:red"><%= request.getAttribute("msg")==null ? "":request.getAttribute("msg")%></caption> --%>
					<td class="tds">用户名：</td>
					<td>
						<input type="text" name="username" 
						onblur="ajaxCheckUsername(this)"
						onfocus="formObj.setMsg('username','')"
						value="${ param.username }" />
						<span id = "username_msg"></span>
						
						
					</td>
				</tr>
				<tr>
					<td class="tds">密码：</td>
					<td>
						<input type="password" name="password"
						onblur="formObj.checkNull('password','密码不能为空')"
						onfocus="formObj.setMsg('password','')"
						value="${ param.password }" />
						<span id = "password_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">确认密码：</td>
					<td>
						<input type="password" name="password2"
						onblur="formObj.checkPassword('password','两次密码不一致')"
						onfocus="formObj.setMsg('password2','')"
						value="${ param.password2 }" />
						<span id = "password2_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">昵称：</td>
					<td>
						<input type="text" name="nickname"
						onblur="formObj.checkNull('nickname','昵称不能为空')"
						onfocus="formObj.setMsg('nickname','')"
						value="${ param.nickname }" />
						<span id = "nickname_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">邮箱：</td>
					<td>
						<input type="text" name="email"
						onblur="formObj.checkEmail('email','邮箱格式不正确')"
						onfocus="formObj.setMsg('email','')"
						value="${ param.email }" />
						<span id = "email_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">验证码：</td>
					<td>
						<input type="text" name="valistr"
						onblur="formObj.checkNull('valistr','验证码不能为空')"
						onfocus="formObj.setMsg('valistr','')"
						value="${ param.valistr }" />
						<img onclick="changeImage(this)" src="${ app }/servlet/ValiImageServlet" width="" height="" alt="" />
						<span id = "valistr_msg"></span>
					</td>
					<script>
						function changeImage(thisobj){
							thisobj.src = "${ app }/servlet/ValiImageServlet?time="+new Date().getTime();
						}
					</script>
				</tr>
				<tr>
					<td class="td_sub" colspan="2" class="tds">
						<input type="submit" value="注册用户"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
