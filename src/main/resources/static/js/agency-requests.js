let requestsTBody = document.getElementById('tbody-requests')
let pathArray = window.location.pathname.split('/');
let userId = pathArray[pathArray.length - 1]
requestsTBody.innerHTML = ""

let newReq = window.location.pathname.includes("new")
let repliedReq = window.location.pathname.includes("replied")
let rejectReq = window.location.pathname.includes("rejected")
let inspectionReq = window.location.pathname.includes("inspection")

if (newReq) {
    window.addEventListener('load', displayNewRequests)
} else if (inspectionReq) {
    window.addEventListener('load', displayInspectionRequests)
} else if (repliedReq) {
    window.addEventListener('load', displayRepliedRequests)
} else if (rejectReq) {
    window.addEventListener('load', displayRejectedRequests)

}

function viewOffer(event){
    let requestId = event.target.dataset.id
    window.location.replace("http://localhost:8080/agency/" + userId + "/requests/" + requestId)

}

function requestInfo(request, class1, var2) {

    let tr = document.createElement('tr')

    let td1 = document.createElement('td')
    td1.textContent = request.id

    let td2 = document.createElement('td')
    td2.classList.add(class1)
    td2.textContent = var2

    let td3 = document.createElement('td')
    td3.classList.add("td-name")
    let a = document.createElement('a')
    a.href = ""
    a.textContent = request.offerName
    td3.appendChild(a)

    let td4 = document.createElement('td')
    td4.textContent = request.clientName

    let td5 = document.createElement('td')
    td5.textContent = request.receivedOn

    let td6 = document.createElement('td')
    let viewBtn = document.createElement('button')
    viewBtn.classList.add("delete-btn")
    viewBtn.textContent = 'View'
    viewBtn.dataset.id = request.id
    viewBtn.addEventListener('click', viewOffer)
    td6.appendChild(viewBtn)

    tr.appendChild(td1)
    tr.appendChild(td2)
    tr.appendChild(td3)
    tr.appendChild(td4)
    tr.appendChild(td5)
    tr.appendChild(td6)

    requestsTBody.appendChild(tr)

}

// todo link to offer hateos


function displayNewRequests() {

    fetch(`http://localhost:8080/api/requests/new/${userId}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            requestInfo(request, "status-new", "New")
        }))
}

function displayInspectionRequests() {

    fetch(`http://localhost:8080/api/requests/inspection/${userId}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            requestInfo(request, "status-inspection", "Inspection")
        }))
}

function displayRepliedRequests() {

    fetch(`http://localhost:8080/api/requests/replied/${userId}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            requestInfo(request, "status-replied", "Replied")
        }))
}

function displayRejectedRequests() {

    fetch(`http://localhost:8080/api/requests/rejected/${userId}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            requestInfo(request, "status-reject", "Rejected")
        }))
}



