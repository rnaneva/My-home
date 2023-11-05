$(document).ready(displayFavouriteOffers);
$(document).ready(displayRentOffers);
$(document).ready(displaySaleOffers);
$(document).ready(displayAgencyOffers);


function displayRentOffers() {
    fetch(`http://localhost:8080/api/rent`)
        .then(result => result.json())
        .then(json => json.length === 0 ? $("#rent-aside").addClass("no-offers") :
            json.forEach(offer => {
                let article = getArticle(offer);
                $("#rent-offers").append(article);
            }))
}


function displaySaleOffers() {
    fetch(`http://localhost:8080/api/sale`)
        .then(result => result.json())
        .then(json => json.length === 0 ? $("#sale-aside").addClass("no-offers") :
            json.forEach(offer => {
                let article = getArticle(offer);
                $("#sale-offers").append(article);
            }))
}


function displayAgencyOffers() {
    let pathArray = window.location.pathname.split('/');
    let name = pathArray[pathArray.length - 1]
    fetch(`http://localhost:8080/api/ag/${name}`)
        .then(result => result.json())
        .then(json => json.length === 0 ? $("#agn-aside").addClass("no-offers") :
            json.forEach(offer => {
                let article = getArticle(offer);
                $("#agn-offers").append(article);
            }))

}


function displayFavouriteOffers() {

    fetch(`http://localhost:8080/api/favourites`)
        .then(result => result.json())
        .then(json => json.length === 0 ? $("#fav-aside").addClass("no-offers") :
            json.forEach(offer => {
                let article = getArticle(offer);
                $("#fav-offers").append(article);
            }))

}



function getArticle(offer) {
    return `<article class="offer-search">
    <div class="img">
        <img src="${offer.imageURL}"  alt="${offer.name}">
    </div>
    <div class="text text-cntr">
        <h3>
            <a class="link-reset"
               href=http://localhost:8080/offers/${offer.visibleId}>
                ${offer.name}</a>
        </h3>
        <div><i class="fa-sharp fa-solid fa-location-dot"></i>
            <span>${offer.address}</span></div>

        <div class="text">
            <span class="bold">${offer.area} m2.</span>
            <span class="floor"><i class="fa-solid fa-stairs"></i>
            <span >${offer.floorInfo}</span>
          </span>
            <div class="italic">
                ${offer.plan}
            </div>
            <div class="desc">${offer.description}</div>
        </div>
        <h4  class="price">${offer.price} €</h4>
    </div>
</article>`
}