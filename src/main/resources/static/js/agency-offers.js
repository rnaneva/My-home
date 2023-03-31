let offersTBody = document.getElementById('tbody-offers')
offersTBody.innerHTML = ""


window.addEventListener('load', function displayActiveOffers() {

        let pathArray = window.location.pathname.split('/');
        let id = pathArray[pathArray.length - 1]

    fetch(`http://localhost:8080/api/offers/active/${id}`)
        .then(result => result.json())
        .then(json => json.forEach(offer => {

               let info = `<tr >
                    <td>${offer.visibleId}</td>\n
                    <td class="active">${offer.status}</td>\n
                    <td class="td-name">\n
                        <a href="">${offer.offerPage1Name}</a>\n
                    </td>\n
                    <td>${offer.createdOn}</td>\n
                    <td><button type="submit" class="new-btn">Edit</button></td>\n
                    <td><button type="submit" class="delete-btn">Delete</button></td>\n
                </tr>`

            offersTBody.innerHTML += info
        }))
})