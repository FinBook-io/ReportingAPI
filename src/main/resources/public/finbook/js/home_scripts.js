const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000
});

function errorMessage() {
    Toast.fire({
        icon: 'error',
        title: 'Something was wrong!'
    });
}

function openWebSocket(randomText) {
    let textToSign = randomText;
    console.log("Text to sign: ", textToSign);
    let socket = new WebSocket("ws://localhost:8080/socket");

    socket.onopen = function (e) {
        console.log("Web socket connected!");
    };

    socket.onmessage = function (e) {
        if (JSON.parse(e.data).id === textToSign) {
            $.ajax({
                url: "/auth/sign-certificate",
                method: "POST",
                data: { firmaResponse : e.data },
                dataType: "JSON",
                success: function(data){
                    if (data.okay){
                        window.location.href = data.goInside;
                    }else{
                        errorMessage();
                    }
                },
                error: function () {
                    errorMessage();
                }
            });
        }else{
            errorMessage();
        }
    };

    socket.onclose = function (e) {
        console.log("Session web socket closed!");
    }
}

$(function () {
    // Open web socket if is sign page
    let ows = $('.if-finbsign');
    if (ows.length) {
        openWebSocket($("div.if-finbsign").data("randomtext"));
    }
});
