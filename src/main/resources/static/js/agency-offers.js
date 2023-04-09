let offersTBody = document.getElementById('tbody-offers')
let pathArray = window.location.pathname.split('/');
let userId = pathArray[pathArray.length - 1]


let inactive = window.location.pathname.includes("inactive")

if (inactive) {
    window.addEventListener('load', displayInactiveOffers)

} else {
    window.addEventListener('load', displayActiveOffers)
}


let edit = function editBtnClicked(event) {

    let offerId = event.target.dataset.id
    window.location.replace("http://localhost:8080/agency/offers/edit/one/" + offerId)
}


let activate = function activateBtnClicked(event) {
    let offerId = event.target.dataset.id

    fetch(`http://localhost:8080/api/offers/inactive/${offerId}/activate`)
        .then(_ => displayActiveOffers())

}

let deactivate = function deactivateBtnClicked(event) {
    let offerId = event.target.dataset.id

    fetch(`http://localhost:8080/api/offers/active/${offerId}/deactivate`)
        .then(_ => displayInactiveOffers())
}



function displayActiveOffers() {
    offersTBody.innerHTML = ""

    fetch(`http://localhost:8080/api/offers/active/${userId}`)
        .then(result => result.json())
        .then(json => json.forEach(offer => {

            fillInfo(offer, "active", "deactivate-btn", "Deactivate", deactivate)


        }))
}

function displayInactiveOffers() {
    offersTBody.innerHTML = ""


    fetch(`http://localhost:8080/api/offers/inactive/${userId}`)
        .then(result => result.json())
        .then(json => json.forEach(offer => {

            fillInfo(offer, "inactive", "activate-btn", "Activate", activate)

        }))
}


function fillInfo(offer, class1, class2, var3, func) {

    let tr = document.createElement('tr')

    let td1 = document.createElement('td')
    td1.textContent = offer.visibleId

    let td2 = document.createElement('td')
    td2.classList.add(class1)
    td2.textContent = offer.status

    let td3 = document.createElement('td')
    td3.classList.add('td-name')
    let a = document.createElement('a')
    a.href = "/offers/" +  offer.visibleId
    a.textContent = offer.offerPageOneName
    td3.appendChild(a)

    let td4 = document.createElement('td')
    td4.textContent = offer.createdOn

    let tdEditBtn = document.createElement('td')
    let editBtn = document.createElement('button')
    editBtn.classList.add("delete-btn")
    editBtn.dataset.id = offer.visibleId
    editBtn.textContent = 'Edit'
    editBtn.addEventListener('click', edit)
    tdEditBtn.appendChild(editBtn);

    let tdActivationBtn = document.createElement('td')
    let activationBtn = document.createElement('button')
    activationBtn.classList.add(class2)
    activationBtn.dataset.id = offer.visibleId
    activationBtn.textContent = var3
    activationBtn.addEventListener('click', func)
    tdActivationBtn.appendChild(activationBtn);

    tr.appendChild(td1)
    tr.appendChild(td2)
    tr.appendChild(td3)
    tr.appendChild(td4)
    tr.appendChild(tdEditBtn)
    tr.appendChild(tdActivationBtn)

    offersTBody.appendChild(tr)


}



