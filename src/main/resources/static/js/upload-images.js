let pathArray = window.location.pathname.split('/');
let offerId = pathArray[pathArray.length - 1]
let imagesTBody = document.getElementById('imagesTBody')


window.addEventListener('load', displayImages)

function displayImages() {

    offersTBody.innerHTML = ""


    fetch(`http://localhost:8080/api/offers/${offerId}/pictures`)
        .then(result => result.json())
        .then(json => json.forEach( picture => {


            let tr = document.createElement('tr')

            let td1 = document.createElement('td')
            let img = document.createElement('img')
            img.classList.add('display-img')
            img.src = picture.url
            td1.appendChild(img)

            let td2 = document.createElement('td')
            let deleteBtn = document.createElement('button')
            deleteBtn.classList.add('delete-btn')
            deleteBtn.dataset.id = picture.id
            deleteBtn.addEventListener('click', deleteImage)
            td2.appendChild(deleteBtn)

            tr.appendChild(td1)
            tr.appendChild(td2)

            imagesTBody.appendChild(tr)

        }))
}

function deleteImage(event) {

    let imgId = event.target.dataset.id

    fetch(`http://localhost:8080/api/offers/${offerId}/pictures/${imgId}/delete`, {
        method: 'DELETE'
    }).then (_ => displayImages())

}
