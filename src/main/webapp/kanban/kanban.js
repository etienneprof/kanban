/***************************************************************
 ****************** VARIABLES DECLARATIONS *********************
 ***************************************************************/

const URL_COLS = "http://localhost:8080/Kanban/api/columns/";
const URL_ISSUES = "http://localhost:8080/Kanban/api/issues/";

let container = document.getElementById("container");
let add_col_form = document.getElementById("add_column");
let add_col_input = document.getElementById("new_column_label");
let popup = document.getElementById("popup");
let popup_close = document.getElementById("popup_close");

let next_columns_count = 1;
let next_issue_count = [];
let issues = [];
let dragged_issue = null;


/***************************************************************
 *** DEFAULT BEHAVIOURS (inital loading + listener for popup) **
 ***************************************************************/
fetch_columns();
add_col_form.addEventListener("submit", add_column);
popup_close.addEventListener("click", close_popup);



/***************************************************************
 *********************** FETCH FUNCTIONS ***********************
 ***************************************************************/
function fetch_columns() {
    fetch(URL_COLS)
        .then(response => response.json())
        .then(display_columns);
}

function add_issue(column_id, form, input) {
    fetch(URL_ISSUES, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "columnId": column_id,
            "title": input.value,
            "order": next_issue_count[column_id]
        })
    })
    .then(response => response.json())
    .then(json => {
        form.reset();
        display_issue(column_id, json, form.parentElement);
    });
}

function add_column(event) {
    event.preventDefault();

    fetch(URL_COLS, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            label: add_col_input.value,
            order: next_columns_count
        })
    })
    .then(response => response.json())
    .then(json => {
        add_col_form.reset();
        display_column(json)}
    );
}

function delete_column(columnId, div) {
    if (confirm("Are you sure you want to delete this item ?")) {
        fetch(URL_COLS + columnId, {
            method: "DELETE"
        })
        .then(_ => container.removeChild(div));
    }
}

function save_issue() {
    let id = popup.querySelector("#issue_id").value;
    let title = popup.querySelector("#title").value;
    let description = popup.querySelector("#description").value;

    fetch(URL_ISSUES + id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "title": title,
            "description": description
        })
    })
    .then(response => response.json())
    .then(issue => {
        document.querySelector(`#issue${id} p`).innerText = title;
        issues[id] = issue;
        close_popup();
    });
}

function delete_issue() {
    let id = popup.querySelector("#issue_id").value;
    if (confirm("Are you sure you want to delete this item ?")) {
        fetch(URL_ISSUES + id, {
            method: "DELETE"
        })
        .then(_ => {
            close_popup();
            document.querySelector(`#issue${id}`).remove();
        });
    }
}


/***************************************************************
 ********************* DISPLAY FUNCTIONS ***********************
 ***************************************************************/
function display_columns(columns) {
    columns.forEach(display_column);
}

function display_column(column) {
    next_columns_count++;

    let div = document.createElement("div");
    div.classList.add("column");
    div.style.order = column.order;
    div.id = `col_${column.id}`;

    let h3 = document.createElement("h3");
    h3.innerText = column.label;

    let delete_button = document.createElement("p");
    delete_button.innerHTML = "&#10006;";
    delete_button.classList.add("delete-button")
    delete_button.addEventListener("click", () => delete_column(column.id, div));

    div.appendChild(h3);
    div.appendChild(delete_button);

    next_issue_count[column.id] = 0;
    if (column.relatedIssues) {
        column.relatedIssues.forEach(issue => {
            display_issue(column.id, issue, div);
        });
    }

    div.addEventListener("dragover", dragover);
    div.addEventListener("drop", drop);

    div.appendChild(generateIssueForm(column.id));
    container.prepend(div);
}

function display_issue(columnId, issue, div) {
    next_issue_count[columnId]++;
    let issue_div = document.createElement("div");
    issue_div.id = `issue${issue.id}`;
    issue_div.style.order = issue.order;
    issue_div.draggable = true;

    let issue_content = document.createElement("p");
    issue_content.innerText = issue.title;

    issues[issue.id] = issue;
    issue_div.addEventListener("click", () => display_popup(issue.id));
    issue_div.addEventListener("dragstart", start_dragging);

    issue_div.appendChild(issue_content);
    div.appendChild(issue_div);
}

function generateIssueForm(column_id) {
    let new_issue_form = document.createElement("form");
    let input = document.createElement("input");
    input.type = "text";
    input.placeholder = "Create a new issue";
    input.classList.add("issue-input");

    new_issue_form.addEventListener("submit", (e) => {
        e.preventDefault();
        add_issue(column_id, new_issue_form, input)
    });

    new_issue_form.appendChild(input);
    return new_issue_form;
}


/***************************************************************
 ********************** POPUP BEHAVIOUR ************************
 ***************************************************************/
function display_popup(id) {
    let issue = issues[id];
    popup.classList.remove("hidden");

    popup.querySelector("#issue_id").value = issue.id;
    popup.querySelector("#title").value = issue.title;
    popup.querySelector("#description").value = issue.description;

    popup.querySelector("#delete_issue").addEventListener("click", delete_issue);
    popup.querySelector("#save_issue").addEventListener("click", save_issue);
}

function close_popup() {
    popup.classList.add("hidden");
    popup.querySelector("#delete_issue").removeEventListener("click", delete_issue);
}


/***************************************************************
 ****************** DRAG AND DROP FUNCTIONS ********************
 ***************************************************************/
function start_dragging(event) {
    event.dataTransfer.setData("text/plain", event.target.id);
    event.dataTransfer.dropEffect = "move";
}

function dragover(event) {
    event.preventDefault();
    event.dataTransfer.dropEffect = "move";
}

function drop(event) {
    event.preventDefault();
    let target = event.target;
    let targetFound = false;
    while (!targetFound && target.parentElement) {
        if (target.classList.contains("column")) {
            let issueId = event.dataTransfer.getData("text/plain");
            target.appendChild(document.getElementById(issueId));

            let issue_id = issueId.substring(5);
            let column_id = target.id.split("_")[1];
            fetch(URL_ISSUES + `${issue_id}/${column_id}`, {
                method: "PUT"
            });

            targetFound = true;
        } else {
            target = target.parentElement;
        }
    }
}
