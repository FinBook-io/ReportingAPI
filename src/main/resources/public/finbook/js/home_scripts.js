function openWebSocket(randomText) {
    let textToSign = randomText;
    console.log("Text to sign: ", textToSign);
    let socket = new WebSocket("ws://localhost:8080/socket");

    socket.onopen = function (e) {
        //console.log("Web socket connected!");
    };

    socket.onmessage = function (e) {
        console.log("ENTRA EN MESSAGE: ");
        if (JSON.parse(e.data).id === textToSign) {
            document.getElementById("signin_form").submit();
        }
    };

    socket.onclose = function (e) {
        // alert("session closed")
        console.log("Session web socket closed!");
    }
}

$(function () {
    /*
    *
    * OPEN WEB SOCKET
    *
    * */
    let ows = $('.if-finbsign');
    if (ows.length) {
        openWebSocket($("div.if-finbsign").data("randomtext"));
    }
});
