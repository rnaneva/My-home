
function displayAgenciesBriefInfo() {

    fetch(`http://localhost:8080/api/agencies`)
        .then(result => result.json())
        .then(json => json.forEach(agency => {

            let article = getArticle(agency);

            $("#all-agencies").append(article);
        }))
}

$(document).ready(displayAgenciesBriefInfo);

function getArticle(agency){
    return `<article class="offer-search" id = "agn-brief">
                   <div class="text">
                   <h3  id="agency-name"><a class="link-reset" href="http://localhost:8080/offers/ag/${agency.id}">${agency.name}</a></h3>
                     <address><i class="fa-sharp fa-solid fa-location-dot"></i>${agency.address}</address>
                 <div><i class="fa-solid fa-phone"></i>${agency.phoneNumber}</div>
                    <div><i class="fa-solid fa-rectangle-ad"></i><a class="agn-properties"
                     href="http://localhost:8080/offers/ag/${agency.id}">${agency.numberOfOffers} offers</a>
                    </div>
                    </div>

                   <img class="agn-logo" src="${agency.logoUrl}" alt="">
                    </article>`
}

//todo filter
function filterByAgencyName(name) {
    fetch(`http://localhost:8080/api/agencies/${name}`)
        .then(result => result.json())
        .then(json => json.value(agency => {

            let article = getArticle(agency);

            $("#all-agencies").append(article);
        }))
}

$("#searchInput").on("keyup", filterByAgencyName);







