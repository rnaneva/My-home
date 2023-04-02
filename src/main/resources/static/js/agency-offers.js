let offersTBody = document.getElementById('tbody-offers')
let pathArray = window.location.pathname.split('/');
let id = pathArray[pathArray.length - 1]
offersTBody.innerHTML = ""

let inactive = window.location.pathname.includes("inactive")

if (inactive) {
    window.addEventListener('load', displayInactiveOffers)
} else {
    window.addEventListener('load', displayActiveOffers)
}


function displayActiveOffers() {

    fetch(`http://localhost:8080/api/offers/active/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(offer => {

            // todo link to offer details
            // todo edit button
            let info = `<tr >
                    <td>${offer.visibleId}</td>\n
                    <td class="status-active">${offer.status}</td>\n
                    <td class="td-name">\n
                        <a href="">${offer.offerPageOneName}</a>\n
                    </td>\n
                    <td>${offer.createdOn}</td>\n
                    <td><button type="submit" class="new-btn">Edit</button></td>\n
                </tr>`


            offersTBody.innerHTML += info
        }))
}

function displayInactiveOffers() {

    fetch(`http://localhost:8080/api/offers/inactive/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(offer => {

            // todo link to offer details
            // todo edit button
            let info = `<tr >
                    <td>${offer.visibleId}</td>\n
                    <td class="status-inactive">${offer.status}</td>\n
                    <td class="td-name">\n
                        <a href="">${offer.offerPageOneName}</a>\n
                    </td>\n
                    <td>${offer.createdOn}</td>\n
                    <td><button type="submit" class="new-btn">Edit</button></td>\n
                </tr>`


            offersTBody.innerHTML += info
        }))
}