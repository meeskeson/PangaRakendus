let button = document.getElementById("submit");
let amount = document.getElementById("sum");
let addressTo = document.getElementById("accountTo");
let addressFrom = document.getElementById("accountFrom")

button.onclick = function () {
    fetch(/transfer/ + addressFrom.value + "/" + addressTo.value + "/" + amount.value,
        {
            method:'PUT',
        }
    ).then(result => result.text())
        .then(result => {
            alert(result);
            window.close();
        })
}