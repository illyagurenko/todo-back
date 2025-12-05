const API_URL = '/ToDoTask';

document.addEventListener('DOMContentLoaded', loadTasks);

async function loadTasks() {
    const list = document.getElementById('taskList');
    list.innerHTML = `<li class="loading">Загрузка...</li>`;

    try {
        const response = await fetch(API_URL);
        const tasks = await response.json();
        render(tasks);
    } catch (e) {
        list.innerHTML = `<li class="loading" style="color:red">Ошибка сервера</li>`;
    }
}

function render(tasks) {
    const list = document.getElementById('taskList');
    list.innerHTML = '';
    tasks.sort((a, b) => a.id - b.id);

    tasks.forEach(task => {
        const isDone = task.isCompleted ? 'completed' : '';
        
        const li = document.createElement('li');
        li.className = `task-item ${isDone}`;
        li.innerHTML = `
            <div class="task-content">
                <!-- Галочка (PATCH) -->
                <div class="checkbox-circle" onclick="toggleTask(${task.id}, ${task.isCompleted})">
                    <i class="fa-solid fa-check"></i>
                </div>
                <!-- Текст -->
                <div class="task-text">
                    <h3>${task.nameTask}</h3>
                    ${task.description ? `<p>${task.description}</p>` : ''}
                </div>
            </div>
            
            <div style="display: flex; gap: 5px;">
                <!-- Кнопка Редактировать (открывает модалку) -->
                <button class="edit-btn" onclick="openEditModal(${task.id}, '${task.nameTask}', '${task.description}', ${task.isCompleted})">
                    <i class="fa-solid fa-pen"></i>
                </button>
                <!-- Кнопка Удалить -->
                <button class="delete-btn" onclick="deleteTask(${task.id})">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </div>
        `;
        list.appendChild(li);
    });
}

// --- Создание (POST) ---
async function createTask() {
    const name = document.getElementById('nameInput').value.trim();
    const desc = document.getElementById('descInput').value.trim();
    if (!name) return alert('Имя обязательно!');

    await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nameTask: name, description: desc })
    });
    
    document.getElementById('nameInput').value = '';
    document.getElementById('descInput').value = '';
    loadTasks();
}

// --- Переключение галочки (PATCH - частичное обновление) ---
async function toggleTask(id, currentStatus) {
    // Шлем ТОЛЬКО поле, которое меняется
    await fetch(`${API_URL}/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ isCompleted: !currentStatus })
    });
    loadTasks();
}

// --- Редактирование (PUT - полное обновление) ---

// 1. Открыть окно и заполнить полями
function openEditModal(id, name, desc, status) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('editId').value = id;
    document.getElementById('editName').value = name;
    document.getElementById('editDesc').value = desc;
    // Мы запоминаем текущий статус где-то (можно в атрибуте), 
    // чтобы при сохранении отправить его обратно.
    document.getElementById('editModal').dataset.status = status;
}

// 2. Закрыть окно
function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

// 3. Сохранить изменения
async function saveEdit() {
    const id = document.getElementById('editId').value;
    const name = document.getElementById('editName').value;
    const desc = document.getElementById('editDesc').value;
    const status = document.getElementById('editModal').dataset.status === 'true';

    await fetch(`${API_URL}/${id}`, {
        method: 'PUT', // Используем PUT для полной замены
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            nameTask: name,
            description: desc,
            isCompleted: status // Обязательно возвращаем статус как был
        })
    });
    
    closeModal();
    loadTasks();
}

// --- Удаление (DELETE) ---
async function deleteTask(id) {
    if(confirm('Удалить?')) {
        await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        loadTasks();
    }
}