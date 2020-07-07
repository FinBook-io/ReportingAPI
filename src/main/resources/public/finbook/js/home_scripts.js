const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000
});

function openWebSocket(randomText) {
    let textToSign = randomText;
    console.log("Text to sign: ", textToSign);
    let socket = new WebSocket("ws://localhost:8080/socket");

    socket.onopen = function (e) {
        console.log("Web socket connected!");
    };

    socket.onmessage = function (e) {
        // console.log(e.data);
        if (JSON.parse(e.data).id === textToSign) {
            let sign = (JSON.parse(e.data).sign).toString();
            // document.getElementById("signin_form").submit();
            $.ajax({
                url: "/auth/sign-certificate",
                method: "POST",
                data: JSON.parse(e.data),
                dataType: "JSON",
                success: function(data){
                    window.location.href = data.redirect;
                },
                error: function () {
                    window.location.href = "/auth/sign-in";
                    Toast.fire({
                        icon: 'error',
                        title: 'Something was wrong!'
                    });
                }
            });

        }else{
            window.location.replace("https://www.google.com/");
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
