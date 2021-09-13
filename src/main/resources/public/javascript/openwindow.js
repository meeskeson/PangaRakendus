let button = document.getElementById("sendB");
let userchoice = document.getElementById("choice");

button.onclick = function () {
    if (userchoice.value == "deposit") {
    window.open("deposit.html", '_blank',
        'location=yes,height=570,width=520,scrollbars=yes,status=yes');
    } else if (userchoice.value == "withdraw") {
        window.open("withdraw.html", '_blank',
            'location=yes,height=570,width=520,scrollbars=yes,status=yes');
    } else {
        window.open("transfer.html", '_blank',
            'location=yes,height=570,width=520,scrollbars=yes,status=yes');
    }
}