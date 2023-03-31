let dropdown = document.getElementById("dropdown-btn")
let bars = document.getElementById('dropdown-elements')


dropdown.addEventListener('mouseover', show)
dropdown.addEventListener('mouseout', hide)

function show() {
    bars.classList.remove('hide-elements')
    bars.classList.add('nav-hovered-dropdown')
}

function hide() {
    bars.classList.remove('nav-hovered-dropdown')
    bars.classList.add('hide-elements')
}
