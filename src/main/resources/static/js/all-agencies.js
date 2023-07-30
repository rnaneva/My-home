$(document).ready(displayAgenciesBriefInfo);

function displayAgenciesBriefInfo() {

    fetch(`http://localhost:8080/api/agencies`)
        .then(result => result.json())
        .then(json => json.forEach(agency => {


            let article = `<article class="offer-search" id = "agn-brief">
                   <div class="text">
                   <h3 id="agency-name">${agency.name}</h3>
                     <address><i class="fa-sharp fa-solid fa-location-dot"></i>${agency.address}</address>
                 <div><i class="fa-solid fa-phone"></i>${agency.phoneNumber}</div>
                    <div><i class="fa-solid fa-rectangle-ad"></i><a class="agn-properties"
                     href="http://localhost:8080/offers/agency/${agency.id}">${agency.numberOfOffers}</a>
                    </div>
                    </div>

                   <img class="agn-logo" src="${agency.logoUrl}" alt="">
                    </article>`


            $("#all-agencies").append(article);
        }))
}



