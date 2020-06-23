function successSendRawTrackInfo(data) {
    console.log("success SendRawTrackInfo", data)

    if ("artist" in data) {
        $("#artistText").val(data["artist"])
    }

    if ("title" in data) {
        $("#titleText").val(data["title"])
    }
}

function sendRawTrackInfo() {
    let rawStr = $("#rawTrackText").val()

    if (!rawStr) {
        return;
    }

    $.ajax({
        'type': 'POST',
        'url': "/consumeRawTrackInfo",
        'contentType': 'text/plain',
        'data': rawStr,
        'dataType': 'json',
        'success': successSendRawTrackInfo
    });
}

function successGetLastTracks(data) {
    $("#send-feedback").html(new Date().toLocaleString() + " @ " + JSON.stringify(data));
}

function successSendTrackInfo(data) {
    console.log("success send:", data);
    $.ajax({
        'type': 'POST',
        'url': "/lastTrackInfos",
        'contentType': 'application/json',
        'data': '1',
        'dataType': 'json',
        'success': successGetLastTracks
    });
}

function sendTrackInfo() {
    let artist = $("#artistText").val();
    let title = $("#titleText").val();

    let sources = "";

    let activeSources = document.getElementsByClassName("active-source-checkbox");

    for (let activeSource of activeSources) {
        if (activeSource.checked) {
            sources += "," + activeSource.value;
        }
    }

    sources = sources.slice(1);

    let jsonStr = JSON.stringify([{artist: artist, title: title, sources: sources}]);
    console.log(jsonStr);

    $.ajax({
        'type': 'POST',
        'url': "/consumeTrackInfos",
        'contentType': 'application/json',
        'data': jsonStr,
        'dataType': 'json',
        'success': successSendTrackInfo
    });
}