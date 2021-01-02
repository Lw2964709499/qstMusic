<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="com.jsu.Bean.Music" %>
<%@ page import="com.jsu.Dao.MusicDao" %>
<%@ page import="com.jsu.Bean.MusicKind" %>
<%@ page import="com.jsu.Dao.Music_KindDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    MusicDao mdao = new MusicDao();
    List<Music> mL = null;
    List<Music> mk = null;
    Music_KindDao mkd = new Music_KindDao();
    List<MusicKind> musicKindList = mkd.getAllKind();
    session.setAttribute("KindList", musicKindList);
    mL = mdao.getSum("playnum");
    application.setAttribute("mLlist", mL);
    mk = mdao.getSum("commentnum");
    application.setAttribute("mKlist", mk);
%>
<html>
<head>
    <title>网上音乐</title>
    <link rel="stylesheet" href="/css/index.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<div id="box">
    <div class="front"></div>
    <div class="back"></div>
    <div class="top_u"></div>
    <div class="bottom"></div>
    <div class="left"></div>
    <div class="right"></div>
</div>
<div class="content1">
    <div class="Rank">
        <span class="span1">排行榜</span>
        <div class="list">
            <p class="playNum">播放量</p>
            <p class="commentNum">评论量</p>
        </div>
        <div class="play_c">
            <c:forEach items="${applicationScope.mLlist}" var="music" varStatus="vst">
                <div>
                    <p style="width:15px;margin-left: 0;">${vst.index+1}.</p>
                    <p class="music_name" style="width:100px;"><a style="color: black"
                                                                  href="music.jsp?id=${music.id}">${music.name}</a></p>
                    <p class="singer_name" style="width:100px;">${music.singerName}</p>
                    <p style="width:100px;">播放量：${music.playnum}</p>
                </div>
            </c:forEach>
        </div>
        <div class="comment_c">
            <c:forEach items="${applicationScope.mKlist}" var="music" varStatus="vst">
                <div>
                    <p style="margin-left: 0;width:15px;">${vst.index+1}.</p>
                    <p class="music_name" style="width:100px;"><a style="color: black"
                                                                  href="music.jsp?id=${music.id}">${music.name}</a></p>
                    <p class="singer_name" style="width:100px;">${music.singerName}</p>
                    <p style="width:100px;">评论次数：${music.commnentNum}</p>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="Kind">
        <span class="span1">歌曲分类</span>
        <div class="list">
            <c:forEach items="${KindList}" var="kl">
                <p>${kl.name}</p>
            </c:forEach>
        </div>
        <div class="kindContent">
            <c:forEach items="${sessionScope.KindList}" var="kl">
                <% int i = 1; %>
                <div>
                    <c:forEach items="${applicationScope.mLlist}" var="music" varStatus="vst">
                        <c:if test="${kl.kid==music.kid}">
                            <div>
                                <p style="width:15px;margin-left: 0;"><%=i%>.</p>
                                <p class="music_name" style="width:150px;"><a style="color: black" href="music.jsp?id=${music.id}">${music.name}</a>
                                </p>
                                <p class="singer_name" style="width:150px;">${music.singerName}</p>
                            </div>
                            <%i++;%>
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
<script type="text/javascript">
    window.onload = function() {
        $("#box div").click(function (){
          Index=$(this).index()+1;
          console.log(Index);
            location=("music.jsp?id="+Index);
        })
        var oBox = document.querySelector('#box');
        var y = -60;
        var x = 45;
        oBox.onmousedown = function(ev) {
            var oEvent = ev || event;
            var disX = oEvent.clientX - y;
            var disY = oEvent.clientY - x;
            document.onmousemove = function(ev) {
                var oEvent = ev || event;
                x = oEvent.clientY - disY;
                y = oEvent.clientX - disX;
                oBox.style.transform = 'perspective(800px) rotateX(' + x + 'deg) rotateY(' + y + 'deg)';
            };
            document.onmouseup = function() {
                document.onmousemove = null;
                document.onmouseup = null;
            };
            return false;
        };

    };
    $(".content1 .Rank .list .playNum").click(function () {
        $(".content1 .Rank .play_c").show();
        $(".content1 .Rank .comment_c").hide();
        $(this).css("background-color","floralwhite");
        mistyrose
        $(".content1 .Rank .list .commentNum").css("background-color", "mistyrose");
    });
    $(".content1 .Rank .list .commentNum").click(function () {
        $(".content1 .Rank .play_c").hide();
        $(".content1 .Rank .comment_c").show();
        $(this).css("background-color","floralwhite");
        $(".content1 .Rank .list .playNum").css("background-color", "mistyrose");
    });
    $(".content1 .Kind .list p").click(function () {
        $(".content1 .Kind .list p").css("background-color", "mistyrose");
        $(this).css("background-color", "floralwhite");
        var index = $(this).index();
        $(".content1 .Kind .kindContent>div").css("display", "none");
        $(".content1 .Kind .kindContent>div").eq(index).css("display", "block");
    });
</script>
</body>
</html>
