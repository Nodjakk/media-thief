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

    if (ss.length == 1) {
        ss = "0" + ss;
    }
    if (mm.length == 1) {
        mm = "0" + mm;
    }

    if (hours == 0) return mm + ":" + ss;

    if (hh.length == 1) {
        hh = "0" + hh;
    }
    
    return hh + ":" + mm + ":" + ss;

}

var element = document.getElementsByClassName("video-duration")[0];
var textContent = element.textContent;
element.textContent = timeFormat(Number(textContent));


