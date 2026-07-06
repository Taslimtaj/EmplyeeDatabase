const API_BASE = '/api/employees';

const employeeTableBody = document.getElementById('employeeTableBody');
const employeeCount = document.getElementById('employeeCount');
const employeeModal = document.getElementById('employeeModal');
const employeeForm = document.getElementById('employeeForm');
const modalTitle = document.getElementById('modalTitle');
const employeeIdInput = document.getElementById('employeeId');
const employeeNameInput = document.getElementById('employeeName');
const formError = document.getElementById('formError');
const toast = document.getElementById('toast');

let editMode = false;
let editingId = null;

document.getElementById('addEmployeeBtn').addEventListener('click', openAddModal);
document.getElementById('refreshBtn').addEventListener('click', loadEmployees);
document.getElementById('closeModalBtn').addEventListener('click', closeModal);
document.getElementById('cancelBtn').addEventListener('click', closeModal);
employeeForm.addEventListener('submit', handleSubmit);

employeeModal.addEventListener('click', (e) => {
    if (e.target === employeeModal) closeModal();
});

async function loadEmployees() {
    try {
        const response = await fetch(API_BASE);
        if (!response.ok) throw new Error('Failed to load employees');
        const employees = await response.json();
        renderEmployees(employees);
    } catch (err) {
        showToast(err.message, 'error');
    }
}

function renderEmployees(employees) {
    employeeCount.textContent = employees.length;

    if (employees.length === 0) {
        employeeTableBody.innerHTML = `
            <tr class="empty-row">
                <td colspan="3">No employees yet. Click "Add Employee" to get started.</td>
            </tr>`;
        return;
    }

    employeeTableBody.innerHTML = employees.map(emp => `
        <tr>
            <td><strong>${escapeHtml(emp.employeeId)}</strong></td>
            <td>${escapeHtml(emp.employeeName)}</td>
            <td>
                <div class="row-actions">
                    <button type="button" class="btn btn-edit" data-edit="${escapeHtml(emp.employeeId)}" data-name="${escapeHtml(emp.employeeName)}">Edit</button>
                    <button type="button" class="btn btn-danger" data-delete="${escapeHtml(emp.employeeId)}">Delete</button>
                </div>
            </td>
        </tr>
    `).join('');

    employeeTableBody.querySelectorAll('[data-edit]').forEach(btn => {
        btn.addEventListener('click', () => openEditModal(btn.dataset.edit, btn.dataset.name));
    });

    employeeTableBody.querySelectorAll('[data-delete]').forEach(btn => {
        btn.addEventListener('click', () => deleteEmployee(btn.dataset.delete));
    });
}

function openAddModal() {
    editMode = false;
    editingId = null;
    modalTitle.textContent = 'Add Employee';
    employeeForm.reset();
    employeeIdInput.disabled = false;
    hideFormError();
    employeeModal.classList.remove('hidden');
    employeeIdInput.focus();
}

function openEditModal(id, name) {
    editMode = true;
    editingId = id;
    modalTitle.textContent = 'Edit Employee';
    employeeIdInput.value = id;
    employeeNameInput.value = name;
    employeeIdInput.disabled = true;
    hideFormError();
    employeeModal.classList.remove('hidden');
    employeeNameInput.focus();
}

function closeModal() {
    employeeModal.classList.add('hidden');
    employeeForm.reset();
    employeeIdInput.disabled = false;
    hideFormError();
}

async function handleSubmit(e) {
    e.preventDefault();
    hideFormError();

    const employeeId = employeeIdInput.value.trim();
    const employeeName = employeeNameInput.value.trim();
    const payload = { employeeId, employeeName };

    try {
        let response;
        if (editMode) {
            response = await fetch(`${API_BASE}/${encodeURIComponent(editingId)}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });
        } else {
            response = await fetch(API_BASE, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });
        }

        const data = response.ok ? null : await response.json().catch(() => ({}));

        if (!response.ok) {
            showFormError(data.message || 'Something went wrong');
            return;
        }

        closeModal();
        showToast(editMode ? 'Employee updated successfully' : 'Employee added successfully', 'success');
        await loadEmployees();
    } catch (err) {
        showFormError(err.message);
    }
}

async function deleteEmployee(id) {
    if (!confirm(`Delete employee ${id}?`)) return;

    try {
        const response = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, {
            method: 'DELETE',
        });

        if (!response.ok) {
            const data = await response.json().catch(() => ({}));
            throw new Error(data.message || 'Failed to delete employee');
        }

        showToast('Employee deleted successfully', 'success');
        await loadEmployees();
    } catch (err) {
        showToast(err.message, 'error');
    }
}

function showFormError(message) {
    formError.textContent = message;
    formError.classList.remove('hidden');
}

function hideFormError() {
    formError.classList.add('hidden');
    formError.textContent = '';
}

function showToast(message, type = 'success') {
    toast.textContent = message;
    toast.className = `toast ${type}`;
    setTimeout(() => toast.classList.add('hidden'), 3000);
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

loadEmployees();
