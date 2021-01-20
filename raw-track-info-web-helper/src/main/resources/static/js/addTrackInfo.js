function successParseTrackInfo(data) {
    console.log("success ParseTrackInfo", data)

    if ("artist" in data) {
        $("#artistNameText").val(data["artist"])
    }

    if ("title" in data) {
        $("#trackTitleText").val(data["title"])
    }
}

function tryParseTrackInfo() {
    let rawStr = $("#rawTrackText").val()

    if (!rawStr) {
        return;
    }

    $.ajax({
        'type': 'POST',
        'url': "/tryParseTrackInfo",
        'contentType': 'text/plain',
        'data': rawStr,
        'dataType': 'json',
        'success': successParseTrackInfo
    });
}

function successSendTrackInfo(data) {
    console.log("success send:", data);
    $("#send-feedback").html(new Date().toLocaleString() + " @ " + JSON.stringify(data));
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function sendTrackInfo() {
    let artistName = $("#artistNameText").val();
    let albumTitle = $("#albumTitleText").val();
    let trackTitle = $("#trackTitleText").val();
    let tags = $("#tagsText").val();

    let sources = "";

    let activeSources = document.getElementsByClassName("active-source-checkbox");

    for (let activeSource of activeSources) {
        if (activeSource.checked) {
            sources += "," + activeSource.value;
        }
    }

    sources = sources.slice(1);

    let message = {artist: artistName, album: albumTitle, track: trackTitle, sources: sources, tags: tags};

    let jsonStr = JSON.stringify(message, (key, value) => {
        if (/\S+/.test(value)) {
            return value;
        }
    });

    console.log(jsonStr);

    $.ajax({
        'type': 'POST',
        'url': "/saveRawTrackInfo",
        'contentType': 'application/json',
        'data': jsonStr,
        'dataType': 'json',
        'success': successSendTrackInfo
    });
}