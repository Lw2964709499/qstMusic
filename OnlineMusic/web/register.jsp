<%--
  Created by IntelliJ IDEA.
  User: 戴尔
  Date: 2020/12/27
  Time: 8:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>网上音乐-注册</title>
    <link rel="stylesheet" href="css/register.css">
    <script type="text/javascript">
        var xhr = false;
        function createXHR() {
            try {
                xhr = new XMLHttpRequest();
            } catch (e) {
                try {
                    xhr = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e1) {
                    xhr = false;
                }
            }
            if (!xhr)
                alert("初始化XMLHttpRequest对象失败！");
        }
        function ajaxValidate(emailObj){
            createXHR();
            var url = "UserRegisterServlet";
            var content = "type=emailAjaxValidate&email=" + emailObj.value;
            xhr.open("POST", url, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var str=xhr.responseText;
                    document.getElementById("emailValidate").innerHTML = str;
                }
            };
            <%--		xhr.setRequestHeader("Content-Length",content.length);--%>
            xhr.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
            xhr.send(content);
        }
	function validate() {
		var email = document.getElementById("email");
		var password = document.getElementById("password");
		var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if (email.value == "") {
			alert("邮箱不能为空！");
			email.focus();
			return false;
		} else if (!pattern.test(email.value)) {
			alert("请输入正确的邮箱格式！");
			email.focus();
			return false;
		}
		if (password.value == "") {
			alert("密码不能为空！");
			password.focus();
			return false;
		} else if (password.length<6 || password.length>12) {
			alert("密码长度不符合要求，请输入6-12位密码!");
			password.focus();
			return false;
		}
		return true;
	}

    </script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div class="bg">
    <div class="content">
        <form action="UserRegisterServlet" method="post" onsubmit="return validate()">
            <div class="register_">
                <div class="span1">
                    <label class="tn-form-label">邮箱：</label>
                    <input class="tn-textbox" type="text" name="email" id="email" onblur="ajaxValidate(this)">
                    <label style="color: #ff0000" id="emailValidate"></label>
                </div>
                <div class="span1">
                    <label class="tn-form-label">密码：</label>
                    <input class="tn-textbox" type="password" name="password" id="password">
                </div>
                <div class="span1">
                    <label class="tn-form-label">昵称：</label>
                    <input class="tn-textbox-long" type="text" name="username" id="username">
                </div>
                <div class="tn-form-row-button">
                    <div class="span1">
                        <input name="submit" type="submit" class="tn-button-text" value="立即注册">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
