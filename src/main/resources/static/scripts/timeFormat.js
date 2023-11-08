function timeFormat(seconds) {
    minutes = 0;
    hours = 0;
    if (seconds >= 60 ){
        minutes = Math.floor(seconds/60);
        seconds = seconds - minutes*60;
    }
    if (minutes >= 60 ){
        hours = Math.floor(minutes/60);
        minutes = minutes - hours*60;
    }
    
    var ss = String(seconds);
    var mm = String(minutes);
    var hh = String(hours);

    if (ss.length == 1 && minutes > 0) {
        ss = "0" + ss;
    }
    if (mm.length == 1 && hours > 0) {
        mm = "0" + mm;
    }

    if (hours == 0) return mm + ":" + ss;
    
    return hh + ":" + mm + ":" + ss;

}

$(".video-duration").html(timeFormat(Number($(".video-duration").text())));


