const selectedTypeButton = document.querySelector(".selected-type-button");
const typeSelectBoxList = document.querySelector(".type-select-box-list");

selectedTypeButton.onclick = () => {
    typeSelectBoxList.classList.toggle("visible");
}

selectedTypeButton.onblur = () => {
    typeSelectBoxList.classList.toggle("visible");
}

function load() {
    $.ajax({
        type: "post",
        url: "/api/v1/todolist"
    });
}