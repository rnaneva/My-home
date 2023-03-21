// upload multiple images

const output = document.querySelector("output")
const input = document.querySelector("input")
let imagesArray = []

input.addEventListener('change', () => {
  const files = input.files
  for (let i = 0; i < files.length; i++) {
    imagesArray.push(files[i])
  }
})


// message

const form = Array.from(document.getElementsByClassName('upload-file-form'))[0]
const div = Array.from(document.getElementsByClassName('successful-upload'))[0]
const btn = document.getElementById('upload-btn')


btn.addEventListener('click', () =>{

    form.style.display = "none"
   div.style.display = "block"

  })
