function getVideo() {
    $.ajax({
        type: "GET",
        url: "/",
        data: {
            requestType: "getVideo"
        },
        success: function( result ) {
            $(":root" ).css("--progress", String(100) + "%");
            window.location = '/download';
        }
    });
}

function getProgress() {
    $.ajax({
        type: "GET",
        url: "/",
        data: {
            requestType: "getVideoStatus"
        },
        success: function( result ) {
        if(result < 100) {
            $(":root" ).css("--progress", String(result) + "%");
            setTimeout(() => { getProgress() }, 300);
        }
        else if (result == 100) {
            getVideo()
        }
        }
    });
}

function startDownload() {
    $.ajax({
        type: "GET",
        url: "/",
        data: {
            requestType: "startDownload"
        },
        success: function( result ) {
        if(result == 1) {
            $(".progress-bars").removeClass("--disabled");
            $(":root" ).css("--progress", String(0) + "%")
            getProgress();
        }
        }
    });
}