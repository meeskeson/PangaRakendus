let fetchButton = document.getElementById("fetch");
let accNr = document.getElementById("accNr");

fetchButton.onclick = function () {
    fetch(/unlock/ + accNr.value,
        {
            method: 'PUT',
        }
    ).then(result => result.text())
        .then(result => alert(result))
};