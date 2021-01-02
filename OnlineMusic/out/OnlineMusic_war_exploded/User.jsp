<%@ page import="com.jsu.Dao.SongListDao" %>
<%@ page import="com.jsu.Dao.SongList_MusicDao" %>
<%@ page import="com.jsu.Bean.User" %>
<%@ page import="com.mysql.cj.Session" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.jsu.Bean.SongList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jsu.Bean.Music" %>
<%@ page import="com.jsu.Bean.User_Music" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    SongListDao sld = new SongListDao();
    SongList_MusicDao slmd = new SongList_MusicDao();
    List<Music> user_music;
    List<SongList> sl ;
    User user = (User) session.getAttribute("SESSION_User");
    sl = sld.findSongList(user.getId());
    session.setAttribute("songList", sl);
    User_Music UM = null;
    List<User_Music> User_Ml = new ArrayList<User_Music>();
    List<Music> music = (List<Music>) application.getAttribute("mLlist");
    for (int i = 0; i < sl.size(); i++) {
        user_music = slmd.find(sl.get(i).getId());
        for (int j = 0; j < user_music.size(); j++) {
            UM = new User_Music(user_music.get(j), sl.get(i).getId());
            User_Ml.add(UM);
        }
    }
    session.setAttribute("User_Ml", User_Ml);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网上音乐-个人资料</title>
    <link rel="stylesheet" href="/css/User.css">
    <script src="/js/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div class="content">
    <div class="createSl">
        <span>歌单名：</span><input type="text">
        <button class="sure">确定</button>
        <button class="no">取消</button>
    </div>
    <div class="center">
        <div class="content_L">
            <div class="userInfo">个人信息</div>
            <div class="songList">歌单</div>
            <div class="modify">编辑资料</div>
        </div>
        <div class="iconfont create" title="创建歌单">&#xe652;</div>
        <div class="content_userInfo content_R">
            <div class="head_img">
                <img src="${sessionScope.SESSION_User.photo}" alt="还未上传" width="150px">
            </div>
            <div class="name">昵称：<span>${sessionScope.SESSION_User.name}</span></div>
            <div class="email">邮箱：<span>${sessionScope.SESSION_User.email}</span></div>
        </div>
        <div class="content_list content_R">
            <div class="slNmae">
                <c:forEach items="${sessionScope.songList}" var="SL" varStatus="vst">
                    <div id="${vst.index}">${SL.name}</div>
                </c:forEach>
            </div>
            <div class="title">
                <p>歌名</p>
                <p>歌手</p>
            </div>
            <div class="ln">
                <c:forEach items="${sessionScope.songList}" var="slm" varStatus="vst">
                    <div class="sl_music${vst.index} SLmusic">
                        <c:forEach items="${sessionScope.User_Ml}" var="uml">
                            <c:if test="${slm.id==uml.slId}">
                                <div>
                                    <p><a href="music.jsp?id=${uml.music.id}">${uml.music.name}</a></p>
                                    <p>${uml.music.singerName}</p>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="content_modify content_R">
            <form action="UserInfoServlet" enctype="multipart/form-data" method="post" onsubmit="return validate();">
                <div class="up_img">
                    <img src="${sessionScope.SESSION_User.photo}" alt="未上传" width="150px">
                </div>
                <input name="headShot" id="headShot" type="file" value="选择文件">
                <div class="M_name">
                    <span>昵称：</span>
                    <input type="text" name="U_name" id="U_name">
                </div>
                <input type="submit" value="保存" name="save" class="save">
                <input type="reset" value="取消" name="reset" class="cancel">
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">

    function validate() {
        var headShot = document.getElementById("headShot");
        if (headShot.value == "") {
            alert("请选择要上传的头像！");
            headShot.focus();
            return false;
        }
        return true;
    }

    $().ready(function () {
        $(".content .center .content_L .userInfo").click(function () {
            $(this).css("background-color", "white");
            $(".content .center .content_L .songList").css("background-color", "#f4f4f4");
            $(".content .center .content_L .modify").css("background-color", "#f4f4f4");
            $(".content .center .content_userInfo.content_R").show();
            $(".content .center .content_list.content_R").hide();
            $(".content .center .content_modify.content_R").hide();
        });
        $(".content .center .content_L .songList").click(function () {
            $(this).css("background-color", "white");
            $(".content .center .content_L .userInfo").css("background-color", "#f4f4f4");
            $(".content .center .content_L .modify").css("background-color", "#f4f4f4");
            $(".content .center .content_list.content_R").show();
            $(".content .center .content_userInfo.content_R").hide();
            $(".content .center .content_modify.content_R").hide();
        })
        $(".content .center .content_L .modify").click(function () {
            $(this).css("background-color", "white");
            $(".content .center .content_L .songList").css("background-color", "#f4f4f4");
            $(".content .center .content_L .userInfo").css("background-color", "#f4f4f4");
            $(".content .center .content_modify.content_R").show();
            $(".content .center .content_list.content_R").hide();
            $(".content .center .content_userInfo.content_R").hide();
        })
        $(".content .center .content_list .slNmae div").click(function () {
            var id = $(this).attr("id");
            console.log(id, 1)
            $(".content .center .content_list .slNmae div").not("#" + id).css("background-color", "#f4f4f4");
            $(this).css("background-color", "#b3d4fc");
            $(".content .center .content_list .ln>div").hide();
            $(".content .center .content_list .ln>div").eq(id).show();
        });

        $(".content .center .create").click(function (){
            $(".content .createSl").show();
        })
        $(".content .createSl .sure").click(function (){
            var childrenNum=$(".content .center .content_list .slNmae").children().length;
            var str=$(".content .createSl input").val();
            if(str==''){
                alert("请输入歌单名字！");
                $(".content .createSl input").focus();
            }else {
                $.ajax({
                    url:'/UserInfoServlet?type=create&slname='+str,
                    type:'post',
                    success:function (data){
                        if("false"==data){
                            alert("歌单名已存在！");
                            $(".content .createSl input").focus();
                        }else {
                            $(".content .createSl").hide();
                            $(".content .createSl input").val("");
                            $(".content .center .content_list .slNmae").append("<div id='" + childrenNum + "'>" + str + "</div>");
                        }
                    }
                })
            }
        });
        $(".content .createSl .no").click(function (){
            $(".content .createSl").hide();
            $(".content .createSl input").val("");
        })
    })
</script>
</body>
</html>
