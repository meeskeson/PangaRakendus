let fetchButton = document.getElementById("fetch");
let address = document.getElementById("address");
let clientFirstName = document.getElementById("clientFirstName");
let clientLastName = document.getElementById("clientLastName")

fetchButton.onclick = function createClient() {
    let client = {
        firstname: clientFirstName.value,
        lastname: clientLastName.value,
        address: address.value
    };
    fetch("/createclient",
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(client)
        }
    ).then(result => result.text())
        .then(result => alert(result))
};