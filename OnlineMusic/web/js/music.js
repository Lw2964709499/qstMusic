const audio = document.getElementById('music')
console.log(audio);
const start = document.querySelector('.start')
const end = document.querySelector('.end')
const progressBar = document.querySelector('.progress-bar')
const now = document.querySelector('.now')
var index = 0;
$().ready(function () {
    $(".play").click(function () {
        time = setInterval(function () {

            if (audio.currentTime >= audio.duration || audio.ended) {
                clearInterval(time);
                index = 0;
            }
        });
        if (index == 0) {
            $.ajax({
                url: '/MusicServlet?type=playAjaxValidate',
                type: 'post',
                success: function (data) {
                    $(".content .up .Function .playNum").text(data);
                }
            });
            index++;
        }
        audio.play();
    });
    $(".pause").click(function () {
        audio.pause();
    });

});

function conversion(value) {
    let minute = Math.floor(value / 60)
    minute = minute.toString().length === 1 ? ('0' + minute) : minute
    let second = Math.round(value % 60)
    second = second.toString().length === 1 ? ('0' + second) : second
    return `${minute}:${second}`
}

//onloadedmetadata	script	��ý��Ԫ�صĳ���ʱ���Լ�����ý�������Ѽ���ʱ���нű�
audio.onloadedmetadata = function () {
    //duration	���ص�ǰ��Ƶ�ĳ��ȣ�����ƣ�
    end.innerHTML = conversion(audio.duration)
    //currentTime	���û򷵻���Ƶ�еĵ�ǰ����λ�ã�����ƣ�
    start.innerHTML = conversion(audio.currentTime)
}

progressBar.addEventListener('click', function (event) {
    let coordStart = this.getBoundingClientRect().left
    let coordEnd = event.pageX
    let p = (coordEnd - coordStart) / this.offsetWidth
    now.style.width = p.toFixed(3) * 100 + '%'

    audio.currentTime = p * audio.duration
    audio.play()
})

setInterval(() => {
    start.innerHTML = conversion(audio.currentTime)
    now.style.width = audio.currentTime / audio.duration.toFixed(3) * 100 + '%'
}, 1000)