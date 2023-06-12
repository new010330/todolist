const selectedTypeButton = document.querySelector(".selected-type-button");
const typeSelectBoxList = document.querySelector(".type-select-box-list");
const todoContentList = document.querySelector(".todo-content-list");

const typeSelectBoxListLis = typeSelectBoxList.querySelectorAll("li");
//const test = document.querySelector(".selected-type");
let listType = "all";
load();

selectedTypeButton.onclick = () => {
    typeSelectBoxList.classList.toggle("visible");
}

// selectedTypeButton.onblur = () => {
//     if(!typeSelectBoxList.classList.contains("visible")) {
//         typeSelectBoxList.classList.toggle("visible"); // visible이 없을 때 토글 작동
//     }
// }

for(let i = 0; i < typeSelectBoxListLis.length; i++) {
    typeSelectBoxListLis[i].onclick = () => {
        // alert(i);

		for(let i = 0; i < typeSelectBoxListLis.length; i++) {
			typeSelectBoxListLis[i].classList.remove("type-selected");
		}
		
		const selectedType = document.querySelector(".selected-type");
		typeSelectBoxListLis[i].classList.add("type-selected");
		
		selectedType.textContent = typeSelectBoxListLis[i].textContent;

        listType = typeSelectBoxListLis[i].textContent;
        todoContentList.innerHTML = ' ';
        // test.innerHTML = ' ';
        // test.innerHTML += listType;
        load();

        typeSelectBoxList.classList.toggle("visible");
    }
}

function load() {
    $.ajax({
        type: "get",
        url: `/api/v1/todolist/list/${listType}`,
        data: {
            page: 1,
            contentCount: 20
        },
        datatype: "json",
        success: (response) => {
            // console.log(JSON.stringify(response));
            getList(response.data);
        },
        error: 
            errorMessage
    });
}

function errorMessage(request, status, error) {
    alert("요청실패");
    console.log(request.status);
    console.log(request.responseText);
    console.log(error);
}

function getList(data) {
    for(let content of data) {
        const incompleteCountNumber = document.querySelector(".incomplete-count-number");
        incompleteCountNumber.textContent = data[0].incompleteCount;

        const listContent = `
                            <li class="todo-content">
                                <input type="checkbox" id="complete-check-${content.todoCode}" class="complete-check">
                                <label for="complete-check-${content.todoCode}"></label>
                                <div class="todo-content-text">${content.todo}</div>
                                <input type="text" class="todo-content-input visible">
                                <input type="checkbox" id="importance-check-${content.todoCode}" class="importance-check">
                                <label for="importance-check-${content.todoCode}"></label>
                                <div class="trash-button">
                                    <i class="fa-solid fa-trash"></i>
                                </div>
                            </li>
        `
        todoContentList.innerHTML += listContent;
    }
}

