<%@ page import="com.jsu.Bean.Music" %>
<%@ page import="com.jsu.Dao.MusicDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jsu.Dao.Music_KindDao" %>
<%@ page import="com.jsu.Bean.MusicKind" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    MusicDao md = new MusicDao();
    Music_KindDao mkd = new Music_KindDao();
    List<MusicKind> mkl = mkd.getAllKind();
    List<Music> adminMusic = md.getAll();
    session.setAttribute("AMusic", adminMusic);
    session.setAttribute("MusicKind", mkl);
    String message =request.getParameter("message");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网上音乐-Admin</title>
    <link rel="stylesheet" href="/css/admin.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
<div class="content">
    <div class="iconfont addMusic" title="添加歌曲">&#xe652;</div>
    <div class="createMusic">
        <form action="MusicServlet" enctype="multipart/form-data" method="post">
            <div class="music_img">
                <img src="" style="width: 150px;height: 150px">
            </div>
            <input type="file" name="musicPhoto" id="musicPhoto" value="上传歌曲照片">
            <input type="text" name="musicName" id="musicName" placeholder="请输入歌曲名字"><span class="s1"></span>
            <input type="text" name="singerName" id="singerName" placeholder="请输入歌手名字"><span class="s2"></span>
            <div class="kindName">
                <select name="kindType" id="kindType">
                    <option value="AL">请选择类别</option>
                    <c:forEach items="${sessionScope.MusicKind}" var="mk">
                        <option value="${mk.kid}">${mk.name}</option>
                    </c:forEach>
                </select>
            </div>
            <span class="s3"></span>
            <input type="file" id="musicFile" name="musicFile" value="选择上传的歌曲"><span class="s4"></span>
            <input type="submit" class="sure">
            <input type="reset" class="no">
            <input type="hidden" value="add" name="type">
        </form>
    </div>
    <div class="createKind">
        <span>种类名称：</span><input type="text">
        <button class="sure">确定</button>
        <button class="no">取消</button>
    </div>
    <div class="list">
        <c:forEach items="${sessionScope.MusicKind}" var="Mk" varStatus="vs">
            <div id="s${vs.index}">${Mk.name}</div>
        </c:forEach>
        <p class="iconfont addKind" title="添加歌曲种类">&#xe652;</p>
    </div>
    <div class="title">
        <p class="NO">序号</p>
        <p class="music_name">歌名</p>
        <p class="singer_name">歌手</p>
        <p class="playNum">播放量</p>
        <p class="commentNum">评论量</p>
    </div>
    <div class="cm">
        <c:forEach items="${sessionScope.MusicKind}" var="Mk" varStatus="vs">
            <div class="c${vs.index}">
                <%
                    int i = 1;
                %>
                <c:forEach items="${sessionScope.AMusic}" var="AM" varStatus="vst">
                    <c:if test="${Mk.kid==AM.kid}">
                        <div class="music${AM.id}">
                            <p class="NO"><%=i%>.</p>
                            <p class="music_name">${AM.name}</p>
                            <p class="singer_name">${AM.singerName}</p>
                            <p class="playNum">${AM.playnum}</p>
                            <p class="commentNum">${AM.commnentNum}</p>
                        </div>
                        <%i++;%>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
</body>
<script type="text/javascript">
    $(".content .list div").click(function () {
        $(".content .list div").css("background-color", "white");
        $(this).css("background-color", "antiquewhite");
        var index = $(this).index();
        $(".content .cm>div").css("display", "none");
        $(".content .cm>div").eq(index).css("display", "block");
    })
    $(".addKind").click(function () {
        $(".content .createKind").show();
    })
    $(".addMusic").click(function () {
        $(".content .createMusic").show();
    })
    //添加种类
    $(".content .createKind .sure").click(function () {
        var str = $(".content .createKind input").val();
        if (str == '') {
            alert("请输入类型名字！");
            $(".content .createKind input").focus();
        } else {
            $.ajax({
                url: '/MusicKindServlet?type=add&musicKind=' + str,
                type: 'post',
                success: function (data) {
                    if ("false" == data) {
                        alert("类型已存在！");
                        $(".content .createKind input").focus();
                    } else {
                        $(".content .createKind").hide();
                        $(".content .createKind input").val("");
                        location.reload();
                    }
                }
            })
        }
    });

    $("#musicName").blur(function (){
        var str=$(this).val();
        if(str=='') {
            $(".content .createMusic .s1").text("不能为空");
            $(this).focus();
            setTimeout(function (){
                $(".content .createMusic .s1").text("")
            },1000);
        }
    })
    $("#singerName").blur(function (){
        var str=$(this).val();
        if(str=='') {
            $(".content .createMusic .s2").text("不能为空");
            $(this).focus();
            setTimeout(function (){
                $(".content .createMusic .s2").text("")
            },1000);
        }
    })
    $("#kindType").blur(function (){
        var str=$(this).val();
        if(str=='AL') {
            $(".content .createMusic .s3").text("不能为空");
            $(this).focus();
            setTimeout(function (){
                $(".content .createMusic .s3").text("")
            },1000);
        }
    })
    $("#musicFile").blur(function (){
        var str=$(this).val();
        if(str=='') {
            $(".content .createMusic .s4").text("不能为空");
            $(this).focus();
            setTimeout(function (){
                $(".content .createMusic .s4").text("")
            },1000);
        }
    })
    $(".content .createKind .no").click(function () {
        $(".content .createKind").hide();
        $(".content .createKind input").val("");
    })
</script>
</html>
