const selectedTypeButton = document.querySelector(".selected-type-button");
const typeSelectBoxList = document.querySelector(".type-select-box-list");
const todoContentList = document.querySelector(".todo-content-list");

const typeSelectBoxListLis = typeSelectBoxList.querySelectorAll("li");
const sectionBody = document.querySelector(".section-body");

let page = 1;
let totalPage = 0;

//const test = document.querySelector(".selected-type");

sectionBody.onscroll = () => {
    console.log("offsetHeight: " + sectionBody.offsetHeight);
    console.log("scrollTop: " + sectionBody.scrollTop);
    console.log("clientHeight: "  + todoContentList.clientHeight);
    let checkNum = todoContentList.clientHeight - sectionBody.offsetHeight - sectionBody.scrollTop;

    if(checkNum < 1 && checkNum > -1) {
        let newPage = page++;
        if(newPage < totalPage) {
            load();
        }
    }
}

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
        page = 1;
		for(let i = 0; i < typeSelectBoxListLis.length; i++) {
			typeSelectBoxListLis[i].classList.remove("type-selected");
		}
		
		const selectedType = document.querySelector(".selected-type");
		typeSelectBoxListLis[i].classList.add("type-selected");
		
        listType = typeSelectBoxListLis[i].textContent.toLowerCase();
		selectedType.textContent = typeSelectBoxListLis[i].textContent;

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
            "page": page,
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

function setTotalCount(totalCount) {
    totalPage = totalCount % 20 == 0 ? totalCount / 20 : Math.floor(totalCount / 20) + 1;
}

function getList(data) {
    for(let content of data) {
        const incompleteCountNumber = document.querySelector(".incomplete-count-number");
        incompleteCountNumber.textContent = data[0].incompleteCount;
		
        setTotalCount(data[0].totalCount);

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
    addEvent();
}

function addEvent() {
    const todoContents = document.querySelectorAll(".todo-content");

    for(let i = 0; i < todoContents.length; i++) {
        let todoCode = todoContents[i].querySelector(".complete-check").getAttribute("id");
        todoCodeIndex = todoCode.substring(todoCode.lastIndexOf("-") + 1);
        console.log(todoCode);
        console.log(todoCodeIndex);
        
        todoContents[i].querySelector(".complete-check").onchange = () => {
            console.log(todoCode);
            console.log(todoCodeIndex);
            updateStatus("complete", todoCodeIndex);
        }

        todoContents[i].querySelector(".importance-check").onchange = () => {

        }
    }
}

function updateStatus(type, todoCode) {
    result = false;
    $.ajax({
        type: "put",
        url: `api/v1/todolist/${type}/todo/${todoCode}`,
        async: false,
        dataType: "json",
        success: (response) => {
            result = response.data;
        },
        error: errorMessage
    })
    return result;
}

function updateCheckStatus() {
    
}