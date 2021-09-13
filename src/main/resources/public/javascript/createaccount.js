let fetchButton = document.getElementById("send");
let id = document.getElementById("clientId");
let address = document.getElementById("accountNr");

fetchButton.onclick = function () {
    let account = {
        number: address.value,
        id: id.value,
    };
    fetch("/createaccount",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(account)
        }
    ).then(result => result.text())
        .then(result => alert(result))
};