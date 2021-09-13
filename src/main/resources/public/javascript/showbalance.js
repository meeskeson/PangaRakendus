let accountNr = document.getElementById("accountNr");
let fetchButton = document.getElementById("sendB");
fetchButton.onclick = function () {
    fetch("/balance/" + accountNr.value)
        .then(result => result.text())
        .then(function (data) {
                console.log(data);
                let fetchResult = document.getElementById("result");
                fetchResult.innerText = data;
            }
        )
}