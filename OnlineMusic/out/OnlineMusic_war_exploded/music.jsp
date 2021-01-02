<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.jsu.Dao.MusicDao" %>
<%@ page import="com.jsu.Bean.Music" %>
<%@ page import="com.jsu.Bean.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jsu.Dao.CommentDao" %>
<%@ page import="com.jsu.Bean.SongList" %>
<%@ page import="com.jsu.Bean.User" %>
<%@ page import="com.jsu.Dao.SongListDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    CommentDao cdao = new CommentDao();
    String id = request.getParameter("id");
    if (id != null) {
        int musicId = Integer.parseInt(id);
        List<Comment> commentList = null;
        commentList = cdao.find(musicId);
        application.setAttribute("musicComment", commentList);
        Music music = new MusicDao().find(musicId);
        application.setAttribute("NowMusic", music);
    }
    User user = (User) session.getAttribute("SESSION_User");

    SongListDao sld = new SongListDao();
    List<SongList> sl=new ArrayList<SongList>();
    sl = (List<SongList>) session.getAttribute("songList");
    if (sl == null) {

        if(user!=null)
        sl = sld.findSongList(user.getId());
        else
            sl=new ArrayList<SongList>();
    }
    session.setAttribute("songList",sl);
%>
<html>
<head>
    <title>网上音乐-歌曲</title>
    <link rel="stylesheet" href="/css/music.css">
    <script src="/js/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<span id="message"></span>
<jsp:include page="top.jsp"></jsp:include>
<div class="content">
    <div class="up">
        <div class="musicPhoto">
            <img src="${applicationScope.NowMusic.photo}" alt="暂无图片">
        </div>
        <div class="music_name">歌曲名称：${applicationScope.NowMusic.name}</div>
        <div class="singer_name">歌手名称：${applicationScope.NowMusic.singerName}</div>
        <div class="kind_name">歌曲种类：${applicationScope.NowMusic.kindName}</div>
        <div class="Function">
            <p class="iconfont play" title="播放">&#xe626;</p>
            <p class="playNum">${applicationScope.NowMusic.playnum}</p>
            <p class="iconfont pause" title="暂停">&#xe61d;</p>
            <p class="iconfont cicon" title="评论量">&#xe695;</p>
            <p class="commentNum">${applicationScope.NowMusic.commnentNum}</p>
            <div class="Drop">
                <p class="iconfont collection" title="收藏">&#xe60f;</p>
                <ul class="droplist">
                    <c:forEach items="${sessionScope.songList}" var="var_sl">
                        <li id="${var_sl.id}">${var_sl.name}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="progress">
            <span class="start"></span>
            <div class="progress-bar">
                <div class="now"></div>
            </div>
            <span class="end"></span>
        </div>
    </div>
    <div class="Comment">
        <input class="description" type="text" name="cDescription" id="cDescription" placeholder="赶快发表自己的评论吧！！">
        <button class="commentButton">发表</button>
        <div class="commentContent">
            <c:forEach items="${applicationScope.musicComment}" var="mc" varStatus="vst">
                <div class="userComment">
                    <div class="userName">${mc.userName}:</div>
                    <div class="desc">${mc.description}</div>
                </div>
            </c:forEach>
        </div>
    </div>
    <audio id="music">
        <source src="${applicationScope.NowMusic.playaddress}" type="audio/mp3"/>
    </audio>
</div>
<script src="/js/music.js"></script>
<script type="text/javascript">
    $(".content .Comment .commentButton").click(function () {
        var str = $(".content .Comment .description").val();
        if (str == '') {
            alert("发表内容为空！");
            $(".content .Comment .description").focus();
        } else {
            $.ajax({
                url: '/CommentServlet?description=' + str + '&type=addComment',
                type: 'post',
                success: function (data) {
                    $(".content .Comment .description").val("");
                    $(".content .Function .commentNum").text(data);
                    var name = "${sessionScope.SESSION_User.name}";
                    $(".content .Comment .commentContent").append("<div class='userComment'><div class='userName'>" + name + ":" + "</div><div class='desc'>" + str + "</div></div>")
                    $(".content .Comment .description").focus();
                }
            })
        }
    });
    $(".content .up .Function .Drop .collection").click(function () {
        <%--if(${sessionScope.SESSION_User!=null}){--%>
        var size =${sessionScope.songList.size()};
        var mid =${applicationScope.NowMusic.id};
        if (size == 0) {
            $.ajax({
                url: '/UserInfoServlet?type=create&Mid=' + mid,
                type: 'post',
                success: function (data) {
                    console.log(data);
                    console.log(data=='true');
                    if (data == 'true')
                        $("#message").text("成功收藏到歌单");
                    else
                        $("#message").text("歌单已有此歌曲");
                    setTimeout(function (){
                        $("#message").text("");
                    },1000);
                }
            })
        }
        // }
    });
    $(".content .up .Function .Drop .droplist li").click(function () {
        var size =${sessionScope.songList.size()};
        var mid =${applicationScope.NowMusic.id};
        if (size > 0) {
            var slid = $(this).attr("id");
            $.ajax({
                url: '/UserInfoServlet?type=add&Slid=' + slid + '&Mid=' + mid,
                type: 'post',
                success: function (data) {
                    console.log(data);
                    console.log(data=='true');
                    if (data == "true")
                        $("#message").text("成功收藏到歌单");
                    else
                        $("#message").text("歌单已有此歌曲");
                    setTimeout(function (){
                        $("#message").text("");
                    },1000);
                }
            })
        }
    })
</script>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
