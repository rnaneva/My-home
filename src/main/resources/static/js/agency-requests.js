let requestsTBody = document.getElementById('tbody-requests')
let pathArray = window.location.pathname.split('/');
let id = pathArray[pathArray.length - 1]
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


function displayNewRequests() {

    fetch(`http://localhost:8080/api/requests/new/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            // todo link to request details
            // todo view button
            // todo link to offer
            let info = `<tr>\n
                    <td>${request.offerVisibleId}</td>\n
                    <td class="status-new">New</td>\n
                    <td class="td-name">\n
                    <a href="">${request.offerName}</a>\n
                    </td>\n
                    <td>${request.clientName}</td>\n
                    <td>${request.receivedOn}</td>\n
                    <td>\n
                    <button class="delete-btn">View</button>\n
                    </td>\n
                </tr> `


            requestsTBody.innerHTML += info
        }))
}

function displayInspectionRequests() {

    fetch(`http://localhost:8080/api/requests/inspection/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            // todo link to request details
            // todo view button
            // todo link to offer
            let info = `<tr>\n
                    <td>${request.offerVisibleId}</td>\n
                    <td class="status-inspection">Inspection</td>\n
                    <td class="td-name">\n
                    <a href="">${request.offerName}</a>\n
                    </td>\n
                    <td>${request.clientName}</td>\n
                    <td>${request.receivedOn}</td>\n
                    <td>\n
                    <button class="delete-btn">View</button>\n
                    </td>\n
                </tr> `


            requestsTBody.innerHTML += info
        }))
}

function displayRepliedRequests() {

    fetch(`http://localhost:8080/api/requests/replied/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            // todo link to request details
            // todo view button
            // todo link to offer
            let info = `<tr>\n
                   <td>${request.offerVisibleId}</td>\n
                    <td class="status-replied">Replied</td>\n
                    <td class="td-name">\n
                    <a href="">${request.offerName}</a>\n
                    </td>\n
                    <td>${request.clientName}</td>\n
                    <td>${request.receivedOn}</td>\n
                    <td>\n
                    <button class="delete-btn">View</button>\n
                    </td>\n
                </tr> `


            requestsTBody.innerHTML += info
        }))
}

function displayRejectedRequests() {

    fetch(`http://localhost:8080/api/requests/rejected/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(request => {

            // todo link to request details
            // todo view button
            // todo link to offer
            let info = `<tr>\n 
                    <td>${request.offerVisibleId}</td>\n
                    <td class="status-reject">Rejected</td>\n
                    <td class="td-name">\n
                    <a href="">${request.offerName}</a>\n
                    </td>\n
                    <td>${request.clientName}</td>\n
                    <td>${request.receivedOn}</td>\n
                    <td>\n
                    <button class="delete-btn">View</button>\n
                    </td>\n
                </tr> `


            requestsTBody.innerHTML += info
        }))
}



