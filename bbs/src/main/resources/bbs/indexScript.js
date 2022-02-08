$(document).ready(function () {
    var tags = [];

    // 기존테이블 데이터 지우고 행을 추가해야한다면 아래 empty() 추가
    $.ajax({
        url: 'http://192.168.0.2:8080/api/bbs',
        method: 'GET',
        success: function (data) {
            console.log("data::", data);
            $("#list").empty();
            if (data != null) {
                $.each(data, function (idx, el) {
                    tags.push("<tr class = 'rows'>");
                    tags.push('<td>' + el.id + '</td>');
                    tags.push('<td class = "title"><a>' + el.title + '</a></td>');
                    tags.push('<td>' + el.insterId + '</td>');
                    tags.push('<td>' + formatDate(el.instDtm) + '</td>');
                    tags.push('<td>' + el.readCnt + '</td>');
                    tags.push('</tr>');
                });
                $("#list").append(tags);
                $("#list").on("click", ".title", function (e) {
                    window.location.href = "/detail.html";
                });
            }
        }
    });
});

function formatDate(date) {

    const d = new Date(date)

    month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear(),
        hour = d.getHours(),
        minutes = d.getMinutes(),
        seconds = d.getSeconds();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-') + " " + [hour, minutes, seconds].join(":");

}