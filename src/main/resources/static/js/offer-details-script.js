// enlarge photo offer-details

let background = document.getElementById("background");
let imges = Array.from(document.getElementsByClassName('image'))
let currentImg = document.getElementById("current-img");

imges.forEach(img => img.addEventListener('click', enlarge))

function enlarge() {
    background.style.display = "block";
    currentImg.src = this.src;
}

currentImg.onclick = function () {
    background.style.display = "none";
}






